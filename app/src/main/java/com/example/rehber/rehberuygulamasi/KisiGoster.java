package com.example.rehber.rehberuygulamasi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class KisiGoster extends AppCompatActivity {


    private Veritabani v3;
    ImageView ımageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_goster);


        final TextView gelenad = (TextView) findViewById(R.id.Aktarılanİsim);
        gelenad.setText(getIntent().getExtras().getString("aktarılacakad"));

        final TextView gelentelelon = (TextView) findViewById(R.id.AktarılanTelefon);
        gelentelelon.setText(getIntent().getExtras().getString("aktarılacaktelefon"));

        final TextView geleneposta = (TextView) findViewById(R.id.AktarılanEposta);
        geleneposta.setText(getIntent().getExtras().getString("aktarilacakeposta"));

        final TextView a = (TextView) findViewById(R.id.zil);
        a.setText(getIntent().getExtras().getString("aktarid"));

        final TextView gostermuzik = (TextView) findViewById(R.id.AktarılanMuzik);
        gostermuzik.setText(getIntent().getExtras().getString("aktarilacakmuzik"));

        ımageView = (ImageView) findViewById(R.id.imggostermealanı);


        ImageButton btnaraa = (ImageButton) findViewById(R.id.btnara);
        btnaraa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String numara=gelentelelon.getText().toString();
                Intent ıntent=new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",numara,null));
                startActivity(ıntent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.gostermenu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();


        if (id==R.id.menuduzenle)
        {
            final  TextView gelenad=(TextView)findViewById(R.id.Aktarılanİsim);
            final TextView gelentelelon=(TextView)findViewById(R.id.AktarılanTelefon);
            final TextView geleneposta=(TextView)findViewById(R.id.AktarılanEposta);
            final TextView gelenmuzik=(TextView)findViewById(R.id.AktarılanMuzik);
            final TextView a=(TextView)findViewById(R.id.zil);

            Intent i=new Intent(KisiGoster.this,KisiDuzenle.class);
            i.putExtra("gonderisim",gelenad.getText());
            i.putExtra("gondertelefon",gelentelelon.getText());
            i.putExtra("gondereposta",geleneposta.getText());
            i.putExtra("gonderid",a.getText());
            i.putExtra("gondermuzik",gelenmuzik.getText());
            startActivity(i);


        }
        else if(id==R.id.menusil)
        {

            final TextView a=(TextView)findViewById(R.id.zil);

            sil(a.getText().toString());
        }


        return super.onOptionsItemSelected(item);
    }

    private void sil(String a) {
        try{
            v3=new Veritabani(this);
            SQLiteDatabase db=v3.getWritableDatabase();
            db.delete("bilgiler","id"+"=?",new String[]{a});

            Toast.makeText(this,"Kişi Silindi",Toast.LENGTH_LONG).show();

        }finally {
            v3.close();
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
             startActivity(new Intent(KisiGoster.this,RehberGoruntule.class));
        }
    }


}
