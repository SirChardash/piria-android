package com.sirchardash.piria;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentFirstBinding;

import javax.inject.Inject;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    @Inject
    UserService userService;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((PiriaApplication) requireContext().getApplicationContext()).applicationComponent.inject(this);
        super.onAttach(context);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("hash u FirstFragment" + userService.hashCode());
        userService.login("hehe", "hehe");

        binding.buttonFirst.setOnClickListener(this::navigateToRandom);
    }

    private void navigateToRandom(View view) {
        FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections
                .actionFirstFragmentToSecondFragment(14);

        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}