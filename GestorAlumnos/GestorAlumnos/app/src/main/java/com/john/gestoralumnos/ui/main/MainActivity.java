package com.john.gestoralumnos.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.john.gestoralumnos.R;
import com.john.gestoralumnos.ui.main.SectionsPagerAdapter;

/**
 * <h1>Gestor alumnos</h1>
 * <h2>Main Activity</h2>
 * <p>Lugar donde empezamos la programacion.</p>
 * @author JuanGregorioVazquezRodriguez JohnGVazquez@gmail.com
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    Context context;
    ViewPager viewPager;
    SectionsPagerAdapter sectionsPagerAdapter;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        context = this;

    }

    /**
     *<p>Refrescar la pagina con los nuevos datos</p>
     */
    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}