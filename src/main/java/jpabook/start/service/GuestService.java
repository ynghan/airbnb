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

  //수용인원으로 조회
  public void readByCapacity(int capacity) {
    List<House> byCapacityHouses = houseRepository.findByCapacity(capacity);
    System.out.println("===========" + capacity + "명 이상 수용 가능한 숙소 ============");
    for (House byCapacityHouse : byCapacityHouses) {
      System.out.println(byCapacityHouse.getName());
    }
    System.out.println("========================================");
  }
  //숙소 타입으로 조회
  public void readByHouseByType(HouseType houseType) {
    List<House> byTypeHouses = houseRepository.findByType(houseType);
    if(houseType == HouseType.ENTIRESPACE) {
      System.out.println("============== 전체공간 ================");
    } else if (houseType == HouseType.PARTSPACE){
      System.out.println("============== 부분공간 ================");
    }
    for (House byTypeHouse : byTypeHouses) {
      System.out.println(byTypeHouse.getName());
    }
    System.out.println("=======================================");

  }
  //체크인 체크아웃 날짜로 조회
  public void readByDateRange(int checkIn, int checkOut) {
    List<House> findHouses = houseRepository.findByDateRange(checkIn, checkOut);
    System.out.println("============= " + checkIn + "일 부터 " + checkOut + "일 까지 예약 가능한 숙소 " + "================");
    for (House findHouse : findHouses) {
      System.out.println(findHouse.getName());
    }
    System.out.println("======================================================================");
  }





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
