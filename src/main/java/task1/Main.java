package task1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";
        String fileNameJson = "data.json";

        List<Employee> employees = parseCSV(columnMapping, fileName);
        String json = listToJson(employees);
        writeString(json, fileNameJson);
    }


    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> result = null;

        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy =
                    new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();

            result = csv.parse();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return result;
    }

    public static String listToJson(List<Employee> employees) {
        Type listType = new TypeToken<List<Employee>>(){}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setPrettyPrinting()
                .create();
        return gson.toJson(employees, listType);
    }

    public static void writeString(String json, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(json);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
