package jpabook.start.domain.booking;

import jpabook.start.domain.house.DateHouse;
import jpabook.start.domain.user.Guest;
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
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "BOOK_ID")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GUEST_ID")
  private Guest guest;

  @OneToMany(mappedBy = "book")
  private List<DateHouse> dateHouses = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private BookStatus status;

  //연관관계 메서드
  public void setGuest(Guest guest) {
    this.guest = guest;
    guest.getBooks().add(this);
  }

  public void addDateHouse(DateHouse dateHouse) {
    dateHouses.add(dateHouse);
    dateHouse.setBook(this);
  }
}
