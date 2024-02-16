package com.enessefacetin.interviewnexus.model.entity;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "company")
@EqualsAndHashCode(callSuper = false)
public class Company extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Interview> interviews;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "company_profession",
        joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "profession_id", referencedColumnName = "id")
    )
    private List<Profession> professions;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "company_industry",
        joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "industry_id", referencedColumnName = "id")
    )
    private List<Industry> industries;
}