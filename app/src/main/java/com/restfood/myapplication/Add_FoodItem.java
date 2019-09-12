package com.restfood.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;

public class Add_FoodItem extends AppCompatActivity {

    public Button done_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__food_item);

        done_button=findViewById(R.id.button_done_food);




    }


    //coped from android studio doc have to change
    //just for desiging
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton_veg:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radioButton_non_veg:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }


}
