package com.app.pakreformers.activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.pakreformers.R;
import com.app.pakreformers.info.Info;
import com.app.pakreformers.models.User;
import com.app.pakreformers.singletons.CurrentUserSingleton;
import com.app.pakreformers.utils.DialogUtils;
import com.app.pakreformers.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity implements Info {
    public static Activity context;
    public static String strEtPassword;
    EditText etPhone;
    EditText etFirstName;
    EditText etLastName;
    String strEtFirstName;
    String strEtLastName;
    String strEtPhone;
    Spinner spnAddress;
    Dialog dgLoading;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = this;
        initViews();
        initUserDetails();
        dgLoading = new Dialog(this);
        DialogUtils.initLoadingDialog(dgLoading);

        Utils.setCharValidation(etFirstName);
        Utils.setCharValidation(etLastName);

    }


    private void initUserDetails() {
        FirebaseDatabase.getInstance().getReference().child(NODE_USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {

                            ProfileActivity.this.user = user;
                            etPhone.setText(user.getStrEtPhone());
                            etFirstName.setText(user.getStrEtFirstName());
                            etLastName.setText(user.getStrEtLastName());
                            String[] myArray = getResources().getStringArray(R.array.provinces);
                            spnAddress.setSelection(Arrays.asList(myArray).indexOf(user.getAddress()));

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void castStrings() {
        strEtFirstName = etFirstName.getText().toString();
        strEtLastName = etLastName.getText().toString();
        strEtPhone = etPhone.getText().toString();
    }

    private void initViews() {
        etPhone = findViewById(R.id.et_phone);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        spnAddress = findViewById(R.id.spn_address);
        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(getColor(R.color.black));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void back(View view) {
        finish();
    }

    public void SignUp(View view) {
        castStrings();
        if (!Utils.validEt(etFirstName))
            return;

        if (!Utils.validEt(etLastName))
            return;

        if (!Utils.validEt(etPhone))
            return;

        user.setStrEtFirstName(strEtFirstName);
        user.setStrEtLastName(strEtLastName);
        user.setStrEtPhone(strEtPhone);
        user.setAddress(spnAddress.getSelectedItem().toString());
        initAuth(user);
    }

    private void initAuth(User userModel) {
        dgLoading.show();
        initData(userModel);
    }

    private void initData(User userModel) {
        dgLoading.show();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .setValue(userModel)
                .addOnCompleteListener(task -> {
                    dgLoading.dismiss();
                    if (task.isSuccessful()) {
                        CurrentUserSingleton.setInstance(userModel);
                        Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();
                        initUserDetails();
                    } else
                        Toast.makeText(this, "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                });
    }
}
