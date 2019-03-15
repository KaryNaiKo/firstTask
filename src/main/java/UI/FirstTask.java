package UI;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import model.Data;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import repository.JDBCUtil;

import javax.servlet.annotation.WebServlet;

import java.util.List;

public class FirstTask extends UI {
    private JDBCUtil db = new JDBCUtil();
    private Navigator navigator;

    protected static final String MAINVIEW = "main";
    protected static final String LOGIN = "login";

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator = new Navigator(this, this);

        navigator.addView(LOGIN, new LoginView());
        navigator.addView(MAINVIEW, new MainView());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    public static class MyUIServlet extends VaadinServlet {
    }

    private class LoginView extends VerticalLayout implements View {
        public LoginView() {
            setSizeFull();

            FormLayout content = new FormLayout();
            TextField username = new TextField("Username");
            content.addComponent(username);
            PasswordField password = new PasswordField("Password");
            content.addComponent(password);

            Button send = new Button("Enter");
            send.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Subject currentUser = SecurityUtil.getCurrentUser();
                    if (!currentUser.isAuthenticated()) {
                        UsernamePasswordToken token
                                = new UsernamePasswordToken(username.getValue(), password.getValue());
                        token.setRememberMe(true);

                        try {
                            currentUser.login(token);
                            navigator.navigateTo(MAINVIEW);
                        } catch (AuthenticationException e) {
                            Notification.show("Incorrect data");
                        }
                    }
                }
            });
            content.addComponent(send);
            addComponent(content);
        }
    }

    private class MainView extends VerticalLayout implements View {
        public MainView() {

            Subject currentUser = SecurityUtil.getCurrentUser();
            if (!currentUser.isAuthenticated()) {
                navigator.navigateTo(LOGIN);
            }

            setSizeFull();

            List<Data> dataList = db.getData();

            Grid<Data> grid = new Grid<>();
            grid.setItems(dataList);
            grid.addColumn(Data::getId).setCaption("Id");
            grid.addColumn(Data::getData1).setCaption("Data1");
            grid.addColumn(Data::getData2).setCaption("Data2");

            grid.setHeight("100%");
            grid.setWidth("100%");

            grid.addItemClickListener((ItemClickListener<Data>) event -> {
                if (event.getMouseEventDetails().isDoubleClick()) {
                    Notification.show("Value: " + event.getItem());
                }
            });

            addComponent(grid);
        }
    }
}
