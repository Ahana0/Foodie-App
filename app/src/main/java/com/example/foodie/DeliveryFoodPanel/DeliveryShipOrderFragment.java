package com.example.foodie.DeliveryFoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryShipOrderFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<DeliveryShipFinalOrders1> deliveryShipFinalOrders1List= new ArrayList<>();
    private DeliveryShipOrderFragmentAdapter adapter;
    private DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    DatabaseReference deliveryShipFinalOrdersRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragemnt_shippingorders, null);
        getActivity().setTitle("Ship Orders");
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = view.findViewById(R.id.delishiporder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deliveryShipFinalOrdersRef=FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders");
        DeliveryShipfinalOrder();
        return view;
    }

    private void DeliveryShipfinalOrder() {

        databaseReference = deliveryShipFinalOrdersRef.child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deliveryShipFinalOrders1List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    databaseReference.child(snapshot.getKey()).child("OtherInformation").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            DeliveryShipFinalOrders1 deliveryShipFinalOrders1 = dataSnapshot.getValue(DeliveryShipFinalOrders1.class);
                            deliveryShipFinalOrders1List.add(deliveryShipFinalOrders1);
                            adapter = new DeliveryShipOrderFragmentAdapter(getContext(), deliveryShipFinalOrders1List);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
