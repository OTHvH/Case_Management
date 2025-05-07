package Ouled.Market.Manage.controller;

import Ouled.Market.Manage.model.PhoneCase;
import Ouled.Market.Manage.service.PhoneCaseService;

import java.util.List;
import java.util.stream.Collectors;

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
     @GetMapping({"/list","/"})
     public String listPhoneCases(Model model) {
         model.addAttribute("phoneCases", phoneCaseService.getAllPhoneCases());
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
        @PathVariable char model,
        @PathVariable String modelNumber,
        @PathVariable Boolean caseType,
        @PathVariable String color,
        Model modelView
    ) {
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
    @GetMapping("/delete/{model}/{modelNumber}/{caseType}/{color}")
    public String deletePhoneCase(
        @PathVariable char model,
        @PathVariable String modelNumber,
        @PathVariable Boolean caseType,
        @PathVariable String color
    ) {
        phoneCaseService.deletePhoneCase(model, modelNumber, caseType, color);
        return "redirect:/phonecases/list";
    }


    @GetMapping("/filter")
    public String filterPhoneCases(
        @RequestParam(required = false) String model,
        @RequestParam(required = false) String modelNumber,
        @RequestParam(required = false) String caseType,
        @RequestParam(required = false) String color,
        Model modelView) {
        
        List<PhoneCase> filteredCases = phoneCaseService.getAllPhoneCases();
    
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
            Boolean caseTypeBool = caseType.equalsIgnoreCase("true");
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
    
        modelView.addAttribute("phoneCases", filteredCases);
        return "phonecases/list";
    }

}