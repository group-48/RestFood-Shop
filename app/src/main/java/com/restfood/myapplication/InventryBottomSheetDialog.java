package com.restfood.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class InventryBottomSheetDialog extends BottomSheetDialogFragment {
  private TextView increaseTextView;
  private TextView decreaseTextView;

  private EditText itemEditText;
  private EditText qtyEditText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.bottom_sheet_layout,container,false);

        increaseTextView=v.findViewById(R.id.increace);
        decreaseTextView=v.findViewById(R.id.reduse);
        itemEditText=v.findViewById(R.id.item_edit_text);
        qtyEditText=v.findViewById(R.id.qty_edit_text);

        


        return v;
    }


}
