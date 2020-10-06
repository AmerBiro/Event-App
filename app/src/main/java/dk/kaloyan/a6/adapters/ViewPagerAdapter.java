package dk.kaloyan.a6.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dk.kaloyan.a6.R;
import dk.kaloyan.a6.models.MyEventViewModel;
import dk.kaloyan.a6.models.TabViewModel;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>{
    public List<TabViewModel> tabVMs = new ArrayList<>();
    private Context context;

    public ViewPagerAdapter(List<TabViewModel> tabVMs, Context context){
        this.tabVMs = tabVMs;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_pager, parent, false);
        return new ViewPagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {

        ListView listView = holder.itemView.findViewById(R.id.my_event_list);
        /*List<MyEventViewModel> events = new ArrayList<>();
        for(int i=0;i<30;i++) {
            events.add(MyEventViewModel.Builder.newInstance()
                    .withImageDrawableId(R.drawable.ic_launcher_background)
                    .withTitle("Title " + i)
                    .withDescription("Description " + i)
                    .build());
        }*/
        listView.setAdapter(new MyEventsAdapter(context, this.tabVMs.get(position).events));



        //ImageView imageView = holder.itemView.findViewById(R.id.ivImage);
        //imageView.setImageResource(this.tab1AndTab2.get(position));
    }

    @Override
    public int getItemCount() {
        return tabVMs.size();
    }

    class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        ViewPagerViewHolder(View itemView){
            super(itemView);

        }
    }
}
