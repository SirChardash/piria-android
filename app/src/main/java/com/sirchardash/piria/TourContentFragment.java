package com.sirchardash.piria;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sirchardash.piria.auth.UserService;
import com.sirchardash.piria.databinding.FragmentTourContentBinding;
import com.sirchardash.piria.model.TourContentEntry;
import com.sirchardash.piria.repository.RepositoryModule;
import com.sirchardash.piria.repository.SimpleCallback;
import com.sirchardash.piria.repository.TourContentRepository;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TourContentFragment extends Fragment {

    private final TourContentRepository tourContentRepository;
    private final String ticketId;
    private final List<TourContentEntry> content;
    private final UserService userService;

    private FragmentTourContentBinding binding;

    public TourContentFragment(TourContentRepository tourContentRepository,
                               String ticketId,
                               List<TourContentEntry> content,
                               UserService userService) {
        this.tourContentRepository = tourContentRepository;
        this.ticketId = ticketId;
        this.content = content;
        this.userService = userService;
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

        List<TourContentEntry> videos = content.stream()
                .filter(entry -> entry.getType().equals("VIDEO"))
                .collect(Collectors.toList());

        if (videos.isEmpty()) {
            binding.tourContentLayout.removeView(binding.videoView);
        } else {
            videos.forEach(this::showVideo);
        }
    }

    private void showLink(TourContentEntry entry) {
        WebSettings webSettings = binding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        String videoId = entry.getUrl().replaceAll("^.*=", "");

        binding.webView.loadUrl("https://www.youtube.com/embed/" + videoId);
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
                            image.setPadding(0, 30, 0, 30);
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

    private void showVideo(TourContentEntry entry) {
        VideoView videoView = binding.videoView;
        Uri uri = Uri.parse(RepositoryModule.MUSEUM_SERVICE_URL + entry.getUrl().replace("/content/", "/content/video/"));
        System.out.println(uri.toString());
        videoView.setVideoURI(uri, Map.of(
                "Authorization", userService.getAuthorizationHeader(),
                "x-tour-ticket", ticketId
        ));
        MediaController mediaController = new MediaController(getContext());

        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

}