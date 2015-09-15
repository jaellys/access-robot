package ksmaragh.c4q.nyc.accessrobot;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class ComponentDialog extends DialogFragment {

    HashMap<String, Integer> componentImages;

    public ComponentDialog() {
        // Empty constructor required for DialogFragment
    }

    public static ComponentDialog newInstance(String partName, String partInfo) {
        ComponentDialog frag = new ComponentDialog();
        Bundle args = new Bundle();
        args.putString("PART_NAME", partName);
        args.putString("PART_INFO", partInfo);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_component, container);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        componentImages = new HashMap<>();
        componentImages.put("part_leds", R.drawable.part_leds);
        componentImages.put("part_breadboard", R.drawable.part_breadboard);
        componentImages.put("part_resistors", R.drawable.part_resistors);
        componentImages.put("part_mini_servos", R.drawable.part_mini_servos);
        componentImages.put("part_arduino", R.drawable.part_arduino);
        componentImages.put("part_usb_otg_bt", R.drawable.part_usb_otg_bt);
        componentImages.put("part_usrf", R.drawable.part_usrf);
        componentImages.put("part_battery_pack", R.drawable.part_battery_pack);

        TextView tvPartName = (TextView) view.findViewById(R.id.tv_part_name);
        TextView tvPartInfo = (TextView) view.findViewById(R.id.tv_part_info);
        ImageView ivPart = (ImageView) view.findViewById(R.id.iv_part);

        String partName = getArguments().getString("PART_NAME");
        String partInfo = getArguments().getString("PART_INFO");

        String partImageName = "";

        if (partName != null) {
            if (partName.equals("Arduino Uno")) {
                partImageName = "part_arduino";
            } else if (partName.equalsIgnoreCase("Breadboard \n + \njumper wires")) {
                partImageName = "part_breadboard";
            } else if (partName.equalsIgnoreCase("Battery pack")) {
                partImageName = "part_battery_pack";
            } else if (partName.equalsIgnoreCase("Resistors")) {
                partImageName = "part_resistors";
            } else if (partName.equalsIgnoreCase("USB to OTG cable/Bluetooth chip")) {
                partImageName = "part_usb_otg_bt";
            } else if (partName.equalsIgnoreCase("Mini servos")) {
                partImageName = "part_mini_servos";
            } else if (partName.equalsIgnoreCase("LEDs")) {
                partImageName = "part_leds";
            } else if (partName.equalsIgnoreCase("Ultrasonic range finder")) {
                partImageName = "part_usrf";
            }
        }

        tvPartName.setText(partName);
        tvPartInfo.setText(partInfo);

        Button btnDismiss = (Button) view.findViewById(R.id.btn_got_it);

        if (partName != null) {
            ivPart.setImageResource(componentImages.get(partImageName));
            View infoContainer = view.findViewById(R.id.info_container);

            if (partName != null) {
                if (partName.equals("Arduino Uno")) {
                    infoContainer.setBackground(getResources().getDrawable(R.drawable.dialog_bg_blue));
                    btnDismiss.setTextColor(getResources().getColor(R.color.blue));
                } else if (partName.equalsIgnoreCase("Breadboard \n + \njumper wires")) {
                    infoContainer.setBackground(getResources().getDrawable(R.drawable.dialog_bg_pink));
                    btnDismiss.setTextColor(getResources().getColor(R.color.pink));
                } else if (partName.equalsIgnoreCase("Battery pack")) {
                    infoContainer.setBackground(getResources().getDrawable(R.drawable.dialog_bg_yellow));
                    btnDismiss.setTextColor(getResources().getColor(R.color.yellow));
                } else if (partName.equalsIgnoreCase("Resistors")) {
                    infoContainer.setBackground(getResources().getDrawable(R.drawable.dialog_bg_orange));
                    btnDismiss.setTextColor(getResources().getColor(R.color.orange));
                } else if (partName.equalsIgnoreCase("USB to OTG cable/Bluetooth chip")) {
                    infoContainer.setBackground(getResources().getDrawable(R.drawable.dialog_bg_purple));
                    btnDismiss.setTextColor(getResources().getColor(R.color.purple));
                } else if (partName.equalsIgnoreCase("Mini servos")) {
                    infoContainer.setBackground(getResources().getDrawable(R.drawable.dialog_bg_blue));
                    btnDismiss.setTextColor(getResources().getColor(R.color.blue));
                } else if (partName.equalsIgnoreCase("LEDs")) {
                    infoContainer.setBackground(getResources().getDrawable(R.drawable.dialog_bg_yellow));
                    btnDismiss.setTextColor(getResources().getColor(R.color.yellow));
                } else if (partName.equalsIgnoreCase("Ultrasonic range finder")) {
                    infoContainer.setBackground(getResources().getDrawable(R.drawable.dialog_bg_blue));
                    btnDismiss.setTextColor(getResources().getColor(R.color.blue));
                }
            }
        }

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}