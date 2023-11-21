package jpabook.start.domain.amentity;

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
public class Amenity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "AMENITY_ID")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "HOUSE_ID")
  private House house;

  @Enumerated(EnumType.STRING)
  private AmenityStatus status;

  public Amenity(House house, AmenityStatus status) {
    this.house = house;
    this.status = status;
  }

}