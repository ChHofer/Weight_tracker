package fang.weighttracker.model;

/**
 * Created by Fang2 on 2016/5/1.
 */
public class WeightDbSchema {
    public static final class WeightTable{
        public static final String NAME = "weights";

        public static final class Columns{
            public static final String UUID = "uuid";
            public static final String WEIGHT = "weight";
            public static final String DATE = "date";
            public static final String DIFF = "diff";
        }
    }
}
