package com.restfood.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


///this class is to assign value and create a componentx
public class FoodListEditAdapter extends RecyclerView.Adapter<FoodListEditAdapter.FoodListEditViewHolder> {

    ///use this list to show in list view
    private ArrayList<FoodData> foodListX;


    //this is for ui view elements
    public static class FoodListEditViewHolder extends RecyclerView.ViewHolder
    {
        ///create view for single unit
        public TextView nameTextView;
        public TextView priceTextView;

        public FoodListEditViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.foodNameTextView);
            priceTextView=itemView.findViewById(R.id.priceTextView);
        }
    }

    //constructor for this class
    public FoodListEditAdapter(ArrayList<FoodData> list)
    {
        foodListX=list;
    }


    ///this is identfing the view component andr returnig
    @NonNull
    @Override
    public FoodListEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_card, parent, false);
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_card, parent, false);
        FoodListEditViewHolder vi = new FoodListEditViewHolder(v);
        return vi;
    }


    //getting position of list and setting all values to that
    @Override
    public void onBindViewHolder(@NonNull FoodListEditViewHolder holder, int position) {
        FoodData currentdata=foodListX.get(position);

        holder.nameTextView.setText(currentdata.getFoodName());
        holder.priceTextView.setText(currentdata.getCategory());

    }


    //this returnig number of elements in this list
    @Override
    public int getItemCount() {
        return foodListX.size();
    }







}
