package com.roomrentalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateRoomActivity extends AppCompatActivity {

    EditText eMaPhong, eTenPhong, eGia, eNguoiThue, eSdt;
    Spinner spTinhTrang;
    Button btUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);

        eMaPhong = findViewById(R.id.maPhong);
        eTenPhong = findViewById(R.id.tenPhong);
        eGia = findViewById(R.id.giaThue);
        eNguoiThue = findViewById(R.id.tenNguoiThue);
        eSdt = findViewById(R.id.soDienThoai);
        spTinhTrang = findViewById(R.id.spTinhTrang);
        btUpdate = findViewById(R.id.btUpdate);

        // Spinner
        String[] data = {"Còn trống", "Đã thuê"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTinhTrang.setAdapter(adapter);

        // Ẩn hiện người thuê
        spTinhTrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if(pos == 0){
                    eNguoiThue.setVisibility(View.GONE);
                    eSdt.setVisibility(View.GONE);
                }else{
                    eNguoiThue.setVisibility(View.VISIBLE);
                    eSdt.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // nhận dữ liệu
        Intent intent = getIntent();

        eMaPhong.setText(intent.getStringExtra("maPhong"));
        eTenPhong.setText(intent.getStringExtra("tenPhong"));
        eGia.setText(intent.getDoubleExtra("giaThue", 0) + "");
        eNguoiThue.setText(intent.getStringExtra("tenNguoiThue"));
        eSdt.setText(intent.getStringExtra("soDienThoai"));

        int position = intent.getIntExtra("position",-1);

        String tinhTrang = intent.getStringExtra("tinhTrang");
        spTinhTrang.setSelection("Còn trống".equals(tinhTrang) ? 0 : 1);

        // Update
        btUpdate.setOnClickListener(v -> {

            try {

                String ma = eMaPhong.getText().toString();
                String ten = eTenPhong.getText().toString();
                String giaStr = eGia.getText().toString();
                String tinhTrangMoi = spTinhTrang.getSelectedItem().toString();
                String nguoi = eNguoiThue.getText().toString();
                String sdt = eSdt.getText().toString();

                if(ma.isEmpty()){
                    Toast.makeText(this,"Nhập mã phòng",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(ten.isEmpty()){
                    Toast.makeText(this,"Nhập tên phòng",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(giaStr.isEmpty()){
                    Toast.makeText(this,"Nhập giá thuê",Toast.LENGTH_SHORT).show();
                    return;
                }

                double gia = Double.parseDouble(giaStr);

                if(tinhTrangMoi.equals("Đã thuê")){

                    if(nguoi.isEmpty()){
                        Toast.makeText(this,"Nhập tên người thuê",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!sdt.matches("\\d{10}")){
                        Toast.makeText(this,"SĐT phải 10 số",Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else{
                    nguoi = "";
                    sdt = "";
                }

                Intent result = new Intent();

                result.putExtra("maPhong", ma);
                result.putExtra("tenPhong", ten);
                result.putExtra("giaThue", gia);
                result.putExtra("tinhTrang", tinhTrangMoi);
                result.putExtra("tenNguoiThue", nguoi);
                result.putExtra("soDienThoai", sdt);
                result.putExtra("position", position);

                setResult(RESULT_OK, result);
                finish();

            }catch (Exception e){
                Toast.makeText(this,"Giá phải là số",Toast.LENGTH_SHORT).show();
            }

        });
    }
}