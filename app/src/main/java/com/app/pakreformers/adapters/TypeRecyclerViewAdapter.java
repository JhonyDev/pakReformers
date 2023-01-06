package com.app.pakreformers.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.pakreformers.R;
import com.app.pakreformers.activities.volunteer.DriveDetails;
import com.app.pakreformers.info.Info;
import com.app.pakreformers.models.Drive;
import com.app.pakreformers.models.MessagePojo;
import com.app.pakreformers.models.Participant;
import com.app.pakreformers.models.Super;
import com.app.pakreformers.models.User;
import com.app.pakreformers.singletons.DriveSingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class TypeRecyclerViewAdapter extends RecyclerView.Adapter<TypeRecyclerViewHolder> implements Info {

    Context context;
    List<Super> listInstances;
    int type;

    public TypeRecyclerViewAdapter(Context context, List<Super> listInstances, int type) {
        this.context = context;
        this.listInstances = listInstances;
        this.type = type;
    }

    @NonNull
    @Override
    public TypeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(context);
        View view;
        view = li.inflate(R.layout.drive_layout, parent, false);

        if (type == RV_TYPE_CHATS)
            view = li.inflate(R.layout.item_chat, parent, false);

        if (type == RV_TYPE_PARTICIPANTS)
            view = li.inflate(R.layout.item_participant, parent, false);

        return new TypeRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TypeRecyclerViewHolder holder, int position) {
        if (type == RV_TYPE_PARTICIPANTS) {
            initParticipant(holder, position);
            return;
        }
        if (type == RV_TYPE_CHATS) {
            initChats(holder, position);
            return;
        }

        initDocuments(holder, position);
    }

    private void initParticipant(TypeRecyclerViewHolder holder, int position) {
        Participant participant = (Participant) listInstances.get(position);
        FirebaseDatabase.getInstance().getReference().child(NODE_USERS).child(participant.getParticipant())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            String fullName = user.getStrEtFirstName() + " " + user.getStrEtLastName();
                            holder.tv_name.setText(fullName);
                            holder.tv_address.setText(user.getAddress() + " - " + user.getStrEtPhone());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initChats(TypeRecyclerViewHolder holder, int position) {
        MessagePojo messagePojo = (MessagePojo) listInstances.get(position);
        holder.tv_name.setText(messagePojo.getAuthorName());
        holder.tv_message.setText(messagePojo.getMessageText());
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messagePojo.getAuthorId())) {
            holder.tv_name.setTextColor(context.getColor(R.color.purple_700));
            holder.tv_name.setText(messagePojo.getAuthorName() + " - (Me)");
        } else
            holder.tv_name.setTextColor(context.getColor(R.color.blue));
    }

    private void initDocuments(TypeRecyclerViewHolder holder, int position) {
        Drive drive = (Drive) listInstances.get(position);
        holder.tvDriveDesc.setText(drive.getDescription());
        holder.tvDriveTitle.setText(drive.getTitle());
        holder.tvDriveType.setText(drive.getType());
        holder.et_etAddress.setText(drive.getAddress());
        Participant participant = new Participant(drive.getId(), FirebaseAuth.getInstance().getCurrentUser().getUid());
        holder.anim.setOnClickListener(view -> {
            DriveSingleton.setSelectedDrive(drive);
            FirebaseDatabase.getInstance().getReference().child(NODE_PARTICIPANTS).child(drive.getId())
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(participant).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Drive joined", Toast.LENGTH_SHORT).show();
                            DriveSingleton.setSelectedDrive(drive);
                            context.startActivity(new Intent(context, DriveDetails.class));
                        }
                    });
        });

    }

    @Override
    public int getItemCount() {
        return listInstances.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
