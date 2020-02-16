package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import javax.sql.StatementEvent;

public class Summary extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<OrderData> orderList = new ArrayList<>();
    ArrayList<String> docIdList = new ArrayList<>();

    //ui components
    private TextView dailyTotalTextView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summaryx);
        dailyTotalTextView=findViewById(R.id.daily_total);

        orderList.add(new OrderData(true, "Pizza", "AA", "gfnufng", "gjfng", "gbuyfg", 522, "gjhbg"));


        getTotal();


    }







    //get order document from database
    //&assinging to a array
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getTotal() {
        orderList.clear();

        LocalDate currentdate = LocalDate.now();
        Month currentMonth = currentdate.getMonth();
        int currentYear = currentdate.getYear();


        //this part is to store order
        final String yea=String.valueOf(currentYear);
        final String mon=currentMonth.toString();
        final String day=String.valueOf(currentdate.getDayOfMonth());
        final String dat=yea+"-"+mon+"-"+day;

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
                            int total=0;
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc Id Are:", document.getId());

                                //creating the OrderData object
                                //OrderData taskItem = new OrderData(Boolean.valueOf(document.get("Done").toString()), document.get("Notes").toString(), document.get("OrderId").toString(), document.get("PaymentMode").toString(), document.get("PaymentStatus").toString(), document.get("Status").toString(), Integer.valueOf(document.get("Total").toString()), document.get("User").toString());
                                try {
                                    total=total+Integer.parseInt(document.get("total").toString());
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }

                                //Toast.makeText(getContext(),.((List<String>) document.get("Food_Names")).get(0),Toast.LENGTH_LONG).show();
                            }


                            //Collections.copy(orderList,list);
                             setTotal(total);

                        } else {
                            Toast.makeText(getApplicationContext(),"Error in getting data",Toast.LENGTH_LONG).show();
                            setTotal(0);



                        }
                    }
                });
    }

    private void setTotal(int total)
    {
//        int total=0;        //this is as a total
//        int i;
//
//        //calculating the total
//        for (i=0;i<orderList.size();i++)
//        {
//            total=total+orderList.get(i).getTotal();
//        }

        String temp=String.valueOf(total);
        String stringTotal="Rs."+temp;
        dailyTotalTextView.setText(stringTotal);


    }
}
