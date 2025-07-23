package com.fbs.central_api.service;

import com.fbs.central_api.connectors.DBApiConnector;
import com.fbs.central_api.enums.UserType;
import com.fbs.central_api.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
This class going to contain all the user related logics
 */
@Service
public class UserService {

    DBApiConnector dbApiConnector;

    @Autowired
    public UserService(DBApiConnector dbApiConnector){
        this.dbApiConnector = dbApiConnector;
    }
    /*
    Work of this function is to get all system admins from our users table.
     */

    public List<AppUser> getAllSystemAdmins(){
        //To get all the system admin from the user table we need to call db api connector
        return dbApiConnector.callGetAllUsersByUserType(UserType.SYSTEM_ADMIN.toString());
    }

}
