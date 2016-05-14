package fang.weighttracker;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.natasa.progressviews.ArcProgressBar;
import com.natasa.progressviews.CircleProgressBar;
import com.natasa.progressviews.CircleSegmentBar;
import com.natasa.progressviews.utils.OnProgressViewListener;

import java.text.DecimalFormat;

import fang.weighttracker.model.User;
import fang.weighttracker.model.UserLocalStore;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static TextView
            tv_start_weight,
            tv_current_weight,
            tv_goal_weight,
            tv_bmi,
            tv_weight_loss_to_date,
            tv_weekly_loss_average;
    private ImageView img_welcome;
    private CircleProgressBar mCircleProgressBar;
    private ArcProgressBar mArcProgressBar;

    private static ImageView img_bmi;
    private static User user;
    private static UserLocalStore userLocalstore;
    private static double d_bmi;
    private static String bmi;
    private static String weight_to_date;
    private static float weight_to_date_f;
    private static String weekly_weight_loss;
    private static float weight_goal_diff;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLocalstore = new UserLocalStore(getContext());
        user = userLocalstore.getSettings();
        float start_w = Float.parseFloat(user.getStart_weight());
        float current_w = Float.parseFloat(user.getCurrent_weight());
        float goal_w = Float.parseFloat(user.getGoal_weight());
        float height_inch = Float.parseFloat(user.getHeight()) * 12;

        weight_to_date_f = current_w - start_w;
        weight_goal_diff = goal_w - start_w;

        DecimalFormat df_todate = new DecimalFormat("###,###.0");
        weight_to_date = df_todate.format(weight_to_date_f);

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
        tv_weight_loss_to_date =(TextView) v.findViewById(R.id.tv_summary_weight_loss_to_date);
        tv_weekly_loss_average = (TextView) v.findViewById(R.id.tv_summary_weekly_weekly_loss);

        mCircleProgressBar = (CircleProgressBar)v.findViewById(R.id.circle_progress);
        mCircleProgressBar.setLinearGradientProgress(true);
        mCircleProgressBar.setRoundEdgeProgress(true);
        mCircleProgressBar.setStartPositionInDegrees(270);
        float progress = weight_to_date_f/weight_goal_diff;
        mCircleProgressBar.setProgress(progress*100);
        //mCircleProgressBar.setProgressIndeterminateAnimation(2000);

        if(weight_to_date_f < 0){
             mCircleProgressBar.setText(weight_to_date);
             mCircleProgressBar.setTextColor(getResources().getColor(R.color.green));
            }else{
                mCircleProgressBar.setText("+" +weight_to_date );
                mCircleProgressBar.setTextColor(getResources().getColor(R.color.red));
        }

        mCircleProgressBar.setTextSize(50);
        mCircleProgressBar.setOnProgressViewListener(new OnProgressViewListener() {
            @Override
            public void onFinish() {
                //do something on progress finish
                mCircleProgressBar.setText("done!");
               // mCircleProgressBar.resetProgressBar();
            }

            @Override
            public void onProgressUpdate(float v) {
                mCircleProgressBar.setText("" + (int) v);
            }
        });

        mArcProgressBar = (ArcProgressBar) v.findViewById(R.id.arc_progressbar);

        tv_start_weight.setText(user.getStart_weight());
        tv_current_weight.setText(user.getCurrent_weight());
        tv_goal_weight.setText(user.getGoal_weight());

        if( d_bmi >= 18.5 && d_bmi < 25.0 ){
            img_bmi.setImageResource(R.drawable.ok);
            tv_bmi.setText(bmi + " Keep up...");
        }else{
            img_bmi.setImageResource(R.drawable.not_ok);
            tv_bmi.setText(bmi + " You know...");
        }
        tv_weight_loss_to_date.setText("Weight Loss To Date : " + weight_to_date);
        return v;
    }
}
