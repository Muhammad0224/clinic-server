package uz.boss.appclinicserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

/**
 * @author Osiyo Adilova
 * @project app-eticket-server
 * @since 12/16/2021
 */

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
    @Bean
    AuditorAware<UUID> auditorAware(){
        return new AuditorAwareImpl();
    }
}
