package security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.domain.*;
import security.repository.SecurityRepository;
import java.util.UUID;

@Service
public class SecurityServiceImpl implements SecurityService{

    @Autowired
    private SecurityRepository securityRepository;

    @Override
    public GetAllSecurityConfigResult findAllSecurityConfig(){
        GetAllSecurityConfigResult result = new GetAllSecurityConfigResult();
        result.setStatus(true);
        result.setMessage("Success");
        result.setResult(securityRepository.findAll());
        return result;
    }

    @Override
    public CreateSecurityConfigResult addNewSecurityConfig(CreateSecurityConfigInfo info){
        SecurityConfig sc = securityRepository.findByName(info.getName());
        CreateSecurityConfigResult result = new CreateSecurityConfigResult();
        if(sc != null){
            result.setStatus(false);
            result.setMessage("Security Config Already Exist");
            result.setSecurityConfig(null);
        }else{
            SecurityConfig config = new SecurityConfig();
            config.setId(UUID.randomUUID());
            config.setName(info.getName());
            config.setValue(info.getValue());
            config.setDescription(info.getDescription());
            securityRepository.save(config);
            result.setStatus(true);
            result.setMessage("Success");
            result.setSecurityConfig(config);
        }
        return result;
    }

    @Override
    public UpdateSecurityConfigResult modifySecurityConfig(UpdateSecurityConfigInfo info){
        SecurityConfig sc = securityRepository.findById(UUID.fromString(info.getId()));
        UpdateSecurityConfigResult result = new UpdateSecurityConfigResult();
        if(sc == null){
            result.setStatus(false);
            result.setMessage("Security Config Not Exist");
            result.setResult(null);
        }else{
            sc.setName(info.getName());
            sc.setValue(info.getValue());
            sc.setDescription(info.getDescription());
            securityRepository.save(sc);
            result.setStatus(true);
            result.setMessage("Success");
            result.setResult(sc);
        }
        return result;
    }

    @Override
    public DeleteConfigResult deleteSecurityConfig(DeleteConfigInfo info){
        securityRepository.deleteById(UUID.fromString(info.getId()));
        SecurityConfig sc = securityRepository.findById(UUID.fromString(info.getId()));
        DeleteConfigResult result = new DeleteConfigResult();
        if(sc == null){
            result.setStatus(true);
            result.setMessage("Success");
        }else{
            result.setStatus(false);
            result.setMessage("Reason Not clear");
        }
        return result;
    }

    @Override
    public CheckResult check(CheckInfo info){
        //1.前往订单服务获取自己的订单

        return null;
    }
}
