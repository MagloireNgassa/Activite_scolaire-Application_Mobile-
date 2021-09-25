package com.aninterface.interactive.scolaire_activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AjoutActivity extends AppCompatActivity {
    private EditText codActAjt;
    private EditText nomActAjt;
    private EditText desActAjt;
    String usercd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        codActAjt = findViewById(R.id.edittextCodActAjt);
        nomActAjt = findViewById(R.id.edittextnomActAjt);
        desActAjt = findViewById(R.id.edittextdesActAjt);
    }

    private class ajoutActivite extends AsyncTask
    {
        private Context c;
        private AlertDialog ad;
        public ajoutActivite (Context c)
        {
            this.c = c;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String cible = "http://192.168.1.105/APP_Mob/Labo2_BD2/requetes_Ajout_activite.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs,"utf-8"));

                String msg = URLEncoder.encode("codeact","utf-8")+"="+
                        URLEncoder.encode((String)objects[0],"utf8")+"&"+
                        URLEncoder.encode("nomact","utf-8")+"="+
                        URLEncoder.encode((String)objects[1],"utf8")+"&"+
                        URLEncoder.encode("descriptionact","utf-8")+"="+
                        URLEncoder.encode((String)objects[2],"utf8");

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
            this.ad.setTitle("Statut de l'ajout de l'activité "+usercd);
        }

        @Override
        protected void onPostExecute(Object o) {
            this.ad.setMessage((String)o);
            this.ad.show();
        }
    }

    public void ajoutActivite(View view)
    {
        usercd = this.codActAjt.getText().toString();
        String userna = this.nomActAjt.getText().toString();
        String userds = this.desActAjt.getText().toString();


        ajoutActivite ajt = new ajoutActivite(this);
        ajt.execute(usercd,userna,userds);
    }
}
