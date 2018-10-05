package com.fear.passcrypter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Credentials> {

    public CustomAdapter(@NonNull Context context, ArrayList<Credentials> us) {
        super(context, R.layout.listitem, us);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater fearsInflator = LayoutInflater.from(getContext());
        View customView = fearsInflator.inflate(R.layout.listitem, parent, false);

        Credentials credential = getItem(position);
        TextView user = (TextView) customView.findViewById(R.id.user);
        TextView pass = (TextView) customView.findViewById(R.id.pass);
        TextView title = (TextView) customView.findViewById(R.id.title);

        user.setText(credential.get_username());
        pass.setText(credential.get_pass());
        title.setText(credential.get_title());
        return customView;

    }
}