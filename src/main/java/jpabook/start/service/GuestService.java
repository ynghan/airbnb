package jpabook.start.service;

import jpabook.start.domain.booking.Book;
import jpabook.start.domain.booking.BookStatus;
import jpabook.start.domain.house.DateHouse;
import jpabook.start.domain.house.House;
import jpabook.start.domain.house.HouseType;
import jpabook.start.domain.house.ReservationState;
import jpabook.start.domain.review.Review;
import jpabook.start.domain.review.StarScore;
import jpabook.start.domain.user.Guest;
import jpabook.start.repository.HouseRepository;
import jpabook.start.repository.userRepository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

import static jpabook.start.domain.booking.BookStatus.COMPLETE;
import static jpabook.start.domain.house.HouseType.*;
import static jpabook.start.domain.house.HouseType.ENTIRESPACE;

@Service
@RequiredArgsConstructor
public class GuestService {

  private HouseRepository houseRepository;

  public GuestService(EntityManager em) {
    houseRepository = new HouseRepository(em);
  }

  /**
   * 검사 항목 3) 게스트는 조건에 맞는 숙소를 조회할 수 있다. (체크인 날짜, 체크아웃 날짜, 인원, 숙소 타입)
   * findHouse(checkInDate, checkOutDate, 5, houseType)
   * findHouse(checkInDate, checkOutDate, null, null)
   * findHouse(checkInDate, checkOutDate, 7, null)
   * 검색 조건에 맞는 숙소는 (숙소 유형, 이름, 총가격, 평균 별점) 정보를 보여준다.
   */

  public List<House> findHouse(int checkIn, int checkOut, int capacity, HouseType houseType) {
    List<House> findHouses = houseRepository.findByDateRange(checkIn, checkOut);
    List<House> returnHouses = new ArrayList<>();
    System.out.println("                          [ 검색 조건 ]                          ");
    System.out.println(" 1. " + houseType.getSpace()
            + "\n 2. " +  capacity + "명 이상 수용 가능"
            + "\n 3. "+ checkIn + "일 부터 " + checkOut + "일 까지 예약 가능 ");
    System.out.println("                          ( 검색 결과 )                          ");

    for (House findHouse : findHouses) {
      if(findHouse.getCapacity() >= capacity && findHouse.getHouseType() == houseType) {
          returnHouses.add(findHouse);
          List<DateHouse> dateHouses = findHouse.getDateHouses(checkIn, checkOut);
          double totalHouseCharge = 0;
          for (DateHouse dateHouse : dateHouses) {
            totalHouseCharge += dateHouse.getDateCharge();
          }
          System.out.println("---------------------------------------------------------------");
          System.out.println(" (1) 이름 - " + findHouse.getName()
                  + "\n (2) 유형 - " + findHouse.getHouseType().getSpace()
                  + "\n (3) 가격 - " + Math.round(totalHouseCharge) + "원");
          System.out.println("---------------------------------------------------------------");
        }
    }
    return returnHouses;
  }

  public List<House> findHouse(int checkIn,int checkOut, int capacity) {
    List<House> findHouses = houseRepository.findByDateRange(checkIn, checkOut);
    List<House> returnHouses = new ArrayList<>();
    System.out.println("                          [ 검색 조건 ]                          ");
    System.out.println(" 1. " +  capacity + "명 이상 수용 가능"
            + "\n 2. "+ checkIn + "일 부터 " + checkOut + "일 까지 예약 가능 ");
    System.out.println("                          ( 검색 결과 )                          ");
    for (House findHouse : findHouses) {
      if(findHouse.getCapacity() >= capacity) {
        returnHouses.add(findHouse);
        double totalHouseCharge = getTotalPrice(findHouse, checkIn, checkOut);
        System.out.println("---------------------------------------------------------------");
        System.out.println(" (1) 이름 - " + findHouse.getName()
                + "\n (2) 유형 - " + findHouse.getHouseType().getSpace()
                + "\n (3) 가격 - " + Math.round(totalHouseCharge) + "원");
        System.out.println("---------------------------------------------------------------");
      }
    }
    return returnHouses;
  }



