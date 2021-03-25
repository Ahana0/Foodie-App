package com.example.foodie.chefFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodie.Chef;
import com.example.foodie.MainMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.R;
import com.example.foodie.SignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChefHomeFragment extends Fragment {


    RecyclerView recyclerView;
    private List<UpdateDishModel> updateDishModelList;
    private ChefHomeAdapter adapter;
    DatabaseReference dataaa;
    private String area;
    public Button logoutt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chef_home, container,false);
        getActivity().setTitle("Foodie");
        setHasOptionsMenu(true);
        logoutt= v.findViewById(R.id.logB);
        logoutt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Logout();
            }

                                   });
        recyclerView = v.findViewById(R.id.Recycle_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateDishModelList = new ArrayList<>();
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dataaa = FirebaseDatabase.getInstance().getReference("Chef").child(userid);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Chef chefc = dataSnapshot.getValue(Chef.class);

                area=chefc.getArea();
                chefDishes();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }


    private void chefDishes() {

        String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodDetails").child(area).child(useridd);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateDishModelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UpdateDishModel updateDishModel = snapshot.getValue(UpdateDishModel.class);
                    updateDishModelList.add(updateDishModel);

                }
                adapter = new ChefHomeAdapter(getContext(), updateDishModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }









    private void Logout() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);

    }



}

