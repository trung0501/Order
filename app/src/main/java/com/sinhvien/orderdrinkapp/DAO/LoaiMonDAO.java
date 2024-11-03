package com.sinhvien.orderdrinkapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sinhvien.orderdrinkapp.DTO.LoaiMonDTO;
import com.sinhvien.orderdrinkapp.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonDAO {

    SQLiteDatabase database;

    public LoaiMonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiMon(LoaiMonDTO loaiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI, loaiMonDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIMON_HINHANH, loaiMonDTO.getHinhAnh()); // lưu đường dẫn ảnh dưới dạng chuỗi
        long ktra = database.insert(CreateDatabase.TBL_LOAIMON, null, contentValues);

        return ktra != 0;
    }

    public boolean XoaLoaiMon(int maloai){
        long ktra = database.delete(CreateDatabase.TBL_LOAIMON, CreateDatabase.TBL_LOAIMON_MALOAI + " = " + maloai, null);
        return ktra != 0;
    }

    public boolean SuaLoaiMon(LoaiMonDTO loaiMonDTO, int maloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAIMON_TENLOAI, loaiMonDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAIMON_HINHANH, loaiMonDTO.getHinhAnh()); // lưu đường dẫn ảnh dưới dạng chuỗi
        long ktra = database.update(CreateDatabase.TBL_LOAIMON, contentValues, CreateDatabase.TBL_LOAIMON_MALOAI + " = " + maloai, null);
        return ktra != 0;
    }

    public List<LoaiMonDTO> LayDSLoaiMon(){
        List<LoaiMonDTO> loaiMonDTOList = new ArrayList<>();
        String query = "SELECT * FROM " + CreateDatabase.TBL_LOAIMON;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            LoaiMonDTO loaiMonDTO = new LoaiMonDTO();
            loaiMonDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI)));
            loaiMonDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI)));
            loaiMonDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_HINHANH))); // thay getBlob thành getString
            loaiMonDTOList.add(loaiMonDTO);

            cursor.moveToNext();
        }
        cursor.close();
        return loaiMonDTOList;
    }

    public LoaiMonDTO LayLoaiMonTheoMa(int maloai){
        LoaiMonDTO loaiMonDTO = new LoaiMonDTO();
        String query = "SELECT * FROM " + CreateDatabase.TBL_LOAIMON + " WHERE " + CreateDatabase.TBL_LOAIMON_MALOAI + " = " + maloai;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){
            loaiMonDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_MALOAI)));
            loaiMonDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_TENLOAI)));
            loaiMonDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAIMON_HINHANH))); // thay getBlob thành getString
        }

        cursor.close();
        return loaiMonDTO;
    }
}
