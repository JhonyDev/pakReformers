package com.app.pakreformers.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.pakreformers.R;
import com.google.android.material.card.MaterialCardView;

public class TypeRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView tvDriveTitle;
    TextView tvDriveDesc;
    TextView tv_name;
    TextView tv_message;
    TextView tvDriveType;
    TextView et_etAddress;
    LottieAnimationView anim;
    TextView tv_address;
    TextView tv_email;

    public TypeRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDriveDesc = itemView.findViewById(R.id.et_desc);
        tvDriveTitle = itemView.findViewById(R.id.et_title);
        tvDriveType = itemView.findViewById(R.id.et_type);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_message = itemView.findViewById(R.id.tv_message);
        et_etAddress = itemView.findViewById(R.id.et_etAddress);
        tv_address = itemView.findViewById(R.id.tv_address);
        tv_email = itemView.findViewById(R.id.tv_email);
        anim = itemView.findViewById(R.id.anim);
    }

}


