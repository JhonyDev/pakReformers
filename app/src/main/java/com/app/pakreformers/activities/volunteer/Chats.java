package com.app.pakreformers.activities.volunteer;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pakreformers.R;
import com.app.pakreformers.adapters.TypeRecyclerViewAdapter;
import com.app.pakreformers.info.Info;
import com.app.pakreformers.models.MessagePojo;
import com.app.pakreformers.models.Super;
import com.app.pakreformers.singletons.CurrentUserSingleton;
import com.app.pakreformers.singletons.DriveSingleton;
import com.app.pakreformers.utils.DialogUtils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chats extends AppCompatActivity implements Info {
    EditText etMessage;
    String strEtMessage;
    Dialog loadingDialog;
    List<Super> superList;
    TextView tvUsername;
    RecyclerView recyclerView;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resourse_management);
        if (DriveSingleton.getInstance() == null) {
            Toast.makeText(this, "Drive not selected", Toast.LENGTH_SHORT).show();
            finish();
        }
        superList = new ArrayList<>();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        etMessage = findViewById(R.id.messageEditText);
        tvUsername = findViewById(R.id.tv_username);
        tvUsername.setText(DriveSingleton.getInstance().getTitle());
        initRv();
        getChatHistory();
    }

    private void initRv() {
        recyclerView = findViewById(R.id.recyclerview);
        superList = new ArrayList<>();
        typeRecyclerViewAdapter
                = new TypeRecyclerViewAdapter(this, superList, RV_TYPE_CHATS);
        recyclerView.setAdapter(typeRecyclerViewAdapter);
    }

    private void getChatHistory() {
        if (!loadingDialog.isShowing())
            loadingDialog.show();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_CHATS)
                .child(DriveSingleton.getInstance().getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingDialog.dismiss();
                        superList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            MessagePojo messagePojo = child.getValue(MessagePojo.class);
                            superList.add(messagePojo);
                        }
                        typeRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void back(View view) {
        finish();
    }

    public void sendMessage(View view) {
        castStrings();
        if (strEtMessage.isEmpty())
            return;

        etMessage.clearFocus();
        etMessage.setText("");
        int i;
        try {
            MessagePojo messagePojo1 = (MessagePojo) superList.get(superList.size() - 1);
            i = Integer.parseInt(messagePojo1.getMessageId()) + 1;
        } catch (Exception e) {
            i = superList.size();
        }
        String id = String.valueOf(i);

        MessagePojo messagePojo = new MessagePojo(id,
                DriveSingleton.getInstance().getId(), DriveSingleton.getInstance().getTitle()
                , strEtMessage, FirebaseAuth.getInstance().getCurrentUser().getUid(), CurrentUserSingleton.getInstance().getStrEtFirstName());

        FirebaseDatabase.getInstance().getReference()
                .child(NODE_CHATS)
                .child(DriveSingleton.getInstance().getId())
                .child(id)
                .setValue(messagePojo).
                addOnCompleteListener(this::initRvScroll);
    }

    private void initRvScroll(Task<Void> task) {
        recyclerView.scrollBy(0, 3000);
    }

    private void castStrings() {
        strEtMessage = etMessage.getText().toString();
    }
}