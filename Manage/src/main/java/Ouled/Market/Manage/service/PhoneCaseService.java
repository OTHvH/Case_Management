package Ouled.Market.Manage.service;

import Ouled.Market.Manage.model.PhoneCase;
import Ouled.Market.Manage.model.PhoneCaseId;
import Ouled.Market.Manage.repository.PhoneCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneCaseService {
    
    @Autowired
    private PhoneCaseRepository phoneCaseRepository;
    
    public List<PhoneCase> getAllPhoneCases() {
        return phoneCaseRepository.findAll();
    }
    
    public Optional<PhoneCase> getPhoneCase(char model, String modelNumber, Boolean caseType, String color) {
        PhoneCaseId id = new PhoneCaseId(model, modelNumber, caseType, color);
        return phoneCaseRepository.findById(id);
    }
    
    public List<PhoneCase> getPhoneCasesByModel(char model) {
        return phoneCaseRepository.findByModel(model);
    }
    
    public List<PhoneCase> getPhoneCasesByModelNumber(String modelNumber) {
        return phoneCaseRepository.findByModelNumber(modelNumber);
    }
    
    public List<PhoneCase> getPhoneCasesByCaseType(Boolean caseType) {
        return phoneCaseRepository.findByCaseType(caseType);
    }
    
    public List<PhoneCase> getPhoneCasesByColor(String color) {
        return phoneCaseRepository.findByColor(color);
    }
    
    public PhoneCase savePhoneCase(PhoneCase phoneCase) {
        return phoneCaseRepository.save(phoneCase);
    }
    
    public void deletePhoneCase(char model, String modelNumber, Boolean caseType, String color) {
        PhoneCaseId id = new PhoneCaseId(model, modelNumber, caseType, color);
        phoneCaseRepository.deleteById(id);
    }
    
    public PhoneCase updateQuantity(char model, String modelNumber, Boolean caseType, String color, int newQuantity) {
        PhoneCaseId id = new PhoneCaseId(model, modelNumber, caseType, color);
        Optional<PhoneCase> phoneCaseOptional = phoneCaseRepository.findById(id);
        
        if (phoneCaseOptional.isPresent()) {
            PhoneCase phoneCase = phoneCaseOptional.get();
            phoneCase.setQuantity(newQuantity);
            return phoneCaseRepository.save(phoneCase);
        }
        return null;
    }
}