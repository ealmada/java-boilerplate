package com.asapp.backend.challenge.resources;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResource {

    public String username;
    public String token;

}
