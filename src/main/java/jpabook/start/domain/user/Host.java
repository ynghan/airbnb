package jpabook.start.domain.user;

import jpabook.start.domain.house.House;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  private String gender;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "HOUSE_ID")
  private House house;

}
