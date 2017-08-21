package sso.repository;

import org.springframework.data.repository.CrudRepository;
import sso.domain.LoginToken;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
public interface LoginTokenRepository extends CrudRepository<LoginToken, String> {
    List<LoginToken> findAll();
    LoginToken findByLoginToken(String loginToken);
    LoginToken findByAccountId(String accountId);
    void deleteByLoginToken(String loginToken);
}
