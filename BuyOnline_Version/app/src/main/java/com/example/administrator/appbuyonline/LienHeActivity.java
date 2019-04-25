package com.example.administrator.appbuyonline;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class LienHeActivity extends AppCompatActivity {

    Toolbar toolbarLienHe;
    ListView lvLienHe;
    EditText edtName, edtEmail,edtNoiDung;
    Button btnGui;
    ArrayList<String>arrList = null;
    ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        AnhXa();
        ActionToolBar();
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrList.add("Name: "+edtName.getText()+"\nEmail: "+ edtEmail.getText()+ "\nMessage: "+ edtNoiDung.getText()+"\n");
                adapter.notifyDataSetChanged();
            }
        });
        lvLienHe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrList.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void AnhXa() {
        toolbarLienHe = findViewById(R.id.tbLienHe);
        lvLienHe = findViewById(R.id.lvLienHe);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtNoiDung = findViewById(R.id.edtNoiDung);
        btnGui = findViewById(R.id.btnSubmit);
        arrList=new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrList);
        lvLienHe.setAdapter(adapter);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarLienHe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienHe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
