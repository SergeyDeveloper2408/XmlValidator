package com.example.xmlvalidator;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

@Service
public class ValidateXMLSchemaService {

    public static boolean validateXMLSchema(String xsdPath, String xmlPath){

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new ClassPathResource(xsdPath).getFile());
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ClassPathResource(xmlPath).getFile()));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }

}