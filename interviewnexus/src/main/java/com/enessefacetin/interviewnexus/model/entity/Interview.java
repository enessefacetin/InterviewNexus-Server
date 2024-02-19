package com.enessefacetin.interviewnexus.model.entity;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "interview")
@EqualsAndHashCode(callSuper = false)
public class Interview extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "profession_id", referencedColumnName = "id")
    private Profession profession;

    @Enumerated(EnumType.STRING)
    private InterviewType type;

    @Min(0)
    @Max(5)
    private int score;

    @Nullable
    private String opinion;

    private LocalDate interviewDate;

    @Enumerated(EnumType.STRING)
    private Status interviewStatus;

    @OneToMany(mappedBy = "interview", cascade = CascadeType.ALL)
    private List<Question> questions;

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        this.interviewStatus = Status.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        super.onUpdate();
        var isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Admin"));
        if (!isAdmin) {
            this.interviewStatus = Status.PENDING;
        }
    }

}
