package jpabook.start.domain.house;

import jpabook.start.domain.amentity.Amenity;
import jpabook.start.domain.user.Host;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class House {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "HOUSE_ID")
  private Long id;

  @OneToOne(mappedBy = "house", fetch = FetchType.LAZY)
  private Host host;

  @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
  private List<Amenity> amenitys = new ArrayList<>();

  @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
  private List<DateHouse> dateHouses = new ArrayList<>();

  private String name;

  private LocalDateTime registerDate;

  private int capacity;

  private int roomCount;

  private int bathroomCount;

  private String introduction;

  @Enumerated(EnumType.STRING)
  private HouseStatus status;

  //== 연관관계 메서드 ==//
  public void addAmenity(Amenity amenity) {
    amenitys.add(amenity);
    amenity.setHouse(this);
  }

  public void addDateHouse(DateHouse dateHouse) {
    dateHouses.add(dateHouse);
    dateHouse.setHouse(this);
  }

  public void setHost(Host host) {
    this.host = host;
    host.setHouse(this);
  }

  //== 생성 메서드 ==//
  public House(String name, LocalDateTime registerDate, HouseStatus houseStatus,
               int capacity, int roomCount, int bathroomCount, String introduction) {
    this.name = name;
    this.registerDate = registerDate;
    this.status = houseStatus;
    this.capacity = capacity;
    this.roomCount = roomCount;
    this.bathroomCount = bathroomCount;
    this.introduction = introduction;
  }

  public static House registHouse(Host host, List<Amenity> amenities, List<DateHouse> dateHouses) {
    House house = new House();
    house.setHost(host);
    for (Amenity amenity : amenities) {
      house.addAmenity(amenity);
    }
    for (DateHouse dateHouse : dateHouses) {
      house.addDateHouse(dateHouse);
    }
    return house;
  }

}
