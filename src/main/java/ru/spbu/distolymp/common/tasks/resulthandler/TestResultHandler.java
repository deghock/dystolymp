package ru.spbu.distolymp.common.tasks.resulthandler;

import ru.spbu.distolymp.common.tasks.auxiliary.QuestionDto;
import ru.spbu.distolymp.common.tasks.auxiliary.QuestionType;
import ru.spbu.distolymp.dto.admin.tests.TestAnswerDto;
import ru.spbu.distolymp.dto.admin.tests.TestResultDto;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Vladislav Konovalov
 */
public class TestResultHandler {
    private TestResultHandler() {}

    public static TestResultDto toResultDto(TestAnswerDto answerDto,
                                            boolean isShowResult,
                                            List<QuestionDto> initQuestionList) {
        TestResultDto resultDto = new TestResultDto();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy kk:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(answerDto.getTestStartDateTime(), formatter);
        LocalDateTime finishDateTime = LocalDateTime.now();
        Duration duration = Duration.between(startDateTime, finishDateTime);
        
        initQuestionList = refactorFieldsToClient(initQuestionList);
        List<QuestionDto> questionList = getQuestionList(
                answerDto.getQuestions(),
                answerDto.getUserAnswers(),
                answerDto.getQuestionNumbers()
        );
        questionList = initQuestionList(initQuestionList, questionList);
        questionList = refactorFieldsToClient(questionList);
        resultDto.setQuestionList(questionList);
        
        resultDto.setCorrectness(checkCorrectness(initQuestionList, questionList));
        resultDto.setQuestionNumber(new Integer[] {
                countTrueAnswers(resultDto.getCorrectness()),
                resultDto.getQuestionList().size()}
        );
        resultDto.setPoints(getPoints(resultDto.getQuestionNumber()));

        resultDto.setInterrupted(answerDto.getInterrupted());
        resultDto.setShowResult(isShowResult);
        resultDto.setStartDateTime(answerDto.getTestStartDateTime());
        resultDto.setDurationTime(durationToString(duration));

        return resultDto;
    }