  public List<House> findHouse(int checkIn, int checkOut) {
    List<House> findHouses = houseRepository.findByDateRange(checkIn, checkOut);
    System.out.println("                          [ 검색 조건 ]                          ");
    System.out.println(" 1. " + checkIn + "일 부터 " + checkOut + "일 까지 예약 가능 ");
    System.out.println("                          ( 검색 결과 )                          ");
    for (House findHouse : findHouses) {
      double totalHouseCharge = getTotalPrice(findHouse, checkIn, checkOut);

      System.out.println("---------------------------------------------------------------");
      System.out.println(" (1) 이름 - " + findHouse.getName()
              + "\n (2) 유형 - " + findHouse.getHouseType().getSpace()
              + "\n (3) 가격 - " + (int)(totalHouseCharge) + "원");
      System.out.println("---------------------------------------------------------------");
    }
    return findHouses;
  }

  /**
   * 검색된 숙소 리스트는 가격(총가격), 별점 등의 기준으로 내림차순 정렬이 가능하다.
   */
  public void sortByPrice(List<House> houses, int checkIn, int checkOut) {

    // 각 House 별로 총 요금을 계산하여 Map에 저장
    Map<House, Double> houseCharges = new HashMap<>();
    for (House findHouse : houses) {
      double totalHouseCharge = getTotalPrice(findHouse, checkIn, checkOut);
      houseCharges.put(findHouse, totalHouseCharge);
    }

    // 총 요금을 기준으로 내림차순 정렬
    houses.sort((h1, h2) -> Double.compare(houseCharges.get(h2), houseCharges.get(h1)));
    System.out.println("                         ( 가격 높은 순 )                         ");
    // 출력
    for (House findHouse : houses) {
      double totalHouseCharge = houseCharges.get(findHouse);
      System.out.println("---------------------------------------------------------------");
      System.out.println(
                " (1) 이름 - " + findHouse.getName()
              + "\n (2) 유형 - " + findHouse.getHouseType().getSpace()
              + "\n (3) 가격 - " + Math.round(totalHouseCharge) + "원");
      System.out.println("---------------------------------------------------------------");
    }
  }
  public void sortByStarPoint(List<House> houses) {

    Map<House, Double> houseStarPoint = new HashMap<>();

    // 각 House 별로 총 요금을 계산하여 Map에 저장
    Map<House, Double> houseCharges = new HashMap<>();
    for (House findHouse : houses) {
      houseCharges.put(findHouse, findHouse.getStarPoint());
    }

    // 총 요금을 기준으로 내림차순 정렬
    houses.sort((h1, h2) -> Double.compare(houseStarPoint.get(h2), houseStarPoint.get(h1)));

    System.out.println("                         ( 별점 높은 순 )                         ");
    // 출력
    for (House findHouse : houses) {
      double totalHouseCharge = houseCharges.get(findHouse);
      System.out.println("---------------------------------------------------------------");
      System.out.println(
              " (1) 이름 - " + findHouse.getName()
                      + "\n (2) 유형 - " + findHouse.getHouseType().getSpace()
                      + "\n (3) 가격 - " + Math.round(totalHouseCharge) + "원");
      System.out.println("---------------------------------------------------------------");
    }
  }


  //==================================================================================================================

