package com.traders.application.items.controller;

import com.traders.application.items.dto.ItemDTO;
import com.traders.application.items.entity.Items;
import com.traders.application.items.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trader")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/item/{itemId}")
    @ResponseBody
    public ItemDTO getItemForTrader(@PathVariable Integer itemId) {

        Items itemForTrader = itemService.getItemForTrader(itemId);
        return convertToDto(itemForTrader);
    }

    @GetMapping("/items")
    @ResponseBody
    public List<ItemDTO> getAllItemsForTrader() {
        List<Items> allItemsForTrader = itemService.getAllItemsForTrader();
        return allItemsForTrader.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/create/item")
    @ResponseBody
    public ResponseEntity<ItemDTO> createItemForTrader(@Valid @RequestBody ItemDTO item) {
        Items savedItem = itemService.createItem(convertToEntity(item));
        return new ResponseEntity<>(convertToDto(savedItem), HttpStatus.CREATED);
    }

    @PutMapping("/update/item")
    @ResponseBody
    public ResponseEntity<ItemDTO> updateItem(@Valid @RequestBody ItemDTO item) {
        Items updatedItem = itemService.updateItem(convertToEntity(item));
        return new ResponseEntity<>(convertToDto(updatedItem), HttpStatus.OK);
    }

    @DeleteMapping("/delete/item/{itemId}")
    @ResponseBody
    public ResponseEntity<String> deleteItem(@PathVariable Integer itemId) {
        String response = itemService.deleteItem(itemId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private ItemDTO convertToDto(Items item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    private Items convertToEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, Items.class);
    }

}
