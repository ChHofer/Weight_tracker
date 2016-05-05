package fang.weighttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Date;

import fang.weighttracker.model.User;
import fang.weighttracker.model.UserLocalStore;
import fang.weighttracker.model.Weight;
import fang.weighttracker.model.WeightLab;

public class Settings extends AppCompatActivity {

    private RadioGroup rg_gender, rg_unit,rg_language;
    private RadioButton rb_gender, rb_unit, rb_language;
    private EditText et_height,et_start_weight,et_goal_weight,et_goal_date;
    private Button btn_save;
    private TextView tv_cancel;
    private static User user = User.getUser();
    private UserLocalStore userLocalstore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userLocalstore = new UserLocalStore(this);

        rg_gender = (RadioGroup) findViewById(R.id.settings_rg_gender);
        rg_unit= (RadioGroup) findViewById(R.id.settings_rg_unit);
        rg_language= (RadioGroup) findViewById(R.id.settings_rg_language);
        et_height = (EditText) findViewById(R.id.settings_et_height);
        et_start_weight = (EditText) findViewById(R.id.settings_et_start_weight);
        et_goal_weight = (EditText) findViewById(R.id.settings_et_goal_weight);
        et_goal_date = (EditText) findViewById(R.id.settings_et_goal_date);

        tv_cancel = (TextView) findViewById(R.id.settings_cancel);

        if(userLocalstore.isSet() == false){
            tv_cancel.setVisibility(View.INVISIBLE);
            int selected_gender = rg_gender.getCheckedRadioButtonId();
            int selected_unit = rg_unit.getCheckedRadioButtonId();
            int selected_language = rg_language.getCheckedRadioButtonId();
            String height = "6.0";
            String start_weight = "200.0";
            String current_weight = "0.0";
            String goal_weight =  "160.0";
            String goal_date = new Date().toString();

            user = user.updateUser(selected_gender,selected_unit,selected_language,height, start_weight,
                    current_weight, goal_weight, goal_date);
            userLocalstore.storeSettings(user);



        } else{
            tv_cancel.setVisibility(View.VISIBLE);
            user = userLocalstore.getSettings();
        }

        rg_gender.check(user.getGender());
        rg_unit.check(user.getUnit());
        rg_language.check(user.getLanguage());

        et_height.setText(user.getHeight());
        et_height.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // clear data on Height text input box
                et_height.setText("");
                return false;
            }
        });


        et_start_weight.setText(user.getStart_weight());
        et_start_weight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    // clear data on et_start_weight text input box once touched
                    et_start_weight.setText("");
                return false;
            }
        });

        et_goal_weight.setText(user.getGoal_weight());
        et_goal_weight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // clear data on et_goal_weight text input box
                et_goal_weight.setText("");
                return false;
            }
        });

        et_goal_date.setText(user.getGoal_date());





        btn_save = (Button) findViewById(R.id.setting_btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected_gender = rg_gender.getCheckedRadioButtonId();
                int selected_unit = rg_unit.getCheckedRadioButtonId();
                int selected_language = rg_language.getCheckedRadioButtonId();

                String height = et_height.getText().toString();
                float f_height = Float.parseFloat(height);

                String start_weight = et_start_weight.getText().toString();
                float f_start_weight = Float.parseFloat(start_weight);

                String goal_weight = et_goal_weight.getText().toString();
                float f_goal_weight = Float.parseFloat(goal_weight);

                if(f_height < 2.0 || f_height > 10.0){
                    et_height.setError("Please enter Height between 2 - 10 ft");
                    View focusView = et_height;
                    focusView.requestFocus();
                } else if(f_start_weight < 50 || f_start_weight > 500){
                    et_start_weight.setError("Please enter Weight between 50 - 500 lbs");
                    View focusView = et_start_weight;
                    focusView.requestFocus();
                }else if(f_goal_weight < 50 || f_goal_weight > 500){
                    et_goal_weight.setError("Please enter Weight between 50 - 500 lbs");
                    View focusView = et_goal_weight;
                    focusView.requestFocus();
                }else{
                    DecimalFormat df1 = new DecimalFormat("###,###.0");
                    height = df1.format(f_height);

                    DecimalFormat df2 = new DecimalFormat("###,###.0");
                    start_weight = df2.format(f_start_weight);


                    String current_weight = user.getCurrent_weight();
                    if(Float.parseFloat(current_weight) == 0.0){
                        current_weight = start_weight;

                    }

                    DecimalFormat df3 = new DecimalFormat("###,###.0");
                    goal_weight = df3.format(f_goal_weight);

                    String goal_date = et_goal_date.getText().toString();

                    user = user.updateUser(selected_gender,selected_unit,selected_language,height, start_weight,
                            current_weight, goal_weight,goal_date);

                    userLocalstore.storeSettings(user);
                    userLocalstore.setUserSetAlready(true);

                    Weight weight = new Weight();
                    weight.setDate(new Date());
                    weight.setWeight(current_weight);

                    WeightLab.get(getApplication()).addWeight(weight);

                    Intent intent = new Intent(Settings.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

}
