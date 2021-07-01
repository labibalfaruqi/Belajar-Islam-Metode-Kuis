package com.ldev.bimq.sqlite;

import android.provider.BaseColumns;

public class Table {

    private Table() {
    }

    public static final class Materi implements BaseColumns {
        public static final String TABLE_NAME = "Materi";
        public static final String COL_ID = "idMateri";
        public static final String COL_JUDUL = "judulMateri";
        public static final String COL_TIPE = "tipeMateri";
        public static final String COL_KONTEN = "kontenMateri";
    }
    public static final class Soal implements BaseColumns {
        public static final String TABLE_NAME = "Soal";
        public static final String COL_ID = "idSoal";
        public static final String COL_SOAL = "soal";
        public static final String COL_TIPE = "tipeSoal";
        public static final String COL_JAWABAN = "jawaban";
        public static final String COL_PILIHAN_JAWABAN = "pilihanJawaban";
        public static final String COL_LEVEL_SOAL = "levelSoal";
    }
    public static final class Statistik implements BaseColumns {
        public static final String TABLE_NAME = "Statistik";
        public static final String COL_ID = "idStatistik";
        public static final String COL_LEVEL_SOAL = "levelSoalStatistik";
        public static final String COL_BENAR = "benarStatistik";
        public static final String COL_SALAH = "salahStatistik";
        public static final String COL_TIPE = "tipeStatistik";
    }
    public static final class Progress implements BaseColumns {
        public static final String TABLE_NAME = "Progress";
        public static final String COL_ID = "idProgress";
        public static final String COL_PROGRESS = "progress";
        public static final String COL_TIPE = "tipeProgress";
    }
}

