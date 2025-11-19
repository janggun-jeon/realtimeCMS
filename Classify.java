package realtimeCMS;
import java.util.*;

class Define { // c언어의 #define과 같은 개념. 로켓 배송은 기본 지연시간 1, 아닌 배송은 2로 설정
    public static final int RoketDefaultTime = 1;
    public static final int NomalDefaultTime = 2;
}

class ClassifyData { // 기본 데이터 속성. 지연시간과 주문을 가짐.
    int relatedtime;
    Order order;

    ClassifyData(int relatedtime, Order order){
        this.relatedtime = relatedtime;
        this.order =order;
    }
}

class Classify {
    public static ArrayList<ClassifyData> noneClassifiedOrders = new ArrayList<ClassifyData>(); // 처리가 지연된 주문들을 넣기 위해 임시 큐 구현
    public static ArrayList<ClassifyData> ClassifyingOrder = new ArrayList<ClassifyData>(4); // arraylist에 현재 분류작업중인 주문들을 넣는다.
    ToolsForClassifying TFC;

    Classify() {
        TFC = new ToolsForClassifying();
    }

    public void AddTempOrders(Order order) { // 객체 생성과 동시에 객체의 지연시간 할당
        int relatedtime = (order.rocketDelivery ? Define.RoketDefaultTime : Define.NomalDefaultTime);
        ClassifyData element = new ClassifyData(relatedtime,order);
        noneClassifiedOrders.add(element);
    }
}

class ToolsForClassifying { // 분류작업 클래스
    private static ArrayList<Order> ReturnOrder = new ArrayList<Order>();

    public static boolean TransferOrderToClassifying(ClassifyData classifyData){//리스트의 용량이 초과되지 않는다면 데이터를 넣고 참값을 반환한다.
        if (Classify.ClassifyingOrder.size() < 30){
            Classify.ClassifyingOrder.add(classifyData);
            classifyData.order.nextState(); // 다음 주문 상태로
            return true;
        }
        else {
           return false;
        }
    }
    public void ChangeState(){//큐가 빌때까지 분류작업에 대기중인 주문을 넘긴다. 만약 도중에 처리 용량(4)가 오버되면 반복문을 종료하고 나머지 주문은 그대로 큐에 남게 된다.
        boolean result;
        while ( !Classify.noneClassifiedOrders.isEmpty() ) {
            result = TransferOrderToClassifying(Classify.noneClassifiedOrders.get(0));
            if (result) {
                Classify.noneClassifiedOrders.remove(0);
            }
            else {
                break;
            }
        }
    }
    public ArrayList<Order> ClassifyOrders() { // 리스트의 주문들에 지연시간을 1감소 후, 지연시간이 0이면 반환리스트에 아니면 분류작업 리스트에 넣어, 마지막에 반환리스트를 반환.
        int index=0;
        ClassifyData temp; 
        if (ReturnOrder.size() != 0)
        ReturnOrder.clear();
        while( index < Classify.ClassifyingOrder.size() ) {
            temp=Classify.ClassifyingOrder.get(index);
            if (--temp.relatedtime==0) {
                ReturnOrder.add(temp.order);
                Classify.ClassifyingOrder.remove(index--);
            }
            index++;  
        }
        return ReturnOrder;
    }
}