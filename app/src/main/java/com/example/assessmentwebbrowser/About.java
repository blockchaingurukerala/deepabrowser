package com.example.assessmentwebbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class About extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
    public void clickBack(View v){
        Toast.makeText(this, "Going Back", Toast.LENGTH_LONG).show();
        Intent mainactivity= new Intent(this,MainActivity.class);
        startActivity(mainactivity);
        finish();
    }

}