package com.ahmedadeltito.photoeditor;

import java.util.HashMap;

/**
 * Created by Thien PV on 11/24/23.
 */
public class TranslationService {

    private static TranslationService instance = null;
    private HashMap<String, String> translations;

    private TranslationService() {
        translations = new HashMap<>();
    }

    public static TranslationService getInstance() {
        if (instance == null) {
            instance = new TranslationService();
        }
        return instance;
    }

    public void init(HashMap translations) {
        this.translations = translations;
    }

    public String getTranslation(String key) {
        return translations.get(key);
    }
}
