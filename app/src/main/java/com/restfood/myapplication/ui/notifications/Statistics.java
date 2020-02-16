package com.restfood.myapplication.ui.notifications;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.restfood.myapplication.Auth;
import com.restfood.myapplication.R;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Statistics extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView dailyTotalTextView;

    ArrayList<Integer> totalList=new ArrayList<>();     //this is to take the plots

    DataPoint[] dataPointArray;     //this is to assign values

    //this is date
    Month currentMonth;
    int currentYear;
    int currentDay;


    private Button beforeButton;
    private TextView dateButton;
    private Button afterButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //setting the ui component
        afterButton=findViewById(R.id.after);
        dateButton=findViewById(R.id.date);
        beforeButton=findViewById(R.id.before);

        setCurrentDate();

        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                after();
            }
        });

        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                before();
            }
        });


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
        getTotal(dat);

        dateButton.setText(dat);


    }

    private void after()
    {
        this.currentDay=this.currentDay+1;

        //this part is to store order
        final String yea=String.valueOf(currentYear);
        final String mon=currentMonth.toString();
        final String day=String.valueOf(currentDay);
        final String dat=yea+"-"+mon+"-"+day;
        getTotal(dat);

        dateButton.setText(dat);
    }

    private void before()
    {
        this.currentDay=this.currentDay-1;

        //this part is to store order
        final String yea=String.valueOf(currentYear);
        final String mon=currentMonth.toString();
        final String day=String.valueOf(currentDay);
        final String dat=yea+"-"+mon+"-"+day;
        getTotal(dat);

        dateButton.setText(dat);
    }


    private void getTotal(String dat)
    {
        totalList.clear();

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

                                try {
                                    int temp=Integer.parseInt(document.get("total").toString());
                                    totalList.add(temp);
                                    total=total+Integer.parseInt(document.get("total").toString());
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                }
                                //Toast.makeText(getContext(),.((List<String>) document.get("Food_Names")).get(0),Toast.LENGTH_LONG).show();
                            }
                            //setTotal(total);       //this call to private method
                            setGraph();

                        } else {
                            Toast.makeText(getApplicationContext(),"Error in getting data",Toast.LENGTH_LONG).show();
                            //setTotal(0);        //if error set is as 0
                            setGraph();



                        }
                    }
                });

    }


    //this is to set the graph
    private void setGraph()
    {
        dataPointArray=new DataPoint[totalList.size()];

        int i;
        for(i=0;i<totalList.size();i++)
        {
            dataPointArray[i]=new DataPoint(i+1,totalList.get(i));
        }

        try
        {

            GraphView graph = (GraphView) findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPointArray);
            series.setBackgroundColor(R.color.button_blue);
            series.setDataPointsRadius(2);
            graph.addSeries(series);

        }


        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

    }
}
