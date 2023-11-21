package jpabook.start.domain.booking;

import jpabook.start.domain.house.DateHouse;
import jpabook.start.domain.house.HouseType;
import jpabook.start.domain.review.Review;
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

  //예약 인원
  private int peopleNum;

  //예약 정보
  private String reserveInfo;

  //숙소 이름
  private String houseName;

  //숙소 타입
  @Enumerated(EnumType.STRING)
  private HouseType houseType;

  //요금
  private double price;

  //체크인
  private LocalDateTime checkInDate;

  //체크아웃
  private LocalDateTime checkOutDate;

  @Enumerated(EnumType.STRING)
  private BookStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GUEST_ID")
  private Guest guest;

  @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
  private List<DateHouse> dateHouses = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "REVIEW_ID")
  private Review review;

  //연관관계 메서드
  public void setGuest(Guest guest) {
    this.guest = guest;
    guest.getBooks().add(this);
  }
}
