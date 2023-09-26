package com.eatelector.api.service;

import com.eatelector.api.dto.AddRestaurantRequestDto;
import com.eatelector.api.dto.AddRestaurantResponseDto;
import com.eatelector.api.dto.GetRandomRestaurantResponseDto;
import com.eatelector.api.dto.ResponseDto;
import com.eatelector.api.entity.Restaurant;
import com.eatelector.api.enums.ResponseStatus;
import com.eatelector.api.exception.ApiException;
import com.eatelector.api.exception.NoRecordsFoundException;
import com.eatelector.api.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class RestaurantService {

    private RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public AddRestaurantResponseDto addRestaurant(AddRestaurantRequestDto addRestaurantRequestDto) {

        try {
            Restaurant restaurant = Restaurant.builder()
                    .name(addRestaurantRequestDto.getRestaurantName())
                    .build();

            restaurant = restaurantRepository.save(restaurant);

            return AddRestaurantResponseDto.builder()
                    .status(ResponseStatus.SUCCESS)
                    .message("Restaurant added successfully!")
                    .restaurantId(restaurant.getId())
                    .restaurantName(restaurant.getName())
                    .build();

        } catch (Exception e) {
            log.error("Failed to add restaurant. Error: {}", e.getMessage());
            throw new ApiException("Error occurred while trying to add the restaurant. Please try again later.");
        }

    }

    public GetRandomRestaurantResponseDto getRandomRestaurant() {

        /*
         * Intention of the code below is to provide a solution which is independent of the database system used.
         * It also assumes that the number of restaurants stored at a given time is not very large.
         * Instead, a native query can be used to retrieve a random record.
         */

        try {
            List<Restaurant> allRestaurantList = restaurantRepository.findAll();

            if (CollectionUtils.isEmpty(allRestaurantList)) {
                throw new NoRecordsFoundException("No restaurants found. Please add one or more restaurants and try.");
            }

            Random random = new Random();
            int randomIndex = random.nextInt(allRestaurantList.size());
            Restaurant randomRestaurant = allRestaurantList.get(randomIndex);

            return GetRandomRestaurantResponseDto.builder()
                    .status(ResponseStatus.SUCCESS)
                    .message("A random restaurant found successfully!")
                    .restaurantId(randomRestaurant.getId())
                    .restaurantName(randomRestaurant.getName())
                    .build();

        } catch (NoRecordsFoundException e) {
            throw  e;

        } catch (Exception e) {
            log.error("Failed to retrieve a random restaurant. Error: {}", e.getMessage());
            throw new ApiException("Error occurred while trying to find a restaurant. Please try again later.");
        }

    }

    public ResponseDto deleteAllRestaurants() {
        try {
            restaurantRepository.deleteAll();

            return ResponseDto.builder()
                    .status(ResponseStatus.SUCCESS)
                    .message("All restaurants deleted successfully.")
                    .build();

        } catch (Exception e) {
            log.error("Failed to delete all restaurants. Error: {}", e.getMessage());
            throw new ApiException("Failed to delete restaurants. Please try again later.");
        }
    }
}
