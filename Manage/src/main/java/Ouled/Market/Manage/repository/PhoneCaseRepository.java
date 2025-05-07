package Ouled.Market.Manage.repository;

import Ouled.Market.Manage.model.PhoneCase;
import Ouled.Market.Manage.model.PhoneCaseId;
import org.springframework.data.jpa.repository.JpaRepository; // Correct import
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhoneCaseRepository extends JpaRepository<PhoneCase, PhoneCaseId> {
    List<PhoneCase> findByModel(char model);
    List<PhoneCase> findByModelNumber(String modelNumber);
    List<PhoneCase> findByCaseType(Boolean caseType);
    List<PhoneCase> findByColor(String color);
}