package ru.spbu.distolymp.controller.admin.models;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spbu.distolymp.dto.entity.answers.AnswerDto;
import ru.spbu.distolymp.service.admin.models.api.ModelService;
import javax.validation.Valid;

/**
 * @author Vladislav Konovalov
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/p-model")
public class BarsicController {
    private static final String ROOT_DIR = "../../resources/data/models/p-model/";
    private static final String PROBLEM_TEXT = ROOT_DIR + "text";
    private static final String PARAM = ROOT_DIR + "param";
    private static final String RESULT_FORM = ROOT_DIR + "result";
    private final ModelService modelService;

    @GetMapping("/param")
    public String getProblemParam() {
        return PARAM;
    }

    @GetMapping("/text")
    public String getProblemText() {
        return PROBLEM_TEXT;
    }

    @GetMapping("/res")
    public String getResultForm(ModelMap modelMap) {
        modelService.fillShowResultFormModelMap(modelMap);
        return RESULT_FORM;
    }

    @PostMapping("/submit-answer")
    public String submitAnswer(@Valid @ModelAttribute("answer") AnswerDto answerDto,
                               BindingResult br, ModelMap modelMap) {
        modelService.fillShowResultModelMap(answerDto, modelMap);
        return RESULT_FORM;
    }
}
