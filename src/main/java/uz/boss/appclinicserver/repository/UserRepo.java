package uz.boss.appclinicserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.boss.appclinicserver.entity.User;
import uz.boss.appclinicserver.enums.RoleType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    List<User> findAllByRole_Type(RoleType role_type);

    List<User> findAllByClinicId(UUID clinicId);

    Optional<User> findByUsernameAndEnabledTrue(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, UUID id);
}
