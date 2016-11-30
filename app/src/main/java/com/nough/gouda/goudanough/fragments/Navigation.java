package com.nough.gouda.goudanough.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.nough.gouda.goudanough.R;
import com.nough.gouda.goudanough.beans.Restaurant;
import com.nough.gouda.goudanough.RestaurantInfo;
import com.nough.gouda.goudanough.databases.DBHelper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //Navigation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class Navigation extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "Navigation Fragment";
    private Restaurant[] favourites;
    private Restaurant[] nearby_restaurants;
    private ImageButton[] nav_buttons = new ImageButton[6];
    private DBHelper dao;

    // interface object for communicating with parent activity.
    private OnNavigationListener mListener;

    /**
     * Interface used to communicate with the parent activity.
     * TODO: add methods to the interface that pass required data to the parent activity.
     */
    public interface OnNavigationListener{
        void setFavourites(Restaurant[] favourite_restaurants);
        void launchTipCalc(TipFragment tip);
    }

    public Navigation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().deleteDatabase("goudanough.db");
        dao = dao.getDBHelper(getActivity());
        dao.insertNewUser("Ryan","H3W1N1","wolrd","railanderson@gmail.com"); //works
        dao.insertNewUser("Evan","H3W1N1","wolrd","railanderson@gmail.com");
        Restaurant resto = new Restaurant("My fav resto", "https://google.com", "food","514-559-7108",2, 2.2,2.1,"http://i.imgur.com/BTyyfVQ.jpg");
        Restaurant resto2 = new Restaurant("My fav resto2", "https://google.com", "food","514-559-7108",2, 2.2,2.1,"http://i.imgur.com/BTyyfVQ.jpg");
        dao.insertNewResto(resto, 1);
        dao.insertNewResto(resto2,1);
//        if (getArguments() != null) {
//
//        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        //add click events to all 5 buttons
        createClickEvent(R.id.nav_favourites,view);
        createClickEvent(R.id.nav_add_restaurant,view);
        createClickEvent(R.id.nav_find,view);
        createClickEvent(R.id.nav_nearby,view);
        createClickEvent(R.id.nav_tip_calculator,view);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnNavigationListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNavigationListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        nav_buttons[0] = (ImageButton)getView().findViewById(R.id.nav_favourites);
        Log.d(TAG,nav_buttons[0].toString());
    }

    private void createClickEvent(int id, View view){
        ImageButton ib = (ImageButton)view.findViewById(id);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.nav_favourites:
                        // fill favourites array.
                        /**
                         * Get the restaurants from the current user, obtain their id from the database
                         * store it in the
                         */
                        List<Restaurant> restaurants =  dao.getRestaurantsByUserId(1);
                        Log.d(TAG,dao.getUserIdByUserName("Evan")+"");
//                        Restaurant[] rs = new Restaurant[2];
//                        rs[0] = new Restaurant("My fav resto", "https://google.com", "food","514-559-7108",2, 2.2,2.1,"http://i.imgur.com/BTyyfVQ.jpg");
//                        rs[1] = new Restaurant("My fav resto2", "https://google.com", "food","514-559-7108",2, 2.2,2.1,"http://i.imgur.com/BTyyfVQ.jpg");
//                        favourites = rs;
                        Restaurant[] rs = restaurants.toArray(new Restaurant[restaurants.size()]);
                        Log.d(TAG,"Favourites clicked");
                        // pass the favourites array to the parent activity via the interface.
                        mListener.setFavourites(rs);
                        break;
                    case R.id.nav_add_restaurant:
                        // launch the add resto activity
                        Log.d(TAG,"Add restaurant");
                        break;
                    case R.id.nav_find:
                        // launch the find activity, or update view
                        Log.d(TAG,"find called");
                        break;
                    case R.id.nav_nearby:
                        // show the nearby restaurants
                        Log.d(TAG,"nearby called");
                        checkStatus();
                        break;
                    case R.id.nav_tip_calculator:
                        Log.d(TAG, "tip calculator selected");
                        // launch tip activity
                        TipFragment tp = new TipFragment();
                        mListener.launchTipCalc(tp);
                        break;
                }
            }
        });
    }

    private void checkStatus() {
        NetworkInfo networkInfo;

        Context context = getActivity(); // changed from getContext to support minimum api.
        boolean networkIsUp = false;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // getActiveNetworkInfo() each time as the network may swap as the
        // device moves
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
            // ALWAYS check isConnected() before initiating network traffic
            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                //  tv2.setText("Network is connected or connecting");
                networkIsUp = true;
            } else {
                // tv2.setText("No network connectivity");
                networkIsUp = false;
            }
        } else {
            //  tv2.setText("No network manager service");
            networkIsUp = false;
        }

        if(networkIsUp= false){
            System.out.println("false");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("No network connectivity")
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            // Create the AlertDialog object
            builder.create().show();
        }
        else{
            //if network is conneced
            RestaurantInfo info = new RestaurantInfo();

            //get the coordinates here
            //  double lat = ;
            //  double lon = ;
            info.downloadJsonData();

        }
    }


    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
