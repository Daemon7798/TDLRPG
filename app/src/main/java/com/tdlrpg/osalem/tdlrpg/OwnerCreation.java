package com.tdlrpg.osalem.tdlrpg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.spec.EncodedKeySpec;

public class OwnerCreation extends AppCompatActivity {
    File owner = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/OwnerData.txt");
    File profile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/profilepicture.png");
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_creation);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void addImage(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showInfo(View v)
    {

    }

    public void createUser(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            return;
        }

        EditText e = (EditText) findViewById(R.id.editText);
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ImageView img = (ImageView) findViewById(R.id.imageView);
        Toast.makeText(this, s.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        Member user = new Member(e.getText().toString(), s.getSelectedItem().toString());
        try {
            owner.createNewFile();
            profile.createNewFile();
            FileWriter f = new FileWriter(owner);
            f.write(Member.toString(user));
            Bitmap bmp = ((BitmapDrawable) img.getDrawable()).getBitmap();
            FileOutputStream out = new FileOutputStream(profile);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            f.close();

        } catch (IOException io) {
            Toast.makeText(this, "Oops", Toast.LENGTH_SHORT).show();
            io.printStackTrace();
        }

        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish();
        }

    @Override // requests permissions (Read/Write and Record)
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                createUser((FloatingActionButton) findViewById(R.id.fab));
            }
        }
    }
}
