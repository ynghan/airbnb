package jpabook.start.domain.house;

import jpabook.start.domain.amentity.Amenity;
import jpabook.start.domain.user.Host;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class House {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  private Host host;

  @OneToMany(mappedBy = "house")
  private List<Amenity> amenity = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  private DateHouse dateHouse;

  private String name;

  private int capacity;

  private int roomCount;

  private int bathroomCount;

  private String introduction;





}
