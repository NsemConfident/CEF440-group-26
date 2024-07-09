package com.example.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Recently Found");
                    break;
                case 1:
                    tab.setText("Recently Missing");
                    break;
            }
        }).attach();

        FloatingActionButton fab = findViewById(R.id.fab_report_missing);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, UploadImageActivity.class);
            startActivity(intent);
            finish();
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.setting) {
            Toast.makeText(this, "Go to settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.terms) {
            Intent intent = new Intent(getApplicationContext(), TermsAndConditionsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.support) {
            Intent intent = new Intent(getApplicationContext(), CustomerServiceActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.logout) {

            Toast.makeText(this, "wished to logout", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
            finish();

        }
        return true;
    }
}
