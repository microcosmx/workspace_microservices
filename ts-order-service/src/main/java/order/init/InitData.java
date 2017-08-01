package order.init;

import order.domain.Order;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Chenjie Xu on 2017/6/5.
 */
@Component
public class InitData implements CommandLineRunner {
    @Autowired
    OrderService service;

    public void run(String... args)throws Exception{
        Order order = new Order();
        order.setId(UUID.fromString("5ad7750b-a68b-49c0-a8c0-32776b067703"));
        order.setBoughtDate(new Date());
        order.setTravelDate(new Date("Sat Jul 29 00:00:00 GMT+0800 2017"));
        order.setTravelTime(new Date("Mon May 04 09:02:00 GMT+0800 2013"));
        order.setAccountId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
        order.setContactsName("Contacts_One");
        order.setDocumentType(1);
        order.setContactsDocumentNumber("DocumentNumber_One");
        order.setTrainNumber("G1237");
        order.setCoachNumber(5);
        order.setSeatClass(2);
        order.setSeatNumber("FirstClass-30");
        order.setFrom("nanjing");
        order.setTo("shanghaihongqiao");
        order.setStatus(0);
        order.setPrice("100.0");
        service.initOrder(order);


        Order orderTwo = new Order();
        orderTwo.setId(UUID.fromString("8177ac5a-61ac-42f4-83f4-bd7b394d0531"));
        orderTwo.setBoughtDate(new Date());
        orderTwo.setTravelDate(new Date("Sat Jul 29 00:00:00 GMT+0800 2017"));
        orderTwo.setTravelTime(new Date("Mon May 04 09:01:00 GMT+0800 2013"));
        orderTwo.setAccountId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
        orderTwo.setContactsName("Contacts_One");
        orderTwo.setDocumentType(1);
        orderTwo.setContactsDocumentNumber("DocumentNumber_One");
        orderTwo.setTrainNumber("G1234");
        orderTwo.setCoachNumber(5);
        orderTwo.setSeatClass(2);
        orderTwo.setSeatNumber("FirstClass-30");
        orderTwo.setFrom("shanghai");
        orderTwo.setTo("beijing");
        orderTwo.setStatus(0);
        orderTwo.setPrice("100.0");
        service.initOrder(orderTwo);

        Order orderThree = new Order();
        orderThree.setId(UUID.fromString("d3c91694-d5b8-424c-9974-e14c89226e49"));
        orderThree.setBoughtDate(new Date());
        orderThree.setTravelDate(new Date("Sat Jul 29 00:00:00 GMT+0800 2017"));
        orderThree.setTravelTime(new Date("Mon May 04 09:00:00 GMT+0800 2013"));
        orderThree.setAccountId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
        orderThree.setContactsName("Contacts_One");
        orderThree.setDocumentType(1);
        orderThree.setContactsDocumentNumber("DocumentNumber_One");
        orderThree.setTrainNumber("G1235");
        orderThree.setCoachNumber(5);
        orderThree.setSeatClass(2);
        orderThree.setSeatNumber("FirstClass-30");
        orderThree.setFrom("shanghai");
        orderThree.setTo("beijing");
        orderThree.setStatus(0);
        orderThree.setPrice("100.0");
        service.initOrder(orderThree);

        Order orderFour = new Order();
        orderFour.setId(UUID.randomUUID());
        orderFour.setBoughtDate(new Date());
        orderFour.setTravelDate(new Date("Sat Jul 29 09:00:00 GMT+0800 2017"));
        orderFour.setTravelTime(new Date("Mon May 04 19:00:00 GMT+0800 2013"));
        orderFour.setAccountId(UUID.fromString("4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f"));
        orderFour.setContactsName("Contacts_One");
        orderFour.setDocumentType(1);
        orderFour.setContactsDocumentNumber("DocumentNumber_One");
        orderFour.setTrainNumber("G1235");
        orderFour.setCoachNumber(5);
        orderFour.setSeatClass(2);
        orderFour.setSeatNumber("FirstClass-30");
        orderFour.setFrom("shanghai");
        orderFour.setTo("beijing");
        orderFour.setStatus(0);
        orderFour.setPrice("100.0");
        service.initOrder(orderFour);

        Order userTwoOrder = new Order();
        userTwoOrder.setId(UUID.randomUUID());
        userTwoOrder.setBoughtDate(new Date());
        userTwoOrder.setTravelDate(new Date("Sat Jul 29 00:00:00 GMT+0800 2017"));
        userTwoOrder.setTravelTime(new Date("Mon May 04 09:02:00 GMT+0800 2013"));
        userTwoOrder.setAccountId(UUID.fromString("03830807-a1ac-4942-aa10-dbe6ed7c7bdf"));
        userTwoOrder.setContactsName("Contacts_One");
        userTwoOrder.setDocumentType(1);
        userTwoOrder.setContactsDocumentNumber("DocumentNumber_One");
        userTwoOrder.setTrainNumber("G1237");
        userTwoOrder.setCoachNumber(5);
        userTwoOrder.setSeatClass(2);
        userTwoOrder.setSeatNumber("FirstClass-30");
        userTwoOrder.setFrom("nanjing");
        userTwoOrder.setTo("shanghaihongqiao");
        userTwoOrder.setStatus(0);
        userTwoOrder.setPrice("100.0");
        service.initOrder(userTwoOrder);


        Order userTwoOrderTwo = new Order();
        userTwoOrderTwo.setId(UUID.randomUUID());
        userTwoOrderTwo.setBoughtDate(new Date());
        userTwoOrderTwo.setTravelDate(new Date("Sat Jul 29 00:00:00 GMT+0800 2017"));
        userTwoOrderTwo.setTravelTime(new Date("Mon May 04 09:01:00 GMT+0800 2013"));
        userTwoOrderTwo.setAccountId(UUID.fromString("03830807-a1ac-4942-aa10-dbe6ed7c7bdf"));
        userTwoOrderTwo.setContactsName("Contacts_One");
        userTwoOrderTwo.setDocumentType(1);
        userTwoOrderTwo.setContactsDocumentNumber("DocumentNumber_One");
        userTwoOrderTwo.setTrainNumber("G1234");
        userTwoOrderTwo.setCoachNumber(5);
        userTwoOrderTwo.setSeatClass(2);
        userTwoOrderTwo.setSeatNumber("FirstClass-30");
        userTwoOrderTwo.setFrom("shanghai");
        userTwoOrderTwo.setTo("beijing");
        userTwoOrderTwo.setStatus(0);
        userTwoOrderTwo.setPrice("100.0");
        service.initOrder(userTwoOrderTwo);

        Order userTwoOrderThree = new Order();
        userTwoOrderThree.setId(UUID.randomUUID());
        userTwoOrderThree.setBoughtDate(new Date());
        userTwoOrderThree.setTravelDate(new Date("Sat Jul 29 00:00:00 GMT+0800 2017"));
        userTwoOrderThree.setTravelTime(new Date("Mon May 04 09:00:00 GMT+0800 2013"));
        userTwoOrderThree.setAccountId(UUID.fromString("03830807-a1ac-4942-aa10-dbe6ed7c7bdf"));
        userTwoOrderThree.setContactsName("Contacts_One");
        userTwoOrderThree.setDocumentType(1);
        userTwoOrderThree.setContactsDocumentNumber("DocumentNumber_One");
        userTwoOrderThree.setTrainNumber("G1235");
        userTwoOrderThree.setCoachNumber(5);
        userTwoOrderThree.setSeatClass(2);
        userTwoOrderThree.setSeatNumber("FirstClass-30");
        userTwoOrderThree.setFrom("shanghai");
        userTwoOrderThree.setTo("beijing");
        userTwoOrderThree.setStatus(0);
        userTwoOrderThree.setPrice("100.0");
        service.initOrder(userTwoOrderThree);

        Order userTwoOrderFour = new Order();
        userTwoOrderFour.setId(UUID.randomUUID());
        userTwoOrderFour.setBoughtDate(new Date());
        userTwoOrderFour.setTravelDate(new Date("Sat Jul 29 09:00:00 GMT+0800 2017"));
        userTwoOrderFour.setTravelTime(new Date("Mon May 04 19:00:00 GMT+0800 2013"));
        userTwoOrderFour.setAccountId(UUID.fromString("03830807-a1ac-4942-aa10-dbe6ed7c7bdf"));
        userTwoOrderFour.setContactsName("Contacts_One");
        userTwoOrderFour.setDocumentType(1);
        userTwoOrderFour.setContactsDocumentNumber("DocumentNumber_One");
        userTwoOrderFour.setTrainNumber("G1235");
        userTwoOrderFour.setCoachNumber(5);
        userTwoOrderFour.setSeatClass(2);
        userTwoOrderFour.setSeatNumber("FirstClass-30");
        userTwoOrderFour.setFrom("shanghai");
        userTwoOrderFour.setTo("beijing");
        userTwoOrderFour.setStatus(0);
        userTwoOrderFour.setPrice("100.0");
        service.initOrder(userTwoOrderFour);
    }

}
//<td class="all_order_id noshow_component">a1674800-1cbb-49e5-ac1a-f193bde8a728</td>
//Order{"id":"5ad7750b-a68b-49c0-a8c0-32776b067703",
// "boughtDate":"Jun 21, 2017 11:52:22 AM",
// "travelDate":"Jun 23, 2017 12:00:00 AM",
// "travelTime":"May 4, 2013 2:51:52 PM",
// "accountId":"4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f",
// "contactsName":"Contacts_One",
// "documentType":1,
// "contactsDocumentNumber":"DocumentNumber_One",
// "trainNumber":"Z1234",
// "coachNumber":5,
// "seatClass":2,
// "seatNumber":"FirstClass-30",
// "from":"Shang Hai",
// "to":"Tai Yuan",
// "status":0,
// "price":100.0}
