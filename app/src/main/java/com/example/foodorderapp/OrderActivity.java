package com.example.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.foodorderapp.Adapters.OrdersAdapter;
import com.example.foodorderapp.Models.OrdersModel;
import com.example.foodorderapp.databinding.ActivityOrderBinding;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;
    ArrayList<OrdersModel> list;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        helper = new DBHelper(this);
        list = new ArrayList<>();

        list = helper.getOrders();
        Log.d("raja",list.toString());
//        list.add(new OrdersModel(R.drawable.pizza,"Pizza","10","23232323"));
//        list.add(new OrdersModel(R.drawable.paubhaji,"Pau bhaji","5","24242422"));
//        list.add(new OrdersModel(R.drawable.burger,"Burger","10","56566565"));
//        list.add(new OrdersModel(R.drawable.pasta,"Pasta","10","78788778878"));
//        list.add(new OrdersModel(R.drawable.pizza,"Pizza","10","23232323"));
//        list.add(new OrdersModel(R.drawable.pizza,"Pizza","10","23232323"));

        OrdersAdapter adapter = new OrdersAdapter(this,list);
        binding.orderrecycler.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.orderrecycler.setLayoutManager(linearLayoutManager);
    }
}