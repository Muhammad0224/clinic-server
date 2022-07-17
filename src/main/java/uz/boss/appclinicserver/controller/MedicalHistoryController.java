package uz.boss.appclinicserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.boss.appclinicserver.service.MedicalHistoryService;
import uz.boss.appclinicserver.utils.Constants;

/**
 * Author: Muhammad
 * Date: 10.07.2022
 * Time: 13:51
 */
@RestController
@RequestMapping(Constants.BASE_PATH + "/medical-history")
@RequiredArgsConstructor
public class MedicalHistoryController {
    private final MedicalHistoryService medicalHistoryService;


}
