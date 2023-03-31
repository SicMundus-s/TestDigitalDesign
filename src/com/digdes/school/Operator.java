package com.digdes.school;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.digdes.school.Exception.InputException;
import com.digdes.school.util.JavaSchoolStarters;

public class Operator {
    public boolean checkOperation(Map<String, Object> mp, String condition) throws InputException {
        if (JavaSchoolStarters.operatorMapping(condition)) {

            condition = condition.replaceAll("'|\\s","");

            String integerKey = "";
            Pattern pattern = Pattern.compile("(id|age)");
            Matcher matcher = pattern.matcher(condition);
            if (matcher.find()) {
                integerKey = matcher.group();
            }

            if (!integerKey.isEmpty()) {
                if (mp.get(integerKey) == null) {
                    return false;
                }
                String value = "";
                Pattern valuePattern = Pattern.compile("[0-9]+");
                Matcher valueMatcher = valuePattern.matcher(condition);
                if (valueMatcher.find()) {
                    value = valueMatcher.group();
                }

                String op = "";
                Pattern opPattern = Pattern.compile("(=|!=|>=|<=|>|<)");
                Matcher opMatcher = opPattern.matcher(condition);
                if (opMatcher.find()) {
                    op = opMatcher.group();
                }
                int mpValue = (Integer) mp.get(integerKey);
                int intValue = Integer.parseInt(value);
                switch (op) {
                    case "=" -> {
                        return mpValue == intValue;
                    }
                    case "!=" -> {
                        return mpValue != intValue;
                    }
                    case ">=" -> {
                        return mpValue >= intValue;
                    }
                    case "<=" -> {
                        return mpValue <= intValue;
                    }
                    case ">" -> {
                        return mpValue > intValue;
                    }
                    case "<" -> {
                        return mpValue < intValue;
                    }
                }
            } else if (condition.toLowerCase().contains("lastname")) {
                if(mp.get("lastname") == null) {
                    return false;
                }

                String op = "";
                pattern = Pattern.compile("(=|!=|like|ilike)");
                matcher =  pattern.matcher(condition);
                if(matcher.find()) {
                    op = matcher.group();
                }

                condition = condition.substring(8);
                if(Objects.equals(op, "=")) {
                    condition = condition.substring(1);
                    String value = "";
                    pattern = Pattern.compile("%?[a-zA-Zа-яА-Я]+%?");
                    matcher =  pattern.matcher(condition);
                    if(matcher.find()) {
                        value = matcher.group();
                    }
                    return(Objects.equals( mp.get("lastname"), value));
                }
                else if(Objects.equals(op, "!=")) {
                    condition = condition.substring(2);
                    String value = "";
                    pattern = Pattern.compile("%?[a-zA-Zа-яА-Я]+%?");
                    matcher =  pattern.matcher(condition);
                    if(matcher.find()) {
                        value = matcher.group();
                    }
                    return(!Objects.equals( mp.get("lastname"), value));
                }
                else if(Objects.equals(op, "ilike")) {
                    condition = condition.substring(5);
                    String value = "";
                    pattern = Pattern.compile("%?[a-zA-Zа-яА-Я]+%?");
                    matcher =  pattern.matcher(condition);
                    if(matcher.find()) {
                        value = matcher.group();
                    }

                    if(value.charAt(0) == '%' && value.charAt(value.length() - 1) == '%') {
                        value = value.replaceAll("%","");
                        return (Pattern.matches(".*" + value.toLowerCase() + ".*", ((String) mp.get("lastname")).toLowerCase()));
                    }
                    else if(value.charAt(0) == '%') {
                        value = value.replaceAll("%","");
                        return (Pattern.matches(".*" + value.toLowerCase(), ((String) mp.get("lastname")).toLowerCase()));
                    }
                    else if(value.charAt(value.length() - 1) == '%') {
                        value = value.replaceAll("%","");
                        return (Pattern.matches(value.toLowerCase() + ".*", ((String) mp.get("lastname")).toLowerCase()));
                    }
                    else return(Objects.equals(((String) mp.get("lastname")).toLowerCase(), value.toLowerCase()));

                }else if(Objects.equals(op, "like")) {
                    condition = condition.substring(4);
                    String value = "";
                    pattern = Pattern.compile("%?[a-zA-Zа-яА-Я]+%?");
                    matcher =  pattern.matcher(condition);
                    if(matcher.find()) {
                        value = matcher.group();
                    }
                    if(value.charAt(0) == '%' && value.charAt(value.length() - 1) == '%') {
                        value = value.replaceAll("%","");
                        return (Pattern.matches(".*" + value + ".*", (String) mp.get("lastname")));
                    }
                    else if(value.charAt(0) == '%') {
                        value = value.replaceAll("%","");
                        return (Pattern.matches(".*" + value, (String) mp.get("lastname")));
                    }
                    else if(value.charAt(value.length() - 1) == '%') {
                        value = value.replaceAll("%","");
                        return (Pattern.matches(value + ".*", (String) mp.get("lastname")));
                    } else {
                        return(Objects.equals( mp.get("lastname"), value));
                    }
                }
                return false;
            } else if(condition.toLowerCase().contains("active")) {
                if(mp.get("active") == null) {
                    return false;
                }
                String value = "";
                pattern = Pattern.compile("(?i)(true|false)");
                matcher =  pattern.matcher(condition);
                if(matcher.find()) {
                    value = matcher.group();
                }

                if(condition.contains("=")) {
                    return ((boolean) mp.get("active") == Boolean.parseBoolean(value));
                }
                else if(condition.contains("!=")) {
                    return ((boolean) mp.get("active") != Boolean.parseBoolean(value));
                }
            } else if(condition.toLowerCase().contains("cost")) {
                if(mp.get("cost") == null) {
                    return false;
                }
                String value = "";
                pattern = Pattern.compile("[0-9]+(\\.[0-9]+)?");
                matcher = pattern.matcher(condition);
                if (matcher.find()) {
                    value = matcher.group();
                }

                String op = "";
                pattern = Pattern.compile("(=|!=|>=|<=|>|<)");
                matcher = pattern.matcher(condition);
                if (matcher.find()) {
                    op = matcher.group();
                }
                if (Objects.equals(op, "=")) {
                    return (((Double) mp.get("cost")) == Double.parseDouble(value));
                } else if (Objects.equals(op, "!=")) {
                    return (((Double) mp.get("cost")) != Double.parseDouble(value));
                } else if (Objects.equals(op, ">=")) {
                    return (((Double) mp.get("cost")) >= Double.parseDouble(value));
                } else if (Objects.equals(op, "<=")) {
                    return (((Double) mp.get("cost")) <= Double.parseDouble(value));
                } else if (Objects.equals(op, "<")) {
                    return (((Double) mp.get("cost")) < Double.parseDouble(value));
                } else if (Objects.equals(op, ">")) {
                    return (((Double) mp.get("cost")) > Double.parseDouble(value));
                }
            }

        }
        return false;
    }
}