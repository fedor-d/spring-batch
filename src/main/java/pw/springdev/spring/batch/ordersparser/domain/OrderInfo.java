package pw.springdev.spring.batch.ordersparser.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfo {

    private Long id;
    private BigDecimal amount;
    private String currency;
    private String comment;
    private String filename;
    private Long line;
    private Result result;
}
