package com.tugasmoop.projectsoftwareengineering.UI.service;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUser;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterDataOrder;
import com.tugasmoop.projectsoftwareengineering.data.models.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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

    RecyclerView orderView;
    LinearLayoutManager linearLayoutManager;
    AdapterDataOrder adapterDataOrder;

    public static List<Transaction> orderList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        orderList.clear();
        int transactionCount = currentUser.getServicer().getTransactionCount();

        Log.d("TEST", String.valueOf(transactionCount));
        for(int i = 0; i < transactionCount; i++){
            Log.d("TEST111", "Masokk for loop");
            if(currentUser.getServicer().getTransactionList().get(i).getStatus() != -99){
                Log.d("TEST123", "masuk for loop if");
                Transaction newTransaction = currentUser.getServicer().getTransactionList().get(i);
                orderList.add(newTransaction);
            }
        }

        orderView = view.findViewById(R.id.orderList);

        if(orderList.size() != 0){
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            orderView.setLayoutManager(linearLayoutManager);


            adapterDataOrder = new AdapterDataOrder(getActivity(), orderList);
            orderView.setAdapter(adapterDataOrder);

            adapterDataOrder.notifyDataSetChanged();
        }
        return view;
    }
}