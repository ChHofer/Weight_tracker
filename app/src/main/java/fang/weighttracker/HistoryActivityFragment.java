package fang.weighttracker;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class HistoryActivityFragment extends Fragment {
    private RecyclerView mWeightRecyclerView;
    private WeightAdapter mAdapter;

    public HistoryActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mWeightRecyclerView = (RecyclerView) view.findViewById(R.id.weight_recycler_view);
        mWeightRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }
    private void updateUI(){
        WeightLab weightLab = WeightLab.get(getActivity());
        List<Weight> weights = weightLab.getWeights();

        mAdapter = new WeightAdapter(weights);
        mWeightRecyclerView.setAdapter(mAdapter);
    }
    private class WeightHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mWeightTextView, mDateTextView,tv_diff;
        private Weight mWeight;
        private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd EEEE");

        public WeightHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            mWeightTextView = (TextView) itemView. findViewById(R.id.list_item_weight_weight);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_weight_date);
            tv_diff = (TextView) itemView.findViewById(R.id.list_item_weight_diff);
        }

        public void bindWeight(Weight weight){
            mWeight = weight;
            mWeightTextView.setText(mWeight.getWeight());
            String date = formatter.format(mWeight.getDate());
            mDateTextView.setText(date);
            tv_diff.setText("diff");
        }

        @Override
        public void onClick(View view) {
            Intent intent = WeightPager.newIntent(getActivity(), mWeight.getId());
            startActivity(intent);
        }
    }
    private class WeightAdapter extends RecyclerView.Adapter<WeightHolder>{
        private List<Weight> mWeights;
        public WeightAdapter(List<Weight> weights){
            mWeights = weights;
        }

        @Override
        public WeightHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_weight, parent, false);
            return new WeightHolder(view);
        }

        @Override
        public void onBindViewHolder(WeightHolder holder, int position) {
            Weight weight = mWeights.get(position);
            holder.bindWeight(weight);
        }

        @Override
        public int getItemCount() {
            return mWeights.size();
        }
    }
}
