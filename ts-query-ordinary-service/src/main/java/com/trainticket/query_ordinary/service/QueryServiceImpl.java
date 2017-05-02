package com.trainticket.query_ordinary.service;

import com.trainticket.query_ordinary.domain.Information;
import com.trainticket.query_ordinary.domain.Item;
import com.trainticket.query_ordinary.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Chenjie Xu on 2017/3/1.
 */
@Service
public class QueryServiceImpl implements QueryService{

    @Autowired
    private ItemRepository repository;

    @Override
    public List<Item> query(Information info){

        List<Item> list = repository.findByStartingAndDestination(info.getStarting(),info.getDestination());
        Date date = info.getDate();
        System.out.println("print date:"+date.toString());

        Iterator<Item> sListIterator = list.iterator();
        while(sListIterator.hasNext()) {
            Item item = sListIterator.next();
            System.out.println(item.getDepartureTime());
            if (!areSameDay(date, item.getDepartureTime())) {
                sListIterator.remove();
            }
        }

        return list;

    }

    public static boolean areSameDay(Date dateA,Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                &&  calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }
}
