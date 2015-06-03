package com.gimbal.sample;

/**
 * Created by akshaybapat on 12/7/14.
 */
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CustomJSONAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Product> products;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomJSONAdapter(Activity activity, List<Product> products) {
        this.activity = activity;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int location) {
        return products.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_json, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView brand = (TextView) convertView.findViewById(R.id.brand);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        //TextView upc = (TextView) convertView.findViewById(R.id.upc);
        //TextView seller = (TextView) convertView.findViewById(R.id.seller);
        //TextView price = (TextView) convertView.findViewById(R.id.price);


        // getting product data for the row
        Product  p = products.get(position);

        // thumbnail image
        thumbNail.setImageUrl(p.getThumbnailUrl(), imageLoader);

        // brand
        brand.setText( String.valueOf(p.getBrand()));

        // name
        name.setText( String.valueOf(p.getName()));

        // upc
        //upc.setText("UPC: " + String.valueOf(p.getUPC()));

        // seller
        //seller.setText("Seller: " + String.valueOf(p.getSeller()));

        // price
        //price.setText("Price: " + String.valueOf(p.getPrice()));

        return convertView;}

}

