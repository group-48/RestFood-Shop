package com.restfood.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class EditFoodList extends AppCompatActivity {
    private RecyclerView rView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_list);


        ArrayList<FoodData> foodlist=new ArrayList<>();
        foodlist.add(new FoodData("Cheese Pizza","Pizza",250,10,20));
        foodlist.add(new FoodData("Sausage Pizza","Pizza",500,2,30));



        rView = findViewById(R.id.edit_food_list_recycler_view);
        rView.setHasFixedSize(true);

        rLayoutManager=new LinearLayoutManager(this);
        rAdapter=new FoodListEditAdapter(foodlist);

        rView.setLayoutManager(rLayoutManager);
        rView.setAdapter(rAdapter);



    }
}
