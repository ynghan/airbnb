package jpabook.start.domain.house;

import jpabook.start.domain.amentity.Amenity;
import jpabook.start.domain.user.Host;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

  private int charge;

  private LocalDateTime registerDate;

  private int capacity;

  private int roomCount;

  private int bathroomCount;

  private Address address;

  private String introduction;

  @Enumerated(EnumType.STRING)
  private HouseType houseType;

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
  public House(int charge, Address address, String name, int capacity, int roomCount, int bathroomCount, HouseType houseType, String introduction) {

    LocalDateTime now = LocalDateTime.now();
    this.address = address;
    this.charge = charge;
    this.name = name;
    this.registerDate = now;
    this.houseType = houseType;
    this.capacity = capacity;
    this.roomCount = roomCount;
    this.bathroomCount = bathroomCount;
    this.introduction = introduction;

    //현재 일 ~ 31일 DateHouse 클래스 생성
    createDateHouse();

  }
  //House 생성 시점에 모든 날짜에 대한 DateHouse를 생성한다.
  public void createDateHouse() {
    //월 저장
    Month monthValue = this.registerDate.getMonth();
    YearMonth yearMonth = YearMonth.of(2023, monthValue);
    int lengthOfMonth = yearMonth.lengthOfMonth();
    int dayOfMonth = this.registerDate.getDayOfMonth();

    //현재 '일'부터 현재 '달'의 마지막 날까지 DateHouse 생성
    for(int i = dayOfMonth; i <= lengthOfMonth; i++) {
      DateHouse dateHouse = new DateHouse(this, monthValue, i);
      dateHouse.setHouse(this);
      this.dateHouses.add(dateHouse);
    }
  }
}
