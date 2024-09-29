package com.module.price.dto.request;

import com.module.price.enums.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String userId;
    private UserType userType;
    private int customerTenure; // in months
}
