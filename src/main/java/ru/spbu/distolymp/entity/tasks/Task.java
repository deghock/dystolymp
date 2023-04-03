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
@Table(name = "p_task")
public class Task extends Problem {

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

    @Column(name = "ans_note")
    private Integer answerNote;

    @Type(type = "text")
    @Column(name = "solution")
    private String solution;

    @Size(max = 255)
    @Column(name = "solutionlink")
    private String solutionLink;

    @Type(type = "text")
    @Column(name = "notes")
    private String notes;

    @Override
    public Task copyFromProblem(Problem problem){
        Task newTask = new Task();
        newTask.setAnswerList(problem.getAnswerList());
        newTask.setComment(comment);
        newTask.setHeight(height);
        newTask.setImageFileName(imageFileName);
        newTask.setMinPoints(minPoints);
        newTask.setMinusPoints(minusPoints);
        newTask.setNotes(notes);
        newTask.setPrefix(getPrefix());
        newTask.setStatus(getStatus());
        newTask.setTitle(problem.getTitle());
        newTask.setType(problem.getType());
        newTask.setWidth(width);
        newTask.setAnswerNote(answerNote);
        newTask.setCorrectAnswer(correctAnswer);
        newTask.setGradePoints(gradePoints);
        newTask.setMaxPoints(maxPoints);
        newTask.setVariables(variables);
        newTask.setSolution(solution);
        newTask.setSolutionLink(solutionLink);
        newTask.setAnswerNote(answerNote);
        return newTask;
    }

}
