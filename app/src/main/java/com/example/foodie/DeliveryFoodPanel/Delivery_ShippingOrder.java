package com.example.foodie.DeliveryFoodPanel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodie.DeliveryFoodPanel_NavigationBottom;
import com.example.foodie.R;
import com.example.foodie.APIService;
import com.example.foodie.Client;
import com.example.foodie.Data;
import com.example.foodie.MyResponse;
import com.example.foodie.NotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Delivery_ShippingOrder extends AppCompatActivity {


    TextView Address, ChefName, grandtotal, MobileNumber, Custname;
    Button Call, Shipped;
    private APIService apiService;
    LinearLayout l1, l2;
    String randomuid;
    String userid, Chefid;
    FirebaseUser firebaseUser;
    DeliveryShipFinalOrders1 deliveryShipFinalOrderObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_shiporders);
        deliveryShipFinalOrderObject=(DeliveryShipFinalOrders1) getIntent().getSerializableExtra("deliveryShipFinalOrderObject");
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Address = (TextView) findViewById(R.id.ad3);
        ChefName = (TextView) findViewById(R.id.chefname2);
        grandtotal = (TextView) findViewById(R.id.Shiptotal1);
        MobileNumber = (TextView) findViewById(R.id.ShipNumber1);
        Custname = (TextView) findViewById(R.id.ShipName1);
        l1 = (LinearLayout) findViewById(R.id.linear3);
        l2 = (LinearLayout) findViewById(R.id.linearl1);
        Call = (Button) findViewById(R.id.call2);
        Shipped = (Button) findViewById(R.id.shipped2);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        setInfo();
        Shipped();

    }

    private void setInfo() {
        grandtotal.setText("BDT " + deliveryShipFinalOrderObject.getGrandTotalPrice());
        Address.setText(deliveryShipFinalOrderObject.getAddress());
        Custname.setText(deliveryShipFinalOrderObject.getName());
        MobileNumber.setText("+880" + deliveryShipFinalOrderObject.getMobileNumber());
        ChefName.setText("Chef " + deliveryShipFinalOrderObject.getChefName());
        userid = deliveryShipFinalOrderObject.getUserId();
        Chefid = deliveryShipFinalOrderObject.getChefId();
        randomuid = deliveryShipFinalOrderObject.getRandomUID();
    }

    private void Shipped() {

        Shipped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(randomuid).child("OtherInformation").child("Status").setValue("Your Order is delivered")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(randomuid).removeValue();
                        FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(firebaseUser.getUid()).child(randomuid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(userid).child("isOrdered").setValue("false");
                            }
                        });
                        AlertDialog.Builder builder = new AlertDialog.Builder(Delivery_ShippingOrder.this);
                        builder.setMessage("Order is delivered, Now you can check for new Orders");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(Delivery_ShippingOrder.this, DeliveryFoodPanel_NavigationBottom.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }
        });


    }

    private void sendNotifications(String usertoken, String title, String message, String order) {
        Data data = new Data(title, message, order);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(retrofit2.Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(Delivery_ShippingOrder.this, "Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}
