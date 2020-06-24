package com.example.mq.controller;

import com.example.mq.produce.QueueAProduce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Ciro
 * On 2020/6/23 15:30
 * @author Administrator
 */

@RestController
public class UserController {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    QueueAProduce queueAProduce;
    @RequestMapping(value = "user")
    public void userAdd(){
        queueAProduce.sendMsg(sdf.format(new Date()));
    }

}
