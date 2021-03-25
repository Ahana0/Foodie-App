package com.example.foodie;

import android.os.Bundle;
import android.view.MenuItem;
import com.example.foodie.DeliveryFoodPanel.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class DeliveryFoodPanel_NavigationBottom extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_panel);
        BottomNavigationView navigationView = findViewById(R.id.delivery_bottom);
        navigationView.setOnNavigationItemSelectedListener(this);
        UpdateToken();
        String name = getIntent().getStringExtra("PAGE");
        if (name != null) {
            if (name.equalsIgnoreCase("DeliveryOrderpage"))
            {
                loaddeliveryfragment(new DeliveryPendingOrderFragment());
            }

        } else {
            loaddeliveryfragment(new DeliveryPendingOrderFragment());
        }

    }

    private void UpdateToken() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    private boolean loaddeliveryfragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_delivery, fragment).commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.pendingOrder:
                fragment = new DeliveryPendingOrderFragment();
                break;
            case R.id.shippingOrder:
                fragment= new DeliveryShipOrderFragment();
                break;}






        return loaddeliveryfragment(fragment);
    }

}
