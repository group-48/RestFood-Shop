package com.restfood.myapplication.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.restfood.myapplication.OrderData;
import com.restfood.myapplication.R;

public class OrderBottomSheetDialog extends BottomSheetDialogFragment {
    TextView tom;
    String docId;  //getting the data passed by fragment4

    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public OrderData orederObj;

    //ui components for payment
    private TextView totalTextView;
    private TextView paymentMethodTextView;
    private TextView paymentStatusTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.order_bottom_sheet_layout,container,false);
        docId=getTag();
        //set the ui
        totalTextView=v.findViewById(R.id.order_total);
        paymentMethodTextView=v.findViewById(R.id.order_payment_method);
        paymentStatusTextView=v.findViewById(R.id.order_payment_status);

        getOrder(docId);




        return v;
    }

    //set the ui after the db transaction
    private void setUi()
    {
        totalTextView.setText(String.valueOf(orederObj.getTotal()));
        paymentStatusTextView.setText(orederObj.getPaymentStatus());
        paymentMethodTextView.setText(orederObj.getPaymentMode());
    }




    //this method is to retreve from database again for
    //real time update
    private void getOrder(String id)
    {
        try
        {
            db.collection("orders")
                    .document(id)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    try{
                                        orederObj=new OrderData(Boolean.valueOf(document.get("Done").toString()), document.get("Notes").toString(), document.get("OrderId").toString(), document.get("PaymentMode").toString(), document.get("PaymentStatus").toString(), document.get("Status").toString(), Integer.valueOf(document.get("Total").toString()), document.get("User").toString());
                                        orederObj.setTempName(document.get("Food_Names").toString().substring(1,document.get("Food_Names").toString().length()-1));
                                        orederObj.setTempQty(document.get("Qty_List").toString().substring(1,document.get("Qty_List").toString().length()-1));
                                    }
                                    catch (Exception e)
                                    {
                                        Log.d("ERROR",e.toString());
                                    }

                                    //call the function here

                                    setUi();        //this part is setting the data

                                } else {
                                    //Log.d(TAG, "No such document");
                                }
                            } else {
                                //Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
        }catch (Exception e)
        {
            Log.d("what",e.toString());
        }





    }


}
