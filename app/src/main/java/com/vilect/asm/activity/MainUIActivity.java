package com.vilect.asm.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.facebook.CallbackManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.vilect.asm.R;
import com.vilect.asm.fragment.CourseFragment;
import com.vilect.asm.fragment.FacebookFragment;
import com.vilect.asm.fragment.GoogleMapFragment;
import com.vilect.asm.fragment.MyCoursesFragment;
import com.vilect.asm.service.ChangeDataService;

public class MainUIActivity extends AppCompatActivity {

    private BottomNavigationView bnvMain;
    private FragmentManager fm = getSupportFragmentManager();
    public DrawerLayout drawerLayout;
    private NavigationView nav;
//    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);

//        callbackManager = CallbackManager.Factory.create();

        mapping();
        setBnvMainOnItemClick();
        setNav();

        MyCoursesFragment myCoursesFragment = new MyCoursesFragment(MainUIActivity.this);
        fm.beginTransaction().replace(R.id.frameMain, myCoursesFragment).commit();

    }

    private void mapping()
    {
        bnvMain = findViewById(R.id.bnvMain);
        nav = findViewById(R.id.navMainUi);
        drawerLayout = findViewById(R.id.drawerMainUi);
    }

    private void setNav()
    {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.itHome:{
                        drawerLayout.closeDrawers();
                        break;
                    }

                    case R.id.itNews:{
                        Intent intent = new Intent(MainUIActivity.this, NewsActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    }

                    case R.id.itPersonal:{
                        Intent intent = new Intent(MainUIActivity.this, PersonalActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;
                    }

                    default: break;
                }

                return false;
            }
        });
    }

    private void setBnvMainOnItemClick()
    {
        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fm = getSupportFragmentManager();

                switch (item.getItemId())
                {
                    case R.id.itCourse: {
                        CourseFragment courseFragment = new CourseFragment(MainUIActivity.this);
                        fm.beginTransaction().replace(R.id.frameMain, courseFragment).commit();
//                        Intent intent = new Intent(MainUIActivity.this, ChangeDataService.class);
//                        startService(intent);
                        break;
                    }

                    case R.id.itMyCourse: {

                        MyCoursesFragment myCoursesFragment = new MyCoursesFragment(MainUIActivity.this);
                        fm.beginTransaction().replace(R.id.frameMain, myCoursesFragment).commit();
                        break;
                    }

                    case R.id.itMap: {
                        GoogleMapFragment googleMapFragment = new GoogleMapFragment(MainUIActivity.this);
                        fm.beginTransaction().replace(R.id.frameMain, googleMapFragment).commit();
                        break;
                    }

                    case R.id.itSocial:{
                        FacebookFragment facebookFragment = new FacebookFragment(MainUIActivity.this);
                        fm.beginTransaction().replace(R.id.frameMain, facebookFragment).commit();
                        break;
                    }

                    default:break;
                }
                return true;
            }
        });
    }

    //onActivityResult Facebook logiun

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
}
