package com.eatelector.api.dto;

import com.eatelector.api.enums.ResponseStatus;
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
public class ResponseDto {
    private ResponseStatus status;
    private String message;
}
