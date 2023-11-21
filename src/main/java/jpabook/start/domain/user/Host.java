package jpabook.start.domain.user;

import jpabook.start.domain.house.House;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Host extends BaseUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "HOST_ID")
  private Long id;

  private double totalMonthAmount;


  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "HOUSE_ID")
  private House house;


  public void registHouse(House house) {
    this.house = house;
    house.setHost(this);
  }

  public Host(String name, int age) {
    super.setName(name);
    super.setAge(age);
  }

}
