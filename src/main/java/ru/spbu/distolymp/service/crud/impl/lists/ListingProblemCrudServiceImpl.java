package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.entity.lists.ListingProblems;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.repository.lists.ListingProblemRepository;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
@RequiredArgsConstructor
public class ListingProblemCrudServiceImpl implements ListingProblemCrudService {
    private final ListingProblemRepository listingProblemRepository;

    @Override
    @Transactional
    public void deleteByProblemId(Long problemId) {
        try {
            listingProblemRepository.deleteAllByProblemId(problemId);
            updateProblemOrder();
        } catch (DataAccessException e) {
            log.error("An error occurred when deleting a problem with id=" + problemId, e);
            throw new TechnicalException();
        }
    }

    private void updateProblemOrder() {
        List<Long> listingIdList = listingProblemRepository.findDistinctListingIds();
        for (Long id : listingIdList) {
            List<ListingProblems> problemList = listingProblemRepository.findAllByListingIdOrderByOrder(id);
            for (int i = 0; i < problemList.size(); i++)
                problemList.get(i).setOrder(i + 1);
        }
    }
}
