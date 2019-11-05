package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    //path to save in firebase
    String phone_number;


    public TextInputEditText shop_name_text;
    private TextInputEditText shop_address_text;
    private TextInputEditText shop_type_text;
    private TextInputEditText shop_email_text;

    //to show phone number from firestore
    private TextView text_phone_number;


    public String uid;
    private Shop shopObj;

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
        shop_address_text=findViewById(R.id.shopaddress_edit_text);
        shop_type_text=findViewById(R.id.type_of_shop_edit_text);
        shop_email_text=findViewById(R.id.email_edit_text);


        onBegi();
        getShopData();


        //getting phone number and display in phone
        text_phone_number.setText(phone_number);


    }

    private void onBegi()
    {
        uid=auth.getUId();
        phone_number=auth.getPhoneNo();

    }


    private void getShopData()
    {
        db.collection("shop")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                shopObj=document.toObject(Shop.class);
                            } else {

                            }
                        } else {

                        }
                    }
                });


        //assigning values to ui
        if(shopObj.getShopName()!=null)
        {
            shop_name_text.setText(shopObj.getShopName());
        }
        if(shopObj.getShop_type()!=null)
        {
            shop_type_text.setText(shopObj.getShop_type());
        }
        if(shopObj.getShopemail() !=null){
            shop_email_text.setText(shopObj.getShopemail());
        }
        if(shopObj.getShopAddress()!=null)
        {
            shop_address_text.setText(shopObj.getShopAddress());
        }

    }





    public void onDone(View v)
    {
        //genarate data to save in database
        String shop_name=shop_name_text.getText().toString();
        String shop_email=shop_email_text.getText().toString();
        String shop_address=shop_address_text.getText().toString();
        String shop_type=shop_type_text.getText().toString();


        //create map object to set in firebase
        Map<String,Object> shopprofile=new HashMap<>();
        shopprofile.put("shop_id",uid);

        if(shop_name!=null)
        {
            shopprofile.put("shop_name",shop_name);
        }

        if(shop_address!=null)
        {
            shopprofile.put("shop_name",shop_address);
        }
        if(shop_email !=null){
            shopprofile.put("shop_email",shop_email);
        }
        if(shop_address!=null){
            shopprofile.put("shop_address",shop_address);

        }
        if(shop_type !=null){
            shopprofile.put( "shop_type",shop_type);
        }


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
