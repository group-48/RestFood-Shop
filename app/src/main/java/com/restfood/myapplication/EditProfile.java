package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    //path to save in firebase
    String profile_path="Shop";
    String phone_number;


    public TextInputEditText shop_name_text;
    private TextView text_phone_number;

    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth fbauth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //initial all objects
        shop_name_text= findViewById(R.id.shopname_edit_text);
        text_phone_number=findViewById(R.id.edit_profile_phone_number_text);

        //getting phone number and display in phone
        phone_number=fbauth.getCurrentUser().getPhoneNumber();
        text_phone_number.setText(phone_number);



    }


    public void onDone(View v)
    {
        //genarate data to save in database
        String shopname=shop_name_text.getText().toString();
//        String uid=fbauth.getCurrentUser().getUid();

        //create map object to set in firebase
        Map<String,Object> shopprofile=new HashMap<>();
        shopprofile.put("shop_name",shop_name_text.getText().toString());
       shopprofile.put("shop_id",fbauth.getCurrentUser().getUid());


        //conect with firebase
        db.collection("shop")
                .add(shopprofile)
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
                        Toast.makeText(getApplicationContext(),"Error adding doc", Toast.LENGTH_SHORT).show();
                    }
                });





    }



}
