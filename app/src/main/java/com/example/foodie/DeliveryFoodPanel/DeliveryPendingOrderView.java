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

public class DeliveryPendingOrderView extends AppCompatActivity {

    RecyclerView recyclerViewdish;
    private List<DeliveryShipOrders> deliveryShipOrdersList= new ArrayList<>();
    private DeliveryPendingOrderViewAdapter adapter;
    DatabaseReference reference;
    String RandomUID;
    TextView grandTotalTextView, addressTextView, nameTextView, numberTextView, chefNameTextView;
    LinearLayout linearLayout1;
    DeliveryShipOrders1 deliveryShipOrderObject;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String deliveryId = "oCpc4SwLVFbKO0fPdtp4R6bmDmI3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_pending_order_view);
        deliveryShipOrderObject= (DeliveryShipOrders1) getIntent().getSerializableExtra("deliveryShipOrderObject");
        recyclerViewdish = findViewById(R.id.delivieworder);
        recyclerViewdish.setHasFixedSize(true);
        recyclerViewdish.setLayoutManager(new LinearLayoutManager(DeliveryPendingOrderView.this));
        linearLayout1 = (LinearLayout) findViewById(R.id.linear1);
        grandTotalTextView = (TextView) findViewById(R.id.Dtotal);
        addressTextView = (TextView) findViewById(R.id.DAddress);
        chefNameTextView =(TextView)findViewById(R.id.chefname);
        nameTextView = (TextView) findViewById(R.id.DName);
        numberTextView = (TextView) findViewById(R.id.DNumber);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        setInfo();
        deliveryOrders();
    }

    private void setInfo() {
        grandTotalTextView.setText("BDT " + deliveryShipOrderObject.getGrandTotalPrice());
        addressTextView.setText(deliveryShipOrderObject.getAddress());
        nameTextView.setText(deliveryShipOrderObject.getName());
        numberTextView.setText("+880" + deliveryShipOrderObject.getMobileNumber());
        chefNameTextView.setText("Chef "+ deliveryShipOrderObject.getChefName());
    }

    private void deliveryOrders() {
        RandomUID = deliveryShipOrderObject.getRandomUID();

        reference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryId).child(RandomUID).child("Dishes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                deliveryShipOrdersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DeliveryShipOrders deliveryShipOrders = snapshot.getValue(DeliveryShipOrders.class);
                    deliveryShipOrdersList.add(deliveryShipOrders);
                }
                if (deliveryShipOrdersList.size() == 0) {
                    linearLayout1.setVisibility(View.INVISIBLE);
                } else {
                    linearLayout1.setVisibility(View.VISIBLE);
                    adapter = new DeliveryPendingOrderViewAdapter(DeliveryPendingOrderView.this, deliveryShipOrdersList);
                    recyclerViewdish.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
