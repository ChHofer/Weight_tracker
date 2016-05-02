package fang.weighttracker.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fang.weighttracker.model.WeightDbSchema.WeightTable;

/**
 * Created by Fang2 on 2016/5/1.
 */
public class WeightBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "weightBase.db";

    public WeightBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + WeightTable.NAME + "(" +
                     " _id integer primary key autoincrement, " +
                     WeightTable.Columns.UUID + ", " +
                     WeightTable.Columns.WEIGHT + ", " +
                     WeightTable.Columns.DATE + "," +
                     WeightTable.Columns.DIFF +
                ")"
        );

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
