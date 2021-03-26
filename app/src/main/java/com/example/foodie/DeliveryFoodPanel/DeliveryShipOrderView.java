package com.example.foodie.DeliveryFoodPanel;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class DeliveryShipOrderView extends AppCompatActivity {

    RecyclerView recyclerViewdish;
    private List<DeliveryShipFinalOrders> deliveryShipFinalOrdersList = new ArrayList<>();
    private DeliveryShipOrderViewAdapter adapter;
    DatabaseReference reference;
    String RandomUID;
    TextView grandtotal, address, name, number, ChefName;
    LinearLayout l1;
    DeliveryShipFinalOrders1 deliveryShipFinalOrderObject;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_ship_order_view);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        deliveryShipFinalOrderObject = (DeliveryShipFinalOrders1) getIntent().getSerializableExtra("deliveryShipFinalOrderObject");
        recyclerViewdish = findViewById(R.id.delishipvieworder);
        recyclerViewdish.setHasFixedSize(true);
        recyclerViewdish.setLayoutManager(new LinearLayoutManager(DeliveryShipOrderView.this));
        l1 = (LinearLayout) findViewById(R.id.linear2);
        grandtotal = (TextView) findViewById(R.id.Shiptotal);
        ChefName = (TextView) findViewById(R.id.chefname1);
        address = (TextView) findViewById(R.id.ShipAddress);
        name = (TextView) findViewById(R.id.ShipName);
        number = (TextView) findViewById(R.id.ShipNumber);
        setInfo();
        deliveryfinalorders();
    }

    private void setInfo() {
        grandtotal.setText("BDT " + deliveryShipFinalOrderObject.getGrandTotalPrice());
        address.setText(deliveryShipFinalOrderObject.getAddress());
        name.setText(deliveryShipFinalOrderObject.getName());
        number.setText("+880" + deliveryShipFinalOrderObject.getMobileNumber());
        ChefName.setText("Chef " + deliveryShipFinalOrderObject.getChefName());
    }

    private void deliveryfinalorders() {

        RandomUID = deliveryShipFinalOrderObject.getRandomUID();

        reference = FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(firebaseUser.getUid()).child(RandomUID).child("Dishes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deliveryShipFinalOrdersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DeliveryShipFinalOrders deliveryShipFinalOrders = snapshot.getValue(DeliveryShipFinalOrders.class);
                    deliveryShipFinalOrdersList.add(deliveryShipFinalOrders);
                }
                if (deliveryShipFinalOrdersList.size() == 0) {
                    l1.setVisibility(View.INVISIBLE);

                } else {
                    l1.setVisibility(View.VISIBLE);
                }
                adapter = new DeliveryShipOrderViewAdapter(DeliveryShipOrderView.this, deliveryShipFinalOrdersList);
                recyclerViewdish.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
