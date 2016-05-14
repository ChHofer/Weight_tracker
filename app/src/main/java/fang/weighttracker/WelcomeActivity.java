package fang.weighttracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import fang.weighttracker.model.UserLocalStore;

public class WelcomeActivity extends AppCompatActivity {
    private static UserLocalStore userLocalstore;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        userLocalstore = new UserLocalStore(getApplication());

        //Goes to Setting Activity if not set, else go main activity

        if (userLocalstore.isSet() == false) {

            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


}


