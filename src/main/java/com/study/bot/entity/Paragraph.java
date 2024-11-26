package com.study.bot.entity;

import com.study.bot.dto.ImageToParagraphDto.ImageToParagraphDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"paragraph\"")
@Getter
@Setter
public class Paragraph {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "image_to_paragraph")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<ImageToParagraphDto> imageToParagraphs = new ArrayList<>();;

    @Column(name = "paragraph_name")
    private String paragraphName;

    @Column(name = "max_test_mark")
    private int maxTestMark;

    @Column(name = "curr_test_mark")
    private int currTestMark;


}
