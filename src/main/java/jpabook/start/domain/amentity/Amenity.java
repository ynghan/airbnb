package jpabook.start.domain.amentity;

import jpabook.start.domain.house.House;

import javax.persistence.*;

public class Amenity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private House house;



}
