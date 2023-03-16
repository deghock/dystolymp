package ru.spbu.distolymp.service.crud.api.geography;

import ru.spbu.distolymp.entity.geography.Town;

/**
 * @author Maxim Andreev
 */
public interface TownCrudService {

    Town getTownById(Long id);

}
