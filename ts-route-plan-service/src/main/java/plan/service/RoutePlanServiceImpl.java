package plan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import plan.domain.GetRoutePlanInfo;
import plan.domain.RoutePlanResults;

@Service
public class RoutePlanServiceImpl implements RoutePlanService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public RoutePlanResults searchCheapestResult(GetRoutePlanInfo info) {
        return null;
    }

    @Override
    public RoutePlanResults searchQuickestResult(GetRoutePlanInfo info) {
        return null;
    }
}
