package pw.springdev.spring.batch.ordersparser.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import static pw.springdev.spring.batch.ordersparser.constant.Constants.*;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @NotNull(message = "OrderId can't be null")
    @Pattern(regexp = ORDER_ID_PATTERN)
    private Long orderId;

    @NotNull(message = "Amount can't be null")
    @Pattern(regexp = AMOUNT_PATTERN)
    private BigDecimal amount;

    @NotNull(message = "Currency can't be null")
    @Pattern(regexp = CURRENCY_PATTERN)
    private String currency;

    @NotNull(message = "Comment can't be null")
    @Size(min = MIN_COMMENT_LENGTH, max = MAX_COMMENT_LENGTH)
    private String comment;
}
