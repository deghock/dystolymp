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
}
