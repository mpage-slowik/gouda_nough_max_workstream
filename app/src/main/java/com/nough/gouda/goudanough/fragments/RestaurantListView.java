package com.nough.gouda.goudanough.fragments;


import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nough.gouda.goudanough.GoudaNoughAlertDialog;
import com.nough.gouda.goudanough.R;
import com.nough.gouda.goudanough.beans.Restaurant;
import com.nough.gouda.goudanough.RestaurantListViewAdapter;
import com.nough.gouda.goudanough.ShowRestaurantActivity;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantListView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantListView#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * THIS WILL BE THE LIST VIEW FRAGMENT THAT DISPLAYS A LIST OF RESTAURANT IN A LIST VIEW.
 * THE FRAGMENT CONSTRUCTOR SHOULD TAKE IN A PARAMETER OF THE DATA SET.
 */
public class RestaurantListView extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RESTO_TAG = "RestaurantList Fragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView lv;
    private RestaurantListViewAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnRestaurantListViewListener mListener;
    public interface OnRestaurantListViewListener{
        void setRestaurant(Restaurant restaurant);
        
    }

    public RestaurantListView() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_list_view, container, false);
        lv = (ListView)view.findViewById(android.R.id.list);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (RestaurantListView.OnRestaurantListViewListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRestaurantListViewListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up long click to call the restaurant
        // does the zomato api provide the restaurant? -- yes it does
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // get restaurant at position
                Restaurant restaurant = (Restaurant)adapterView.getAdapter().getItem(i);
                String telephone_number = restaurant.getPhone_numbers();

                Log.d(RESTO_TAG, telephone_number);
                Log.d(RESTO_TAG, "firing telephony");
                // Make an implicit intent to the telephony using the phone number.
                if(isTelephonyEnabled()){
                    String uri = "tel:" + telephone_number.trim(); // sets the uri for the implicity(?)
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }else{
                    Log.i(RESTO_TAG,"No telephone service enabled.");
                    GoudaNoughAlertDialog alert = new GoudaNoughAlertDialog();
                    Bundle b = new Bundle();
                    b.putString("header","Calling Error");
                    b.putString("content","We attempted to call the restaurant, however it seems that your device does not support this feature.");
                    alert.setArguments(b);
                    alert.show(getFragmentManager(),"Dialog");
                }

                return false;
            }
        });

        /**
         * Set up the click event for the restaurant clicks
         * They fire the ShowRestaurantActivity
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // click event
                Restaurant r = (Restaurant)adapterView.getAdapter().getItem(i);
                // send the restaurant to the ShowRestaurantActivity
                // using the fragment to activity interface.

                Intent resto_info = new Intent(getActivity(), ShowRestaurantActivity.class);
                Log.d(RESTO_TAG, r.getName());
                mListener.setRestaurant(r);
                /** have to put it through the intent to be used in
                 * onCreate of ShowRestaurantActivity
                 * really dislike doing this...
                 */
                resto_info.putExtra("selected_restaurant", r);
                getActivity().startActivity(resto_info);


            }
        });
    }

    /**
     * public method to set the adapter of the list view, to hand it a different dataset outside
     * of the fragment's code.
     * @param adapter
     */
    public void setAdapter(RestaurantListViewAdapter adapter){
        setListAdapter(adapter);
    }

    /**
     * returns the list view view object to manipulate from the parent activity.
     * @return
     */
    public ListView getListView(){
        return lv;
    }


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
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/

    /**
     * Helper method to check if the device supports telephony calls -- ie: if they have a service
     * or a device capable of making calls.
     * Courtesy of Viacheslav from StackOverflow
     * http://stackoverflow.com/questions/16236504/no-activity-found-to-handle-intent-with-action-dial
     * @return
     */
    private boolean isTelephonyEnabled(){
        TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(TELEPHONY_SERVICE);
        return telephonyManager != null && telephonyManager.getSimState()==TelephonyManager.SIM_STATE_READY;
    }

    public void setDataset(Restaurant[] data){
        ((RestaurantListViewAdapter)getListAdapter()).setDataset(data);
    }
}
