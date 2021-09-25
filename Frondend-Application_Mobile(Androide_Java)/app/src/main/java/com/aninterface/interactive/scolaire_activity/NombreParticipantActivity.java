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

public class NombreParticipantActivity extends AppCompatActivity {
    private EditText nbrepart;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre_participant);

        nbrepart = findViewById(R.id.edittextCodNbrepartAct);
    }
    private class nbrePartAct extends AsyncTask
    {
        private Context c;
        private AlertDialog ad;
        public nbrePartAct (Context c)
        {
            this.c = c;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            String cible = "http://192.168.2.20/APP_Mob/Labo2_BD2/requete_nombre_Participant_a_lactivite.php";
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
            this.ad.setTitle("Nombre de participant à l'activité "+ user +" est:");
        }

        @Override
        protected void onPostExecute(Object o) {
            this.ad.setMessage((String)o);
            this.ad.show();
        }
    }


    public void nbreParticipant(View view)
    {

         user = this.nbrepart.getText().toString();

        nbrePartAct nta = new nbrePartAct(this);
        nta.execute(user);
    }
}
