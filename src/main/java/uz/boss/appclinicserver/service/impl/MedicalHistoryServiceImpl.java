package uz.boss.appclinicserver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.boss.appclinicserver.repository.MedicalHistoryRepo;
import uz.boss.appclinicserver.service.MedicalHistoryService;

/**
 * Author: Muhammad
 * Date: 10.07.2022
 * Time: 13:57
 */
@Service
@RequiredArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {
    private final MedicalHistoryRepo medicalHistoryRepo;
}
