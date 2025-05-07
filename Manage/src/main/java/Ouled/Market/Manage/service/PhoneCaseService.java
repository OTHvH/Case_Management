package Ouled.Market.Manage.service;

import Ouled.Market.Manage.model.PhoneCase;
import Ouled.Market.Manage.model.PhoneCaseId;
import Ouled.Market.Manage.repository.PhoneCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhoneCaseService {

    @Autowired
    private PhoneCaseRepository phoneCaseRepository;

    // Get all phone cases
    public List<PhoneCase> getAllPhoneCases() {
        return phoneCaseRepository.findAll().stream()
                                  .filter(phoneCase -> phoneCase != null)
                                  .collect(Collectors.toList());
    }

    // Get a specific phone case by composite key
    public Optional<PhoneCase> getPhoneCase(char model, String modelNumber, Boolean caseType, String color) {
        PhoneCaseId id = new PhoneCaseId(model, modelNumber, caseType, color);
        return phoneCaseRepository.findById(id);
    }

    // Get phone cases by model
    public List<PhoneCase> getPhoneCasesByModel(char model) {
        return phoneCaseRepository.findByModel(model);
    }

    // Get phone cases by model number
    public List<PhoneCase> getPhoneCasesByModelNumber(String modelNumber) {
        return phoneCaseRepository.findByModelNumber(modelNumber);
    }

    // Get phone cases by case type
    public List<PhoneCase> getPhoneCasesByCaseType(Boolean caseType) {
        return phoneCaseRepository.findByCaseType(caseType);
    }

    // Get phone cases by color
    public List<PhoneCase> getPhoneCasesByColor(String color) {
        return phoneCaseRepository.findByColor(color);
    }

    // Save a phone case
    public PhoneCase savePhoneCase(PhoneCase phoneCase) {
        return phoneCaseRepository.save(phoneCase);
    }

    // Delete a phone case by composite key
    public void deletePhoneCase(char model, String modelNumber, Boolean caseType, String color) {
        PhoneCaseId id = new PhoneCaseId(model, modelNumber, caseType, color);
        phoneCaseRepository.deleteById(id);
    }

    // Update the quantity of a specific phone case
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