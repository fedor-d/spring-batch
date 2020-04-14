package pw.springdev.spring.batch.ordersparser.writer;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
public class ConsoleItemWriter<T> implements ItemWriter<T> {

    @Override
    public void write(List<? extends T> items) {
        for (T item : items) {
            System.out.println(item);
        }
    }
}
