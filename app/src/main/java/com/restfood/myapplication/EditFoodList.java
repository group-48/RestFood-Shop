package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

    private ArrayList<FoodData> foodList=new ArrayList<>();


    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_list);





        foodList.add(new FoodData("Cheese Pizza","Pizza",250,10,20));
        foodList.add(new FoodData("Sausage Pizza","Pizza",500,2,30));

        getFoodList();



        rView = findViewById(R.id.edit_food_list_recycler_view);
        rView.setHasFixedSize(true);

        rLayoutManager=new LinearLayoutManager(this);

        //assigning list to adepter
        rAdapter=new FoodListEditAdapter(foodList);

        rView.setLayoutManager(rLayoutManager);
        rView.setAdapter(rAdapter);

    }

    //this function is to get food list form firestore
    private void getFoodList()
    {
        //used own auth class to get details about firestore
        String shopDocId=new Auth().getShopDocId();

        db.collection("shop")
                .document(shopDocId)
                .collection("ShopList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    //Log.d("this_id", document.getId() + " => " + document.getData());
                                    //foodList.add(document.toObject(FoodData.class));

                                }
                            } else {
                //                            Log.d(TAG, "Error getting documents: ", task.getException());

                            }
                        }
                    });

    }



}
