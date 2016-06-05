package fang.weighttracker.model;

/**
 * @author Fang Fang
 * Date: 2016/5/1
 *
 */
public class WeightDbSchema {
    public static final class WeightTable{
        public static final String NAME = "weights";

        public static final class Columns{
            public static final String UUID = "uuid";
            public static final String WEIGHT = "weight";
            public static final String DATE = "date";
            public static final String DIFF = "diff";
            public static final String FLAG_SAVED = "flag_saved";
        }
    }

}
