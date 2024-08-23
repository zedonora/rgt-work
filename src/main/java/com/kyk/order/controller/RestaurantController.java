package com.kyk.order.controller;

import com.kyk.order.dto.DiningTableDto;
import com.kyk.order.dto.RestaurantDto;
import com.kyk.order.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;


    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{restaurantId}/tables")
    public ResponseEntity<List<DiningTableDto>> getRestaurantTables(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantTables(restaurantId));
    }
}