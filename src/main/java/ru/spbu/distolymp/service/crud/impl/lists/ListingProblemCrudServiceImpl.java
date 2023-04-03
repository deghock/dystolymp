package ru.spbu.distolymp.service.crud.impl.lists;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.distolymp.entity.lists.Listing;
import ru.spbu.distolymp.entity.lists.ListingProblems;
import ru.spbu.distolymp.exception.common.TechnicalException;
import ru.spbu.distolymp.repository.lists.ListingProblemRepository;
import ru.spbu.distolymp.service.crud.api.lists.ListingProblemCrudService;
import ru.spbu.distolymp.service.crud.api.tasks.ProblemCrudService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vladislav Konovalov
 */
@Log4j
@Service
public class ListingProblemCrudServiceImpl implements ListingProblemCrudService {
    private final ListingProblemRepository listingProblemRepository;
    private final ProblemCrudService problemCrudService;

    public ListingProblemCrudServiceImpl(ListingProblemRepository listingProblemRepository, @Lazy ProblemCrudService problemCrudService) {
        this.listingProblemRepository = listingProblemRepository;
        this.problemCrudService = problemCrudService;
    }

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


    @Override
    @Transactional(readOnly = true)
    public ListingProblems findByIdOrNull(Long id){
        if(id == null){
            return null;
        }
        try {
            return listingProblemRepository.findFirstById(id);
        } catch (DataAccessException e) {
            log.error("An error occurred when finding with id=" + id, e);
            throw new TechnicalException();
        }

    }


    @Override
    @Transactional
    public List<ListingProblems> copyListingProblem(List<ListingProblems> copyListingProblem, Listing copyListing , String prefix){
        List<ListingProblems> newProblems = new ArrayList<>();
        for(int i = 0; i < copyListingProblem.size(); i++){
            newProblems.add(copyListingProblem.get(i).copyFrom());
            newProblems.get(i).setListing(copyListing);
            newProblems.get(i).setProblem(problemCrudService.copyProblem(copyListingProblem.get(i).getProblem(), prefix));
        }
        return newProblems;
    }


}
