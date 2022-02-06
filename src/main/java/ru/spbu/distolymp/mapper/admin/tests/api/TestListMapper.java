package ru.spbu.distolymp.mapper.admin.tests.api;

import org.mapstruct.Mapper;
import ru.spbu.distolymp.dto.admin.tests.TestListDto;
import ru.spbu.distolymp.entity.tasks.Test;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Mapper
public interface TestListMapper {
    TestListDto toDto(Test test);
    List<TestListDto> toDtoList(List<Test> testList);

    default String integerToString(Integer status) {
        switch (status) {
            case 1:
                return "Новый";
            case 2:
                return "Опубликован";
            case 3:
                return "Архив";
            default:
                return "Неизвестен";
        }
    }
}
