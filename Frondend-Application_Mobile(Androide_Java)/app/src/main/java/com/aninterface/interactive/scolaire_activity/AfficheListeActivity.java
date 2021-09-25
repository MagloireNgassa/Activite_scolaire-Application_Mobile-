package com.aninterface.interactive.scolaire_activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AfficheListeActivity extends AppCompatActivity {
    String info;
    String[] tabline;
    String[] tabmot;
    private ListView list;
    private ArrayList<listeAct> Listofitem;
    ArrayAdapter adapter;
    private TextView cd;
    private TextView nm;
    private  TextView de;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affiche_liste);


        Listofitem = new ArrayList<listeAct>();
        list = (ListView) findViewById(R.id.list1);

        adapter = new afficheAdapter(this, R.layout.layout_affiche_liste,Listofitem);

        //appel direct de la classe override Asyntac
        bdafficheactivite bda = new bdafficheactivite(this);
        bda.execute();





    }

    private class bdafficheactivite extends AsyncTask
    {
        private Context c;
        private AlertDialog ad;
        public bdafficheactivite (Context c)
        {
            this.c = c;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String cible = "http://192.168.2.20/APP_Mob/Labo2_BD2/requetes_affiche_activites.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

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
            this.ad.setTitle("Activités et Descriptions ");
        }

        @Override
        protected void onPostExecute(Object o) {
            info = ((String)o);

            tabline = info.split("@");
            for (int i=0; i<=tabline.length-2; i++) {
                tabmot = tabline[i].split("/");
                    listeAct temp = new listeAct();
                    temp.actcode = tabmot[0];
                    temp.actnom = tabmot[1];
                    temp.actdes = tabmot[2];

                Listofitem.add(temp);
            }
            list.setAdapter(adapter);
        }
    }
}
