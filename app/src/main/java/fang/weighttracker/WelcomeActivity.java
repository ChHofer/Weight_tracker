package fang.weighttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    private UserLocalStore userLocalstore;
    Button btn_settings, btn_go_summary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btn_settings = (Button) findViewById(R.id.welcome_initial_setting);
        btn_go_summary = (Button) findViewById(R.id.welcome_go_summary);

        userLocalstore = new UserLocalStore(this);
        if (userLocalstore.isSet() == false) {
            btn_go_summary.setVisibility(View.INVISIBLE);
            btn_settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Settings.class);
                    startActivity(intent);
                }
            });

        }else{
            btn_settings.setVisibility(View.INVISIBLE);
            btn_go_summary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
