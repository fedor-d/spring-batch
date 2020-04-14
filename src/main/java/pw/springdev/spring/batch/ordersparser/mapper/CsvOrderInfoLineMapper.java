package pw.springdev.spring.batch.ordersparser.mapper;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;
import pw.springdev.spring.batch.ordersparser.domain.Order;
import pw.springdev.spring.batch.ordersparser.domain.OrderInfo;
import pw.springdev.spring.batch.ordersparser.domain.Result;

import java.math.BigDecimal;
import java.util.ArrayList;

import static pw.springdev.spring.batch.ordersparser.constant.Constants.*;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
public class CsvOrderInfoLineMapper implements LineMapper<OrderInfo> {

    private final MultiResourceItemReader delegator;

    public CsvOrderInfoLineMapper(MultiResourceItemReader delegator) {
        this.delegator = delegator;
    }

    @Override
    public OrderInfo mapLine(String line, int lineNumber) {

        OrderInfo orderInfo = new OrderInfo();

        Resource currentResource = delegator.getCurrentResource();
        String[] fileName = currentResource.getFilename().split("/");

        orderInfo.setFilename(fileName[fileName.length - 1]);
        orderInfo.setLine((long) lineNumber);

        Long id;
        BigDecimal amount;
        String currency;
        String comment;


        if (StringUtils.isBlank(line)) {
            orderInfo.setResult(Result.BLANK_LINE);
            return orderInfo;
        } else if (!line.contains(",")) {
            orderInfo.setResult(Result.WRONG_LINE);
            return orderInfo;
        }

        String[] fields = line.split(DELIMITER);
        if (fields.length != Order.class.getDeclaredFields().length) {
            orderInfo.setResult(Result.WRONG_LINE);
            return orderInfo;
        }

        ArrayList<String> fieldList = new ArrayList<>();

        for (String field : fields) {
            fieldList.add(field.replaceAll("\"", ""));
        }

        if (fieldList.get(0).equals(EMPTY_STRING)) {
            orderInfo.setResult(Result.NULL_ORDER_ID);
            return orderInfo;
        } else if (!fieldList.get(0).matches(ORDER_ID_PATTERN)) {
            orderInfo.setResult(Result.INVALID_ORDER_ID);
            return orderInfo;
        }
        id = Long.parseLong(fieldList.get(0));
        orderInfo.setId(id);

        if (fieldList.get(1).equals(EMPTY_STRING)) {
            orderInfo.setResult(Result.NULL_AMOUNT);
            return orderInfo;
        } else if (!fieldList.get(1).matches(AMOUNT_PATTERN)) {
            orderInfo.setResult(Result.INVALID_AMOUNT);
            return orderInfo;
        }
        amount = new BigDecimal(fieldList.get(1));

        orderInfo.setAmount(amount);

        if (fieldList.get(2).equals(EMPTY_STRING)) {
            orderInfo.setResult(Result.NULL_CURRENCY);
            return orderInfo;
        } else if (!fieldList.get(2).matches(CURRENCY_PATTERN)) {
            orderInfo.setResult(Result.INVALID_CURRENCY);
            return orderInfo;
        }
        currency = fieldList.get(2);
        orderInfo.setCurrency(currency);

        comment = fieldList.get(3);
        orderInfo.setComment(comment);

        orderInfo.setResult(Result.OK);

        return orderInfo;
    }
}
