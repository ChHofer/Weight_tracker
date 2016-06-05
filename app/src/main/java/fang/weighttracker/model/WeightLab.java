package fang.weighttracker.model;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fang.weighttracker.model.WeightDbSchema.WeightTable;

/**
 * Created by Fang2 on 2016/4/22.
 */
public class WeightLab {
    private static WeightLab sWeightLab;
    private Context mContext;
    private static SQLiteDatabase mDatabase;

    public static WeightLab get(Context context){
        if(sWeightLab == null){
            sWeightLab = new WeightLab(context);
        }
        return sWeightLab;
    }
    private WeightLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new WeightBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addWeight(Weight w){
        ContentValues values = getContentValues(w);
        mDatabase.insert(WeightTable.NAME,null,values);
    }

    public void deleteWeight(Weight w){
        mDatabase.delete(WeightTable.NAME,
                WeightTable.Columns.UUID + " = ?",new String[]{w.getId().toString()});
    }

    public void delete_temp_Weight(){
        mDatabase.delete(WeightTable.NAME,
                WeightTable.Columns.FLAG_SAVED + " = ?",new String[]{"false"});
    }

    public List<Weight> getWeights(){
        List<Weight> weights = new ArrayList<>();
        WeightCursorWrapper cursor = queryWeights(null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                weights.add(cursor.getWeight());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return weights;
    }

    public Weight getWeight(UUID id){
        WeightCursorWrapper cursor = queryWeights(WeightTable.Columns.UUID + " = ?",
                new String[]{
                        id.toString()
                });
        try{
            if(cursor.getCount() ==  0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getWeight();
        }finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Weight weight){
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null){
            return null;
        }

        return new File(externalFilesDir, weight.getPhotoFilename());
    }

    public void updateWeight(Weight weight){
        String uuidString = weight.getId().toString();
        ContentValues values = getContentValues(weight);

        mDatabase.update(WeightTable.NAME, values,
                WeightTable.Columns.UUID +" = ?",
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(Weight weight){
        ContentValues values = new ContentValues();
        values.put(WeightTable.Columns.UUID, weight.getId().toString());
        values.put(WeightTable.Columns.WEIGHT, weight.getWeight());
        values.put(WeightTable.Columns.DATE, weight.getDate().getTime());
        values.put(WeightTable.Columns.DIFF, "");
        values.put(WeightTable.Columns.FLAG_SAVED, weight.isFlag_saved());

        return values;

    }

   private WeightCursorWrapper queryWeights(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                WeightTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                WeightTable.Columns.DATE + " DESC"
        );
        return new WeightCursorWrapper(cursor);
    }

}
