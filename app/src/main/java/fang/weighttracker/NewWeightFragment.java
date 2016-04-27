package fang.weighttracker;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewWeightFragment extends Fragment {
    private static final String ARG_WEIGHT_ID = "weight_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private SimpleDateFormat formatter = new SimpleDateFormat("E yyyy-MM-dd");
    private Weight mWeight;
    private EditText et_New_Weight, et_New_Date;
    private Button btn_back;

    private static UserLocalStore userLocalstore;

    public NewWeightFragment() {
    }

    public static NewWeightFragment newInstance(UUID weightId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEIGHT_ID,weightId);

        NewWeightFragment fragment = new NewWeightFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID weightId = (UUID) getArguments().getSerializable(ARG_WEIGHT_ID);
        mWeight = WeightLab.get(getActivity()).getWeight(weightId);
        userLocalstore = new UserLocalStore(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_weight, container,false);

        et_New_Weight = (EditText) v.findViewById(R.id.et_new_weight_value);
        et_New_Date = (EditText) v.findViewById(R.id.et_new_date_value);
        btn_back = (Button) v.findViewById(R.id.btn_back);

        et_New_Weight.setText(mWeight.getWeight());
        String date = formatter.format(mWeight.getDate());
        et_New_Date.setText(date);
        userLocalstore.storeCurrentWeight(mWeight);

        et_New_Weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                float new_weight = Float.parseFloat(charSequence.toString());
                DecimalFormat df = new DecimalFormat("###,###.0");

                mWeight.setWeight(df.format(new_weight));
                userLocalstore.storeCurrentWeight(mWeight);
                Toast.makeText(getActivity(), "Weight Saved!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_New_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mWeight.getDate());
                dialog.setTargetFragment(NewWeightFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        final String str_new_weight = et_New_Weight.getText().toString();
        final String str_new_date = et_New_Date.getText().toString();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), History.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mWeight.setDate(date);
            String new_date = formatter.format(mWeight.getDate());
            et_New_Date.setText(new_date);
        }
    }
}
