package UI;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class SecurityUtil {
    static {
//        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
//        DefaultSecurityManager securityManager = new DefaultSecurityManager(iniRealm);
//        SecurityUtils.setSecurityManager(securityManager);

//        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//        SecurityManager securityManager = factory.getInstance();
//        SecurityUtils.setSecurityManager(securityManager);

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }

    private SecurityUtil() {
    }

    public static Subject getCurrentUser() {
        return SecurityUtils.getSubject();
    }
}
