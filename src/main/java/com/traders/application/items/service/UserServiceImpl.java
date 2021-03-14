package com.traders.application.items.service;

import com.traders.application.items.entity.Items;
import com.traders.application.items.repository.ItemRepository;
import com.traders.application.items.util.CurrencyExchangeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyExchangeUtility currencyExchangeUtility;

    @Autowired
    private ItemRepository itemRepository;

    public List<Items> getAllItems()
    {
        List<Items> allItems = itemRepository.findAll();
        return currencyExchangeUtility.ConvertCurrency(allItems);
    }
}
