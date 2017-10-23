package plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RoutePlanServiceImpl implements RoutePlanService{

    @Autowired
    private RestTemplate restTemplate;
}
