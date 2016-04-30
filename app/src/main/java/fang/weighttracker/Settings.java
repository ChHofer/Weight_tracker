package fang.weighttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Date;

public class Settings extends AppCompatActivity {

    private RadioGroup rg_gender, rg_unit,rg_language;
    private RadioButton rb_gender, rb_unit, rb_language;
    private EditText et_height,et_start_weight,et_goal_weight,et_goal_date;
    private Button btn_save;
    private TextView tv_cancel;
    private static User user;
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
            String current_weight = start_weight;
            String goal_weight =  "160.0";
            String goal_date = new Date().toString();

            user = new User(selected_gender,selected_unit,selected_language,height, start_weight,
                    current_weight, goal_weight, goal_date);
            userLocalstore.storeSettings(user);
        } else{
            tv_cancel.setVisibility(View.VISIBLE);
            user = userLocalstore.getSettings();
        }

        rg_gender.check(user.gender);
        rg_unit.check(user.unit);
        rg_language.check(user.language);
        et_height.setText(user.height);
        et_start_weight.setText(user.start_weight);
        et_goal_weight.setText(user.goal_weight);
        et_goal_date.setText(user.goal_date);


        btn_save = (Button) findViewById(R.id.setting_btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected_gender = rg_gender.getCheckedRadioButtonId();
                int selected_unit = rg_unit.getCheckedRadioButtonId();
                int selected_language = rg_language.getCheckedRadioButtonId();

                String height = et_height.getText().toString();
                float f_height = Float.parseFloat(height);
                DecimalFormat df1 = new DecimalFormat("###,###.0");
                height = df1.format(f_height);



                String start_weight = et_start_weight.getText().toString();
                float f_start_weight = Float.parseFloat(start_weight);
                DecimalFormat df2 = new DecimalFormat("###,###.0");
                start_weight = df2.format(f_start_weight);

                String current_weight = user.current_weight;

                String goal_weight = et_goal_weight.getText().toString();
                float f_goal_weight = Float.parseFloat(goal_weight);
                DecimalFormat df3 = new DecimalFormat("###,###.0");
                goal_weight = df3.format(f_goal_weight);

                String goal_date = et_goal_date.getText().toString();



                user = new User(selected_gender,selected_unit,selected_language,height, start_weight,
                        current_weight, goal_weight,goal_date);

                userLocalstore.storeSettings(user);
                userLocalstore.setUserSetAlready(true);
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
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
