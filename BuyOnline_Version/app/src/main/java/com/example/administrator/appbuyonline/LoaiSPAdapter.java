package com.example.administrator.appbuyonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends BaseAdapter {
    ArrayList<Loaisp> loaispArrayList;
    Context context;

    public LoaiSPAdapter(ArrayList<Loaisp> loaispArrayList, Context context) {
        this.loaispArrayList = loaispArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaispArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaispArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //ViewHolder
    public class ViewHolder{
        TextView txtTenLoaiSP;
        ImageView imgLoaiSP;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_loai_sp,null);
            //gan id
            viewHolder.txtTenLoaiSP = view.findViewById(R.id.txtLoaiSP);
            viewHolder.imgLoaiSP = view.findViewById(R.id.imgLoaiSP);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Loaisp loaisp = (Loaisp) getItem(i);
        viewHolder.txtTenLoaiSP.setText(loaisp.getTenLoaisp());
        Picasso.with(context).load(loaisp.getHinhanhLoaisp()).placeholder(R.drawable.noimage).error(R.drawable.erorr).into(viewHolder.imgLoaiSP);

        return view;
    }
}
