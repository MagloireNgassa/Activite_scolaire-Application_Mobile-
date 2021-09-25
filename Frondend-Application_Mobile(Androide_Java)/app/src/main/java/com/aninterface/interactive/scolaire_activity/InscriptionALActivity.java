package com.aninterface.interactive.scolaire_activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class InscriptionALActivity extends AppCompatActivity {
    private EditText lgInsAct;
    private EditText cdInsAct;
    private TextView textinfoins;
    String userCD;
    String userLI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_a_l);

        lgInsAct = findViewById(R.id.edittextLgActIns);
        cdInsAct = findViewById(R.id.edittextCodeActIns);
        textinfoins = findViewById(R.id.textInfoInsAct);

    }

    private class inscriptionAct extends AsyncTask
    {
        private Context c;
        private AlertDialog ad;
        public inscriptionAct (Context c)
        {
            this.c = c;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String cible = "http://192.168.1.105/APP_Mob/Labo2_BD2/requete_inscription_a_une_activite.php";
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
                        URLEncoder.encode("login","utf-8")+"="+
                        URLEncoder.encode((String)objects[1],"utf8");

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
            this.ad.setTitle("Inscription de "+ userLI +" à L'activité"+userCD);
        }

        @Override
        protected void onPostExecute(Object o) {
            this.ad.setMessage((String)o);
            this.ad.show();
        }
    }

    public void inscriptionActivite(View view)
    {
         userLI = this.lgInsAct.getText().toString();
         userCD = this.cdInsAct.getText().toString();

        inscriptionAct insA = new inscriptionAct(this);
        insA.execute(userCD,userLI);
    }

}
