package com.sirchardash.piria;

import static com.sirchardash.piria.repository.RepositoryModule.KEYCLOAK_REGISTER_URL;
import static com.sirchardash.piria.repository.RepositoryModule.SERVER_URL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentLoginBinding;
import com.sirchardash.piria.repository.SimpleCallback;

import javax.inject.Inject;


public class LoginFragment extends Fragment {

    @Inject
    UserService authenticator;

    private FragmentLoginBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        ((PiriaApplication) requireContext().getApplicationContext()).applicationComponent.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_login, container, false);
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.loginButton.setOnClickListener(view1 -> {
            System.out.println("did a cliccko");
            System.out.println(binding.usernameInput.getText().toString());
            System.out.println(binding.passwordInput.getText().toString());
            authenticator.login(
                    binding.usernameInput.getText().toString(),
                    binding.passwordInput.getText().toString(),
                    new SimpleCallback<>(
                            response -> {
                                System.out.println(response.code());
                                System.out.println("heeheheheh");
                                if (response.isSuccessful()) {
                                    authenticator.setAccessToken(response.body());
                                    System.out.println("successso");
                                    getActivity().getSupportFragmentManager().popBackStack();
                                }
                            },
                            error -> {
                            }
                    ));
        });

        binding.registerButton.setOnClickListener(view1 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(KEYCLOAK_REGISTER_URL));
            startActivity(browserIntent);
        });
    }


}