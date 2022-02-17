package com.example.testone.Interface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.testone.Database.UserDB;


@Dao
public interface UserDao {

    // insert user to database
    @Insert()
    void signUpUser(UserDB userEntity);

    // get all data from user table with current email input
    @Query("SELECT * from UserDB where email=(:email)")
    UserDB ValidEmail(String email);

    // get all data from user table with current password input
    @Query("SELECT * from UserDB where password=(:password)")
    UserDB ValidPassw(String password);

    @Query(("SELECT * from UserDB where email = (:email)"))
    UserDB Register(String email);

    // get userId from user table with current logged in email
    @Query("SELECT userID FROM UserDB WHERE email = (:UserEmail)")
    int getUserIdByEmail(String UserEmail);

    // get first name from user table with current logged in userId
    @Query("SELECT firstName FROM UserDB WHERE userID = :userID")
    String getFirstName(int userID);

    // get last name name from user table with current logged in userId
    @Query("SELECT lastName FROM UserDB WHERE userID = :userID")
    String getLastName(int userID);

    // get phone number from user table with current logged in userId
    @Query("SELECT phoneNumber FROM UserDB WHERE userID = :userID")
    String getPhoneNumber(int userID);

    // get email from user table with current logged in userId
    @Query("SELECT email FROM UserDB WHERE userID = :userID")
    String getEmail(int userID);
}