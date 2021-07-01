package com.ldev.bimq;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MateriFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MateriFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MateriFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MateriFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MateriFragment newInstance(String param1, String param2) {
        MateriFragment fragment = new MateriFragment();
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
        return inflater.inflate(R.layout.fragment_materi, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button MButtonAqidah = requireActivity().findViewById(R.id.MButtonAqidah);
        Button MButtonFiqh = requireActivity().findViewById(R.id.MButtonFiqh);
        Button MButtonSiroh = requireActivity().findViewById(R.id.MButtonSiroh);
        MButtonAqidah.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), StartPlayActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("tipeSoal", "Materi Aqidah");
            intent.putExtra("judulSoal", "");
            startActivity(intent);
        });
        MButtonFiqh.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), StartPlayActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("tipeSoal", "Materi Fiqh");
            intent.putExtra("judulSoal", "");
            startActivity(intent);
        });
        MButtonSiroh.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), StartPlayActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("tipeSoal", "Materi Siroh");
            intent.putExtra("judulSoal", "");
            startActivity(intent);
        });
    }
}