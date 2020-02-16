package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.restfood.myapplication.ui.dashboard.DashboardViewModel;
import com.restfood.myapplication.ui.dashboard.OrderAdapter;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity {

    private RecyclerView rView;
    private OrderHistoryAdapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<OrderData> orderList = new ArrayList<>();
    ArrayList<String> docIdList = new ArrayList<>();

    //this is date
    Month currentMonth;
    int currentYear;
    int currentDay;


    //this is to set the date
    private Button beforeButton;
    private TextView dateButton;
    private Button afterButton;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        //this is pre process of ui or recyclerview
        rView = findViewById(R.id.order_history_recycler_view);
        rView.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(getApplicationContext());

        //setting the ui component
        afterButton=findViewById(R.id.after);
        dateButton=findViewById(R.id.date);
        beforeButton=findViewById(R.id.before);

        setCurrentDate();

    }

    private void postUi()
    {
        rAdapter = new OrderHistoryAdapter(orderList);
        rView.setLayoutManager(rLayoutManager);
        rView.setAdapter(rAdapter);


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setCurrentDate()
    {
        LocalDate currentDate = LocalDate.now();
        this.currentMonth = currentDate.getMonth();
        this.currentYear = currentDate.getYear();
        this.currentDay=currentDate.getDayOfMonth();

        //this part is to store order
        final String yea=String.valueOf(currentYear);
        final String mon=currentMonth.toString();
        final String day=String.valueOf(currentDay);
        final String dat=yea+"-"+mon+"-"+day;
        getOrderHistory(dat);

        dateButton.setText(dat);

    }

    private void getOrderHistory(String dat)
    {
        orderList.clear();
        db.collection("shop")
                .document(new Auth().getUId())
                .collection("orders")
                .document(new Auth().getUId())
                .collection(dat)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //this is temp list to get from database
                            //orderList.clear();
                            ArrayList<OrderData> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc Id Are:", document.getId());

                                //creating the OrderData object
                                //OrderData taskItem = new OrderData(Boolean.valueOf(document.get("Done").toString()), document.get("Notes").toString(), document.get("OrderId").toString(), document.get("PaymentMode").toString(), document.get("PaymentStatus").toString(), document.get("Status").toString(), Integer.valueOf(document.get("Total").toString()), document.get("User").toString());
                                try
                                {
                                    OrderData taskItem=new OrderData();
                                    taskItem.setTempName(document.get("tempName").toString());
                                    taskItem.setTempQty(document.get("tempQty").toString());
                                    taskItem.setTotal(Integer.parseInt(document.get("total").toString()));
                                    taskItem.setOrderId(document.get("orderId").toString());

                                    orderList.add(taskItem);
                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                                }

                                docIdList.add(document.getId());


                            }
                            //Toast.makeText(getApplicationContext(),orderList.size(),Toast.LENGTH_LONG).show();

                            //Collections.copy(orderList,list);
                            postUi();

                        } else {
                            //postUi();


                        }
                    }
                });


    }
}
