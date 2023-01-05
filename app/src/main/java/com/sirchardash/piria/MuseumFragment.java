package com.sirchardash.piria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sirchardash.piria.databinding.FragmentMuseumBinding;
import com.sirchardash.piria.model.Museum;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.repository.TourRepository;

public class MuseumFragment extends Fragment {

    private final Museum museum;
    private TourRepository tourRepository;

    private FragmentMuseumBinding binding;

    public MuseumFragment(Museum museum, TourRepository tourRepository) {
        this.museum = museum;
        this.tourRepository = tourRepository;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMuseumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        binding.museumNameLabel.setText(museum.getName());
        binding.museumCategoryLabel.setText(museum.getMuseumType());
        // todo jooooj
        binding.mapView.getMapAsync(view1 -> {
            view1.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
        });
        binding.museumAddressLabel.setText(museum.getAddress());
        binding.museumCityCountryLabel.setText(String.format("%s, %s", museum.getCity(), museum.getCountry()));
        binding.museumContactLabel.setText(museum.getPhoneNumber());

        tourRepository.findUpcomingByMuseumId(museum.getId()).enqueue(new SimpleCallback<>(
                response -> {
                    if (response.isSuccessful()) {
                        System.out.println(response.body());
                        binding.toursLayout.removeAllViews();
                        if (response.body().getTours().isEmpty()) {
                            TextView textView = new TextView(getContext(), null);
                            textView.setText("Guess not.");
                            binding.toursLayout.addView(textView);
                            System.out.println("happened");
                        } else {
                            System.out.println("happened else");
                            response.body().getTours().stream()
                                    .map(tour -> {
                                        TourCard tourCard = new TourCard(getContext(), null);
                                        tourCard.setData(tour);

                                        return tourCard;
                                    }).forEach(tour -> binding.toursLayout.addView(tour));
                        }
                    } else {
                        binding.toursLayout.removeAllViews();
                        TextView textView = new TextView(getContext(), null);
                        textView.setText("Certified ohno moment.");
                        binding.toursLayout.addView(textView);
                    }
                },
                error -> {
                    error.printStackTrace();
                }
        ));
    }

}