package com.nough.gouda.goudanough;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nough.gouda.goudanough.beans.Restaurant;

/**
 * Created by 1432581 on 11/23/2016.
 */

public class RestaurantListViewAdapter extends ArrayAdapter<Restaurant> {
    private Context context;
    private int resource;
    private Restaurant[] data;

    public RestaurantListViewAdapter(Context context, int resource, Restaurant[] data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    public class ViewHolder{
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder = null;
        View row = convertView;
        if(row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.tv = (TextView)row.findViewById(R.id.list_resto_name);
            holder.img = (ImageView)row.findViewById(R.id.list_featured_img);
            row.setTag(holder);
        }else{
            holder = (ViewHolder)row.getTag();
        }
        Restaurant restaurant = data[position];
        holder.img.setImageResource(R.drawable.taco_dummy);
        holder.tv.setText(restaurant.getName());

        return row;
    }

    public void setDataset(Restaurant[] rs){
        this.data = rs;
        notifyDataSetChanged();
    }
}
