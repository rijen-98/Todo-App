package com.example.todoapp.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todoapp.model.User;
import com.example.todoapp.Data.UserDao;


@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDao getUserDao();

}
