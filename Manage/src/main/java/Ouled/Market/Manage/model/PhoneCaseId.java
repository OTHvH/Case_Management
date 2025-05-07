package Ouled.Market.Manage.model;

import java.io.Serializable;
import java.util.Objects;

public class PhoneCaseId implements Serializable {
    private char model;
    private String modelNumber;
    private boolean caseType;
    private String color;
    
    public PhoneCaseId() {
    }
    
    public PhoneCaseId(char model, String modelNumber, Boolean caseType, String color) {
        this.model = model;
        this.modelNumber = modelNumber;
        this.caseType = caseType;
        this.color = color;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneCaseId that = (PhoneCaseId) o;
        return model == that.model && 
               Objects.equals(modelNumber, that.modelNumber) && 
               Objects.equals(caseType, that.caseType) && 
               Objects.equals(color, that.color);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(model, modelNumber, caseType, color);
    }
}