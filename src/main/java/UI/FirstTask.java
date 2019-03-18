package UI;

import com.vaadin.navigator.Navigator;;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.apache.shiro.subject.Subject;
import repository.JDBCUtil;

public class FirstTask extends UI {
    private JDBCUtil db = new JDBCUtil();
    private Navigator navigator;

    protected static final String MAINVIEW = "main";
    protected static final String LOGIN = "login";

    public JDBCUtil getDb() {
        return db;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
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
