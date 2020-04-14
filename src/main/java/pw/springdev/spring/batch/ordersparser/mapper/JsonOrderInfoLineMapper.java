package pw.springdev.spring.batch.ordersparser.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;
import pw.springdev.spring.batch.ordersparser.domain.Order;
import pw.springdev.spring.batch.ordersparser.domain.OrderInfo;
import pw.springdev.spring.batch.ordersparser.domain.Result;

import static pw.springdev.spring.batch.ordersparser.constant.Constants.JSON_LINE_PATTERN;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
public class JsonOrderInfoLineMapper implements LineMapper<OrderInfo> {

    private final MultiResourceItemReader delegator;

    public JsonOrderInfoLineMapper(MultiResourceItemReader delegator) {
        this.delegator = delegator;
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OrderInfo mapLine(String line, int lineNumber) {

        OrderInfo orderInfo = new OrderInfo();

        Resource currentResource = delegator.getCurrentResource();
        String[] fileName = currentResource.getFilename().split("/");

        orderInfo.setFilename(fileName[fileName.length - 1]);
        orderInfo.setLine((long) lineNumber);

        Order order;

        try {
            order = objectMapper.readValue(line, Order.class);
        } catch (Exception exception) {
            orderInfo.setResult(Result.CORRUPTED_JSON_LINE);
            return orderInfo;
        }

        if(!line.matches(JSON_LINE_PATTERN)) {
            orderInfo.setResult(Result.CORRUPTED_JSON_LINE);
            return orderInfo;
        }

        orderInfo.setId(order.getOrderId());
        orderInfo.setAmount(order.getAmount());
        orderInfo.setCurrency(order.getCurrency());
        orderInfo.setComment(order.getComment());
        orderInfo.setResult(Result.OK);

        return orderInfo;
    }
}
