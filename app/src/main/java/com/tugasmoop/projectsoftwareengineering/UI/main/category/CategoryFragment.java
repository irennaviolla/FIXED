package com.tugasmoop.projectsoftwareengineering.UI.main.category;

import static com.tugasmoop.projectsoftwareengineering.SharedData.listData;
import static com.tugasmoop.projectsoftwareengineering.SharedData.mode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterData;
import com.tugasmoop.projectsoftwareengineering.UI.main.MainFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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

    ImageView backHome;
    RecyclerView servicerList;
    LinearLayoutManager linearLayoutManager;
    AdapterData adapterData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        mode = 1;

        servicerList = view.findViewById(R.id.servicerList);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        servicerList.setLayoutManager(linearLayoutManager);

        adapterData = new AdapterData(getActivity(), listData);
        servicerList.setAdapter(adapterData);

        adapterData.notifyDataSetChanged();

        backHome = view.findViewById(R.id.backHome);
        backHome.setOnClickListener(toMainPage->{
            LinearLayout menu = getActivity().findViewById(R.id.menu);
            menu.setVisibility(View.VISIBLE);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containerFrameMain, new MainFragment()).commit();
        });

        return view;
    }
}