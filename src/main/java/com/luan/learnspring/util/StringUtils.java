package com.luan.learnspring.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * Remove acentos de uma string.
     *
     * @param value a string a ser processada
     * @return a string sem acentos
     */
    public static String removeAccents(String value) {
        if (value == null) {
            return null;
        }
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

}
