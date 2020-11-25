package dk.events.a6.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dk.events.a6.R;
import dk.events.a6.models.ChatList;

//https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatList> chatLists;
    private Context context;


    public ChatAdapter(List<ChatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_chat_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        ChatList chatList = chatLists.get(position);
        holder.name.setText(chatList.getName());
        holder.date.setText(chatList.getDate());
        holder.description.setText(chatList.getDescription());
        holder.profileImage.setImageResource(chatList.getImage());
    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, description, date;
        private CircleImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.image_profile);
            name=itemView.findViewById(R.id.name_text);
            description=itemView.findViewById(R.id.profileDescription);
            date=itemView.findViewById(R.id.date_text);
        }
    }



}
