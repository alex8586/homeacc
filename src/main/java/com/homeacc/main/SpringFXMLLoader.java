package com.homeacc.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

@Component
public class SpringFXMLLoader {

	@Autowired
    private ApplicationContext ctx;

    public FXMLLoader getLoader(String url) {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aClass) {
                    return ctx.getBean(aClass);
                }
            });
    	loader.setLocation(getClass().getResource(url));
    	return loader;
    }

}
