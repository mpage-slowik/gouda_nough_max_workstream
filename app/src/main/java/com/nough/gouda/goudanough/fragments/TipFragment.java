package com.nough.gouda.goudanough.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nough.gouda.goudanough.R;

/**
 * @author Max Page-Slowik
 *         The fragment that will be used to calculate the tip for the user
 *         The default percentages being 10, 15, 20
 */

public class TipFragment extends Fragment {
    private  RadioGroup radioButtonGroup;
    private EditText amount_view;
    private EditText amount_of_people;
    private TextView tip_amount_view;
    private TextView total_amount_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tip_layout, container, false);
        Button b = (Button) view.findViewById(R.id.tip_calc_button);
        radioButtonGroup = (RadioGroup) view.findViewById(R.id.radio_group_tip);
        amount_view = (EditText) view.findViewById(R.id.bill);
        tip_amount_view = (TextView) view.findViewById(R.id.tip);
        total_amount_view = (TextView) view.findViewById(R.id.total);
        amount_of_people = (EditText) view.findViewById(R.id.num_of_people);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d("VIEW: ", amount_view.toString());
                calculateTip(amount_view.getText().toString());


            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * @param bill_amount
     */
    public void calculateTip(String bill_amount) {

        Log.d("VIEW: ", bill_amount);
        if (amount_view == null) {
            Log.e("TIP: ", "Error with tip amount");
        } else {
            double bill = 0;
            try {
                bill = Double.parseDouble(amount_view.getText().toString());
            } catch (NumberFormatException nfe) {
                Log.e("TIP: ", nfe.getMessage());
            }

            int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) radioButtonGroup.findViewById(radioButtonID);
            if (radioButton!=null) {
                String percent = radioButton.getText().toString();

                percent = percent.substring(0, percent.indexOf('%'));
                double percent_amount = Double.parseDouble(percent);
                percent_amount = percent_amount / 100;
                int people = Integer.valueOf(amount_of_people.getText().toString());
                double tip = percent_amount * bill;
                double total = ((percent_amount + 1.0) * bill) / people;


                tip_amount_view.setText(String.valueOf(String.format("%.2f", tip)));
                total_amount_view.setText(String.valueOf(String.format("%.2f", total)));
            }
        }

    }
}
