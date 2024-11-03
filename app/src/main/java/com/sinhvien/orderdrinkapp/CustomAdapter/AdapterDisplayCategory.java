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

import com.sinhvien.orderdrinkapp.DTO.LoaiMonDTO;
import com.sinhvien.orderdrinkapp.R;

import java.util.List;

public class AdapterDisplayCategory extends BaseAdapter {

    private Context context;
    private int layout;
    private List<LoaiMonDTO> loaiMonDTOList;
    private ViewHolder viewHolder;

    // Constructor
    public AdapterDisplayCategory(Context context, int layout, List<LoaiMonDTO> loaiMonDTOList) {
        this.context = context;
        this.layout = layout;
        this.loaiMonDTOList = loaiMonDTOList;
    }

    @Override
    public int getCount() {
        return loaiMonDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiMonDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiMonDTOList.get(position).getMaLoai();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        // Nếu lần đầu gọi view
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            // Truyền component vào viewHolder để không gọi findViewById ở những lần hiển thị khác
            viewHolder.img_customcategory_HinhLoai = view.findViewById(R.id.img_customcategory_HinhLoai);
            viewHolder.txt_customcategory_TenLoai = view.findViewById(R.id.txt_customcategory_TenLoai);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        LoaiMonDTO loaiMonDTO = loaiMonDTOList.get(position);
        viewHolder.txt_customcategory_TenLoai.setText(loaiMonDTO.getTenLoai());

        // Lấy hình ảnh từ chuỗi Base64 và giải mã thành Bitmap
        String base64Image = loaiMonDTO.getHinhAnh(); // Lấy hình ảnh dưới dạng String
        if (base64Image != null && !base64Image.isEmpty()) {
            // Kiểm tra định dạng Base64
            base64Image = base64Image.replaceAll("[^A-Za-z0-9+/=]", ""); // Loại bỏ ký tự không hợp lệ
            if (base64Image.length() % 4 == 0) {
                try {
                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    viewHolder.img_customcategory_HinhLoai.setImageBitmap(bitmap);
                } catch (IllegalArgumentException e) {
                    // Xử lý khi có lỗi giải mã Base64
                    viewHolder.img_customcategory_HinhLoai.setImageResource(R.drawable.cafe_americano); // Hình ảnh mặc định
                    e.printStackTrace();
                }
            } else {
                // Nếu chuỗi không hợp lệ, sử dụng hình ảnh mặc định
                viewHolder.img_customcategory_HinhLoai.setImageResource(R.drawable.cafe_americano);
            }
        } else {
            viewHolder.img_customcategory_HinhLoai.setImageResource(R.drawable.cafe_americano); // Hình ảnh mặc định
        }

        return view;
    }

    // Tạo ViewHolder lưu trữ component
    public static class ViewHolder {
        TextView txt_customcategory_TenLoai;
        ImageView img_customcategory_HinhLoai;
    }
}
