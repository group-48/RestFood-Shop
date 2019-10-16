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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class Add_FoodItem extends AppCompatActivity {
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth fbauth= FirebaseAuth.getInstance();


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

    private TextInputEditText cat_text;





    ///this data for firestore
    String shop_doc_id;
    String uid;     //this is given by firestore & shop_id





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__food_item);

        getShopData();

        //assigning xml component to that
        food_name_edit_text=findViewById(R.id.food_name_text);
        food_name_layout=findViewById(R.id.food_name_layout);

        food_price_edit_text=findViewById(R.id.food_price_text);
        food_price_layout=findViewById(R.id.food_price_layout);

        min_duration_text=findViewById(R.id.min_pre_time_text);
        min_duration_layout=findViewById(R.id.min_pre_time_layout);
        max_duration_text=findViewById(R.id.max_pre_time_text);
        //max_duration_layout=findViewById(R.id.max_pre_time_layout);

        cat_text=findViewById(R.id.food_cat_text);




        //this call when food field change
        //for every change
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


    public void getShopData()
    {
        ///this part is to get user id of the shop
        uid=fbauth.getCurrentUser().getUid();


        //this part is to get shop's document id
        db.collection("shop")
                .whereEqualTo("shop_id", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("this_id", document.getId() + " => " + document.getData());
                                Toast.makeText(getApplicationContext(),document.getId() + " => " + document.getData(),Toast.LENGTH_SHORT).show();
                                shop_doc_id=document.getId();
                            }
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                        }
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



    //this work for button click
    //this add data to database
    public void onSubmit(View v)
    {
        if(checkFoodName() & checkPrice() & checkDuration())
        {
            //Toast.makeText(getApplicationContext(),"No error Done", Toast.LENGTH_SHORT).show();

            // Create a new food item object  with a first and last name
            Map<String, Object> fooditem = new HashMap<>();
            fooditem.put("food_name", food_name_edit_text.getText().toString());
            fooditem.put("price", food_price_edit_text.getText().toString() );
            fooditem.put("min_duration",min_duration_text.getText().toString());
            fooditem.put("max_duration",max_duration_text.getText().toString());
            fooditem.put("category",cat_text.getText().toString());

            // Add a new document with a generated ID
            db.collection("shop")
                    .document(shop_doc_id)
                    .collection(cat_text.getText().toString())
                    .add(fooditem)
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


    //this method creates dialog box
    //to conform message after sucess full add
    //need to complete this
    public void openDialog() {
        AddFoodDialog addfoodDialog = new AddFoodDialog();
        addfoodDialog.show(getSupportFragmentManager(), "example dialog");
    }


}
