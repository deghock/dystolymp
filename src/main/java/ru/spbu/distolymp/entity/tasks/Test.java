package ru.spbu.distolymp.entity.tasks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Vladislav Konovalov
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "p_test")
public class Test extends ProblemType {

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

    @Column(name = "show_res")
    private boolean isShowResult = true;

    @Type(type = "text")
    @Column(name = "notes")
    private String notes;

}
