package realtimeCMS; // Real-Time Courier Monitoring System
import java.lang.String;
import java.util.*;


public class Main {
    static int[] best = new int[100];
    public static int probability(int n) { // (n >= 2)인 정수
        return (int)(Math.random()*n); // 0 ~ n-1 중 하나의 값을 반환
    }

    public static void SetUP(){//시뮬레이션 초기설정: 배달 임계값, 루프 설정(24시간,48시간,72시간), classifyorder의 처리작업단위? +a
        System.out.print("시뮬레이션 초기설정값을 입력(배달 시작 임계값, ");
    }

    public static void main(String [] args) {
        int time = 0; // 경과시간
        boolean delayflag = false;
        Scanner sc = new Scanner(System.in);
        StorageSpace stock = new StorageSpace(); // 창고공간 클래스 객체
        LinkedList<Order> orderQue = new LinkedList<Order>(); // 생성된 주문에 대한 대기열
        LinkedList<Order> requestQue = new LinkedList<Order>(); // orderQue에 order들을 다음 사이클때 출하하기 전에 현재 사이클에 저장하기 위한 대기열
        ReceivedSpace received = new ReceivedSpace(); // 납품공간 클래스 객체
        Classify process = new Classify(); // 작업공간 클래스 객체
        PickUp pick = new PickUp(); // 배차공간 클래스 객체
        //ShowData showdata= new ShowData();
        SetUp setup = new SetUp();
        //SetUP();
        System.out.print("느린 출력: 1 | 빠른 출력: 나머지 >>");
        delayflag= sc.nextInt()==1? true:false;

    do {
        System.out.printf("\n경과시간 : %d\n\n", time); // 경과시간 : time

        System.out.println("\n<배송차량>\n배송지[현재적재부피 / 최대적재부피] : 주문번호");
        {
            for(String s: Area.destination){
                System.out.printf("%s[%5d / %d] :", s, Area.contents.get(s).storage, SetUp.truckcapacity);
                Iterator <Order> it = Area.contents.get(s).orders.iterator();
                while (it.hasNext()) {
                    
                    System.out.print(it.next().orderNumber+" ");
                }
                System.out.println();
            }
           pick.TFP.SendOrder();
               
        }
        
        System.out.println("\n<상하차>\n주문번호 | 상품고유번호 | 상품명 | 수량 | 주소 | 적재부피 | 주문상태 | 로켓배송");
        {   
            pick.TFP.TransferTempSpaceToAreaOrder();
            pick.TFP.PickupOrder(process.TFC.ClassifyOrders()); 
        }

        System.out.println("\n<집품&포장>\n대기시간 | 주문번호 | 상품고유번호 | 상품명 | 수량 | 주소 | 적재부피 | 주문상태 | 로켓배송");
        {
            process.TFC.ChangeState();
            Iterator <ClassifyData> it = Classify.ClassifyingOrder.iterator();
            while (it.hasNext()) {
                ClassifyData data = it.next();
                System.out.print(data.relatedtime+"시간 | "); data.order.printState();
            }
        }





        System.out.println("\n<입고목록>\n상품명 | 발주량 | 파손량 | 입고량");
        {
            received.receive(stock.request()); 
        }

        System.out.println("\n<품절주문>\n주문번호 | 상품고유번호 | 상품명 | 수량 | 주소 | 적재부피 | 주문상태 | 로켓배송");
        { // 이전 타임의 주문된 order들을 먼저 현재 타임에 출하시도 후에 새 주문 접수
            Iterator <Order> it = requestQue.iterator(); // requestQue의 order들을 출하하기 위해 블록에서만 사용될 Iterator 참조를 선언해줌
            while(it.hasNext()) {
                Order order = it.next();
                Boolean shipment = stock.shipment(order) ;
                if (order.count <= 0) { // 품절된 경우
                    order.printState(); // 품절상태 출력
                }
                if (shipment) {
                    process.AddTempOrders(order); // 출하
                }
            }
            requestQue.clear(); // 객체의 삭제가 아닌 arraylist와 내부객체들의 연결을 삭제
        }

        System.out.println("\n<출하목록>\n주문번호 | 상품고유번호 | 상품명 | 수량 | 주소 | 적재부피 | 주문상태 | 로켓배송");
        {   
            Iterator <ClassifyData> it = Classify.noneClassifiedOrders.iterator();
            while (it.hasNext()) {
                it.next().order.printState();
            }
        }

        System.out.println("\n<신규주문>\n주문번호 | 상품고유번호 | 상품명 | 수량 | 주소 | 적재부피 | 주문상태 | 로켓배송");
        {
            for (int min = 0; min < 60; min++) {
                if ( SetUp.orderRate > probability(100) ) { 
                    Order order = new Order(ShowData.ordercount++, probability(100) + 1, stock, Area.destination[probability(10)], (probability(100) % 2 == 0));
                    orderQue.add(order);
                }
            }
        }
        
        {      
            Iterator <Order> it = orderQue.iterator(); // orderQue의 요소를 requestQue로 옮기기 위해 블록에서만 사용될 Iterator 참조를 선언해줌
            while (it.hasNext()) {
                requestQue.add(it.next());
            }
            orderQue.clear(); // 객체의 삭제가 아닌 arraylist와 내부객체들의 연결을 삭제
        }


        System.out.println();
        if(delayflag){
            try {
                Thread.sleep(5);
            } 
            catch (InterruptedException e) {
                System.err.format("IOException: %s%n", e);
            }
        }
    } while (time++ < SetUp.setuptime);
    ShowData.PrintResult(stock);

        
    //sc.close();
    



    }    
}

