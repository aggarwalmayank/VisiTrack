package com.appsaga.visitrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HostAdapter extends ArrayAdapter<Host> {

    public HostAdapter(Context context, ArrayList<Host> host) {
        super(context, 0,host);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View hostView = convertView;

        if(hostView == null)
        {
            hostView = LayoutInflater.from(getContext()).inflate(R.layout.host_view,parent,false);
        }

        Host currenthost = getItem(position);

        TextView name = hostView .findViewById(R.id.name);
        TextView email = hostView .findViewById(R.id.email);
        TextView phone = hostView .findViewById(R.id.phone);

        name.setText(currenthost .getName());
        email.setText(currenthost .getEmail());
        phone.setText(currenthost .getPhone());

        return hostView ;
    }
}
