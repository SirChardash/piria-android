package com.sirchardash.piria;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.databinding.FragmentTourContentBinding;
import com.sirchardash.piria.model.TourContentEntry;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.repository.TourContentRepository;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TourContentFragment extends Fragment {

    private final TourContentRepository tourContentRepository;
    private final String ticketId;
    private final List<TourContentEntry> content;

    private FragmentTourContentBinding binding;

    public TourContentFragment(TourContentRepository tourContentRepository,
                               String ticketId,
                               List<TourContentEntry> content) {

        this.tourContentRepository = tourContentRepository;
        this.ticketId = ticketId;
        this.content = content;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTourContentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        content.stream()
                .filter(entry -> entry.getType().equals("IMAGE"))
                .forEach(this::showImage);

        List<TourContentEntry> links = content.stream()
                .filter(entry -> entry.getType().equals("LINK"))
                .collect(Collectors.toList());

        if (links.isEmpty()) {
            binding.tourContentLayout.removeView(binding.webView);
        } else {
            links.forEach(this::showLink);
        }
    }

    private void showLink(TourContentEntry entry) {
        int id = Integer.parseInt(entry.getUrl().replace("/content/", ""));

        tourContentRepository.getContent(id, ticketId).enqueue(new SimpleCallback<>(
                response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            WebSettings webSettings = binding.webView.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            webSettings.setLoadWithOverviewMode(true);
                            webSettings.setUseWideViewPort(true);
                            String videoId = new String(response.body().bytes()).replaceAll("^.*=", "");

                            binding.webView.loadUrl("https://www.youtube.com/embed/" + videoId);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                Throwable::printStackTrace
        ));
    }

    private void showImage(TourContentEntry entry) {
        int id = Integer.parseInt(entry.getUrl().replace("/content/", ""));

        tourContentRepository.getContent(id, ticketId).enqueue(new SimpleCallback<>(
                response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            byte[] bytes = response.body().bytes();
                            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView image = new ImageView(getContext());
                            image.setMinimumHeight(binding.tourContentLayout.getWidth());
                            image.setMinimumWidth(binding.tourContentLayout.getWidth());
                            image.setImageBitmap(bmp);

                            binding.tourContentLayout.addView(image);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },
                Throwable::printStackTrace
        ));
    }

}