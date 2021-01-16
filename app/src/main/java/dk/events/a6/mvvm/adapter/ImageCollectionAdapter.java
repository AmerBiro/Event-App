package dk.events.a6.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import dk.events.a6.R;
import dk.events.a6.mvvm.model.ImageCollectionModel;

public class ImageCollectionAdapter extends RecyclerView.Adapter <ImageCollectionAdapter.ImageCollectionViewHolder> {

    private List<ImageCollectionModel> imageCollectionModels;
    private OnImageCollectionItemClicked onImageCollectionItemClicked;

    public ImageCollectionAdapter(OnImageCollectionItemClicked onImageCollectionItemClicked) {
        this.onImageCollectionItemClicked = onImageCollectionItemClicked;
    }

    public void setImageCollectionModels(List<ImageCollectionModel> imageCollectionModels) {
        this.imageCollectionModels = imageCollectionModels;
    }

    @NonNull
    @Override
    public ImageCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_account_overview_image_collections, parent, false);
        return new ImageCollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageCollectionViewHolder holder, int position) {


        Glide
                .with(holder.itemView.getContext())
                .load(imageCollectionModels.get(position).getImage_url())
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_image_24)
                .into(holder.imageCollection);
    }

    @Override
    public int getItemCount() {
        if(imageCollectionModels == null){
            return 0;
        } else {
            return imageCollectionModels.size();
        }
    }


    public class ImageCollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageCollection;

        public ImageCollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCollection = itemView.findViewById(R.id.single_item_image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onImageCollectionItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnImageCollectionItemClicked {
        public void onItemClicked(int position);
    }
}
