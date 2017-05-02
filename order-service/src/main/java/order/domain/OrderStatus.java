package order.domain;

public enum OrderStatus {

    NOTPAID   (0,"未付款"),
    PAID      (1,"已付款"),
    COLLECTED (2,"已取票"),
    CHANGE    (3,"已改签"),
    CANCEL    (4,"已取消"),
    REFUNDS   (5,"已退票");

    private int code;
    private String name;

    OrderStatus(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode(){
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(int code){
        OrderStatus[] orderStatusSet = OrderStatus.values();
        for(OrderStatus orderStatus : orderStatusSet){
            if(orderStatus.getCode() == code){
                return orderStatus.getName();
            }
        }
        return orderStatusSet[0].getName();
    }

}
