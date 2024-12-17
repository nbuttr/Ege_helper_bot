package com.study.bot.dto.assessment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatGptRequestDto {

    private String model;

    private List<Messages> messages;

    private int max_tokens;

    @Data
    @AllArgsConstructor
    @Builder
    public static class Messages{
        private String role;

        private List<Content> content;

        @Data
        @AllArgsConstructor
        @Builder
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Content{

            private String type;

            private String text;

            private ImageUrl image_url;

            @Data
            @AllArgsConstructor
            public static class ImageUrl{

                private String url;
            }
        }
    }
}
