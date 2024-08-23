package com.kyk.order.service;

import com.kyk.order.dto.DiningTableDto;
import com.kyk.order.dto.RestaurantDto;
import com.kyk.order.entity.Restaurant;
import com.kyk.order.repository.DiningTableRepository;
import com.kyk.order.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final DiningTableRepository tableRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, DiningTableRepository tableRepository) {
        this.restaurantRepository = restaurantRepository;
        this.tableRepository = tableRepository;
    }

    public List<RestaurantDto> getAllRestaurants() {
        // 모든 식당 조회 로직
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        return restaurantList.stream().map(restaurant -> RestaurantDto.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .owner(restaurant.getOwner())
                .tables(tableRepository.findByRestaurantId(restaurant.getId()))
                .menus(restaurant.getMenus())
                .build()).collect(Collectors.toList());
    }

    public List<DiningTableDto> getRestaurantTables(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(IllegalArgumentException::new);

        // 특정 식당의 테이블 조회 로직
        return tableRepository.findByRestaurantId(restaurantId).stream().map(table -> DiningTableDto.builder()
                .id(table.getId())
                .restaurant(restaurant)
                .build()).collect(Collectors.toList());
    }
}