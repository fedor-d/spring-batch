package pw.springdev.spring.batch.ordersparser.domain;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
public enum Result {
    OK, BLANK_LINE, WRONG_LINE, NULL_ORDER_ID, INVALID_ORDER_ID, NULL_AMOUNT,
    INVALID_AMOUNT, NULL_CURRENCY, INVALID_CURRENCY, CORRUPTED_JSON_LINE
}
