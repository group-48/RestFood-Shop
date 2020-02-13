package com.restfood.myapplication.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.restfood.myapplication.Auth;
import com.restfood.myapplication.FoodData;
import com.restfood.myapplication.InventryBottomSheetDialog;
import com.restfood.myapplication.OrderBottomSheetDialog;
import com.restfood.myapplication.OrderData;
import com.restfood.myapplication.R;
import com.restfood.myapplication.ui.home.FoodAvailableAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardFragment extends Fragment {
    private RecyclerView rView;
    private OrderAdapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    private DashboardViewModel dashboardViewModel;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    ArrayList<OrderData> orderList=new ArrayList<>();
    ArrayList<String> docIdList=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //this is pre process of ui or recyclerview
        rView = root.findViewById(R.id.order_recycler_view);
        rView.setHasFixedSize(true);
        rLayoutManager=new LinearLayoutManager(getActivity());

        //getOrder();

        orderList.add(new OrderData(true,"Pizza","AA","gfnufng","gjfng","gbuyfg",522,"gjhbg"));

        getOrder();

        postsetUi();


        return root;
    }



    //this is to set ui after the data base transaction
    private void postsetUi()
    {
        rAdapter=new OrderAdapter(orderList);
        rView.setLayoutManager(rLayoutManager);
        rView.setAdapter(rAdapter);

        //this part for real time update
        //to handle in recyclerview
        rAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            //click a card
            //to get bottomsheet
            @Override
            public void onItemClick(int position) {
                OrderBottomSheetDialog bottomsheet=new OrderBottomSheetDialog();
                bottomsheet.show(getFragmentManager(),"Thisis");
            }

            // update the three function
            @Override
            public void onDone(int position) {
                updateStatus(position,"Done");
                updateUI();
            }

            @Override
            public void onPrepare(int position) {
                updateStatus(position,"Preparing");
                orderList.get(position).setStatus("Preparing");
                updateUI();
            }

            @Override
            public void onReady(int position) {
                updateStatus(position,"Ready");
                orderList.get(position).setStatus("Ready");
                updateUI();
                //set status to database remove from list

            }

            @Override
            public void onSwitchClick(int position, boolean click) {

            }

        });

        Toast.makeText(getContext(),"Updated", Toast.LENGTH_LONG).show();
    }

    //refresh the recyclerview
    private void updateUI()
    {
        rAdapter.notifyDataSetChanged();
    }



    //this is to update the status of order
    //pos- position of object in list
    //status- what you update
    private void updateStatus(int pos, final String status)
    {
        db.collection("orders")
                .document(docIdList.get(pos))
                .update("Status",status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("This done", "DocumentSnapshot successfully updated!");
                        //Toast.makeText(getActivity().getApplicationContext(),foodList.get(position).getFoodName()+":Updated",Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity().getApplicationContext(),status+"...",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("This error", "Error updating document", e);
                        Toast.makeText(getActivity().getApplicationContext(),"Server issue",Toast.LENGTH_LONG).show();
                    }
                });
    }



    //get order document from database
    //&assinging to a array
    private void getOrder()
    {
        db.collection("orders")
                .whereEqualTo("Shop",new Auth().getUId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //this is temp list to get from database
                            orderList.clear();
                            ArrayList<OrderData> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc Id Are:",document.getId());

                                //creating the OrderData object
                                OrderData taskItem=new OrderData(Boolean.valueOf(document.get("Done").toString()),document.get("Notes").toString(),document.get("OrderId").toString(),document.get("PaymentMode").toString(),document.get("PaymentStatus").toString(),document.get("Status").toString(),Integer.valueOf(document.get("Total").toString()),document.get("User").toString());

                                //assigning food detales to that

                                orderList.add(taskItem);
                                docIdList.add(document.getId());

                                //Toast.makeText(getContext(),.((List<String>) document.get("Food_Names")).get(0),Toast.LENGTH_LONG).show();
                            }

                            //Collections.copy(orderList,list);
                            updateFood();
                            postsetUi();

                        } else {
                            postsetUi();
                            //  Toast.makeText(getApplicationContext(),"No Foods",Toast.LENGTH_LONG).show();
                            //                            Log.d(TAG, "Error getting documents: ", task.getException());

                        }
                    }
                });

    }

    //this is to get food data from database to
    private void updateFood()
    {
        int i;

        for(i=0;i<orderList.size();i++)
        {
            final int finalI = i;
            db.collection("orders")
                    .document(orderList.get(finalI).getOrderId())
                    .collection("foods")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                //this is temp list to get from database
                                OrderData objOrder=orderList.get(finalI);
                                ArrayList<OrderFoodData> list=new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    Log.d("Doc Id Are:",document.getId());
//                                    try{
                                        OrderFoodData obj=new OrderFoodData(document.get("foodId").toString(),document.get("image").toString(),document.get("name").toString(),Integer.parseInt(document.get("price").toString()),Integer.parseInt(document.get("qty").toString()),document.get("shopId").toString());
//                                    }
//                                    catch (Exception e)
//                                    {
//                                        Log.d("this is done nhe",document.getId());
//
//                                    }
                                    list.add(obj);

                                    //Toast.makeText(getContext(),document.getData().toString(),Toast.LENGTH_LONG).show();
                                }
                                objOrder.setFoodNameList(list);
                                orderList.set(finalI,objOrder);
                                Toast.makeText(getContext(),String.valueOf(orderList.get(finalI).getFoodNameList().size()),Toast.LENGTH_LONG).show();

                                //Collections.copy(orderList,list);
                                //postsetUi();

                            } else {
                                //postsetUi();
                                //  Toast.makeText(getApplicationContext(),"No Foods",Toast.LENGTH_LONG).show();
                                //                            Log.d(TAG, "Error getting documents: ", task.getException());

                            }
                        }
                    });
        }
    }

    //this is to
    private OrderData getFood(final OrderData obj, String orderId)
    {
        db.collection("orders")
                .document(orderId)
                .collection("foods")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //this is temp list to get from database

                            ArrayList<OrderData> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Doc Id Are:",document.getId());

                                OrderFoodData objx=document.toObject(OrderFoodData.class);
                                //obj.addFood(objx);


                                //Toast.makeText(getContext(),.((List<String>) document.get("Food_Names")).get(0),Toast.LENGTH_LONG).show();
                            }


                        } else {


                        }
                    }
                });
        Toast.makeText(getContext(),obj.getFoodNameList().toString(),Toast.LENGTH_LONG).show();



        return obj;

    }



}