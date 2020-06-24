package com.example.mq.consumer;

import com.example.mq.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ciro
 * On 2020/6/23 15:19
 * @author Administrator
 */
@Component
public class QueueConsumer {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RabbitListener(queues = RabbitMQConfig.QUEUE_A)
    @RabbitHandler
    public void process(Message message, Channel channel) throws IOException, ParseException {
        MessageProperties properties = message.getMessageProperties();
        String messageBody = new String(message.getBody());

        long tag = properties.getDeliveryTag();
        Date date = sdf.parse(messageBody);
        Date now = new Date();
        double sec = (now.getTime() - date.getTime())/1000;
        if (sec<(20)){
            System.out.println("消费失败 时间相差："+sec);
            channel.basicNack(tag, false, true);
        }else {
            System.out.println("消费成功 时间相差："+sec/60);
            // 成功消费确认
            channel.basicAck(tag, false);
        }


    }
}
