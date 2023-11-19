package jpabook.start.domain.house;

import jpabook.start.domain.booking.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DateHouse {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DATEHOUSE_ID")
  private Long id;

  private Month houseMonth;

  private int houseDate;

  private double dateCharge;

  private ReservationState reservationState;

  @Enumerated(EnumType.STRING)
  private SaleStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_ID")
  private Book book;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "HOUSE_ID")
  private House house;

  public DateHouse(House house, Month monthValue, int date) {
    this.status = SaleStatus.QUANTITY;
    this.houseMonth = monthValue;
    this.houseDate = date;
    this.dateCharge = house.getCharge();
    this.reservationState = ReservationState.UNRESERVE;
  }


  //== 연관관계 메서드 ==//
  public void setBook(Book book) {
    this.book = book;
    book.getDateHouses().add(this);
  }
  public void setHouse(House house) {
    this.house = house;
  }



}
