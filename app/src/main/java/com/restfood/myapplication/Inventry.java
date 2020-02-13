package com.restfood.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.restfood.myapplication.OrderBottomSheetDialog;

public class Inventry extends AppCompatActivity {
    FloatingActionButton buttonAdd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventry);

        buttonAdd=findViewById(R.id.floatingbutton_invent);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderBottomSheetDialog bottomsheet=new OrderBottomSheetDialog();
                bottomsheet.show(getSupportFragmentManager(),"Hi this is ");
            }
        });
    }

    private void add()
    {
        InventryBottomSheetDialog bottomsheet=new InventryBottomSheetDialog();
        bottomsheet.show(getSupportFragmentManager(),"Hi this is ");
    }
}
