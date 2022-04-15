package ru.spbu.distolymp.common.tasks.auxiliary;

/**
 * @author Vladislav Konovalov
 */
public enum QuestionType {
    S("Один ответ"),
    C("Несколько ответов"),
    L("Соответствие"),
    I("Целочисленный ответ"),
    F("Вещественный ответ"),
    U("Неизвестно");

    final String name;

    QuestionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        switch (this) {
            case S:
                return "S";
            case C:
                return "C";
            case L:
                return "L";
            case I:
                return "I";
            case F:
                return "F";
            default:
                return "U";
        }
    }
}
