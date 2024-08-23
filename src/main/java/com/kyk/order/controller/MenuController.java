package com.kyk.order.controller;

import com.kyk.order.dto.MenuDetailDto;
import com.kyk.order.dto.MenuDto;
import com.kyk.order.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<List<MenuDto>> getRestaurantMenus(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuService.getRestaurantMenus(restaurantId));
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuDetailDto> getMenuDetail(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenuDetail(menuId));
    }
}


