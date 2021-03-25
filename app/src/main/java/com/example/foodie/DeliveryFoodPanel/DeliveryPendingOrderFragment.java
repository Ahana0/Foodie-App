package com.example.foodie.DeliveryFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodie.MainMenu;
import com.example.foodie.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryPendingOrderFragment extends Fragment {

    public RecyclerView recyclerView;
    public List<DeliveryShipOrders1> deliveryShipOrders1List= new ArrayList<>();
    public DeliveryPendingOrderFragmentAdapter adapter;
    private DatabaseReference databaseReference;
    private SwipeRefreshLayout swipeRefreshLayout;
    String deliveryId = "oCpc4SwLVFbKO0fPdtp4R6bmDmI3";
View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pendingorders, null);
        getActivity().setTitle("Pending Orders");
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.delipendingorder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DeliveryPendingOrders();

//        swipeRefreshLayout = view.findViewById(R.id.Swipe);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.green);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                DeliveryPendingOrders();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//        });

        return view;
    }

    private void DeliveryPendingOrders() {
        databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            //(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Dishes")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryId).child(snapshot.getKey()).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                DeliveryShipOrders1 deliveryShipOrders1 = dataSnapshot.getValue(DeliveryShipOrders1.class);
                                deliveryShipOrders1List.add(deliveryShipOrders1);
                                adapter = new DeliveryPendingOrderFragmentAdapter(getContext(), deliveryShipOrders1List);
                                recyclerView.setAdapter(adapter);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
//                        Log.d("delivery","size:"+deliveryShipOrders1List.size());

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
