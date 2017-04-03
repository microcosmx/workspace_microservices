package com.trainticket.query_crh.service;

import com.trainticket.query_crh.domain.Information;
import com.trainticket.query_crh.domain.Item;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/3/1.
 */
public interface QueryService {

    List<Item> query(Information info);
}
