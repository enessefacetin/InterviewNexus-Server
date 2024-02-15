package com.enessefacetin.interviewnexus.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profession")
@EqualsAndHashCode(callSuper = false)
public class Profession extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "profession", cascade = CascadeType.ALL)
    private List<Interview> interviews;

    @ManyToMany(mappedBy = "professions")
    private List<Company> companies;
}
