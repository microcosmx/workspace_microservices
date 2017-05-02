package com.trainticket.query_ordinary.service;

import com.trainticket.query_ordinary.domain.Information;
import com.trainticket.query_ordinary.domain.Item;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/3/1.
 */
public interface QueryService {

    List<Item> query(Information info);
}
