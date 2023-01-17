package com.sirchardash.piria;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.UserInfo;
import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentProfileBinding;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.util.LocaleUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        userService.popLoginScreenIfNeeded((MainActivity) getActivity());
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.profileLayout.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);
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
        binding.switchLanguageButton.setOnClickListener(this::changeLanguage);
    }

    public void updateInterface() {
        binding.logOutButton.setText(R.string.log_out_cta);
        binding.switchLanguageButton.setText(R.string.change_language_cta);
    }

    private void changeLanguage(View view) {
        String currentLanguage = LocaleUtils.getCurrentLocale();

        String language = currentLanguage.equals("sr") ? "en" : "sr";
        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(language);
        AppCompatDelegate.setApplicationLocales(appLocale);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void populateUserInfo(UserInfo info) {
        if (binding == null) {
            return;
        }

        binding.profileNameLabel.setText(info.getFullName());
        binding.profileUsernameLabel.setText(info.getUsername());
        binding.profileEmaiLabel.setText(info.getEmail());
        binding.profileLayout.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }

    private void logOut(View view) {
        userService.setAccessToken(null);
        getActivity().getPreferences(Context.MODE_PRIVATE).edit().putString("refreshToken", null).apply();
        ((MainActivity) getActivity()).navigateTo(new LoginFragment(), true);
    }

}