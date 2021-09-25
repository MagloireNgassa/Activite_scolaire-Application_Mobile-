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

public class MiseAjourActivity extends AppCompatActivity {
    private EditText lgMAJ;
    private EditText nomMAJ;
    private EditText prenomMAJ;
    private EditText phoneMAJ;
    private EditText pwMAJ;
    String userlg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mise_ajour);

        lgMAJ = findViewById(R.id.edittextloginMAJ);
        nomMAJ = findViewById(R.id.edittextnomMAJ);
        prenomMAJ = findViewById(R.id.edittextprenomMAJ);
        phoneMAJ = findViewById(R.id.edittextphoneMAJ);
        pwMAJ = findViewById(R.id.edittextpwMAJ);

    }

    private class miseAjour extends AsyncTask
    {
        private Context c;
        private AlertDialog ad;
        public miseAjour (Context c)
        {
            this.c = c;
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            String cible = "http://192.168.1.105/APP_Mob/Labo2_BD2/requete_modif_compte.php";

            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs,"utf-8"));

                String msg = URLEncoder.encode("login","utf-8")+"="+
                        URLEncoder.encode((String)objects[0],"utf8")+"&"+
                        URLEncoder.encode("nom","utf-8")+"="+
                        URLEncoder.encode((String)objects[1],"utf8")+"&"+
                        URLEncoder.encode("prenom","utf-8")+"="+
                        URLEncoder.encode((String)objects[2],"utf8")+"&"+
                        URLEncoder.encode("telephone","utf-8")+"="+
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
            this.ad.setTitle("Mise à jour du compte "+userlg);
        }

        @Override
        protected void onPostExecute(Object o) {
            this.ad.setMessage((String)o);
            this.ad.show();
        }
    }

    public void miseAjourclick(View view)
    {
         userlg = this.lgMAJ.getText().toString();
        String usernm = this.nomMAJ.getText().toString();
        String userpn = this.prenomMAJ.getText().toString();
        String userph = this.phoneMAJ.getText().toString();
        String userPw = this.pwMAJ.getText().toString();

        miseAjour maj = new miseAjour(this);
        maj.execute(userlg,usernm,userpn,userph,userPw);
    }

}
