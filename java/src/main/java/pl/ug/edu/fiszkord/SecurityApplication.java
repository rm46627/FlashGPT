package pl.ug.edu.fiszkord;

import pl.ug.edu.fiszkord.auth.AuthenticationService;
import pl.ug.edu.fiszkord.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static pl.ug.edu.fiszkord.users.Role.*;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			/*var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("manager@mail.com")
					.password("password")
					.role(MANAGER)
					.build();
//			System.out.println("Manager token: " + service.register(manager).getAccessToken());

			var user = RegisterRequest.builder()
					.firstname("user")
					.lastname("user")
					.email("user@mail.com")
					.password("password")
					.role(USER)
					.build();
			System.out.println("User token: " + service.register(user).getAccessToken());*/

		};
	}
}
