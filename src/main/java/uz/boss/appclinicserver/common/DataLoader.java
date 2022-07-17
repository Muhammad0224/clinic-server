package uz.boss.appclinicserver.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.boss.appclinicserver.entity.Role;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.enums.Permission;
import uz.boss.appclinicserver.enums.RoleType;
import uz.boss.appclinicserver.repository.RoleRepo;
import uz.boss.appclinicserver.repository.UserRepo;

import java.util.Set;

/**
 * @author Murtazayev Muhammad
 * @since 25.12.2021
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")){
            Role role = new Role();
            role.setType(RoleType.SYSTEM_ADMIN);
            role.setPermissions(Set.of(Permission.values()));
            role.setName("SYSTEM ADMIN");
            roleRepo.save(role);

            Role role1 = new Role();
            role.setType(RoleType.CUSTOM);
            role.setPermissions(Set.of(Permission.values()));
            role.setName("DOKTOR");
            roleRepo.save(role1);

            User user = new User();
            user.setUsername("muhammad0224");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRole(role);
            userRepo.save(user);
        }
    }
}
