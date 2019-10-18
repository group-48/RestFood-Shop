package com.restfood.myapplication;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Auth {
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseAuth fbauth= FirebaseAuth.getInstance();

    //this
    String temp=null;


    public String getUId()
    {
        return fbauth.getCurrentUser().getUid();
    }

    public String getShopDocId()
    {
        //String shop_doc_id=null;
        //this part is to get shop's document id
        db.collection("shop")
                .whereEqualTo("shop_id", getUId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("this_id", document.getId() + " => " + document.getData());
                                temp=document.getId();
                            }
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());

                        }
                    }
                });

       // shop_doc_id=temp;
        //temp=null;

        return temp;
    }


}
