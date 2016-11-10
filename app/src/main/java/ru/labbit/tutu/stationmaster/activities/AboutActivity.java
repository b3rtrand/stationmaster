package ru.labbit.tutu.stationmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.labbit.tutu.stationmaster.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
    }

    public void backBtnClick(View view) {
        Intent intent = new Intent(this, StationsActivity.class);
        startActivity(intent);
    }
}
