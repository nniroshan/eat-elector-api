package com.eatelector.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class AddRestaurantRequestDto {

    @NotNull(message = "Restaurant name can not be null.")
    @NotEmpty(message = "Restaurant name can not be empty.")
    @Size(min = 1, max = 100, message = "Length of the restaurant name must be between 1 to 100 characters.")
    private String restaurantName;

}
