package jpabook.start.domain.user;

import jpabook.start.domain.house.House;

import javax.persistence.*;
@Entity
public class Host {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "host_id")
  private Long id;

  @OneToOne(mappedBy = "host")
  private House house;

}
