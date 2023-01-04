package com.sirchardash.piria;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.sirchardash.piria.model.Museum;

public class MuseumCard extends ConstraintLayout {

    private final TextView name;
    private final TextView type;
    private final TextView address;

    public MuseumCard(Context context, AttributeSet attrs) {
        super(context, null);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.museum_card, this, true);

        ViewGroup children = (ViewGroup) getChildAt(0);
        name = (TextView) children.getChildAt(0);
        type = (TextView) children.getChildAt(1);
        address = (TextView) children.getChildAt(2);
    }

    public void setMuseum(Museum museum) {
        name.setText(museum.getName());
        type.setText(museum.getMuseumType());
        address.setText(String.format("%s, %s", museum.getCity(), museum.getCountry()));
    }

}