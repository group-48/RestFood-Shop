package com.restfood.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class DetailInventory extends AppCompatActivity {
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_inventory);

        Intent inta=getIntent();
        docId=inta.getStringExtra("DocId");
    }
}
