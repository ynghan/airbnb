package jpabook.start.domain.sale;

import jpabook.start.domain.house.DateHouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SALE_ID")
  private Long id;

  @OneToOne(mappedBy = "sale")
  private DateHouse dateHouse;

  @Enumerated(EnumType.STRING)
  private SaleStatus status;

  public Sale(SaleStatus status) {
    this.status = status;
  }

}
