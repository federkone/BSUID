/**
 * 2026 Federico Barrionuevo github: "@federkone"
 *
*/

package io.github.federkone.bsuid;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Business-Scoped User ID
 * <p>
 * They include the user's ISO 3166 alpha-2 two-letter country code and a dot as a prefix,
 * followed by up to 128 alphanumeric characters (e.g., US.13491208655302741918).
 * <p>
 *  Officially documented by Meta for Meta Business use.
 *  <a href="https://developers.facebook.com/documentation/business-messaging/whatsapp/business-scoped-user-ids/">Facebook documentation</a>
 * */
public final class BSUID implements java.io.Serializable, Comparable<BSUID>{
    private static final Pattern FORMAT = Pattern.compile("^([A-Z]{2})\\.[A-Za-z0-9]{1,128}$");
    private static Set<String> ISO_COUNTRIES = Set.of(Locale.getISOCountries());

    private final String stringBSUID;

    private BSUID(String stringBSUID) {
        this.stringBSUID = stringBSUID;
    }

    /**
     * @return A valid BSUID from String.
     * @param input intentionally String input.
     * @throws InvalidBSUIDException if the String input is not valid BSUID.
     * @throws NullPointerException if the String input is null.
     *
     * */
    public static BSUID fromString(String input) throws InvalidBSUIDException,NullPointerException {
        if (input == null) throw new NullPointerException();
        Matcher matcher = FORMAT.matcher(input);
        boolean validPattern = matcher.matches();
        if (!validPattern) {
            throw new InvalidBSUIDException("Invalid pattern for BSUID");
        }
        String country = matcher.group(1);
        boolean validCountry = ISO_COUNTRIES.contains(country);
        if (!validCountry) {
            throw new InvalidBSUIDException("Invalid country for BSUID");
        }

        return new BSUID(input);
    }

    /**
     * @return Generate a valid random BSUID for testing.
     * */
    public static BSUID randomBSUID() {
        return BSUIDRandomizer.random();
    }

    /**
     * @return if a String is an BSUID representation.
     * @throws NullPointerException if String input is null.
     * */
    public static boolean itIsAnBSUID(String string)throws NullPointerException {
        if (string == null) throw new NullPointerException();
        Matcher matcher = FORMAT.matcher(string);
        boolean validPattern = matcher.matches();
        if (!validPattern) {
            return false;
        }
        String country = matcher.group(1);
        return ISO_COUNTRIES.contains(country);
    }

    /**
     * @return String value of BSUID.
     * */
    public String value(){
        return stringBSUID;
    }

    /**
     * Optional method.
     * <p>
     * Setup ISO 3166 alpha-2 from your dataset.
     * @throws IllegalArgumentException if a country from the list doesn't match with pattern ISO 3166 alpha-2.
     *<p>
     * Default countries: java.util.Locale.getISOCountries()
     * */
    public void setupCountries(Set<String> countries) throws IllegalArgumentException{
        Pattern countryPattern = Pattern.compile("^([A-Z]{2})");
        countries.forEach(country -> {
            if (!countryPattern.matcher(country).matches()){
                throw new IllegalArgumentException("Invalid country for BSUID"+ country + ", valid pattern: "+ countryPattern.pattern());
            }
        });

        BSUID.ISO_COUNTRIES = countries;
        BSUIDRandomizer.ISO_COUNTRIES_ARRAY = countries.toArray(new String[0]);
    }

    @Override
    public String toString() {
        return stringBSUID;
    }

    /**
     * Check if a BSUID is equal to another BSUID.
     * */
    public boolean equals(BSUID bsuid){
        if (bsuid == null) return false;
        if (bsuid == this) return true;
        return this.stringBSUID.equals(bsuid.value());
    }

    @Override
    public int compareTo(BSUID o) {
        return this.stringBSUID.compareTo(o.value());
    }

    /**
     * Randomizer class for BSUID.
     * */
    private static class BSUIDRandomizer{
        private static final int MAX_LENGTH = 128;
        private static final Random RANDOM = new SecureRandom();
        private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private static String[] ISO_COUNTRIES_ARRAY = Locale.getISOCountries();

        public static BSUID random(){
            return randomExample(1 + RANDOM.nextInt(MAX_LENGTH));
        }

        private static BSUID randomExample(int suffixLength) {
            if (suffixLength < 1 || suffixLength > MAX_LENGTH) {
                throw new IllegalArgumentException("suffixLength debe estar entre 1 y " + MAX_LENGTH);
            }
            return BSUID.fromString(randomCountry() + "." + randomAlphanumeric(suffixLength));
        }

        private static String randomCountry() {
            return ISO_COUNTRIES_ARRAY[RANDOM.nextInt(ISO_COUNTRIES_ARRAY.length)];
        }

        private static String randomAlphanumeric(int length) {
            var sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
            }
            return sb.toString();
        }
    }
}
