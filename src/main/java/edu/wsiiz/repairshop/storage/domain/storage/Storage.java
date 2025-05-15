    package edu.wsiiz.repairshop.storage.domain.storage;

    import jakarta.persistence.*;
    import lombok.EqualsAndHashCode;
    import lombok.Getter;
    import lombok.Setter;

    import java.util.List;

    @Entity
    @EqualsAndHashCode(of = "id")
    @Getter
    @Setter
    public class Storage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String location;

        @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
        private List<InventoryItem> inventoryItems;
    }