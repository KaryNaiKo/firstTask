package auth;


import com.example.hibernate.entity.User;
import dao.UserDAO;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.SimplePrincipalCollection;


public class TestAuth implements Authenticator {
    private UserDAO userDAO = new UserDAO();

    @Override
    public AuthenticationInfo authenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        String login = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());

        System.out.println("login: " + login);
        System.out.println("password: " + password);

        User user = null;
        SimpleAuthenticationInfo ret = new SimpleAuthenticationInfo();
        try {
            user = userDAO.authUser(login, password);
        } catch (Exception e) {
        }
        ret.setPrincipals(new SimplePrincipalCollection(user, "client"));

        return ret;
    }
}
