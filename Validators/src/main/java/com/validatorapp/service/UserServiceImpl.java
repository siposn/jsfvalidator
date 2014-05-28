package com.validatorapp.service;

import com.validatorapp.entity.UserData;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;

import javax.inject.Named;


@Named
@Stateful
public class UserServiceImpl implements UserService {
    
    List<UserData> list = new ArrayList<>();
    
    @Override
    public boolean save(UserData data) {
        list.add(data);
        return !list.isEmpty();
    }
    
    @PreDestroy
    public void clear() {
        list.clear();
    }
}
