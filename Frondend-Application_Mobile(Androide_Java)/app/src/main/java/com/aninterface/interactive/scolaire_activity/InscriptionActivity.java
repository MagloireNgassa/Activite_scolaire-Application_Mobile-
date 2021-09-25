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

public class InscriptionActivity extends AppCompatActivity {
    private EditText insNom;
    private EditText insPrenom;
    private EditText insTelephone;
    private EditText insLogin;
    private EditText insPw;
    private TextView textInfo;
    String userL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        insNom = findViewById(R.id.edittextnomins);
        insPrenom = findViewById(R.id.edittextprenomins);
        insTelephone = findViewById(R.id.edittextphoneins);
        insLogin = findViewById(R.id.edittextloginins);
        insPw = findViewById(R.id.edittextpasswordins);
        textInfo = findViewById(R.id.textInfo);
    }

    private class inscription extends AsyncTask
    {
        private Context c;
        private AlertDialog ad;
        public inscription (Context c)
        {
            this.c = c;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String cible = "http://192.168.1.105/APP_Mob/Labo2_BD2/requetes_inscription.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs,"utf-8"));

                String msg = URLEncoder.encode("nom","utf-8")+"="+
                        URLEncoder.encode((String)objects[0],"utf8")+"&"+
                        URLEncoder.encode("prenom","utf-8")+"="+
                        URLEncoder.encode((String)objects[1],"utf8")+"&"+
                        URLEncoder.encode("telephone","utf-8")+"="+
                        URLEncoder.encode((String)objects[2],"utf8")+"&"+
                        URLEncoder.encode("login","utf-8")+"="+
                        URLEncoder.encode((String)objects[3],"utf8")+"&"+
                        URLEncoder.encode("password","utf-8")+"="+
                        URLEncoder.encode((String)objects[4],"utf8");

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
            this.ad.setTitle("Statut de l'inscription de "+ userL);
        }

        @Override
        protected void onPostExecute(Object o) {
            this.ad.setMessage((String)o);
            this.ad.show();
        }
    }public void connectinscription(View view)
    {
        String userN = this.insNom.getText().toString();
        String userP = this.insPrenom.getText().toString();
        String userT = this.insTelephone.getText().toString();
         userL = this.insLogin.getText().toString();
        String userPw = this.insPw.getText().toString();

        inscription ins = new inscription(this);
        ins.execute(userN,userP,userT,userL,userPw);

    }
}
