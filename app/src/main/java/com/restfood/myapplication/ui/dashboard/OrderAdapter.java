package com.restfood.myapplication.ui.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.restfood.myapplication.FoodData;
import com.restfood.myapplication.R;

import java.util.ArrayList;


///this class is to assign value and create a componentx
public class OrderAdapter extends RecyclerView.Adapter<com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder> {

    ///use this list to show in list view
    private ArrayList<FoodData> foodListX;

    //private OnItemClickListener mListener;
    private com.restfood.myapplication.ui.dashboard.OrderAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onSwitchClick(int position,boolean click);
    }

    public void setOnItemClickListener(com.restfood.myapplication.ui.dashboard.OrderAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    //this is for ui view elements
    public static class OrderViewHolder extends RecyclerView.ViewHolder
    {
        ///create view for single unit
        public TextView foodnameTextView;
        //public TextView priceTextView;


        public OrderViewHolder(@NonNull View itemView,final com.restfood.myapplication.ui.dashboard.OrderAdapter.OnItemClickListener listener) {
            super(itemView);
            foodnameTextView=itemView.findViewById(R.id.foodNameTextViewAvail);

            //catTextView=itemView.findViewById(R.id.catTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }

                }
            });


        }
    }

    //constructor for this class
    public OrderAdapter(ArrayList<FoodData> list)
    {
        foodListX=list;
    }


    ///this is identfing the view component andr returnig
    @NonNull
    @Override
    public com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_card, parent, false);
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card, parent, false);
        com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder vi = new com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder(v,mListener);
        return vi;
    }


    //getting position of list and setting all values to that
    @Override
    public void onBindViewHolder(@NonNull com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder holder, int position) {
        FoodData currentdata=foodListX.get(position);

        holder.foodnameTextView.setText(currentdata.getFoodName());


        Log.d("Available check",String.valueOf(currentdata.getIsAvailable()));

    }


    //this returnig number of elements in this list
    @Override
    public int getItemCount() {
        return foodListX.size();
    }


}