package com.example.java_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// OrderRecordAdapter.java
public class OrderRecordAdapter extends RecyclerView.Adapter<OrderRecordAdapter.ViewHolder> {
    private List<OrderRecord> orderRecords;

    public OrderRecordAdapter(List<OrderRecord> orderRecords) {
        this.orderRecords = orderRecords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderRecord orderRecord = orderRecords.get(position);
        holder.bind(orderRecord);
    }

    @Override
    public int getItemCount() {
        return orderRecords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewUsername, textViewMenu, textViewBread, textViewSauce, textViewPrice, textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewMenu = itemView.findViewById(R.id.textViewMenu);
            textViewBread = itemView.findViewById(R.id.textViewBread);
            textViewSauce = itemView.findViewById(R.id.textViewSauce);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }

        public void bind(OrderRecord orderRecord) {
            textViewUsername.setText("주문자 명: " + orderRecord.getUsername());
            textViewMenu.setText("메뉴: " + orderRecord.getMenu());
            textViewBread.setText("빵: " + orderRecord.getBread());
            textViewSauce.setText("소스: " + orderRecord.getSauce());
            textViewPrice.setText("금액: " + orderRecord.getPrice());
            textViewDate.setText("주문 일자: " + orderRecord.getDate());
        }
    }
}

