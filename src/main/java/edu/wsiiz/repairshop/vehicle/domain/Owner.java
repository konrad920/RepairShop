package edu.wsiiz.repairshop.vehicle.domain;

import edu.wsiiz.repairshop.vehicle.domain.OwnerName;
import java.util.UUID;

public class Owner {
    private final UUID id;
    private final OwnerName name;
    private final boolean isPrimary;

    public Owner(UUID id, OwnerName name, boolean isPrimary) {
        this.id = id;
        this.name = name;
        this.isPrimary = isPrimary;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public OwnerName name() {
        return name;
    }
}
