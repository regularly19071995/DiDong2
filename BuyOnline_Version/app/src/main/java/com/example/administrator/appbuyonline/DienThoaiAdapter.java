package com.example.administrator.appbuyonline;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiAdapter extends BaseAdapter {
    public DienThoaiAdapter(Context context, ArrayList<SanPham> arrayDienThoai) {
        this.context = context;
        this.arrayDienThoai = arrayDienThoai;
    }

    Context context;
    ArrayList<SanPham> arrayDienThoai;

    @Override
    public int getCount() {
        return arrayDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txtTenDT, txtGiaDT, txtMoTaDT;
        public ImageView imgDT;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view  = inflater.inflate(R.layout.dong_dien_thoai,viewGroup, false);
            viewHolder.txtTenDT = view.findViewById(R.id.txtTenDienThoai);
            viewHolder.txtGiaDT = view.findViewById(R.id.txtGiaDienThoai);
            viewHolder.txtMoTaDT = view.findViewById(R.id.txtMoTaDienThoai);
            viewHolder.imgDT = view.findViewById(R.id.imgDienThoai);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = (SanPham) getItem(i);
        viewHolder.txtTenDT.setText(sanPham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDT.setText("Giá: " + decimalFormat.format(sanPham.getGia())+ "Đ");
        viewHolder.txtMoTaDT.setMaxLines(2);
        viewHolder.txtMoTaDT.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTaDT.setText(sanPham.getMotasp());
        Picasso.with(context).load(sanPham.getHinhanhsp()).placeholder(R.drawable.noimage).error(R.drawable.erorr).into(viewHolder.imgDT);
        return view;
    }
}
