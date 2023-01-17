package com.sirchardash.piria;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentMuseumsBinding;
import com.sirchardash.piria.model.Museum;
import com.sirchardash.piria.repository.MuseumRepository;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.repository.TourRepository;
import com.sirchardash.piria.util.LocaleUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class MuseumsFragment extends Fragment implements NavbarDockedFragment {

    @Inject
    MuseumRepository museumRepository;
    @Inject
    UserService userService;
    @Inject
    TourRepository tourRepository;

    private FragmentMuseumsBinding binding;

    private List<Museum> museums = Collections.emptyList();

    @Override
    public void onAttach(@NonNull Context context) {
        ((PiriaApplication) requireContext().getApplicationContext())
                .applicationComponent
                .inject(this);

        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        userService.popLoginScreenIfNeeded((MainActivity) getActivity());
        binding = FragmentMuseumsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateList(museums);
        binding.searchInput.setOnQueryTextListener(searchListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void populateList(List<Museum> museums) {
        binding.museumLayout.removeAllViews();
        museums.stream()
                .map(museum -> {
                    MuseumCard museumCard = new MuseumCard(getContext(), null);
                    museumCard.setMuseum(museum);
                    museumCard.setOnClickListener(x -> ((MainActivity) getActivity()).navigateTo(
                            new MuseumFragment(museum, tourRepository), true
                    ));

                    return museumCard;
                }).forEach(museum -> binding.museumLayout.addView(museum));
    }

    SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            System.out.println(binding.searchInput.getQuery().toString());
            museumRepository.find(LocaleUtils.getCurrentLocale(), binding.searchInput.getQuery().toString()).enqueue(new SimpleCallback<>(
                    response -> {
                        if (response.isSuccessful() && response.body() != null) {
                            populateList(museums = response.body().getMuseums());
                        }
                    },
                    x -> System.out.println(x.getMessage())
            ));

            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    public void updateInterface() {
        museums = Collections.emptyList();
    }

}