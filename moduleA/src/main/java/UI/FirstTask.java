package UI;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.ui.UI;
import dao.UserDAO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.logging.Logger;

@PreserveOnRefresh
public class FirstTask extends UI {
    static {
        SLF4JBridgeHandler.install();
    }

    private final static Logger logger = Logger.getLogger(FirstTask.class.getName());

    private Navigator navigator;

    protected static final String MAINVIEW = "main";
    protected static final String LOGIN = "login";

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator = new Navigator(this, this);

        LoginView loginView = new LoginView();
        MainView mainView = new MainView();
        navigator.addView(LOGIN, loginView);
        navigator.addView(MAINVIEW, mainView);
        navigator.setErrorView(loginView);


        navigator.addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                Subject currentUser = SecurityUtil.getCurrentUser();
                if (currentUser.isAuthenticated()) {
                    return true;
                } else {
                    if (event.getNewView().equals(loginView)) {
                        return true;
                    } else {
                        navigator.navigateTo(LOGIN);
                        return false;
                    }
                }
            }
        });
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = FirstTask.class, productionMode = true, heartbeatInterval = 30, closeIdleSessions = true)
    public static class MyUIServlet extends VaadinServlet implements SessionDestroyListener {
        private ShiroFilter filter;
        private MethodHandle mm;

        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();

            getService().addSessionDestroyListener(this);
            IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro.ini");
            SecurityManager securityManager = factory.getInstance();
            SecurityUtils.setSecurityManager(securityManager);
            EnvironmentLoaderListener ell = new EnvironmentLoaderListener();
            ell.initEnvironment(this.getServletContext());
            filter = new ShiroFilter();
            filter.setServletContext(this.getServletContext());
            try {
                filter.init();


            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mm = MethodHandles.lookup().findSpecial(VaadinServlet.class, "service",
                        MethodType.methodType(void.class, HttpServletRequest.class, HttpServletResponse.class),
                        MyUIServlet.class);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            CustomizedSystemMessages messages = new CustomizedSystemMessages();
            messages.setSessionExpiredCaption("Время сессии истекло");
            messages.setSessionExpiredMessage("Кликните на это сообщение или нажмите ESC для продолжения");

            getService().setSystemMessagesProvider(e -> messages);

        }

        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

            req.setAttribute("sec", filter);

            MyUIServlet Super = this;

            filter.doFilter(req, res, new FilterChain() {
                @Override
                public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse)
                        throws IOException, ServletException {
                    try {
                        mm.invoke(Super, servletRequest, servletResponse);
                    } catch (Exception ex) {
//                        ex.printStackTrace();
                    } catch (Throwable throwable) {
//                        throwable.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void sessionDestroy(SessionDestroyEvent event) {
            if (SecurityUtils.getSubject().isAuthenticated()) {
                SecurityUtils.getSubject().logout();
            }
        }
    }
}
