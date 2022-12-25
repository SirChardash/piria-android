package com.sirchardash.piria;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentMuseumsBinding;
import com.sirchardash.piria.model.Museum;
import com.sirchardash.piria.repository.MuseumRepository;
import com.sirchardash.piria.repository.SimpleCallback;

import java.util.function.Consumer;

import javax.inject.Inject;

import retrofit2.Response;

public class MuseumsFragment extends Fragment implements NavbarDockedFragment {

    @Inject
    MuseumRepository museumRepository;
    @Inject
    UserService authenticator;

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

        binding.buttonSecond.setOnClickListener(view1 -> {
                    museumRepository.findById(4).enqueue(new SimpleCallback<>(
                            x -> {
                                System.out.println("mamma fucker");
                                System.out.println(x.body());
                                System.out.println(x.code());
                            },
                            x -> System.out.println(x.getMessage())
                    ));

                    ((MainActivity) getActivity()).navigateTo(new MuseumFragment(), true);
                }
        );
    }

    private void eh(Consumer<Response<Museum>> enqueue) {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}