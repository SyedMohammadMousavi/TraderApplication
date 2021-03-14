package com.traders.application.items.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traders.application.items.dto.ItemDTO;
import com.traders.application.items.entity.Items;
import com.traders.application.items.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
class ItemsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();
        modelMapper = new ModelMapper();
    }


    @Test
    void getItemForTrader() throws Exception {

        Items item = new Items();
        Mockito.when(itemService.getItemForTrader(Mockito.anyInt()))
                .thenReturn(item);

        mockMvc.perform(get("/trader/item/5"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllItemsForTrader() throws Exception {

        List<Items> itemsList = Arrays.asList(new Items());

        Mockito.when(itemService.getAllItemsForTrader())
                .thenReturn(itemsList);

        mockMvc.perform(get("/trader/items"))
                .andExpect(status().isOk());
    }

    @Test
    void createItemForTrader() throws Exception {

        Items item = new Items();
        item.setItemName("Unit Test");
        item.setCurrency("INR");
        item.setPrice(new BigDecimal(23.23));

        ItemDTO itemDTO = convertToDto(item);

        String json = objectMapper.writeValueAsString(itemDTO);

        Mockito.when(itemService.createItem(item))
                .thenReturn(item);

        mockMvc.perform(post("/trader/create/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void updateItem() throws Exception {


        Items item = new Items();
        item.setId(5);
        item.setItemName("Unit Test");
        item.setCurrency("INR");
        item.setPrice(new BigDecimal(23.23));

        ItemDTO itemDTO = convertToDto(item);

        String json = objectMapper.writeValueAsString(itemDTO);

        Mockito.when(itemService.updateItem(item))
                .thenReturn(item);

        mockMvc.perform(put("/trader/update/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteItem() throws Exception {

        Mockito.when(itemService.deleteItem(Mockito.anyInt()))
                .thenReturn("Deleted Successfully!!!");

        mockMvc.perform(delete("/trader/delete/item/5"))
                .andExpect(status().isCreated());
    }

    private ItemDTO convertToDto(Items item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    private Items convertToEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, Items.class);
    }
}