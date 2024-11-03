package com.sinhvien.orderdrinkapp.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sinhvien.orderdrinkapp.DTO.LoaiMonDTO;
import com.sinhvien.orderdrinkapp.R;

import java.util.List;

public class AdapterRecycleViewCategory extends RecyclerView.Adapter<AdapterRecycleViewCategory.ViewHolder> {

    private Context context;
    private int layout;
    private List<LoaiMonDTO> loaiMonDTOList;

    public AdapterRecycleViewCategory(Context context, int layout, List<LoaiMonDTO> loaiMonDTOList) {
        this.context = context;
        this.layout = layout;
        this.loaiMonDTOList = loaiMonDTOList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiMonDTO loaiMonDTO = loaiMonDTOList.get(position);
        holder.txt_customcategory_TenLoai.setText(loaiMonDTO.getTenLoai());

        // Lấy hình ảnh từ chuỗi Base64
        String base64Image = loaiMonDTO.getHinhAnh();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.img_customcategory_HinhLoai.setImageBitmap(bitmap);
        } else {
            // Hình ảnh mặc định nếu không có
            holder.img_customcategory_HinhLoai.setImageResource(R.drawable.cafe_americano); // Thay 'default_image' bằng hình ảnh mặc định của bạn
        }
    }

    @Override
    public int getItemCount() {
        return loaiMonDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_customcategory_TenLoai;
        ImageView img_customcategory_HinhLoai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_customcategory_TenLoai = itemView.findViewById(R.id.txt_customcategory_TenLoai);
            img_customcategory_HinhLoai = itemView.findViewById(R.id.img_customcategory_HinhLoai);
        }
    }
}
