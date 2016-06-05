package fang.weighttracker.model;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import fang.weighttracker.model.WeightDbSchema.WeightTable;

/**
 * Created by Fang2 on 2016/5/1.
 */
public class WeightCursorWrapper extends CursorWrapper {
    public WeightCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Weight getWeight(){
        String uuidString = getString(getColumnIndex(WeightTable.Columns.UUID));
        String weight = getString(getColumnIndex(WeightTable.Columns.WEIGHT));
        long date =getLong(getColumnIndex(WeightTable.Columns.DATE));
        String diff = getString(getColumnIndex(WeightTable.Columns.DIFF));
        String flag_saved = getString(getColumnIndex(WeightTable.Columns.FLAG_SAVED));

        Weight w = new Weight(UUID.fromString(uuidString));
        w.setWeight(weight);
        w.setDate(new Date(date));
        w.setFlag_saved(flag_saved);

        return w;
    }
}
