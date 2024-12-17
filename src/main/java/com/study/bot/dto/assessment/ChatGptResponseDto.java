package com.study.bot.dto.assessment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatGptResponseDto {

    private String id;

    private String object;

    private long created;

    private String model;

    private List<Choice> choices;

    private Usage usage;

    private SystemFingerprint system_fingerprint;

    @Data
    @AllArgsConstructor
    public static class Choice {

        private int index;

        private Message message;

        private Object logprobs;

        private String finish_reason;

        @Data
        @AllArgsConstructor
        public static class Message {

            private String role;

            private String content;

            private Object refusal;
        }
    }

    @Data
    @AllArgsConstructor
    public static class Usage {

        private int prompt_tokens;

        private int completion_tokens;

        private int total_tokens;

        private TokenDetails prompt_tokens_details;

        private TokenDetails completion_tokens_details;

        @Data
        @AllArgsConstructor
        public static class TokenDetails {

            private int cached_tokens;

            private int audio_tokens;

            private int accepted_prediction_tokens;

            private int rejected_prediction_tokens;
        }
    }
    @Data
    @AllArgsConstructor
    public static class SystemFingerprint {

        private String fp_39a40c96a0;

    }
}
