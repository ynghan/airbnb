package jpabook.start.domain.booking;

import jpabook.start.domain.house.DateHouse;
import jpabook.start.domain.user.Guest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "guest_id")
  private Guest guest;

  @OneToMany(mappedBy = "book")
  private List<DateHouse> dateHouse = new ArrayList<>();


}
