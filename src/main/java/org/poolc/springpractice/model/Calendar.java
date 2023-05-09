package org.poolc.springpractice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "calendar")
    private List<Schedule> schedules = new ArrayList<>();
}
