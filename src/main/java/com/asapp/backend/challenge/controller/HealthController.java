package com.asapp.backend.challenge.controller;

import com.asapp.backend.challenge.resources.HealthResource;
import com.asapp.backend.challenge.utils.JSONUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class HealthController {

    public static Route check = (Request req, Response rep) -> {
        // TODO: Check service health. Feel free to add any check you consider necessary. The instruction says we can assume it's implemented on the skeleton
        return JSONUtil.dataToJson(new HealthResource());
    };
}
