package com.eatelector.api.controller;

import com.eatelector.api.dto.AddRestaurantRequestDto;
import com.eatelector.api.dto.AddRestaurantResponseDto;
import com.eatelector.api.dto.GetRandomRestaurantResponseDto;
import com.eatelector.api.dto.ResponseDto;
import com.eatelector.api.entity.Restaurant;
import com.eatelector.api.repository.RestaurantRepository;
import com.eatelector.api.service.RestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantRepository restaurantRepository;


    @Test
    void addRestaurantTestOnValidUserInput() throws Exception {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(getRestaurant());

        MvcResult result = mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getAddRestaurantRequestDto()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        AddRestaurantResponseDto addRestaurantResponseDto = new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(), AddRestaurantResponseDto.class);
        assertEquals("SUCCESS", addRestaurantResponseDto.getStatus().name());
        assertEquals("Restaurant added successfully!", addRestaurantResponseDto.getMessage());
        assertEquals(1L, addRestaurantResponseDto.getRestaurantId());
        assertEquals("restaurant-name", addRestaurantResponseDto.getRestaurantName());

    }

    @Test
    void addRestaurantTestOnInvalidUserInput() throws Exception {
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(getRestaurant());

        MvcResult result = mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getAddRestaurantRequestDto("")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        ResponseDto responseDto = new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(), ResponseDto.class);
        assertEquals("ERROR", responseDto.getStatus().name());
        assertEquals("Length of the restaurant name must be between 1 to 100 characters.",
                responseDto.getMessage());

    }

    @Test
    void addRestaurantTestOnInternalServerError() throws Exception {
        doThrow(new RuntimeException()).when(restaurantRepository).save(any());

        MvcResult result = mockMvc.perform(post("/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getAddRestaurantRequestDto()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andReturn();

        ResponseDto responseDto = new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(), ResponseDto.class);
        assertEquals("ERROR", responseDto.getStatus().name());
        assertEquals("Error occurred while trying to add the restaurant. Please try again later.",
                responseDto.getMessage());
    }

    @Test
    void getRandomRestaurantMethodTestWhenSavedRestaurantsExists() throws Exception {
        when(restaurantRepository.findAll()).thenReturn(getResurantList());

        MvcResult result = mockMvc.perform(get("/restaurant/random"))
                .andExpect(status().isOk())
                .andReturn();

        GetRandomRestaurantResponseDto getRandomRestaurantResponseDto =  new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(), GetRandomRestaurantResponseDto.class);

        assertEquals("SUCCESS", getRandomRestaurantResponseDto.getStatus().name());
        assertEquals("A random restaurant found successfully!", getRandomRestaurantResponseDto.getMessage());
        assertNotNull(getRandomRestaurantResponseDto.getRestaurantId());
        assertNotNull(getRandomRestaurantResponseDto.getRestaurantName());

    }

    @Test
    void getRandomRestaurantMethodTestWhenNoSavedRestaurants() throws Exception {
        when(restaurantRepository.findAll()).thenReturn(new ArrayList<>());

        MvcResult result = mockMvc.perform(get("/restaurant/random"))
                .andExpect(status().isNotFound())
                .andReturn();

        GetRandomRestaurantResponseDto getRandomRestaurantResponseDto =  new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(), GetRandomRestaurantResponseDto.class);

        assertEquals("NO_RECORDS_FOUND", getRandomRestaurantResponseDto.getStatus().name());
        assertEquals("No restaurants found. Please add one or more restaurants and try.",
                getRandomRestaurantResponseDto.getMessage());
    }

    @Test
    void getRandomRestaurantMethodTestOnInternalServerError() throws Exception {
        doThrow(new RuntimeException()).when(restaurantRepository).findAll();

        MvcResult result = mockMvc.perform(get("/restaurant/random"))
                .andExpect(status().isInternalServerError())
                .andReturn();

        GetRandomRestaurantResponseDto getRandomRestaurantResponseDto =  new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(), GetRandomRestaurantResponseDto.class);

        assertEquals("ERROR", getRandomRestaurantResponseDto.getStatus().name());
        assertEquals("Error occurred while trying to find a restaurant. Please try again later.",
                getRandomRestaurantResponseDto.getMessage());
    }

    private AddRestaurantRequestDto getAddRestaurantRequestDto() {
        return AddRestaurantRequestDto.builder()
                .restaurantName("restaurant-name")
                .build();
    }

    private AddRestaurantRequestDto getAddRestaurantRequestDto(String restaurantName) {
        return AddRestaurantRequestDto.builder()
                .restaurantName(restaurantName)
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