    private static boolean[] checkCorrectness(List<QuestionDto> initList, List<QuestionDto> list) {
        boolean[] result = new boolean[list.size()];
        int count = 0;
        for (QuestionDto question : list) {
            for (QuestionDto initQuestion : initList)
                if (question.getNumber() == initQuestion.getNumber()) {
                    switch (question.getType()) {
                        case S:
                        case C:
                            List<String> trueAnswerList = new ArrayList<>();
                            List<String> initTrueAnswerList = new ArrayList<>();
                            List<Boolean> correctnessList = new ArrayList<>();
                            for (int i = 0; i < question.getTrueAnswers().length; i++) {
                                String trueAnswer = question.getTrueAnswers()[i].trim();
                                if (!trueAnswer.equals("")) {
                                    int index = Integer.parseInt(trueAnswer) - 1;
                                    trueAnswerList.add(question.getAnswers()[index]);
                                }
                            }
                            if (trueAnswerList.isEmpty()) {
                                result[count] = false;
                                break;
                            }
                            for (int i = 0; i < initQuestion.getTrueAnswers().length; i++) {
                                String initTrueAnswer = initQuestion.getTrueAnswers()[i].trim();
                                if (!initTrueAnswer.equals("")) {
                                    int index = Integer.parseInt(initTrueAnswer) - 1;
                                    initTrueAnswerList.add(initQuestion.getAnswers()[index]);
                                }
                            }
                            if (trueAnswerList.size() != initTrueAnswerList.size()) {
                                result[count] = false;
                                break;
                            }
                            Collections.sort(trueAnswerList);
                            Collections.sort(initTrueAnswerList);
                            for (int i = 0; i < trueAnswerList.size(); i++) {
                                correctnessList.add(trueAnswerList.get(i).equals(initTrueAnswerList.get(i)));
                            }
                            boolean bool = true;
                            for (boolean b : correctnessList) {
                                if (!b) {
                                    bool = false;
                                    break;
                                }
                            }
                            result[count] = bool;
                            break;
                        case L:
                            Map<String, String> trueAnswerMap = new HashMap<>();
                            Map<String, String> initTrueAnswerMap = new HashMap<>();
                            correctnessList = new ArrayList<>();
                            for (int i = 0; i < question.getAnswers().length; i++) {
                                String answer = question.getAnswers()[i].trim();
                                if (!"".equals(answer)) {
                                    String trueAnswer = question.getTrueAnswers()[i].trim();
                                    trueAnswerMap.put(answer, trueAnswer);
                                }
                            }
                            for (int i = 0; i < initQuestion.getAnswers().length; i++) {
                                String answer = initQuestion.getAnswers()[i].trim();
                                if (!"".equals(answer)) {
                                    String trueAnswer = initQuestion.getTrueAnswers()[i].trim();
                                    initTrueAnswerMap.put(answer, trueAnswer);
                                }
                            }
                            if (trueAnswerMap.size() != initTrueAnswerMap.size()) {
                                result[count] = false;
                                break;
                            }
                            for (String key : trueAnswerMap.keySet()) {
                                correctnessList.add(trueAnswerMap.get(key).equals(initTrueAnswerMap.get(key)));
                            }
                            bool = true;
                            for (boolean b : correctnessList) {
                                if (!b) {
                                    bool = false;
                                    break;
                                }
                            }
                            result[count] = bool;
                            break;
                        case I:
                            String userAnswerStr = question.getTrueAnswers()[0];
                            String answerStr = initQuestion.getAnswers()[0];
                            result[count] = answerStr.equals(userAnswerStr);
                            break;
                        case F:
                            userAnswerStr = question.getTrueAnswers()[0].replace(",", ".");
                            answerStr = initQuestion.getAnswers()[0];
                            String errorStr = initQuestion.getAnswers()[2];
                            double userAnswer;
                            double answer;
                            double error;
                            try {
                                userAnswer = Double.parseDouble(userAnswerStr);
                                answer = Double.parseDouble(answerStr);
                                error = Double.parseDouble(errorStr);
                            } catch (NumberFormatException e) {
                                result[count] = false;
                                break;
                            }
                            bool = (answer - error <= userAnswer) && (userAnswer <= answer + error);
                            result[count] = bool;
                            break;
                    }
                }
            count++;
        }
        return result;
    }

    private static int countTrueAnswers(boolean[] input) {
        int result = 0;
        for (boolean b : input) {
            if (b) result++;
        }
        return result;
    }

    private static String getPoints(Integer[] questionNumbers) {
        double result = 100.0 * questionNumbers[0] / questionNumbers[1];
        NumberFormat format = new DecimalFormat("#0.##");
        return format.format(result) + "%";
    }

    private static String durationToString(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutes() - hours * 60;
        long seconds = duration.getSeconds() - hours * 3600 - minutes * 60;

        String hoursString = "" + hours;
        String minutesString = "" + minutes;
        String secondsString = "" + seconds;

        if (hoursString.length() < 2) hoursString = "0" + hoursString;
        if (minutesString.length() < 2) minutesString = "0" + minutesString;
        if (secondsString.length() < 2) secondsString = "0" + secondsString;

        return hoursString + ":" + minutesString + ":" + secondsString;
    }

