package com.example.huyviet1995.criminalintent;

/**
 * Created by huyviet1995 on 3/14/16.
 */
public class CrimeDbSchema {
    public static final class CrimeTable {
        public static final String NAME = "crimes";


        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
            public static final String PHONE = "phone";
            public static final String SUSPECTID = "SuspectID";
        }
    }
}
