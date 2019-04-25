package com.example.administrator.appbuyonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.HashMap;
import java.util.Map;

public class ThongTinKHActivity extends AppCompatActivity {

    Button btnXacNhan, btnBack;
    EditText edtTenKH, edtSDT, edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_kh);
        AnhXa();
        //tro ve
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventButton();
                }
            });
        }else{
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối  internet");
        }
    }

    private void EventButton() {
        final String ten = edtTenKH.getText().toString().trim();
        final String sdt = edtSDT.getText().toString().trim();
        final String email = edtEmail.getText().toString().trim();
        if (ten.length() > 0 && sdt.length() > 0 && email.length() >0){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongDanDonHang, new Response.Listener<String>() {
                @Override
                public void onResponse(final String madonhang) {
                    Log.d("madonhang",madonhang);
                    if (Integer.parseInt(madonhang) > 0){
                        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
                        StringRequest sr = new StringRequest(Request.Method.POST, Server.duongDanChiTiet, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("1")){
                                    MainActivity.mangGioHang.clear();
                                    CheckConnection.showToast_Short(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công");
                                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    CheckConnection.showToast_Short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                }else{
                                    CheckConnection.showToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng đã bị lỗi");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                JSONArray jsonArray = new JSONArray();
                                for (int i=0; i < MainActivity.mangGioHang.size();i++){
                                    JSONObject jsonObject= new JSONObject();
                                    try {
                                        jsonObject.put("madonhang",madonhang);
                                        jsonObject.put("masanpham",MainActivity.mangGioHang.get(i).getIdsp());
                                        jsonObject.put("tensanpham",MainActivity.mangGioHang.get(i).getTensp());
                                        jsonObject.put("giasanpham",MainActivity.mangGioHang.get(i).getGiasp());
                                        jsonObject.put("soluongsanpham",MainActivity.mangGioHang.get(i).getSoluongsp());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    jsonArray.put(jsonObject);
                                }
                                HashMap<String,String> hashMap = new HashMap<String,String>();
                                hashMap.put("json",jsonArray.toString());
                                return hashMap;
                            }
                        };
                        request.add(sr);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<String,String>();
                    hashMap.put("tenkhachhang",ten);
                    hashMap.put("sodienthoai",sdt);
                    hashMap.put("email",email);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }else{
            CheckConnection.showToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại dữ liệu");
        }
    }

    private void AnhXa() {
        edtTenKH = findViewById(R.id.edtTenKH);
        edtSDT = findViewById(R.id.edtSDT);
        edtEmail = findViewById(R.id.edtEmail);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnBack = findViewById(R.id.btnTroVe);
    }
}
