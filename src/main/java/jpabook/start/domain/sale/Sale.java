package jpabook.start.domain.sale;

import jpabook.start.domain.house.DateHouse;

import javax.persistence.*;

@Entity
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sale_id")
  private Long id;

  @OneToOne(mappedBy = "sale")
  private DateHouse dateHouse;


}
