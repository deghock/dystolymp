package ru.spbu.distolymp.entity.lists;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.spbu.distolymp.entity.tasks.Problem;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "list_problems")
public class ListingProblems implements Comparable<ListingProblems>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lp")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_list", referencedColumnName = "id_list")
    private Listing listing;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_problem", referencedColumnName = "id_problem")
    private Problem problem;

    @Column(name = "`order`")
    private Integer order;


    @Override
    public int compareTo(ListingProblems problems){
        if(problems.getOrder() == null || getOrder() == null){
            return 0;
        }
        return getOrder().compareTo(problems.getOrder());
    }

    public ListingProblems copyFrom(){
        ListingProblems listingProblems = new ListingProblems();
        listingProblems.setOrder(order);
        return listingProblems;
    }
}
