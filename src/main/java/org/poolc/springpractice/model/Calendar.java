package org.poolc.springpractice.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Calendar {
    @GeneratedValue @Id
    private Long id;
    @NotEmpty
    private String title;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "calendar")
    private List<Schedule> schedules = new ArrayList<>();
}
