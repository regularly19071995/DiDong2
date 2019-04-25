package com.example.administrator.appbuyonline;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder>{

    Context context;
    ArrayList<SanPham> sanPhamArrayList;

    public SanPhamAdapter(Context context, ArrayList<SanPham> sanPhamArrayList) {
        this.context = context;
        this.sanPhamArrayList = sanPhamArrayList;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dong_sanphammoinhat,viewGroup, false);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder itemHolder, final int pos) {
        SanPham sanPham = sanPhamArrayList.get(pos);
        itemHolder.txtTensp.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        itemHolder.txtGiasp.setText("Giá: " + decimalFormat.format(sanPham.getGia())+ "Đ");
        Picasso.with(context).load(sanPham.getHinhanhsp()).placeholder(R.drawable.noimage).error(R.drawable.erorr).into(itemHolder.imgHinhsp);
    }

    @Override
    public int getItemCount() {
        return sanPhamArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhsp;
        public TextView txtTensp, txtGiasp;

        public ItemHolder(View itemView) {
            super(itemView);
            imgHinhsp = itemView.findViewById(R.id.imgSP);
            txtTensp = itemView.findViewById(R.id.textViewTen);
            txtGiasp = itemView.findViewById(R.id.textViewGia);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham",sanPhamArrayList.get(getLayoutPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.showToast_Short(context,sanPhamArrayList.get(getLayoutPosition()).getTensp());
                    context.startActivity(intent);
                }
            });
        }
    }
}
