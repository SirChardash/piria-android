package com.sirchardash.piria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentMuseumsBinding;
import com.sirchardash.piria.model.Languages;
import com.sirchardash.piria.model.Museum;
import com.sirchardash.piria.model.Museums;
import com.sirchardash.piria.repository.MuseumRepository;
import com.sirchardash.piria.repository.SimpleCallback;

import java.util.function.Consumer;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class MuseumsFragment extends Fragment {

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

        museumRepository.findById(4).enqueue(new SimpleCallback<>(
                x -> {
                    System.out.println("mamma fucker");
                    System.out.println(x.body());
                    System.out.println(x.code());
                },
                x -> System.out.println(x.getMessage())
        ));

        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(view1 -> {
//                    Intent myIntent = new Intent(getActivity(), ViewMuseumActivity.class);
//                    getActivity().startActivity(myIntent);
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