package org.bom.india_hackaton.activities;

import android.content.ComponentName;
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
import android.widget.Toast;

import org.bom.india_hackaton.R;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.cards.actions.BaseSupplementalAction;
import it.gmariotti.cardslib.library.cards.actions.IconSupplementalAction;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set supplemental actions as icon
        ArrayList<BaseSupplementalAction> actions = new ArrayList<BaseSupplementalAction>();

        IconSupplementalAction t1 = new IconSupplementalAction(this, R.id.apply);
        t1.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(MainActivity.this," Click on Text APPLY ",Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t1);

        IconSupplementalAction t2 = new IconSupplementalAction(this, R.id.learn);
        t2.setOnActionClickListener(new BaseSupplementalAction.OnActionClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(MainActivity.this," Click on Text LEARN ",Toast.LENGTH_SHORT).show();
            }
        });
        actions.add(t2);

        MaterialLargeImageCard card =
                MaterialLargeImageCard.with(this)
                        .setTextOverImage("Digilocker")
                        .setTitle("Register for this service to keep your documents safe")
                        .setSubTitle("14517 people registered")
                        .useDrawableId(R.drawable.pmjdylogo)
                        .setupSupplementalActions(R.layout.supplemental_actions, actions)
                        .build();

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
        getMenuInflater().inflate(R.menu.main, menu);
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

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_banking) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.putExtra("activity","banking");
            startActivity(i);
        } else if (id == R.id.nav_account) {
            Intent intentToLaunch = new Intent(this, AccountInfoActivity.class);
            startActivity(intentToLaunch);
        } else if (id == R.id.nav_pension) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            i.putExtra("activity","pension");
            startActivity(i);
        } else if (id == R.id.nav_share) {
            Uri uri = Uri.parse("http://www.github.com/brainbreaker"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.nav_aadhar) {
            startActivity(new Intent("com.barclays.indiahackathonsample.update_aadhar"));
        } else if (id == R.id.nav_atm) {
            startActivity(new Intent("com.barclays.indiahackathonsample.get_atm"));
        } else if (id == R.id.nav_bankList) {
            startActivity(new Intent("com.barclays.indiahackathonsample.get_banks"));
        } else if (id == R.id.nav_support) {
            startActivity(getPackageManager().getLaunchIntentForPackage("com.ibm.mobileappbuilder.pmjdyapp"));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