class ShowData {
    public static int ordercount = 1;
    public static int[] resultorder = new int[100];
    public static int sumdeliver =0;
    public static void PrintResult(StorageSpace stock){
        int best=0,key=-1;
        System.out.println("[각 상품 주문 횟수 | 주문 합 | 인기 상품]  ");
        System.out.println("<각 주문 생성 횟수>");
        for(int i=0; i<100; i++){
            if(i%4==0){
                System.out.print("\n| ");
            }
            System.out.print("              "+stock.inventory.get(i + 1).productName+": "+resultorder[i]+" |");
            if (best < resultorder[i]) {
                best = resultorder[i];
                key = i;
            } 
        }
        System.out.println("\n<총 주문수 : "+ordercount+"개>");
        System.out.println("<인기상품 : " + stock.inventory.get(key + 1).productName+">");
        //double o = ShowData.ordercount;
        //double s = SetUp.setuptime;
        System.out.printf("<시간당 평균 주문수:%.3f >\n",ShowData.ordercount/((double)SetUp.setuptime));
        //System.out.printf("<시간당 평균 생성 주문:%.3f >\n",o/s);
        System.out.println("<총 배송출발 횟수:"+ sumdeliver+" >");
    }
}

class SetUp{
    private static Scanner sc;
    public static int setuptime;
    public static int truckcapacity;
    public static float sendstandard;
    public static int orderRate; // 분당 주문률[0 ~ 100 중 하나의 정수 : %], 나중에 사용자 입력이 가능할 수 있는 요소
    public static int basicrecievenumber;
    public static int breakage_rate;

    SetUp() {
        sc = new Scanner(System.in);
        System.out.println("<시뮬레이션 초기값 설정>");
        System.out.print("[시뮬레이션 실행 시간] 하루: 1 입력| 일주일: 나머지 숫자 입력 >>");
        setuptime= sc.nextInt()==1? 24:168;
        System.out.print("[배송트럭] 5톤트럭 : 1 입력| 8톤트럭 : 나머지 숫자 입력 >>");
        truckcapacity= sc.nextInt()==1? 15000:25000;
        System.out.print("[주문가능 최대수량] >>");
        sendstandard= sc.nextFloat();
        System.out.print("[분당 주문률] >>");
        orderRate=sc.nextInt();
        System.out.print("[기본 납품 개수] >>");
        basicrecievenumber= sc.nextInt();
        System.out.print("[최대 파손률] >>");
        breakage_rate=sc.nextInt();
        System.out.println("<시뮬레이션 초기값 설정 종료>");
        
    }
}
