package com.example.xmlvalidator.validation;

import com.example.xmlvalidator.errors.StorageFileNotFoundException;
import com.example.xmlvalidator.errors.ValidationFailedException;
import com.example.xmlvalidator.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ValidateXMLSchemaController {

    private final StorageService storageService;

    @Autowired
    ValidateXMLSchemaService validateXMLSchemaService;

    @Autowired
    public ValidateXMLSchemaController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        return "validateForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("filename", file.getOriginalFilename());

        return "redirect:/";
    }

    @PostMapping("/validate")
    public String validateXMLSchema(@RequestParam("filename") String filename, RedirectAttributes redirectAttributes){
        String message;
        try {
            validateXMLSchemaService.validateXMLSchema("/2020v3.0/Extensions/2350/Return2350.xsd", filename);
            message = "Success";
            redirectAttributes.addFlashAttribute("success", true);
        } catch (ValidationFailedException e) {
            redirectAttributes.addFlashAttribute("success", false);
            message = "Exception: " + e.getMessage();
        }
        redirectAttributes.addFlashAttribute("result", message);
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}