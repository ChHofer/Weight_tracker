package fang.weighttracker.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fang2 on 2016/4/26.
 */
public class UserLocalStore{
    private static final String SETTINGS = "userSettings";
    private static SharedPreferences userLocalStoredb;
    private User user = User.getUser();
    public  UserLocalStore(Context context){
        userLocalStoredb = context.getSharedPreferences(SETTINGS,Context.MODE_PRIVATE);
    }
    public void storeSettings(User user){
        SharedPreferences.Editor editor = userLocalStoredb.edit();
        editor.putInt("gender", user.getGender());
        editor.putInt("unit", user.getUnit());
        editor.putInt("language", user.getLanguage());
        editor.putString("height", user.getHeight());
        editor.putString("start_weight", user.getStart_weight());
        editor.putString("current_weight", user.getCurrent_weight());
        editor.putString("goal_weight", user.getGoal_weight());
        editor.putString("goal_date", user.getGoal_date());
        editor.apply();
    }

    public void storeCurrentWeight(Weight weight){
        SharedPreferences.Editor editor = userLocalStoredb.edit();
        editor.putString("current_weight",weight.getWeight());
        editor.apply();
    }

    public User getSettings(){
        int gender = userLocalStoredb.getInt("gender",0);
        int unit = userLocalStoredb.getInt("unit",0);
        int language = userLocalStoredb.getInt("language",0);
        String height = userLocalStoredb.getString("height","");
        String start_weight = userLocalStoredb.getString("start_weight","");
        String current_weight = userLocalStoredb.getString("current_weight","");
        String goal_weight = userLocalStoredb.getString("goal_weight","");
        String goal_date = userLocalStoredb.getString("goal_date","");


        User storedSettings = user.updateUser(gender, unit,language,height, start_weight,
                current_weight,goal_weight,goal_date);
        return storedSettings;
    }

    public void setUserSetAlready(boolean isSet){
        SharedPreferences.Editor editor = userLocalStoredb.edit();
        editor.putBoolean("isSetAlready", isSet);
        editor.apply();
    }

    public boolean isSet(){
        return userLocalStoredb.getBoolean("isSetAlready",false);

    }
    public void clearSettings(){
        SharedPreferences.Editor editor = userLocalStoredb.edit();
        editor.clear();
        editor.apply();
    }

}
