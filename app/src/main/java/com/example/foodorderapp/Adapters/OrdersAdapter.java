package com.example.foodorderapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.DBHelper;
import com.example.foodorderapp.DetailActivity;
import com.example.foodorderapp.MainActivity;
import com.example.foodorderapp.Models.OrdersModel;
import com.example.foodorderapp.OrderActivity;
import com.example.foodorderapp.R;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.viewholder> {

    Context context;
    ArrayList<OrdersModel> list;

    public OrdersAdapter(Context context, ArrayList<OrdersModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_sample,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

       final OrdersModel ordersModel = list.get(position);

        holder.orderitemImage.setImageResource(ordersModel.getOrderImage());
        holder.orderitemname.setText(ordersModel.getSoldItemName());
        holder.orderprice.setText(ordersModel.getPrice());
        holder.orderitemno.setText(ordersModel.getOrderNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id",Integer.parseInt(ordersModel.getOrderNumber()));
                intent.putExtra("type",2);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Item?").setIcon(R.drawable.ic_warning)
                        .setMessage("Are You Sure To Delete This Item...").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBHelper helper = new DBHelper(context);
                        int deleted = helper.deleteorder(Integer.parseInt(ordersModel.getOrderNumber()));
                        if (deleted == 1){
                            Toast.makeText(context, "Deleted!!!", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(context, OrderActivity.class);
                            context.startActivity(i1);
                        }else {
                            Toast.makeText(context, "Error!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        ImageView orderitemImage;
        TextView orderitemname,orderitemno,orderprice;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            orderitemImage = itemView.findViewById(R.id.orderimage);
            orderitemname = itemView.findViewById(R.id.orderitemname);
            orderitemno = itemView.findViewById(R.id.ordernumber);
            orderprice = itemView.findViewById(R.id.orderprice1);
        }
    }
}
