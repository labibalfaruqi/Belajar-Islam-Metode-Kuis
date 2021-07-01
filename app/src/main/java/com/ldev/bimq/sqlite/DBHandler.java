package com.ldev.bimq.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "dbuser";
    public static final int DB_VERSION = 1;
    private static final String TAG = "Checker DBHandler";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //    @Override
    public void onCreate(SQLiteDatabase db) {
        String TAG = "CheckerDBHandler";

        //create table
        String queryMateri = "CREATE TABLE " + Table.Materi.TABLE_NAME + " ( " +
                Table.Materi.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Table.Materi.COL_JUDUL + " VARCHAR(255)," +
                Table.Materi.COL_TIPE + " VARCHAR(255)," +
                Table.Materi.COL_KONTEN + " VARCHAR(255))";
        Log.d("Data", "onCreate: " + queryMateri);
        db.execSQL(queryMateri);
        String querySoal = "CREATE TABLE " + Table.Soal.TABLE_NAME + " ( " +
                Table.Soal.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Table.Soal.COL_SOAL + " VARCHAR(255)," +
                Table.Soal.COL_TIPE + " VARCHAR(255)," +
                Table.Soal.COL_JAWABAN + " VARCHAR(255)," +
                Table.Soal.COL_PILIHAN_JAWABAN + " VARCHAR(255)," +
                Table.Soal.COL_LEVEL_SOAL + " INTEGER)";
        Log.d("Data", "onCreate: " + querySoal);
        db.execSQL(querySoal);
        String queryStatistik = "CREATE TABLE " + Table.Statistik.TABLE_NAME + " ( " +
                Table.Statistik.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Table.Statistik.COL_LEVEL_SOAL + " INTEGER," +
                Table.Statistik.COL_BENAR + " INTEGER," +
                Table.Statistik.COL_SALAH + " INTEGER," +
                Table.Statistik.COL_TIPE + " VARCHAR(255))";
        Log.d("Data", "onCreate: " + queryStatistik);
        db.execSQL(queryStatistik);
        String queryProgress = "CREATE TABLE " + Table.Progress.TABLE_NAME + " ( " +
                Table.Progress.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Table.Progress.COL_PROGRESS + " VARCHAR(255)," +
                Table.Progress.COL_TIPE + " VARCHAR(255))";
        Log.d("Data", "onCreate: " + queryProgress);
        db.execSQL(queryProgress);

        String[] tipe = {"Materi Aqidah", "Materi Fiqh", "Materi Siroh"
                , "Pilihan GandaMateri Aqidah", "Pilihan GandaMateri Fiqh", "Pilihan GandaMateri Siroh"
                , "Benar SalahMateri Aqidah", "Benar SalahMateri Fiqh", "Benar SalahMateri Siroh"};
        String[][][] kontenMateri = {
                {//aqidah
                        {"Aqidah secara bahasa artinya ketetapan yang tidak ada keraguan pada orang yang mengambil keputusan", "Terdapat 3 macam tauhid; Rububiyyah, Uluhiyyah, Asma' wa Sifat", "Tauhid Rububiyyah: mengesakan Allah dalam segala perbuatan-Nya", "Tauhid Uluhiyyah: mengesakan Allah melalui perbuatan para hamba", " Tauhid Asma wa Sifat: Keyakinan bahwa Allah mempunyai Asma-ul Husna dan sifat-sifat yang mulia"}
                        , {"Beriman kepada para Malaikat yaitu mengimani keberadaannya dengan keimanan yang teguh lagi kokoh, tidak tergoyahkan oleh keraguan ataupun kebimbangan", "Beriman kepada Kitab-kitab-Nya yaitu mengimani bahwa Allah menurunkan kepada para Rasul-Nya kitab-kitab", "Isi dari kitab-kitab itu adalah perintah, larangan, janji, ancaman, dan apa-apa yang dikehendaki Allah dari makhluk-Nya", "Beriman kepada para Rasul-Nya yaitu meyakini Allah mengutus para Rasul-Nya kepada para hamba-Nya sebagai pemberi kabar gembira juga peringatan, dan sebagai penyeru kepada agama yang haq", "Beriman kepada hari Akhir yaitu beriman kepada apa yang Allah telah kabarkan tentang hal itu dalam Kitab-Nya dan apa yang dikabarkan tentang segala hal yang akan terjadi setelah mati hingga penghuni surga masuk surga dan penghuni neraka masuk neraka"}
                        , {"Beriman kepada Takdir yaitu segala sesuatu yang baik atau buruk terjadi dengan takdir dan ketentuan Allah", "Tanda tanda kecil hari kiamat: Diutusnya Rasulullah, Pemalsuan hadits dengan mengatasnamakan Rasulullah, banyak terjadi pembunuhan", "munculnya alat musik, arak, perzinaan, riba", "disiasiakannya amanah, memberikan salam hanya kepada orang yang dikenal saja, kurang bersyukur", "waktu terasa cepat, sedikitnya keberkahan dalam waktu, penaklukan kota romawi, dan tanda tanda lainnya"}
                        , {"Tanda besar hari kiamat: munculnya Imam Mahdi", "Keluarnya al-Masih ad-Dajjal, turunnya Nabi Isa bin Maryam", "Keluarnya Ya'juj dan Ma'juj, terbenamnya salah satu bagian bumi di timur, barat, dan di jazirah arab", "Keluarnya asap tebal", "Terbitnya matahari dari barat"}
                        , {"Maratibul Qadr (tingkatan takdir) atau rukun takdir:", "Al Ilm (Ilmu): Beriman bahwa Allah Maha Mengetahui segala sesuatu yang telah, sedang, atau akan terjadi ", "Al Kitabah: Beriman bahwa Allah telah mencatat segala sesuatu dalam Lauhul Mahfuzh", "Al Iradah wal Masyi'ah: Beriman bahwa sesuatu yang terjadi di alam ini adalah dengan keinginan dan kehendak Allah", "Al Khalq (Penciptaan): Beriman bahwa Allah pencipta segala sesuatu"}
                },
                {//fiqh
                        {"Thaharah adalah menghilangkan hal-hal yang dapat menghalangi shalat berupa hadats atau najis dengan menggunakan (air atau selainnya), atau mengangkat hukum najis itu dengan tanah", "Najis adalah lawan kata dari thaharah, najis merupakan istilah untuk suatu benda yang kotor secara syar'i", "Wudhu merupakan penggunaan air pada anggota-anggota tubuh tertentu (yaitu wajah, dua tangan, kepala, dan dua kaki) untuk menghilangkan apa yang menghalangi seseorang dari melaksanakan shalat dan ibadah yang lain.", "Tidak sah shalat tanpa bersuci, jika dia mampu untuk melakukannya. Hadits tentang \"sholat tidak diterima tanpa bersuci \" HR. Muslim", "Mensucikan diri dari hadats hukumnya adalah wajib untuk dapat melaksanakan shalat"}
                        , {"Rukun-rukun wudhu: mencuci seluruh wajah, mencuci kedua tangan hingga siku, ", "mengusap kepala (Al-Maidah:6), mengusap kedua telinga, ", "mencuci kedua kaki hingga mata kaki, ", "tertib (membersihkannya secara berurutan), ", "Al-Muwalat (berturut-turut; sebelum anggota wudhu lainnya mengering, membasuh lagi anggota wudhu lainnya dalam waktu yang normal)"}
                        , {"Sunnah wudhu: bersiwak, membaca tasmiyah (basmalah), mencuci kedua telapak tangan sebelum wudhu", "berkumur-kumur dan instinsyaq (memasukkan air ke dalam hidung) dari satu telapak tangan 3x, menambah basuhan melebihi bagian yang wajib dibasuh", "bersungguh-sungguh memasukkan air ke dalam hidung dan mengeluarkannya (kecuali bagi orang yang sedang berpuasa)", "mendahulukan yang sebelah kanan dari yang kiri, mencuci anggota-anggota wudhu sebanyak 3x, menyela-nyela jenggot yang lebat", "menggosok anggota wudhu, menyela-nyela jari-jari tangan dan kaki, hemat dalam menggunakan air, berdoa setelah wudhu, shalat dua rakaat setelah wudhu"}
                        , {"Perkara yang tidak membatalkan wudhu: Laki-laki menyentuh wanita dengan tanpa pembatas (Pendapat imam syafi'i)", "keluarnya mani, wadi, dan madzi", "tidur yang lelap hingga tidak sadarkan diri", "hilangnya akal disebabkan mabuk, pingsan, atau gila", "menyentuh kemaluan tanpa alas, baik dengan syahwat maupun tanpa syahwat dan memakan daging unta. Hadits Ummu Habibah, Rasulullah bersabda: \"Barangsiapa yang menyentuh kemaluannya, hendaklah ia berwudhu\""}
                        , {"Perkara yang tidak membatalkan wudhu: Laki-laki menyentuh wanita dengan tanpa pembatas (Pendapat imam syafi'i)", "Keluarnya darah sedikit atau banyak dari tempat yang tidak biasanya (karena luka atau bekam dan sejenisnya)", "Muntah", "Tertawa terbahak-bahak", "Memandikan jenazah dan mengusungnya (menggotongnya)"}
                },
                {//siroh
                        {"Rasulullah dilahirkan tepat pada tahun Gajah", "Ayah Rasulullah meninggal ketika beliau masih dalam kandungan ibunya", "Ibu susu yang menyusui beliau bernama Halimah as-Sa'diyyah", "Rasulullah tinggal di kabilah tersebut selama kurang lebih 4 tahun", "Terjadi juga pembelahan dada Rasulullah oleh malaikat jibril di kampung tersebut"}
                        , {"Pada musim haji Rasulillah bertemu dengan 6 orang Anshar di daerah bernama Aqabah", "Pada tahun berikutnya datanglah 12 orang laki-laki, dari suku Aus dan Khazraj", "Pada baiat Aqabah kedua datanglah 73 orang laki-laki dan 2 orang perempuan", "Yang pertama kali berbai'at kepada beliau pada malam itu adalah al-Barra bin Ma'rur", "2 orang perempuan yang berbai'at kepada beliau pada malam itu adalah Ummu 'Umarah Nasibah binti Ka'ab bin 'Amr & Asma binti 'Amr bin 'Adi bin Nabi"}
                        , {"Semenjak peristiwa Baiat Aqabah, Kaum Muslimin secara berturut turut keluar berhijrah", "Orang pertama yang berhijrah dari penduduk Makkah adalah Abu Salamah bin 'Abdul Asad bersama isterinya", "Sehingga kaum muslimin yang masih tinggal di kota Makkah hanya Rasulullah, Abu Bakar, dan Ali", "Orang yang disewa untuk penunjuk jalan yang terampil adalah Abdullah bin Uraiqith", "Dijanjikannya 100 ekor unta bagi siapa saja yang menangkap Muhammad atau Abu Bakar"}
                        , {"Pada hari Senin, tanggal 12 Rabi'ul Awwal, yaitu setelah 13 tahun masa kenabian, akhirnya Rasulullah sampai ke Madinah", "Orang yang pertama kali melihat beliau adalah seorang Yahudi", "Rasulullah sempat singgah di kampung Quba', di rumah Kultsum bin al-Hadm, ada riwayat yang menyebutkan bahwa beliau singgah di rumah Sa'ad bin Khaitsamah", "Ketika sampai di Madinah, mereka mengira Abu Bakar adalah Nabi karena melihat ubannya banyak", "Namun ketika cahaya matahari sudah terik, Abu Bakar berdiri memayungi Nabi dengan sehelai kain, sehingga saat itulah kaum Muslimin mengetahui mana yang sebenarnya Rasulullah"}
                        , {"Pada saat di kampung Quba, ada yang mengatakan 14 hari lamanya, pada saat itu juga Rasulullah membangun masjid Quba'", "Kemudian melanjutkan perjalanan dan mendapatkan waktu Jum'at di perkampungan Bani Salim bin 'Auf dan sholat di masjid di Lembah Ranuna", "Unta yang dinaiki Rasulullah duduk di perkampungan Bani an-Najjar", "Rasulullah membeli tanah yang akan menjadi tempat didirikannya masjid Nabawi yang sebelumnya tanah itu milik 2 orang anak Yatim yang digunakan untuk tempat pengeringan kurma", "Sementara itu Ali masih tinggal di Makkah untuk mengembalikan barang-barang yang pernah dititipkan kepada Nabi dan melakukan beberapa tugas lain"}
                }};
        String[][][] kontenSoal = {
                {//aqidah PG Level 1
                        {"Aqidah secara bahasa artinya ketetapan yang tidak ada", "Keraguan", "Keyakinan", "Kemalasan", "Kebohongan"}
                        , {"Ada 3 macam tauhid, kecuali:", "Ushuliyyah", "Uluhiyyah", "Asma' wa Sifat", "Rububiyyah"}
                        , {"Tauhid Rububiyyah: mengesakan Allah dalam", "segala perbuatan-Nya", "perbuatan para hamba", "Asma-ul Husna dan sifat-sifat yang mulia", "perintah para hamba"}
                        , {"Tauhid Uluhiyyah: mengesakan Allah melalui", "perbuatan para hamba", "segala perbuatan-Nya", "Asma-ul Husna dan sifat-sifat yang mulia", "perintah para hamba"}
                        , {"Tauhid Asma wa Sifat: Keyakinan bahwa Allah mempunyai", "Asma-ul Husna dan sifat-sifat yang mulia", "perbuatan para hamba", "segala perbuatan-Nya", "perintah para hamba"}
                },
                {//aqidah PG Level 2
                        {"Beriman kepada para Malaikat yaitu mengimani …. dengan keimanan yang teguh lagi kokoh", "Keberadaannya", "Maknawi", "Kekuatan", "Kekuatan yang tersembunyi"}
                        , {"Kepada siapa Allah menurunkan kitab-kitab", "Rasul-Nya", "Sahabat", "Tabiin", "Tabiut Tabiin"}
                        , {"Isi dari kitab-kitab itu meliputi, kecuali", "tulisan", "perintah", "larangan", "janji"}
                        , {"Beriman kepada para Rasul-Nya yaitu meyakini Allah mengutus para Rasul-Nya kepada para hamba-Nya sebagai, kecuali:", "sebagai penyeru kepada agama yang batil", "pemberi kabar gembira", "sebagai penyeru kepada agama yang haq", "pemberi peringatan"}
                        , {"Beriman kepada hari akhir meliputi, kecuali:", "Terbitnya matahari dari barat", "Penghuni neraka", "Penghuni Surga", "Segala Hal yang terjadi setelah mati"}
                },
                {//aqidah PG Level 3
                        {"Beriman kepada …. yaitu segala sesuatu yang baik atau buruk terjadi dengan takdir dan ketentuan Allah", "Takdir", "Hari Akhir", "Rasul-Nya", "Allah"}
                        , {"Salah satu tanda kecil hari kiamat", "Diutusnya Rasulullah", "Munculnya Imam Mahdi", "Keluarnya Ya'Juj dan Ma'juj", "Terbitnya matahari dari barat"}
                        , {"Salah satu tanda kecil hari kiamat", "munculnya alat musik", "munculnya alat perkakas", "munculnya Imam Mahdi", "Turunnya Nabi Isa bin Maryam"}
                        , {"Salah satu tanda kecil hari kiamat", "kurang bersyukur", "banyak bersyukur", "Keluarnya Ya'Juj dan Ma'juj", "Terbitnya matahari dari barat"}
                        , {"Salah satu tanda kecil hari kiamat", "Penaklukan kota romawi", "Penaklukan kota mekah", "Penaklukan kota madinah", "Penaklukan kota Riyadh"}
                },
                {//aqidah PG Level 4
                        {"Salah satu tanda besar hari kiamat", "munculnya Imam Mahdi", "Munculnya arak", "Munculnya alat musik", "Munculnya perzinaan"}
                        , {"Salah satu tanda besar hari kiamat", "Keluarnya al-Masih ad-Dajjal", "Waktu terasa cepat", "Munculnya alat musik", "Munculnya perzinaan"}
                        , {"Salah satu tanda besar hari kiamat", "Keluarnya Ya'juj dan Ma'juj", "Waktu terasa cepat", "Munculnya alat musik", "Munculnya perzinaan"}
                        , {"Salah satu tanda besar hari kiamat keluarnya", "Asap tebal", "Asap tipis", "Awan tebal", "Awan tipis"}
                        , {"Salah satu tanda besar hari kiamat terbitnya matahari dari", "Barat", "Timur", "Utara", "Selatan"}
                },
                {//aqidah PG Level 5
                        {"Tingkatan takdir atau yang disebut", "Maratibul Qadr", "Mawafiul Qadr", "Martibul Qadr", "Mawafiqul Qadr"}
                        , {"Beriman bahwa Allah Maha Mengetahui segala sesuatu yang telah, sedang, atau akan terjadi merupakan definisi dari", "Al Ilm (Ilmu)", "Al Kitabah", "Al Iradah wal Masyi'ah", "Al Khalq (Penciptaan)"}
                        , {"Beriman bahwa Allah telah mencatat segala sesuatu dalam Lauhul Mahfuzh merupakan definisi dari", "Al Kitabah", "Al Ilm (Ilmu)", "Al Iradah wal Masyi'ah", "Al Khalq (Penciptaan)"}
                        , {"Beriman bahwa sesuatu yang terjadi di alam ini adalah dengan keinginan dan kehendak Allah merupakan definisi dari", "Al Iradah wal Masyi'ah", "Al Kitabah", "Al Ilm (Ilmu)", "Al Khalq (Penciptaan)"}
                        , {"Beriman bahwa Allah pencipta segala sesuatu merupakan definisi dari", "Al Khalq (Penciptaan)", "Al Kitabah", "Al Iradah wal Masyi'ah", "Al Ilm (Ilmu)"}
                },
                {//Fiqh PG Level 1
                        {"Thaharah adalah menghilangkan hal-hal yang dapat menghalangi", "Sholat", "Syahadat", "Zakat", "Puasa"}
                        , {"Lawan kata dari thoharoh adalah", "najis", "nifak", "suci", "tinja"}
                        , {"Hukum bersuci sebelom sholat", "wajib", "haram", "sunnah", "makruh"}
                        , {"Anggota wudhu meliputi, kecuali:", "leher", "wajah", "tangan", "kaki"}
                        , {"Riwayat Hadits tentang sholat tidak sah", "muslim", "bukhori", "At Tirmidzi", "An Nasai"}
                },
                {//Fiqh PG Level 2
                        {"Salah satu rukun wudhu, kecuali: ", "Tangan pergelangan", "Mengusap kepala", "Mengusap telinga", "Mencuci seluruh wajah"}
                        , {"dalil mengusap kepala", "Al-Maidah:6", "Al-Maidah:5", "Al-Maidah:7", "Al-Maidah:8"}
                        , {"Kaki wajib dicuci sampai", "Mata kaki", "betis", "lutut", "paha"}
                        , {"apa arti tartib", "berurutan", "berturut-turut", "lancar", "siap"}
                        , {"Apa istilah berwudhu berturut-turut", "muwalat", "tertib", "mumtaz", "muwashol"}
                },
                {//Fiqh PG Level 3
                        {"Apa istilah untuk memasukkan air ke hidung", "istinsyak", "bernafas", "instinsyur", "instinbar"}
                        , {"Apa hukum membasuh melebihi yang diwajibkan", "Sunnah", "Wajib", "Haram", "Makruh"}
                        , {"Sunnah nya berapa kali cuci anggota wudhu", "3x", "1x", "2x", "7x"}
                        , {"Yang bukan merupakan sunnah wudhu", "Menggosok leher", "Menyela jenggot", "Menyela jari-jarinya", "Menggosok anggota wudhu"}
                        , {"Pernyataan ini termasuk sunnah wudhu", "Sholat 2 rakaat", "Sholat 4 rakaat", "Air berlebih", "Doa Sebelom wudhu"}
                },
                {//Fiqh PG Level 4
                        {"Yang membatalkan wudhu kecuali", "Muntah", "Keluar air seni", "Keluar tinja", "Keluar angin dari 2 jalan"}
                        , {"Yang tidak membatalkan wudhu", "Sendawa", "Mani", "Wadi", "Madzi"}
                        , {"Salah satu yang membatalkan wudhu", "Tidur", "Main", "Makan", "Minum"}
                        , {"Salah satu yang membatalkan wudhu", "Hilang akal", "hilang uang", "hilang penciuman", "hilang perasaan"}
                        , {"Hewan apakah yang apabila kita memakannya maka wudhu menjadi batal", "unta", "sapi", "kuda", "gajah"}
                },
                {//Fiqh PG Level 5
                        {"Ulama yang berpendapat tidak membatalkan wudhu jika bersentuhan dengan wanita tanpa pembatas", "imam syafi'i", "malik", "Ahmad", "Hanifah"}
                        , {"Yang termasuk membatalkan wudhu, kecuali:", "haid", "keluar darah luka", "bekam", "suntik"}
                        , {"Salah satu yang tidak membatalkan wudhu", "Muntah", "BAB", "BAK", "Kentut"}
                        , {"Yang membatalkan wudhu", "Keluar angin", "Makan", "Minum", "Tertawa"}
                        , {"Apakah memandikan jenazah membatalkan wudhu", "Tidak", "Ya", "Tidak Tahu", "Boleh ya Boleh tidak"}
                },
                {//Siroh PG Level 1
                        {"Rasulullah dilahirkan tepat pada tahun", "Gajah", "Tikus", "Harimau", "Buaya"}
                        , {"Kapan Ayah Rasulullah meninggal", "Ketika Rasulullah masih dalam kandungan ibunya", "Ketika Rasulullah sudah meninggal", "Ketika Ibu Rasulullah sudah meninggal", "Ketika Ayah Rasulullah masih dalam kandungan ibunya"}
                        , {"Ibu susu yang menyusui beliau bernama", "Halimah as-Sa'diyyah", "Ummu Umarah Nasibah", "Asma binti 'Amr", "Halimatussaudiyah"}
                        , {"Rasulullah tinggal di kabilah Sa'diyah selama", "4 tahun", "3 tahun", "2 tahun", "1 tahun"}
                        , {"Terjadi pembelahan dada Rasulullah oleh", "malaikat jibril", "malaikat isrofil", "malaikat maut", "malaikat ridwan"}
                },
                {//Siroh PG Level 2
                        {"Pada musim haji Rasulillah bertemu dengan berapa orang Anshar di daerah bernama Aqabah ", "6 orang ", "5 orang ", "3 orang ", "10 orang "}
                        , {"Pada tahun berikutnya datanglah 12 orang laki-laki, dari suku ", "Aus dan Khazraj", "al-Asyhal dan Khazraj", "Nadzir dan Qaynuqa", "Nadzir dan Khazraj"}
                        , {"Pada baiat Aqabah kedua datanglah (laki-laki dan perempuan)", "75 orang", "100 orang", "60 orang", "50 orang"}
                        , {"Siapa orang yang pertama kali berbai'at pada bai'at aqabah kedua", "al-Barra bin Ma'rur", "Ka'ab bin 'Amr", "Habib bin Zaid", "Zaid bin Ma'rur"}
                        , {"Siapa orang perempuan yang berbai'at kepada beliau pada malam itu", "Ummu 'Umarah Nasibah binti Ka'ab bin 'Amr", "Asma binti 'Adi bin 'Amr", "Ummu 'Umarah Nasibah binti 'Amr bin Ka'ab", "Nasibah binti 'Amr bin Ka'ab"}
                },
                {//Siroh PG Level 3
                        {"Peristiwa apa yang menjadikan kaum muslimin secara berturut-turut keluar berhijrah", "Baiat Aqabah", "Baiat Makkah", "Baiat Madinah", "Baiat Riyadh"}
                        , {"Orang pertama yang berhijrah dari penduduk Makkah adalah", "Abu Salamah bin 'Abdul Asad bersama isterinya", "'Abdul Asad", "'Abdul Asad bersama isterinya", "Abdullah bin Amr bin Haram"}
                        , {"Setelah banyak yang hijrah, kaum muslimin yang masih tinggal di kota Makkah kecuali", "Abu Salamah bin 'Abdul Asad", "Rasulullah", "Abu Bakar", "Ali"}
                        , {"Siapa orang yang disewa untuk penunjuk jalan", "Abdullah bin Uraiqith", "Abdullah bin Amr bin Haram", "Nadzir bin Qaynuqa", "Aus bin Khazraj"}
                        , {"Berapa unta yang dijanjikan bagi siapa saja yang menangkap Muhammad atau Abu Bakar", "100 ekor", "150 ekor", "200 ekor", "50 ekor"}
                },
                {//Siroh PG Level 4
                        {"Kapan Rasulullah sampai ke Madinah", "Senin, 12 Rabi'ul Awwal", "Selasa, 17 Rajab", "Senin, 17 Ramadhan", "Kamis, 12 Syawal"}
                        , {"Siapa orang yang pertama kali melihat Rasulullah", "seorang Yahudi", "seorang Nasrani", "seorang Muslim", "seorang majusi"}
                        , {"Rasulullah sempat singgah di kampung", "Quba'", "Riyadh", "Makkah", "Tsaur"}
                        , {"Siapa yang awalnya dikira Nabi ketika sampai ke Madinah", "Abu Bakar", "Umar bin Khathab", "Utsman bin Affan", "Ali bin Abi Thalib"}
                        , {"Kapan kaum Muslimin mengetahui mana yang sebenarnya Rasulullah", "cahaya matahari sudah terik", "matahari sudah terbit dari barat", "matahari sudah tidak terbit", "cahaya matahari tidak memacarkan cahayanya"}
                },
                {//Siroh PG Level 5
                        {"Pada saat di kampung Quba, masjid apa yang dibangun Rasulullah", "Masjid Quba", "Masjid Ar-Rahman", "Masjid Baiturrahman", "Masjid Haram"}
                        , {"Di masjid apa Rasulullah sholat Jum'at pada saat beliau hijrah", "di masjid di Lembah Ranuna", "di masjid di Lembah Rananza", "di masjid di Lembah Ranum", "di masjid di Lembah Ran"}
                        , {"Di perkampungan mana unta yang dinaiki Rasulullah duduk", "Bani an-Najjar", "Bani Aus", "Bani Qaynuqa", "Bani Khazraj"}
                        , {"Rasulullah membeli tanah yang akan menjadi tempat didirikannya", "Masjid Nabawi", "Masjidil Haram", "Masjid Quba", "Masjid Tsaur"}
                        , {"Siapa yang akhirnya masih tinggal di Makkah", "Ali", "Abu Bakar", "Umar", "Utsman"}
                },
                {//aqidah BS Level 1
                        {"Aqidah secara bahasa artinya ketetapan yang tidak ada keraguan pada orang yang mengambil keputusan", "Benar"}
                        , {"Terdapat 3 macam tauhid; Rububiyyah, Ushuliyyah, Asma' wa Sifat", "Salah"}
                        , {"Tauhid Rububiyyah: mengesakan Allah dalam segala perbuatan-Nya", "Benar"}
                        , {"Tauhid Asma wa Sifat: mengesakan Allah melalui perbuatan para hamba", "Salah"}
                        , {" Tauhid Asma wa Sifat: Keyakinan bahwa Allah mempunyai Asma-ul Husna dan sifat-sifat yang mulia", "Benar"}
                },
                {//aqidah BS Level 2
                        {"Beriman kepada para Malaikat yaitu mengimani maknawi dengan keimanan yang teguh lagi kokoh", "Salah"}
                        , {"Beriman kepada Kitab-kitab-Nya yaitu mengimani bahwa Allah menurunkan kepada para Rasul-Nya kitab-kitab", "Benar"}
                        , {"Salah satu isi dari kitab-kitab itu adalah tulisan", "Salah"}
                        , {"Beriman kepada para Rasul-Nya yaitu meyakini Allah mengutus para Rasul-Nya kepada para hamba-Nya", "Benar"}
                        , {"Beriman kepada hari Akhir yaitu beriman kepada apa yang Allah telah kabarkan tentang hal itu dalam Kitab-Nya dan apa yang dikabarkan tentang segala hal yang akan terjadi setelah mati hingga penghuni surga masuk neraka dan penghuni neraka masuk surga", "Salah"}
                },
                {//aqidah BS Level 3
                        {"Beriman kepada Takdir yaitu segala sesuatu yang baik atau buruk terjadi dengan takdir dan ketentuan Allah", "Benar"}
                        , {"Salah satu tanda kecil hari kiamat adalah terbitnya matahari dari barat", "Salah"}
                        , {"Salah satu tanda kecil hari kiamat munculnya alat musik", "Benar"}
                        , {"Salah satu tanda kecil hari kiamat kurang bersyukur", "Benar"}
                        , {"Salah satu tanda kecil hari kiamat Penaklukan kota mekah", "Salah"}
                },
                {//aqidah BS Level 4
                        {"Salah satu tanda besar hari kiamat Munculnya perzinaan", "Salah"}
                        , {"Salah satu tanda besar hari kiamat keluarnya al-Masih ad-Dajjal", "Benar"}
                        , {"Salah satu tanda besar hari kiamat keluarnya Ya'juj dan Ma'juj", "Benar"}
                        , {"Salah satu tanda besar hari kiamat keluarnya asap tipis", "Salah"}
                        , {"Salah satu tanda besar hari kiamat terbitnya matahari dari barat", "Benar"}
                },
                {//aqidah BS Level 5
                        {"Tingkatan takdir biasa disebut juga Mawafiul Qadr", "Salah"}
                        , {"Al Ilm (Ilmu): Beriman bahwa Allah Maha Mengetahui segala sesuatu yang telah, sedang, atau akan terjadi", "Benar"}
                        , {"Al Kitabah: Beriman bahwa Allah telah mencatat segala sesuatu dalam Lauhul Mahfuzh", "Benar"}
                        , {"Al Khalq (Penciptaan): Beriman bahwa sesuatu yang terjadi di alam ini adalah dengan keinginan dan kehendak Allah", "Salah"}
                        , {"Al Khalq (Penciptaan): Beriman bahwa Allah pencipta segala sesuatu", "Benar"}
                },
                {//Fiqh BS Level 1
                        {"Lawan kata dari thoharoh adalah najis", "Benar"}
                        , {"Thaharoh adalah menghilangkan hal hal yang dapat menghalangi syahadat", "Salah"}
                        , {"Hukum bersuci sebelum sholat adalah wajib", "Benar"}
                        , {"Riwayat hadits tentang sholat tidak sah diriwayatkan Bukhori", "Salah"}
                        , {"Salah satu rukun wudhu yaitu mengusap kepala", "Salah"}
                },
                {//Fiqh BS Level 2
                        {"Salah satu rukun wudhu yaitu mengusap kepala", "Salah"}
                        , {"dalil mengusap kepala adalah Al-Maidah:6", "Benar"}
                        , {"kaki wajib dicuci sampai betis", "Salah"}
                        , {"tertib itu membersihkannya secara berturut-turut", "Salah"}
                        , {"Al-Muwalat itu membersihkannya secara berurutan", "Salah"}
                },
                {//Fiqh BS Level 3
                        {"Bersiwak merupakan salah satu sunnah wudhu", "Benar"}
                        , {"Hukum membasuh melebihi yang diwajibkan merupakan hal yang wajib", "Salah"}
                        , {"Mendahulukan yang sebelah kanan dari yang kiri", "Benar"}
                        , {"Yang merupakan sunnah wudhu adalah menyela jenggot", "Benar"}
                        , {"Ketika berwudhu, disunnahkan untuk berhemat dalam menggunakan air", "Benar"}
                },
                {//Fiqh BS Level 4
                        {"Keluarnya air seni tidak membatalkan wudhu", "Salah"}
                        , {"Keluarnya mani, wadi, dan madzi membatalkan wudhu", "Benar"}
                        , {"Tidur yang lelap hingga tidak sadarkan diri membatalkan wudhu", "Benar"}
                        , {"Mabuk tidak membatalkan wudhu", "Salah"}
                        , {"Memakan daging unta membatalkan wudhu", "Benar"}
                },
                {//Fiqh BS Level 5
                        {"Imam Malik yang berpendapat tidak membatalkan wudhu jika bersentuhan dengan wanita tanpa pembatas", "Salah"}
                        , {"Keluarnya darah dari luka membatalkan wudhu", "Salah"}
                        , {"Muntah tidak membatalkan wudhu", "Benar"}
                        , {"Tertawa terbahak-bahak membatalkan wudhu", "Salah"}
                        , {"Memandikan jenazah membatalkan wudhu", "Salah"}
                },
                {//Siroh BS Level 1
                        {"Rasulullah dilahirkan tepat pada tahun gajah", "Benar"}
                        , {"Ayah Rasulullah meninggal ketika Ayah Rasulullah masih dalam kandungan ibunya", "Salah"}
                        , {"Ibu susu yang menyusui beliau bernama Asma binti 'Amr", "Salah"}
                        , {"Rasulullah tinggal di kabilah tersebut selama kurang lebih 4 tahun", "Benar"}
                        , {"Terjadi juga pembelahan dada Rasulullah oleh malaikat isrofil di kampung tersebut", "Salah"}
                },
                {//Siroh BS Level 2
                        {"Pada musim haji Rasulillah bertemu dengan 4 orang Anshar di daerah bernama Aqabah", "Salah"}
                        , {"Pada tahun berikutnya datanglah 12 orang laki-laki, dari suku Aus dan Khazraj", "Benar"}
                        , {"Pada baiat Aqabah kedua datanglah 73 orang laki-laki dan 2 orang perempuan", "Benar"}
                        , {"Yang pertama kali berbai'at kepada beliau pada malam itu adalah Ka'ab bin 'Amr", "Salah"}
                        , {"2 orang perempuan yang berbai'at kepada beliau pada malam itu adalah Ummu 'Umarah Nasibah binti 'Amr bin Ka'ab& Asma binti 'Amr bin 'Adi bin Nabi", "Salah"}
                },
                {//Siroh BS Level 3
                        {"Semenjak peristiwa Baiat Aqabah, Kaum Muslimin secara berturut turut keluar berhijrah", "Benar"}
                        , {"Orang pertama yang berhijrah dari penduduk Makkah adalah Abu Salamah bin 'Abdul Asad bersama isterinya", "Benar"}
                        , {"Sehingga kaum muslimin yang masih tinggal di kota Makkah hanya Rasulullah dan Abu Bakar", "Salah"}
                        , {"Orang yang disewa untuk penunjuk jalan yang terampil adalah Abdullah bin Amr bin Haram", "Salah"}
                        , {"Dijanjikannya 100 ekor unta bagi siapa saja yang menangkap Muhammad atau Abu Bakar", "Benar"}
                },
                {//Siroh BS Level 4
                        {"Pada hari Senin, tanggal Senin, 17 Ramadhan, yaitu setelah 13 tahun masa kenabian, akhirnya Rasulullah sampai ke Madinah", "Salah"}
                        , {"Orang yang pertama kali melihat beliau adalah seorang Majusi", "Salah"}
                        , {"Rasulullah sempat singgah di kampung Tsaur", "Salah"}
                        , {"Ketika sampai di Madinah, mereka mengira Abu Bakar adalah Nabi karena melihat ubannya banyak", "Benar"}
                        , {"ketika cahaya matahari sudah terik, Abu Bakar berdiri memayungi Nabi dengan sehelai kain", "Benar"}
                },
                {//Siroh BS Level 5
                        {"Pada saat di kampung Quba, ada yang mengatakan 14 hari lamanya, pada saat itu juga Rasulullah membangun masjid Haram", "Salah"}
                        , {"Jum'at di perkampungan Bani Salim bin 'Auf dan sholat di masjid di Lembah Ranuna", "Benar"}
                        , {"Unta yang dinaiki Rasulullah duduk di perkampungan Bani Qaynuqa", "Salah"}
                        , {"Rasulullah membeli tanah yang akan menjadi tempat didirikannya Masjid Nabawi", "Benar"}
                        , {"Sementara itu Ali masih tinggal di Makkah", "Benar"}
                }
        };

        //looping insert materi
        int count = 0;
        for (int i = 0; i < kontenMateri.length; i++) {
            for (int j = 0; j < kontenMateri[i].length; j++) {
                if (count == 5) count = 0;
                ++count;
                for (int k = 0; k < kontenMateri[i][j].length; k++) {
                    insertDataMateri(db, count, tipe[i], kontenMateri[i][j][k]);
//                    String queryInsertDataMateri = "INSERT INTO " + Table.Materi.TABLE_NAME + " ( " +
//                            Table.Materi.COL_JUDUL + "," +
//                            Table.Materi.COL_TIPE + "," +
//                            Table.Materi.COL_KONTEN + ") VALUES ('" +
//                            count + "', '" +
//                            tipe[i] + "', '" +
//                            kontenMateri[i][j][k] + "')";
//                    Log.d("Data", "onCreate: " + queryInsertDataMateri);
//                    db.execSQL(queryInsertDataMateri);
                }
            }
        }

        //looping insert soal
        int countLevelSoal = 0;
        for (int i = 0; i < kontenSoal.length; i++) {
            if (countLevelSoal == 5) countLevelSoal = 0;
            Log.d(TAG, "masuk ke #1DBH countLevelSoal: " + countLevelSoal
//                    + ". kontenSoal: " + Arrays.deepToString(kontenSoal)
                    + ". kontenSoal.length: " + kontenSoal.length
                    + ". i: " + i);
            ++countLevelSoal;
            for (int j = 0; j < kontenSoal[i].length; j++) {
                Log.d(TAG, "masuk ke #2DBH kontenSoal[i]: " + Arrays.deepToString(kontenSoal[i])
//                        + ". kontenSoal: " + Arrays.deepToString(kontenSoal)
                        + ". kontenSoal[i].length: " + kontenSoal[i].length
                        + ". j: " + j);
                String[] pilihanJawaban = new String[4];
                if (kontenSoal[i][j].length - 1 >= 0)
                    System.arraycopy(kontenSoal[i][j], 1, pilihanJawaban, 0, kontenSoal[i][j].length - 1);
                Log.d(TAG, "masuk ke #3DBH kontenSoal[i][j]: " + Arrays.deepToString(kontenSoal[i][j])
//                        + ". kontenSoal: " + Arrays.deepToString(kontenSoal)
                        + ". kontenSoal[i][j].length: " + kontenSoal[i][j].length
                        + ". pilihanJawaban: " + Arrays.toString(pilihanJawaban));

                Log.d(TAG, "masuk ke #4DBH kontenSoal[i][j][0]: " + kontenSoal[i][j][0]
//                        + ". tipe[(int) Math.ceil(i + 1 / 5.0) + 2]: " + tipe[(int) Math.ceil(i + 1 / 5.0) + 2]
//                        + ". (int) Math.ceil(i + 1 / 5.0) + 2: " + (int) Math.ceil(i + 1 / 5.0) + 2
                        + ". i: " + i
                        + ". tipe[(int) Math.ceil(((i + 1) / 5.0)) + 2]: " + tipe[(int) Math.ceil(((i + 1) / 5.0)) + 2]
                        + ". (int) Math.ceil(((i + 1) / 5.0)) + 2: " + (int) Math.ceil(((i + 1) / 5.0)) + 2
                        + ". kontenSoal[i][j][1]: " + kontenSoal[i][j][1]
                        + ". pilihanJawaban: " + Arrays.toString(pilihanJawaban)
                        + ". String.valueOf(countLevelSoal)" + countLevelSoal);
                insertDataSoal(db, kontenSoal[i][j][0], tipe[(int) Math.ceil(((i + 1) / 5.0)) + 2], kontenSoal[i][j][1], pilihanJawaban, String.valueOf(countLevelSoal));
            }
        }

    }

    public void insertDataMateri(SQLiteDatabase db, int judul, String tipe, String konten) {
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.Materi.COL_JUDUL, judul);
        contentValues.put(Table.Materi.COL_TIPE, tipe);
        contentValues.put(Table.Materi.COL_KONTEN, konten);
        db.insert(Table.Materi.TABLE_NAME, null, contentValues);
        Log.d("DataMateri", "onCreate: " + contentValues.toString());

