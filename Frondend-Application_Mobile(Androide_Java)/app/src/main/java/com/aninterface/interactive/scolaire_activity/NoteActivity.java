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

public class NoteActivity extends AppCompatActivity {
    private EditText codAttNote;
    private EditText lgAttNote;
    private EditText noteAttAct;
    String userCodNt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        codAttNote = findViewById(R.id.edittextCodNoteAttAct);
        lgAttNote = findViewById(R.id.edittextlgNoteAttAct);
        noteAttAct = findViewById(R.id.edittextNoteAttAct);
    }

    private class noteact extends AsyncTask
    {
        private Context c;
        private AlertDialog ad;
        public noteact (Context c)
        {
            this.c = c;
        }
        @Override
        protected Object doInBackground(Object[] objects) {
            String cible = "http://192.168.2.20/APP_Mob/Labo2_BD2/donne_une_note.php";
            try {
                URL url = new URL(cible);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");

                OutputStream outs = con.getOutputStream();
                BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(outs,"utf-8"));

                String msg = URLEncoder.encode("noteact","utf-8")+"="+
                        URLEncoder.encode(String.valueOf((Double) objects[0]),"utf8")+"&"+
                        URLEncoder.encode("login","utf-8")+"="+
                        URLEncoder.encode((String)objects[1],"utf8")+"&"+
                        URLEncoder.encode("codeact","utf-8")+"="+
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
            this.ad.setTitle("Note attribué l'activité "+ userCodNt);
        }

        @Override
        protected void onPostExecute(Object o) {
            this.ad.setMessage((String)o);
            this.ad.show();
        }
    }

    public void noteActivite(View view)
    {
        Double userNAt = Double.parseDouble(this.noteAttAct.getText().toString());
        String userlgAt = this.lgAttNote.getText().toString();
        userCodNt = this.codAttNote.getText().toString();

        noteact nta = new noteact(this);
        nta.execute(userNAt,userlgAt,userCodNt);
    }
}
