package com.sirchardash.piria.util;

import androidx.appcompat.app.AppCompatDelegate;

public class LocaleUtils {

    public static String getCurrentLocale() {
        return AppCompatDelegate.getApplicationLocales().get(0) == null
                ? "en"
                : AppCompatDelegate.getApplicationLocales().get(0).toString();
    }

}
