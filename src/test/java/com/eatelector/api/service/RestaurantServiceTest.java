package com.eatelector.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.eatelector.api.dto.AddRestaurantRequestDto;
import com.eatelector.api.dto.AddRestaurantResponseDto;
import com.eatelector.api.dto.GetRandomRestaurantResponseDto;
import com.eatelector.api.entity.Restaurant;
import com.eatelector.api.exception.ApiException;
import com.eatelector.api.exception.NoRecordsFoundException;
import com.eatelector.api.repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Test
    void addRestaurantMethodTest() {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(getRestaurant());
        AddRestaurantResponseDto addRestaurantResponseDto
                = restaurantService.addRestaurant(getAddRestaurantRequestDto());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
        assertEquals("SUCCESS", addRestaurantResponseDto.getStatus().name());
        assertEquals("Restaurant added successfully!", addRestaurantResponseDto.getMessage());
        assertEquals(1L, addRestaurantResponseDto.getRestaurantId());
        assertEquals("restaurant-name", addRestaurantResponseDto.getRestaurantName());
    }

    @Test
    void addRestaurantMethodShouldThrowApiExceptionOnInternalError() {
        doThrow(new RuntimeException()).when(restaurantRepository).save(any());
        AddRestaurantRequestDto addRestaurantRequestDto = getAddRestaurantRequestDto();
        ApiException apiException = assertThrows(ApiException.class,
                () -> restaurantService.addRestaurant(addRestaurantRequestDto));
        assertEquals("Error occurred while trying to add the restaurant. Please try again later.",
                apiException.getMessage());
    }

    @Test
    void getRandomRestaurantMethodTestWhenSavedRestaurantsAreExists() {
        when(restaurantRepository.findAll()).thenReturn(getResurantList());
        GetRandomRestaurantResponseDto getRandomRestaurantResponseDto = restaurantService.getRandomRestaurant();
        assertEquals("SUCCESS", getRandomRestaurantResponseDto.getStatus().name());
        assertEquals("A random restaurant found successfully!", getRandomRestaurantResponseDto.getMessage());
        assertNotNull(getRandomRestaurantResponseDto.getRestaurantId());
        assertNotNull(getRandomRestaurantResponseDto.getRestaurantName());
    }

    @Test
    void getRandomRestaurantMethodTestWhenNoSavedRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());
        NoRecordsFoundException noRecordsFoundException = assertThrows(NoRecordsFoundException.class,
                () -> restaurantService.getRandomRestaurant());
        assertEquals("No restaurants found. Please add one or more restaurants and try.",
                noRecordsFoundException.getMessage());
    }

    @Test
    void getRandomRestaurantMethodTestWhenOnInternalServerError() {
        doThrow(new RuntimeException()).when(restaurantRepository).findAll();
        ApiException apiException = assertThrows(ApiException.class,
                () -> restaurantService.getRandomRestaurant());
        assertEquals("Error occurred while trying to find a restaurant. Please try again later.",
                apiException.getMessage());
    }

    private AddRestaurantRequestDto getAddRestaurantRequestDto() {
        return AddRestaurantRequestDto.builder()
                .restaurantName("restaurant-name")
                .build();
    }

    private Restaurant getRestaurant() {
        return Restaurant.builder()
                .id(1L)
                .name("restaurant-name")
                .build();
    }

    private Restaurant getRestaurant(Long id, String restaurantName) {
        return Restaurant.builder()
                .id(id)
                .name(restaurantName)
                .build();
    }

    private List<Restaurant> getResurantList() {
        return List.of(getRestaurant(1L, "restaurant-1"),
                getRestaurant(2L, "restaurant-2"));
    }

}