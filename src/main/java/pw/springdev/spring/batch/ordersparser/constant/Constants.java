package pw.springdev.spring.batch.ordersparser.constant;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
public class Constants {

    /**
     * Domain constants
     */
    public final static int MIN_COMMENT_LENGTH = 1;
    public final static int MAX_COMMENT_LENGTH = 50;

    /**
     * RegEx constants
     */
    public final static String ORDER_ID_PATTERN = "^\\d+$";
    public final static String AMOUNT_PATTERN = "^\\d*\\.\\d{2}$|^\\d+$";
    public final static String CURRENCY_PATTERN = "^[A-Z]{3}$";
    public final static String JSON_LINE_PATTERN = "^\\{(\"orderId\":\\d+,\"amount\":(\\d*\\.\\d{2}|\\d+),\"currency\":\"[A-Z]{3}\",\"comment\":\"){1}.{"
            .concat(Integer.toString(MIN_COMMENT_LENGTH))
            .concat(",")
            .concat(Integer.toString(MAX_COMMENT_LENGTH))
            .concat("}\"}$");

    /**
     * Order Parser Configuration constants
     */
    public final static String CSV_JOB_PARAMETERS = "csvFiles";
    public final static String JSON_JOB_PARAMETERS = "jsonFiles";
}
