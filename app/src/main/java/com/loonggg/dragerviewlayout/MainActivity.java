package com.loonggg.dragerviewlayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.loonggg.lib.DragerViewLayout;

public class MainActivity extends AppCompatActivity {
    TextView btn;
    private DragerViewLayout drager_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (TextView) findViewById(R.id.btn);
        drager_layout = (DragerViewLayout) findViewById(R.id.drager_layout);
        drager_layout.isDrager(true);
        drager_layout.setFilePathAndName(Environment.getExternalStorageDirectory().getPath() + "/loonggg", "weds");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TwoActivity.class));
            }
        });
    }
}
