package com.restfood.myapplication.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.restfood.myapplication.OrderData;
import com.restfood.myapplication.R;

import java.util.ArrayList;
import java.util.List;


///this class is to assign value and create a componentx
public class OrderAdapter extends RecyclerView.Adapter<com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder> {

    ///use this list to show in list view

    private ArrayList<OrderData> orderListX;

    //private OnItemClickListener mListener;
    private com.restfood.myapplication.ui.dashboard.OrderAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDone(int position);
        void onPrepare(int position);
        void onReady(int position);

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
        public TextView totalTextView;
        public TextView doneTextView;
        public TextView prepareTextView;
        public TextView readyTextView;
        public TextView statusTextView;


        public OrderViewHolder(@NonNull View itemView,final com.restfood.myapplication.ui.dashboard.OrderAdapter.OnItemClickListener listener) {
            super(itemView);
            foodnameTextView=itemView.findViewById(R.id.order_food_item);
            totalTextView=itemView.findViewById(R.id.total);
            doneTextView=itemView.findViewById(R.id.done);
            prepareTextView=itemView.findViewById(R.id.prepare);
            readyTextView=itemView.findViewById(R.id.ready);
            statusTextView=itemView.findViewById(R.id.is_paid);



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
    public OrderAdapter(ArrayList<OrderData> list)
    {
        orderListX=list;
    }


    ///this is identfing the view component andr returnig
    @NonNull
    @Override
    public com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card, parent, false);
        com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder vi = new com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder(v,mListener);
        return vi;
    }


    //getting position of list and setting all values to that
    @Override
    public void onBindViewHolder(@NonNull com.restfood.myapplication.ui.dashboard.OrderAdapter.OrderViewHolder holder, int position) {
        OrderData currentdata=orderListX.get(position);

        //holder.foodnameTextView.setText(getFood(currentdata.getFoodList(),currentdata.getQuanList()));
        holder.foodnameTextView.setText(currentdata.getShop());

        String tot="Price:"+currentdata.getTotal();
        holder.totalTextView.setText(tot);

        String stat="Status:"+currentdata.getStatus();
        holder.statusTextView.setText(stat);


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
        return orderListX.size();
    }


}