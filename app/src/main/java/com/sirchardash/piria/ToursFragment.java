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
import com.sirchardash.piria.model.Tours;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.repository.TourRepository;

import javax.inject.Inject;

public class ToursFragment extends Fragment implements NavbarDockedFragment {

    @Inject
    TourRepository tourRepository;
    @Inject
    UserService userService;

    private FragmentToursBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        ((PiriaApplication) requireContext().getApplicationContext()).applicationComponent.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        userService.popLoginScreenIfNeeded((MainActivity) getActivity());
        binding = FragmentToursBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tourRepository.listUpcoming().enqueue(populateLayout(binding.bookedToursLayout));
        tourRepository.listRecent().enqueue(populateLayout(binding.previousToursLayout));
    }

    private SimpleCallback<Tours> populateLayout(LinearLayout layout) {
        return new SimpleCallback<>(
                response -> {
                    if (response.isSuccessful()) {
                        System.out.println(response.body());
                        layout.removeAllViews();
                        if (response.body().getTours().isEmpty()) {
                            TextView textView = new TextView(getContext(), null);
                            textView.setText("Guess not.");
                            layout.addView(textView);
                            System.out.println("happened");
                        } else {
                            System.out.println("happened else");
                            response.body().getTours().stream()
                                    .map(tour -> {
                                        TourCard tourCard = new TourCard(getContext(), null);
                                        tourCard.setData(tour);

                                        return tourCard;
                                    }).forEach(layout::addView);
                        }
                    } else {
                        layout.removeAllViews();
                        TextView textView = new TextView(getContext(), null);
                        textView.setText("Certified ohno moment.");
                        layout.addView(textView);
                    }
                },
                error -> {
                    error.printStackTrace();
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}