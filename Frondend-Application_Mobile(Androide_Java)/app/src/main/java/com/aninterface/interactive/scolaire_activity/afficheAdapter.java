package com.aninterface.interactive.scolaire_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class afficheAdapter extends ArrayAdapter<listeAct> {
    private Context context;
    private ArrayList<listeAct> ar;
    private int resource;

    public afficheAdapter(Context context, int resource,  List<listeAct> objects)
    {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        ar = new ArrayList<listeAct>();
        ar = (ArrayList<listeAct>)objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        listeAct ctemp = this.ar.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        convertView = layoutInflater.inflate(this.resource,parent,false);

        TextView CodeAct = (TextView)convertView.findViewById(R.id.codeActAff);
        TextView NomAct = (TextView)convertView.findViewById(R.id.nomActAff);
        TextView DesAct = (TextView)convertView.findViewById(R.id.desActAff);

        CodeAct.setText("CODE ACTIVITÉ : " +ctemp.getactcode());
        NomAct.setText("NOM ACTIVITÉ : "+ctemp.getactnom());
        DesAct.setText("DESCRIPTION ACTIVITÉ : "+ctemp.getactdes());

        return convertView;
    }

}
