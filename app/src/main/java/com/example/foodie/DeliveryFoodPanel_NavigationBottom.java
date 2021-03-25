package com.example.foodie;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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
        loadFragment(new DeliveryPendingOrderFragment());
        UpdateToken();
//        String name = getIntent().getStringExtra("PAGE");
//        if (name != null) {
//            if (name.equalsIgnoreCase("DeliveryOrderpage"))
//            {
//                loaddeliveryfragment(new DeliveryPendingOrderFragment());
//            }
//
//        } else {
//            loaddeliveryfragment(new DeliveryPendingOrderFragment());
//        }

    }

    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    public void loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_delivery, fragment).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        boolean ok=false;
        switch (menuItem.getItemId()) {
            case R.id.pendingOrder:
                loadFragment(new DeliveryPendingOrderFragment());
                ok=true;
                break;
            case R.id.shippingOrder:
                loadFragment(new DeliveryShipOrderFragment());
                break;
            case R.id.logout:
                Logout();
                break;
        }

        return ok;
    }
    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(DeliveryFoodPanel_NavigationBottom.this, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
