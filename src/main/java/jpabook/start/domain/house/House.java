package jpabook.start.domain.house;

import jpabook.start.domain.amentity.Amenity;
import jpabook.start.domain.user.Host;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class House {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "house_id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  private Host host;

  @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
  private List<Amenity> amenity = new ArrayList<>();

  @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
  private List<DateHouse> dateHouse = new ArrayList<>();

  private String name;

  private int capacity;

  private int roomCount;

  private int bathroomCount;

  private String introduction;





}
