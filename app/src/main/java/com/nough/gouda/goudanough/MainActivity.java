package com.nough.gouda.goudanough;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nough.gouda.goudanough.beans.Restaurant;
import com.nough.gouda.goudanough.fragments.Header;
import com.nough.gouda.goudanough.fragments.Navigation;
import com.nough.gouda.goudanough.fragments.RestaurantListView;
import com.nough.gouda.goudanough.fragments.TipFragment;

public class MainActivity extends AppCompatActivity implements Navigation.OnNavigationListener, RestaurantListView.OnRestaurantListViewListener {
    private static final String TAG = "Main Activity";
    private RestaurantListViewAdapter adapter;
    private Restaurant[] favourite_restaurants;
    private Restaurant selected_restaurant;
    // Fragment objects. Should be better named tbh.
    private Header header;
    private Navigation nav;
    private RestaurantListView list;
    private TipFragment tip;
    private TextView list_title;
    private FragmentManager manager;
    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_title = (TextView)findViewById(R.id.main_listview_title);

        manager = getFragmentManager();
        ft = manager.beginTransaction();

        // create the fragments to be used in this layout
        header = new Header();
        nav = new Navigation();
        list = new RestaurantListView();
        tip = new TipFragment();
        if(savedInstanceState == null){
            // add the fragments to the linear layout
            ft.add(R.id.main_fragment_container,header, "header");
            ft.add(R.id.main_fragment_container,nav,"navigation_menu");
            ft.add(R.id.list_container, list, "list_view");
            //ft.add(R.id.tip_clac_main, tip,"tip calc");
            ft.commit();
        }

        // Dummy dataset for the list view adapter
        // should display the recent viewed restaurants.
        Restaurant[] rs = new Restaurant[2];
        rs[0] = new Restaurant("My resto", "https://google.com", "food","514-559-7108",2, 2.2,2.1,"http://i.imgur.com/BTyyfVQ.jpg");
        rs[1] = new Restaurant("My resto2", "https://google.com", "food","514-559-7108",2, 2.2,2.1,"http://i.imgur.com/BTyyfVQ.jpg");
        adapter = new RestaurantListViewAdapter(this,R.layout.restaurant_listview,rs);

        list.setAdapter(adapter);
    }

    @Override
    public void setFavourites(Restaurant[] favourite_restaurants) {
        this.favourite_restaurants = favourite_restaurants;
        adapter.setDataset(favourite_restaurants);
        list_title.setText("Favourites");
    }

    @Override
    public void launchTipCalc(TipFragment tip) {
        Log.d("TIP|NAV","ACCESSING TIP CALC");
        FragmentTransaction ft2 = manager.beginTransaction();
        ft2.replace(R.id.list_container, tip,"tip calc");
        ft2.addToBackStack("list_view");
        ft2.commit();
    }

    @Override
    public void setRestaurant(Restaurant restaurant) {
        this.selected_restaurant = restaurant;
    }
}
