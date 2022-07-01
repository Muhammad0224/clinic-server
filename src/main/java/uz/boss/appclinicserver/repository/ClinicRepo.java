package uz.boss.appclinicserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.boss.appclinicserver.entity.Clinic;

import java.util.UUID;

/**
 * Author: Muhammad
 * Date: 30.06.2022
 * Time: 14:20
 */
@Repository
public interface ClinicRepo extends JpaRepository<Clinic, UUID> {
}
