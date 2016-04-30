package fang.weighttracker;

import android.content.Context;

import java.util.Date;

/**
 * Created by Fang2 on 2016/4/22.
 */
public class User {
    int  gender,unit,language;
    String height, start_weight,current_weight, goal_weight,goal_date;
    public User(int gender, int unit, int language, String height, String start_weight,
                String current_weight, String goal_weight,String goal_date){
        this.gender = gender;
        this.unit = unit;
        this.language = language;
        this.height = height;
        this.start_weight = start_weight;
        this.current_weight = current_weight;
        this.goal_weight = goal_weight;
        this.goal_date = goal_date;
    }



}
