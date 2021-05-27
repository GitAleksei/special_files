package task2;

import org.w3c.dom.*;
import task1.Employee;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "data.xml";
        String fileNameJson = "data2.json";
        List<Employee> employees = parseXML(fileName);

        String json = task1.Main.listToJson(employees);
        task1.Main.writeString(json, fileNameJson);
    }

    private static List<Employee> parseXML(String fileName) {
        List<Employee> employeeList = new ArrayList<>();
        long id = 0;
        String firstName = "";
        String lastName = "";
        String country = "";
        int age = 0;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));

            Node root = doc.getDocumentElement();

            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeName().equals("employee")) {
                    NodeList fieldList = node.getChildNodes();
                    for (int j = 0; j < fieldList.getLength(); j++) {
                        Node node_ = fieldList.item(j);

                        switch (node_.getNodeName()) {
                            case "id":
                                id = Long.parseLong(node_.getTextContent());
                                break;
                            case "firstName":
                                firstName = node_.getTextContent();
                                break;
                            case "lastName":
                                lastName = node_.getTextContent();
                                break;
                            case "country":
                                country = node_.getTextContent();
                                break;
                            case "age":
                                age = Integer.parseInt(node_.getTextContent());
                                break;
                        }
                    }

                    employeeList.add(new Employee(id, firstName, lastName, country, age));
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return employeeList;
    }
}
