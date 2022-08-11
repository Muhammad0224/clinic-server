package uz.boss.appclinicserver.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.boss.appclinicserver.entity.Doctor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 04.07.2022
 * Time: 17:44
 */
@Repository
public interface DoctorRepo extends JpaRepository<Doctor, UUID> {
    List<Doctor> findAllByClinicId(UUID clinicId, Sort sort);

    Optional<Doctor> findByUserId(UUID userId);

    boolean existsByPnfl(String pnfl);

    boolean existsByPnflAndIdNot(String pnfl, UUID id);
}
