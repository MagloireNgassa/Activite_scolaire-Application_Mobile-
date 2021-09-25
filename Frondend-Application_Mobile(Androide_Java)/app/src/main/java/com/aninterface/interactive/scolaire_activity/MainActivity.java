package com.aninterface.interactive.scolaire_activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {



    private TextView textAlert;
    private EditText lg;
    private EditText pw;
    Intent l;
    String moi;
    String insert0;
    String insert1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l = new Intent(this,MenuActivity.class);
       // l.putExtra("moi",moi);
    textAlert = findViewById(R.id.textviewalert);
    lg = findViewById(R.id.activite1Login);
    pw = findViewById(R.id.activite1Password);



    }

    private class bdconnect extends AsyncTask
    {

        private Context c;
        private AlertDialog ad;
        public bdconnect (Context c)
        {
            this.c = c;
        }

        @Override
        protected void onPreExecute() {
            this.ad = new AlertDialog.Builder(this.c).create();
            this.ad.setTitle("login statut ");
        }

        @Override
        protected void onPostExecute(Object o) {

           //this.ad.setMessage((String)o);
            //this.ad.show();
            moi = ((String)o);
            String[] insert;
            insert = moi.split("/");
            insert0 = insert[0];
            insert1 = insert[1];
            if(insert0.equals("true") )
            {
                l.putExtra("insert1",insert1);
                startActivity(l);
            }
            else
            {

                textAlert.setText("Password ou Login Incorect ");
            }
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String cible = "http://192.168.2.20/APP_Mob/Labo2_BD2/connexion.php";
            try
            {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs,"utf-8"));

                String msg = URLEncoder.encode("login","utf-8")+"="+
                        URLEncoder.encode((String)objects[0],"utf8")+"&"+
                        URLEncoder.encode("password","utf-8")+"="+
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
    }

    public void inscription(View view)
    {
        Intent j = new Intent(this,InscriptionActivity.class);
        startActivity(j);
    }

    public void login (View view)
    {
        String userL = this.lg.getText().toString();
        String userPw = this.pw.getText().toString();

        bdconnect bdc = new bdconnect(this);
        bdc.execute(userL,userPw);

    }
}
