package UI;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class SecurityUtil {
    static {
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        DefaultSecurityManager securityManager = new DefaultSecurityManager(iniRealm);
        SecurityUtils.setSecurityManager(securityManager);
    }

    private SecurityUtil() {
    }

    public static Subject getCurrentUser() {
        return SecurityUtils.getSubject();
    }
}
