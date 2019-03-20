package UI;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.Navigator;;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.apache.shiro.subject.Subject;
import repository.JDBCUtil;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@PreserveOnRefresh
public class FirstTask extends UI {
    static {
        SLF4JBridgeHandler.install();
    }

    private final static Logger logger = Logger.getLogger(FirstTask.class.getName());

    private JDBCUtil db = JDBCUtil.getInstance();
    private Navigator navigator;

    protected static final String MAINVIEW = "main";
    protected static final String LOGIN = "login";

    public JDBCUtil getDb() {
        return db;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VaadinSession.getCurrent().getSession().setMaxInactiveInterval((int) TimeUnit.MINUTES.toSeconds(5));

        navigator = new Navigator(this, this);

        LoginView loginView = new LoginView(this);
        MainView mainView = new MainView(this);
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
}
