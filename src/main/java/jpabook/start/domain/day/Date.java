package jpabook.start.domain.day;

import jpabook.start.domain.house.DateHouse;

import javax.persistence.*;

public class Date {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Month month;

  @OneToOne(mappedBy = "date")
  private DateHouse dateHouse;


}
