package com.vehiclerentalservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vehiclerentalservices.Jwt.JwtUtils;
import com.vehiclerentalservices.model.User;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnbar, ownerBnBar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    String owner = "OWNER", customer = "CUSTOMER", admin = "ADMIN";
    TextView l;
    View fragment;
    TextView forAdmin;
    int userId;
    ImageView notification;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnbar = findViewById(R.id.bn_bar);
        ownerBnBar = findViewById(R.id.owner_bn_bar);
        drawerLayout = findViewById(R.id.navDrawer);
        navigationView = findViewById(R.id.nanView);
        toolbar = findViewById(R.id.toolbar);
        notification = findViewById(R.id.notification);
        l = findViewById(R.id.noOfNotification);
        forAdmin = findViewById(R.id.ForAdmin);
        fragment = findViewById(R.id.container);
        View view = navigationView.getHeaderView(0);
       TextView UserName = view.findViewById(R.id.customerName);
        TextView UserEmail = view.findViewById(R.id.customerEmail);
        TextView UserStatus = view.findViewById(R.id.customerStatus);
        ImageView UserPhoto = view.findViewById(R.id.customerPhoto);
        requestQueue = Volley.newRequestQueue(this);

        String jwtToken = SharedPreferencesUtil.getToken(MainActivity.this);

        String username = JwtUtils.extractFromToken(jwtToken);

        String urls = "http://192.168.43.231:8080/api/vehicleRentalServices/user/email/" + username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urls,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        User[] users = gson.fromJson(response, User[].class);
                        for (User user : users) {
                            userId = user.getId();
                            int sts = user.getStatus();
                            String namess = user.getName();
                            String emailsss = user.getEmail();
                            String role = user.getRole();
                            UserName.setText(namess);
                            UserEmail.setText(emailsss);
                            if (sts == 0){
                                UserStatus.setText("Unverified");
                            }
                            if (sts == 1){
                                UserStatus.setText("Unverified");
                            }
                            if (sts == 2){
                                UserStatus.setText("Verified");
                            }
                            SharedPreferences pref = getSharedPreferences("UserId", MODE_PRIVATE);
                            SharedPreferences.Editor edits = pref.edit();
                            edits.putInt("userId", userId);
                            edits.apply();
                            String roles = null;
                            if (role.equals("OWNER")){
                                SharedPreferences preferences = getSharedPreferences("UserRole", MODE_PRIVATE);
                                 roles = preferences.getString("roles", "OWNER");
                            }
                            if (role.equals("CUSTOMER")){
                                roles = "CUSTOMER";
                            }
                            if (role.equals("ADMIN")){
                                roles = "ADMIN";
                            }
                            ResponsiveDdesign(roles);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SharedPreferences preferences = getSharedPreferences("UserRole", MODE_PRIVATE);
                String role = preferences.getString("roles", "CUSTOMER");
                ResponsiveDdesign(role);
                Toast.makeText(MainActivity.this, "Network error !!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

        setSupportActionBar(toolbar);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        forAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ApproveUserByAdminActivity.class);
                startActivity(intent);
            }
        });

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.profile) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);

                } else if (id == R.id.applyKyc) {
                    Intent intent = new Intent(MainActivity.this, KycActivity.class);
                    startActivity(intent);

                }else if (id == R.id.isBook) {
                    Intent intent = new Intent(MainActivity.this, YourBookActivity.class);
                    startActivity(intent);

                } else if (id == R.id.EarnWithUs) {
                    Intent intent = new Intent(MainActivity.this, EarnWithUsActivity.class);
                    startActivity(intent);

                } else if (id == R.id.switchProfile) {
                    SharedPreferences pref = getSharedPreferences("UserRole", MODE_PRIVATE);
                    String role = pref.getString("roles", "CUSTOMER");
                    Intent intent;
                    if (role.equals("OWNER")){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("roles","CUSTOMER");
                        editor.apply();
                       intent = new Intent(MainActivity.this, MainActivity.class);
                       startActivity(intent);
                       finish();
                    }
                    if (role.equals("CUSTOMER")){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("roles","OWNER");
                        editor.apply();
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else if (id == R.id.changePass) {
                    Intent intent = new Intent(MainActivity.this, ForgetActivity.class);
                    startActivity(intent);

                } else if (id == R.id.support) {
                    Intent intent = new Intent(MainActivity.this, SupportHelpActivity.class);
                    startActivity(intent);

                }else if (id == R.id.privacy) {
                    Intent intent = new Intent(MainActivity.this, PrivacyPolicyActivity.class);
                    startActivity(intent);

                } else if (id == R.id.aboutUs) {
                    Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                    startActivity(intent);

                } else if (id == R.id.terms) {
                    Intent intent = new Intent(MainActivity.this, TermsActivity.class);
                    startActivity(intent);
                } else {
                    SharedPreferences pref = getSharedPreferences("LoginInfo", MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putBoolean("Logs", false);
                    edit.apply();

                    Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

        bnbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.search) {
                    loadFrag(new SearchFragment(), false);
                } else if (id == R.id.home) {
                    loadFrag(new HomeFragment(), true);
                } else if (id == R.id.favorite) {
                    loadFrag(new FavoriteFragment(), false);
                } else {
                    loadFrag(new HistoryFragment(), false);
                }
                return true;
            }
        });

        bnbar.setSelectedItemId(R.id.home);

        ownerBnBar.setSelectedItemId(R.id.homeO);

        ownerBnBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.homeO) {
                    loadFragForOwner(new HomeFragment(), true);
                } else if (id == R.id.approveBooking) {
                    loadFragForOwner(new BookingRequestFragment(), false);
                } else if (id == R.id.addVehicle) {
                    loadFragForOwner(new AddVehicleFragment(), false);
                } else {
                    loadFragForOwner(new HistoryFragment(), false);
                }
                return true;
            }
        });

    }

    private void ResponsiveDdesign(String role) {
          Menu dMenu = navigationView.getMenu();
        SharedPreferences preferences = getSharedPreferences("doubleRole", MODE_PRIVATE);
        Boolean isRole = preferences.getBoolean("IsDouble",false);
        if (role.equals(owner)) {
            dMenu.findItem(R.id.profile).setVisible(true);
            dMenu.findItem(R.id.applyKyc).setVisible(false);
            dMenu.findItem(R.id.changePass).setVisible(true);
            dMenu.findItem(R.id.EarnWithUs).setVisible(false);
            dMenu.findItem(R.id.isBook).setVisible(false);
            dMenu.findItem(R.id.support).setVisible(true);
            dMenu.findItem(R.id.privacy).setVisible(true);
            dMenu.findItem(R.id.terms).setVisible(true);
            dMenu.findItem(R.id.aboutUs).setVisible(true);
            dMenu.findItem(R.id.logout).setVisible(true);
            if (isRole){
                dMenu.findItem(R.id.switchProfile).setVisible(true);
            }else {
                dMenu.findItem(R.id.switchProfile).setVisible(false);
            }

            forAdmin.setVisibility(View.GONE);

            ownerBnBar.setVisibility(View.VISIBLE);
            bnbar.setVisibility(View.GONE);
        }
        if (role.equals(customer)) {
            dMenu.findItem(R.id.profile).setVisible(true);
            dMenu.findItem(R.id.applyKyc).setVisible(true);
            dMenu.findItem(R.id.changePass).setVisible(true);
            dMenu.findItem(R.id.support).setVisible(true);
            dMenu.findItem(R.id.isBook).setVisible(true);
            dMenu.findItem(R.id.privacy).setVisible(true);
            dMenu.findItem(R.id.terms).setVisible(true);
            dMenu.findItem(R.id.aboutUs).setVisible(true);
            dMenu.findItem(R.id.logout).setVisible(true);
            if (isRole){
                dMenu.findItem(R.id.EarnWithUs).setVisible(false);
                dMenu.findItem(R.id.switchProfile).setVisible(true);
            }else {
                dMenu.findItem(R.id.EarnWithUs).setVisible(true);
                dMenu.findItem(R.id.switchProfile).setVisible(false);
            }

            forAdmin.setVisibility(View.GONE);

            ownerBnBar.setVisibility(View.GONE);
            bnbar.setVisibility(View.VISIBLE);
        }
        if (role.equals(admin)) {
            dMenu.findItem(R.id.profile).setVisible(true);
            dMenu.findItem(R.id.applyKyc).setVisible(false);
            dMenu.findItem(R.id.changePass).setVisible(true);
            dMenu.findItem(R.id.EarnWithUs).setVisible(false);
            dMenu.findItem(R.id.isBook).setVisible(false);
            dMenu.findItem(R.id.switchProfile).setVisible(false);
            dMenu.findItem(R.id.support).setVisible(true);
            dMenu.findItem(R.id.privacy).setVisible(true);
            dMenu.findItem(R.id.terms).setVisible(true);
            dMenu.findItem(R.id.aboutUs).setVisible(true);
            dMenu.findItem(R.id.logout).setVisible(true);

            fragment.setVisibility(View.GONE);

            forAdmin.setVisibility(View.VISIBLE);

            ownerBnBar.setVisibility(View.GONE);
            bnbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences preferences = getSharedPreferences("UserRole", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void loadFrag(Fragment fragment, boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag) {
            ft.replace(R.id.container, fragment);

        } else {
            ft.replace(R.id.container, fragment);
        }
        ft.commit();
    }

    public void loadFragForOwner(Fragment fragment, boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag) {
            ft.replace(R.id.container, fragment);
        } else {
            ft.replace(R.id.container, fragment);
        }
        ft.commit();
    }

}