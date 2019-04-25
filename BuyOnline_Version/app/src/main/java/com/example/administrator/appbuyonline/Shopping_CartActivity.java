package com.example.administrator.appbuyonline;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Shopping_CartActivity extends AppCompatActivity {
    ListView lvGioHang;
    TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhToan, btnTiepTucMua;
    Toolbar tbGioHang;
    GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping__cart);
        AnhXa();
        ActionToolBar();
        CheckData();
        EventUtil();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.mangGioHang.size() >0){
                    Intent intent = new Intent(getApplicationContext(),ThongTinKHActivity.class);
                    startActivity(intent);
                }else{
                    CheckConnection.showToast_Short(getApplicationContext(),"Giỏ hàng của hạn chưa có sản phẩm để thanh toán");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Shopping_CartActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.mangGioHang.size() <=0){
                            txtThongBao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.mangGioHang.remove(pos);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUtil();
                            if(MainActivity.mangGioHang.size() <=0){
                                txtThongBao.setVisibility(View.VISIBLE);
                            }
                            else{
                                txtThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUtil() {
        long tongtien = 0;
        for(int i = 0; i < MainActivity.mangGioHang.size(); i++)
        {
            tongtien+= MainActivity.mangGioHang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien)+"Đ");
    }

    private void CheckData() {
        if(MainActivity.mangGioHang.size() <=0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        }else{
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(tbGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        lvGioHang = findViewById(R.id.lvGioHang);
        txtThongBao = findViewById(R.id.txtThongBao);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToanGioHang);
        btnTiepTucMua = findViewById(R.id.btnTiepTucMuaHang);
        tbGioHang = findViewById(R.id.tbGioHang);
        gioHangAdapter = new GioHangAdapter(Shopping_CartActivity.this,MainActivity.mangGioHang);
        lvGioHang.setAdapter(gioHangAdapter);
    }
}
