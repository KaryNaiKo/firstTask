package com.example.vaadin.UI;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class SecurityUtil {
    static {
//        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
//        DefaultSecurityManager securityManager = new DefaultWebSecurityManager(iniRealm);
//        SecurityUtils.setSecurityManager(securityManager);

//        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//        SecurityManager securityManager = factory.getInstance();
//        SecurityUtils.setSecurityManager(securityManager);

//        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//        SecurityManager securityManager = factory.getInstance();
//        SecurityUtils.setSecurityManager(securityManager);
    }

    private SecurityUtil() {
    }

    public static Subject getCurrentUser() {
        return SecurityUtils.getSubject();
    }
}
