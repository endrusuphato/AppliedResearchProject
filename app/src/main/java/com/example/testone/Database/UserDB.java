package com.example.testone.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserDB")
public class UserDB {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    Integer userID;

    @NonNull
    @ColumnInfo(name = "FirstName")
    String firstName;

    @NonNull
    @ColumnInfo(name = "LastName")
    String lastName;

    @NonNull
    @ColumnInfo(name = "Email")
    String email;

    @NonNull
    @ColumnInfo(name = "PhoneNumber")
    String phoneNumber;

    @NonNull
    @ColumnInfo(name = "Password")
    String password;

    public UserDB(){

    }

    public UserDB(@NonNull Integer userID, @NonNull String firstName, @NonNull String lastName, @NonNull String phoneNumber, @NonNull String email, @NonNull String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    @NonNull
    public Integer getUserId() {
        return userID;
    }

    public void setUserId(@NonNull Integer userID) {
        this.userID = userID;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}




