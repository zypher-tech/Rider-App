package com.bookaholicc.ridersapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookaholicc.ridersapp.Activity.OrderDetailsActivity;
import com.bookaholicc.ridersapp.Model.MiniProduct;
import com.bookaholicc.ridersapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

/**
 * Created by nandhu on 23/6/17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListHolder>{


    private Context mContext;
    private List<MiniProduct> mList;



    public ProductListAdapter(Context mContext, List<MiniProduct> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ProductListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.product_item,parent,false);
        return new ProductListHolder(v);

    }

    @Override
    public void onBindViewHolder(ProductListHolder holder, int position) {
        Picasso.with(mContext)
                .load(mList.get(position).getImageURL());
        holder.mPname.setText(mList.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class  ProductListHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.p_item_p_image)
        ImageView mpImage;
        @BindView(R.id.p_item_pname)
        TextView mPname;
        public ProductListHolder(View itemView) {
            super(itemView);
        }
    }

}
