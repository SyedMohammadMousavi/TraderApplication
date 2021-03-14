package com.traders.application.items.controller;

import com.traders.application.items.entity.Items;
import com.traders.application.items.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/items/list")
    @ResponseBody
    public ResponseEntity<List<Items>> getAllItems()
    {
        List<Items> allItems = userService.getAllItems();
        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }
}
