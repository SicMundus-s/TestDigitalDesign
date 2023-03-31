package com.digdes.school.util;

import com.digdes.school.Exception.InputException;
import com.digdes.school.Operator;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The class contains helper methods for JavaSchoolStarter
 */
public class JavaSchoolStarters {
    private JavaSchoolStarters() {

    }

    public static boolean checkIndex(String index) throws InputException {
        String idRegex = "(?i)(\\s*'id'\\s?=\\s?[0-9]+\\s*)";
        String lastnameRegex = "(\\s*'lastname'\\s?(=)\\s?'[a-zA-Zа-яА-Я]+'\\s*)";
        String ageRegex = "(\\s*'age'\\s?=\\s?[0-9]+\\s*)";
        String costRegex = "(\\s*'cost'\\s?=\\s?[0-9]+(\\.[0-9]+)*\\s*)";
        String activeRegex = "(\\s*'active'\\s?=\\s?(true|false)\\s*)";
        String regex = String.format(
                "(?i)%s|%s|%s|%s|%s",
                idRegex,
                lastnameRegex,
                ageRegex,
                costRegex,
                activeRegex
        );
        if (!Pattern.matches(regex, index)) {
            throw new InputException("Input error : " + index);
        } else {
            return true;
        }
    }

    public static boolean operatorMapping(String operator) throws InputException {
        String idRegex = "(\\s*'id'\\s?(=|!=|>=|<=|>|<)\\s?[0-9]+\\s*)";
        String lastnameRegex = "(\\s*'lastname'\\s?(=|!=|like|ilike)\\s?'%?[a-zA-Zа-яА-Я]+%?'\\s*)";
        String ageRegex = "(\\s*'age'\\s?(=|!=|>=|<=|>|<)\\s?[0-9]+\\s*)";
        String costRegex = "(\\s*'cost'\\s?(=|!=|>=|<=|>|<)\\s?[0-9]+(\\.[0-9]+)?\\s*)";
        String activeRegex = "(\\s*'active'\\s?(=|!=)\\s?(true|false)\\s*)";
        String regex = String.format(
                "(?i)%s|%s|%s|%s|%s",
                idRegex,
                lastnameRegex,
                ageRegex,
                costRegex,
                activeRegex
        );
        if (!Pattern.matches(regex, operator)) {
            throw new InputException("Input error : " + operator);
        } else {
            return true;
        }
    }

    public static boolean checkWhere(Map<String, Object> mp, String metewand) {
        String[] ands = metewand.split("(?i)OR");
        Operator operator = new Operator();
        return Arrays.stream(ands)
                .map(cr -> cr.split("(?i)AND"))
                .anyMatch(conditions -> Arrays.stream(conditions)
                        .allMatch(condition -> {
                            try {
                                return operator.checkOperation(mp, condition);
                            } catch (InputException e) {
                                throw new RuntimeException(e);
                            }
                        })
                );
    }
}
