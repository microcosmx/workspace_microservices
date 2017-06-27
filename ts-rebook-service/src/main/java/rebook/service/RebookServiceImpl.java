package rebook.service;

import org.springframework.stereotype.Service;
import rebook.domain.RebookInfo;

/**
 * Created by Administrator on 2017/6/26.
 */
@Service
public class RebookServiceImpl implements RebookService{
    public boolean rebook(RebookInfo info){
        return true;
    }
}
