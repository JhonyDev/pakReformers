package com.app.pakreformers.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pakreformers.R;

public class TypeRecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView tvDriveTitle;
    TextView tvDriveDesc;
    TextView tv_name;
    TextView tv_message;
    TextView tvDriveType;
    CardView touch_field;

    public TypeRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tvDriveDesc = itemView.findViewById(R.id.et_desc);
        tvDriveTitle = itemView.findViewById(R.id.et_title);
        tvDriveType = itemView.findViewById(R.id.et_type);
        touch_field = itemView.findViewById(R.id.touch_field);
        tv_name = itemView.findViewById(R.id.tv_name);
        tv_message = itemView.findViewById(R.id.tv_message);
    }

}


