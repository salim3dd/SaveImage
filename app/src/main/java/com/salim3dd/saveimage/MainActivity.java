package com.salim3dd.saveimage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

        Button btn_save_MediaStore = (Button) findViewById(R.id.btn_save_MediaStore);
        Button btn_Save_Folder = (Button) findViewById(R.id.btn_Save_Folder);

        btn_save_MediaStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                permission_Check();

            }
        });
        btn_Save_Folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission_Check();

            }
        });

    }
    public void permission_Check() {
        //////////////////
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                return;
            }

        }
        SaveFile();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SaveFile();
        } else {
            permission_Check();
        }
    }

    private void SaveFile() {

        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

       // MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", "info");

        //Toast.makeText(MainActivity.this, "تم حفظ الصورة في الاستوديو", Toast.LENGTH_SHORT).show();
        savePhoto(bitmap);
    }






    private void savePhoto(Bitmap bitmap) {

        try {
            ByteArrayOutputStream  byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArray);

            Calendar calendar = Calendar.getInstance();
            String fileName = "img_" + String.valueOf(calendar.getTimeInMillis()) + ".png";

            File StorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            new File(StorageDir + "/FolderName" ).mkdirs();
            File outputfile = new File(StorageDir + "/FolderName/", fileName);

            FileOutputStream fos = new FileOutputStream(outputfile);
            fos.write(byteArray.toByteArray());
            fos.close();

            Toast.makeText(MainActivity.this, "تم حفظ الصورة في الاستوديو", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

}
