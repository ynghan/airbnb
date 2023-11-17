package jpabook.start.domain.day;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Months {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "month_id")
  private Long id;

  @OneToMany(mappedBy = "month", cascade = CascadeType.ALL)
  private List<Date> date = new ArrayList<>();
}
