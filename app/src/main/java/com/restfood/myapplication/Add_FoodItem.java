package com.restfood.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Add_FoodItem extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private int price;      //this is to use for database


    //creating variables
    //this is for food
    private TextInputEditText food_name_edit_text;
    private TextInputLayout food_name_layout;

    private TextInputEditText food_price_edit_text;
    private TextInputLayout food_price_layout;

    private TextInputEditText min_duration_text;
    private TextInputLayout min_duration_layout;
    private TextInputEditText max_duration_text;

    private TextInputEditText cat_text;

    private TextInputEditText des_text;
    private TextInputLayout des_layout;

    //for image
    private ImageView img;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    ///this data for firestore
    String uid;     //this is given by firestore & shop_id

    //this is for check veg or non veg
    boolean isVeg;


    ArrayList<String> catList=new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        setContentView(R.layout.activity_add__food_item);
        setTitle("Add Food Item");



        //getting user id using auth class
        uid=new Auth().getUId();



        //assigning xml component to that
        food_name_edit_text=findViewById(R.id.food_name_text);
        food_name_layout=findViewById(R.id.food_name_layout);

        food_price_edit_text=findViewById(R.id.food_price_text);
        food_price_layout=findViewById(R.id.food_price_layout);

        min_duration_text=findViewById(R.id.min_pre_time_text);
        min_duration_layout=findViewById(R.id.min_pre_time_layout);
        max_duration_text=findViewById(R.id.max_pre_time_text);
        //max_duration_layout=findViewById(R.id.max_pre_time_layout);

        cat_text=findViewById(R.id.food_cat_text);


        des_text=findViewById(R.id.food_des_text);
        des_layout=findViewById(R.id.food_des_layout);


        img=findViewById(R.id.food_image);

        getCat();       //this is requesting to get



        //this call when food field change
        //for every change
        food_name_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //not doing now
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFoodName();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //not doing now
            }
        });
    }





    //this is validation for both
    //max and min
    //if max or min one can be null
    //didn't check for  becuse for ready madefood
    private boolean checkDuration()
    {
        String min_time=min_duration_text.getText().toString();
        String max_time=max_duration_text.getText().toString();

        if(!min_time.isEmpty() | !max_time.isEmpty())
        {
            min_duration_layout.setError(null);
            return true;
        }
        else
        {
            min_duration_layout.setError("Enter Duration");
            return false;
        }

    }

    //validation checking for price
    private boolean checkPrice()
    {
        String food_price=food_price_edit_text.getText().toString();

        if(food_price.length()==0)
        {
            food_price_layout.setError("Price is Empty");
            return false;
        }
        else
        {

            try
            {
                price=Integer.parseInt(food_price);
                if(price>0)
                {
                    food_price_layout.setError(null);
                    return true;

                }
                else
                {
                    food_price_layout.setError("Food Price is 0");
                    return false;

                }
            }
            catch (NumberFormatException e)
            {
                food_price_layout.setError("Dandanaka done");
                return false;
            }
        }
    }


    //validation checking for food name
    private boolean checkFoodName()
    {
        String food_name=food_name_edit_text.getText().toString().trim();

        if(food_name.length()==0)
        {
            food_name_layout.setError("Length is 0");
            return false;
        }
        else
        {
            if(food_name.length()<4)
            {
                food_name_layout.setError("Food Name is too short");
            }
            else
            {
                food_name_layout.setError(null);
            }

            return true;
        }
    }


    private boolean checkDescription()
    {
        String data=des_text.getText().toString();
        if(data==null)
        {
            des_layout.setError("Empty");
            return false;
        }
        else
        {
            return true;
        }
    }

    //this is to convert string from textfield to int
    private int convertToInt(String num)
    {
        if(num==null)
        {
            return 0;
        }
        else
        {
            return Integer.parseInt(num);
        }
    }


    //this work for button click
    //this add data to database
    public void onSubmit(View v)
    {
        if(checkFoodName() & checkPrice() & checkDuration() & checkDescription())
        {
            addFood();
            setCat(cat_text.getText().toString());
        }

    }




    //this create a new doc for a cat
    private void getCat() {
//        Map<String, Object> cat = new HashMap<>();
//        cat.put("name", name);

        db.collection("shop")
                .document(new Auth().getUId())
                .collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //this is temp list to get from database
                            List<FoodData> list = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("this_id", document.getId() + " => " + document.getData());

                                String cat=document.getString("Name");
                                catList.add(cat);
                            }

                            //Toast.makeText(getApplicationContext(),String.valueOf(catList.size()),Toast.LENGTH_LONG).show();


                        } else {


                        }
                    }
                });
    }


    private void setCat(String str)
    {
        boolean flag=false;

        int i;
        //this is to check if there is a cat already
        for(i=0;i<catList.size();i++)
        {
            if(str.equals(catList.get(i)))
            {
                flag=true;

            }
        }

        if(!flag)
        {
            Map<String, Object> cat = new HashMap<>();
            cat.put("Name", str);

            db.collection("shop")
                    .document(new Auth().getUId())
                    .collection("Category")
                    .add(cat)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Log.w(TAG, "Error adding document", e);
                            Toast.makeText(getApplicationContext(),"Erroe adding doc", Toast.LENGTH_SHORT).show();
                        }
                    });



        }






    }


    private void addFood()
    {
        FoodData food=new FoodData(food_name_edit_text.getText().toString(),cat_text.getText().toString(),convertToInt(food_price_edit_text.getText().toString()),convertToInt(min_duration_text.getText().toString()),convertToInt(max_duration_text.getText().toString()),isVeg,false,des_text.getText().toString()  );

        db.collection("shop")
                .document(uid)
                .collection("FoodList")
                .add(food)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        //Toast.makeText(getApplicationContext(),"DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_SHORT).show();

                        //here we call this function
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(getApplicationContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile(documentReference.getId());
                    }


                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.w(TAG, "Error adding document", e);
                                Toast.makeText(getApplicationContext(),"Erroe adding doc", Toast.LENGTH_SHORT).show();
                            }
                        });

    }



        //coped from android studio doc have to change
        //just for desiging
        public void onRadioButtonClicked(View view) {
            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();

            //Toast.makeText(getApplicationContext(),String.valueOf(checked), Toast.LENGTH_SHORT).show();

            // Check which radio button was clicked
            switch(view.getId()) {
                case R.id.radioButton_veg:

                    if (checked)
                        // Pirates are the best
                        this.isVeg=true;
                        break;
                case R.id.radioButton_non_veg:
                    if (checked)
                        // Ninjas rule
                        this.isVeg=false;
                        break;
            }

            //Toast.makeText(getApplicationContext(),String.valueOf(isVeg), Toast.LENGTH_SHORT).show();

        }

        public void onCancel(View view)
        {
            finish();
        }





    //this method creates dialog box
    //to conform message after sucess full add
    //need to complete this
    public void openDialog() {
        AddFoodDialog addfoodDialog = new AddFoodDialog();
        addfoodDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void openFileChooser(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            //img.setImageURI(mImageUri);
            Glide.with(getApplicationContext())
                    .load(mImageUri)
                    .into(img);
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(final String docId) {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            final String ur=fileReference.getDownloadUrl().toString();

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            Toast.makeText(getApplicationContext(), "Food Updated", Toast.LENGTH_LONG).show();
                            setUrl(docId,ur);
                            finish();


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUrl(String docId,String url)
    {
        db.collection("shop")
                .document(new Auth().getUId())
                .collection("FoodList")
                .document(docId)
                .update("image",url)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("This done", "DocumentSnapshot successfully updated!");
                        finish();
                        //Toast.makeText(getActivity().getApplicationContext(),foodList.get(position).getFoodName()+":Updated",Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("This error", "Error updating document", e);
                        //Toast.makeText(getApplicationContext(), "Server issue", Toast.LENGTH_LONG).show();
                    }
                });

    }
}
