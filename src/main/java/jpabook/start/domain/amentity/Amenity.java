package jpabook.start.domain.amentity;

import jpabook.start.domain.house.House;

import javax.persistence.*;
@Entity
public class Amenity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "amenty_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "house_id")
  private House house;



}
