package com.sinhvien.orderdrinkapp.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sinhvien.orderdrinkapp.DAO.LoaiMonDAO;
import com.sinhvien.orderdrinkapp.DTO.LoaiMonDTO;
import com.sinhvien.orderdrinkapp.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Button BTN_addcategory_TaoLoai;
    ImageView IMG_addcategory_back, IMG_addcategory_ThemHinh;
    TextView TXT_addcategory_title;
    TextInputLayout TXTL_addcategory_TenLoai;
    LoaiMonDAO loaiMonDAO;
    int maloai = 0;
    Uri imageUri; // Biến để lưu URI của hình ảnh

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData(); // Lưu URI của hình ảnh
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addcategory_ThemHinh.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcategory_layout);

        loaiMonDAO = new LoaiMonDAO(this);

        //region Lấy đối tượng view
        BTN_addcategory_TaoLoai = findViewById(R.id.btn_addcategory_TaoLoai);
        TXTL_addcategory_TenLoai = findViewById(R.id.txtl_addcategory_TenLoai);
        IMG_addcategory_back = findViewById(R.id.img_addcategory_back);
        IMG_addcategory_ThemHinh = findViewById(R.id.img_addcategory_ThemHinh);
        TXT_addcategory_title = findViewById(R.id.txt_addcategory_title);
        //endregion

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        maloai = getIntent().getIntExtra("maloai", 0);
        if (maloai != 0) {
            TXT_addcategory_title.setText(getResources().getString(R.string.editcategory));
            LoaiMonDTO loaiMonDTO = loaiMonDAO.LayLoaiMonTheoMa(maloai);

            // Hiển thị lại thông tin từ csdl
            TXTL_addcategory_TenLoai.getEditText().setText(loaiMonDTO.getTenLoai());
            imageUri = Uri.parse(loaiMonDTO.getHinhAnh()); // Lấy đường dẫn hình ảnh từ CSDL
            IMG_addcategory_ThemHinh.setImageURI(imageUri); // Hiển thị hình ảnh
            BTN_addcategory_TaoLoai.setText("Sửa loại");
        }
        //endregion

        IMG_addcategory_back.setOnClickListener(this);
        IMG_addcategory_ThemHinh.setOnClickListener(this);
        BTN_addcategory_TaoLoai.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        switch (id) {
            case R.id.img_addcategory_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;

            case R.id.img_addcategory_ThemHinh:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG, getResources().getString(R.string.choseimg)));
                break;

            case R.id.btn_addcategory_TaoLoai:
                if (!validateImage() || !validateName()) {
                    return;
                }

                String sTenLoai = TXTL_addcategory_TenLoai.getEditText().getText().toString();
                LoaiMonDTO loaiMonDTO = new LoaiMonDTO();
                loaiMonDTO.setTenLoai(sTenLoai);
                loaiMonDTO.setHinhAnh(imageUri.toString()); // Lưu đường dẫn hình ảnh dưới dạng String

                if (maloai != 0) {
                    ktra = loaiMonDAO.SuaLoaiMon(loaiMonDTO, maloai);
                    chucnang = "sualoai";
                } else {
                    ktra = loaiMonDAO.ThemLoaiMon(loaiMonDTO);
                    chucnang = "themloai";
                }

                Intent intent = new Intent();
                intent.putExtra("ktra", ktra);
                intent.putExtra("chucnang", chucnang);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private boolean validateImage() {
        if (imageUri == null) {
            Toast.makeText(getApplicationContext(), "Xin chọn hình ảnh", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateName() {
        String val = TXTL_addcategory_TenLoai.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            TXTL_addcategory_TenLoai.setError(getResources().getString(R.string.not_empty));
            return false;
        } else {
            TXTL_addcategory_TenLoai.setError(null);
            TXTL_addcategory_TenLoai.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}
