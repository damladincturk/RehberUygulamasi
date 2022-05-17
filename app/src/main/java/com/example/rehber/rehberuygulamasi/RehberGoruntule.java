package com.example.rehber.rehberuygulamasi;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class RehberGoruntule extends AppCompatActivity {

     ListView liste;
     private Veritabani v2;
     ArrayAdapter adaptor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rehber_goruntule);



        TextView yenikisibuton=(TextView)findViewById(R.id.yenikisieklebuton);
        yenikisibuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RehberGoruntule.this,YeniKisiEkle.class));


            }
        });




        liste=(ListView)findViewById(R.id.listRehber);
        v2=new Veritabani(this);
        SQLiteDatabase db = v2.getReadableDatabase();


        String[] sutunlar = new String[] { "id","ad", "telefon", "eposta","muzik"};

        final Cursor c = db.query("bilgiler", sutunlar, null, null,null, null, null);
        final int adSiraNo = c.getColumnIndex("ad");
        int telSiraNo = c.getColumnIndex("telefon");
        int epostaSiraNo = c.getColumnIndex("eposta");
        int muzikSiraNo = c.getColumnIndex("muzik");
        int idno=c.getColumnIndex("id");
        final String diziad[]=new String[c.getCount()];
        final String dizitelefon[]=new String[c.getCount()];
        final String dizieposta[]=new String[c.getCount()];
        final String dizimuzik[]=new String[c.getCount()];
        final String diziid[]=new String[c.getCount()];

        int sayac=0;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            diziad[sayac]=c.getString(adSiraNo);
            dizitelefon[sayac]=c.getString(telSiraNo);
            dizieposta[sayac]=c.getString(epostaSiraNo);
            dizimuzik[sayac]=c.getString(muzikSiraNo);
            diziid[sayac]=c.getString(idno);
            sayac+=1;
        }

        final TextView kisi=(TextView)findViewById(R.id.kisi);
        adaptor=this.tumKayitlar(getApplicationContext());
        liste.setAdapter(adaptor);
        liste.setTextFilterEnabled(true);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                String aktarılacakad=diziad[position];
                String aktarılacaktelefon=dizitelefon[position];
                String aktarilacakeposta=dizieposta[position];
                String aktarilacakmuzik=dizimuzik[position];
                String aktarid=diziid[position];

                Intent ıntent=new Intent(RehberGoruntule.this,KisiGoster.class);
                ıntent.putExtra("aktarılacakad",aktarılacakad);
                ıntent.putExtra("aktarılacaktelefon",aktarılacaktelefon);
                ıntent.putExtra("aktarilacakeposta",aktarilacakeposta);
                ıntent.putExtra("aktarilacakmuzik",aktarilacakmuzik);
                ıntent.putExtra("aktarid",aktarid);
                startActivity(ıntent);



            }
        });

    }



    public ArrayAdapter<String> tumKayitlar(Context context)
    {

        v2=new Veritabani(this);
        String[] sutunlar = new String[] { "ad", "telefon", "eposta","muzik"};
        SQLiteDatabase db = v2.getReadableDatabase();
        Cursor c = db.query("bilgiler", sutunlar, null, null,null, null, null);

        int adSiraNo = c.getColumnIndex("ad");

        String dizi[]=new String[c.getCount()];
        int sayac=0;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            dizi[sayac]=c.getString(adSiraNo);
            sayac+=1;
        }

        ArrayAdapter AA= new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,dizi);
        return AA;
    }

    public void sil(String silad) {



    }





}
