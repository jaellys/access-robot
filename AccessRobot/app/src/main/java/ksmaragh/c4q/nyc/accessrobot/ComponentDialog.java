package ksmaragh.c4q.nyc.accessrobot;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class ComponentDialog extends DialogFragment {

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
        getDialog().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView tvPartName = (TextView) view.findViewById(R.id.part_name);
        TextView tvPartInfo = (TextView) view.findViewById(R.id.part_info);

        String partName = getArguments().getString("PART_NAME");
        String partInfo = getArguments().getString("PART_INFO");

        tvPartName.setText(partName);
        tvPartInfo.setText(partInfo);

        return view;
    }
}
