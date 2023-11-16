package jpabook.start.domain.user;

import jpabook.start.domain.house.House;

import javax.persistence.*;

public class Host {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "host")
  private House house;

}
