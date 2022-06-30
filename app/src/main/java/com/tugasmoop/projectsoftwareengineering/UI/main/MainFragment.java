package com.tugasmoop.projectsoftwareengineering.UI.main;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currCategory;
import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.SharedData.databaseReference;
import static com.tugasmoop.projectsoftwareengineering.SharedData.listData;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.UI.main.category.CategoryFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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

    LinearLayout television, refrigerator, airConditioner, dispenser, washingMachine, fan, speaker, computer, laptop, handphone;
    TextView welcome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        television = view.findViewById(R.id.television);
        television.setOnClickListener(televisionCategory->toCategory("television"));

        refrigerator = view.findViewById(R.id.refrigerator);
        refrigerator.setOnClickListener(refrigeratorCategory->toCategory("refrigerator"));

        airConditioner = view.findViewById(R.id.airConditioner);
        airConditioner.setOnClickListener(airConditionerCategory->toCategory("air conditioner"));

        dispenser = view.findViewById(R.id.dispenser);
        dispenser.setOnClickListener(dispenserCategory->toCategory("dispenser"));

        washingMachine = view.findViewById(R.id.washingMachine);
        washingMachine.setOnClickListener(washingMachineCategory->toCategory("washing machine"));

        fan = view.findViewById(R.id.fan);
        fan.setOnClickListener(fanCategory->toCategory("fan"));

        speaker = view.findViewById(R.id.speaker);
        speaker.setOnClickListener(speakerCategory->toCategory("speaker"));

        computer = view.findViewById(R.id.computer);
        computer.setOnClickListener(computerCategory->toCategory("computer"));

        laptop = view.findViewById(R.id.laptop);
        laptop.setOnClickListener(laptopCategory->toCategory("laptop"));

        handphone = view.findViewById(R.id.handphone);
        handphone.setOnClickListener(handphoneCategory->toCategory("handphone"));

        welcome = view.findViewById(R.id.welcome);
        welcome.setText("Welcome, " + currentUsername + " ! ");

        return view;
    }

    public void toCategory(String category){
        listData.clear();
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                    if(data.child("servicer").child("category").getValue(String.class).equals(category)) {//liat ada ga email di datanya
                        String temp = data.child("username").getValue(String.class);
                        listData.add(temp);
                    }
                }

                currCategory = category;

                LinearLayout menu = getActivity().findViewById(R.id.menu);
                menu.setVisibility(View.INVISIBLE);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.containerFrameMain, new CategoryFragment()).commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}