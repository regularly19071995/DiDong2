package com.example.administrator.appbuyonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar tbChiTiet;
    ImageView imgChiTiet;
    TextView txtTen, txtGia,txtMoTa;
    Spinner spChiTiet;
    Button btnDatMua;
    int id = 0;
    String ten = "";
    int gia = 0;
    String hinhanh = "";
    String mota = "";
    int IDsanpham = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        AnhXa();
        ActionToolBar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }

    private void EventButton() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.mangGioHang.size() > 0){
                    int sl = Integer.parseInt(spChiTiet.getSelectedItem().toString());
                    boolean exit = false;
                    for(int i = 0; i< MainActivity.mangGioHang.size(); i++){
                        if(MainActivity.mangGioHang.get(i).getIdsp() == id){
                            MainActivity.mangGioHang.get(i).setSoluongsp(MainActivity.mangGioHang.get(i).getSoluongsp() + sl);
                            if(MainActivity.mangGioHang.get(i).getSoluongsp() >= 10){
                                MainActivity.mangGioHang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGioHang.get(i).setGiasp(gia + MainActivity.mangGioHang.get(i).getSoluongsp());
                            exit = true;
                        }
                    }
                    if(exit = false){
                        int soluong = Integer.parseInt(spChiTiet.getSelectedItem().toString());
                        long giamoi = soluong * gia;
                        MainActivity.mangGioHang.add(new GioHang(id,ten,giamoi,hinhanh,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spChiTiet.getSelectedItem().toString());
                    long giamoi = soluong * gia;
                    MainActivity.mangGioHang.add(new GioHang(id,ten,giamoi,hinhanh,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),Shopping_CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spChiTiet.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getId();
        ten = sanPham.getTensp();
        gia = sanPham.getGia();
        hinhanh = sanPham.getHinhanhsp();
        mota = sanPham.getMotasp();
        IDsanpham = sanPham.getIdsp();
        txtTen.setText(ten);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Gia: "+decimalFormat.format(gia)+ "Đ");
        txtMoTa.setText(mota);
        Picasso.with(getApplicationContext()).load(hinhanh).placeholder(R.drawable.noimage).error(R.drawable.erorr).into(imgChiTiet);
    }

    private void ActionToolBar() {
        setSupportActionBar(tbChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        tbChiTiet = findViewById(R.id.tbChiTietSP);
        imgChiTiet = findViewById(R.id.imgChiTiet);
        txtTen = findViewById(R.id.txtTenChiTiet);
        txtGia = findViewById(R.id.txtGiaChiTiet);
        txtMoTa = findViewById(R.id.txtMotaChiTietSP);
        spChiTiet = findViewById(R.id.spinner);
        btnDatMua = findViewById(R.id.btnDatMua);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mGioHang:
                Intent intent = new Intent(getApplicationContext(),Shopping_CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
