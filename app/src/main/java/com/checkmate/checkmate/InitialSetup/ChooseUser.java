package com.checkmate.checkmate.InitialSetup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.checkmate.checkmate.MainActivity;
import com.checkmate.checkmate.R;

public class ChooseUser extends AppCompatActivity {
    Spinner mSpinner;
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        mSpinner = (Spinner)findViewById(R.id.spinner);
        mLoginButton = (Button)findViewById(R.id.login);


    }

    public void chooseUser(View view){
        Intent userIntent = new Intent(this, MainActivity.class);
        userIntent.putExtra("USER", String.valueOf(mSpinner.getSelectedItem()));
        startActivity(userIntent);
    }
}
