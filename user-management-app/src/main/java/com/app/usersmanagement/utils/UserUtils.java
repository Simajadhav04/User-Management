package com.app.usersmanagement.utils;

import com.app.usersmanagement.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.app.usersmanagement.enums.Roles;
import lombok.Data;

import java.util.List;
/*
 *author: Sima Jadhav
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUtils {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String city;
    private Roles role;
    private String email;
    private String password;
    private Users users;
    private List<Users> usersList;

}