//        db.close();
//        String queryInsertDataMateri = "INSERT INTO " + Table.Materi.TABLE_NAME + " ( " +
//                Table.Materi.COL_JUDUL + "," +
//                Table.Materi.COL_TIPE + "," +
//                Table.Materi.COL_KONTEN + ") VALUES ('" +
//                judul + "', '" +
//                tipe + "', '" +
//                konten + "')";

//        db.execSQL(queryInsertDataMateri);
    }

    public void insertDataSoal(SQLiteDatabase db, String soal, String tipe, String jawaban, String[] arrayPilihanJawaban, String levelSoal) {
        ContentValues contentValues = new ContentValues();
        String pilihanJawaban = TextUtils.join("pemisahArray", arrayPilihanJawaban);
//        StringBuilder pilihanJawaban = null;
//        if (arrayPilihanJawaban != null) {
//            for (String s : arrayPilihanJawaban) {
//                pilihanJawaban.append("pemisahArray").append(s);
//            }
//        }
        contentValues.put(Table.Soal.COL_SOAL, soal);
        contentValues.put(Table.Soal.COL_TIPE, tipe);
        contentValues.put(Table.Soal.COL_JAWABAN, jawaban);
        contentValues.put(Table.Soal.COL_PILIHAN_JAWABAN, pilihanJawaban);
        contentValues.put(Table.Soal.COL_LEVEL_SOAL, levelSoal);
        db.insert(Table.Soal.TABLE_NAME, null, contentValues);
//        SQLiteOpenHelper.close();
//        db.close();
        Log.d("DataSoal", "onCreate: " + contentValues.toString());
    }

    public void insertUpdateDataStatistik(int levelSoalStatistik, int benarStatistik, int salahStatistik, String tipeSoal, String judulSoal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String conditionStatistik = Table.Statistik.COL_TIPE + "='" + tipeSoal + judulSoal
                + "' AND " + Table.Statistik.COL_LEVEL_SOAL + "='" + levelSoalStatistik + "'";
        contentValues.put(Table.Statistik.COL_LEVEL_SOAL, levelSoalStatistik);
        contentValues.put(Table.Statistik.COL_BENAR, benarStatistik);
        contentValues.put(Table.Statistik.COL_SALAH, salahStatistik);
        contentValues.put(Table.Statistik.COL_TIPE, tipeSoal + judulSoal);

        Cursor cursorStatistik = getData(Table.Statistik.TABLE_NAME, "*", conditionStatistik);
        if (cursorStatistik != null && cursorStatistik.moveToFirst()) {
            String where = Table.Statistik.COL_LEVEL_SOAL + "=? AND " + Table.Statistik.COL_TIPE + "=?";
            String[] whereArgs = new String[]{String.valueOf(levelSoalStatistik), tipeSoal + judulSoal};

            db.update(Table.Statistik.TABLE_NAME, contentValues, where, whereArgs);
        } else {
            db.insert(Table.Statistik.TABLE_NAME, null, contentValues);
        }
    }

    public void insertDataStatistik(int levelSoalStatistik, int benarStatistik, int salahStatistik, String tipeSoal, String judulSoal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.Statistik.COL_LEVEL_SOAL, levelSoalStatistik);
        contentValues.put(Table.Statistik.COL_BENAR, benarStatistik);
        contentValues.put(Table.Statistik.COL_SALAH, salahStatistik);
        contentValues.put(Table.Statistik.COL_TIPE, tipeSoal + judulSoal);
        db.insert(Table.Statistik.TABLE_NAME, null, contentValues);
    }

    public void insertDataProgress(String progress, String tipeProgress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.Progress.COL_PROGRESS, progress);
        contentValues.put(Table.Progress.COL_TIPE, tipeProgress);
        db.insert(Table.Progress.TABLE_NAME, null, contentValues);
    }

    public void insertUpdateDataProgress(int progress, String tipeSoal, String judulSoal, int levelSoal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int levelProgressDatabase;
        String conditionProgress = Table.Progress.COL_TIPE + "='" + tipeSoal + judulSoal + "'";
        contentValues.put(Table.Progress.COL_PROGRESS, progress);
        contentValues.put(Table.Progress.COL_TIPE, tipeSoal + judulSoal);

        Cursor cursorProgress = getData(Table.Progress.TABLE_NAME, "*", conditionProgress);

        if (cursorProgress != null && cursorProgress.moveToFirst()) {
                levelProgressDatabase = Integer.parseInt(cursorProgress.getString(1));
                if (levelProgressDatabase < levelSoal) {
                    String where = Table.Progress.COL_TIPE + "=?";
                    String[] whereArgs = new String[]{tipeSoal + judulSoal};

                    db.update(Table.Progress.TABLE_NAME, contentValues, where, whereArgs);
                }
        } else {
            db.insert(Table.Progress.TABLE_NAME, null, contentValues);
        }
    }

