package com.scalux.stockManagement.entities;

import com.scalux.stockManagement.enums.PartnerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String ice;

    private String rib;

    private String address;

    @Enumerated(EnumType.STRING)
    private PartnerType partnerType;
}
