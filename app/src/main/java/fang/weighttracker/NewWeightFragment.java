package fang.weighttracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import fang.weighttracker.model.User;
import fang.weighttracker.model.UserLocalStore;
import fang.weighttracker.model.Weight;
import fang.weighttracker.model.WeightDbSchema;
import fang.weighttracker.model.WeightLab;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewWeightFragment extends Fragment {
    private static final String ARG_WEIGHT_ID = "weight_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_NUMBER = "DialogNumber";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_NUMBER= 1;
    private static final int REQUEST_PHOTO = 2;

    private SimpleDateFormat formatter = new SimpleDateFormat("E yyyy-MM-dd");
    private Weight mWeight;
    private File mPhotoFile;
    private TextView et_New_Weight, et_New_Date;
    private ImageView mPhotoView;
    private Button btn_save,btn_delete;
    private ImageButton mPhotoButton;
    private Date date_update;

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
        date_update = mWeight.getDate();
        userLocalstore = new UserLocalStore(getContext());
        mPhotoFile = WeightLab.get(getActivity()).getPhotoFile(mWeight);
    }

    @Override
    public void onPause() {
        super.onPause();
        WeightLab.get(getActivity())
                    .updateWeight(mWeight);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_new_weight, container,false);


        et_New_Weight = (TextView) v.findViewById(R.id.et_new_weight_value);
        et_New_Weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                NumberPickerFragment dialog_number = NumberPickerFragment
                        .newInstance(Float.parseFloat(mWeight.getWeight()));
                dialog_number.setTargetFragment(NewWeightFragment.this, REQUEST_NUMBER);
                dialog_number.show(manager, DIALOG_NUMBER);
            }
        });
        et_New_Date = (TextView) v.findViewById(R.id.et_new_date_value);
        btn_save = (Button) v.findViewById(R.id.new_weight_btn_save);
        btn_delete = (Button) v.findViewById(R.id.btn_delete_weight);

        et_New_Weight.setText(mWeight.getWeight());
        String date = formatter.format(mWeight.getDate());
        et_New_Date.setText(date);


        et_New_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mWeight.getDate());
                dialog.setTargetFragment(NewWeightFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String st_new_weight = et_New_Weight.getText().toString();
                float new_weight = Float.parseFloat(st_new_weight);
                if(new_weight < 50.0 || new_weight > 500.0) {
                    View focusView;
                    et_New_Weight.setError("Please enter between 50 - 500 lbs");
                    focusView = et_New_Weight;
                    focusView.requestFocus();
                }else{

                    DecimalFormat df = new DecimalFormat("######.0");
                    mWeight.setWeight(df.format(new_weight));
                    mWeight.setDate(date_update);
                    mWeight.setFlag_saved("true");
                    if(!mWeight.getDate().before(new Date()) || DateUtils.isToday(mWeight.getDate().getTime())){
                        userLocalstore.storeCurrentWeight(mWeight);
                    }
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeightLab.get(getContext()).deleteWeight(mWeight);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.weight_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        if(canTakePhoto){
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
        mPhotoView = (ImageView) v.findViewById(R.id.weight_photo);
        updatePhotoView();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_DATE){
            date_update = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            String new_date = formatter.format(date_update);
            et_New_Date.setText(new_date);
        }else if(requestCode == REQUEST_PHOTO){
            updatePhotoView();
        }else if(requestCode == REQUEST_NUMBER){
            float number = (float) data.getSerializableExtra(NumberPickerFragment.EXTRA_NUMBER);
            et_New_Weight.setText(Float.toString(number));
        }
    }

    private void updatePhotoView(){
        if(mPhotoFile == null || !mPhotoFile.exists()){
            mPhotoView.setImageDrawable(getResources().getDrawable(R.drawable.default_photo));
        }else {
            Bitmap bitmap = PictureUtils.getScaleBitmap(
                    mPhotoFile.getPath(),getActivity()
            );
            mPhotoView.setImageBitmap(bitmap);
        }
    }


}
