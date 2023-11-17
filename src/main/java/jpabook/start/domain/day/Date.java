package jpabook.start.domain.day;

import jpabook.start.domain.house.DateHouse;

import javax.persistence.*;
@Entity
public class Date {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "date_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "month_id")
  private Months months;

  @OneToOne(mappedBy = "date")
  private DateHouse dateHouse;


}
