/************************************************************************************************
 *  ____ _____ ______ ___   __ _____  __   _ _____ _        __    _ _      _   _   _ ___
 * | __ \_   _|_   __| __|/ _ \_   _||   \| |___  | \      / /_ _| | | ___| |_| | | |_ _|
 * |____/ | |   | |/ /   / / \ \| |  | |\ | |  / / \ \ /\ / / _` | | |/ _ \ __| | | || |
 * | ___ \| |_  | |\ \__ \ \_/ /| |_ | | \  | / /_  \ V  V / (_| | | |  __/ |_| |_| || |
 * |_____/____| |_| \____|\___/_____||_|  \_|/____|  \_/\_/ \__,_|_|_|\___|\__|\___/|___|

 * Copyright (c) 2017-2022 BitcoinZ team
 * Copyright (c) 2016 Ivan Vaklinov <ivan@vaklinov.com>

 */

package com.bitcoinz.btczui;

import javax.swing.*;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Language Utility resource bundle loader.
 *
 * @author aballaci <aballaci@gmail.com>
 */
public class LanguageUtil {

    private static final String PREFERRED_LOCALE_FILE_NAME = "language_preferences.txt";

    private static final String RESOURCE_BUNDLE_FILE_NAME = "messages.bitcoinz";

    private static final Locale DEFAULT_LOCALE = Locale.US;


    private LanguageUtil(){
        supportedLocale = new HashMap();
        supportedLocale.put(Locale.US.getCountry(), Locale.US);
        supportedLocale.put(Locale.ITALY.getCountry(), Locale.ITALY);
        supportedLocale.put(Locale.GERMANY.getCountry(), Locale.GERMANY);
    }

    private static LanguageUtil instance;

    private Map<String, Locale>  supportedLocale;

    private ResourceBundle rb;

    public static LanguageUtil instance(){
        if(instance == null){
            instance = new LanguageUtil();
            instance.loadBundle();
        }
        return instance;
    }

    private void loadBundle(){
        Locale currentLocale = getUsersPreferredLocale();
        rb = ResourceBundle.getBundle(RESOURCE_BUNDLE_FILE_NAME, currentLocale);
        Log.info("Loading locale: " + currentLocale.toString());
    }

    public String getString(String key){
        try {
            return rb.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public  String getString(String key, Object... params  ) {
        try {
            return MessageFormat.format(rb.getString(key), params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public void updatePreferredLanguage(Locale locale) {
        try {
            File languagePrefsFile = new File(OSUtil.getSettingsDirectory(),PREFERRED_LOCALE_FILE_NAME );
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(languagePrefsFile))) {
                    printWriter.println(locale.getCountry());
            }

        } catch (IOException e) {
            Log.error("Saving Preferred Locale Failed!!!!", e);
        }
    }

    public Locale getUsersPreferredLocale() {
        File languagePrefsFile;
        try {
            languagePrefsFile = new File(OSUtil.getSettingsDirectory(),PREFERRED_LOCALE_FILE_NAME);

        if (!languagePrefsFile.exists()) {
            return DEFAULT_LOCALE;
        }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(languagePrefsFile));
            String country = bufferedReader.readLine().trim();
            bufferedReader.close();
            return supportedLocale.get(country);
        } catch (FileNotFoundException e) {
            Log.error("Loading Language file Failed!!!!", e);
            return DEFAULT_LOCALE;
        } catch (IOException e) {
            Log.error("Loading Language file Failed!!!!", e);
            return DEFAULT_LOCALE;
        }
    }

}
