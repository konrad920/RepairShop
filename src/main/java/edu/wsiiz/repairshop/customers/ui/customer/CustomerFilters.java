package edu.wsiiz.repairshop.customers.ui.customer;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import edu.wsiiz.repairshop.customers.domain.customer.Address;
import edu.wsiiz.repairshop.customers.domain.customer.Customer;
import edu.wsiiz.repairshop.foundation.ui.view.ListView.Filters;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

public class CustomerFilters extends Filters<Customer> {

    TextField fullName = new TextField(i18n("fullName"));
    //  TextField address = new TextField(i18n("address"));
    TextField pesel = new TextField(i18n("pesel"));
    TextField regon = new TextField(i18n("regon"));
    TextField vehicleRegistrationNumber = new TextField(i18n("vehicleRegistrationNumber"));
    Checkbox status = new Checkbox(i18n("status"), true);

    public CustomerFilters(Runnable onSearch) {
        super(onSearch);
        setupFilters();
    }

    @Override
    protected void setupFilters() {
        status.getStyle().set("margin-top", "auto");
        HorizontalLayout filtersLayout = new HorizontalLayout(fullName, pesel, regon, vehicleRegistrationNumber, status);
        filtersLayout.setWidthFull();

        getContent().add(filtersLayout);
    }

    @Override
    protected void onReset() {

        fullName.clear();
//    address.clear();
        pesel.clear();
        regon.clear();
        vehicleRegistrationNumber.clear();
        status.setValue(true);

        triggerSearch();
    }

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        String fullNameValue = fullName.getValue();
        if (fullNameValue != null && !fullNameValue.trim().isEmpty()) {
            Expression<String> firstNameExp = cb.coalesce(root.get("firstName"), "");
            Expression<String> lastNameExp = cb.coalesce(root.get("lastName"), "");
            Expression<String> fullNameExp = cb.concat(cb.concat(firstNameExp, " "), lastNameExp);

            predicates.add(
                    cb.like(
                            cb.lower(fullNameExp),
                            "%" + fullNameValue.trim().toLowerCase() + "%"
                    )
            );
        }

//    String addressValue = address.getValue();
//    if (addressValue != null && !addressValue.trim().isEmpty()) {
//      Join<Customer, Address> addressJoin = root.join("addresses", JoinType.LEFT);
//
//      String likePattern = "%" + addressValue.trim().toLowerCase() + "%";
//
//      Predicate cityPredicate = cb.like(cb.lower(addressJoin.get("city")), likePattern);
//      Predicate streetPredicate = cb.like(cb.lower(addressJoin.get("street")), likePattern);
//
//      predicates.add(cb.or(cityPredicate, streetPredicate));
//    }

        Boolean statusValue = status.getValue();
        if (statusValue != null) {
            predicates.add(cb.equal(root.get("isActive"), statusValue));
        }

        String vehicleRegistrationNumberValue = vehicleRegistrationNumber.getValue();
        if (vehicleRegistrationNumberValue != null && !vehicleRegistrationNumberValue.trim().isEmpty()) {
            predicates.add(cb.like(root.get("vehicleRegistrationNumber"), "%" + vehicleRegistrationNumberValue.trim() + "%"));
        }

        String peselValue = pesel.getValue();
        if (peselValue != null && !peselValue.trim().isEmpty()) {
            predicates.add(cb.like(root.get("pesel"), "%" + peselValue.trim() + "%"));
        }

        String regonValue = regon.getValue();
        if (regonValue != null && !regonValue.trim().isEmpty()) {
            predicates.add(cb.like(root.get("regon"), "%" + regonValue.trim() + "%"));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}