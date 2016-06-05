package fang.weighttracker.model;

import java.util.Date;
import java.util.UUID;

/**
 * @author Fang Fang
 * Date: 2016/4/22
 *
 */
public class Weight {
    private UUID mId;
    private String mWeight;
    private Date mDate;
    private User user = User.getUser();
    private String flag_saved;

    public Weight(){
        this(UUID.randomUUID());
    }
    public Weight(UUID id){
        mId = id;
        mDate = new Date();
        mWeight = user.getCurrent_weight();
        flag_saved = "false";
    }

    public String isFlag_saved() {
        return flag_saved;
    }

    public void setFlag_saved(String flag_saved) {
        this.flag_saved = flag_saved;
    }

    public UUID getId() {
        return mId;
    }

    public String getWeight() {
        return mWeight;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getPhotoFilename(){
        return "IMG_" +getId().toString() + ".jpg";
    }
}
