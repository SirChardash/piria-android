package com.sirchardash.piria;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentToursBinding;
import com.sirchardash.piria.model.Tour;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.repository.TourRepository;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ToursFragment extends Fragment implements NavbarDockedFragment {

    @Inject
    TourRepository tourRepository;
    @Inject
    UserService userService;

    private FragmentToursBinding binding;

    private List<Tour> upcomingTours = Collections.emptyList();
    private List<Tour> previousTours = Collections.emptyList();

    @Override
    public void onAttach(@NonNull Context context) {
        ((PiriaApplication) requireContext().getApplicationContext())
                .applicationComponent
                .inject(this);

        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        userService.popLoginScreenIfNeeded((MainActivity) getActivity());
        binding = FragmentToursBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateLayout(binding.bookedToursLayout, upcomingTours);
        populateLayout(binding.previousToursLayout, previousTours);

        tourRepository.listUpcoming().enqueue(new SimpleCallback<>(
                response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        if (binding != null) {
                            populateLayout(
                                    binding.bookedToursLayout,
                                    upcomingTours = response.body().getTours()
                            );
                        }
                    }
                },
                Throwable::printStackTrace
        ));
        tourRepository.listRecent().enqueue(new SimpleCallback<>(
                response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        if (binding != null) {
                            populateLayout(
                                    binding.previousToursLayout,
                                    previousTours = response.body().getTours()
                            );
                        }
                    }
                },
                Throwable::printStackTrace
        ));
    }

    private void populateLayout(LinearLayout layout, List<Tour> tours) {
        layout.removeAllViews();

        if (tours.isEmpty()) {
            TextView textView = new TextView(getContext(), null);
            textView.setText("Guess not.");
            layout.addView(textView);
        } else {
            tours.stream()
                    .map(tour -> {
                        TourCard tourCard = new TourCard(getContext(), null);
                        tourCard.setData(tour);

                        return tourCard;
                    }).forEach(layout::addView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}