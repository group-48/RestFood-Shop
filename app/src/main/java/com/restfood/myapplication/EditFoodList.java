package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class EditFoodList extends AppCompatActivity {
    private RecyclerView rView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    public ArrayList<FoodData> foodList=new ArrayList<>();
    private String uId;
    FoodData dat;


    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_list);



        foodList.add(new FoodData("Cheese Pizza","Pizza",250,10,20));
        foodList.add(new FoodData("Sausage Pizza","Pizza",500,2,30));

        onbegi();
        getFoodList();





        rView = findViewById(R.id.edit_food_list_recycler_view);
        rView.setHasFixedSize(true);

        rLayoutManager=new LinearLayoutManager(this);

        //assigning list to adepter
        rAdapter=new FoodListEditAdapter(foodList);

        rView.setLayoutManager(rLayoutManager);
        rView.setAdapter(rAdapter);

    }

    private void onbegi()
    {
        Auth auth=new Auth();
        uId=auth.getUId();
    }

    //this function is to get food list form firestore
    private void getFoodList()
    {
        db.collection("shop")
                .document(uId)
                .collection("FoodList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("this_id", document.getId() + " => " + document.getData());
                                    dat=document.toObject(FoodData.class);

                                    Log.d("test this", dat.getFoodName());
                                    Log.d("test this", dat.getCategory());

//                                    foodList.add(dat);
                                    foodList.add(new FoodData("Sausage Pizza","Pizza",500,2,30));


                                }
                            } else {
                //                            Log.d(TAG, "Error getting documents: ", task.getException());

                            }
                        }
                    });

        int num=foodList.size();
        foodList.add(new FoodData("Sausage Pizza","Pizza",500,2,30));
        Log.d("number",String.valueOf(num));
        Toast.makeText(getApplicationContext(),String.valueOf(num),Toast.LENGTH_LONG).show();



    }



}
