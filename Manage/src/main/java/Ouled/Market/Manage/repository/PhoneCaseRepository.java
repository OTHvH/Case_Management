package Ouled.Market.Manage.repository;

import Ouled.Market.Manage.model.PhoneCase;
import Ouled.Market.Manage.model.PhoneCaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneCaseRepository extends JpaRepository<PhoneCase, PhoneCaseId> {

    // Find phone cases by model
    List<PhoneCase> findByModel(char model);

    // Find phone cases by model number
    List<PhoneCase> findByModelNumber(String modelNumber);

    // Find phone cases by case type
    List<PhoneCase> findByCaseType(Boolean caseType);

    // Find phone cases by color
    List<PhoneCase> findByColor(String color);

    // Find a specific phone case by composite key fields
    List<PhoneCase> findByModelAndModelNumberAndCaseTypeAndColor(
            char model,
            String modelNumber,
            Boolean caseType,
            String color
    );
}