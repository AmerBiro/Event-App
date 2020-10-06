package dk.kaloyan.a6.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dk.kaloyan.a6.R;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>{
    List<Integer> tab1AndTab2 = new ArrayList<>();
    public ViewPagerAdapter(List<Integer> tab1AndTab2){
        this.tab1AndTab2 = tab1AndTab2;
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_pager, parent, false);
        return new ViewPagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
        ImageView imageView = holder.itemView.findViewById(R.id.ivImage);
        imageView.setImageResource(this.tab1AndTab2.get(position));
    }

    @Override
    public int getItemCount() {
        return tab1AndTab2.size();
    }

    class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        ViewPagerViewHolder(View itemView){
            super(itemView);

        }
    }
}
