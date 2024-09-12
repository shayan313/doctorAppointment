package com.sadad.doctorappointment;

import com.sadad.doctorappointment.config.ResourceBundleConfiguration;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@EnableAdminServer
@SpringBootApplication
@Import({
		//	DatabaseConfig.class,
        //Oauth2ResourceServerConfig.class,
		//	ComponentScanConfig.class,
        //MvcConfigurer.class,
        ResourceBundleConfiguration.class,
        //SwaggerConfig.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
