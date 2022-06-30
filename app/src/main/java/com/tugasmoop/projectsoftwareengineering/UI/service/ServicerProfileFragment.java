package com.tugasmoop.projectsoftwareengineering.UI.service;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUser;
import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.SharedData.storage;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.tugasmoop.projectsoftwareengineering.R;

import java.io.File;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServicerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicerProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ServicerProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServicerProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServicerProfileFragment newInstance(String param1, String param2) {
        ServicerProfileFragment fragment = new ServicerProfileFragment();
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

    ImageView backProfile, profilePictureServicer, bCategory, bPrice;
    TextView description, nameServicerProfile, categoryProfile, emailServicerProfile, phoneServicerProfile, addressServicerProfile, dobServicerProfile, rating, priceProfile;
    EditText categoryProfileEdit, priceProfileEdit;
    Boolean buttonToggleCategory = false, buttonTogglePrice = false;
    String usernameTxt, nameTxt, emailTxt, phoneNumTxt, addressTxt, dobTxt, categoryTxt, descriptionTxt;
    int ratingTxt, custCountTxt, priceTxt, walletTxt;
    double totalRating;
    Button walletProfile;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText popUpDescription;
    private Button bCancel, bSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicer_profile, container, false);

        usernameTxt = currentUsername;

        nameTxt = currentUser.getServicer().getName();
        emailTxt = currentUser.getEmail();
        phoneNumTxt = currentUser.getPhoneNumber();
        addressTxt = currentUser.getAddress();
        dobTxt = currentUser.getDOB();
        categoryTxt = currentUser.getServicer().getCategory();
        descriptionTxt = currentUser.getServicer().getDescription();
        ratingTxt = currentUser.getServicer().getRating();
        custCountTxt = currentUser.getServicer().getCustCount();
        priceTxt = currentUser.getServicer().getPrice();
        walletTxt = currentUser.getServicer().getWallet();

        emailServicerProfile = view.findViewById(R.id.emailServicerProfile);
        emailServicerProfile.setText(emailTxt);

        phoneServicerProfile = view.findViewById(R.id.phoneServicerProfile);
        phoneServicerProfile.setText(phoneNumTxt);

        addressServicerProfile = view.findViewById(R.id.addressServicerProfile);
        addressServicerProfile.setText(addressTxt);

        dobServicerProfile = view.findViewById(R.id.dobServicerProfile);
        dobServicerProfile.setText(dobTxt);

        categoryProfile = view.findViewById(R.id.categoryProfile);
        categoryProfile.setText(categoryTxt);

        priceProfile = view.findViewById(R.id.priceProfile);
        priceProfile.setText("Rp. " + String.valueOf(priceTxt));

        walletProfile = view.findViewById(R.id.walletProfile);
        walletProfile.setText("Rp. " + String.valueOf(walletTxt));

        profilePictureServicer = view.findViewById(R.id.profilePictureServicer);
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
                                profilePictureServicer.setImageURI(userUri);
                            }
                        });
            } else {
                Glide.with(this)
                        .load("https://upload.wikimedia.org/wikipedia/commons/7/70/User_icon_BLACK-01.png?20140731221454")
                        .into(profilePictureServicer);
            }
        }catch (Exception e){

        }

        description = view.findViewById(R.id.description);
        nameServicerProfile = view.findViewById(R.id.nameServicerProfile);
        phoneServicerProfile = view.findViewById(R.id.phoneServicerProfile);
        addressServicerProfile = view.findViewById(R.id.addressServicerProfile);
        dobServicerProfile = view.findViewById(R.id.dobServicerProfile);
        categoryProfileEdit = view.findViewById(R.id.categoryProfileEdit);
        priceProfileEdit = view.findViewById(R.id.priceProfileEdit);

        nameServicerProfile.setText(nameTxt);

        rating = view.findViewById(R.id.rating);
        if(custCountTxt == 0){
            rating.setText("0/5");
        }
        else{
            totalRating = (double) ratingTxt/ (double) custCountTxt;
        }
        rating.setText(totalRating + "/5");

        bCategory = view.findViewById(R.id.bCategory);
        bPrice = view.findViewById(R.id.bPrice);
        backProfile = view.findViewById(R.id.backProfile);

        bCategory.setOnClickListener(category->changeCategory());
        backProfile.setOnClickListener(back->{
            getActivity().finish();
        });
        description.setOnClickListener(description->changeDescription());
        bPrice.setOnClickListener(price->changePrice());


        return view;
    }

    private void changePrice() {
        if(!buttonTogglePrice){
            buttonTogglePrice = true;
            priceProfile.setVisibility(View.GONE);
            priceProfileEdit.setVisibility(View.VISIBLE);
        }
        else{
            String newPrice;
            newPrice = priceProfileEdit.getText().toString().trim();

            buttonTogglePrice = false;
            priceProfile.setVisibility(View.VISIBLE);
            priceProfileEdit.setVisibility(View.GONE);

            priceProfile.setText("Rp. " + newPrice);//category di UI jadi category baru
            priceTxt = Integer.valueOf(newPrice);//category lama diganti baru

            currentUser.getServicer().setPrice(Integer.valueOf(newPrice));

            HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
            newUser.put("price", priceTxt);

            databaseReference.child("Users").child(usernameTxt).child("servicer").updateChildren(newUser)
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

    private void changeCategory() {
        if(!buttonToggleCategory){
            buttonToggleCategory = true;
            categoryProfile.setVisibility(View.GONE);
            categoryProfileEdit.setVisibility(View.VISIBLE);
        }
        else{
            String newCategory, currentCategory;
            boolean categoryCheck = false;
            String arr[] = {"television","refrigerator","air conditioner","dispenser","washing machine","fan","speaker","computer","laptop","handphone"};
            newCategory = categoryProfileEdit.getText().toString().trim();// buat ngambil category yang barunya apa
            for(int i = 0; i < 10; i++){
                if(newCategory.equalsIgnoreCase(arr[i])){
                    categoryCheck = true;
                    break;
                }
            }
            if(categoryCheck == false){
                Toast.makeText(getActivity(), "Please fill in the available categories", Toast.LENGTH_SHORT).show();
            }
            else{
                buttonToggleCategory = false;
                categoryProfile.setVisibility(View.VISIBLE);
                categoryProfileEdit.setVisibility(View.GONE);

                categoryProfile.setText(newCategory);//category di UI jadi category baru
                categoryTxt = newCategory;//category lama diganti baru

                currentUser.getServicer().setCategory(newCategory);

                HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                newUser.put("category", newCategory);

                databaseReference.child("Users").child(usernameTxt).child("servicer").updateChildren(newUser)
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
    }

    private void changeDescription() {
        createNewDescriptionDialog();
    }

    public void  createNewDescriptionDialog(){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final View descriptionPopUpView = getLayoutInflater().inflate(R.layout.popup, null);
        popUpDescription = (EditText) descriptionPopUpView.findViewById(R.id.popUpDescription);

        bSave = (Button) descriptionPopUpView.findViewById(R.id.bSave);
        bCancel = (Button) descriptionPopUpView.findViewById(R.id.bCancel);

        popUpDescription.setText(descriptionTxt);

        dialogBuilder.setView(descriptionPopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newDescription;
                newDescription = popUpDescription.getText().toString().trim();// buat ngambil address yang barunya apa

                popUpDescription.setText(descriptionTxt);
                descriptionTxt = newDescription;

                currentUser.getServicer().setDescription(newDescription);

                HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
                newUser.put("description", newDescription);

                databaseReference.child("Users").child(usernameTxt).child("servicer").updateChildren(newUser)
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
                dialog.hide();
            }
        });
    }
}