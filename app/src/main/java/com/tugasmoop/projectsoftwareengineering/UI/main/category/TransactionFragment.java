package com.tugasmoop.projectsoftwareengineering.UI.main.category;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUser;
import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.SharedData.databaseReference;
import static com.tugasmoop.projectsoftwareengineering.SharedData.day;
import static com.tugasmoop.projectsoftwareengineering.SharedData.hour;
import static com.tugasmoop.projectsoftwareengineering.SharedData.minute;
import static com.tugasmoop.projectsoftwareengineering.SharedData.month;
import static com.tugasmoop.projectsoftwareengineering.SharedData.year;
import static com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterData.currentDetailServicer;
import static com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterDataHistory.currentHistoryServicer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.data.models.Transaction;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
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

    String cNameTxt, cAddressTxt, cPhoneNumTxt, sNameTxt, sPhoneNumTxt, sCategoryTxt;
    int transactionCount = 0, transactionCountServicer = 0;
    TextView servicerFee, totalCost, dateTimeTransaction;
    ImageView backTransaction;
    Button bOrder;
    int fee;
    TextView customerName, customerAddress, customerPhoneNum, servicerName, servicerPhoneNum, servicerCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        customerName = view.findViewById(R.id.customerName);
        customerAddress = view.findViewById(R.id.customerAddress);
        customerPhoneNum = view.findViewById(R.id.customerPhone);

        servicerName = view.findViewById(R.id.servicerName);
        servicerPhoneNum = view.findViewById(R.id.servicerPhone);
        servicerCategory = view.findViewById(R.id.servicerCategory);

        backTransaction = view.findViewById(R.id.backTransaction);
        backTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        Log.d("Test1", currentUsername);

        //ambil data customer
        databaseReference.child("Users").child(currentUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Test2", "masukkkk");
                cNameTxt = snapshot.child("name").getValue(String.class);
                cAddressTxt = snapshot.child("address").getValue(String.class);
                cPhoneNumTxt = snapshot.child("phoneNumber").getValue(String.class);
                transactionCount = snapshot.child("transactionCount").getValue(Integer.class);


                customerName.setText(cNameTxt);
                customerAddress.setText(cAddressTxt);
                customerPhoneNum.setText(cPhoneNumTxt);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //ambil data servicer
        databaseReference.child("Users").child(currentDetailServicer).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Test3", "masukkkk");
                sNameTxt = snapshot.child("servicer").child("name").getValue(String.class);
                sPhoneNumTxt = snapshot.child("phoneNumber").getValue(String.class);
                sCategoryTxt = snapshot.child("servicer").child("category").getValue(String.class);
                transactionCountServicer = snapshot.child("servicer").child("transactionCount").getValue(Integer.class);


                servicerName.setText(sNameTxt);
                servicerPhoneNum.setText(sPhoneNumTxt);
                servicerCategory.setText(sCategoryTxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("Test5", "masukkkk");
        servicerFee = view.findViewById(R.id.servicerFee);
        totalCost = view.findViewById(R.id.totalCost);
        Log.d("Test6", "masukkkk");

        bOrder = view.findViewById(R.id.bOrder);
        bOrder.setOnClickListener(order->processOrder());

        databaseReference.child("Users").child(currentHistoryServicer).child("servicer").child("price").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Test4", "masukkkk");

                servicerFee.setText("Rp" + String.valueOf(snapshot.getValue(Integer.class)));
                fee = snapshot.getValue(Integer.class);
                totalCost.setText("TOTAL : Rp" + (fee + 20000));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String[] strMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Ags", "Sept", "Okt", "Nov", "Dec"};
        String dateAndTime = String.valueOf(day) + " " + strMonth[month] + " " + year + " at " + String.format("%02d:%02d", hour, minute);

        dateTimeTransaction = view.findViewById(R.id.dateTimeTransaction);
        dateTimeTransaction.setText(dateAndTime);

        return view;
    }

    public void processOrder() {
        //masukin db orders customer dengan status 0 (pending)
        Transaction newTransaction = new Transaction(0, currentUsername, currentDetailServicer);

        newTransaction.setDay(day);
        newTransaction.setMonth(month);
        newTransaction.setYear(year);
        newTransaction.setHour(hour);
        newTransaction.setMinute(minute);

        Log.d("TESTDATE", String.valueOf(day) + " - " + String.valueOf(month) + " - " + String.valueOf(year));

        currentUser.getTransactionList().add(newTransaction);
        int transactionCount = currentUser.getTransactionCount();
        currentUser.setTransactionCount(transactionCount + 1);

        newTransaction.setIdOrderCustomer(transactionCount);
        newTransaction.setIdOrderServicer(transactionCountServicer);

        databaseReference.child("Orders").child(currentUsername).child(String.valueOf(transactionCount)).setValue(newTransaction);

        HashMap newTransactionCount = new HashMap();
        newTransactionCount.put("transactionCount", currentUser.getTransactionCount());

        databaseReference.child("Users").child(currentUsername).updateChildren(newTransactionCount);

        //masukin ke db orders servicer
        databaseReference.child("Users").child(currentDetailServicer).child("servicer").child("orders").child(String.valueOf(transactionCountServicer)).setValue(newTransaction);

        HashMap newTransactionCountServicer = new HashMap();
        newTransactionCountServicer.put("transactionCount", transactionCountServicer + 1);

        databaseReference.child("Users").child(currentDetailServicer).child("servicer").updateChildren(newTransactionCountServicer);

        Toast.makeText(getActivity(), "Transaction has been recorded", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}