  public House getDetailHouse(String houseName) {
    System.out.println();
    System.out.println("======== [" + houseName + " 상세 정보] ========");

    House house = houseRepository.findByName(houseName);

    //1. 숙소 기본 정보
    System.out.println("수용인원 : " + house.getCapacity());
    System.out.println("방 개수 : " + house.getRoomCount());
    System.out.println("화장실 개수 : " + house.getBathroomCount());
    System.out.println("소개 : " + house.getIntroduction());
    System.out.println("시설 : " + house.getAmenitys().toString());
    System.out.println("주소 : " + house.getAddress().getCity() + " " + house.getAddress().getStreet() + " " + house.getAddress().getZipcode());
    System.out.println();







    //2. 숙소의 모든 별점과 리뷰
    System.out.println("별점 : " + house.getStarPoint());
    Set<Review> allReview = house.getAllReview();
    System.out.println("----------- [리뷰 내용] -----------");
    if(allReview.isEmpty()) {
      System.out.println("등록된 리뷰가 없습니다.");
      System.out.println();
    } else {
      for (Review review : allReview) {
        System.out.println(review.getComments());
      }
    }






    //3. 선택한 달의 예약 현황
    /**
     * 공간 전체 숙소의 경우
     */
    if(house.getHouseType() == ENTIRESPACE) {
      LocalDateTime now = LocalDateTime.now();
      int lengthOfMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth();
      char[] calender = new char[lengthOfMonth+1];
      Arrays.fill(calender, '-');

      System.out.println("-------- [11월 예약 현황] ---------");
      List<DateHouse> dateHouses = house.getDateHouses();
      for (DateHouse dateHouse : dateHouses) {
        if(dateHouse.getHouseDate() >= 1 && dateHouse.getHouseDate() <= lengthOfMonth) {
          if(dateHouse.getReservationState() == ReservationState.UNRESERVE) {
            calender[dateHouse.getHouseDate()] = 'O';
          } else if( dateHouse.getReservationState() == ReservationState.RESERVE) {
            calender[dateHouse.getHouseDate()] = '*';
          }
        }
      }

      System.out.println("일\t월\t화\t수\t목\t금\t토");
      System.out.print("\t\t\t");
      for(int i = 1 ; i <= lengthOfMonth; i++) {
        if(i % 7 != 4)
          System.out.print(calender[i] + "\t");
        else {
          System.out.println(calender[i] + "\t");
        }


        // 0 --> 화요일
        // 6 --> 월요일
        // 5 --> 일요일
        // 4 --> 토요일
        // 3 --> 금요일
        // 2 --> 목요일
        // 1 --> 수요일
      }
      System.out.println();
      System.out.println();
    }
    /**
     * 개인실 숙소의 경우
     */
    if(house.getHouseType() == PARTSPACE) {
      LocalDateTime now = LocalDateTime.now();
      int lengthOfMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth();
      String[] calender = new String[lengthOfMonth+1];
      Arrays.fill(calender, "-");

      System.out.println("-------- [11월 예약 현황] ---------");
      List<DateHouse> dateHouses = house.getDateHouses();
      for (DateHouse dateHouse : dateHouses) {
        if(dateHouse.getHouseDate() >= 1 && dateHouse.getHouseDate() <= lengthOfMonth) {
          if(dateHouse.getReservationState() == ReservationState.UNRESERVE) {
            //예약할 때 DateHouse 객체의 방 개수 필드를 생성하고 하나를 감소하는 로직 구현해야함.
            calender[dateHouse.getHouseDate()] = String.valueOf(dateHouse.getRoomCount());
          } else if( dateHouse.getReservationState() == ReservationState.RESERVE) {
            calender[dateHouse.getHouseDate()] = "*";
          }
        }
      }

      System.out.println("일\t월\t화\t수\t목\t금\t토");
      System.out.print("\t\t\t");
      for(int i = 1 ; i <= lengthOfMonth; i++) {
        if(i % 7 != 4)
          System.out.print(calender[i] + "\t");
        else {
          System.out.println(calender[i] + "\t");
        }


        // 0 --> 화요일
        // 6 --> 월요일
        // 5 --> 일요일
        // 4 --> 토요일
        // 3 --> 금요일
        // 2 --> 목요일
        // 1 --> 수요일
      }
      System.out.println();
    }

    //11월에 검사하면 11월달 정보를 표시

    return house;
  }

