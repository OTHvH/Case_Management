package Ouled.Market.Manage.controller;

import Ouled.Market.Manage.model.PhoneCase;
import Ouled.Market.Manage.service.PhoneCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phonecases")
public class PhoneCaseRestController {

    @Autowired
    private PhoneCaseService phoneCaseService;

    // Get all phone cases
    @GetMapping
    public ResponseEntity<List<PhoneCase>> getAllPhoneCases() {
        List<PhoneCase> phoneCases = phoneCaseService.getAllPhoneCases();
        if (phoneCases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(phoneCases);
    }

    // Get a specific phone case by composite key
    @GetMapping("/{model}/{modelNumber}/{caseType}/{color}")
    public ResponseEntity<PhoneCase> getPhoneCase(
            @PathVariable char model,
            @PathVariable String modelNumber,
            @PathVariable Boolean caseType,
            @PathVariable String color) {

        Optional<PhoneCase> phoneCase = phoneCaseService.getPhoneCase(model, modelNumber, caseType, color);
        return phoneCase.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get phone cases by model
    @GetMapping("/model/{model}")
    public ResponseEntity<List<PhoneCase>> getPhoneCasesByModel(@PathVariable char model) {
        List<PhoneCase> phoneCases = phoneCaseService.getPhoneCasesByModel(model);
        if (phoneCases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(phoneCases);
    }

    // Get phone cases by model number
    @GetMapping("/modelNumber/{modelNumber}")
    public ResponseEntity<List<PhoneCase>> getPhoneCasesByModelNumber(@PathVariable String modelNumber) {
        List<PhoneCase> phoneCases = phoneCaseService.getPhoneCasesByModelNumber(modelNumber);
        if (phoneCases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(phoneCases);
    }

    // Get phone cases by case type
    @GetMapping("/caseType/{caseType}")
    public ResponseEntity<List<PhoneCase>> getPhoneCasesByCaseType(@PathVariable Boolean caseType) {
        List<PhoneCase> phoneCases = phoneCaseService.getPhoneCasesByCaseType(caseType);
        if (phoneCases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(phoneCases);
    }

    // Get phone cases by color
    @GetMapping("/color/{color}")
    public ResponseEntity<List<PhoneCase>> getPhoneCasesByColor(@PathVariable String color) {
        List<PhoneCase> phoneCases = phoneCaseService.getPhoneCasesByColor(color);
        if (phoneCases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(phoneCases);
    }

    // Create a new phone case
    @PostMapping
    public ResponseEntity<PhoneCase> createPhoneCase(@RequestBody PhoneCase phoneCase) {
        PhoneCase savedPhoneCase = phoneCaseService.savePhoneCase(phoneCase);
        return new ResponseEntity<>(savedPhoneCase, HttpStatus.CREATED);
    }

    // Update an existing phone case
    @PutMapping
    public ResponseEntity<PhoneCase> updatePhoneCase(@RequestBody PhoneCase phoneCase) {
        Optional<PhoneCase> existingPhoneCase = phoneCaseService.getPhoneCase(
                phoneCase.getModel(),
                phoneCase.getModelNumber(),
                phoneCase.getCaseType(),
                phoneCase.getColor());

        if (existingPhoneCase.isPresent()) {
            PhoneCase updatedPhoneCase = phoneCaseService.savePhoneCase(phoneCase);
            return ResponseEntity.ok(updatedPhoneCase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update the quantity of a specific phone case
    @PatchMapping("/{model}/{modelNumber}/{caseType}/{color}")
    public ResponseEntity<PhoneCase> updateQuantity(
            @PathVariable char model,
            @PathVariable String modelNumber,
            @PathVariable Boolean caseType,
            @PathVariable String color,
            @RequestParam int quantity) {

        PhoneCase updatedPhoneCase = phoneCaseService.updateQuantity(model, modelNumber, caseType, color, quantity);
        if (updatedPhoneCase != null) {
            return ResponseEntity.ok(updatedPhoneCase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a phone case
    @DeleteMapping("/{model}/{modelNumber}/{caseType}/{color}")
    public ResponseEntity<Void> deletePhoneCase(
            @PathVariable char model,
            @PathVariable String modelNumber,
            @PathVariable Boolean caseType,
            @PathVariable String color) {

        Optional<PhoneCase> existingPhoneCase = phoneCaseService.getPhoneCase(model, modelNumber, caseType, color);
        if (existingPhoneCase.isPresent()) {
            phoneCaseService.deletePhoneCase(model, modelNumber, caseType, color);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}