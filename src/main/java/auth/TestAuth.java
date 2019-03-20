package auth;

import model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.SimplePrincipalCollection;
import repository.JDBCUtil;

public class TestAuth implements Authenticator {
    private JDBCUtil jdbcUtil = JDBCUtil.getInstance();

    @Override
    public AuthenticationInfo authenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        String login = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());

        System.out.println("login: " + login);
        System.out.println("password: " + password);

        User user = null;
        SimpleAuthenticationInfo ret = new SimpleAuthenticationInfo();
        try {
            user = jdbcUtil.authUser(login, password);
        } catch (Exception e) {
        }
        ret.setPrincipals(new SimplePrincipalCollection(user, "client"));

        return ret;
    }
}
