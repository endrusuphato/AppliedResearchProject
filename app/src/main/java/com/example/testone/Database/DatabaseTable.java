package com.example.testone.Database;

import androidx.room.Database;

import com.example.testone.Interface.UserDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.testone.Interface.UserDao;

@Database(entities = {UserDB.class}, version = 1)
public abstract class DatabaseTable extends RoomDatabase{
    public abstract UserDao userDao();
}
