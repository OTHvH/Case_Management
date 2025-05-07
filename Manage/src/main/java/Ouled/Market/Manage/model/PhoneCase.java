package Ouled.Market.Manage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.util.*;

@Entity
@IdClass(PhoneCaseId.class)
public class PhoneCase {

    @Id
    private char model;

    @Id
    private String modelNumber;

    @Id
    private Boolean caseType;

    @Id
    private String color;

    private int quantity;

    // Constructors, getters, and setters
    public PhoneCase() {}

    public PhoneCase(char model, String modelNumber, Boolean caseType, String color, int quantity) {
        this.model = model;
        this.modelNumber = modelNumber;
        this.caseType = caseType;
        this.color = color;
        this.quantity = quantity;
    }

    public char getModel() {
        return model;
    }

    public void setModel(char model) {
        this.model = model;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public Boolean getCaseType() {
        return caseType;
    }

    public void setCaseType(Boolean caseType) {
        this.caseType = caseType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    public String getModelName() {
        switch(model) {
            case ' ': return "--------";
            case 'I': return "iPhone";
            case 'S': return "Samsung";
            case 'O': return "Oppo";
            case 'R': return "Redmi";
            case 'H': return "Huawei";
            case 'E': return "Realmi";
            default: return "Unknown";
        }
    }
    
    public String getCaseTypeName() {
        return caseType ? "One-sided" : "Double-sided";
    }

    public static Map<Character, String> getAllBrands() {
        return Map.of(
            'I', "iPhone",
            'S', "Samsung",
            'O', "Oppo",
            'R', "Redmi",
            'H', "Huawei",
            'E', "Realmi"
        );
    }

}