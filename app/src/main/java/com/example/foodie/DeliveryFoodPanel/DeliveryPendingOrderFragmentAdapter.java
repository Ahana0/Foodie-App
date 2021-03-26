package com.example.foodie.DeliveryFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.R;
import com.example.foodie.ReusableCodeForAll;
import com.example.foodie.APIService;
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

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryPendingOrderFragmentAdapter extends RecyclerView.Adapter<DeliveryPendingOrderFragmentAdapter.ViewHolder> {

    private Context context;
    private List<DeliveryShipOrders1> deliveryShipOrders1list;
    private APIService apiService;
    String chefid;
    LayoutInflater layoutInflater;
    String deliveryId = "oCpc4SwLVFbKO0fPdtp4R6bmDmI3";
    FirebaseUser firebaseUser;


    public DeliveryPendingOrderFragmentAdapter(Context context, List<DeliveryShipOrders1> deliveryShipOrders1list) {
        this.deliveryShipOrders1list = deliveryShipOrders1list;
        this.context = context;
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_pendingorder, parent, false);
//        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DeliveryShipOrders1 deliveryShipOrders1 = deliveryShipOrders1list.get(position);
        holder.Address.setText(deliveryShipOrders1.getAddress());
        holder.mobilenumber.setText("+880" + deliveryShipOrders1.getMobileNumber());
        holder.grandtotalprice.setText("Grand Total: BDT " + deliveryShipOrders1.getGrandTotalPrice());
        String randomUID = deliveryShipOrders1.getRandomUID();
        //final String randomuid="ae61508c-1aaf-4813-a04c-5fef9b07266e";

        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeliveryPendingOrderView.class);
                intent.putExtra("deliveryShipOrderObject", deliveryShipOrders1);
                context.startActivity(intent);
            }
        });

        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryId).child(randomUID).child("Dishes");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            DeliveryShipOrders deliveryShipOrderss = snapshot.getValue(DeliveryShipOrders.class);
                            String dishid = deliveryShipOrderss.getDishId();
                            chefid = deliveryShipOrderss.getChefId();
                            FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(firebaseUser.getUid()).child(randomUID).child("Dishes").child(dishid).setValue(deliveryShipOrderss);
                        }

                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryId).child(randomUID).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                DeliveryShipOrders1 deliveryShipOrders11 = dataSnapshot.getValue(DeliveryShipOrders1.class);
                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("Address", deliveryShipOrders11.getAddress());
                                hashMap1.put("ChefId", deliveryShipOrders11.getChefId());
                                hashMap1.put("ChefName", deliveryShipOrders11.getChefName());
                                hashMap1.put("GrandTotalPrice", deliveryShipOrders11.getGrandTotalPrice());
                                hashMap1.put("MobileNumber", deliveryShipOrders11.getMobileNumber());
                                hashMap1.put("Name", deliveryShipOrders11.getName());
                                hashMap1.put("RandomUID", randomUID);
                                hashMap1.put("UserId", deliveryShipOrders11.getUserId());
                                FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(firebaseUser.getUid()).child(randomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryId).child(randomUID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                FirebaseDatabase.getInstance().getReference().child("Tokens").child(chefid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        String usertoken = dataSnapshot.getValue(String.class);
                                                        Toast.makeText(context,"Now you can check orders which are to be shipped",Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(chefid).child(randomUID).removeValue();
                                            }
                                        });

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryId).child(randomUID).removeValue();
            }
        });

    }

    private void sendNotifications(String usertoken, String title, String message, String order) {

        Data data = new Data(title, message, order);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return deliveryShipOrders1list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice, mobilenumber;
        Button viewOrder, Accept, Reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Address = itemView.findViewById(R.id.ad1);
            mobilenumber = itemView.findViewById(R.id.MB1);
            grandtotalprice = itemView.findViewById(R.id.TP1);
            viewOrder = itemView.findViewById(R.id.view1);
            Accept = itemView.findViewById(R.id.accept1);
            Reject = itemView.findViewById(R.id.reject1);
        }
    }
}
