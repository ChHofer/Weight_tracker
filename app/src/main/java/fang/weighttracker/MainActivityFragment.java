package fang.weighttracker;

import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static TextView tv_start_weight,tv_current_weight,tv_goal_weight,tv_bmi;
    private static ImageView img_bmi;
    private static User user;
    private static UserLocalStore userLocalstore;
    private static double d_bmi;
    private static String bmi;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalstore = new UserLocalStore(getContext());
        user = userLocalstore.getSettings();
        float current_w = Float.parseFloat(user.getCurrent_weight());
        float height_inch = Float.parseFloat(user.getHeight()) * 12;

        DecimalFormat df = new DecimalFormat("###,###.0");
        d_bmi = (current_w * 703)/Math.pow(height_inch,2);
         bmi = df.format(d_bmi);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_main, container, false);

        tv_start_weight = (TextView) v.findViewById(R.id.summary_tv_start_weight);
        tv_current_weight = (TextView) v.findViewById(R.id.summary_tv_current_weight);
        tv_goal_weight = (TextView) v.findViewById(R.id.summary_tv_goal_weight);
        tv_bmi = (TextView) v.findViewById(R.id.summary_tv_bmi);
        img_bmi = (ImageView) v.findViewById(R.id.img_bmi);

        tv_start_weight.setText(user.getStart_weight());
        tv_current_weight.setText(user.getCurrent_weight());
        tv_goal_weight.setText(user.getGoal_weight());
        tv_bmi.setText(bmi);
        if( d_bmi >= 18.5 && d_bmi < 25.0 ){
            img_bmi.setImageResource(R.drawable.ok);
        }else{
            img_bmi.setImageResource(R.drawable.not_ok);
        }
        return v;
    }
}
