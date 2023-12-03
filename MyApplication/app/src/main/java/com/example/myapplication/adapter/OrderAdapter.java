package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RowOrdersLayoutBinding;
import com.example.myapplication.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orders = new ArrayList<>();
    private List<Order> ordersFiltered = new ArrayList<>();
    private Context context;
    private OnItemClickListener listener;

    public OrderAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        RowOrdersLayoutBinding binding = RowOrdersLayoutBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderAdapter.ViewHolder holder, final int position) {
        holder.bind( context, ordersFiltered.get(position) );

        holder.binding.content.setOnClickListener(v->{
            if(listener != null) {
                listener.onItemClick( ordersFiltered.get(position), position );
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersFiltered.size();
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        ordersFiltered.clear();
        ordersFiltered.addAll(orders);
    }

    public void removeOrder(Order order) {
        orders.remove( order );
        ordersFiltered.remove( order );
    }

    public void addOrder(Order order) {
        orders.add( order );
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ordersFiltered.clear();

        if (charText.length() == 0) {
            ordersFiltered.addAll(orders);
        } else {
            for (Order order : orders) {
                if (order.getOrderId().toLowerCase(Locale.getDefault()).contains(charText)
                    || order.getPlates().toLowerCase(Locale.getDefault()).contains(charText)) {
                    ordersFiltered.add( order );
                }
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RowOrdersLayoutBinding binding;

        ViewHolder(RowOrdersLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind( Context context,  Order order ) {
            binding.type.setText( order.getServiceType() );
            binding.orderStatus.setText( order.getOrderStatus() );
            binding.ordenId.setText( order.getOrderId() );
            binding.finicio.setText( order.getStartTime() );
            binding.model.setText( order.getModel() );
            binding.plates.setText( order.getPlates() );
            binding.piramidColor.setText( order.getPyramidColor() );
            binding.piramidNumber.setText( "" + order.getPyramidNumber() );

            switch(order.getOrderStatusId()) {
                case 1:
                    binding.cardStatus.setCardBackgroundColor( ContextCompat.getColor(context, R.color.red) );
                    break;
                case 2:
                    binding.cardStatus.setCardBackgroundColor( ContextCompat.getColor(context, R.color.black) );
                    break;
                case 3:
                    binding.cardStatus.setCardBackgroundColor( ContextCompat.getColor(context, R.color.aqua) );
                    break;
                case 4:
                    binding.cardStatus.setCardBackgroundColor( ContextCompat.getColor(context, R.color.yellow) );
                    break;
                default:
                    binding.cardStatus.setCardBackgroundColor( ContextCompat.getColor(context, R.color.gray) );
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Order order, Integer positionRemoved);
    }
}
