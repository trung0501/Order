package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinhvien.orderdrinkapp.DTO.MonDTO;
import com.sinhvien.orderdrinkapp.R;

import java.util.List;

public class AdapterDisplayMenu extends BaseAdapter {

    private Context context;
    private int layout;
    private List<MonDTO> monDTOList;
    private ViewHolder viewHolder;

    // Constructor
    public AdapterDisplayMenu(Context context, int layout, List<MonDTO> monDTOList) {
        this.context = context;
        this.layout = layout;
        this.monDTOList = monDTOList;
    }

    @Override
    public int getCount() {
        return monDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return monDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monDTOList.get(position).getMaMon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolder.img_custommenu_HinhMon = view.findViewById(R.id.img_custommenu_HinhMon);
            viewHolder.txt_custommenu_TenMon = view.findViewById(R.id.txt_custommenu_TenMon);
            viewHolder.txt_custommenu_TinhTrang = view.findViewById(R.id.txt_custommenu_TinhTrang);
            viewHolder.txt_custommenu_GiaTien = view.findViewById(R.id.txt_custommenu_GiaTien);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        MonDTO monDTO = monDTOList.get(position);
        viewHolder.txt_custommenu_TenMon.setText(monDTO.getTenMon());
        viewHolder.txt_custommenu_GiaTien.setText(monDTO.getGiaTien() + " VNĐ");
        viewHolder.txt_custommenu_TinhTrang.setText(monDTO.getTinhTrang().equals("true") ? "Còn món" : "Hết món");

        // Lấy hình ảnh từ chuỗi Base64
        String base64Image = monDTO.getHinhAnh();
        if (base64Image != null && !base64Image.isEmpty()) {
            // Kiểm tra và giải mã chuỗi Base64
            base64Image = base64Image.replaceAll("[^A-Za-z0-9+/=]", ""); // Loại bỏ ký tự không hợp lệ
            if (base64Image.length() % 4 == 0) {
                try {
                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    viewHolder.img_custommenu_HinhMon.setImageBitmap(bitmap);
                } catch (IllegalArgumentException e) {
                    viewHolder.img_custommenu_HinhMon.setImageResource(R.drawable.cafe_americano); // Hình ảnh mặc định
                    e.printStackTrace();
                }
            } else {
                viewHolder.img_custommenu_HinhMon.setImageResource(R.drawable.cafe_americano); // Hình ảnh mặc định
            }
        } else {
            viewHolder.img_custommenu_HinhMon.setImageResource(R.drawable.cafe_americano); // Hình ảnh mặc định
        }

        return view;
    }

    // Tạo ViewHolder lưu trữ component
    public static class ViewHolder {
        ImageView img_custommenu_HinhMon;
        TextView txt_custommenu_TenMon, txt_custommenu_GiaTien, txt_custommenu_TinhTrang;
    }
}
