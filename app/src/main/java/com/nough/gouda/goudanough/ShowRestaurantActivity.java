package com.nough.gouda.goudanough;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.nough.gouda.goudanough.beans.Restaurant;
import com.nough.gouda.goudanough.fragments.RestaurantListView;

public class ShowRestaurantActivity extends AppCompatActivity implements RestaurantListView.OnRestaurantListViewListener{
    private Restaurant restaurant;
    // Handle to View objects
    private ImageView featured_image;
    private TextView name;
    private TextView cuisine;
    private TextView price_range; // this could turn into an image view.
    private TextView url;
    private TextView telephone;
    private static final String TAG = "Restaurant Info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_restaurant);

        // init the view objects.
        initView();

        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        RestaurantListView list = new RestaurantListView();
        ft.replace(R.id.show_resto_list_container, list);
        ft.commit();
        // Dummy dataset for the list view adapter
        // should display the recent viewed restaurants.
        Restaurant[] rs = new Restaurant[2];
        rs[0] = new Restaurant("My resto", "https://google.com", "food","514-559-7108",2, 2.2,2.1,"http://i.imgur.com/BTyyfVQ.jpg");
        rs[1] = new Restaurant("My resto2", "https://google.com", "food","514-559-7108",2, 2.2,2.1,"http://i.imgur.com/BTyyfVQ.jpg");
        RestaurantListViewAdapter adapter = new RestaurantListViewAdapter(this,R.layout.restaurant_listview,rs);
        list.setAdapter(adapter);
        restaurant = getIntent().getParcelableExtra("selected_restaurant");
        displayRestaurant();
        Log.d(TAG,restaurant.getName());
    }

    /**
     * private helper method to initialize the view objects.
     */
    private void initView() {
        this.featured_image = (ImageView)findViewById(R.id.info_featured_image);
        this.name = (TextView) findViewById(R.id.info_name);
        this.cuisine = (TextView) findViewById(R.id.info_cuisine);
        this.name = (TextView) findViewById(R.id.info_name);
        this.price_range = (TextView) findViewById(R.id.info_price_range);
        this.url = (TextView) findViewById(R.id.info_url);
        this.telephone = (TextView) findViewById(R.id.info_telephone_number);
    }

    private void displayRestaurant(){

        featured_image.setImageResource(R.drawable.taco_dummy);// set this to the drawable that will get downloaded from network io.
        name.setText(restaurant.getName());
        cuisine.setText(cuisine.getText() + ": " + restaurant.getCuisine());
        // check price range and set appropriate dollar signs for it.
        price_range.setText(convertPriceRangeToDollarSigns(restaurant.getPrice_range()));
        url.setText(restaurant.getUrl());
        telephone.setText(restaurant.getPhone_numbers());
    }

    @Override
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
       // Log.d(TAG, restaurant.getName());
    }

    private String convertPriceRangeToDollarSigns(int price){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < price; i++){
            s.append('$');
        }
        return s.toString();
    }


}
