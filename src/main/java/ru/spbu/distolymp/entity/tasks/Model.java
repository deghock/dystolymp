package ru.spbu.distolymp.entity.tasks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "p_sim")
public class Model extends Problem {

    @Type(type = "text")
    @Column(name = "problemtext")
    private String problemText;

    @Size(max = 255)
    @Column(name = "imagefilename")
    private String imageFileName;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Size(max = 255)
    @Column(name = "commenttext")
    private String comment;

    @Type(type = "text")
    @Column(name = "`variables`")
    private String variables;

    @Type(type = "text")
    @Column(name = "correctanswer")
    private String correctAnswer;

    @Column(name = "points_max")
    private Double maxPoints;

    @Size(max = 255)
    @Column(name = "grade_points")
    private String gradePoints;

    @Column(name = "points_low")
    private Double minPoints;

    @Column(name = "minuspoints")
    private Double minusPoints;

    @Size(max = 255)
    @Column(name = "brcfilename")
    private String barsicFileName;

    @Type(type = "text")
    @Column(name = "problemform")
    private String problemForm;

    @Type(type = "text")
    @Column(name = "notes")
    private String notes;


    public Model copyFromProblem(Problem problem){
        Model newModel = new Model();
        newModel.setAnswerList(problem.getAnswerList());
        newModel.setBarsicFileName(barsicFileName);
        newModel.setComment(comment);
        newModel.setHeight(height);
        newModel.setImageFileName(imageFileName);
        newModel.setMinPoints(minPoints);
        newModel.setMinusPoints(minusPoints);
        newModel.setMaxPoints(maxPoints);
        newModel.setNotes(notes);
        newModel.setPrefix(problem.getPrefix());
        newModel.setGradePoints(gradePoints);
        newModel.setStatus(problem.getStatus());
        newModel.setVariables(variables);
        newModel.setTitle(problem.getTitle());
        newModel.setType(problem.getType());
        newModel.setWidth(width);
        newModel.setProblemForm(problemForm);
        newModel.setProblemText(problemText);
        return newModel;
    }

}
