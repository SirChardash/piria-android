package com.sirchardash.piria;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentMuseumsBinding;
import com.sirchardash.piria.repository.MuseumRepository;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.repository.TourRepository;

import javax.inject.Inject;

public class MuseumsFragment extends Fragment implements NavbarDockedFragment {

    @Inject
    MuseumRepository museumRepository;
    @Inject
    UserService authenticator;
    @Inject
    TourRepository tourRepository;

    private FragmentMuseumsBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        ((PiriaApplication) requireContext().getApplicationContext()).applicationComponent.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMuseumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        Call<Museums> call = museumRepository.find(Languages.EN, "art");

//        call.enqueue(new SimpleCallback<>(
//                response -> binding.textView.setText("" + (response.body().getMuseums().size())),
//                t -> binding.textView.setText(t.getMessage())
//        ));

        System.out.println("### hehe");

        super.onViewCreated(view, savedInstanceState);

        binding.searchInput.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {

                        System.out.println(binding.searchInput.getQuery().toString());
                        museumRepository.find("en", binding.searchInput.getQuery().toString()).enqueue(new SimpleCallback<>(
                                x -> {
                                    System.out.println("mamma fucker");
                                    System.out.println(x.body());
                                    System.out.println(x.code());
                                    if (x.code() == 401) {
                                        ((MainActivity) getActivity()).navigateTo(new LoginFragment(), true);
                                    } else {
                                        binding.museumLayout.removeAllViews();
                                        x.body().getMuseums().stream()
                                                .map(museum -> {
                                                    MuseumCard museumCard = new MuseumCard(getContext(), null);
                                                    museumCard.setMuseum(museum);
                                                    museumCard.setOnClickListener(view1 -> {
                                                        ((MainActivity) getActivity()).navigateTo(new MuseumFragment(museum, tourRepository), true);
                                                    });

                                                    return museumCard;
                                                }).forEach(museum -> binding.museumLayout.addView(museum));
                                    }
                                },
                                x -> {
                                    System.out.println(x.getMessage());
                                }

                        ));

                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                }

        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}