package com.sirchardash.piria;

import android.app.Application;

public class PiriaApplication extends Application {

    ApplicationComponent applicationComponent = DaggerApplicationComponent.create();

}
