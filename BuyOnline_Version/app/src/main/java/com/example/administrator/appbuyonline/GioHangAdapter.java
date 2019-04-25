package com.example.administrator.appbuyonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> gioHangArrayList;

    public GioHangAdapter(Context context, ArrayList<GioHang> gioHangArrayList) {
        this.context = context;
        this.gioHangArrayList = gioHangArrayList;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return gioHangArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtTenGH, txtGiaGH;
        public ImageView imgGioHang;
        public Button btnMinus,btnValue, btnPlus;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_gio_hang,viewGroup,false);
            viewHolder.txtTenGH = view.findViewById(R.id.txtTenGH);
            viewHolder.txtGiaGH = view.findViewById(R.id.txtGiaGH);
            viewHolder.imgGioHang = view.findViewById(R.id.imgGioHang);
            viewHolder.btnMinus = view.findViewById(R.id.btnMinus);
            viewHolder.btnValue = view.findViewById(R.id.btnValue);
            viewHolder.btnPlus = view.findViewById(R.id.btnPlus);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        viewHolder.txtTenGH.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGH.setText(decimalFormat.format(gioHang.getGiasp())+ "Đ");
        Picasso.with(context).load(gioHang.getHinhanhsp()).placeholder(R.drawable.noimage).error(R.drawable.erorr).into(viewHolder.imgGioHang);
        viewHolder.btnValue.setText(gioHang.getSoluongsp() +"");
        int sl = Integer.parseInt(viewHolder.btnValue.getText().toString());
        if(sl >= 10){
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }
        else if(sl <=1){
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        }else if(sl >=1){
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoinhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString())+1;
                int soluonghientai = MainActivity.mangGioHang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGioHang.get(i).getGiasp();
                MainActivity.mangGioHang.get(i).setSoluongsp(soluongmoinhat);
                long giamoinhat = (giaht * soluongmoinhat) / soluonghientai;
                MainActivity.mangGioHang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGH.setText(decimalFormat.format(giamoinhat)+ "Đ");
                Shopping_CartActivity.EventUtil();
                if(soluongmoinhat >9){
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(soluongmoinhat));
                }else{
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(soluongmoinhat));
                }
            }
        });
        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoinhat = Integer.parseInt(finalViewHolder.btnValue.getText().toString())-1;
                int soluonghientai = MainActivity.mangGioHang.get(i).getSoluongsp();
                long giaht = MainActivity.mangGioHang.get(i).getGiasp();
                MainActivity.mangGioHang.get(i).setSoluongsp(soluongmoinhat);
                long giamoinhat = (giaht * soluongmoinhat) / soluonghientai;
                MainActivity.mangGioHang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtGiaGH.setText(decimalFormat.format(giamoinhat)+ "Đ");
                Shopping_CartActivity.EventUtil();
                if(soluongmoinhat <2){
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(soluongmoinhat));
                }else{
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnValue.setText(String.valueOf(soluongmoinhat));
                }
            }
        });
        return view;
    }
}
