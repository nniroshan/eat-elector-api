package com.eatelector.api.controller;

import com.eatelector.api.dto.AddRestaurantRequestDto;
import com.eatelector.api.dto.AddRestaurantResponseDto;
import com.eatelector.api.dto.GetRandomRestaurantResponseDto;
import com.eatelector.api.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<AddRestaurantResponseDto> addRestaurant(
            @Valid @RequestBody AddRestaurantRequestDto addRestaurantRequestDto) {
        return ResponseEntity.ok(restaurantService.addRestaurant(addRestaurantRequestDto));
    }

    @GetMapping("/random")
    public ResponseEntity<GetRandomRestaurantResponseDto> getRandomRestaurant() {
        return ResponseEntity.ok(restaurantService.getRandomRestaurant());
    }

}
