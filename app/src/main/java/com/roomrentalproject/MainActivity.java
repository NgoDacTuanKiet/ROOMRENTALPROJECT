package com.roomrentalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roomrentalproject.adapter.RoomAdapter;
import com.roomrentalproject.model.Room;

public class MainActivity extends AppCompatActivity {

    EditText eMaPhong,eTenPhong,eGia,eNguoiThue,eSdt;
    Spinner spTinhTrang;
    Button btAdd;

    RecyclerView recyclerView;
    RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        adapter = new RoomAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        btAdd.setOnClickListener(v -> addRoom());

    }

    private void initView(){

        eMaPhong = findViewById(R.id.maPhong);
        eTenPhong = findViewById(R.id.tenPhong);
        eGia = findViewById(R.id.giaThue);
        eNguoiThue = findViewById(R.id.tenNguoiThue);
        eSdt = findViewById(R.id.soDienThoai);

        spTinhTrang = findViewById(R.id.spTinhTrang);

        spTinhTrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String status = spTinhTrang.getSelectedItem().toString();

                if(status.equals("Còn trống")){
                    eNguoiThue.setVisibility(View.GONE);
                    eSdt.setVisibility(View.GONE);
                }else{
                    eNguoiThue.setVisibility(View.VISIBLE);
                    eSdt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btAdd = findViewById(R.id.btAdd);

        recyclerView = findViewById(R.id.recycleView);

        String[] data = {"Còn trống","Đã thuê"};

        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,data);

        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spTinhTrang.setAdapter(spinnerAdapter);
    }

    private boolean isMaPhongExist(String ma){

        for(int i=0;i<adapter.getItemCount();i++){
            if(adapter.getItem(i).getMaPhong().equals(ma))
                return true;
        }
        return false;
    }

    private boolean isTenPhongExist(String ten){

        for(int i=0;i<adapter.getItemCount();i++){
            if(adapter.getItem(i).getTenPhong().equals(ten))
                return true;
        }
        return false;
    }

    private boolean isValidPhone(String sdt){
        return sdt.matches("\\d{10}");
    }

    private void addRoom(){

        try{

            String ma = eMaPhong.getText().toString();
            String ten = eTenPhong.getText().toString();
            double gia = Double.parseDouble(eGia.getText().toString());
            String tinhTrang = spTinhTrang.getSelectedItem().toString();
            String nguoi = eNguoiThue.getText().toString();
            String sdt = eSdt.getText().toString();

            if(isMaPhongExist(ma)){
                Toast.makeText(this,"Mã phòng đã tồn tại",Toast.LENGTH_SHORT).show();
                return;
            }

            if(isTenPhongExist(ten)){
                Toast.makeText(this,"Tên phòng đã tồn tại",Toast.LENGTH_SHORT).show();
                return;
            }

            if(tinhTrang.equals("Đã thuê")){

                if(nguoi.isEmpty()){
                    Toast.makeText(this,"Nhập tên người thuê",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isValidPhone(sdt)){
                    Toast.makeText(this,"SĐT phải 10 số",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Room room = new Room(ma,ten,gia,tinhTrang,nguoi,sdt);

            adapter.add(room);

            clearForm();

        }catch (Exception e){

            Toast.makeText(this,"Giá phải là số",Toast.LENGTH_SHORT).show();
        }
    }


    private void clearForm(){
        eMaPhong.setText("");
        eTenPhong.setText("");
        eGia.setText("");
        eNguoiThue.setText("");
        eSdt.setText("");

        spTinhTrang.setSelection(0);
    }
}