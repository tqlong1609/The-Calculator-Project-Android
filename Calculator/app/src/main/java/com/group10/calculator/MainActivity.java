package com.group10.calculator;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.Gravity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * This is function create ActionBar in MainActivity
     */
    private void CreatedActionBar() {
        mDrawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreatedActionBar();
        FirstAddCalculator();
    }
    /**
     * This is function create fragment caculator on first
     */
    private void FirstAddCalculator(){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        CalculatorFragment fragCalculator = new CalculatorFragment();
        fragTransaction.add(R.id.framelayout, fragCalculator);
        fragTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * This is function handle fragments
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        switch (menuItem.getItemId()){
            case R.id.txt_Calculator:
                CalculatorFragment fragCalculator = new CalculatorFragment();
                fragTransaction.replace(R.id.framelayout, fragCalculator);
                break;
            case R.id.txt_Money:
                ConvertMoneyFragment fragConvertMoney= new ConvertMoneyFragment();
                fragTransaction.replace(R.id.framelayout, fragConvertMoney);
                break;
            case R.id.txt_Convert:
                ConverterUnitFragment fragConverterUnit = new ConverterUnitFragment();
                fragTransaction.replace(R.id.framelayout, fragConverterUnit);
                break;
            case R.id.txt_History:
                HistoryFragment view_history = new HistoryFragment();
                fragTransaction.replace(R.id.framelayout,view_history);
                break;
        }
        fragTransaction.commit();
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }

}
