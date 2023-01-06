package com.sirchardash.piria;

import com.sirchardash.piria.repository.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RepositoryModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(MuseumsFragment museumsFragment);

    void inject(LoginFragment loginFragment);

    void inject(ToursFragment toursFragment);

    void inject(ProfileFragment profileFragment);

}
