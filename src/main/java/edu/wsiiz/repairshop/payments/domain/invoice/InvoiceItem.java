package edu.wsiiz.repairshop.payments.domain.invoice;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double netPrice;      // cena netto

    private double vatRate = 0.08;  // domyślnie 8% VAT – dla usług z montażem

    @ManyToOne(optional = false)
    private Invoice invoice;

    public InvoiceItem(String description, double netPrice) {
        this.description = description;
        this.netPrice = netPrice;
    }

    public double getGrossPrice() {
        return roundToTwoDecimals(netPrice * (1 + vatRate));
    }

    public double getVatAmount() {
        return roundToTwoDecimals(netPrice * vatRate);
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
