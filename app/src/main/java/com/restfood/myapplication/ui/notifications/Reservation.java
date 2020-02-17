package com.restfood.myapplication.ui.notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.restfood.myapplication.Auth;
import com.restfood.myapplication.R;

import java.util.ArrayList;

public class Reservation extends AppCompatActivity {

    //this is objects for recycler view
    private RecyclerView rView;
    private ReservationAdapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    ArrayList<ReservationData> reservationList = new ArrayList<>();
    ArrayList<String> docIdList = new ArrayList<>();
    ArrayList<QueryDocumentSnapshot> list=new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        setTitle("Reservation");

        //recycler view assigning
        rView = findViewById(R.id.reserve_recycler_view);
        rView.setHasFixedSize(true);
        rLayoutManager = new LinearLayoutManager(getApplicationContext());

        getReservation();
    }


    private void postsetUi()
    {
        rAdapter = new ReservationAdapter(list);
        rView.setLayoutManager(rLayoutManager);
        rView.setAdapter(rAdapter);

    }


    private void getReservation()
    {
        reservationList.clear();

        db.collection("reserve")
                .whereEqualTo("shopId",new Auth().getUId())
                .whereEqualTo("status","Requested")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //this is temp list to get from database
                            //orderList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc Id Are:", document.getId());
                                try
                                {
//                                    ReservationData obj=new ReservationData();
//                                    obj.setBookingId(document.getString("bookingId"));
//                                    obj.setDate(document.getString("date"));
//                                    obj.setGuestno(document.getString("guestno"));
//                                    obj.setShopId(document.getString("shopId"));
//                                    obj.setShopName(document.getString("shopName"));
//                                    obj.setTime(document.getString("time"));
//                                    obj.setStatus(document.getString("status"));
//                                    obj.setUserId(document.getString("userId"));
//                                    reservationList.add(obj);
                                    list.add(document);

                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                                }

                                docIdList.add(document.getId());

                                //Toast.makeText(getContext(),.((List<String>) document.get("Food_Names")).get(0),Toast.LENGTH_LONG).show();
                            }

                            //Collections.copy(orderList,list);
                            postsetUi();

                        } else {
                            postsetUi();


                        }
                    }
                });

        db.collection("reserve")
                .whereEqualTo("shopId",new Auth().getUId())
                .whereEqualTo("status","Accepted")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //this is temp list to get from database
                            //orderList.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc Id Are:", document.getId());
                                try
                                {
//                                    ReservationData obj=new ReservationData();
//                                    obj.setBookingId(document.getString("bookingId"));
//                                    obj.setDate(document.getString("date"));
//                                    obj.setGuestno(document.getString("guestno"));
//                                    obj.setShopId(document.getString("shopId"));
//                                    obj.setShopName(document.getString("shopName"));
//                                    obj.setTime(document.getString("time"));
//                                    obj.setStatus(document.getString("status"));
//                                    obj.setUserId(document.getString("userId"));
//                                    reservationList.add(obj);
                                    list.add(document);
                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                                }

                                docIdList.add(document.getId());

                                //Toast.makeText(getContext(),.((List<String>) document.get("Food_Names")).get(0),Toast.LENGTH_LONG).show();
                            }

                            //Collections.copy(orderList,list);
                            postsetUi();

                        } else {
                            postsetUi();


                        }
                    }
                });

    }
}
