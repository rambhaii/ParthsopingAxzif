package com.app.axzif.CardView.ui;

import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.axzif.R;


public class FavouriteFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    RelativeLayout rlCancel;


    RecyclerView recycler_view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cardfragment, container, false);

        getIds(v);

        return v;
    }

    private void getIds(View v) {

        rlCancel = (RelativeLayout)v.findViewById(R.id.rl_close);
        recycler_view = (RecyclerView)v.findViewById(R.id.recycler_view);

        setListners();

    }

    private void setListners() {

        rlCancel.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v==rlCancel)
        {
            dismiss();
        }
    }

}
