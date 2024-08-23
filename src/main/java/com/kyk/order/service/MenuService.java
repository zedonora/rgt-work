package com.kyk.order.service;

import com.kyk.order.dto.MenuDetailDto;
import com.kyk.order.dto.MenuDto;
import com.kyk.order.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuDto> getRestaurantMenus(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId).stream()
                .map(MenuDto::new)
                .collect(Collectors.toList());
    }

    public MenuDetailDto getMenuDetail(Long menuId) {
        return menuRepository.findById(menuId)
                .map(MenuDetailDto::new)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다. menuId=" + menuId));
    }
}