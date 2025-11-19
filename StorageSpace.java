package realtimeCMS; // Real-Time Courier Monitoring System
import java.lang.String;
import java.util.*;

class product { // 상품정보
    String productName; // 상품명
    int quantity; // 재고수량
    int volume; // 상품부피[L], 1개당

    public product(String productName, int quantity, int volume) {
        this.productName = productName;
        this.quantity = quantity;
        this.volume = volume; 
    }
}

class StorageSpace { // 창고공간 
    HashMap <Integer, product> inventory = new HashMap <Integer, product> (); // 재고를 고유식별번호[=key]로 관리할 자료구조
    LinkedList <product> queue = new LinkedList <product> (); // 발주할 product들의 대기열

    public StorageSpace() {
        inventory.put(1, new product("             [표백제]", 100, 2));
        inventory.put(2, new product("           [주방세제]", 100, 1));
        inventory.put(3, new product("           [골프가방]", 100, 152));
        inventory.put(4, new product("               [맥북]", 100, 1));
        inventory.put(5, new product("         [드럼세탁기]", 100, 520));
        inventory.put(6, new product("             [선풍기]", 100, 98));
        inventory.put(7, new product("    [무선 진공청소기]", 100, 3));
        inventory.put(8, new product("         [로봇청소기]", 100, 73));
        inventory.put(9, new product("         [김치냉장고]", 100, 875));
        inventory.put(10, new product("         [일반세탁기]", 100, 515));
        inventory.put(11, new product("         [무선마우스]", 100, 1));
        inventory.put(12, new product("        [건식 다리미]", 100, 5));
        inventory.put(13, new product("  [무게조절 덤벨세트]", 100, 20));
        inventory.put(14, new product("             [케틀벨]", 100, 22));
        inventory.put(15, new product("               [이불]", 100, 3));
        inventory.put(16, new product("         [5단 서랍장]", 100, 26));
        inventory.put(17, new product("           [매트리스]", 100, 700));
        inventory.put(18, new product("           [메뚜기쌀]", 100, 2));
        inventory.put(19, new product("    [냉동 대패삼겹살]", 100, 1));
        inventory.put(20, new product("  [닭볶음탕용 닭고기]", 100, 2));
        inventory.put(21, new product("               [짐볼]", 100, 151));
        inventory.put(22, new product("      [맥심 커피믹스]", 100, 2));
        inventory.put(23, new product("           [소고기죽]", 100, 1));
        inventory.put(24, new product("        [여행용 백팩]", 100, 45));
        inventory.put(25, new product("           [사양벌꿀]", 100, 2));
        inventory.put(26, new product("           [흑당시럽]", 100, 3));
        inventory.put(27, new product("      [밀피터리 백팩]", 100, 38));
        inventory.put(28, new product("   [방울토마토 1봉지]", 100, 1));
        inventory.put(29, new product("         [키위 1봉지]", 100, 2));
        inventory.put(30, new product("     [블루베리 1봉지]", 100, 1));
        inventory.put(31, new product("       [오렌지 1봉지]", 100, 1));
        inventory.put(32, new product("           [수박 1통]", 100, 7));
        inventory.put(33, new product("         [참외 1박스]", 100, 5));
        inventory.put(34, new product("         [레몬 1봉지]", 100, 1));
        inventory.put(35, new product("         [사과 1봉지]", 100, 3));
        inventory.put(36, new product("         [포도 1봉지]", 100, 2));
        inventory.put(37, new product("             [캐리어]", 100, 107));
        inventory.put(38, new product("         [왕교자만두]", 100, 1));
        inventory.put(39, new product("           [배추김치]", 100, 2));
        inventory.put(40, new product("      [치킨너켓 1봉지", 100, 1));
        inventory.put(41, new product("           [클렌징폼]", 100, 1));
        inventory.put(42, new product("        [비누 10묶음]", 100, 1));
        inventory.put(43, new product("           [탈모샴푸]", 100, 1));
        inventory.put(44, new product("             [컬크림]", 100, 1));
        inventory.put(45, new product("       [탈색제 1박스]", 100, 3));
        inventory.put(46, new product("               [치약]", 100, 1));
        inventory.put(47, new product("         [섬유유연제]", 100, 9));
        inventory.put(48, new product("       [A4용지 100매]", 100, 1));
        inventory.put(49, new product("    [호텔수건 10묶음]", 100, 2));
        inventory.put(50, new product("          [특란 30구]", 100, 2));
        inventory.put(51, new product("        [왕계란 30구]", 100, 2));
        inventory.put(52, new product("           [생유산균]", 100, 1));
        inventory.put(53, new product("     [닭가슴살 1봉지]", 100, 2));
        inventory.put(54, new product("    [참치통조림 12팩]", 100, 2));
        inventory.put(55, new product("         [비타민 1통]", 100, 1));
        inventory.put(56, new product("             [기저귀]", 100, 1));
        inventory.put(57, new product("        [수딩 파우더]", 100, 1));
        inventory.put(58, new product("  [무선 전동드라이버]", 100, 1));
        inventory.put(59, new product("             [디퓨저]", 100, 1));
        inventory.put(60, new product("       [모기스프레이]", 100, 1));        
        inventory.put(61, new product("             [건조대]", 100, 1));
        inventory.put(62, new product("               [샴푸]", 100, 1));
        inventory.put(63, new product("         [욕실세정제]", 100, 1));
        inventory.put(64, new product("           [액체세제]", 100, 3));
        inventory.put(65, new product("             [휴지통]", 100, 9));
        inventory.put(66, new product("           [올리브유]", 100, 1));
        inventory.put(67, new product("         [설탕 1포대]", 100, 2));
        inventory.put(68, new product("             [텀블러]", 100, 2));
        inventory.put(69, new product("   [베이킹소다 1봉지]", 100, 2));
        inventory.put(70, new product("         [참기름 1병]", 100, 1));        
        inventory.put(71, new product("       [고춧가루 1병]", 100, 1));
        inventory.put(72, new product("       [다진마늘 1봉]", 100, 1));
        inventory.put(73, new product("    [실내 감시카메라]", 100, 1));
        inventory.put(74, new product("      [노트북 거치대]", 100, 2));
        inventory.put(75, new product("      [게이밍 장패드]", 100, 1));
        inventory.put(76, new product("         [전자체중계]", 100, 2));
        inventory.put(77, new product("         [갤럭시워치]", 100, 1));
        inventory.put(78, new product("  [모짜렐라 치즈 1봉]", 100, 1));
        inventory.put(79, new product("       [휘핑크림 1통]", 100, 1));
        inventory.put(80, new product("          [4k UHD TV]", 100, 138));       
        inventory.put(81, new product("      [양문형 냉장고]", 100, 846));
        inventory.put(82, new product("         [미니냉장고]", 100, 24));
        inventory.put(83, new product(" [무선 키보드&마우스]", 100, 2));
        inventory.put(84, new product("      [공기청정 필터]", 100, 7));
        inventory.put(85, new product("           [호두 1병]", 100, 1));
        inventory.put(86, new product("         [땅콩 1봉지]", 100, 1));
        inventory.put(87, new product("    [대용량 빨래가방]", 100, 45));
        inventory.put(88, new product("         [분리수거함]", 100, 50));
        inventory.put(89, new product("         [살균소독제]", 100, 1));
        inventory.put(90, new product("      [배수구 세정제]", 100, 2));
        inventory.put(91, new product("           [압력밥솥]", 100, 34));
        inventory.put(92, new product("           [바디워시]", 100, 1));
        inventory.put(93, new product("             [헤드셋]", 100, 4));
        inventory.put(94, new product("        [자바 입문서]", 100, 1));
        inventory.put(95, new product("       [C언어 입문서]", 100, 1));
        inventory.put(96, new product("         [C++ 입문서]", 100, 1));
        inventory.put(97, new product("      [파이썬 입문서]", 100, 1));
        inventory.put(98, new product("[자바스크립트 입문서]", 100, 1));
        inventory.put(99, new product("[웹프로그래밍 입문서]", 100, 1));
        inventory.put(100, new product("    [자료구조 입문서]", 100, 1));
    }

    public LinkedList<product> request() { // 발주
        return this.queue; // 납품공간에 발주할 상품정보의 리스트를 반환
    }
    public boolean shipment(Order ord) { // 출하
        if (inventory.get(ord.key).quantity > ord.quantity) { // 재고수량 >= 주문수량
            inventory.get(ord.key).quantity -= ord.quantity; // 재고 삭감
            ord.nextState(); // 주문상태 변경
            if (inventory.get(ord.key).quantity == 0) { // 재고가 0
                queue.add( inventory.get(ord.key) ); // 발주 대기열에 상품정보를 추가
            }
            return true; // 출하성공
        }
        else { // 재고부족
            ord.sold_out(); // 주문상태 변경
            queue.add(inventory.get(ord.key));
            return false; // 출하실패
        }
    }
}