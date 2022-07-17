package uz.boss.appclinicserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.boss.appclinicserver.entity.MedicalHistory;

import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 10.07.2022
 * Time: 13:58
 */
@Repository
public interface MedicalHistoryRepo extends JpaRepository<MedicalHistory, UUID> {
}
