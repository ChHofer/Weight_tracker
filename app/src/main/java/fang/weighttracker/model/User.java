package fang.weighttracker.model;

/**
 * Created by Fang2 on 2016/4/22.
 */
public class User {
    private static User user = new User();
    private int  gender,unit,language;
    private String height, start_weight,current_weight, goal_weight,goal_date;
    private User(){}
    public static User getUser(){
        return user;
    }

    public User updateUser(int gender, int unit, int language, String height, String start_weight,
                String current_weight, String goal_weight,String goal_date){
        this.gender = gender;
        this.unit = unit;
        this.language = language;
        this.height = height;
        this.start_weight = start_weight;
        this.current_weight = current_weight;
        this.goal_weight = goal_weight;
        this.goal_date = goal_date;
        return user;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getStart_weight() {
        return start_weight;
    }

    public void setStart_weight(String start_weight) {
        this.start_weight = start_weight;
    }

    public String getCurrent_weight() {
        return current_weight;
    }

    public void setCurrent_weight(String current_weight) {
        this.current_weight = current_weight;
    }

    public String getGoal_weight() {
        return goal_weight;
    }

    public void setGoal_weight(String goal_weight) {
        this.goal_weight = goal_weight;
    }

    public String getGoal_date() {
        return goal_date;
    }

    public void setGoal_date(String goal_date) {
        this.goal_date = goal_date;
    }
}
