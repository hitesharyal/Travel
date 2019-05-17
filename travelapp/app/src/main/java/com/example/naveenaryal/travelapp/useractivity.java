package com.example.naveenaryal.travelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class useractivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onStart() {
        super.onStart();

        ArrayList<TourPkg> tour= new ArrayList<TourPkg>();
        tour.add(new TourPkg("Roopkund","7D/6N","7000",R.drawable.img1));
        tour.add(new TourPkg("Roopkund","7D/6N","7000",R.drawable.img1));
        tour.add(new TourPkg("Roopkund","7D/6N","7000",R.drawable.img1));
        tour.add(new TourPkg("Roopkund","7D/6N","7000",R.drawable.img1));
        tour.add(new TourPkg("Roopkund","7D/6N","7000",R.drawable.img1));
        tour.add(new TourPkg("Roopkund","7D/6N","7000",R.drawable.img1));



    TourAdapter adapter= new TourAdapter(this, tour);
        ListView listView= (ListView) findViewById(R.id.mylist);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useractivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.useractivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profiles) {
            Intent i =new Intent(this, profileActivity.class);
                 startActivity(i);

            // Handle the camera action
        } else if (id == R.id.mybookings) {

        } else if (id == R.id.aboutus) {

        } else if (id == R.id.cancelationp) {

        } else if (id == R.id.drawerlogout) {

            FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
            FirebaseUser user= firebaseAuth.getCurrentUser();
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,MainActivity.class));

        } else if (id == R.id.mailus) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"+"travelforyou@gmail.com")); // only email apps should handle this

            intent.putExtra(Intent.EXTRA_TEXT,"Write your query here :\n");
            intent.putExtra(Intent.EXTRA_SUBJECT, "My Query");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
