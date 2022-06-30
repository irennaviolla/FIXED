package com.tugasmoop.projectsoftwareengineering.UI.main.category;

import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUser;
import static com.tugasmoop.projectsoftwareengineering.SharedData.currentUsername;
import static com.tugasmoop.projectsoftwareengineering.SharedData.databaseReference;
import static com.tugasmoop.projectsoftwareengineering.SharedData.storage;
import static com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterData.currentDetailServicer;
import static com.tugasmoop.projectsoftwareengineering.UI.adapter.AdapterDataHistory.currentHistoryServicer;

import android.net.Uri;
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
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.tugasmoop.projectsoftwareengineering.R;

import java.io.File;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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

    TextView nameDetail, categoryDetail, descriptionDetail, priceDetail, ratingDetail;
    Button bFavorite, bBook;
    String nameTxt, categoryTxt, descriptionTxt;
    ImageView backDetail, photoDetail;
    int priceTxt;
    double ratingTxt;
    int favCount = currentUser.getFavCount();
    boolean favorite = false, clickFav = false;
    int checkPhoto = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        currentHistoryServicer = currentDetailServicer;

        photoDetail = view.findViewById(R.id.photoDetail);
        databaseReference.child("Users").child(currentDetailServicer).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 nameTxt = snapshot.child("servicer").child("name").getValue(String.class);
                 categoryTxt = snapshot.child("servicer").child("category").getValue(String.class);
                 descriptionTxt = snapshot.child("servicer").child("description").getValue(String.class);
                 priceTxt = snapshot.child("servicer").child("price").getValue(Integer.class);
                 checkPhoto = snapshot.child("checkPhoto").getValue(Integer.class);

                 try {
                     if (checkPhoto == 1) {
                         final File localFile = File.createTempFile("photo_" + currentDetailServicer, "jpg");
                         Log.d("testImage", currentDetailServicer + "aaaa");
                         storage.child("images/" + currentDetailServicer + "/profile.jpg").getFile(localFile)
                                 .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                     @Override
                                     public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                         Uri userUri = Uri.fromFile(localFile);
                                         Log.d("test", "test");
                                         photoDetail.setImageURI(userUri);
                                     }
                                 });
                     } else {
                         Glide.with(getActivity())
                                 .load("https://upload.wikimedia.org/wikipedia/commons/7/70/User_icon_BLACK-01.png?20140731221454")
                                 .into(photoDetail);
                     }

                     if (snapshot.child("servicer").child("custCount").getValue(Integer.class) != 0) {
                         ratingTxt = (double) snapshot.child("servicer").child("rating").getValue(Integer.class) / (double) snapshot.child("servicer").child("custCount").getValue(Integer.class);
                     } else {
                         ratingTxt = 0.0;
                     }

                     nameDetail = view.findViewById(R.id.nameDetail);
                     nameDetail.setText(nameTxt);

                     categoryDetail = view.findViewById(R.id.categoryDetail);
                     categoryDetail.setText(categoryTxt);

                     descriptionDetail = view.findViewById(R.id.descriptionDetail);
                     descriptionDetail.setText(descriptionTxt);

                     priceDetail = view.findViewById(R.id.priceDetail);
                     priceDetail.setText(String.valueOf(priceTxt));

                     ratingDetail = view.findViewById(R.id.ratingDetail);
                     ratingDetail.setText(String.valueOf(ratingTxt));
                 } catch (Exception e) {

                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });

        bFavorite = view.findViewById(R.id.bFavorite);
        bBook = view.findViewById(R.id.bBook);
        backDetail = view.findViewById(R.id.backDetail);

        for(int i = 0; i < favCount; i++){
            if(currentUser.getFavServicer().get(i).equals(currentDetailServicer)){
                favorite = true;
                break;
            }
        }
        if(favorite == true){
            bFavorite.setText("Delete from favorite");
        }

        bFavorite.setOnClickListener(buttonFavorite->changeFavorite());
        bBook.setOnClickListener(toBooking->{
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.detailContainer, new ScheduleFragment()).commit();
        });
        backDetail.setOnClickListener(backToCategory->{
            getActivity().finish();
        });

        return view;
    }

    private void changeFavorite() {
        if(favorite == false) {
            if(clickFav == false){
                //tinggal update ke array
                currentUser.getFavServicer().add(currentDetailServicer);
                favCount++;
                currentUser.setFavCount(favCount);

                HashMap newUser = new HashMap();
                newUser.put("favCount", favCount);

                Log.d("Tess", String.valueOf(favCount));

                HashMap newFavorite = new HashMap();
                newFavorite.put(String.valueOf(favCount - 1), currentDetailServicer);

                Log.d("Tess", String.valueOf(favCount - 1) + "servicer : " + currentDetailServicer);

                HashMap newFavorite2 = new HashMap();
                newFavorite2.put(String.valueOf(favCount), "null");

                Log.d("Tess", String.valueOf(favCount) + "into null");

                databaseReference.child("Users").child(currentUsername).updateChildren(newUser);
                databaseReference.child("Users").child(currentUsername).child("favServicer").child(String.valueOf(favCount - 1)).setValue(currentDetailServicer);
                databaseReference.child("Users").child(currentUsername).child("favServicer").child(String.valueOf(favCount)).setValue("null");
            }
            else{
                Toast.makeText(getActivity(), "Already added to favorite", Toast.LENGTH_SHORT).show();
            }
            clickFav = true;
        }
        else{
            if(clickFav == false){

                databaseReference.child("Users").child(currentUsername).child("favServicer").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(int i = 0; i < favCount; i++){
                            Log.d("Tesssss", snapshot.child(String.valueOf(i)).getValue(String.class) + " vs " + currentDetailServicer);
                            Log.d("Tesssss", "Value i : " + String.valueOf(i));
                            if(snapshot.child(String.valueOf(i)).getValue(String.class).equals(currentDetailServicer)){
                                databaseReference.child("Users").child(currentUsername).child("favServicer").child(String.valueOf(i)).setValue("null");
                            }
                            //jangan di hapus, tapi di set favorite yang sebelumnya jadi null
                            if(currentUser.getFavServicer().get(i).equals(currentDetailServicer)){
                                currentUser.getFavServicer().set(i, "null");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else{
                Toast.makeText(getActivity(), "Already deleted from favorite", Toast.LENGTH_SHORT).show();
            }
            clickFav = true;
        }
    }

}