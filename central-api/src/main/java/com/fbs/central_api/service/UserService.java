package com.fbs.central_api.service;

import com.fbs.central_api.connectors.DBApiConnector;
import com.fbs.central_api.enums.UserType;
import com.fbs.central_api.exceptions.InvalidCredentials;
import com.fbs.central_api.model.AppUser;
import com.fbs.central_api.utility.AuthUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
This class going to contain all the user related logics
 */
@Slf4j
@Service
public class UserService {

    DBApiConnector dbApiConnector;
    AuthUtility authUtility;

    @Autowired
    public UserService(DBApiConnector dbApiConnector, AuthUtility authUtility){
        this.dbApiConnector = dbApiConnector;
        this.authUtility = authUtility;
    }
    /*
    Work of this function is to get all system admins from our users table.
     */

    public List<AppUser> getAllSystemAdmins(){
        //To get all the system admin from the user table we need to call db api connector
        return dbApiConnector.callGetAllUsersByUserType(UserType.SYSTEM_ADMIN.toString());
    }

    public AppUser updateUserDetails(AppUser appUser){
        // need to call db api connector for updating the details of AirlineAdmin
        return dbApiConnector.callupdateUserEndpoint(appUser);
    }

    public AppUser getUserByMail(String email){
        //This method will bring User details from db by email
        return dbApiConnector.callGetUserByEmailEndpoint(email);
    }

    /*
    This function should be able to validate the credentials of the user
    1. Get user record from Database by email
    2. Compare the password
    3. Correct return true else return false
     */
    public String isValidCredentials(String email, String password){

        //2. Validate user - null ? If yes throw exception else Move forward
        //h.w
        try{
            //1. Need method to get user details from details by mail
            AppUser user = this.getUserByMail(email);
            // 2. Validate user - if null, throw exception
            if (user == null) {
                throw new InvalidCredentials("Email or Password is wrong");
            }

            // 3. Validate password
            // If Valid return Token else return null value.
            if(user.getPassword().equals(password)){
                return authUtility.generateToken(user.getEmail(),user.getPassword(),user.getUserType());
            }else {
                throw new InvalidCredentials("Email or password is wrong");
            }
//            return user.getPassword().equals(password);

        } catch (InvalidCredentials e) {
            log.warn("Authentication failed: " + e.getMessage());
            throw e;  // rethrow custom exception
        } catch (Exception e) {
            log.error("Unexpected error during login", e);
            throw new InvalidCredentials("Login failed due to system error");
        }


    }

    public boolean validateToken(String token){
        String payload = authUtility.decryptJwtToken(token);
        String [] details = payload.split(":");
        String email = details[0];
        String password = details[1];
        // I want to validate email & password is it belonging to correct user or not
        // Auth Utility is going to call UserService to validate email and password belongs to correct user or not

//        try{
//            isValidCredentials(email, password);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
        //Using GetUserByEmail and validate the password
        try{
            AppUser user = this.getUserByMail(email);
            if (user == null) {
                log.warn("Token "+ token +" is not valid");
                return false;
            }
            if(user.getPassword().equals(password)){
                return true;
            }
        } catch (Exception e) {
            log.warn("Invalid Token: "+ token);
            log.error("Error Occurred: "+ e.getMessage());

        }
        return false;
    }
}
