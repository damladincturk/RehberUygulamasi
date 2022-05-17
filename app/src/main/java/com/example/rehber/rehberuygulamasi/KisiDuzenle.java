package com.example.rehber.rehberuygulamasi;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class KisiDuzenle extends AppCompatActivity {

    TextView ad;
    TextView telefon;
    TextView eposta;
    Button btnguncelle;
    Button butonmuzik;
    String id;
    TextView zila;
    private Veritabani v1;

    private static final int IMAGE_PICK = 1;
    private ImageView imgresimekle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_duzenle);

        ImageView img=(ImageView)findViewById(R.id.imgviewresimekleduzen);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Bir Fotoðraf Seçin"), IMAGE_PICK);
            }
        });

        v1=new Veritabani(this);

        ad=(TextView)findViewById(R.id.txtisimduzen);
        telefon=(TextView)findViewById(R.id.txttelefonduzen);
        eposta=(TextView)findViewById(R.id.txtepostaduzen);
        zila=(TextView)findViewById(R.id.zil1);


        butonmuzik=(Button)findViewById(R.id.muziksecduzenle);
        ad.setText(getIntent().getExtras().getString("gonderisim"));
        telefon.setText(getIntent().getExtras().getString("gondertelefon"));
        eposta.setText(getIntent().getExtras().getString("gondereposta"));
        zila.setText(getIntent().getExtras().getString("gonderid"));
        butonmuzik.setText(getIntent().getExtras().getString("gondermuzik"));


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



                btnguncelle = (Button) findViewById(R.id.btnduzen);
                btnguncelle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        try {
                            Guncelle(ad.getText().toString(), telefon.getText().toString(), eposta.getText().toString(), zila.getText().toString(),butonmuzik.getText().toString());
                            Toast.makeText(KisiDuzenle.this, "Kayıt Güncelledi", Toast.LENGTH_LONG).show();
                            ad.setText(" ");
                            telefon.setText(" ");
                            eposta.setText(" ");
                            butonmuzik.setText("Zil Sesi Seçiniz");
                        } finally {
                            v1.close();
                            Thread timer = new Thread() {
                                public void run() {
                                    try {

                                        sleep(5000);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            };
                            timer.start();
                            startActivity(new Intent(KisiDuzenle.this, RehberGoruntule.class));

                        }
                    }
                });


            }

            private void Guncelle(String ad, String telefon, String eposta, String id,String muzik) {

                ContentValues cv1 = new ContentValues();
                cv1.put("ad", ad);
                cv1.put("telefon", telefon);
                cv1.put("eposta", eposta);
                cv1.put("muzik",muzik);
                SQLiteDatabase db = v1.getWritableDatabase();
                db.update("bilgiler", cv1, "id" + "=?", new String[]{id});
                db.close();


            }


            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == Activity.RESULT_OK) {
                    switch (requestCode) {
                        case IMAGE_PICK:
                            this.imageFromGallery(resultCode, data);
                    }

                }
                //Müzik seçilince yazma işlemi
                if (resultCode==RESULT_OK)
                {

                    Uri DefaultRingtoneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    Cursor c = getContentResolver().query(DefaultRingtoneUri, null, null, null, null);
                    c.moveToFirst();
                    String BellName = c.getString(c.getColumnIndex(MediaStore.MediaColumns.TITLE));
                    butonmuzik.setText(BellName);
                }
            }

            private void imageFromGallery(int resultCode, Intent data) {
                imgresimekle = (ImageView) findViewById(R.id.imgviewresimekle);
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                this.imgresimekle.setImageBitmap(BitmapFactory.decodeFile(filePath));
                cursor.close();
            }


        }
