package com.ldev.bimq;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ldev.bimq.sqlite.DBHandler;
import com.ldev.bimq.sqlite.Table;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String TAG = "CheckerP";

        TextView PTextViewName = requireActivity().findViewById(R.id.PTextViewName);
        TextView PTextViewEmail = requireActivity().findViewById(R.id.PTextViewEmail);
        TextView PTextViewProgressPGAqidah = requireActivity().findViewById(R.id.PTextViewProgressPGAqidah);
        TextView PTextViewProgressPGFiqh = requireActivity().findViewById(R.id.PTextViewProgressPGFiqh);
        TextView PTextViewProgressPGSiroh = requireActivity().findViewById(R.id.PTextViewProgressPGSiroh);
        TextView PTextViewProgressBSAqidah = requireActivity().findViewById(R.id.PTextViewProgressBSAqidah);
        TextView PTextViewProgressBSFiqh = requireActivity().findViewById(R.id.PTextViewProgressBSFiqh);
        TextView PTextViewProgressBSSiroh = requireActivity().findViewById(R.id.PTextViewProgressBSSiroh);
        TextView PTextViewProgressMAqidah = requireActivity().findViewById(R.id.PTextViewProgressMAqidah);
        TextView PTextViewProgressMFiqh = requireActivity().findViewById(R.id.PTextViewProgressMFiqh);
        TextView PTextViewProgressMSiroh = requireActivity().findViewById(R.id.PTextViewProgressMSiroh);

        PieChart PChartStatistikPGAqidah = requireActivity().findViewById(R.id.PChartStatistikPGAqidah);
        PieChart PChartStatistikPGFiqh = requireActivity().findViewById(R.id.PChartStatistikPGFiqh);
        PieChart PChartStatistikPGSiroh = requireActivity().findViewById(R.id.PChartStatistikPGSiroh);
        PieChart PChartStatistikBSAqidah = requireActivity().findViewById(R.id.PChartStatistikBSAqidah);
        PieChart PChartStatistikBSFiqh = requireActivity().findViewById(R.id.PChartStatistikBSFiqh);
        PieChart PChartStatistikBSSiroh = requireActivity().findViewById(R.id.PChartStatistikBSSiroh);

        DBHandler dbHandler = new DBHandler(requireContext());


        setPieChartStyle(PChartStatistikPGAqidah);
        setPieChartStyle(PChartStatistikPGFiqh);
        setPieChartStyle(PChartStatistikPGSiroh);
        setPieChartStyle(PChartStatistikBSAqidah);
        setPieChartStyle(PChartStatistikBSFiqh);
        setPieChartStyle(PChartStatistikBSSiroh);

        int countKontenStatistik = 0;
        String[] materiProgress = {"Aqidah", "Fiqh", "Siroh"};
        String[] tipeSoal = {"Pilihan GandaMateri Aqidah", "Pilihan GandaMateri Fiqh", "Pilihan GandaMateri Siroh",
                "Benar SalahMateri Aqidah", "Benar SalahMateri Fiqh", "Benar SalahMateri Siroh",
                "Materi Aqidah", "Materi Fiqh", "Materi Siroh"};
        int benarStatistikPGAqidah = 0;
        int salahStatistikPGAqidah = 0;
        int benarStatistikPGFiqh = 0;
        int salahStatistikPGFiqh = 0;
        int benarStatistikPGSiroh = 0;
        int salahStatistikPGSiroh = 0;
        int benarStatistikBSAqidah = 0;
        int salahStatistikBSAqidah = 0;
        int benarStatistikBSFiqh = 0;
        int salahStatistikBSFiqh = 0;
        int benarStatistikBSSiroh = 0;
        int salahStatistikBSSiroh = 0;

        Cursor cursorStatistik = dbHandler.getData(Table.Statistik.TABLE_NAME, "*", null);
        List<List<String>> listOfListsStatistik = null;
        if (cursorStatistik != null && cursorStatistik.moveToFirst()) {
            listOfListsStatistik = new ArrayList<>(cursorStatistik.getCount());
            for (int i = 0; i < cursorStatistik.getCount(); i++) {
                listOfListsStatistik.add(new ArrayList<>());
            }
            Log.d(TAG, String.valueOf(listOfListsStatistik));
            do {
                listOfListsStatistik.get(countKontenStatistik).add(String.valueOf(cursorStatistik.getString(2))); //benar
                listOfListsStatistik.get(countKontenStatistik).add(String.valueOf(cursorStatistik.getString(3))); //salah
                listOfListsStatistik.get(countKontenStatistik).add(String.valueOf(cursorStatistik.getString(4))); //tipe
                countKontenStatistik++;
            } while (cursorStatistik.moveToNext());
        }
        Log.d(TAG, String.valueOf(listOfListsStatistik));

        for (int i = 0; i < countKontenStatistik; i++) {
            if (listOfListsStatistik.get(i).get(2).equals(tipeSoal[0])) {
                benarStatistikPGAqidah += Integer.parseInt(listOfListsStatistik.get(i).get(0));
                salahStatistikPGAqidah += Integer.parseInt(listOfListsStatistik.get(i).get(1));
            }
            if (listOfListsStatistik.get(i).get(2).equals(tipeSoal[1])) {
                benarStatistikPGFiqh += Integer.parseInt(listOfListsStatistik.get(i).get(0));
                salahStatistikPGFiqh += Integer.parseInt(listOfListsStatistik.get(i).get(1));
            }
            if (listOfListsStatistik.get(i).get(2).equals(tipeSoal[2])) {
                benarStatistikPGSiroh += Integer.parseInt(listOfListsStatistik.get(i).get(0));
                salahStatistikPGSiroh += Integer.parseInt(listOfListsStatistik.get(i).get(1));
            }
            if (listOfListsStatistik.get(i).get(2).equals(tipeSoal[3])) {
                benarStatistikBSAqidah += Integer.parseInt(listOfListsStatistik.get(i).get(0));
                salahStatistikBSAqidah += Integer.parseInt(listOfListsStatistik.get(i).get(1));
            }
            if (listOfListsStatistik.get(i).get(2).equals(tipeSoal[4])) {
                benarStatistikBSFiqh += Integer.parseInt(listOfListsStatistik.get(i).get(0));
                salahStatistikBSFiqh += Integer.parseInt(listOfListsStatistik.get(i).get(1));
            }
            if (listOfListsStatistik.get(i).get(2).equals(tipeSoal[5])) {
                benarStatistikBSSiroh += Integer.parseInt(listOfListsStatistik.get(i).get(0));
                salahStatistikBSSiroh += Integer.parseInt(listOfListsStatistik.get(i).get(1));
            }
        }

        if (benarStatistikPGAqidah != 0 || salahStatistikPGAqidah != 0)
            setData(benarStatistikPGAqidah, salahStatistikPGAqidah, PChartStatistikPGAqidah);
        if (benarStatistikPGFiqh != 0 || salahStatistikPGFiqh != 0)
            setData(benarStatistikPGFiqh, salahStatistikPGFiqh, PChartStatistikPGFiqh);
        if (benarStatistikPGSiroh != 0 || salahStatistikPGSiroh != 0)
            setData(benarStatistikPGSiroh, salahStatistikPGSiroh, PChartStatistikPGSiroh);
        if (benarStatistikBSAqidah != 0 || salahStatistikBSAqidah != 0)
            setData(benarStatistikBSAqidah, salahStatistikBSAqidah, PChartStatistikBSAqidah);
        if (benarStatistikBSFiqh != 0 || salahStatistikBSFiqh != 0)
            setData(benarStatistikBSFiqh, salahStatistikBSFiqh, PChartStatistikBSFiqh);
        if (benarStatistikBSSiroh != 0 || salahStatistikBSSiroh != 0)
            setData(benarStatistikBSSiroh, salahStatistikBSSiroh, PChartStatistikBSSiroh);

        int countKontenProgress = 0;
        Cursor cursorProgress = dbHandler.getData(Table.Progress.TABLE_NAME, "*", null);
        List<List<String>> listOfListsProgress = null;
        if (cursorProgress != null && cursorProgress.moveToFirst()) {
            listOfListsProgress = new ArrayList<>(cursorProgress.getCount());
            for (int i = 0; i < cursorProgress.getCount(); i++) {
                listOfListsProgress.add(new ArrayList<>());
            }
            do {
                listOfListsProgress.get(countKontenProgress).add(String.valueOf(cursorProgress.getString(1))); //progress
                listOfListsProgress.get(countKontenProgress).add(String.valueOf(cursorProgress.getString(2))); //tipe
                countKontenProgress++;
            } while (cursorProgress.moveToNext());
        }
        Log.d(TAG, String.valueOf(listOfListsProgress));

        for (int i = 0; i < countKontenProgress; i++) {
            if (listOfListsProgress.get(i).get(1).equals(tipeSoal[0]))
                setTextProgress(PTextViewProgressPGAqidah, materiProgress[0], listOfListsProgress.get(i).get(0));
            if (listOfListsProgress.get(i).get(1).equals(tipeSoal[1]))
                setTextProgress(PTextViewProgressPGFiqh, materiProgress[1], listOfListsProgress.get(i).get(0));
            if (listOfListsProgress.get(i).get(1).equals(tipeSoal[2]))
                setTextProgress(PTextViewProgressPGSiroh, materiProgress[2], listOfListsProgress.get(i).get(0));
            if (listOfListsProgress.get(i).get(1).equals(tipeSoal[3]))
                setTextProgress(PTextViewProgressBSAqidah, materiProgress[0], listOfListsProgress.get(i).get(0));
            if (listOfListsProgress.get(i).get(1).equals(tipeSoal[4]))
                setTextProgress(PTextViewProgressBSFiqh, materiProgress[1], listOfListsProgress.get(i).get(0));
            if (listOfListsProgress.get(i).get(1).equals(tipeSoal[5]))
                setTextProgress(PTextViewProgressBSSiroh, materiProgress[2], listOfListsProgress.get(i).get(0));
            if (listOfListsProgress.get(i).get(1).equals(tipeSoal[6]))
                setTextProgress(PTextViewProgressMAqidah, materiProgress[0], listOfListsProgress.get(i).get(0));
            if (listOfListsProgress.get(i).get(1).equals(tipeSoal[7]))
                setTextProgress(PTextViewProgressMFiqh, materiProgress[1], listOfListsProgress.get(i).get(0));
            if (listOfListsProgress.get(i).get(1).equals(tipeSoal[8]))
                setTextProgress(PTextViewProgressMSiroh, materiProgress[2], listOfListsProgress.get(i).get(0));
        }
    }

    private void setTextProgress(TextView textViewProgress, String materiProgress, String levelProgress) {
        String Progress = materiProgress + " Level " + levelProgress + "/5";
        textViewProgress.setText(Progress);
    }

    private void setPieChartStyle(PieChart pieChart) {
        pieChart.getDescription().setEnabled(false); // disable description text
        pieChart.setUsePercentValues(false);
        Legend l = pieChart.getLegend(); //disable legend
        l.setEnabled(true);
        pieChart.setClickable(false);    //disable click
        pieChart.setTouchEnabled(false);    //disable click
//        pieChart.setEntryLabelColor();
        pieChart.setEnabled(false);
        pieChart.setDrawEntryLabels(false);
//        l.setDescriptionTextSize(7f);
//        pieChart.text
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setRotationAngle(0);
        pieChart.setExtraOffsets(0, 0, 0, -10);
    }

    private void setData(int benar, int salah, PieChart pieChart) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        //add datanya
        if (benar != 0)
            entries.add(new PieEntry((float) benar, "benar", null));
        if (salah != 0)
            entries.add(new PieEntry((float) salah, "salah", null));

        PieDataSet dataSet = new PieDataSet(entries, null);

//        dataSet.setValueFormatter(new YourFormatter());
        dataSet.setDrawIcons(false);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10f);

        dataSet.setColors(new int[]{R.color.hijau_benar, R.color.merah_salah}, requireContext());

        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueFormatter(new YourFormatter());
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }
}