package com.restfood.myapplication.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.restfood.myapplication.Add_FoodItem;
import com.restfood.myapplication.EditFoodList;
import com.restfood.myapplication.EditProfile;
import com.restfood.myapplication.MainActivity;
import com.restfood.myapplication.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private Button button_add_food;
    private TextView edit_profile;

    private Button buttonViewFood;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);

        //here root
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        final TextView textView = root.findViewById(R.id.shop_name);

        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        //here initializing variable
        button_add_food=root.findViewById(R.id.button_add_food);
        edit_profile=root.findViewById(R.id.edit_profile);
        buttonViewFood=root.findViewById(R.id.button_view_food);

        button_add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), Add_FoodItem.class);
                startActivity(intent2);
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });



        buttonViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), EditFoodList.class);
                startActivity(intent);
            }
        });





        return root;
    }


    private void editProfile()
    {
        Intent intent=new Intent(getActivity(), EditProfile.class);
        startActivity(intent);
    }





}