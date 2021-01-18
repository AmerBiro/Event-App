package dk.events.a6.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import dk.events.a6.R;
import dk.events.a6.home.Chat;
import dk.events.a6.mvvm.model.UserModel;

public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.ViewHolder>{

    private Context mContext;
    private List<UserModel> users;
    String theLastMessage;

    public UserChatAdapter(Context mContext, List<UserModel> users) {
        this.mContext = mContext;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_chat_list, parent, false);
        return new UserChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserModel user = users.get(position);
        holder.textName.setText(user.getFirst_name());

        if (user.getImage_url_account().equals("default")){
            holder.profileImageView.setImageResource(R.drawable.profile);
        }else {
            Glide.with(mContext).load(user.getImage_url_account()).into(holder.profileImageView);
        }

        lastMessage(user.getUserId(), holder.last_message);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        //todo go to message view
//                Intent intent = new Intent(mContext, MessageActivity.class);
//                intent.putExtra("userid", user.getId());
//                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public  class  ViewHolder extends RecyclerView.ViewHolder{

        public TextView textName;
        public ImageView profileImageView;
        private TextView last_message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName= itemView.findViewById(R.id.user_single_name);
            profileImageView = itemView.findViewById(R.id.user_single_image);
            last_message = itemView.findViewById(R.id.last_message);

        }
    }


    private void  lastMessage(String userid, TextView last_msg ){
        theLastMessage = "default";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ChatModel chat = dataSnapshot.getValue(ChatModel.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid)
                            ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid()) ){

                        theLastMessage = chat.getMessage();
                    }
                }
                switch (theLastMessage){
                    case "default":
                        last_msg.setText("i wish to join your event");
                        break;
                    default:
                        last_msg.setText(theLastMessage);
                        break;
                }

                theLastMessage = "default";

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
