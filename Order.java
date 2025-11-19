package realtimeCMS; // Real-Time Courier Monitoring System
import java.lang.String;

class State {
    static String process[] = {"품절됨", "주문됨", "출하중", "작업중", "적재됨", "배송됨"};
}

class Order extends State {
    int orderNumber; // 주문번호
    int key; // 상품식별번호
    String productName; // 상품명
    int quantity; // 주문수량
    String address; // 배송주소
    int totalvolume; // 총 부피[kg] = 상품부피 X 주문수량 == 적재량
    boolean rocketDelivery; // 로켓배송여부
    String orderState; // 주문상태
    int count; // process의 인덱스를 변경시키는 변수

    public Order(int orderNumber, int key, StorageSpace store, String address, boolean rocketDelivery) {
        this.orderNumber = orderNumber;
        this.key = key;    
        this.productName = store.inventory.get(key).productName;
        this.quantity = Main.probability(100);
        this.address = address;
        this.totalvolume = ( store.inventory.get(key).volume )*(this.quantity);
        this.rocketDelivery = rocketDelivery;
        this.count = 1;
        this.orderState = State.process[count];
        printState();
        ShowData.resultorder[this.key - 1]++;
    }
    
    public String nextState() { // 다음 주문상태로 전환
        return orderState = State.process[++count];       
    }
    public String sold_out() { // 품절
        return orderState = State.process[--count];
    }
    public void printState() { // 주문된 상품정보 출력
        System.out.printf("order%4d | key%4d | %s | %2d개 | %s | %5dL | %3s | %b\n", orderNumber, key, productName, quantity, address, totalvolume, orderState, rocketDelivery);
    }
}