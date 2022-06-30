package com.tugasmoop.projectsoftwareengineering.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.UI.favorite.FavoriteFragment;
import com.tugasmoop.projectsoftwareengineering.UI.history.HistoryFragment;
import com.tugasmoop.projectsoftwareengineering.UI.main.MainFragment;
import com.tugasmoop.projectsoftwareengineering.UI.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity{

    ImageView homeDot, favoriteDot, historyDot, profileDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeDot = findViewById(R.id.homeDot);
        favoriteDot = findViewById(R.id.favoriteDot);
        historyDot = findViewById(R.id.historyDot);
        profileDot = findViewById(R.id.profileDot);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerFrameMain, new MainFragment()).commit();

    }

    public void toHomePage(View view) {
        homeDot.setVisibility(View.VISIBLE);
        favoriteDot.setVisibility(View.INVISIBLE);
        historyDot.setVisibility(View.INVISIBLE);
        profileDot.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerFrameMain, new MainFragment()).commit();
    }

    public void toFavoritePage(View view) {
        homeDot.setVisibility(View.INVISIBLE);
        favoriteDot.setVisibility(View.VISIBLE);
        historyDot.setVisibility(View.INVISIBLE);
        profileDot.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerFrameMain, new FavoriteFragment()).commit();
    }

    public void toHistoryPage(View view) {
        homeDot.setVisibility(View.INVISIBLE);
        favoriteDot.setVisibility(View.INVISIBLE);
        historyDot.setVisibility(View.VISIBLE);
        profileDot.setVisibility(View.INVISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerFrameMain, new HistoryFragment()).commit();
    }

    public void toProfilePage(View view) {
        homeDot.setVisibility(View.INVISIBLE);
        favoriteDot.setVisibility(View.INVISIBLE);
        historyDot.setVisibility(View.INVISIBLE);
        profileDot.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.containerFrameMain, new ProfileFragment()).commit();
    }
}