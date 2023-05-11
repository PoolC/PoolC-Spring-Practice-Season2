package org.poolc.springpractice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Schedule {
    @GeneratedValue @Id
    private Long id;

    @NotBlank
    @Length(max = 50)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    @JsonIgnore
    private Calendar calendar;

    @NotEmpty
    private LocalDate startDate;

    @NotEmpty
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;

    @Length(max = 200)
    private String description;

    public Schedule() {}
    public Schedule(String title, LocalDate startDate, LocalDate endDate, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
    public Schedule(String title, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }
}
