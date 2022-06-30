package com.tugasmoop.projectsoftwareengineering;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tugasmoop.projectsoftwareengineering.data.models.User;

import java.util.ArrayList;
import java.util.List;

public class SharedData {

    public static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fixedofficial-f94c9-default-rtdb.firebaseio.com/");
    public static User currentUser;
    public static String currentUsername;
    public static List<String> listData = new ArrayList<>();
    public static int mode = 0;
    public static String currCategory;
    public static int day, month, year, hour, minute;
    public static StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fixedofficial-f94c9.appspot.com/");
    public static int statusTxt;

}
