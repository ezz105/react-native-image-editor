package com.ahmedadeltito.photoeditor;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import ui.photoeditor.R;

/**
 * Created by Ahmed Adel on 5/8/17.
 */

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Integer> colorPickerColors;
    private OnColorPickerClickListener onColorPickerClickListener;
    private int selectedPosition = 0;

    public ColorPickerAdapter(@NonNull Context context, @NonNull List<Integer> colorPickerColors) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.colorPickerColors = colorPickerColors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.color_picker_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        boolean isSelected = position == selectedPosition; // replace with your selection logic
        buildColorPickerView(holder.colorPickerView, colorPickerColors.get(position), isSelected);
    }

    @Override
    public int getItemCount() {
        return colorPickerColors.size();
    }

    private ShapeDrawable createShapeDrawable(String shapeType, int size, int color) {
        ShapeDrawable shapeDrawable;

        if ("diamond".equals(shapeType)) {
            Path path = new Path();
            path.moveTo(size / 2f, 0);
            path.lineTo(size, size / 2f);
            path.lineTo(size / 2f, size);
            path.lineTo(0, size / 2f);
            path.close();

            shapeDrawable = new ShapeDrawable(new PathShape(path, size, size));
        } else {
            shapeDrawable = new ShapeDrawable(new OvalShape());
        }

        shapeDrawable.setIntrinsicHeight(size);
        shapeDrawable.setIntrinsicWidth(size);
        shapeDrawable.setBounds(new Rect(0, 0, size, size));
        shapeDrawable.getPaint().setColor(color);

        return shapeDrawable;
    }

    private void buildColorPickerView(View view, int colorCode, boolean isSelected) {
        view.setVisibility(View.VISIBLE);
        String shapeType = isSelected ? "diamond" : "circle";
        ShapeDrawable biggerShape = createShapeDrawable(shapeType, 20, colorCode);
        ShapeDrawable smallerShape = createShapeDrawable(shapeType, 5, Color.WHITE);
        smallerShape.setPadding(10, 10, 10, 10);

        Drawable[] drawables = { smallerShape, biggerShape };

        LayerDrawable layerDrawable = new LayerDrawable(drawables);

        view.setBackgroundDrawable(layerDrawable);
    }

    public void setOnColorPickerClickListener(
        OnColorPickerClickListener onColorPickerClickListener
    ) {
        this.onColorPickerClickListener = onColorPickerClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        View colorPickerView;

        public ViewHolder(View itemView) {
            super(itemView);
            colorPickerView = itemView.findViewById(R.id.color_picker_view);
            itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (
                            onColorPickerClickListener != null
                        ) onColorPickerClickListener.onColorPickerClickListener(
                            colorPickerColors.get(getAdapterPosition())
                        );
                        selectedPosition = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                }
            );
        }
    }

    public interface OnColorPickerClickListener {
        void onColorPickerClickListener(int colorCode);
    }
}
