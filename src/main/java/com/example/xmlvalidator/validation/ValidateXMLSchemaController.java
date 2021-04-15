package com.example.xmlvalidator.validation;

import com.example.xmlvalidator.error.errors.ValidationFailedException;
import com.example.xmlvalidator.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String validateForm() {
        return "validateForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Nothing to upload...");
        } else {
            storageService.store(file);
            redirectAttributes.addFlashAttribute("filename", file.getOriginalFilename());
        }

        return "redirect:/";
    }

    @PostMapping("/validate")
    public String validateXMLSchema(@RequestParam("filename") String filename, RedirectAttributes redirectAttributes){
        String message;
        try {
            validateXMLSchemaService.validateXMLSchema("schema.xsd", filename);
            redirectAttributes.addFlashAttribute("success", true);
            message = "The XMl document is valid";
        } catch (IOException | ValidationFailedException e) {
            redirectAttributes.addFlashAttribute("success", false);
            message = "Exception: " + e.getMessage();
        }
        redirectAttributes.addFlashAttribute("result", message);
        return "redirect:/";
    }
}