  /**
   * 체크인, 체크아웃 날짜와 인원을 입력하여 예약을 진행한다.
   */
  public Book bookHouse(Guest guest, House house, int capacity, int checkinDate, int checkoutDate) {

    Book book = new Book();

    book.setStatus(BookStatus.RESERVATION);
    //예약한 게스트
    book.setGuest(guest);
    //수용 인원
    book.setPeopleNum(capacity);
    //숙소 이름
    book.setHouseName(house.getName());
    //숙소 유형
    book.setHouseType(house.getHouseType());
    //숙소 가격
    double totalPrice = getTotalPrice(house, checkinDate, checkoutDate);
    book.setPrice(totalPrice);
    //체크인 날짜
    LocalDateTime checkIn = LocalDateTime.of(2023, 11, checkinDate, 16, 0);
    book.setCheckInDate(checkIn);
    //체크아웃 날짜
    LocalDateTime checkOut = LocalDateTime.of(2023, 11, checkinDate, 11, 0);
    book.setCheckOutDate(checkOut);
    //예약 정보
    book.setReserveInfo(house.getName() + ": " + checkinDate + "일부터 " + checkoutDate + "일까지 " + capacity + "명 예약");

    //공간전체
    if(house.getHouseType() == ENTIRESPACE) {
      if(house.getCapacity() >= capacity) {
        List<DateHouse> dateHouses = house.getDateHouses(checkinDate, checkoutDate);
        for (DateHouse dateHouse : dateHouses) {
          book.getDateHouses().add(dateHouse);
          dateHouse.setReservationState(ReservationState.RESERVE);
        }
      }
    }
    //개인실
    else if(house.getHouseType() == PARTSPACE) {
      if(house.getCapacity() >= capacity) {
        List<DateHouse> dateHouses = house.getDateHouses(checkinDate, checkoutDate);
        for (DateHouse dateHouse : dateHouses) {
          if(dateHouse.getRoomCount() >= capacity) {
            book.getDateHouses().add(dateHouse);
            int remainRoomCount = dateHouse.getRoomCount() - capacity;
            dateHouse.setRoomCount(remainRoomCount);
          }
        }
      }
    }
    System.out.println("========= [예약 완료 메시지] =========");
    System.out.println(book.getReserveInfo());
    System.out.println();

    return book;
  }

  /**
   * 6) 게스트는 예약한 숙소를 취소할 수 있다.
   */
  public void bookCancel(Book book) {
    List<DateHouse> dateHouses = book.getDateHouses();

    //공간 전체
    if(book.getHouseType() == ENTIRESPACE) {
      for (DateHouse dateHouse : dateHouses) {
        dateHouse.setReservationState(ReservationState.UNRESERVE);
      }
    }
    //개인실
    else if(book.getHouseType() == PARTSPACE) {
      for (DateHouse dateHouse : dateHouses) {
        dateHouse.setRoomCount(dateHouse.getRoomCount() + book.getPeopleNum());
      }
    }
    book.setStatus(BookStatus.CANCEL);
    System.out.println("======== [예약 취소 메시지] ========");
    System.out.println(book.getReserveInfo() + " 취소");
    System.out.println();
  }

  public void reservationHistory(Guest guest) {
    List<Book> books = guest.getBooks();
    List<Book> resultBooks = new ArrayList<>();
    for (Book book : books) {
      if(book.getStatus() != BookStatus.CANCEL) {
        resultBooks.add(book);
      }
    }
    findHistoryAll(resultBooks);
    findHistoryReservation(resultBooks);
    findHistoryComplete(resultBooks);
  }

