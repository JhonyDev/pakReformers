package com.app.pakreformers.utils;

import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Utils {

    public static boolean validEt(EditText etUserName) {
        String strEtUsername = etUserName.getText().toString();
        if (strEtUsername.isEmpty()) {
            etUserName.setError("Field Empty");
            return false;
        }
        return true;
    }

    public static DatabaseReference getReference() {
        return FirebaseDatabase.getInstance().getReference();
    }
}
