package com.example.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodorderapp.databinding.ActivityDetailBinding;


public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new DBHelper(this);

        if (getIntent().getIntExtra("type",0) == 1){

        int image = getIntent().getIntExtra("image",0);
        int price = Integer.parseInt(getIntent().getStringExtra("price"));
        final String name = getIntent().getStringExtra("name");
        final String desc = getIntent().getStringExtra("desc");

        binding.detailimage.setImageResource(image);
        binding.detaildescription.setText(desc);
        binding.price.setText(String.format("%d",price));
        binding.foodname.setText(name);

        binding.insertbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean inserted = helper.insertorders(binding.namebox.getText().toString(),binding.phonebox.getText().toString(),
                        price,image,name,desc,Integer.parseInt(binding.quantity.getText().toString()));

                if (inserted){
                    Toast.makeText(DetailActivity.this, "Inserted Successfully!!!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(DetailActivity.this,OrderActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(DetailActivity.this, "Error!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }else{
            int id = getIntent().getIntExtra("id",0);
            Cursor cursor = helper.getOrderById(id);
            Toast.makeText(this, ""+cursor.getString(1), Toast.LENGTH_SHORT).show();
            int image = cursor.getInt(4);
            binding.detailimage.setImageResource(cursor.getInt(4));
            binding.detaildescription.setText(cursor.getString(6));
            binding.price.setText(String.format("%d",cursor.getInt(3)));
            binding.foodname.setText(cursor.getString(6));

            binding.namebox.setText(cursor.getString(1));
            binding.phonebox.setText(cursor.getString(2));

            binding.insertbutton.setText("Update Now");
            binding.insertbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean updated = helper.updateorder(binding.namebox.getText().toString(),binding.phonebox.getText().toString(),
                            Integer.parseInt(binding.price.getText().toString()),image,binding.foodname.getText().toString(),
                            binding.detaildescription.getText().toString(),1,id);
                    if (updated){
                        Toast.makeText(DetailActivity.this, "Updated!!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(DetailActivity.this,OrderActivity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(DetailActivity.this, "Error!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}