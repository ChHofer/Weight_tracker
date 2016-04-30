package fang.weighttracker;

import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Fang2 on 2016/4/22.
 */
public class WeightLab {
    private static WeightLab sWeightLab;
    private List<Weight> mWeights;

    public static WeightLab get(Context context){
        if(sWeightLab == null){
            sWeightLab = new WeightLab(context);
        }
        return sWeightLab;
    }
    private WeightLab(Context context){
        mWeights = new ArrayList<>();
    }

    public void addWeight(Weight w){
        mWeights.add(w);
    }
    public List<Weight> getWeights(){
        return mWeights;
    }

    public Weight getWeight(UUID id){
        for(Weight weight:mWeights){
            if(weight.getId().equals(id)){
                return weight;
            }
        }
        return null;
    }


}
