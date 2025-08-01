package com.fbs.db_api.controllers;

import com.fbs.db_api.dto.AllUsersDto;
import com.fbs.db_api.models.AppUser;
import com.fbs.db_api.repositories.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get/byType/{userType}")
    public ResponseEntity getAlluserByUserType(@PathVariable String userType){
        List<AppUser> users = appUserRepo.findByUserType(userType);
        AllUsersDto allUsersDto = new AllUsersDto(users);
        return new ResponseEntity(allUsersDto, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<AppUser> updateAppUser(@RequestBody AppUser appUser){
        AppUser appUser1 = appUserRepo.save(appUser);
        return new ResponseEntity<>(appUser1, HttpStatus.OK);
    }

    @GetMapping("/get/byEmail/{Email}")
    public ResponseEntity<AppUser> getuserByEmail(@PathVariable String Email){
        AppUser appUser = appUserRepo.findByEmail(Email);
        return new ResponseEntity<>(appUser,HttpStatus.OK);
    }
}
