package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventry extends AppCompatActivity {
    FloatingActionButton buttonAdd;
    RecyclerView itemRecyclerView;
    ArrayList<InventryData> itemList;
    ArrayList<String> docIdList;
    private String uId;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventry);

        itemList=new ArrayList<>();
        docIdList=new ArrayList<>();

        buttonAdd=findViewById(R.id.floatingbutton_invent);
        itemRecyclerView=findViewById(R.id.inventory_recycler_view);

        onbegi();
        getDoc();



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventryBottomSheetDialog bottomsheet=new InventryBottomSheetDialog();
                bottomsheet.show(getSupportFragmentManager(),"Hi this is ");
            }
        });
    }


    private void getDoc()
    {
        try{
            db.collection("shop")
                    .document(new Auth().getUId())
                    .collection("inventory")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                //this is temp list to get from database
                                List<InventryData> list = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d("this_id", document.getId() + " => " + document.getData());

                                    docIdList.add(document.getId());
                                    Log.d("Doc Id Are:",document.getId());
                                    try
                                    {
                                        InventryData taskItem = document.toObject(InventryData.class);
                                        list.add(taskItem);
                                    }
                                    catch (Exception e)
                                    {
                                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                                    }

                                    //foodList.add(new FoodData("Sausage Pizza","Pizza",500,2,30));

                                }
                                Collections.copy(itemList,list);
                                //postsetUi();

                            } else {
                                //postsetUi();
                                //  Toast.makeText(getApplicationContext(),"No Foods",Toast.LENGTH_LONG).show();
                                //                            Log.d(TAG, "Error getting documents: ", task.getException());

                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Server error Unable to retreve data",Toast.LENGTH_LONG).show();
        }

    }

    //user id retreve
    private void onbegi()
    {
        Auth auth=new Auth();
        uId=auth.getUId();
    }

    private void add()
    {
        InventryBottomSheetDialog bottomsheet=new InventryBottomSheetDialog();
        bottomsheet.show(getSupportFragmentManager(),"Hi this is ");
    }
}
