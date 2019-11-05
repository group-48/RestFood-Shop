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
import com.google.android.material.textfield.TextInputLayout;
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

    private TextInputLayout shop_email_layout;

    //to show phone number from firestore
    private TextView text_phone_number;


    public String uid;
    public Shop shopObj;

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

        shop_email_layout=findViewById(R.id.email_text_layout);


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
                .document(new Auth().getUId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Toast.makeText(getApplicationContext(),document.getData().toString(),Toast.LENGTH_LONG).show();
                                //shopObj=document.toObject(Shop.class);

                            } else {

                            }
                        } else {

                        }
                    }
                });


        //assigning values to ui
//        if(shopObj.getShopName()!=null)
//        {
//            shop_name_text.setText(shopObj.getShopName());
//        }
//        if(shopObj.getShop_type()!=null)
//        {
//            shop_type_text.setText(shopObj.getShop_type());
//        }
//        if(shopObj.getShopemail() !=null){
//            shop_email_text.setText(shopObj.getShopemail());
//        }
//        if(shopObj.getShopAddress()!=null)
//        {
//            shop_address_text.setText(shopObj.getShopAddress());
//        }

    }


    private boolean valEmail()
    {
        String email=shop_email_text.getText().toString();

        if(email==null)
        {
            return true;
        }
        else
        {
            boolean check=false;
            for(int i=0;i<email.length();i++)
            {
                if(email.charAt(i)=='@')
                {
                    check=true;
                }
            }

            if(check==true)
            {
                return true;
            }
            else
            {
                shop_email_layout.setError("No @ in your email");
                return false;
            }
        }
    }

    public void onDone(View v)
    {
        if(valEmail() )
        {
            submit();
        }

    }


    private void submit()
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
            shopprofile.put("shopName",shop_name);
        }

        if(shop_address!=null)
        {
            shopprofile.put("shopAddress",shop_address);
        }
        if(shop_email !=null){
            shopprofile.put("shopEmail",shop_email);
        }

        if(shop_type !=null){
            shopprofile.put( "shopType",shop_type);
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
