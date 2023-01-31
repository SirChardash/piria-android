package com.sirchardash.piria;

import static com.sirchardash.piria.util.ToastUtil.showToast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.UserInfo;
import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentTourBinding;
import com.sirchardash.piria.model.Tour;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.repository.TourContentRepository;
import com.sirchardash.piria.util.RandomUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class TourFragment extends Fragment {

    private static final String BANK_URL_TEMPLATE = "http://34.118.42.41:8040/%s/" +
            "?paymentNumber=%s" +
            "&amount=%.2f" +
            "&callbackUrl=getfucked" +
            "&receiver=3205732519283123" +
            "&purpose=attendance+of+%s+to+tour+%d";

    private final Tour tour;
    private final UserService userService;
    private final TourContentRepository tourContentRepository;
    private final boolean allowBuyTicket;
    private final boolean allowVisit;
    private UserInfo userInfo;

    private FragmentTourBinding binding;

    public TourFragment(Tour tour,
                        UserService userService,
                        TourContentRepository tourContentRepository,
                        boolean allowBuyTicket,
                        boolean allowVisit) {
        this.tour = tour;
        this.userService = userService;
        this.tourContentRepository = tourContentRepository;
        this.allowBuyTicket = allowBuyTicket;
        this.allowVisit = allowVisit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTourBinding.inflate(inflater, container, false);
        binding.tourNameLabel.setText(tour.getTitle());
        binding.tourDescriptionLabel.setText(tour.getDescription());
        binding.tourStartTimeLabel.setText(getTime(tour.getStartTime()));
        binding.tourEndTimeLabel.setText(getTime(tour.getEndTime()));
        String startDate = getDate(tour.getStartTime());
        String endDate = getDate(tour.getEndTime());
        binding.tourDateLabel.setText(
                startDate.equals(endDate)
                        ? startDate
                        : startDate + " - " + endDate
        );

        binding.buyTicketButton.setVisibility(allowBuyTicket ? View.VISIBLE : View.INVISIBLE);
        binding.visitTourButton.setVisibility(allowVisit ? View.VISIBLE : View.INVISIBLE);
        binding.ticketIdInput.setVisibility(allowVisit ? View.VISIBLE : View.INVISIBLE);

        if (allowBuyTicket) {
            userService.getUserInfo().enqueue(new SimpleCallback<>(
                    response -> {
                        if (response.isSuccessful()) {
                            userInfo = response.body();
                        }
                    },
                    error -> {
                    }
            ));
            binding.buyTicketButton.setOnClickListener(this::openBuyPage);
        }
        if (allowVisit) {
            binding.visitTourButton.setOnClickListener(this::visitTour);
        }

        return binding.getRoot();
    }

    private void visitTour(View view) {
        String ticketId = binding.ticketIdInput.getText().toString();

        if (ticketId.trim().isEmpty()) {
            showToast(getContext(), "Please input your ticket ID");
            return;
        }

        System.out.println("#####" + tour.getId() + " #### " + ticketId);
        tourContentRepository.getContentList(tour.getId(), ticketId).enqueue(
                new SimpleCallback<>(
                        response -> {
                            if (response.isSuccessful()) {
                                ((MainActivity) getActivity()).navigateTo(
                                        new TourContentFragment(
                                                tourContentRepository,
                                                ticketId,
                                                response.body()),
                                        true
                                );
                            } else {
                                showToast(getContext(), "Gone fukked: " + response.code());
                            }
                        },
                        error -> showToast(getContext(), "Gone fukked: " + error.getMessage())
                )
        );
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

    }

    private void openBuyPage(View view) {
        if (userInfo == null) {
            return;
        }

        String paymentNumber = generatePaymentNumber();
        String url = String.format(
                Locale.ENGLISH,
                BANK_URL_TEMPLATE,
                "sr",
                paymentNumber,
                tour.getTicketPrice(),
                userInfo.getUserId(),
                tour.getId()
        );

        getActivity().getPreferences(Context.MODE_PRIVATE).edit()
                .putString("pendingPaymentNumber", paymentNumber)
                .putInt("pendingPaymentTourId", tour.getId())
                .apply();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private String getTime(String date) {
        return date.replaceAll(".*T", "").replaceAll(":\\d\\d$", "");
    }

    private String getDate(String date) {
        String[] split = date.replaceAll("T.*", "").split("-");
        return split.length != 3 ? "" : split[2] + "-" + split[1] + "-" + split[0];
    }

    private String generatePaymentNumber() {
        return String.format(Locale.ENGLISH,
                "%d%d%d",
                new Date().getTime() % 1_000_000,
                Math.abs(userInfo.getEmail().hashCode() % 1_000_000),
                1000 + RandomUtils.random().nextInt(8999)
        );
    }

}
