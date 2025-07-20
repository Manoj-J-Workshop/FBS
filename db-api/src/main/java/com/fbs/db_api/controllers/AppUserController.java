package com.fbs.db_api.controllers;

import com.fbs.db_api.models.AppUser;
import com.fbs.db_api.repositories.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/db/user")
public class AppUserController {

    AppUserRepo appUserRepo;

    @Autowired
    public AppUserController(AppUserRepo appUserRepo){
        this.appUserRepo = appUserRepo;
    }

    @PostMapping("/create")
    public ResponseEntity CreateUser(@RequestBody AppUser appUser){
        AppUser userRepo = appUserRepo.save(appUser);
        return new ResponseEntity(userRepo, HttpStatus.CREATED);
    }
}
