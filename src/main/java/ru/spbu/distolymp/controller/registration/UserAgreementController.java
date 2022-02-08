package ru.spbu.distolymp.controller.registration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Daria Usova
 */
@Controller
public class UserAgreementController {

    @GetMapping("/user_agreement")
    public String showUserAgreementPage() {
        return "registration/user_agreement";
    }

    @GetMapping("/user_data_agreement")
    public String showUserDataAgreementPage() {
        return "registration/user_data_agreement";
    }

}
