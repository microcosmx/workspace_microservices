package other.init;

import other.domain.Order;
import other.domain.OrderStatus;
import other.domain.SeatClass;
import other.service.OrderOtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    OrderOtherService service;

    public void run(String... args)throws Exception{
//        Order order = new Order();
//        order.setId(UUID.fromString("5ad7750b-a68b-4920-a8c0-32976b067703"));
//        order.setBoughtDate(new Date());
//        order.setTravelDate(new Date("Wed Aug 02 00:00:00 GMT+0000 2017"));
//        order.setTravelTime(new Date("Mon May 04 09:51:52 GMT+0800 2013"));
//        order.setAccountId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
//        order.setContactsName("Contacts_One");
//        order.setDocumentType(1);
//        order.setContactsDocumentNumber("DocumentNumber_One");
//        order.setTrainNumber("Z1234");
//        order.setCoachNumber(5);
//        order.setSeatClass(SeatClass.FIRSTCLASS.getCode());
//        order.setSeatNumber("FirstClass-30");
//        order.setFrom("shanghai");
//        order.setTo("taiyuan");
//        order.setStatus(OrderStatus.PAID.getCode());
//        order.setPrice("1800.0");
//        service.initOrder(order);
//
//        Order order2 = new Order();
//        order2.setId(UUID.fromString("5ad7750b-a68b-4920-a8c0-64776b067753"));
//        order2.setBoughtDate(new Date());
//        order2.setTravelDate(new Date("Wed Aug 02 00:00:00 GMT+0000 2017"));
//        order2.setTravelTime(new Date("Mon May 04 09:51:52 GMT+0800 2013"));
//        order2.setAccountId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
//        order2.setContactsName("Contacts_One");
//        order2.setDocumentType(1);
//        order2.setContactsDocumentNumber("DocumentNumber_One");
//        order2.setTrainNumber("Z1235");
//        order2.setCoachNumber(5);
//        order2.setSeatClass(SeatClass.FIRSTCLASS.getCode());
//        order2.setSeatNumber("FirstClass-30");
//        order2.setFrom("shanghai");
//        order2.setTo("taiyuan");
//        order2.setStatus(OrderStatus.PAID.getCode());
//        order2.setPrice("1800.0");
//        service.initOrder(order2);
    }

}
