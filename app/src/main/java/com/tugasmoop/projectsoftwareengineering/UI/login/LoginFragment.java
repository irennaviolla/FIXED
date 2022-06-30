package com.tugasmoop.projectsoftwareengineering.UI.login;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUser;
import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.SharedData.databaseReference;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.UI.MainActivity;
import com.tugasmoop.projectsoftwareengineering.UI.register.RegisterFragment;
import com.tugasmoop.projectsoftwareengineering.data.models.Servicer;
import com.tugasmoop.projectsoftwareengineering.data.models.Transaction;
import com.tugasmoop.projectsoftwareengineering.data.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    EditText username, password;
    Button login;
    TextView register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.usernameLogin);
        password = view.findViewById(R.id.passwordLogin);
        register = view.findViewById(R.id.register);
        login = view.findViewById(R.id.buttonLogin);

        register.setOnClickListener(toRegister -> {
            Fragment fragment = new RegisterFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containerFrame, fragment).commit();
        });

        login.setOnClickListener(toMain-> {
            String usernameTxt = username.getText().toString().trim();
            String passwordTxt = password.getText().toString().trim();

            if (usernameTxt.equals("") || passwordTxt.equals("")) {
                Toast.makeText(getActivity(), "All fields must be field in", Toast.LENGTH_SHORT).show();
            } else {
                //firebase
                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //kalau username ditemukan
                        if (snapshot.hasChild(usernameTxt)) {
                            //ambil passwordnya dan cek
                            String getPassword = snapshot.child(usernameTxt).child("password").getValue(String.class);
                            if (passwordTxt.equals(getPassword)) {
                                currentUsername = usernameTxt;

                                //main attribute
                                String name = snapshot.child(usernameTxt).child("name").getValue(String.class);
                                String email = snapshot.child(usernameTxt).child("email").getValue(String.class);
                                String username = snapshot.child(usernameTxt).child("username").getValue(String.class);
                                String password = snapshot.child(usernameTxt).child("password").getValue(String.class);
                                String phoneNumber = snapshot.child(usernameTxt).child("phoneNumber").getValue(String.class);
                                String address = snapshot.child(usernameTxt).child("address").getValue(String.class);
                                String DOB = snapshot.child(usernameTxt).child("DOB").getValue(String.class);
                                int favCount = snapshot.child(usernameTxt).child("favCount").getValue(Integer.class);
                                int transactionCount = snapshot.child(usernameTxt).child("transactionCount").getValue(Integer.class);
                                int checkProfile = snapshot.child(usernameTxt).child("checkPhoto").getValue(Integer.class);

                                Log.d("TEST", "masukkkk");
                                //servicer attribute
                                int status = snapshot.child(usernameTxt).child("servicer").child("status").getValue(Integer.class);
                                String category = snapshot.child(usernameTxt).child("servicer").child("category").getValue(String.class);
                                int wallet = snapshot.child(usernameTxt).child("servicer").child("wallet").getValue(Integer.class);
                                String NIK = snapshot.child(usernameTxt).child("servicer").child("NIK").getValue(String.class);
                                String servicerName = snapshot.child(usernameTxt).child("servicer").child("name").getValue(String.class);
                                String noRek = snapshot.child(usernameTxt).child("servicer").child("noRek").getValue(String.class);
                                String bank = snapshot.child(usernameTxt).child("servicer").child("bank").getValue(String.class);
                                String description = snapshot.child(usernameTxt).child("servicer").child("description").getValue(String.class);
                                int rating = snapshot.child(usernameTxt).child("servicer").child("rating").getValue(Integer.class);
                                int custCount = snapshot.child(usernameTxt).child("servicer").child("custCount").getValue(Integer.class);
                                int price = snapshot.child(usernameTxt).child("servicer").child("price").getValue(Integer.class);
                                int transactionCountServicer = snapshot.child(usernameTxt).child("servicer").child("transactionCount").getValue(Integer.class);


                                //make object user just for data (loading)
                                Servicer servicer = new Servicer();
                                servicer.setStatus(status);
                                servicer.setCategory(category);
                                servicer.setWallet(wallet);
                                servicer.setNIK(NIK);
                                servicer.setName(servicerName);
                                servicer.setNoRek(noRek);
                                servicer.setBank(bank);
                                servicer.setDescription(description);
                                servicer.setRating(rating);
                                servicer.setCustCount(custCount);
                                servicer.setPrice(price);
                                servicer.setTransactionCount(transactionCountServicer);

                                currentUser = new User(name, email, username, password, phoneNumber);
                                currentUser.setAddress(address);
                                currentUser.setDOB(DOB);
                                currentUser.setFavCount(favCount);
                                currentUser.setServicer(servicer);
                                currentUser.setTransactionCount(transactionCount);
                                currentUser.setCheckPhoto(checkProfile);

                                List<String> favServicer = new ArrayList<>();
                                for (int i = 0; i < favCount; i++) {//buat looping fav servicer
                                    String fav = snapshot.child(usernameTxt).child("favServicer").child(String.valueOf(i)).getValue(String.class);
                                    favServicer.add(fav);//nambahin favorit
                                    Log.d("teslagi", fav);
                                }
                                currentUser.setFavServicer(favServicer);

                                databaseReference.child("Orders").child(currentUsername).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        List<Transaction> transactionList = new ArrayList<>();
                                        for (int i = 0; i < transactionCount; i++) {
                                            int status = snapshot.child(String.valueOf(i)).child("status").getValue(Integer.class);
                                            String customerUsername = snapshot.child(String.valueOf(i)).child("customerUsername").getValue(String.class);
                                            String servicerUsername = snapshot.child(String.valueOf(i)).child("servicerUsername").getValue(String.class);

                                            Log.d("TESTStatusTransaction", String.valueOf(status));

                                            if (status != -99) {
                                                Transaction newTransaction = new Transaction(status, customerUsername, servicerUsername);

                                                //masukin data-data di orders
                                                int day = snapshot.child(String.valueOf(i)).child("day").getValue(Integer.class);
                                                int month = snapshot.child(String.valueOf(i)).child("month").getValue(Integer.class);
                                                int year = snapshot.child(String.valueOf(i)).child("year").getValue(Integer.class);
                                                int hour = snapshot.child(String.valueOf(i)).child("hour").getValue(Integer.class);
                                                int minute = snapshot.child(String.valueOf(i)).child("minute").getValue(Integer.class);
                                                newTransaction.setDay(day);
                                                newTransaction.setMonth(month);
                                                newTransaction.setYear(year);
                                                newTransaction.setHour(hour);
                                                newTransaction.setMinute(minute);

                                                Log.d("TestMasukinData", "success");

                                                transactionList.add(newTransaction);
                                            }
                                        }

                                        currentUser.setTransactionList(transactionList);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                //ambil orders di servicer
                                List<Transaction> transactionList = new ArrayList<>();
                                Log.d("TEST transaction", String.valueOf(transactionCountServicer));
                                for (int i = 0; i < transactionCountServicer; i++) {
                                    status = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("status").getValue(Integer.class);
                                    String customerUsername = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("customerUsername").getValue(String.class);
                                    String customerServicer = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("servicerUsername").getValue(String.class);
                                    Transaction newTransaction = new Transaction(status, customerUsername, customerServicer);

                                    int day = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("day").getValue(Integer.class);
                                    int month = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("month").getValue(Integer.class);
                                    int year = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("year").getValue(Integer.class);
                                    int hour = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("hour").getValue(Integer.class);
                                    int minute = snapshot.child(usernameTxt).child("servicer").child("orders").child(String.valueOf(i)).child("minute").getValue(Integer.class);

                                    newTransaction.setDay(day);
                                    newTransaction.setMonth(month);
                                    newTransaction.setYear(year);
                                    newTransaction.setHour(hour);
                                    newTransaction.setMinute(minute);

                                    transactionList.add(newTransaction);

                                }
                                currentUser.getServicer().setTransactionList(transactionList);

                                Toast.makeText(getActivity(), "Successfully login", Toast.LENGTH_SHORT).show();
                                Intent toMainActivity = new Intent(getActivity(), MainActivity.class);
                                startActivity(toMainActivity);
                            } else {
                                Toast.makeText(getActivity(), "Username or Password are wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return view;
    }
}