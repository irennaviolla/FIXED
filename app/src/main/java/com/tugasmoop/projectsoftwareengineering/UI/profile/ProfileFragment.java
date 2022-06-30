package com.tugasmoop.projectsoftwareengineering.UI.profile;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUser;
import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tugasmoop.projectsoftwareengineering.UI.service.PendingPage;
import com.tugasmoop.projectsoftwareengineering.R;
import com.tugasmoop.projectsoftwareengineering.UI.service.ServicerActivity;
import com.tugasmoop.projectsoftwareengineering.UI.service.UploadDataPage;

import java.io.File;
import java.util.HashMap;

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

    TextView nameProfile, usernameProfile, emailProfile, phoneNumberProfile, addressProfile, dobProfile;
    ImageView bEmail, bPhoneNumber, bAddress, bDob, profilePicture;
    EditText emailProfileEdit, phoneProfileEdit, addressProfileEdit, dobProfileEdit;
    Boolean buttonToggleEmail = false, buttonTogglePhone = false, buttonToggleAddress = false, buttonToggleDob = false;
    Button offer, logout;
    LinearLayout home, favorite, history;
    String usernameTxt, nameTxt, emailTxt, passwordTxt, phoneNumTxt, addressTxt, dobTxt;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");
    boolean check = false;
    StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fixedofficial-f94c9.appspot.com/");
    int status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        usernameTxt = currentUsername;

        //mengambil data user dari main activity(currentUser) --> Object User
        nameTxt = currentUser.getName();
        emailTxt = currentUser.getEmail();
        phoneNumTxt = currentUser.getPhoneNumber();
        addressTxt = currentUser.getAddress();
        dobTxt = currentUser.getDOB();
        passwordTxt = currentUser.getPassword();

        //mengambil variabel dari xml dan diubah datanya menjadi data yang sudah diambil sebelumnya
        nameProfile = view.findViewById(R.id.nameProfile);
        nameProfile.setText(nameTxt);

        usernameProfile = view.findViewById(R.id.usernameProfile);
        usernameProfile.setText(usernameTxt);

        emailProfile = view.findViewById(R.id.emailProfile);
        emailProfile.setText(emailTxt);

        phoneNumberProfile = view.findViewById(R.id.phoneNumberProfile);
        phoneNumberProfile.setText(phoneNumTxt);

        addressProfile = view.findViewById(R.id.addressProfile);
        addressProfile.setText(addressTxt);

        dobProfile = view.findViewById(R.id.dobProfile);
        dobProfile.setText(dobTxt);

        bEmail = view.findViewById(R.id.bEmail);
        bPhoneNumber = view.findViewById(R.id.bPhoneNumber);
        bAddress = view.findViewById(R.id.bAddress);
        bDob = view.findViewById(R.id.bDob);

        emailProfileEdit = view.findViewById(R.id.emailProfileEdit);
        phoneProfileEdit = view.findViewById(R.id.phoneProfileEdit);
        addressProfileEdit = view.findViewById(R.id.addressProfileEdit);
        dobProfileEdit = view.findViewById(R.id.dobProfileEdit);

        offer = view.findViewById(R.id.offer);
        logout = view.findViewById(R.id.logout);

        profilePicture = view.findViewById(R.id.profilePicture);
        try {
            if (currentUser.getCheckPhoto() == 1) {
                final File localFile = File.createTempFile("photo_" + currentUsername, "jpg");
                Log.d("testImage", currentUsername + "aaaa");
                storage.child("images/" + currentUsername + "/profile.jpg").getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Uri userUri = Uri.fromFile(localFile);
                                Log.d("test", "test");
                                profilePicture.setImageURI(userUri);
                            }
                        });
            } else {
                Glide.with(this)
                        .load("https://upload.wikimedia.org/wikipedia/commons/7/70/User_icon_BLACK-01.png?20140731221454")
                        .into(profilePicture);
            }
        }catch (Exception e){

        }

        bEmail.setOnClickListener(email->changeEmail());
        bPhoneNumber.setOnClickListener(phone->changePhone());
        bAddress.setOnClickListener(address->changeAddress());
        bDob.setOnClickListener(DOB->changeDOB());
        logout.setOnClickListener(logoutClick->{
            getActivity().finish();
        });
        profilePicture.setOnClickListener(profileClick->{
            Intent showProfile = new Intent(getActivity(), ShowProfile.class);
            startActivity(showProfile);
        });
        offer.setOnClickListener(offerClick->{
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    status = snapshot.child(currentUsername).child("servicer").child("status").getValue(Integer.class);
                    if(status == 1){
                        Intent offer = new Intent(getActivity(), ServicerActivity.class);
                        startActivity(offer);
                    }
                    else if(status == 0){
                        Intent upload = new Intent(getActivity(), UploadDataPage.class);
                        startActivity(upload);
                    }
                    else{
                        Intent pending = new Intent(getActivity(), PendingPage.class);
                        startActivity(pending);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        return view;
    }

    private void changeAddress() {
        if(!buttonToggleAddress){
            buttonToggleAddress = true;
            addressProfile.setVisibility(View.GONE);
            addressProfileEdit.setVisibility(View.VISIBLE);
        }
        else {
            String newAddress;
            newAddress = addressProfileEdit.getText().toString().trim();// buat ngambil address yang barunya apa

            buttonToggleAddress = false;
            addressProfile.setVisibility(View.VISIBLE);
            addressProfileEdit.setVisibility(View.GONE);

            addressProfile.setText(newAddress);//address di UI jadi address baru
            addressTxt = newAddress;//address lama diganti baru

            currentUser.setAddress(newAddress);

            HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
            newUser.put("address", newAddress);

            databaseReference.child("Users").child(usernameTxt).updateChildren(newUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "Successfully update", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void changeDOB() {
        if(!buttonToggleDob){
            buttonToggleDob = true;
            dobProfile.setVisibility(View.GONE);
            dobProfileEdit.setVisibility(View.VISIBLE);
        }
        else {
            String newDob;
            newDob = dobProfileEdit.getText().toString().trim();// buat ngambil dob yang barunya apa

            buttonToggleDob = false;
            dobProfile.setVisibility(View.VISIBLE);
            dobProfileEdit.setVisibility(View.GONE);

            dobProfile.setText(newDob);//dob di UI jadi dob baru
            dobTxt = newDob;//dob lama diganti baru

            currentUser.setDOB(newDob);

            HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
            newUser.put("DOB", newDob);

            databaseReference.child("Users").child(usernameTxt).updateChildren(newUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "Successfully update", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void changePhone() {
        if(!buttonTogglePhone){
            buttonTogglePhone = true;
            phoneNumberProfile.setVisibility(View.GONE);
            phoneProfileEdit.setVisibility(View.VISIBLE);
        }
        else{
            check = false;// buat ngecek unik ato ga
            String newPhoneNum;
            newPhoneNum = phoneProfileEdit.getText().toString().trim();// buat ngambil phone yang barunya apa

            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        if(data.child("phoneNumber").getValue(String.class).equals(newPhoneNum)) {//liat ada ga phone di datanya
                            Toast.makeText(getActivity(), "Phone number already exists", Toast.LENGTH_SHORT).show();//kalo phone nya sama
                            check = true;//ga unik
                            return;
                        }
                    }

                    if(check == false){//ini unik
                        buttonTogglePhone  = false;
                        phoneNumberProfile.setVisibility(View.VISIBLE);
                        phoneProfileEdit.setVisibility(View.GONE);

                        phoneNumberProfile.setText(newPhoneNum);//phone di UI jadi phone baru
                        phoneNumTxt = newPhoneNum;//phone lama diganti baru

                        currentUser.setPhoneNumber(newPhoneNum);

                        HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                        newUser.put("phoneNumber", newPhoneNum);

                        databaseReference.child("Users").child(usernameTxt).updateChildren(newUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(), "Successfully update", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void changeEmail() {
        if(!buttonToggleEmail){
            buttonToggleEmail = true;
            emailProfile.setVisibility(View.GONE);
            emailProfileEdit.setVisibility(View.VISIBLE);
        }
        else{
            check = false;// buat ngecek unik ato ga
            String newEmail;
            newEmail = emailProfileEdit.getText().toString().trim();// buat ngambil email yang barunya apa

            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {//dicek nya skali aja
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {//ada di tabel user, snapshoot = user
                    Log.d("TEST", "TEST");//buat ngecek
                    for(DataSnapshot data : snapshot.getChildren()){//loop buat liat data dari user
                        Log.d("TEST94", data.child("email").getValue(String.class));
                        if(data.child("email").getValue(String.class).equals(newEmail)) {//liat ada ga email di datanya
                            Log.d("TEST3", "TEST");
                            //kalo email nya sama
                            check = true;//ga unik
                            return;
                        }
                    }

                    if(check == false){//ini unik
                        Log.d("TEST4", "TEST");
                        buttonToggleEmail  = false;
                        emailProfile.setVisibility(View.VISIBLE);
                        emailProfileEdit.setVisibility(View.GONE);

                        emailProfile.setText(newEmail);//email di UI jadi email baru
                        emailTxt = newEmail;//email lama diganti baru

                        currentUser.setEmail(newEmail);

                        HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                        newUser.put("email", newEmail);

                        databaseReference.child("Users").child(usernameTxt).updateChildren(newUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(), "Successfully update", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        Log.d("TEST2", emailTxt);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}

