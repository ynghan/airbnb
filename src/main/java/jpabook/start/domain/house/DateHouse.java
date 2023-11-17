package jpabook.start.domain.house;

import jpabook.start.domain.booking.Book;
import jpabook.start.domain.day.Date;
import jpabook.start.domain.sale.Sale;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class DateHouse {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "DATEHOUSE_ID")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BOOK_ID")
  private Book book;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "HOUSE_ID")
  private House house;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "DATE_ID")
  private Date date;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SALE_ID")
  private Sale sale;

  //== 연관관계 메서드 ==//
  public void setBook(Book book) {
    this.book = book;
    book.getDateHouses().add(this);
  }
  public void setHouse(House house) {
    this.house = house;
    house.getDateHouses().add(this);
  }
  public void setDates(Date date) {
    this.date = date;
    date.setDateHouse(this);
  }
  public void setSale(Sale sale) {
    this.sale = sale;
    sale.setDateHouse(this);
  }

  //==생성 메서드==//
  public DateHouse(House house, Date date, Sale sale) {
    this.house = house;
    this.date = date;
    this.sale = sale;
  }
}
