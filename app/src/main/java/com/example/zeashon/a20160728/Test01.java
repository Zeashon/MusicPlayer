package com.example.zeashon.a20160728;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Test01 extends AppCompatActivity {
    Button btn02;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test01);
        intent = this.getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            Toast.makeText(this,"hello "+name, Toast.LENGTH_LONG).show();
        }

    }
}
