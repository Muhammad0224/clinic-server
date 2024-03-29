package uz.boss.appclinicserver.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.boss.appclinicserver.entity.Patient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 05.07.2022
 * Time: 22:49
 */
@Repository
public interface PatientRepo extends JpaRepository<Patient, UUID> {
    List<Patient> findAllByClinicId(UUID clinicId, Sort sort);

    @Query("select p from patients p where p.uniqueCode like concat(?1, '%')")
    List<Patient> findAllByUniqueCodeStartsWith(String uniqueCode);

    Optional<Patient> findByUniqueCode(String uniqueCode);

    boolean existsByPnfl(String pnfl);

    boolean existsByPnflAndIdNot(String pnfl, UUID id);

    boolean existsByUniqueCode(String uniqueCode);
}
