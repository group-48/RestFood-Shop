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

import javax.sql.StatementEvent;


///this class is to assign value and create a componentx
public class ReservationAdapter extends RecyclerView.Adapter<com.restfood.myapplication.ui.notifications.ReservationAdapter.ReservationViewHolder> {

    ///use this list to show in list view

    private ArrayList<QueryDocumentSnapshot> list;

    //private OnItemClickListener mListener;
    private com.restfood.myapplication.ui.notifications.ReservationAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onAccept(int position);
        void onCancel(int position);
        void onCheckIn(int position);

    }

    public void setOnItemClickListener(com.restfood.myapplication.ui.notifications.ReservationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    //this is for ui view elements
    public static class ReservationViewHolder extends RecyclerView.ViewHolder
    {
        ///create view for single unit
        private TextView reservationTextView;
        private TextView dateTextView;
        private TextView timeTextView;
        private TextView guestTextView;
        private TextView statusTextView;

        //this is to handle reservation
        private TextView acceptTextView;
        private TextView cancelTextView;
        private TextView checkinTextView;


        public ReservationViewHolder(@NonNull View itemView,final com.restfood.myapplication.ui.notifications.ReservationAdapter.OnItemClickListener listener) {
            super(itemView);

            //assigning ui values to variables
            reservationTextView=itemView.findViewById(R.id.res_title);
            dateTextView=itemView.findViewById(R.id.date_res);
            timeTextView=itemView.findViewById(R.id.time_res);
            guestTextView=itemView.findViewById(R.id.no_guest);
            statusTextView=itemView.findViewById(R.id.status_res);

            acceptTextView=itemView.findViewById(R.id.prepare);
            cancelTextView=itemView.findViewById(R.id.ready);
            checkinTextView=itemView.findViewById(R.id.done);


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
            acceptTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onAccept(position);
                        }
                    }

                }
            });

            cancelTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onCancel(position);
                        }
                    }

                }
            });

            checkinTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onCheckIn(position);
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
        QueryDocumentSnapshot obj=list.get(position);

        String temp;
        temp=obj.get("date").toString();
        String date="Date:"+temp;

        holder.dateTextView.setText(date);

        temp=obj.get("time").toString();
        String time="Time:"+temp;
        holder.timeTextView.setText(time);

        temp=obj.get("guestno").toString();
        String gNo="No of Guest:"+temp;
        holder.guestTextView.setText(gNo);

        temp=obj.get("status").toString();
        String status="Status:"+temp;
        holder.statusTextView.setText(status);


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