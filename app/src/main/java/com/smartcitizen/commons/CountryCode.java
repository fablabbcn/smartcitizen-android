package com.smartcitizen.commons;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by ferran on 27/08/15.
 *
 * http://kodejava.org/how-do-i-get-a-list-of-country-names/
 */
public class CountryCode {

    private static CountryCode ourInstance = new CountryCode();

    public static CountryCode getInstance() {
        return ourInstance;
    }

    private Map<String, String> countries;

    private CountryCode() {
        countries = new HashMap<>();

        String[] isoCountries = Locale.getISOCountries();
        String tagLocale = Locale.getDefault().toString();

        for (String country : isoCountries) {

            Locale locale = new Locale(tagLocale, country);
            String iso = locale.getISO3Country();
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();

            if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
                //countries.add(new Country(iso, code, name));
                countries.put(code, name);
            }
        }

    }

    public String getCountryNameByCode(String code) {
        String name = countries.get(code);
        if (name == null) {
            return code;
        } else {
            return name;
        }
    }
}
