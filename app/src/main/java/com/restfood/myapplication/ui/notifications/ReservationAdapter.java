package com.restfood.myapplication.ui.notifications;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.restfood.myapplication.R;

import java.util.ArrayList;
import java.util.List;


///this class is to assign value and create a componentx
public class ReservationAdapter extends RecyclerView.Adapter<com.restfood.myapplication.ui.notifications.ReservationAdapter.ReservationViewHolder> {

    ///use this list to show in list view

    private ArrayList<QueryDocumentSnapshot> list;

    //private OnItemClickListener mListener;
    private com.restfood.myapplication.ui.notifications.ReservationAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDone(int position);
        void onPrepare(int position);
        void onReady(int position);

        void onSwitchClick(int position,boolean click);
    }

    public void setOnItemClickListener(com.restfood.myapplication.ui.notifications.ReservationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    //this is for ui view elements
    public static class ReservationViewHolder extends RecyclerView.ViewHolder
    {
        ///create view for single unit
        private TextView foodnameTextView;
        private TextView totalTextView;
        private TextView doneTextView;
        private TextView prepareTextView;
        private TextView readyTextView;
        private TextView statusTextView;
        private TextView paidTextView;
        private TextView orderTitleTextView;


        public ReservationViewHolder(@NonNull View itemView,final com.restfood.myapplication.ui.notifications.ReservationAdapter.OnItemClickListener listener) {
            super(itemView);

            //assigning ui values to variables
            foodnameTextView=itemView.findViewById(R.id.order_food_item);
            totalTextView=itemView.findViewById(R.id.total);
            doneTextView=itemView.findViewById(R.id.done);
            prepareTextView=itemView.findViewById(R.id.prepare);
            readyTextView=itemView.findViewById(R.id.ready);
            paidTextView=itemView.findViewById(R.id.is_paid);
            statusTextView=itemView.findViewById(R.id.food_status);
            orderTitleTextView=itemView.findViewById(R.id.order_title);



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


            //three updating the food state
            //this gives position to fragment
            doneTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onDone(position);
                        }
                    }

                }
            });

            prepareTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onPrepare(position);
                        }
                    }

                }
            });

            readyTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onReady(position);
                        }
                    }

                }
            });


        }
    }

    //constructor for this class
    public ReservationAdapter(ArrayList<QueryDocumentSnapshot> listx)
    {
        list=listx;
    }


    ///this is identfing the view component andr returnig
    @NonNull
    @Override
    public com.restfood.myapplication.ui.notifications.ReservationAdapter.ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_card, parent, false);
        com.restfood.myapplication.ui.notifications.ReservationAdapter.ReservationViewHolder vi = new com.restfood.myapplication.ui.notifications.ReservationAdapter.ReservationViewHolder(v,mListener);
        return vi;
    }


    //getting position of list and setting all values to that
    @Override
    public void onBindViewHolder(@NonNull com.restfood.myapplication.ui.notifications.ReservationAdapter.ReservationViewHolder holder, int position) {
        //ReservationData currentdata=list.get(position);




        //Log.d("Available check",String.valueOf(currentdata.getIsAvailable()));

    }

    private String getFood(List<String> name,List<String> qty)
    {

        String va="Hi";
        va =name.get(0);
        if(name.size()>1)
        {
            int i;
            for(i=1;i<name.size();i++)
            {
                //va=va+name.get(i)+qty.get(i);

            }
        }


        return va;
    }


    //this returnig number of elements in this list
    @Override
    public int getItemCount() {
        return list.size();
    }


}