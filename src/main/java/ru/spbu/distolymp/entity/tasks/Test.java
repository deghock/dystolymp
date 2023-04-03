package ru.spbu.distolymp.entity.tasks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import ru.spbu.distolymp.entity.answers.Answer;
import ru.spbu.distolymp.entity.converter.BooleanToIntegerConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "p_test")
public class Test extends Problem {

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

    @Column(name = "points")
    private Double points;

    @Column(name = "points_low")
    private Double minPoints;

    @Column(name = "minuspoints")
    private Double minusPoints;

    @NotNull
    @Size(max = 255)
    @Column(name = "test_folder")
    private String testFolder;

    @Size(max = 255)
    @Column(name = "brcfilename")
    private String brcFileName;

    @Size(max = 255)
    @Column(name = "infolink")
    private String infoLink;

    @Size(max = 255)
    @Column(name = "parfilename")
    private String parFileName;

    @Convert(converter = BooleanToIntegerConverter.class)
    @Column(name = "show_res", columnDefinition = "TINYINT")
    private boolean showResult = true;

    @Type(type = "text")
    @Column(name = "notes")
    private String notes;

    @Override
    public Test copyFromProblem(Problem problem){
        Test newTest = new Test();
        newTest.setAnswerList(new ArrayList<Answer>());
        newTest.setBrcFileName(brcFileName);
        newTest.setComment(comment);
        newTest.setHeight(height);
        newTest.setImageFileName(imageFileName);
        newTest.setInfoLink(infoLink);
        newTest.setMinPoints(minPoints);
        newTest.setMinusPoints(minusPoints);
        newTest.setNotes(notes);
        newTest.setParFileName(parFileName);
        newTest.setPoints(points);
        newTest.setPrefix(getPrefix());
        newTest.setShowResult(showResult);
        newTest.setStatus(getStatus());
        newTest.setTestFolder(testFolder);
        newTest.setTitle(problem.getTitle());
        newTest.setType(problem.getType());
        newTest.setWidth(width);
        return newTest;
    }
}
