package com.sirchardash.piria;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.sirchardash.piria.model.Museum;
import com.sirchardash.piria.model.Tour;

public class TourCard extends ConstraintLayout {

    private final TextView name;
    private final TextView description;

    public TourCard(Context context, AttributeSet attrs) {
        super(context, null);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tour_card, this, true);

        ViewGroup children = (ViewGroup) getChildAt(0);
        name = (TextView) children.getChildAt(0);
        description = (TextView) children.getChildAt(1);
    }

    public void setData(Tour tour) {
        name.setText(tour.getTitle());
        description.setText(tour.getDescription());
    }

}