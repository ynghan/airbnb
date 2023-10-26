package jpabook.start;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Team {

    @Id @GeneratedValue
    @Column
    private Long id;

    private String name;



    public Team(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    @BatchSize(size = 50)
    private List<Member> members = new ArrayList<>();

}
