package jpabook.start.domain.day;

import jpabook.start.domain.house.DateHouse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Date {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DATE_ID")
  private Long id;

  @OneToOne(mappedBy = "date", fetch = FetchType.LAZY)
  private DateHouse dateHouse;

  @Enumerated(EnumType.STRING)
  private DateType type;

  //날짜 일 월
  private LocalDateTime monthAndDate;

}
