package com.enessefacetin.interviewnexus.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "industry")
@EqualsAndHashCode(callSuper = false)
public class Industry extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "industries", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Company> companies;
}
