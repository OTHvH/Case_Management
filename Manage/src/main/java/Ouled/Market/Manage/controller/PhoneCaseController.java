package Ouled.Market.Manage.controller;

import Ouled.Market.Manage.model.PhoneCase;
import Ouled.Market.Manage.service.PhoneCaseService;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/phonecases")
public class PhoneCaseController {

    private final PhoneCaseService phoneCaseService;

    public PhoneCaseController(PhoneCaseService phoneCaseService) {
        this.phoneCaseService = phoneCaseService;
    }

     // List all phone cases
     @GetMapping("/list")
     public String listPhoneCases(
             @RequestParam(name = "sort", required = false, defaultValue = "model") String sort,
             @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
             Model model) {
     
         List<PhoneCase> phoneCases = phoneCaseService.getAllPhoneCases();
     
         // Sort the list based on the sort and direction parameters
         Comparator<PhoneCase> comparator = switch (sort) {
             case "modelNumber" -> Comparator.comparing(PhoneCase::getModelNumber);
             case "caseType" -> Comparator.comparing(PhoneCase::getCaseType);
             case "color" -> Comparator.comparing(PhoneCase::getColor);
             case "quantity" -> Comparator.comparing(PhoneCase::getQuantity);
             default -> Comparator.comparing(PhoneCase::getModel);
         };
     
         if ("desc".equals(direction)) {
             comparator = comparator.reversed();
         }
     
         phoneCases.sort(comparator);
     
         model.addAttribute("phoneCases", phoneCases);
         model.addAttribute("param", Map.of("sort", sort, "direction", direction));
         return "phonecases/list";
     }

    // Show add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("phoneCase", new PhoneCase());
        return "phonecases/add";
    }

    // Process add form submission
    @PostMapping("/add")
    public String addPhoneCase(@ModelAttribute PhoneCase phoneCase) {
        phoneCaseService.savePhoneCase(phoneCase);
        return "redirect:/phonecases/list";
    }

    // Show edit form
    @GetMapping("/edit/{model}/{modelNumber}/{caseType}/{color}")
    public String showEditForm(
            @PathVariable("model") char model,
            @PathVariable("modelNumber") String modelNumber,
            @PathVariable("caseType") Boolean caseType,
            @PathVariable("color") String color,
            Model modelView) {
            
        PhoneCase phoneCase = phoneCaseService.getPhoneCase(model, modelNumber, caseType, color)
                .orElseThrow(() -> new IllegalArgumentException("Invalid phone case"));
        modelView.addAttribute("phoneCase", phoneCase);
        return "phonecases/edit";
    }
    
    // Process edit form submission
    @PostMapping("/edit")
    public String updatePhoneCase(@ModelAttribute PhoneCase phoneCase) {
        phoneCaseService.savePhoneCase(phoneCase);
        return "redirect:/phonecases/list";
    }

    // Delete phone case
    @DeleteMapping("/delete/{model}/{modelNumber}/{caseType}/{color}")
    public ResponseEntity<Void> deletePhoneCase(
            @PathVariable("model") char model,
            @PathVariable("modelNumber") String modelNumber,
            @PathVariable("caseType") Boolean caseType,
            @PathVariable("color") String color) {

        Optional<PhoneCase> existingPhoneCase = phoneCaseService.getPhoneCase(model, modelNumber, caseType, color);
        if (existingPhoneCase.isPresent()) {
            phoneCaseService.deletePhoneCase(model, modelNumber, caseType, color);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/filter")
    public String filterPhoneCases(
        @RequestParam(name = "model", required = false) String model,
        @RequestParam(name = "modelNumber", required = false) String modelNumber,
        @RequestParam(name = "caseType", required = false) String caseType,
        @RequestParam(name = "color", required = false) String color,
        Model modelView) {
    
        List<PhoneCase> filteredCases = phoneCaseService.getAllPhoneCases();
    
        try {
            // Handle brand filter
            if (model != null && !model.isEmpty() && model.length() == 1) {
                char modelChar = model.charAt(0);
                filteredCases = filteredCases.stream()
                    .filter(c -> c.getModel() == modelChar)
                    .collect(Collectors.toList());
            }
    
            // Handle model number filter
            if (modelNumber != null && !modelNumber.isEmpty()) {
                filteredCases = filteredCases.stream()
                    .filter(c -> c.getModelNumber().equalsIgnoreCase(modelNumber))
                    .collect(Collectors.toList());
            }
    
            // Handle case type filter
            if (caseType != null && !caseType.isEmpty()) {
                Boolean caseTypeBool = Boolean.parseBoolean(caseType);
                filteredCases = filteredCases.stream()
                    .filter(c -> c.getCaseType().equals(caseTypeBool))
                    .collect(Collectors.toList());
            }
    
            // Handle color filter
            if (color != null && !color.isEmpty()) {
                filteredCases = filteredCases.stream()
                    .filter(c -> c.getColor().equalsIgnoreCase(color))
                    .collect(Collectors.toList());
            }
        } catch (Exception e) {
            modelView.addAttribute("error", "Invalid filter parameters.");
            return "phonecases/list";
        }
    
        modelView.addAttribute("phoneCases", filteredCases);
        return "phonecases/list";
    }
}