  private void findHistoryReservation(List<Book> resultBooks) {

    List<Book> reservationBooks = new ArrayList<>();

    for (Book resultBook : resultBooks) {
      if(resultBook.getStatus() == BookStatus.RESERVATION) {
        reservationBooks.add(resultBook);
      }
    }

    if(!(reservationBooks.isEmpty())) {
      //전체
      System.out.println("\n================ [예약 조회] ================");

      System.out.println("\t\t숙소명\t\t체크인\t\t\t\t\t체크아웃\t\t\t\t\t요금");
      int index = 1;
      for (Book book : reservationBooks) {
          System.out.print(index + "\t\t"
                  + book.getHouseName() + "\t\t"
                  + book.getCheckInDate() + "\t\t"
                  + book.getCheckOutDate() + "\t\t"
                  + Math.round(book.getPrice()) + "원\n");
          index++;
      }
    }
    else {
      System.out.println("\n예약한 숙박이 없습니다.");
    }
  }

  private void findHistoryComplete(List<Book> resultBooks) {
    List<Book> completeBooks = new ArrayList<>();

    for (Book resultBook : resultBooks) {
      if(resultBook.getStatus() == COMPLETE) {
        completeBooks.add(resultBook);
      }
    }
    //전체
    System.out.println();
    System.out.println("============ [숙박 완료 리스트] ============");
    if(!(completeBooks.isEmpty())) {
      System.out.println("\t\t숙소명\t\t체크인\t\t\t\t\t체크아웃\t\t\t\t\t요금\t\t\t\t후기");
      int index = 1;
      for (Book book : completeBooks) {
        System.out.print(index + "\t\t"
                + book.getHouseName() + "\t\t"
                + book.getCheckInDate() + "\t\t"
                + book.getCheckOutDate() + "\t\t"
                + Math.round(book.getPrice()) + "원\t\t");
        if(book.getReview() == null) {
          System.out.println("X");
        }
        else {
          System.out.println("O");
        }
        index++;
      }
    }
    else {
      System.out.println("완료한 숙박이 없습니다.");
    }
  }

  private void findHistoryAll(List<Book> resultBooks) {
    if(!(resultBooks.isEmpty())) {
      //전체
      System.out.println("\n===================== [전체] ======================");

      System.out.println("\t\t숙소명\t\t체크인\t\t\t\t\t체크아웃\t\t\t\t\t요금\t\t\t\t후기");
      int index = 1;
      for (Book book : resultBooks) {
        if(book.getStatus() == BookStatus.RESERVATION) {
          System.out.print(index + "\t\t"
                  + book.getHouseName() + "\t\t"
                  + book.getCheckInDate() + "\t\t"
                  + book.getCheckOutDate() + "\t\t"
                  + Math.round(book.getPrice()) + "원\t\t");
          System.out.println();
        }
        else {
          System.out.print(index + "\t\t"
                  + book.getHouseName() + "\t\t"
                  + book.getCheckInDate() + "\t\t"
                  + book.getCheckOutDate() + "\t\t"
                  + Math.round(book.getPrice()) + "원\t\t");
          if(book.getReview() == null) {
            System.out.println("X");
          }
          else {
            System.out.println("O");
          }
        }
        index++;
      }
    }
    else {
      System.out.println("\n조회할 숙박이 없습니다.");
    }
  }


  private double getTotalPrice(House house, int checkIn, int checkOut) {
    double totalPrice = 0;

    List<DateHouse> dateHouses = house.getDateHouses(checkIn, checkOut);
    for (DateHouse dateHouse : dateHouses) {
      totalPrice += dateHouse.getDateCharge();
    }

    return totalPrice;

  }

  public void addComments(Book book, StarScore starScore, String comments) {
    book.setStatus(BookStatus.COMPLETE);
    System.out.println();
    System.out.println(book.getHouseName() + "에 대한 후기를 등록합니다.");
    System.out.println(">> " + comments);
    Review review = new Review(book, starScore, comments);
    book.setReview(review);
    book.getDateHouses().get(0).getHouse().getHost().setTotalMonthAmount(book.getDateHouses().get(0).getHouse().getHost().getTotalMonthAmount() + book.getPrice());
  }
}

