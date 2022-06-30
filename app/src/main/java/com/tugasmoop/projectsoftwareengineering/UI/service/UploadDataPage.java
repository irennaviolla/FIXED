package com.tugasmoop.projectsoftwareengineering.UI.service;


import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tugasmoop.projectsoftwareengineering.R;

import java.util.HashMap;

public class UploadDataPage extends AppCompatActivity implements View.OnClickListener {

    Button bIdentityCard, bSelfie, bBankAccount, buttonSubmit;
    ImageView backUpload, identityCard, selfie, bankAccount;
    EditText name, nik, accountNumber;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");

    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data_page);

        storageReference = storage.getReference();

        backUpload = findViewById(R.id.backUpload);
        bIdentityCard = findViewById(R.id.bIdentityCard);
        identityCard = findViewById(R.id.identityCard);
        bSelfie = findViewById(R.id.bSelfie);
        selfie = findViewById(R.id.selfie);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        name = findViewById(R.id.name);
        nik = findViewById(R.id.nik);
        accountNumber = findViewById(R.id.accountNumber);

        bBankAccount = findViewById(R.id.bBankAccount);
        bankAccount = findViewById(R.id.bankAccount);

        bIdentityCard.setOnClickListener(this);
        backUpload.setOnClickListener(this);
        bBankAccount.setOnClickListener(this);
        bSelfie.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent getImage = new Intent();
        getImage.setType("image/*");//image diambil dari directory manapun
        getImage.setAction(Intent.ACTION_GET_CONTENT);
        if(view == findViewById(R.id.bIdentityCard)){
            startActivityForResult(getImage, 1);
        }
        else if(view == findViewById(R.id.bSelfie)){
            startActivityForResult(getImage, 2);
        }
        else if(view == findViewById(R.id.bBankAccount)){
            startActivityForResult(getImage, 3);
        }
        else if(view == findViewById(R.id.buttonSubmit)){
            HashMap newUser = new HashMap();//di update children mintanya map, bukan objek
            newUser.put("status", 8);
            newUser.put("name", name.getText().toString().trim());
            newUser.put("nik", nik.getText().toString().trim());
            newUser.put("bank", accountNumber.getText().toString().trim());
            databaseReference.child("Users").child(currentUsername).child("servicer").updateChildren(newUser)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UploadDataPage.this, "Your data has been recorded", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadDataPage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                        }
                    });
            finish();
        }
        else{
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            identityCard.setImageURI(imageUri);

            // Create a reference to 'images/mountains.jpg'
            StorageReference mountainImagesRef = storageReference.child("images/" + currentUsername + "/identityCard.jpg");

            mountainImagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadDataPage.this, "Identity Card Successfully Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadDataPage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            selfie.setImageURI(imageUri);

            // Create a reference to 'images/mountains.jpg'
            StorageReference mountainImagesRef = storageReference.child("images/" + currentUsername + "/selfie.jpg");

            mountainImagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadDataPage.this, "Your Selfie Successfully Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadDataPage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            bankAccount.setImageURI(imageUri);

            // Create a reference to 'images/mountains.jpg'
            StorageReference mountainImagesRef = storageReference.child("images/" + currentUsername + "/bankAccount.jpg");

            mountainImagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UploadDataPage.this, "Bank Account Successfully Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadDataPage.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}