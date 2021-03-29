package com.example.xmlvalidator.validation;

import com.example.xmlvalidator.errors.ValidationFailedException;
import com.example.xmlvalidator.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    StorageService storageService;

    public void validateXMLSchema(String xsdPath, String filename){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new ClassPathResource(xsdPath).getInputStream()));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(storageService.loadAsResource(filename).getInputStream()));
        } catch (IOException | SAXException e) {
            throw new ValidationFailedException(e.getMessage());
        }
    }
}