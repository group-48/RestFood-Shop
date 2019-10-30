package com.restfood.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EditFood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        String str= (String) getIntent().getSerializableExtra("Demo");
        //FoodData obj= (FoodData) getIntent().getSerializableExtra("Class");
        TextView imgview=findViewById(R.id.textView2);
        imgview.setText(str);
    }
}
