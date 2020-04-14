package pw.springdev.spring.batch.ordersparser.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import pw.springdev.spring.batch.ordersparser.domain.OrderInfo;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
@Service
public class OrderService {

    public String orderInfoToJson(OrderInfo orderInfo) {
        return new Gson().toJson(orderInfo);
    }
}
