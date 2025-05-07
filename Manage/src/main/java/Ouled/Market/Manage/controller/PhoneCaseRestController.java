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
    
    @GetMapping
    public List<PhoneCase> getAllPhoneCases() {
        return phoneCaseService.getAllPhoneCases();
    }
    
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
    
    @GetMapping("/model/{model}")
    public List<PhoneCase> getPhoneCasesByModel(@PathVariable char model) {
        return phoneCaseService.getPhoneCasesByModel(model);
    }
    
    @GetMapping("/modelNumber/{modelNumber}")
    public List<PhoneCase> getPhoneCasesByModelNumber(@PathVariable String modelNumber) {
        return phoneCaseService.getPhoneCasesByModelNumber(modelNumber);
    }
    
    @GetMapping("/caseType/{caseType}")
    public List<PhoneCase> getPhoneCasesByCaseType(@PathVariable Boolean caseType) {
        return phoneCaseService.getPhoneCasesByCaseType(caseType);
    }
    
    @GetMapping("/color/{color}")
    public List<PhoneCase> getPhoneCasesByColor(@PathVariable String color) {
        return phoneCaseService.getPhoneCasesByColor(color);
    }
    
    @PostMapping
    public ResponseEntity<PhoneCase> createPhoneCase(@RequestBody PhoneCase phoneCase) {
        PhoneCase savedPhoneCase = phoneCaseService.savePhoneCase(phoneCase);
        return new ResponseEntity<>(savedPhoneCase, HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<PhoneCase> updatePhoneCase(@RequestBody PhoneCase phoneCase) {
        Optional<PhoneCase> existingPhoneCase = phoneCaseService.getPhoneCase(
                phoneCase.getModel(), 
                phoneCase.getModelNumber(), 
                phoneCase.getCaseType(), 
                phoneCase.getColor());
        
        if (existingPhoneCase.isPresent()) {
            return ResponseEntity.ok(phoneCaseService.savePhoneCase(phoneCase));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
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