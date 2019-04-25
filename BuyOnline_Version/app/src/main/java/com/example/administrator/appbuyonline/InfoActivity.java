package com.example.administrator.appbuyonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class InfoActivity extends AppCompatActivity {
    Toolbar tbThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        AnhXa();
        ActionToolBar();
    }
    private void AnhXa() {
        tbThongTin = findViewById(R.id.tbThongTin);
    }
    private void ActionToolBar() {
        setSupportActionBar(tbThongTin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbThongTin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
