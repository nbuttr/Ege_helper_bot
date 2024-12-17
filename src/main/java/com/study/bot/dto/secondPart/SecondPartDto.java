package com.study.bot.dto.secondPart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SecondPartDto {

    private UUID id;

    private String imageUrl;

}
