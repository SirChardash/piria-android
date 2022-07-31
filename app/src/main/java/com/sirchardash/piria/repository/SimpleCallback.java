package com.sirchardash.piria.repository;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SimpleCallback<T> implements Callback<T> {

    private final Consumer<Response<T>> successConsumer;
    private final Consumer<Throwable> errorConsumer;

    public SimpleCallback(Consumer<Response<T>> successConsumer,
                          Consumer<Throwable> errorConsumer) {
        this.successConsumer = successConsumer;
        this.errorConsumer = errorConsumer;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        successConsumer.accept(response);
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        errorConsumer.accept(t);
    }
}
