package com.example.videomanager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

public class MenuActivity extends Activity implements OnClickListener {

    ImageButton menuTrimBtn;
    ImageButton menuMergeBtn;
    ImageButton menuFiltersBtn;
    ImageButton menuMyAccountBtn;
    ImageButton menuGetFrameBtn;
    ImageButton menuHelpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuFiltersBtn = (ImageButton)findViewById(R.id.menuFiltersBtn);
        menuFiltersBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuFiltersBtn:
                Intent intent = new Intent(this, FiltersActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
