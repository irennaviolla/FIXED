package com.tugasmoop.projectsoftwareengineering.UI.service;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.tugasmoop.projectsoftwareengineering.R;

public class ServicerActivity extends AppCompatActivity {

    ImageView orderDot, profileDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicer);

        orderDot = findViewById(R.id.orderDot);
        profileDot = findViewById(R.id.profileDot);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.servicerContainer, new ServicerProfileFragment()).commit();

    }

    public void toOrder(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.servicerContainer, new OrderFragment()).commit();
        orderDot.setVisibility(View.VISIBLE);
        profileDot.setVisibility(View.INVISIBLE);
    }

    public void toProfile(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.servicerContainer, new ServicerProfileFragment()).commit();
        orderDot.setVisibility(View.INVISIBLE);
        profileDot.setVisibility(View.VISIBLE);
    }
}