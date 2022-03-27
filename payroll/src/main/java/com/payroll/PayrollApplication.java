
package com.payroll;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payroll.login.repository.RoleRepository;
import com.payroll.login.repository.SequenceRepository;
import com.payroll.login.repository.UserLoginRepository;
import com.payroll.master.repository.MasterRepository;
import com.payroll.payments.repositroys.PaymentsRepository;
import com.payroll.payrolls.repository.PayrollsRepository;
import com.payroll.workingDay.repositorys.WorkingDaysRepository;
import com.payroll.boot.CustomJackson2RepositoryPopulatorFactoryBean;
import com.payroll.department.repository.DepartmentRepository;
import com.payroll.designations.repository.DesignationRepository;
import com.payroll.employee.repository.EmployeeRepository;
import com.payroll.increments.repositorys.IncrementRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.init.AbstractRepositoryPopulatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import lombok.val;

@EnableMongoAuditing
@SpringBootApplication()
@EnableSwagger2

public class PayrollApplication {

	@Autowired
	private Environment env;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";


	public static void main(String[] args) {
		SpringApplication.run(PayrollApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(env.getProperty("cors.allowedOrigins").split(",")).exposedHeaders("**").allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");;
			}
		};
	}

    @Bean
    public AbstractRepositoryPopulatorFactoryBean repositorySequencePopulator(ObjectMapper objectMapper, SequenceRepository sequenceRepository) {
        Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
        sequenceRepository.deleteAll();
		factory.setMapper(objectMapper);
        factory.setResources(new Resource[]{new ClassPathResource("seed-data/sequence-data.json")});
        return factory;
    }

	 @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryUserPopulator(ObjectMapper objectMapper, UserLoginRepository userLoginRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         userLoginRepository.deleteAll();
	 	factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/user-data.json")});
         return factory;
     }

     @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryRolePopulator(ObjectMapper objectMapper, RoleRepository roleRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         roleRepository.deleteAll();
	 	factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/role-data.json")});
         return factory;
     }

     @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryEmployeePopulator(ObjectMapper objectMapper, EmployeeRepository employeeRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         employeeRepository.deleteAll();
	 	 factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/employee-data.json")});
         return factory;
     }

     @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryDepartmentPopulator(ObjectMapper objectMapper, DepartmentRepository departmentRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         departmentRepository.deleteAll();
	 	 factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/department-data.json")});
         return factory;
     }

     @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryDesignationPopulator(ObjectMapper objectMapper, DesignationRepository designationRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         designationRepository.deleteAll();
	 	 factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/designation-data.json")});
         return factory;
     }

     @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryPayrollsPopulator(ObjectMapper objectMapper, PayrollsRepository payrollsRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         payrollsRepository.deleteAll();
	 	 factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/payrolls-data.json")});
         return factory;
     }

     @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryMasterPopulator(ObjectMapper objectMapper, MasterRepository masterRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         masterRepository.deleteAll();
	 	 factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/master-data.json")});
         return factory;
     }

     @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryIncrementPopulator(ObjectMapper objectMapper, IncrementRepository incrementRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         incrementRepository.deleteAll();
	 	 factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/increment-data.json")});
         return factory;
     }

     @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryWorkingPopulator(ObjectMapper objectMapper, WorkingDaysRepository workingDaysRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         workingDaysRepository.deleteAll();
	 	 factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/workingday-data.json")});
         return factory;
     }

     @Bean
     public AbstractRepositoryPopulatorFactoryBean repositoryPaymentsPopulator(ObjectMapper objectMapper, PaymentsRepository paymentsRepository) {
         Jackson2RepositoryPopulatorFactoryBean factory = new CustomJackson2RepositoryPopulatorFactoryBean();
         paymentsRepository.deleteAll();
	 	 factory.setMapper(objectMapper);
         factory.setResources(new Resource[]{new ClassPathResource("seed-data/payments-data.json")});
         return factory;
     }


    // swagger configuration
	private List<Parameter> globalParameterList() {

        val authTokenHeader =
            new ParameterBuilder()
                .name("skip")
                .modelRef(new ModelRef("string"))
                .required(true)
                .parameterType("header")
                .description("Skip check")
                .build();


        return Arrays.asList(authTokenHeader);
      }

    Contact contact = new Contact("", "", "");

    List<VendorExtension> vext = new ArrayList<VendorExtension>();
    ApiInfo apiInfo = new ApiInfo(
            "Payroll System Spring Boot API",
            "",
            "1.0",
            "",
            contact,
            "",
            "",
            vext);

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(
                new SecurityReference("JWT", authorizationScopes));
    }

	@Bean
    public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo)
        .forCodeGeneration(true)
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()))
        .globalOperationParameters(globalParameterList())
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build();
  }


}