//    public void setSQLiteFromFirebase(List<List<String>> listofLists) {
//        //delete all row in sqlite table
//        //copy from firebase (list of list), paste it to sqlite
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        int countProgress = 0;
//        int countStatistik = 0;
//        db.delete(Table.Progress.TABLE_NAME, null, null);
//        db.delete(Table.Statistik.TABLE_NAME, null, null);
//        for (int i = 0; i < listofLists.size(); i++) {
//            if (listofLists.get(i).size() >= 4) { // statistik
//                contentValues.put(Table.Statistik.COL_ID, listofLists.get(countProgress).get(0));
//                contentValues.put(Table.Statistik.COL_LEVEL_SOAL, listofLists.get(countProgress).get(1));
//                contentValues.put(Table.Statistik.COL_BENAR, listofLists.get(countProgress).get(2));
//                contentValues.put(Table.Statistik.COL_SALAH, listofLists.get(countProgress).get(3));
//                contentValues.put(Table.Statistik.COL_TIPE, listofLists.get(countProgress).get(4));
//                db.insert(Table.Statistik.TABLE_NAME, null, contentValues);
//                contentValues.clear();
//                countProgress++;
//            } else { // progress
//                contentValues.put(Table.Progress.COL_ID, listofLists.get(countStatistik).get(0));
//                contentValues.put(Table.Progress.COL_PROGRESS, listofLists.get(countStatistik).get(1));
//                contentValues.put(Table.Progress.COL_TIPE, listofLists.get(countStatistik).get(2));
//                db.insert(Table.Progress.TABLE_NAME, null, contentValues);
//                contentValues.clear();
//                countStatistik++;
//            }
//        }
//    }

    public Cursor getData(String table, String column, String condition) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (condition == null) {
            try {
                return db.rawQuery("select " + column + " from " + table, null);
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                return null;
            }
        } else {
            try {
                return db.rawQuery("select " + column + " from " + table + " where " + condition, null);
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                return null;
            }
        }
    }

    public void updateDataStatistik(int levelSoalStatistik, int benarStatistik, int salahStatistik, String tipeStatistik) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.Statistik.COL_LEVEL_SOAL, levelSoalStatistik);
        contentValues.put(Table.Statistik.COL_BENAR, benarStatistik);
        contentValues.put(Table.Statistik.COL_SALAH, salahStatistik);
        contentValues.put(Table.Statistik.COL_TIPE, tipeStatistik);
        String where = Table.Statistik.COL_LEVEL_SOAL + "=? AND " + Table.Statistik.COL_TIPE + "=?";
        String[] whereArgs = new String[]{String.valueOf(levelSoalStatistik), String.valueOf(tipeStatistik)};

        db.update(Table.Statistik.TABLE_NAME, contentValues, where, whereArgs);

//        db.update(Table.Statistik.TABLE_NAME, contentValues, Table.Statistik.COL_LEVEL_SOAL.equals(levelSoalStatistik) + " AND " + Table.Statistik.COL_TIPE.equals(tipeStatistik), null);
//        db.close();
    }

    public void updateDataProgress(String progress, String tipeProgress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.Progress.COL_PROGRESS, progress);
        contentValues.put(Table.Progress.COL_TIPE, tipeProgress);
        String where = Table.Progress.COL_TIPE + "=?";
        String[] whereArgs = new String[]{String.valueOf(tipeProgress)};

        db.update(Table.Progress.TABLE_NAME, contentValues, where, whereArgs);

//        db.update(Table.Progress.TABLE_NAME, contentValues, Table.Progress.COL_PROGRESS.equals(progress) + " AND " + Table.Progress.COL_TIPE.equals(tipeProgress), null);
//        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

