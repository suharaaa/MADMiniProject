package com.example.madminiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ItemDetails extends AppCompatActivity {

    ImageButton back;
    private ImageView itemImage;
    private TextView itemName, itemPrice, itemDescription;
    private String itemId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        itemId = getIntent().getStringExtra("code");
        back = findViewById(R.id.imageButton6);
        itemImage = (ImageView) findViewById(R.id.imageView15);
        itemName = (TextView) findViewById(R.id.textView3);
        itemPrice = (TextView) findViewById(R.id.textView4);
        itemDescription = (TextView) findViewById(R.id.textView8);

        getItemDetails(itemId);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ItemDetails.this, itemDisplay.class);
                startActivity(i);
            }
        });
    }

    private void getItemDetails(String itemId) {

        DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("item");

        itemRef.child(itemId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    ItemModel item = dataSnapshot.getValue(ItemModel.class);

                    itemName.setText(item.getName());
                    itemPrice.setText(item.getPrice());
                    itemDescription.setText(item.getDescription());
                    Picasso.get().load(item.getImg()).into(itemImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
