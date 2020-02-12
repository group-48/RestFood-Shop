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

        orderList.add(new OrderData(true,"Pizza","AA",100,"BB"));
        orderList.add(new OrderData(true,"Pizza","AA",100,"BB"));
        orderList.add(new OrderData(true,"Pizza","AA",100,"BB"));
        getOrder();

        postsetUi();


        return root;
    }


    private void postsetUi()
    {
        rAdapter=new OrderAdapter(orderList);
        rView.setLayoutManager(rLayoutManager);
        rView.setAdapter(rAdapter);

        //this part for real time update
        rAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                InventryBottomSheetDialog bottomsheet=new InventryBottomSheetDialog();
                bottomsheet.show(getFragmentManager(),"Thisis");
            }

            @Override
            public void onDone(int position) {
                updateStatus(position,"Done");
            }

            @Override
            public void onPrepare(int position) {
                updateStatus(position,"Preparing");
                orderList.get(position).setStatus("Preparing");

            }

            @Override
            public void onReady(int position) {
                updateStatus(position,"Ready");
                orderList.get(position).setStatus("Ready");

            }

            @Override
            public void onSwitchClick(int position, boolean click) {

            }

        });

        Toast.makeText(getContext(),"Updated", Toast.LENGTH_LONG).show();
    }


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

                                OrderData taskItem=new OrderData(Boolean.valueOf(document.getData().get("Done").toString()),document.getData().get("Shop").toString(),document.getData().get("Status").toString(),Integer.valueOf(document.getData().get("Total").toString()),document.getData().get("User").toString(),(List<String>)document.getData().get("Food_Name"),(List<String>)document.getData().get("Qty_List"));
                                //
                                orderList.add(taskItem);
                                docIdList.add(document.getId());

                                //Toast.makeText(getContext(),taskItem.getFoodList().get(0),Toast.LENGTH_LONG).show();
                            }

                            //Collections.copy(orderList,list);
                            postsetUi();

                        } else {
                            postsetUi();
                            //  Toast.makeText(getApplicationContext(),"No Foods",Toast.LENGTH_LONG).show();
                            //                            Log.d(TAG, "Error getting documents: ", task.getException());

                        }
                    }
                });



    }



}