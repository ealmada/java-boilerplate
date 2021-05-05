package com.asapp.backend.challenge.resources;

import lombok.Data;

@Data
public class GetMessageResourceRequestDTO {
    private Long recipient;
    private Long start;
    private Long limit;
}
