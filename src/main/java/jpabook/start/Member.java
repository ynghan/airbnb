package jpabook.start;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class Member {
    @Id @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    private String name;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;



    @Embedded
    private Period workPeriod;
    @Embedded
    private Address homeAddress;

    public Member(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        this.team = team;
    }
}

