package UI;

import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

public class LoginView extends VerticalLayout implements View {

    public LoginView() {
        init();
    }

    private void init() {
        setSizeFull();

        FormLayout content = new FormLayout();
        TextField username = new TextField("Username");
        content.addComponent(username);
        PasswordField password = new PasswordField("Password");
        content.addComponent(password);

        Button send = new Button("Enter");
        send.addClickListener((Button.ClickListener) event -> {
            Subject currentUser = SecurityUtil.getCurrentUser();
            if (!currentUser.isAuthenticated()) {
                UsernamePasswordToken token
                        = new UsernamePasswordToken(username.getValue(), password.getValue());
                token.setRememberMe(true);

                try {
                    currentUser.login(token);
                    this.getUI().getNavigator().navigateTo(FirstTask.MAINVIEW);
                } catch (AuthenticationException e) {
                    Notification.show("Incorrect data");
                }
            }
        });
        content.addComponent(send);
        addComponent(content);
    }
}