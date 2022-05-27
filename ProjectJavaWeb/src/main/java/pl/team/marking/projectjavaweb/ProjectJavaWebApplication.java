package pl.team.marking.projectjavaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.team.marking.projectjavaweb.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class ProjectJavaWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectJavaWebApplication.class, args);
	}

}
