package com.sirchardash.piria;

import static com.sirchardash.piria.repository.RepositoryModule.KEYCLOAK_REGISTER_URL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.AccessToken;
import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentLoginBinding;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.repository.TourRepository;
import com.sirchardash.piria.util.RandomUtils;
import com.sirchardash.piria.util.ToastUtil;

import java.util.Random;

import javax.inject.Inject;


public class LoginFragment extends Fragment {

    private static final String REFRESH_TOKEN = "refreshToken";

    @Inject
    UserService userService;
    @Inject
    TourRepository tourRepository;

    private FragmentLoginBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        ((PiriaApplication) requireContext().getApplicationContext())
                .applicationComponent
                .inject(this);

        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        ((MainActivity) getActivity()).setBackDisabled(true);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.loginFormLayout.setVisibility(View.INVISIBLE);

        userService.onRefresh(this::saveRefreshToken);
        tryRestoreSession();

        loadSplashImage();

        binding.loginButton.setOnClickListener(this::login);
        binding.registerButton.setOnClickListener(this::openRegisterPage);
    }

    private void tryRestoreSession() {
        AccessToken storedToken = getStoredToken();
        if (storedToken != null) {
            userService.setAccessToken(storedToken);
            tourRepository.listUpcoming().enqueue(new SimpleCallback<>(
                    response -> {
                        if (response.isSuccessful()) {
                            ((MainActivity) getActivity()).setBackDisabled(false);
                            getActivity().onBackPressed();
                        } else {
                            showForm();
                        }
                    },
                    error -> showForm()
            ));
        } else {
            showForm();
        }
    }

    private void saveRefreshToken(AccessToken accessToken) {
        getActivity().getPreferences(Context.MODE_PRIVATE).edit()
                .putString(REFRESH_TOKEN, accessToken != null ? accessToken.getRefreshToken() : null)
                .apply();
    }

    private AccessToken getStoredToken() {
        String refreshToken = getActivity().getPreferences(Context.MODE_PRIVATE)
                .getString(REFRESH_TOKEN, null);

        return refreshToken != null
                ? new AccessToken(
                null,
                refreshToken,
                0,
                0,
                null,
                null
        ) : null;
    }

    private void showForm() {
        binding.loginFormLayout.setVisibility(View.VISIBLE);
        binding.loader.setVisibility(View.INVISIBLE);
    }

    private void login(View view) {
        userService.login(
                binding.usernameInput.getText().toString(),
                binding.passwordInput.getText().toString(),
                new SimpleCallback<>(
                        response -> {
                            if (response.isSuccessful()) {
                                userService.setAccessToken(response.body());
                                saveRefreshToken(response.body());
                                ((MainActivity) getActivity()).setBackDisabled(false);
                                getActivity().onBackPressed();
                            } else {
                                ToastUtil.showToast(getContext(), "Login failed. Check credentials.");
                            }
                        },
                        error -> ToastUtil.showToast(getContext(), "Login failed.")
                ));
    }

    private void openRegisterPage(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(KEYCLOAK_REGISTER_URL));
        startActivity(browserIntent);
    }

    private void loadSplashImage() {
        int id = getResources().getIdentifier(
                "login_screen_" + RandomUtils.random().nextInt(5),
                "drawable",
                getActivity().getPackageName()
        );
        binding.splashImage.setImageResource(id);
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        binding.splashImage.startAnimation(fadeIn);
    }

}