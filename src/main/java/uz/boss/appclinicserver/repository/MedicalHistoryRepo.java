package uz.boss.appclinicserver.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.boss.appclinicserver.entity.MedicalHistory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 10.07.2022
 * Time: 13:58
 */
@Repository
public interface MedicalHistoryRepo extends JpaRepository<MedicalHistory, UUID> {
    List<MedicalHistory> findAllByPatientId(UUID patientId, Sort sort);

    List<MedicalHistory> findAllByDoctorId(UUID doctorId, Sort sort);

    List<MedicalHistory> findAllByPatientIdAndDateBeforeAndDateAfter(UUID patientId, LocalDateTime to, LocalDateTime from, Sort sort);
}
