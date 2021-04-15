package com.example.xmlvalidator.validation;

import com.example.xmlvalidator.error.errors.StorageFileNotFoundException;
import com.example.xmlvalidator.error.errors.ValidationFailedException;
import com.example.xmlvalidator.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

@Service
public class ValidateXMLSchemaService {

    @Autowired
    StorageService storageService;

    public void validateXMLSchema(String xsdPath, String filename) throws IOException {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(new ClassPathResource(xsdPath).getInputStream()));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(storageService.loadAsResource(filename).getInputStream()));
        } catch (IOException | StorageFileNotFoundException e) {
            throw new IOException("Unable to validate the XML input. Empty or unreadable XML input.");
        } catch (SAXException e) {
            throw new ValidationFailedException(e.getMessage());
        }
    }
}