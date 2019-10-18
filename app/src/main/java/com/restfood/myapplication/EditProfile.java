package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    //path to save in firebase
    String profile_path="Shop";
    String phone_number;


    public TextInputEditText shop_name_text;

    //to show phone number from firestore
    private TextView text_phone_number;


    public String uid;
    private String doc_id;


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

        getShopData();





    }


    public void getShopData()
    {
        uid=fbauth.getCurrentUser().getUid();

        CollectionReference shop_col_ref=db.collection("shop");
        Query query1=shop_col_ref.whereEqualTo("shop_id",uid);

        db.collection("shop")
                .whereEqualTo("shop_id", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               Log.d("this_id", document.getId() + " => " + document.getData());

                                Toast.makeText(getApplicationContext(),document.getId() + " => " + document.getData(),Toast.LENGTH_SHORT).show();

                                doc_id=document.getId();
                                Map<String,Object> shopprofile=new HashMap<>();
                                shopprofile=document.getData();

                                text_phone_number.setText(shopprofile.toString());



                            }
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });

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
                .document(doc_id)
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
