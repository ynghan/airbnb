package jpabook.start.service;

import jpabook.start.domain.house.House;
import jpabook.start.domain.house.HouseType;
import jpabook.start.domain.user.Guest;
import jpabook.start.repository.HouseRepository;
import jpabook.start.repository.userRepository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {

  private HouseRepository houseRepository;
  private GuestRepository guestRepository;

  public GuestService(EntityManager em) {
    houseRepository = new HouseRepository(em);
    guestRepository = new GuestRepository(em);
  }

  public void save(Guest guest) {
    guestRepository.save(guest);
  }

  /**
   * 검사 항목 3) 게스트는 조건에 맞는 숙소를 조회할 수 있다. (체크인 날짜, 체크아웃 날짜, 인원, 숙소 타입)
   * findHouse(checkInDate, checkOutDate, 5, houseType)
   * findHouse(checkInDate, checkOutDate, null, null)
   * findHouse(checkInDate, checkOutDate, 7, null)
   */

  public void findHouse(int checkIn, int checkOut, int capacity, HouseType houseType) {
    List<House> findHouses = houseRepository.findByDateRange(checkIn, checkOut);
    System.out.println("============= " + checkIn + "일 부터 " + checkOut + "일 까지 예약 가능한 숙소 " + "================");
    for (House findHouse : findHouses) {
      if(findHouse.getCapacity() >= capacity && findHouse.getHouseType() == houseType) {
        System.out.println(findHouse);
      }
    }
    System.out.println("======================================================================");
  }

  public void findHouse(int checkIn,int checkOut, int capacity) {
    List<House> findHouses = houseRepository.findByDateRange(checkIn, checkOut);
    System.out.println("============= " + checkIn + "일 부터 " + checkOut + "일 까지 예약 가능한 숙소 " + "================");
    for (House findHouse : findHouses) {
      if(findHouse.getCapacity() >= capacity) {
        System.out.println(findHouse.getName());
      }
    }
    System.out.println("======================================================================");
  }

  public void findHouse(int checkIn, int checkOut) {
    List<House> findHouses = houseRepository.findByDateRange(checkIn, checkOut);
    System.out.println("============= " + checkIn + "일 부터 " + checkOut + "일 까지 예약 가능한 숙소 " + "================");
    for (House findHouse : findHouses) {
      System.out.println(findHouse.getHouseType() + ", " + findHouse.getName() + ", " + findHouse.getCharge() * (checkOut - checkIn + 1));
    }
    System.out.println("======================================================================");
  }

  /**
   * 검색된 숙소 리스트는 가격(총가격), 별점 등의 기준으로 내림차순 정렬이 가능하다.
   */
  public void sortByPrice(List<House> houses, int totalPrice) {

  }
  public void sortByStarPoint(List<House> houses) {

  }

  /**
   * 검색 조건에 맞는 숙소는 (숙소 유형, 이름, 총가격, 평균 별점) 정보를 보여준다.
   */
  public void sortByStarPoint() {

  }



  //==================================================================================================================

  public House getDetailHouse(String houseName) {
    House house = houseRepository.findByName(houseName);

    //1. 숙소 기본 정보
    System.out.println("수용인원 : " + house.getCapacity());
    System.out.println("방 개수 : " + house.getRoomCount());
    System.out.println("화장실 개수 : " + house.getBathroomCount());
    System.out.println("소개 : " + house.getIntroduction());
    System.out.println("시설 : " + house.getAmenitys().toString());
    System.out.println("주소 : " + house.getAddress().toString());

    //2. 숙소의 모든 별점과 리뷰
    //3. 선택한 달의 예약 현황
    //11월에 검사하면 11월달 정보를 표시

    return house;
  }

}
