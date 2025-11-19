package realtimeCMS; // Real-Time Courier Monitoring System
//import java.lang.String;
import java.util.*;

class ReceivedSpace { // 납품공간
    int realNumber; // 실제 납품개수
    public int breakage() { // 파손
        return (int)( ( ( Main.probability(101) )*( (double)SetUp.breakage_rate / 100 ) ) ); // 파손되는 개수 반환
        
    }
    public void disposal() { // 폐기
        this.realNumber = SetUp.basicrecievenumber - breakage(); // 파손된 수량을 폐기하고 실제 납품개수를 결정
    }
    public void receive(LinkedList<product> queue) { // 입고
        while ( !(queue.isEmpty()) ) { // 대기열이 비어있을 때까지
            disposal();
            product p = queue.remove(0);
            print_received(p);
            p.quantity += this.realNumber; // 재고수량 추가
        }
    }
    public void print_received(product p) {
        System.out.printf("%s | %2d개 | %2d개 | %2d개\n", p.productName, SetUp.basicrecievenumber, SetUp.basicrecievenumber - realNumber, realNumber);
    }
}