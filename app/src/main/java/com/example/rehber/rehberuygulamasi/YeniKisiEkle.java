package com.example.rehber.rehberuygulamasi;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class YeniKisiEkle extends AppCompatActivity {

    private final static int RESULT_LOAD_IMG = 200;
    ImageView imgresimekle;
    TextView ad;
    TextView telefon;
    TextView eposta;
    Button btnkaydet;
    private Veritabani v1;
    Button butonmuzik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_kisi_ekle);




        imgresimekle=(ImageView)findViewById(R.id.imgviewresimekle);
        imgresimekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage();

            }
        });



        butonmuzik=(Button)findViewById(R.id.muziksec);
        butonmuzik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION | RingtoneManager.TYPE_RINGTONE);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                startActivityForResult(intent, 0);
            }
        });






        v1=new Veritabani(this);
        ad = (TextView) findViewById(R.id.txtisim);
        telefon = (TextView) findViewById(R.id.txttelefon);
        eposta = (TextView) findViewById(R.id.txteposta);


        btnkaydet = (Button) findViewById(R.id.btnkaydet);



        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    ekleme(ad.getText().toString(),telefon.getText().toString(),eposta.getText().toString(),butonmuzik.getText().toString());
                    Toast.makeText(YeniKisiEkle.this,"Kayıt edildi",Toast.LENGTH_LONG).show();
                    ad.setText(" ");
                    telefon.setText(" ");
                    eposta.setText(" ");
                    butonmuzik.setText("Zil Sesi Seçiniz");
                }
                finally {
                    v1.close();
                    Thread timer=new Thread(){
                      public void run(){
                          try {

                              sleep(5000);

                          }catch (Exception e)
                          {
                              e.printStackTrace();
                          }
                      }

                    };
                    timer.start();
                    startActivity(new Intent(YeniKisiEkle.this,RehberGoruntule.class));

                }
            }
        });


    }



private void selectImage() {

    final CharSequence[] options = { "Galeriden Resim Seç","Cancel" };

    AlertDialog.Builder builder = new AlertDialog.Builder(YeniKisiEkle.this);
    builder.setItems(options, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int item) {
            if (options[item].equals("Galeriden Resim Seç"))
            {
                Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);

            }
            else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        }
    });
    builder.show();
    }
    private void ekleme(String ad, String telefon, String eposta,String muzik) {

        SQLiteDatabase db=v1.getWritableDatabase();
        ContentValues cv1=new ContentValues();
        cv1.put("ad",ad);
        cv1.put("telefon",telefon);
        cv1.put("eposta",eposta);
        cv1.put("muzik",muzik);
        db.insertOrThrow("bilgiler",null,cv1);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Müzik seçilince yazma işlemi
        if (requestCode==0 && resultCode==RESULT_OK)
        {

            Uri DefaultRingtoneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Cursor c = getContentResolver().query(DefaultRingtoneUri, null, null, null, null);
            c.moveToFirst();
            String BellName = c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE));
            butonmuzik.setText(BellName);

        }

        if (resultCode == RESULT_OK) {
              if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath+"");
                imgresimekle.setImageBitmap(thumbnail);
            }
        }
    }

}




