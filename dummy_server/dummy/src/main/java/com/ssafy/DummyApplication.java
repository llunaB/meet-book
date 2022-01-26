package com.ssafy;

import com.ssafy.db.openApi.OpenApiHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class DummyApplication {

	public static void main(String[] args) throws Exception {
//		SpringApplication.run(DummyApplication.class, args);
		OpenApiHelper openApiHelper = new OpenApiHelper();
		openApiHelper.ApiToJSONObject();
	}

}
