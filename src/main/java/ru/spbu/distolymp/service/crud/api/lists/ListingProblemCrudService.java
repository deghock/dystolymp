package ru.spbu.distolymp.service.crud.api.lists;

import ru.spbu.distolymp.entity.lists.ListingProblems;

/**
 * @author Vladislav Konovalov
 */
public interface ListingProblemCrudService {
    void deleteByProblemId(Long problemId);
    ListingProblems findByIdOrNull(Long id);
}
