package com.aninterface.interactive.scolaire_activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NomDesParticipantActivity extends AppCompatActivity {
    private EditText nompart;
    String userNpa;
     private ListView listnom;
    private ArrayList<String> arraylistnom;
    ArrayAdapter adapter;
    String nom;
    String[] nomtab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nom_des_participant);
        nompart = findViewById(R.id.edittextCodNompartAct);
        this.arraylistnom = new ArrayList<String>();

        this.listnom = (ListView) findViewById(R.id.textInfo);

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,this.arraylistnom);
    }

    private class nompartact extends AsyncTask
    {
        private Context c;
        private AlertDialog ad;
        private ListView listnom;

        public nompartact (Context c)
        {
            this.c = c;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String cible = "http://192.168.2.20/APP_Mob/Labo2_BD2/requete_nom_participants_a_lactivite.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs,"utf-8"));

                String msg = URLEncoder.encode("codeact","utf-8")+"="+
                        URLEncoder.encode((String)objects[0],"utf8");

                bufw.write(msg);
                bufw.flush();
                bufw.close();
                outs.close();

                InputStream ins = con.getInputStream();
                BufferedReader bufr = new BufferedReader(new InputStreamReader(ins,"iso-8859-1"));
                String line;
                StringBuffer sbuff = new StringBuffer();

                while ((line = bufr.readLine()) !=null)
                {
                    sbuff.append(line + "\n");
                }
                return  sbuff.toString();

            }
            catch (Exception ex)
            {
                return ex.getMessage();
            }
        }

        @Override
        protected void onPreExecute() {
            this.ad = new AlertDialog.Builder(this.c).create();
            this.ad.setTitle("Nom des participant à l'actvité "+userNpa);
        }

        @Override
        protected void onPostExecute(Object o) {
           //this.ad.setMessage((String)o);
           // this.ad.show();
             nom = ((String)o);
             //listeDesNom();


        }

    }

    public void nomParticipant(View view)
    {
         userNpa = this.nompart.getText().toString();

        nompartact npa = new nompartact(this);
        npa.execute(userNpa);

        Toast.makeText(this,"nom des participant",Toast.LENGTH_LONG).show();
    }
    public void listeDesNom(View view)
    {
        nomtab = nom.split("/");

        for (int i=0; i<=nomtab.length-2; i++)
        {
            arraylistnom.add(nomtab[i]);
        }

        this.listnom = (ListView) findViewById(R.id.textInfo);
        this.listnom.setAdapter(adapter);
    }
}
