package com.restfood.myapplication.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.restfood.myapplication.OrderData;
import com.restfood.myapplication.R;

import static com.restfood.myapplication.R.drawable.buttonstyle1;
import static com.restfood.myapplication.R.drawable.buttonstyle2;
import static com.restfood.myapplication.R.drawable.cancelbutton;

public class OrderBottomSheetDialog extends BottomSheetDialogFragment {
    TextView tom;
    String docId;  //getting the data passed by fragment4

    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public OrderData orederObj;

    //ui components for payment
    private TextView totalTextView;
    private TextView paymentMethodTextView;
    private TextView paymentStatusTextView;
    private Button paymetButton;
    private Button cancelButton;

    //button to handleorer
    private Button preparingButton;
    private Button readyButton;
    private Button doneButton;

    private TextView orderIdTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.order_bottom_sheet_layout,container,false);
        docId=getTag();
        //set the ui
        totalTextView=v.findViewById(R.id.order_total);
        paymentMethodTextView=v.findViewById(R.id.order_payment_method);
        paymentStatusTextView=v.findViewById(R.id.order_payment_status);
        paymetButton=v.findViewById(R.id.order_payment_button);
        cancelButton=v.findViewById(R.id.cancel_order);

        //order handling
        preparingButton=v.findViewById(R.id.button_preparing);
        readyButton=v.findViewById(R.id.button_ready);
        doneButton=v.findViewById(R.id.button_done);

        orderIdTextView=v.findViewById(R.id.order_title);


        getOrder(docId);




        return v;
    }

    //set the ui after the db transaction
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void setUi()
    {
        totalTextView.setText(String.valueOf(orederObj.getTotal()));
        paymentStatusTextView.setText(orederObj.getPaymentStatus());
        paymentMethodTextView.setText(orederObj.getPaymentMode());

        String title=orederObj.getOrderId();
        orderIdTextView.setText(title);


        //Toast.makeText(getContext(),orederObj.getStatus(),Toast.LENGTH_LONG).show();
        String status=orederObj.getStatus();
        Toast.makeText(getContext(),status,Toast.LENGTH_LONG).show();
        if(status.equals("Preparing"))
        {
            //setting button color
            readyButton.setBackground(getResources().getDrawable(buttonstyle2));
            preparingButton.setBackground(getResources().getDrawable(buttonstyle1));
            doneButton.setBackground(getResources().getDrawable(buttonstyle2));

            preparingButton.setTextColor(R.color.white);


        }
        else if(status.equals("Ready"))
        {
            //ui change part
            readyButton.setBackground(getResources().getDrawable(buttonstyle1));
            preparingButton.setBackground(getResources().getDrawable(buttonstyle1));
            doneButton.setBackground(getResources().getDrawable(buttonstyle2));

            readyButton.setTextColor(R.color.white);
            preparingButton.setTextColor(R.color.white);

            //db part


        }
        else if(status.equals("Done"))
        {
            //ui change part
            readyButton.setBackground(getResources().getDrawable(buttonstyle1));
            preparingButton.setBackground(getResources().getDrawable(buttonstyle1));
            doneButton.setBackground(getResources().getDrawable(buttonstyle1));

            readyButton.setTextColor(R.color.white);
            preparingButton.setTextColor(R.color.white);
            doneButton.setTextColor(R.color.white);



        }

        if(orederObj.getPaymentStatus().equals("Paid"))
        {
            paymetButton.setText("Paid");   //this check and update
        }


        //this is to butoon
        paymetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //paymetButton.setText("Paid");
                if(orederObj.getPaymentStatus().equals("Paid"))
                {
                    Toast.makeText(getActivity(),"Already Paid",Toast.LENGTH_LONG).show();

                }
                else
                {
                    updatePayment(orederObj.getOrderId(),"Paid");
                    paymetButton.setText("Paid");   //this check and update
                }


            }
        });


        //button to handle cancel part
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrder(orederObj.getOrderId(),"Cancel");
            }
        });


        ///oder handling part

        preparingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readyButton.setBackground(getResources().getDrawable(buttonstyle2));
                preparingButton.setBackground(getResources().getDrawable(buttonstyle1));
                doneButton.setBackground(getResources().getDrawable(buttonstyle2));

                preparingButton.setTextColor(R.color.white);

                updateStatus(orederObj.getOrderId(),"Preparing");


            }
        });


        readyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyButton.setBackground(getResources().getDrawable(buttonstyle1));
                preparingButton.setBackground(getResources().getDrawable(buttonstyle1));
                doneButton.setBackground(getResources().getDrawable(buttonstyle2));

                readyButton.setTextColor(R.color.white);
                preparingButton.setTextColor(R.color.white);

                updateStatus(orederObj.getOrderId(),"Ready");


            }
        });


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyButton.setBackground(getResources().getDrawable(buttonstyle1));
                preparingButton.setBackground(getResources().getDrawable(buttonstyle1));
                doneButton.setBackground(getResources().getDrawable(buttonstyle1));

                readyButton.setTextColor(R.color.white);
                preparingButton.setTextColor(R.color.white);
                doneButton.setTextColor(R.color.white);

                updateStatus(orederObj.getOrderId(),"Done");

            }
        });




    }


    //status- what you update
    private void updateStatus(String docId, final String status) {
        db.collection("orders")
                .document(docId)
                .update("Status", status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("This done", "DocumentSnapshot successfully updated!");
                        //Toast.makeText(getActivity().getApplicationContext(),foodList.get(position).getFoodName()+":Updated",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("This error", "Error updating document", e);
                        Toast.makeText(getActivity().getApplicationContext(), "Server issue", Toast.LENGTH_LONG).show();
                    }
                });
    }


    //this is to set the payment status
    private void updatePayment(String docId, final String payment) {
        db.collection("orders")
                .document(docId)
                .update("PaymentStatus",payment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("This done", "DocumentSnapshot successfully updated!");
                        //Toast.makeText(getActivity().getApplicationContext(),foodList.get(position).getFoodName()+":Updated",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("This error", "Error updating document", e);
                        Toast.makeText(getActivity().getApplicationContext(), "Server issue", Toast.LENGTH_LONG).show();
                    }
                });
    }

    //this is to set the payment status
    private void cancelOrder(String docId, final String cancel) {
        db.collection("orders")
                .document(docId)
                .update("Status",cancel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("This done", "DocumentSnapshot successfully updated!");
                        //Toast.makeText(getActivity().getApplicationContext(),foodList.get(position).getFoodName()+":Updated",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("This error", "Error updating document", e);
                        Toast.makeText(getActivity().getApplicationContext(), "Server issue", Toast.LENGTH_LONG).show();
                    }
                });
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
