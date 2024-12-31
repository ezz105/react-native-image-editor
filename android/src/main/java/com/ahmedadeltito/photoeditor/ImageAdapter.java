package com.ahmedadeltito.photoeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ui.photoeditor.R;

/**
 * Created by Ahmed Adel on 5/4/17.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Bitmap> imageBitmaps;
    private LayoutInflater inflater;
    private OnImageClickListener onImageClickListener;

    public ImageAdapter(@NonNull Context context, @NonNull List<Bitmap> imageBitmaps) {
        this.inflater = LayoutInflater.from(context);
        this.imageBitmaps = imageBitmaps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_photo_edit_image_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.imageView.getContext())
            .load(imageBitmaps.get(position))
            .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageBitmaps.size();
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.fragment_photo_edit_image_iv);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onImageClickListener != null)
                        onImageClickListener.onImageClickListener(imageBitmaps.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnImageClickListener {
        void onImageClickListener(Bitmap image);
    }

    // Example usage
    public static void setupRecyclerView(RecyclerView recyclerView, Context context, List<String> imagePaths) {
        List<Bitmap> imageBitmaps = new ArrayList<>();
        BitmapFactory.Options options = new BitmapFactory.Options(); // Add necessary options if needed
        for (String path : imagePaths) {
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            imageBitmaps.add(bitmap);
        }
        ImageAdapter adapter = new ImageAdapter(context, imageBitmaps);
        recyclerView.setAdapter(adapter);
    }
}
