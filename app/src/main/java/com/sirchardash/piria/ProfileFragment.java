package com.sirchardash.piria;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.UserInfo;
import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentProfileBinding;
import com.sirchardash.piria.repository.SimpleCallback;

import javax.inject.Inject;

public class ProfileFragment extends Fragment implements NavbarDockedFragment {

    @Inject
    UserService userService;

    private FragmentProfileBinding binding;
    private UserInfo userInfo;

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
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (userInfo == null) {
            userService.getUserInfo().enqueue(new SimpleCallback<>(response -> {
                if (response.isSuccessful() && response.body() != null) {
                    populateUserInfo(userInfo = response.body());
                }
            }, error -> {
            }));
        } else {
            populateUserInfo(userInfo);
        }

        binding.logOutButton.setOnClickListener(this::logOut);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void populateUserInfo(UserInfo info) {
        binding.profileNameLabel.setText(info.getFullName());
        binding.profileUsernameLabel.setText(info.getUsername());
        binding.profileEmaiLabel.setText(info.getEmail());
    }

    private void logOut(View view) {
        userService.setAccessToken(null);
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putString("refreshToken", null).apply();
        ((MainActivity) getActivity()).navigateTo(new LoginFragment(), true);
    }

}