package com.app.pakreformers.activities.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.pakreformers.R;
import com.app.pakreformers.activities.volunteer.DrivesDashboard;
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

import java.util.Objects;

/**
 * Email = shekii.shakiir.1@gmail.com
 * Password = 03346583522.
 */

public class AuthLoginActivity extends AppCompatActivity implements Info {

    public static Activity context;
    EditText etEmail;
    EditText etPassword;
    String strEtEmail;
    String strEtPassword;
    boolean isPassVisible = false;
    TextView forgotPassword;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        forgotPassword = findViewById(R.id.forgot_password);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_pass);

        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            loadingDialog.show();
            parseUserData();
        }
        forgotPassword.setOnClickListener(view -> {
            String strEmail = etEmail.getText().toString().trim();
            if (strEmail.isEmpty()) {
                etEmail.setError("Please enter email first.");
            } else {
                Utils.sendPasswordResetEmail(AuthLoginActivity.this, strEmail);
            }
        });
    }

    private void parseUserData() {
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingDialog.dismiss();
                        User userModel = snapshot.getValue(User.class);
                        if (userModel == null) {
                            Toast.makeText(AuthLoginActivity.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        CurrentUserSingleton.setInstance(userModel);
                        startActivity(new Intent(AuthLoginActivity.this, DrivesDashboard.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


    public void signUp(View view) {
        startActivity(new Intent(this, AuthRegistration.class));
    }

    public void showPassword(View view) {
        if (!isPassVisible) {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isPassVisible = true;
        } else {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isPassVisible = false;
        }

    }


    private void castStrings() {
        strEtEmail = etEmail.getText().toString().trim();
        strEtPassword = etPassword.getText().toString();
    }

    private boolean isEverythingValid() {
        if (!Utils.validEt(etEmail))
            return false;
        return Utils.validEt(etPassword);
    }

    public void Login(View view) {
        castStrings();
        if (!isEverythingValid())
            return;
        loadingDialog.show();
        initSignIn();
    }

    private void initSignIn() {
        loadingDialog.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(strEtEmail, strEtPassword)
                .addOnCompleteListener(task -> {
                    Log.i(TAG, "initSignIn: RESPONDED");
                    loadingDialog.dismiss();
                    if (task.isSuccessful()) {
                        initUserData();
                    } else {
                        if (task.getException() != null)
                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "initSignIn: " + task.getException().getMessage());
                    }
                });
    }

    private void initUserData() {
        loadingDialog.show();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingDialog.dismiss();
                        User user = snapshot.getValue(User.class);
                        if (user == null) {
                            Toast.makeText(AuthLoginActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        CurrentUserSingleton.setInstance(user);
                        startActivity(new Intent(AuthLoginActivity.this, DrivesDashboard.class));
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}