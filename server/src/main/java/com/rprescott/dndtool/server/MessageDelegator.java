package com.rprescott.dndtool.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.rprescott.dndtool.server.processors.UserLoginProcessor;
import com.rprescott.dndtool.server.processors.UserRegistrationProcessor;
import com.rprescott.dndtool.sharedmessages.login.UserLogin;
import com.rprescott.dndtool.sharedmessages.registration.NewUserRegistration;

@Service
public class MessageDelegator implements ApplicationContextAware {

    // private static final Map<Class<?>, Class<? extends MessageProcessor<? extends ClientMessage, ? extends ServerMessage>>> MESSAGE_PROCESSOR_MAP;
    private static final Map<Class<?>, Class<? extends MessageProcessor>> MESSAGE_PROCESSOR_MAP;
    
    private ApplicationContext context;
    
    static {
        // MESSAGE_PROCESSOR_MAP = new HashMap<Class<?>, Class<? extends MessageProcessor<? extends ClientMessage, ? extends ServerMessage>>>();
        MESSAGE_PROCESSOR_MAP = new HashMap<Class<?>, Class<? extends MessageProcessor>>();
        MESSAGE_PROCESSOR_MAP.put(UserLogin.class, UserLoginProcessor.class);
        MESSAGE_PROCESSOR_MAP.put(NewUserRegistration.class, UserRegistrationProcessor.class);
    }
    
    public void delegateWork(Object object) {
        Class<? extends MessageProcessor> serviceClass = MESSAGE_PROCESSOR_MAP.get(object.getClass());
        if (serviceClass != null) {
            if (context.getBean(serviceClass) != null) {
                context.getBean(serviceClass).processMessage(object);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
