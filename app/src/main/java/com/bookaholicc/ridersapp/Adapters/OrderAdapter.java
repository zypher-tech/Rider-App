package com.bookaholicc.ridersapp.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bookaholicc.ridersapp.Model.MiniProduct;
import com.bookaholicc.ridersapp.Model.Order;
import com.bookaholicc.ridersapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 22/6/17.
 * The Adapter Which Display ORders in list
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {



    private List<Order>  mList ;
    private Context mContext;
    private  OrderCallback mCallback ;

    public OrderAdapter(List<Order> mList, Context mContext, OrderCallback mCallback) {
        this.mList = mList;
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    @Override
    public OrderAdapter.OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.order_item,parent,false);
        return new OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.OrderHolder holder, int position) {

        final Order order = mList.get(position);
        holder.mOrderId.setText(""+order.getOrderId());
        holder.mOrderName.setText(""+order.getAmount());
        holder.mOrderName.setText(order.getFirstName());
        holder.mSeeDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallback != null){
                    mCallback.seeDetails(order);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class  OrderHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.order_id) TextView mOrderId;
        @BindView(R.id.order_name) TextView mOrderName;
        @BindView(R.id.order_amount) TextView mAmount;
        @BindView(R.id.see_details) Button mSeeDetailsButton;


        public OrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OrderCallback{
        void seeDetails(Order order);
        void seeLocation(Order order);
    }
}


