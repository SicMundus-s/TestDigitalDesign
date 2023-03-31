package com.digdes.school;


import com.digdes.school.Exception.ExecuteException;
import com.digdes.school.Exception.InputException;
import com.digdes.school.Exception.UpdateException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.digdes.school.util.JavaSchoolStarters.checkIndex;
import static com.digdes.school.util.JavaSchoolStarters.checkWhere;


public class JavaSchoolStarter {
    private List<Map<String, Object>> table = new ArrayList<>();
    private List<Map<String, Object>> response;


    public JavaSchoolStarter() {

    }

    public List<Map<String, Object>> execute(String request) throws ExecuteException, InputException, UpdateException {
        String regex = "(?i)(?<method>INSERT VALUES|UPDATE VALUES|SELECT|DELETE)(?<values>.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);
        if (matcher.find()) {
            String method = matcher.group("method").toUpperCase();
            String values = matcher.group("values");
            switch (ActionType.fromString(method)) {
                case INSERT -> response = insert(values);
                case UPDATE -> response = update(values);
                case SELECT -> response = select(values);
                case DELETE -> response = delete(values);
                default -> throw new ExecuteException("Command not defined");
            }
        }
        return response;
    }

    private List<Map<String, Object>> select(String request) {
        List<Map<String, Object>> selectResponse = new ArrayList<>();
        if (request.length() == 0) {
            if (table.isEmpty()) {
                System.out.println("Table is empty: ");
            }
            return table;
        } else {
            String[] conditions = request.split("(?i)WHERE");
            for (Map<String, Object> row : table) {
                if (conditions.length == 1 && checkWhere(row, conditions[0])) {
                    selectResponse.add(row);
                    continue;
                }
                if (conditions.length == 2 && checkWhere(row, conditions[1])) {
                    selectResponse.add(row);
                }
            }
            return selectResponse;
        }
    }

    private List<Map<String, Object>> delete(String request) {
        List<Map<String, Object>> deleteResponse = new ArrayList<>();
        if (request.isEmpty()) {
            deleteResponse.addAll(table);
            table.clear();
        } else {
            String[] conditions = request.split("(?i)WHERE");

            Iterator<Map<String, Object>> iterator = table.iterator();

            while (iterator.hasNext()) {
                Map<String, Object> row = iterator.next();

                if (conditions.length == 1 && checkWhere(row, conditions[0])) {
                    deleteResponse.add(row);
                    iterator.remove();
                } else if (conditions.length == 2 && checkWhere(row, conditions[1])) {
                    deleteResponse.add(row);
                    iterator.remove();
                }
            }
        }
        return deleteResponse;
    }

    private List<Map<String, Object>> insert(String input) throws InputException {
        Map<String, Object> row = new HashMap<>();
        List<Map<String, Object>> insertResponse = new ArrayList<>();
        String[] indexs = input.split(",");
        for (String index : indexs) {
            checkIndex(index);
            index = index.replaceAll("\s+|'", "");
            String[] keyValue = index.split("=");
            for (int i = 0; i < keyValue.length - 1; i++) {
                switch (keyValue[i].toLowerCase()) {
                    case "id" -> row.put(keyValue[i], Integer.parseInt(keyValue[i + 1]));
                    case "lastname" -> row.put(keyValue[i], keyValue[i + 1]);
                    case "age" -> row.put(keyValue[i], Integer.parseInt(keyValue[i + 1]));
                    case "cost" -> row.put(keyValue[i], Double.parseDouble(keyValue[i + 1]));
                    case "active" -> row.put(keyValue[i], Boolean.parseBoolean(keyValue[i + 1]));
                    default -> {
                    }
                }
            }
        }

        table.add(row);
        insertResponse.add(row);
        return insertResponse;
    }

    private List<Map<String, Object>> update(String input) throws  UpdateException {
        List<Map<String, Object>> updateResponse = new ArrayList<>();
        String[] indexs_and_conditions = input.split("(?i)WHERE");

        if (indexs_and_conditions.length > 2) {
            throw new UpdateException("Update exception");
        }

        for (Map<String, Object> row : table) {
            if (indexs_and_conditions.length == 1 || checkWhere(row, indexs_and_conditions[1])) {
                String[] indexs = indexs_and_conditions[0].split(",");
                for (String index : indexs) {
                    index = index.replaceAll("\\s+|'", "");
                    String[] keyValue = index.split("=");

                    switch (keyValue[0].toLowerCase()) {
                        case "id" -> row.put(keyValue[0], Integer.parseInt(keyValue[1]));
                        case "lastname" -> row.put(keyValue[0], keyValue[1]);
                        case "age" -> row.put(keyValue[0], Integer.parseInt(keyValue[1]));
                        case "cost" -> row.put(keyValue[0], Double.parseDouble(keyValue[1]));
                        case "active" -> row.put(keyValue[0], Boolean.parseBoolean(keyValue[1]));
                        default -> {
                        }
                    }
                }
                updateResponse.add(row);
            }
        }
        return updateResponse;
    }
}