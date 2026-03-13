package com.roomrentalproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.roomrentalproject.R;
import com.roomrentalproject.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder>{

    private Context context;
    private List<Room> list;
    private RoomItemListener listener;

    public RoomAdapter(Context context){
        this.context = context;
        list = new ArrayList<>();
    }

    public void setClickListener(RoomItemListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_room,parent,false);

        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {

        Room room = list.get(position);

        holder.txtTenPhong.setText("Tên phòng: " + room.getTenPhong());
        holder.txtGia.setText("Giá thuê: " + room.getGiaThue());
        holder.txtTinhTrang.setText("Tình trạng: " + room.getTinhTrang());

        if(room.getTinhTrang().equals("Còn trống")){
            holder.txtTinhTrang.setTextColor(Color.GREEN);
        }else{
            holder.txtTinhTrang.setTextColor(Color.RED);
        }

        holder.itemView.setOnClickListener(v -> {
            if(listener != null)
                listener.onItemClick(v,holder.getAdapterPosition());
        });

        holder.btDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(context)
                    .setTitle("Xóa phòng")
                    .setMessage("Bạn chắc chắn muốn xóa?")
                    .setPositiveButton("Yes",(d,w)->{

                        int pos = holder.getAdapterPosition();
                        list.remove(pos);
                        notifyDataSetChanged();

                    })
                    .setNegativeButton("No",null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Room room){
        list.add(room);
        notifyDataSetChanged();
    }

    public void update(int position, Room room){
        list.set(position,room);
        notifyDataSetChanged();
    }

    public Room getItem(int position){
        return list.get(position);
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder{

        TextView txtTenPhong, txtGia, txtTinhTrang;
        Button btDelete;

        public RoomViewHolder(View view) {
            super(view);

            txtTenPhong = view.findViewById(R.id.txtTenPhong);
            txtGia = view.findViewById(R.id.txtGia);
            txtTinhTrang = view.findViewById(R.id.txtTinhTrang);
            btDelete = view.findViewById(R.id.btDelete);
        }
    }

    public interface RoomItemListener{
        void onItemClick(View view, int position);
    }
}