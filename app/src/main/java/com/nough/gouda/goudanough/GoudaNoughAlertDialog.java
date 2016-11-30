package com.nough.gouda.goudanough;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Evang on 2016-11-24.
 */

public class GoudaNoughAlertDialog extends DialogFragment {
    Button btn_positive;
    Button btn_negative;
    TextView header;
    TextView content;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String header = "";
        String content = "";

        // Inflate the alert dialog view to grant access to its objects.
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alert_view = inflater.inflate(R.layout.gouda_nough_alert,null);
        // loads the view objects in memory.
        initViewObjects(alert_view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(getArguments() != null){
            Bundle bundle = getArguments();
            if(bundle.containsKey("header")){
                header = bundle.getString("header");
                this.header.setText(header);
            }else{ // set the default header text.
                this.header.setText(R.string.alert_default_header);
            }
            if(bundle.containsKey("content")){
                content = bundle.getString("content");
                this.content.setText(content);
            }else{ // set default content from string values.
                this.content.setText(R.string.alert_default_content);
            }
        }

        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        builder.setView(alert_view);


        return builder.create();
    }

    private void initViewObjects(View v) {
        btn_negative = (Button)v.findViewById(R.id.alert_btn_negative);
        btn_positive = (Button)v.findViewById(R.id.alert_btn_positive);
        header = (TextView)v.findViewById(R.id.alert_header);
        content = (TextView)v.findViewById(R.id.alert_content);
    }
}
