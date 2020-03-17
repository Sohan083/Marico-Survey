package com.example.maricosurvey;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class form extends AppCompatActivity {

    String[] sDropList = new String[] {"Male", "Female"};
    String[] bcsDropList = new String[] {"Yes", "No"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Spinner sdropdown = findViewById(R.id.sdropdown);
        Spinner bcsdropdown = findViewById(R.id.bcsdropdown);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sDropList);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bcsDropList);
        sdropdown.setAdapter(adapter1);
        bcsdropdown.setAdapter(adapter2);
    }

}
