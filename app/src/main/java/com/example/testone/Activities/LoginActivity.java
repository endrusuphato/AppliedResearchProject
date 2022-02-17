package com.example.testone.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.testone.Database.DatabaseTable;
import com.example.testone.Database.UserDB;
import com.example.testone.Interface.UserDao;
import com.example.testone.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail, userPW;
    Button login, createAccount;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.editTxtLoginEmail);
        userPW = findViewById(R.id.editTxtLoginPassword);
        login = findViewById(R.id.btnLogin);
        createAccount = findViewById(R.id.btnCreateAccount);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().toLowerCase();
                String password = userPW.getText().toString();

                //check fields
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    //create DB
                    DatabaseTable db = Room.databaseBuilder(getApplicationContext(), DatabaseTable.class, "EasyManage.db").build();
                    UserDao userDao = db.userDao();

                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(()->{
                        UserDB validEmail = userDao.ValidEmail(email); //check valid email
                        UserDB validPassword = userDao.ValidPassw(password); //for password

                        try{
                            if(validEmail == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Wrong Email Address and/or invalid", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            else if (validEmail != null && validPassword == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                userID = userDao.getUserIdByEmail(email); //retrieve userid

                                SharedPreferences preferences = getSharedPreferences("MYPREF", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("USERID", userID);
                                editor.apply();

                                //startActivity(new Intent()); HELP!!!
                            }
                        } catch (Exception ex){
                            Log.d("check", "DB Exception Occured: " + ex.getMessage());
                        }
                    });
                }
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //startActivity(new Intent()); HELP!!!
            }
        });
    }
}
