package task3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import task1.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileNameJson = "new_data.json";

        String json = readString(fileNameJson);

        jsonToList(json).forEach(System.out::println);
    }

    private static String readString(String fileName) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return sb.toString();
    }

    private static List<Employee> jsonToList(String json) {
        Type listType = new TypeToken<List<Employee>>(){}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, listType);
    }
}
