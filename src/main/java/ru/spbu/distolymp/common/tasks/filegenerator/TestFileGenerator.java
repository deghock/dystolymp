package ru.spbu.distolymp.common.tasks.filegenerator;

import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import ru.spbu.distolymp.dto.entity.tasks.TestDto;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
public class TestFileGenerator {
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private TestFileGenerator() {}

    public static byte[] generateParamFile(TestDto testDto) {
        return generateParamFile(testDto, new ArrayList<>());
    }

    public static byte[] generateParamFile(TestDto testDto, List<QuestionDto> questions) {
        StringBuilder fileContent = new StringBuilder();

        fileContent.append("<?PHP\r\n")
                .append("$test_name='")
                .append(replace(testDto.getTitle()))
                .append("';\r\n")
                .append("$test_type=1;\r\n")
                .append("$question_order=")
                .append(testDto.isRandomOrder() ? "1" : "0")
                .append(";\r\n")
                .append("$question_skip=")
                .append(testDto.isQuestionSkip() ? "true" : "false")
                .append(";\r\n")
                .append("$question_count_2=")
                .append(testDto.getQuestionsNumber()[0])
                .append(";\r\n")
                .append("$question_count_4=")
                .append(testDto.getQuestionsNumber()[1])
                .append(";\r\n")
                .append("$question_count_6=")
                .append(testDto.getQuestionsNumber()[2])
                .append(";\r\n")
                .append("$question_count_S=0;\r\n")
                .append("$question=array(\r\n");

        int count = 0;
        for (QuestionDto question : questions) {
            count++;
            fileContent.append(count)
                    .append("=>array(\r\n")
                    .append("         0=>'")
                    .append(question.getType().getShortName())
                    .append("',\r\n")
                    .append("         1=>'")
                    .append(replace(question.getText()))
                    .append("',\r\n")
                    .append("         2=>'")
                    .append(question.getImageName())
                    .append("',\r\n")
                    .append("         3=>'',\r\n")
                    .append("         4=>'',\r\n")
                    .append("         5=>")
                    .append(parseDifficulty(question.getDifficulty()))
                    .append(",\r\n")
                    .append("         'answer'=>array(\r\n");
            String[] answers = question.getAnswers();
            String[] trueAnswers = question.getTrueAnswers();
            switch (question.getType()) {
                case S:
                case C:
                    for (int i = 0; i < answers.length; i++) {
                        fileContent.append("                         ")
                                .append(i + 1)
                                .append("=>'")
                                .append(replace(answers[i]))
                                .append("'");
                        if (i < answers.length - 1) fileContent.append(",");
                        fileContent.append("\r\n");
                    }
                    fileContent.append("                         ),\r\n")
                            .append("         'answer_type'=>array(\r\n")
                            .append("                              1=>'T',\r\n")
                            .append("                              2=>'T',\r\n")
                            .append("                              3=>'T',\r\n")
                            .append("                              4=>'T',\r\n")
                            .append("                              5=>'T'\r\n")
                            .append("                              ),\r\n")
                            .append("         'true_answer'=>array(\r\n");
                    for (int i = 0; i < countAnswers(trueAnswers); i++) {
                        if (!"".equals(trueAnswers[i])) {
                            fileContent.append("                              ")
                                    .append(i + 1)
                                    .append("=>")
                                    .append(trueAnswers[i]);
                            if (i < countAnswers(trueAnswers) - 1) fileContent.append(",");
                            fileContent.append("\r\n");
                        }
                    }
                    fileContent.append("                              )\r\n")
                            .append("         )");
                    break;
                case L:
                    for (int i = 0; i < answers.length; i++) {
                        fileContent.append("                         ")
                                .append(i + 1)
                                .append("=>array('")
                                .append(replace(answers[i]))
                                .append("','")
                                .append(replace(trueAnswers[i]))
                                .append("')");
                        if (i < answers.length - 1) fileContent.append(",");
                        fileContent.append("\r\n");
                    }
                    fileContent.append("                         ),\r\n")
                            .append("         'true_answer'=>array(\r\n");
                    for (int i = 0; i < countAnswers(trueAnswers); i++) {
                        fileContent.append("                              ")
                                .append(i + 1)
                                .append("=>")
                                .append(i + 1);
                        if (i < countAnswers(trueAnswers) - 1) fileContent.append(",");
                        fileContent.append("\r\n");
                    }
                    fileContent.append("                              )\r\n")
                            .append("         )");
                    break;
                case I:
                case F:
                    int length = 0;
                    for (String answer : answers) {
                        if (!"".equals(replace(answer))) {
                            length++;
                        }
                    }
                    for (int i = 0; i < length; i++) {
                        fileContent.append("                         ")
                                .append(i + 1)
                                .append("=>'")
                                .append(replace(answers[i]))
                                .append("'");
                        if (i < length - 1) fileContent.append(",");
                        fileContent.append("\r\n");
                    }
                    fileContent.append("                         ),\r\n")
                            .append("         'true_answer'=>array(\r\n")
                            .append("                              1=>1\r\n")
                            .append("                              )\r\n")
                            .append("         )");
                    break;
                default:
                    break;
            }
            if (count < questions.size())
                fileContent.append(",");
            fileContent.append("\r\n");
        }

        fileContent.append(");\r\n")
                .append("\r\n")
                .append("?>");

        return fileContent.toString().getBytes(CHARSET);
    }

    private static String replace(String input) {
        if (input == null) return "";
        return input.replace("'", "&prime;")
                .replace("\n", "<br />")
                .replace("\r", "");
    }

    private static int parseDifficulty(String input) {
        switch (input) {
            case "Лёгкий":
                return 2;
            case "Средний":
                return 4;
            case "Сложный":
                return 6;
            default:
                return 0;
        }
    }

    private static int countAnswers(String[] answers) {
        int result = 0;
        for (String answer : answers) {
            if (!"".equals(answer)) result++;
        }
        return result;
    }
}
