package com.traders.application.items.util;

import com.traders.application.items.entity.Items;
import com.traders.application.items.model.CurrencyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrencyExchangeUtility {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;


    public CurrencyInfo getCurrencyConversion() {
        URI url = URI.create("http://data.fixer.io/api/latest?access_key=6999e23df31f50f642a4ba61e3389790");
        CurrencyInfo currencyInfo = restTemplate.getForObject(url, CurrencyInfo.class);

        logger.info(Boolean.toString(currencyInfo.isSuccess()));
        logger.info(currencyInfo.getBase());
        currencyInfo.getRates().forEach((k, v) -> logger.info("Key is: " + k + " Value is: " + v));

        return currencyInfo;
    }

    public List<Items> ConvertCurrency(List<Items> itemsList) {

        CurrencyInfo currencyConversion = getCurrencyConversion();

        List<Items> updatedList = itemsList.stream()
                .map(item -> {

                    if (!item.getCurrency().equalsIgnoreCase("GBP")) {
                        BigDecimal dbValue = currencyConversion.getRates().get(item.getCurrency());
                        BigDecimal baseValue = currencyConversion.getRates().get("GBP");

                        BigDecimal conversionFactor = dbValue.divide(baseValue, 6, RoundingMode.HALF_UP);
                        BigDecimal price = item.getPrice();

                        BigDecimal priceInGBP = price.divide(conversionFactor,6,RoundingMode.HALF_UP);

                        item.setCurrency("GBP");
                        item.setPrice(priceInGBP);
                    }

                    return item;
                })
                .collect(Collectors.toList());

        return updatedList;

    }
}
