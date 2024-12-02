package com.study.bot.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"section\"")
@Getter
@Setter
public class Section {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "section_name")
    private String sectionName;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "paragraph")
    private List<Paragraph> paragraphs = new ArrayList<>();
}
