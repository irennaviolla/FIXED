package com.tugasmoop.projectsoftwareengineering.UI.register;


import static com.tugasmoop.projectsoftwareengineering.SharedData.databaseReference;

import android.os.Bundle;
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
import com.tugasmoop.projectsoftwareengineering.UI.login.LoginFragment;
import com.tugasmoop.projectsoftwareengineering.data.models.User;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

    EditText name, email, username, phoneNumber, password, cPassword;
    Button register;
    TextView login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        name = view.findViewById(R.id.name);
        username = view.findViewById(R.id.usernameRegister);
        email = view.findViewById(R.id.email);
        phoneNumber = view.findViewById(R.id.phoneNumber);
        password = view.findViewById(R.id.passwordRegister);
        cPassword = view.findViewById(R.id.confirmPassword);
        register = view.findViewById(R.id.buttonRegister);
        login = view.findViewById(R.id.login);

        register.setOnClickListener(registerUser->{
            String nameTxt = name.getText().toString().trim();
            String usernameTxt = username.getText().toString().trim();
            String emailTxt = email.getText().toString().trim();
            String phoneNumTxt = phoneNumber.getText().toString().trim();
            String passwordTxt = password.getText().toString().trim();
            String cPasswordTxt = cPassword.getText().toString().trim();

            //cek jika semua kosong
            if(usernameTxt.equals("") || emailTxt.equals("") || phoneNumTxt.equals("") || passwordTxt.equals("") || cPasswordTxt.equals("")){
                //alert
                Toast.makeText(getActivity(), "All fields must be field in", Toast.LENGTH_SHORT).show();
            }
            //cek jika password dan confirm password berbeda
            else if(!password.getText().toString().equals(cPassword.getText().toString())){
                Toast.makeText(getActivity(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                //ngosongin passnya lagi
                password.setText("");
                cPassword.setText("");
            }
            else{
                //masuk, buat user baru
                User newUser = new User(nameTxt, emailTxt, usernameTxt, passwordTxt, phoneNumTxt);

                //User baru masukin ke database di firebase
                databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //cek dulu kalo ada user yang input usernamenya sama
                        if(snapshot.hasChild(usernameTxt)){
                            Toast.makeText(getActivity(), "Username Already Exists", Toast.LENGTH_SHORT).show();
                            username.setText("");
                            username.requestFocus();
                        }
                        //username is unique
                        else {
                            databaseReference.child("Users").child(usernameTxt).setValue(newUser)
                                    .addOnSuccessListener(success->{
                                        Toast.makeText(view.getContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(fail->{
                                        Toast.makeText(view.getContext(), "Please check your connection", Toast.LENGTH_SHORT).show();
                                    });
                            HashMap newFavServicer = new HashMap();
                            newFavServicer.put("0", "null");
                            databaseReference.child("Users").child(usernameTxt).child("favServicer").setValue(newFavServicer);

                            HashMap newTransaction = new HashMap();
                            newTransaction.put("status", -99);//blm di bayar (pending)
                            newTransaction.put("customerUsername", "null");
                            newTransaction.put("servicerUsername", "null");
                            newTransaction.put("day", 0);
                            newTransaction.put("month", 0);
                            newTransaction.put("year", 0);
                            newTransaction.put("hour", 0);
                            newTransaction.put("minute", 0);
                            newTransaction.put("idOrderCustomer", -1);
                            newTransaction.put("idOrderServicer", -1);
                            databaseReference.child("Orders").child(usernameTxt).child("0").setValue(newTransaction);
                            databaseReference.child("Users").child(usernameTxt).child("servicer").child("orders").child("0").setValue(newTransaction);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        login.setOnClickListener(backToLogin->{
            Fragment fragment = new LoginFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containerFrame, fragment).commit();
        });

        return view;
    }
}