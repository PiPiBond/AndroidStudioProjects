package com.example.androidsession_04.list_view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidsession_04.R;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit> {

    private Activity context;
    private int resourceId;
    private List<Fruit> data;

    public FruitAdapter(@NonNull Activity context, int resourceId, @NonNull List<Fruit> data) {
        super(context, resourceId, data);
        this.context = context;
        this.resourceId = resourceId;
        this.data = data;
    }

    class ViewHolder {

        private ImageView fruitImage;

        private TextView fruitName;

        public ViewHolder(ImageView fruitImage, TextView fruitName) {
            this.fruitImage = fruitImage;
            this.fruitName = fruitName;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ImageView fruitImage = view.findViewById(R.id.fruitImage);
            TextView fruitName = view.findViewById(R.id.fruitName);
            viewHolder = new ViewHolder(fruitImage, fruitName);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }

        Fruit fruit = getItem(position);
        if (fruit != null) {
            viewHolder.fruitImage.setImageResource(fruit.getImageId());
            viewHolder.fruitName.setText(fruit.getName());
        }
        return view;
    }
}
