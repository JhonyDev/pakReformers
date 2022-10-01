package com.app.pakreformers.activities.volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.app.pakreformers.R;
import com.app.pakreformers.singletons.DriveSingleton;

public class ResourceManagement extends AppCompatActivity {

    CheckBox cbCloth;
    CheckBox cbMoney;
    CheckBox cbMed;
    CheckBox cbShelter;
    CheckBox cbFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_management);

        cbCloth = findViewById(R.id.cl);
        cbMoney = findViewById(R.id.mn);
        cbMed = findViewById(R.id.md);
        cbShelter = findViewById(R.id.sh);
        cbFood = findViewById(R.id.fd);

        cbMoney.setChecked(DriveSingleton.getInstance().getMoney().equals("Money"));
        cbFood.setChecked(DriveSingleton.getInstance().getFood().equals("Food"));
        cbShelter.setChecked(DriveSingleton.getInstance().getShelter().equals("Shelter"));
        cbMed.setChecked(DriveSingleton.getInstance().getMedicine().equals("Medicine"));
        cbCloth.setChecked(DriveSingleton.getInstance().getCloths().equals("Cloth"));

    }

    public void back(View view) {
        finish();
    }
}