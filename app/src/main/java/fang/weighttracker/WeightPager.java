package fang.weighttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

import fang.weighttracker.model.Weight;
import fang.weighttracker.model.WeightLab;

/**
 * Created by Fang2 on 2016/4/23.
 */
public class WeightPager extends AppCompatActivity{
    private static final String EXTRA_WEIGHT_ID =
            "fang.weighttracker.weight_id";

    private ViewPager mViewPager;
    private List<Weight> mWeights;

    public static Intent newIntent(Context packageContext, UUID weightId){
        Intent intent = new Intent(packageContext, WeightPager.class);
        intent.putExtra(EXTRA_WEIGHT_ID, weightId);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_pager);

        UUID weightId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_WEIGHT_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_weight_pager_view_pager);
        mWeights = WeightLab.get(this).getWeights();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Weight weight = mWeights.get(position);
                return NewWeightFragment.newInstance(weight.getId());
            }

            @Override
            public int getCount() {
                return mWeights.size();
            }
        });

        for(int i = 0; i< mWeights.size(); i++){
            if(mWeights.get(i).getId().equals(weightId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


}
