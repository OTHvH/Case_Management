//package Ouled.Market.Manage.controller;
//
//import Ouled.Market.Manage.model.PhoneCase;
//import Ouled.Market.Manage.service.PhoneCaseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/phonecases")
//public class PhoneCaseWebController {
//    
//    @Autowired
//    private PhoneCaseService phoneCaseService;
//    
//    @GetMapping
//    public String listPhoneCases(Model model) {
//        model.addAttribute("phoneCases", phoneCaseService.getAllPhoneCases());
//        return "phonecases/list";
//    }
//    
//    @GetMapping("/add")
//    public String showAddForm(Model model) {
//        model.addAttribute("phoneCase", new PhoneCase());
//        return "phonecases/add";
//    }
//    
//    @PostMapping("/add")
//    public String addPhoneCase(@ModelAttribute PhoneCase phoneCase) {
//        phoneCaseService.savePhoneCase(phoneCase);
//        return "redirect:/phonecases";
//    }
//    
//    @GetMapping("/edit/{model}/{modelNumber}/{caseType}/{color}")
//    public String showEditForm(
//            @PathVariable char model, 
//            @PathVariable String modelNumber, 
//            @PathVariable Boolean caseType, 
//            @PathVariable String color,
//            Model uiModel) {
//        
//        Optional<PhoneCase> phoneCase = phoneCaseService.getPhoneCase(model, modelNumber, caseType, color);
//        if (phoneCase.isPresent()) {
//            uiModel.addAttribute("phoneCase", phoneCase.get());
//            return "phonecases/edit";
//        } else {
//            return "redirect:/phonecases";
//        }
//    }
//    
//    @PostMapping("/edit")
//    public String updatePhoneCase(@ModelAttribute PhoneCase phoneCase) {
//        phoneCaseService.savePhoneCase(phoneCase);
//        return "redirect:/phonecases";
//    }
//    
//    @GetMapping("/delete/{model}/{modelNumber}/{caseType}/{color}")
//    public String deletePhoneCase(
//            @PathVariable char model, 
//            @PathVariable String modelNumber, 
//            @PathVariable Boolean caseType, 
//            @PathVariable String color) {
//        
//        phoneCaseService.deletePhoneCase(model, modelNumber, caseType, color);
//        return "redirect:/phonecases";
//    }
//    
//    @GetMapping("/filter")
//    public String filterPhoneCases(
//            @RequestParam(required = false) Character model,
//            @RequestParam(required = false) String modelNumber,
//            @RequestParam(required = false) Boolean caseType,
//            @RequestParam(required = false) String color,
//            Model uiModel) {
//        
//        if (model != null) {
//            uiModel.addAttribute("phoneCases", phoneCaseService.getPhoneCasesByModel(model));
//        } else if (modelNumber != null && !modelNumber.isEmpty()) {
//            uiModel.addAttribute("phoneCases", phoneCaseService.getPhoneCasesByModelNumber(modelNumber));
//        } else if (caseType != null) {
//            uiModel.addAttribute("phoneCases", phoneCaseService.getPhoneCasesByCaseType(caseType));
//        } else if (color != null && !color.isEmpty()) {
//            uiModel.addAttribute("phoneCases", phoneCaseService.getPhoneCasesByColor(color));
//        } else {
//            uiModel.addAttribute("phoneCases", phoneCaseService.getAllPhoneCases());
//        }
//        
//        return "phonecases/list";
//    }
//}
//