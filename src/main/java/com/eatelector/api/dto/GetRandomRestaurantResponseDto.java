package com.eatelector.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class GetRandomRestaurantResponseDto extends ResponseDto {

    private Long restaurantId;
    private String restaurantName;

}
