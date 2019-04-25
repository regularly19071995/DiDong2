package com.example.administrator.appbuyonline;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbarLaptop;
    ListView lvLaptop;
    LaptopAdapter laptopAdapter;
    ArrayList<SanPham> mangLaptop;
    int idLaptop = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    LaptopActivity.mHandler mHandler;
    boolean limitData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIDLoaisp();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }else{
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối internet");
            finish();
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
    private void LoadMoreData() {
        lvLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangLaptop.get(i));
                startActivity(intent);
            }
        });
        lvLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firtItem, int visibleItem, int totallItem) {
                if(firtItem + visibleItem == totallItem && totallItem != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    Thread thread = new Thread();
                    thread.start();
                }
            }
        });
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void AnhXa() {
        toolbarLaptop = findViewById(R.id.tbLaptop);
        lvLaptop = findViewById(R.id.lvLaptop);
        mangLaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(),mangLaptop);
        lvLaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }
    private void GetIDLoaisp() {
        idLaptop = getIntent().getIntExtra("idLoaisanpham",-1);
        Log.d("giatriLoaisanpham",idLaptop+"");
    }

    private void GetData(int Page) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongDanDienThoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenLaptop = "";
                int giaLaptop = 0;
                String hinhanhLaptop = "";
                String motaLaptop = "";
                int IDLaptop = 0;
                if(response != null && response.length() != 2){
                    lvLaptop.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i =0 ; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idLaptop = jsonObject.getInt("id");
                            tenLaptop = jsonObject.getString("tensp");
                            giaLaptop  = jsonObject.getInt("giasp");
                            hinhanhLaptop = jsonObject.getString("hinhanhsp");
                            motaLaptop = jsonObject.getString("motasp");
                            IDLaptop = jsonObject.getInt("idsanpham");
                            mangLaptop.add(new SanPham(idLaptop,tenLaptop,giaLaptop,hinhanhLaptop,motaLaptop,IDLaptop));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitData = true;
                    lvLaptop.removeFooterView(footerView);
                    CheckConnection.showToast_Short(getApplicationContext(),"Da het du lieu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idLaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvLaptop.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class Thread extends java.lang.Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                DienThoaiActivity.Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