    private static List<QuestionDto> initQuestionList(List<QuestionDto> initQuestionList,
                                                      List<QuestionDto> questionList) {
        List<QuestionDto> result = new ArrayList<>();
        for (QuestionDto question : questionList) {
            for (QuestionDto initQuestion : initQuestionList) {
               if (question.getNumber() == initQuestion.getNumber()) {
                   question.setType(initQuestion.getType());
                   question.setText(initQuestion.getText());
                   question.setDifficulty(initQuestion.getDifficulty());
                   if (question.getType() == QuestionType.I) {
                       String[] answer = new String[] {
                               "",
                               initQuestion.getAnswers()[1],
                               initQuestion.getAnswers()[2],
                               "",
                               ""};
                       question.setAnswers(answer);
                   }
                   if (question.getType() == QuestionType.F) {
                       String[] answer = new String[] {
                               "",
                               "",
                               "",
                               initQuestion.getAnswers()[3],
                               initQuestion.getAnswers()[4]};
                       question.setAnswers(answer);
                   }
                   result.add(question);
               }
            }
        }
        return result;
    }

    private static List<QuestionDto> getQuestionList(String[] initAnswers,
                                                     String[] initUserAnswers,
                                                     int[] questionNumbers) {
        if (initUserAnswers == null) initUserAnswers = new String[1];
        if (initAnswers == null) initAnswers = new String[1];
        String[] answers = new String[questionNumbers.length];
        String[] userAnswers = new String[questionNumbers.length];
        Arrays.fill(answers, "");
        Arrays.fill(userAnswers, "");
        for (int i = 0; i < initAnswers.length; i++) {
            if (initAnswers[i] != null)
                answers[i] = initAnswers[i];
        }
        for (int i = 0; i < initUserAnswers.length; i++) {
            if (initUserAnswers[i] != null)
                userAnswers[i] = initUserAnswers[i];
        }
        List<QuestionDto> result = new ArrayList<>();
        for (int i = 0; i < questionNumbers.length; i++) {
            QuestionDto question = new QuestionDto();
            question.setNumber(questionNumbers[i]);
            question.setAnswers(to2dArray(answers)[i]);
            question.setTrueAnswers(to2dArray(userAnswers)[i]);
            result.add(question);
        }
        return result;
    }

    private static List<QuestionDto> refactorFieldsToClient(List<QuestionDto> questionDtoList) {
        List<QuestionDto> resultList = new ArrayList<>();
        for (QuestionDto questionDto : questionDtoList) {
            if (questionDto.getType() == QuestionType.C) {
                String[] initTrueAnswers = questionDto.getTrueAnswers();
                String[] result = new String[] {"", "", "", "", ""};
                for (int i = 0; i < 5; i++) {
                    if (initTrueAnswers[i] != null &&
                            !"".equals(initTrueAnswers[i]) &&
                            !",".equals(initTrueAnswers[i]) &&
                            !",,".equals(initTrueAnswers[i]) &&
                            !",,,".equals(initTrueAnswers[i]) &&
                            !",,,,".equals(initTrueAnswers[i])) {
                        result[Integer.parseInt(initTrueAnswers[i]) - 1] = initTrueAnswers[i];
                    }
                }
                questionDto.setTrueAnswers(result);
            }
            resultList.add(questionDto);
        }
        return resultList;
    }

    private static String[] parse(String input) {
        if (input == null || input.trim().equals("")) return new String[] {"", "", "", "", ""};
        String expr = "~;{>/:$^)-";
        List<Integer> indexes = new ArrayList<>();
        List<String> result = new ArrayList<>();
        indexes.add(0);
        int index = 0;
        while (input.indexOf(expr, index) != -1) {
            index = input.indexOf(expr, index);
            indexes.add(index);
            index += 10;
        }
        for (int i = 0; i < indexes.size() - 1; i++) {
            String str = input.substring(indexes.get(i), indexes.get(i + 1));
            result.add(str.replace(expr + ",", ""));
        }
        if (result.isEmpty()) result.add(input);
        while (result.size() < 5) result.add("");
        return result.toArray(new String[0]);
    }

    private static String[][] to2dArray(String[] input) {
        String[][] result = new String[input.length][5];
        for (int i = 0; i < input.length; i++) {
            result[i] = parse(input[i]);
        }
        return result;
    }
}
