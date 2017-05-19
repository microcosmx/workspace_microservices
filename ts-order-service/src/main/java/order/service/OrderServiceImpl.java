package order.service;

import com.google.gson.Gson;
import order.domain.*;
import order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order findOrderById(UUID id){
        return orderRepository.findById(id);
    }

    @Override
    public CreateOrderResult create(Order order){
        System.out.println("[OrderService][CreateOrder] Ready Create Order" + new Gson().toJson(order));
        ArrayList<Order> accountOrders = orderRepository.findByAccountId(order.getAccountId());
        CreateOrderResult cor = new CreateOrderResult();
        if(accountOrders.contains(order)){
            System.out.println("[Order-Create-Service][OrderCreate] Fail.Order already exists.");
            cor.setStatus(false);
            cor.setMessage("Order already exist");
            cor.setOrder(null);
        }else{
            order.setId(UUID.randomUUID());
            orderRepository.save(order);
            System.out.println("[Order-Create-Service][OrderCreate] Success.");
            cor.setStatus(true);
            cor.setMessage("Success");
            cor.setOrder(order);
        }
        return cor;
    }

    @Override
    public Order alterOrder(OrderAlterInfo oai){
        UUID oldOrderId = oai.getPreviousOrderId();
        Order oldOrder = findOrderById(oldOrderId);
        if(oldOrder == null){
            System.out.println("[Order-Alter-Service][AlterOrder] Fail.Order do not exist.");
            return null;
        }
        oldOrder.setStatus(OrderStatus.CANCEL.getCode());
        saveChanges(oldOrder);
        create(oai.getNewOrderInfo());
        System.out.println("[Order-Alter-Service][AlterOrder] Success.");
        return oai.getNewOrderInfo();
    }

    @Override
    public ArrayList<Order> queryOrders(QueryInfo qi){
        //1.Get all orders of the user
        ArrayList<Order> list = orderRepository.findByAccountId(qi.getAccountId());
        //2.Check is these orders fit the requirement/
        if(qi.isEnableStateQuery() || qi.isEnableBoughtDateQuery() || qi.isEnableTravelDateQuery()){
            ArrayList<Order> finalList = new ArrayList<>();
            for(Order tempOrder : list){
                boolean statePassFlag = false;
                boolean boughtDatePassFlag = false;
                boolean travelDatePassFlag = false;
                //3.Check order state requirement.
                if(qi.isEnableStateQuery()){
                    if(tempOrder.getStatus() != qi.getState()){
                        statePassFlag = false;
                    }else{
                        statePassFlag = true;
                    }
                }else{
                    statePassFlag = true;
                }
                //4.Check order travel date requirement.
                if(qi.isEnableTravelDateQuery()){
                    if(tempOrder.getTravelDate().before(qi.getTravelDateEnd()) &&
                            tempOrder.getTravelDate().after(qi.getBoughtDateStart())){
                        travelDatePassFlag = true;
                    }else{
                        travelDatePassFlag = false;
                    }
                }else{
                    travelDatePassFlag = true;
                }
                //5.Check order bought date requirement.
                if(qi.isEnableBoughtDateQuery()){
                    if(tempOrder.getBoughtDate().before(qi.getBoughtDateEnd()) &&
                            tempOrder.getBoughtDate().after(qi.getBoughtDateStart())){
                        boughtDatePassFlag = true;
                    }else{
                        boughtDatePassFlag = false;
                    }
                }else{
                    boughtDatePassFlag = true;
                }
                //6.check if all requirement fits.
                if(statePassFlag && boughtDatePassFlag && travelDatePassFlag){
                    finalList.add(tempOrder);
                }
            }
            System.out.println("[Order-Query-Service][QueryOrder] Get order num:" + finalList.size());
            return finalList;
        }else{
            System.out.println("[Order-Query-Service][QueryOrder] Get order num:" + list.size());
            return list;
        }
    }

    @Override
    public ChangeOrderResult saveChanges(Order order){
        Order oldOrder = findOrderById(order.getId());
        ChangeOrderResult cor = new ChangeOrderResult();
        if(oldOrder == null){
            System.out.println("[Order-Modify-Service][ModifyOrder] Fail.Order not found.");
            cor.setStatus(false);
            cor.setMessage("Order Not Found");
            cor.setOrder(null);
        }else{
            oldOrder.setAccountId(order.getAccountId());
            oldOrder.setBoughtDate(order.getBoughtDate());
            oldOrder.setTravelDate(order.getTravelDate());
            oldOrder.setTravelTime(order.getTravelTime());
            oldOrder.setCoachNumber(order.getCoachNumber());
            oldOrder.setSeatClass(order.getSeatClass());
            oldOrder.setSeatNumber(order.getSeatNumber());
            oldOrder.setFrom(order.getFrom());
            oldOrder.setTo(order.getTo());
            oldOrder.setStatus(order.getStatus());
            oldOrder.setTrainNumber(order.getTrainNumber());
            oldOrder.setPrice(order.getPrice());
            orderRepository.save(oldOrder);
            System.out.println("[Order-Modify-Service] Success.");
            cor.setOrder(oldOrder);
            cor.setStatus(true);
            cor.setMessage("Success");
        }
        return cor;
    }

    @Override
    public CancelOrderResult cancelOrder(CancelOrderInfo coi){
        UUID orderId = coi.getOrderId();
        Order oldOrder = orderRepository.findById(orderId);
        CancelOrderResult cor = new CancelOrderResult();
        if(oldOrder == null){
            System.out.println("[Cancel-Order-Service][CancelOrder] Fail.Order not found.");
            cor.setStatus(false);
            cor.setMessage("Order Not Found");
            cor.setOrder(null);

        }else{
            oldOrder.setStatus(OrderStatus.CANCEL.getCode());
            orderRepository.save(oldOrder);
            System.out.println("[Cancel-Order-Service][CancelOrder] Success.");
            cor.setStatus(true);
            cor.setMessage("Success");
            cor.setOrder(oldOrder);
        }
        return cor;
    }

    @Override
    public  CalculateSoldTicketResult queryAlreadySoldOrders(CalculateSoldTicketInfo csti){
        ArrayList<Order> orders = orderRepository.findByTravelDateAndTrainNumber(csti.getTravelDate(),csti.getTrainNumber());
        CalculateSoldTicketResult cstr = new CalculateSoldTicketResult();
        cstr.setTravelDate(csti.getTravelDate());
        cstr.setTrainNumber(csti.getTrainNumber());
        System.out.println("[OrderService][CalculateSoldTicket] Get Orders Number:" + orders.size());
        for(Order order : orders){
            if(order.getStatus() >= OrderStatus.CHANGE.getCode()){
                continue;
            }
            if(order.getSeatClass() == SeatClass.NONE.getCode()){
                cstr.setNoSeat(cstr.getNoSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.BUSINESS.getCode()){
                cstr.setBusinessSeat(cstr.getBusinessSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.FIRSTCLASS.getCode()){
                cstr.setFirstClassSeat(cstr.getFirstClassSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.SECONDCLASS.getCode()){
                cstr.setSecondClassSeat(cstr.getSecondClassSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.HARDSEAT.getCode()){
                cstr.setHardSeat(cstr.getHardSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.SOFTSEAT.getCode()){
                cstr.setSoftSeat(cstr.getSoftSeat() + 1);
            }else if(order.getSeatClass() == SeatClass.HARDBED.getCode()){
                cstr.setHardBed(cstr.getHardBed() + 1);
            }else if(order.getSeatClass() == SeatClass.SOFTBED.getCode()){
                cstr.setSoftBed(cstr.getSoftBed() + 1);
            }else if(order.getSeatClass() == SeatClass.HIGHSOFTBED.getCode()){
                cstr.setHighSoftBed(cstr.getHighSoftBed() + 1);
            }else{
                System.out.println("[OrderService][Calculate Sold Tickets] Seat class not exists. Order ID:" + order.getId());
            }
        }
        return cstr;
    }

}

