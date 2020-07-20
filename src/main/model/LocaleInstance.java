package main.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Properties;

public class LocaleInstance {
    private static final LocaleInstance instance = new LocaleInstance();
    private Locale defaultLanguage = Locale.getDefault();
    String language = defaultLanguage.getLanguage();
    private Properties dictionary = new Properties();

    private Map<String, String> dictionaryFiles = Stream.of(new String[][] {
            { "en", "dictionary.properties" },
            { "es", "dictionary_es.properties" },
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private LocaleInstance(){
        String languageFile = dictionaryFiles.get(language);
        try {
            getLanguageValues(languageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getDictionary(){
        return dictionary;
    }
    private void getLanguageValues(String languageFile) throws IOException {
        InputStream inputStream;
        if (languageFile == null) {
            System.out.println("property file for the language '" + language + "' not found in the resources folder using default english");
            inputStream = getClass().getClassLoader().getResourceAsStream(dictionaryFiles.get("en"));
        } else {
            inputStream = getClass().getClassLoader().getResourceAsStream(languageFile);
        }
        if (inputStream != null) {
            dictionary.load(inputStream);
            inputStream.close();
        } else {
            System.out.println("There was a problem loading the property file");
        }
    }

    public static LocaleInstance getInstance(){
        return instance;
    }
}