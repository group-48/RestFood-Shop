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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddInventory extends AppCompatActivity {
    String docId;


    TextInputEditText priceEditText;
    TextInputEditText qtyEditText;
    TextInputEditText minQtyEditText;
    TextInputEditText nameEditText;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inventory);
        setTitle("Add Item");

        priceEditText=findViewById(R.id.item_price_text);
        qtyEditText=findViewById(R.id.item_qty_text);
        minQtyEditText=findViewById(R.id.item_min_qty_text);
        nameEditText=findViewById(R.id.item_name_text);
    }


    public void onSubmit(View view)
    {
        String price=priceEditText.getText().toString();
        String qty=qtyEditText.getText().toString();
        String minQty=minQtyEditText.getText().toString();
        String name=nameEditText.getText().toString();

        InventryData objx=new InventryData(name,Double.valueOf(qty),Double.valueOf(price),Double.valueOf(minQty));

        db.collection("shop")
                .document(new Auth().getUId())
                .collection("inventory")
                .add(objx)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        //Toast.makeText(getApplicationContext(),"DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();

                        finish();
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

    public void onCancel(View view)
    {
        finish();
    }
}
