package com.enessefacetin.interviewnexus.model.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "question")
@EqualsAndHashCode(callSuper = false)
public class Question extends BaseEntity {

    private String content;

    @Nullable
    private String answer;

    @Enumerated(EnumType.STRING)
    private Status questionStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "interview_id", referencedColumnName = "id")
    private Interview interview;


    @PrePersist
    protected void onCreate() {
        super.onCreate();
        this.questionStatus = Status.PENDING;
    }
}
