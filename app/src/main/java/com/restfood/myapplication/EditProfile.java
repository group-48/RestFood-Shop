package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    //path to save in firebase
    String phone_number;


    public TextInputEditText shop_name_text;

    //to show phone number from firestore
    private TextView text_phone_number;


    public String uid;

    //this is to get auth data
    private Auth auth=new Auth();
    FirebaseFirestore db=FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //initial all objects
        shop_name_text= findViewById(R.id.shopname_edit_text);
        text_phone_number=findViewById(R.id.edit_profile_phone_number_text);

        onBegi();


        //getting phone number and display in phone
        text_phone_number.setText(phone_number);


    }

    private void onBegi()
    {
        uid=auth.getUId();
        phone_number=auth.getPhoneNo();

    }





    public void onDone(View v)
    {
        //genarate data to save in database
        String shopname=shop_name_text.getText().toString();


        //create map object to set in firebase
        Map<String,Object> shopprofile=new HashMap<>();
        shopprofile.put("shop_name",shopname);
        shopprofile.put("shop_id",uid);


        //conect with firebase
        //and here updating theprofile
        db.collection("shop")
                .document(uid)
                .set(shopprofile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();;
                    }
                });
    }



}
