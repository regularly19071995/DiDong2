package com.example.administrator.appbuyonline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolBar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    ListView listView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> loaispArrayList;
    LoaiSPAdapter loaiSPAdapter;
    int id = 0;
    String tenLoaiSP = "";
    String hinhanhLoaiSP = "";
    //mang san pham
    ArrayList<SanPham> mangSP;
    SanPhamAdapter sanPhamAdapter;
    public static ArrayList<GioHang> mangGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaiSP();
            GetDuLieuLoaiSPMoiNhat();
            CatchOnItemListView();
        }
        else{
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }


    }

    public void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idLoaisanpham",loaispArrayList.get(i).getId());
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idLoaisanpham",loaispArrayList.get(i).getId());
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                            startActivity(intent);
                        }
                        else{
                            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    public void GetDuLieuLoaiSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanLoaiSPMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID = 0;
                    String ten = "";
                    Integer gia = 0;
                    String hinhanh = "";
                    String mota = "";
                    int IDSanPham = 0;
                    for(int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            ten = jsonObject.getString("tensp");
                            gia = jsonObject.getInt("gia");
                            hinhanh = jsonObject.getString("hinhanh");
                            mota = jsonObject.getString("mota");
                            IDSanPham = jsonObject.getInt("idsp");
                            mangSP.add(new SanPham(ID,ten,gia,hinhanh,mota,IDSanPham));
                            sanPhamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void GetDuLieuLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for (int i=0;i < response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLoaiSP = jsonObject.getString("tenloaisanpham");
                            hinhanhLoaiSP = jsonObject.getString("hinhanhloaisanpham");
                            loaispArrayList.add(new Loaisp(id,tenLoaiSP,hinhanhLoaiSP));
                            loaiSPAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loaispArrayList.add(3, new Loaisp(0,"LIÊN HỆ","http://sre-energy.com/uploads/icon/landline.png"));
                    loaispArrayList.add(4,new Loaisp(0,"THÔNG TIN","https://condotelnhatrang.com/wp-content/uploads/sites/110/2015/11/icon-form.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.showToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://cdn.tgdd.vn/qcao/24_02_2019_22_30_59_M20-800-170.png");
        mangQuangCao.add("https://cdn.tgdd.vn/qcao/09_04_2019_16_23_08_Doc-quyen-800-170.png");
        mangQuangCao.add("https://cdn.tgdd.vn/qcao/11_04_2019_14_32_29_Realme-3-800-170.png");
        mangQuangCao.add("https://cdn.tgdd.vn/qcao/31_03_2019_23_55_17_800-170.png");
        mangQuangCao.add("https://cdn.tgdd.vn/qcao/21_04_2019_16_27_07_Galaxy-A10---normal-sale-800-170.png");
        for(int i =0;i <mangQuangCao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhXa(){
        toolBar = findViewById(R.id.tbManHinhChinh);
        viewFlipper = findViewById(R.id.vfManHinhChinh);
        recyclerView = findViewById(R.id.rvManHinhChinh);
        navigationView = findViewById(R.id.ngvManHinhchinh);
        listView = findViewById(R.id.lvManHinhChinh);
        drawerLayout = findViewById(R.id.dlManHinhChinh);
        loaispArrayList = new ArrayList<>();
        loaispArrayList.add(0, new Loaisp(0,"TRANG CHÍNH","http://www.stickpng.com/assets/thumbs/588a66aad06f6719692a2d1d.png"));
        loaiSPAdapter = new LoaiSPAdapter(loaispArrayList,getApplicationContext());
        listView.setAdapter(loaiSPAdapter);
        //san pham
        mangSP = new ArrayList<SanPham>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(),mangSP);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanPhamAdapter);
        if(mangGioHang != null){

        }else{
            mangGioHang = new ArrayList<>();
        }
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
