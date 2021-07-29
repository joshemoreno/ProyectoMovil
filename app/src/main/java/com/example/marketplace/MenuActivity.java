package com.example.marketplace;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.marketplace.databinding.ActivityMenuBinding;
import com.example.marketplace.ui.gallery.GalleryFragment;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    private Activity mySelf;

    private NavigationView navView;
    private DrawerLayout draView;
    private TextView tvName;
    private View hView;
    private int count=0;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySelf = this;


        draView = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        binding.appBarMenu.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act_goMain = new Intent(mySelf, activity_add_product.class);
                startActivity(act_goMain);
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.navName);
        textView.setText("Hola");
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean (@NonNull @NotNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.nav_home:
//                        Toast.makeText(MenuActivity.this, "home", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_favorites:
//                        GalleryFragment fragment2=new GalleryFragment();
//                        FragmentManager fragmentManager=getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.nav_host_fragment_content_menu,fragment2,"tag");
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//
//                        Toast.makeText(MenuActivity.this, "favorites", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.nav_shopping:
//                        Toast.makeText(MenuActivity.this, "shopping", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.signOut:
//                        AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
//                        builder.setTitle(R.string.logOut);
//                        builder.setMessage(R.string.sure);
//                        builder.setIcon(R.drawable.warning);
//                        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                                FirebaseAuth.getInstance().signOut();
//                                Intent act_goMain = new Intent(mySelf, MainActivity.class);
//                                startActivity(act_goMain);
//                            }
//                        });
//                        builder.setNegativeButton(R.string.no,null);
//
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                        break;
//                }
//                return true;
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_favorites: {
                GalleryFragment fragment2=new GalleryFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_menu,fragment2,"tag");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(count == 0){
            Toast.makeText(MenuActivity.this, R.string.backAlert, Toast.LENGTH_SHORT).show();
            count++;
        }else{
            super.onBackPressed();
        }
        new CountDownTimer(2000,1000){
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                count=0;
            }
        }.start();

    }


}