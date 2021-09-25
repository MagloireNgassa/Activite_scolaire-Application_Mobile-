package com.aninterface.interactive.scolaire_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private ListView list;
    private ArrayList<String> listmenu;
    String recap;
    String[] spliter;
    String infomoi;
    Intent b;
    Intent h;
    Intent a;
    Intent c;
    Intent e;
    Intent f;
    Intent g;
    Intent d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.listmenu = new ArrayList<String>();
        this.listmenu.add("Mise à jour du compte");
        this.listmenu.add("Afficher la liste des activités");
        this.listmenu.add("Inscription à une activité");
        this.listmenu.add("Ajouter une activités");
        this.listmenu.add("Donner une note à une activité");
        this.listmenu.add("Nombre de participant à une activité");
        this.listmenu.add("Le nom des participants à une activité");
        this.listmenu.add("La moyenne des notes d'une activité");

         b = new Intent(this,MiseAjourActivity.class);
         h = new Intent(this,AfficheListeActivity.class);
         a = new Intent(this,InscriptionALActivity.class);
         c = new Intent(this,AjoutActivity.class);
         e = new Intent(this,NombreParticipantActivity.class);
         f = new Intent(this,NomDesParticipantActivity.class);
         g = new Intent(this,MoyenneDesNotesActivity.class);
         d = new Intent(this,NoteActivity.class);

        Bundle recmoi = getIntent().getExtras();
        if(recmoi != null)
        {
            infomoi = recmoi.getString("insert1");
            Toast.makeText(this,"Bienvenu "+infomoi,Toast.LENGTH_LONG).show();
        }


        this.list = (ListView) findViewById(R.id.listmenu);
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,this.listmenu);
        this.list.setAdapter(adapter);

        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recap = ((TextView)view).getText().toString();
                spliter = recap.split(" ");
                for(int i =0; i<=spliter.length-1; i++)
                {
                    if (spliter[i].contains("Mise") )
                    {
                        startActivity(b);
                    }
                    if(spliter[i].contains("Afficher"))
                    {
                        startActivity(h);
                    }
                    if(spliter[i].contains("Inscription"))
                    {
                        startActivity(a);
                    }
                    if(spliter[i].contains("Ajouter"))
                    {
                        startActivity(c);
                    }
                    if(spliter[i].contains("Nombre"))
                    {
                        startActivity(e);
                    }
                    if(spliter[i].contains("Le"))
                    {
                        startActivity(f);
                    }
                    if(spliter[i].contains("moyenne"))
                    {
                        startActivity(g);
                    }
                    if(spliter[i].contains("Donner"))
                    {
                        startActivity(d);
                    }
                }


            }
        });
    }
}
