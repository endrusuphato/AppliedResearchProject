package com.example.testone.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testone.Interface.UserDao;
import com.example.testone.Database.DatabaseTable;
import com.example.testone.Database.UserDB;
import com.example.testone.R;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText email, firstName, lastName, password, phoneNumber;

    Button signUp, backToSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.editTxtEmail);
        firstName = findViewById(R.id.editTxtFirstName);
        lastName = findViewById(R.id.editTxtLastName);
        phoneNumber = findViewById(R.id.editTxtPhone);
        password = findViewById(R.id.editTxtPassword);
        signUp = findViewById(R.id.btnRegister);
        backToSignIn = findViewById(R.id.btnBackToLogin);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating user entity
                UserDB userTable = new UserDB();
                userTable.setFirstName(firstName.getText().toString());
                userTable.setLastName(lastName.getText().toString());
                userTable.setPhoneNumber(phoneNumber.getText().toString());
                userTable.setEmail(email.getText().toString().toLowerCase());
                userTable.setPassword(password.getText().toString());

                //creating the database
                DatabaseTable db = Room.databaseBuilder(getApplicationContext(), DatabaseTable.class, "EasyManage.db").build();
                UserDao userDao = db.userDao();

                //create new thread on which commands can be executed
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {

                    // queries for checking if the email is already exists/registered
                    UserDB userEmail = userDao.Register(email.getText().toString());

                    try {
                        if (userEmail == null) {

                            //to make sure all the fields is filled
                            if (validateEmptyInput(userTable)) {

                                //to validate the format of email and firstname lastname input
                                if (validateRegisterInput(email.getText().toString(), firstName.getText().toString(), lastName.getText().toString()) == 1) {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Your email is invalid", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else if (validateRegisterInput(email.getText().toString(), firstName.getText().toString(), lastName.getText().toString()) == 2) {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "Please only input letter for your first and last name", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    try {
                                        //Inserting user data to user table
                                        userDao.signUpUser(userTable);

                                        //display toast on background threat
                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(getApplicationContext(), "User Registered, please login!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    } catch (Exception ex) {
                                        Log.d("EASYMONEYDB", "DB Exception Occurred: " + ex.getMessage());
                                    }
                                }

                            } else {

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User is already registered, please login!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (Exception ex) {
                        Log.d("EASYMONEYDB", "DB Exception Occured" + ex.getMessage());
                    }
                });
            }
        });

        backToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    //empty user input validator
    private Boolean validateEmptyInput(UserDB userTable) {
        if (userTable.getEmail().isEmpty() ||
                userTable.getFirstName().isEmpty() ||
                userTable.getLastName().isEmpty() ||
                userTable.getPhoneNumber().isEmpty() ||
                userTable.getPassword().isEmpty()) {
            return false;
        }
        return true;
    }

    //email validator method
    public static int validateRegisterInput(String email, String firstName, String lastName) {
        //condition for email
        Pattern pattern1 = Pattern.compile("@", Pattern.CASE_INSENSITIVE); // @ exist in email field
        Pattern pattern2 = Pattern.compile(".com", Pattern.CASE_INSENSITIVE); // .com exist in email field
        Matcher matcher1 = pattern1.matcher(email);
        Matcher matcher2 = pattern2.matcher(email);

        boolean condition1 = matcher1.find();
        boolean condition2 = matcher2.find();

        //condition for firstName and lastName
        Pattern pattern3 = Pattern.compile("^[a-zA-Z]*$", Pattern.CASE_INSENSITIVE); //only allow text input
        Matcher matcher3 = pattern3.matcher(firstName);
        Matcher matcher4 = pattern3.matcher(lastName);

        boolean condition3 = matcher3.find();
        boolean condition4 = matcher4.find();

        if (condition1 == false || condition2 == false) {
            return 1;
        } else if (condition3 == false || condition4 == false) {
            return 2;
        } else {
            return 0;
        }

    }

}