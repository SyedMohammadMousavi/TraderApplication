package com.traders.application.items.service;

import com.traders.application.items.entity.Items;
import com.traders.application.items.entity.Users;
import com.traders.application.items.repository.ItemRepository;
import com.traders.application.items.repository.UserRepository;
import com.traders.application.items.util.CurrencyExchangeUtility;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CurrencyExchangeUtility currencyExchangeUtility;

    @InjectMocks
    @Spy
    public ItemService itemService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getAllItemsForTrader() {

        Users user = new Users();
        Items item = new Items();
        item.setId(1);
        item.setItemName("Test");
        item.setPrice(new BigDecimal(23.23));
        item.setCurrency("USD");
        item.setUserId(1);
        item.setDescription("Test Item");
        List<Items> itemsList = Arrays.asList(item);
        user.setItemsList(itemsList);

        Mockito.doReturn("Test").when(itemService).getLoggedInUserName();
        Mockito.doReturn(Optional.of(user)).when(userRepository).findByUsername(Mockito.anyString());
        Mockito.doReturn(itemsList).when(currencyExchangeUtility).ConvertCurrency(itemsList);

        List<Items> allItemsForTrader = itemService.getAllItemsForTrader();

        assertEquals(item.getItemName(),allItemsForTrader.get(0).getItemName());

    }

    @Test
    public void createItem() {

        Users user = new Users();
        user.setId(1);
        Items item = new Items();
        item.setId(1);
        item.setItemName("Test");
        item.setPrice(new BigDecimal(23.23));
        item.setCurrency("USD");
        item.setUserId(1);
        item.setDescription("Test Item");

        Mockito.doReturn("Test").when(itemService).getLoggedInUserName();
        Mockito.doReturn(Optional.of(user)).when(userRepository).findByUsername(Mockito.anyString());
        Mockito.doReturn(item).when(itemRepository).save(item);

        Items itemResult = itemService.createItem(item);

        assertEquals(item.getItemName(),itemResult.getItemName());

    }

    @Test
    public void updateItem() {
    }

    @Test
    public void getItemForTrader() {
    }

    @Test
    public void deleteItem() {
    }
}