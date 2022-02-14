package com.ssafy.config;

import io.swagger.models.Swagger;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.spring.web.json.JsonSerializer;

import java.util.Arrays;
import java.util.List;

//@Component
//@Primary
public class CustomBasePathSerialize extends JsonSerializer {

    // this injection is optional, if you don't need to
    // add basePath based on active profile, remove this.
    private final Environment env;

    public CustomBasePathSerialize(List<JacksonModuleRegistrar> modules,
                                   Environment env) {
        super(modules);
        this.env = env;
    }

    @Override
    public Json toJson(Object toSerialize) {
        if (toSerialize instanceof Swagger) {
            Swagger swagger = (Swagger) toSerialize;
            String basePath = "/api";
            swagger.basePath(basePath);
        }
        return super.toJson(toSerialize);
    }
}
