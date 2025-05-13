package com.example.project_1.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class HibernateConfigUpdater {

    public static void main(String[] args) {
        HibernateConfigUpdater.updateConfig("com.example.project_1.dataModels.Basedata.NewUser");
    }

    public static void updateConfig(String newClass) {
        // List of new classes to add to hibernate.cfg.xml
        // List<String> newClasses = List.of(
        //     "com.example.project_1.dataModels.Basedata.NewUser",
        //     "com.example.project_1.dataModels.Basedata.NewCompany"
        // );
        
        try {
            // Path to your hibernate.cfg.xml file
            File xmlFile = new File("src/main/resources/dataBase/hibernate.cfg.xml");

            // Parse the XML file into a Document object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            // Normalize the XML document to avoid any issues with formatting
            document.getDocumentElement().normalize();

            // Find the <session-factory> element
            Node sessionFactoryNode = document.getElementsByTagName("session-factory").item(0);


            Element newMapping = document.createElement("mapping");
            newMapping.setAttribute("class", newClass);
            sessionFactoryNode.appendChild(newMapping); // Append the new mapping element
        

            // Write the changes back to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("src/main/resources/dataBase/hibernate.cfg.xml"));
            transformer.transform(source, result);

            System.out.println("New class mappings added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
