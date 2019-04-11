package com.example.vaadin.UI;

import com.example.jedis.JedisUtil;
import com.example.vaadin.UI.forms.Tab1Form;
import com.example.vaadin.UI.forms.Tab2Form;
import com.example.vaadin.auth.SecurityUtil;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

public class MainView extends VerticalLayout implements View {
    private TabSheet tabsheet = new TabSheet();
    private JedisUtil jedis = JedisUtil.getJedisUtil();
    private Tab1Form tab1Form = new Tab1Form(this);
    private Tab2Form tab2Form = new Tab2Form(this);

    public MainView() {
        setSizeFull();
        initMain();
    }

    private void initMain() {
        Button logout = new Button("Logout");
        logout.addClickListener((Button.ClickListener) event -> {
            SecurityUtil.getCurrentUser().logout();
            this.getUI().getNavigator().navigateTo(Main.LOGIN);
            Page.getCurrent().reload();
        });

        addComponent(logout);
        setComponentAlignment(logout, Alignment.MIDDLE_RIGHT);
        setExpandRatio(logout, 0.5f);

        HorizontalLayout content = new HorizontalLayout();
        Label label = new Label("To jedis");
        TextField toJedis = new TextField();


        Button send = new Button("Send");
        send.addClickListener((Button.ClickListener) event -> {
            String str = toJedis.getValue();
            jedis.add(str);
            toJedis.clear();
        });

        content.addComponents(label, toJedis, send);
        addComponent(content);
        setExpandRatio(content, 0.5f);

        VerticalLayout tab1 = new VerticalLayout();
        tab1.setCaption("tab1");
        tab1.setSizeFull();
        tab1.setMargin(false);
        tab1.addComponent(tab1Form);
        tabsheet.addTab(tab1);

        VerticalLayout tab2 = new VerticalLayout();
        tab2.setCaption("tab2");
        tab2.setSizeFull();
        tab2.setMargin(false);
        tab2.addComponent(tab2Form);
        tabsheet.addTab(tab2);

        tabsheet.setSizeFull();

        addComponent(tabsheet);
        setExpandRatio(tabsheet, 9);
    }

    public void updateList() {
        tab1Form.updateList();
    }

    public void addWindow(String s, Component form) {
        closeAllWindow();

        Window window = new Window(s);
        window.setContent(form);
        window.center();
        this.getUI().addWindow(window);
    }

    public void closeAllWindow() {
        for (Window window : getUI().getWindows()) getUI().removeWindow(window);
    }
}