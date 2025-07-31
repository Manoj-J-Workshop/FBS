package com.fbs.central_api.controllers;

import com.fbs.central_api.connectors.GeminiConnector;
import com.fbs.central_api.dto.LoginDto;
import com.fbs.central_api.exceptions.InvalidCredentials;
import com.fbs.central_api.service.UserService;
import com.fbs.central_api.utility.AuthUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Purpose of this generalUserController to have all the common endpoints of all the type of users.
 */
@Slf4j
@RestController
@RequestMapping("api/v1/central/user")
public class GeneralUserController {

    UserService userService;
    AuthUtility authUtility;

    public GeneralUserController(UserService userService, AuthUtility authUtility){
        this.userService = userService;
        this.authUtility = authUtility;
    }
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        log.info("Running Login method for user email: "+ loginDto.getEmail());
        try{
            String token = userService.isValidCredentials(loginDto.getEmail(),loginDto.getPassword());
            return new ResponseEntity<>(token, HttpStatus.OK);// Status code: 200 OK
        } catch (InvalidCredentials e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);// Status code: 401 UNAUTHORIZED
        }
    }
}
