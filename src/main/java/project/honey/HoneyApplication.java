package project.honey;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class HoneyApplication {

	private final HttpSession session;


	public static void main(String[] args) {
		SpringApplication.run(HoneyApplication.class, args);
	}

	//생성자 수정자 자동화
	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of((String) session.getAttribute("user"));
	}
}
