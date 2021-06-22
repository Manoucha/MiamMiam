package com.imene.miamiam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.imene.miamiam.Adapters.MyselectionAdapter;
import com.imene.miamiam.Models.Recette;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class MyselectionFragment extends Fragment {

    private List<Recette> lstFavorites;
    private RecyclerView recyclerView;
    private DatabaseReference RefDB;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_myselection, container, false);
        progressBar = RootView.findViewById(R.id.progressbar);
        emptyView= RootView.findViewById(R.id.empty_view);
        getFavorites(RootView);
        return RootView;
    }

    private void getFavorites(final View rootView) {
        mAuth = FirebaseAuth.getInstance();
        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        RefDB = FirebaseDatabase.getInstance().getReference().child(uid);
        RefDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstFavorites = new ArrayList<>();
                HashMap favoris = (HashMap) dataSnapshot.getValue();
                if (favoris != null) {
                    for (Object recette : favoris.keySet()) {
                        String title = (String) dataSnapshot.child(recette.toString()).child("title").getValue();
                        String img = (String) dataSnapshot.child(recette.toString()).child("img").getValue();
                        lstFavorites.add(new Recette(recette.toString(), title, img, 0, 0));
                    }
                }
                progressBar.setVisibility(View.GONE);
                recyclerView = rootView.findViewById(R.id.recycleview_favorites);
                if(lstFavorites.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else{
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    MyselectionAdapter myAdapter = new MyselectionAdapter(getContext(), lstFavorites);
                    recyclerView.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void sendToLogin() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        requireActivity().finish();// The user can't come back to this page
    }
}