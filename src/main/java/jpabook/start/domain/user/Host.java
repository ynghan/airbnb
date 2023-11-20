package jpabook.start.domain.user;

import jpabook.start.domain.house.House;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Host {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "HOST_ID")
  private Long id;

  private String name;

  private int age;

  public Host(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "HOUSE_ID")
  private House house;


  public void registHouse(House house) {
    this.house = house;
    house.setHost(this);
  }

}
