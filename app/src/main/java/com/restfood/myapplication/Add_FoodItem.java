package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Add_FoodItem extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //creating variables
    //this is for food
    private TextInputEditText food_name_edit_text;
    private TextInputLayout food_name_layout;

    private TextInputEditText food_price_edit_text;
    private TextInputLayout food_price_layout;
    private int price;      //this is to use for database

    private TextInputEditText min_duration_text;
    private TextInputLayout min_duration_layout;

    private TextInputEditText max_duration_text;
    //private TextInputLayout max_duration_layout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__food_item);

        //assigning xml component to that
        food_name_edit_text=findViewById(R.id.food_name_text);
        food_name_layout=findViewById(R.id.food_name_layout);

        food_price_edit_text=findViewById(R.id.food_price_text);
        food_price_layout=findViewById(R.id.food_price_layout);

        min_duration_text=findViewById(R.id.min_pre_time_text);
        min_duration_layout=findViewById(R.id.min_pre_time_layout);
        max_duration_text=findViewById(R.id.max_pre_time_text);
        //max_duration_layout=findViewById(R.id.max_pre_time_layout);




        //this call when food field change
        food_name_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //not doing now
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFoodName();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //not doing now
            }
        });
    }

    //this is validation for both
    //max and min
    //if max or min one can be null
    //didn't check for  becuse for ready madefood
    private boolean checkDuration()
    {
        String min_time=min_duration_text.getText().toString();
        String max_time=max_duration_text.getText().toString();

        if(!min_time.isEmpty() | !max_time.isEmpty())
        {
            min_duration_layout.setError(null);
            return true;
        }
        else
        {
            min_duration_layout.setError("Enter Duration");
            return false;
        }

    }

    //validation checking for price
    private boolean checkPrice()
    {
        String food_price=food_price_edit_text.getText().toString();

        if(food_price.length()==0)
        {
            food_price_layout.setError("Price is Empty");
            return false;
        }
        else
        {

            try
            {
                price=Integer.parseInt(food_price);
                if(price>0)
                {
                    food_price_layout.setError(null);
                    return true;

                }
                else
                {
                    food_price_layout.setError("Food Price is 0");
                    return false;

                }
            }
            catch (NumberFormatException e)
            {
                food_price_layout.setError("Dandanaka done");
                return false;
            }
        }
    }


    //validation checking for food name
    private boolean checkFoodName()
    {
        String food_name=food_name_edit_text.getText().toString().trim();

        if(food_name.length()==0)
        {
            food_name_layout.setError("Length is 0");
            return false;
        }
        else
        {
            if(food_name.length()<4)
            {
                food_name_layout.setError("Food Name is too short");
            }
            else
            {
                food_name_layout.setError(null);
            }

            return true;
        }
    }




    public void onSubmit(View v)
    {

        if(checkFoodName() & checkPrice() & checkDuration())
        {
            //Toast.makeText(getApplicationContext(),"No error Done", Toast.LENGTH_SHORT).show();

            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("first", "Ada");
            user.put("last", "Lovelace");
            user.put("born", 1815);

            // Add a new document with a generated ID
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(getApplicationContext(),"DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w(TAG, "Error adding document", e);
                            Toast.makeText(getApplicationContext(),"Erroe adding doc", Toast.LENGTH_SHORT).show();
                        }
                    });

        }

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
