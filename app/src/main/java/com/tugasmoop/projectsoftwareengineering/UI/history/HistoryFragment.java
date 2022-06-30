package com.tugasmoop.projectsoftwareengineering.UI.history;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUser;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterDataHistory;
import com.tugasmoop.projectsoftwareengineering.data.models.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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

    LinearLayout home, favorite, profile;
    RecyclerView historyList;
    LinearLayoutManager linearLayoutManager;
    AdapterDataHistory adapterDataHistory;

    public static List<Transaction> transactionList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        transactionList.clear();
        int transactionCount = currentUser.getTransactionCount();
        Log.d("TEST123", String.valueOf(transactionCount));
        for(int i = 0; i < transactionCount; i++){
            Log.d("TEST111", "Masokk for loop");
            if(currentUser.getTransactionList().get(i).getStatus() != -99){
                Log.d("TEST123", "masuk for loop if");
                Transaction newTransaction = currentUser.getTransactionList().get(i);
                transactionList.add(newTransaction);
            }
        }

        historyList = view.findViewById(R.id.historyList);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        historyList.setLayoutManager(linearLayoutManager);

        adapterDataHistory = new AdapterDataHistory(getActivity(), transactionList);
        historyList.setAdapter(adapterDataHistory);

        adapterDataHistory.notifyDataSetChanged();

        home = view.findViewById(R.id.home);
        favorite = view.findViewById(R.id.favorite);
        profile = view.findViewById(R.id.profile);

        return view;
    }
}