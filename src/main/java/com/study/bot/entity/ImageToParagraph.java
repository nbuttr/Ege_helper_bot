package com.study.bot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "\"image_to_paragraph\"")
@Getter
@Setter
public class ImageToParagraph {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "paragraph_id", nullable = false)
    private Paragraph paragraph;

    @OneToOne    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "ordinal_number")
    private int ordinalNumber;
}
