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

    public final static String JSON_FLOW = "jsonFlow";
    public final static String CSV_FLOW = "csvFlow";
    public final static String PARALLEL_STEPS_JOB = "parallelStepsJob";
    public final static String JSON_STEP = "jsonStep";
    public final static String CSV_STEP = "csvStep";
    public final static String TRANSFER_ORDER_INFO_TO_JSON_METHOD = "orderInfoToJson";

    /**
     * Other constants
     */
    public final static String EMPTY_STRING = "";
    public final static String DELIMITER = ",";
}
