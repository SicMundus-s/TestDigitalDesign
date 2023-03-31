package com.digdes.school;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        List<Map<String, Object>> execute1 = starter.execute("INSERT VALUES  'id' = 1, 'lastname' = 'Волков', 'cost' = 21, 'age' = 21, 'active' = true");
        System.out.println("INSERT1: " + execute1);
        List<Map<String, Object>> execute2 = starter.execute("INSERT VALUES  'id' = 2, 'lastname' = 'Панферов', 'age' = 19, 'active' = true");
        System.out.println("INSERT2: " + execute2);
        List<Map<String, Object>> execute3 = starter.execute("INSERT VALUES  'id' = 3,'lastname' = 'Лавренов',  'cost' = 100000.0, 'age' = 100, 'active' = false");
        System.out.println("INSERT3: " + execute3);
        List<Map<String, Object>> execute4 = starter.execute("INSERT VALUES  'id' = 4, 'cost' = 1.01, 'age' = 50, 'active' = true");
        System.out.println("INSERT4: " + execute4);
        List<Map<String, Object>> execute5 = starter.execute("INSERT VALUES  'id' = 5");
        System.out.println("INSERT5: " + execute5);
        List<Map<String, Object>> execute6 = starter.execute("select WHERE 'lastname' ilike 'Во%'");
        System.out.println("SELECT6: " + execute6);
        List<Map<String, Object>> execute7 = starter.execute("select WHERE 'lastname' ilike 'Пак%' or 'id' = 4 or 'active' = false");
        System.out.println("SELECT7: " + execute7);
        List<Map<String, Object>> execute8 = starter.execute("select");
        System.out.println("SELECT1: " + execute8);
        List<Map<String, Object>> execute9 = starter.execute("select WHERE 'cost' >= 10000.0");
        System.out.println("SELECT2: " + execute9);
        List<Map<String, Object>> execute10 = starter.execute("UPDATE VALUES 'active' = false, WHERE 'lastname' ilike 'Во%'");
        System.out.println("UPDATE1: " + execute10);
        List<Map<String, Object>> execute11 = starter.execute("UPDATE VALUES 'age' = 50, 'active'= false");
        System.out.println("UPDATE2: " + execute11);
        List<Map<String, Object>> execute12 = starter.execute("delete where 'cost' >= 10000.0");
        System.out.println("DELETE1: " + execute12);
        List<Map<String, Object>> execute13 = starter.execute("delete where 'age'=21");
        System.out.println("DELETE2: " + execute13);
        List<Map<String, Object>> execute14 = starter.execute("delete");
        System.out.println("DELETE3: " + execute14);

    }
}