package pw.springdev.spring.batch.ordersparser.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemProcessorAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import pw.springdev.spring.batch.ordersparser.domain.OrderInfo;
import pw.springdev.spring.batch.ordersparser.helper.ResourceHelper;
import pw.springdev.spring.batch.ordersparser.mapper.CsvOrderInfoLineMapper;
import pw.springdev.spring.batch.ordersparser.mapper.JsonOrderInfoLineMapper;
import pw.springdev.spring.batch.ordersparser.service.OrderService;
import pw.springdev.spring.batch.ordersparser.validator.OrdersParserJobParametersValidator;
import pw.springdev.spring.batch.ordersparser.writer.ConsoleItemWriter;

import static pw.springdev.spring.batch.ordersparser.constant.Constants.*;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
@Configuration
public class OrdersParserConfiguration {


    @Value("${order-parser.default-chunk-size}")
    int chunkSize;

    @Value("${order-parser.default-lines-to-skip}")
    int linesToSkip;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public OrdersParserConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job parallelStepsJob() throws Exception {

        Flow jsonFlow = new FlowBuilder<Flow>(JSON_FLOW)
                .start(jsonStep())
                .build();

        Flow csvFlow = new FlowBuilder<Flow>(CSV_FLOW)
                .start(csvStep())
                .split(new SimpleAsyncTaskExecutor())
                .add(jsonFlow)
                .build();

        return this.jobBuilderFactory
                .get(PARALLEL_STEPS_JOB)
                .validator(ordersParserJobParametersValidator())
                .start(csvFlow)
                .end()
                .build();
    }

    @Bean
    public JobParametersValidator ordersParserJobParametersValidator() {
        return new OrdersParserJobParametersValidator();
    }

    @Bean
    public Step jsonStep() throws Exception {
        return this.stepBuilderFactory
                .get(JSON_STEP)
                .<OrderInfo, String>chunk(chunkSize)
                .reader(jsonOrderInfoMultiResourceItemReader(null))
                .processor(orderProcessor(null))
                .writer(orderInfoItemWriter())
                .build();
    }

    @Bean
    public Step csvStep() throws Exception {
        return this.stepBuilderFactory
                .get(CSV_STEP)
                .<OrderInfo, String>chunk(chunkSize)
                .reader(csvOrderInfoMultiResourceItemReader(null))
                .processor(orderProcessor(null))
                .writer(orderInfoItemWriter())
                .build();
    }

    @Bean
    public ItemProcessorAdapter<OrderInfo, String> orderProcessor(OrderService orderService) {
        ItemProcessorAdapter<OrderInfo, String> adapter = new ItemProcessorAdapter<>();

        adapter.setTargetObject(orderService);
        adapter.setTargetMethod("orderInfoToJson");

        return adapter;
    }

    @Bean
    public ItemWriter<String> orderInfoItemWriter() {
        return new ConsoleItemWriter<String>();
    }

    @Bean
    @StepScope
    public ItemReader<OrderInfo> csvOrderInfoMultiResourceItemReader(
            @Value("#{jobParameters['csvFiles']}") String csvFiles) throws Exception {

        Resource[] resources = ResourceHelper.getResources(csvFiles);

        MultiResourceItemReader<OrderInfo> reader = new MultiResourceItemReader<>();
        reader.setResources(resources);
        reader.setDelegate(orderInfoFlatFileItemReader(reader));

        return reader;
    }

    private FlatFileItemReader<OrderInfo> orderInfoFlatFileItemReader(MultiResourceItemReader delegator)
            throws Exception {
        FlatFileItemReader<OrderInfo> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(linesToSkip);
        reader.setLineMapper(new CsvOrderInfoLineMapper(delegator));
        reader.afterPropertiesSet();

        return reader;
    }

    @Bean
    @StepScope
    public ItemReader<OrderInfo> jsonOrderInfoMultiResourceItemReader(@Value("#{jobParameters['jsonFiles']}") String jsonFiles) throws Exception {
        Resource[] resources = ResourceHelper.getResources(jsonFiles);

        MultiResourceItemReader<OrderInfo> reader = new MultiResourceItemReader<>();
        reader.setResources(resources);
        reader.setDelegate(jsonOrderInfoFlatFileItemReader(reader));

        return reader;
    }

    private FlatFileItemReader<OrderInfo> jsonOrderInfoFlatFileItemReader(MultiResourceItemReader delegator) throws Exception {
        FlatFileItemReader<OrderInfo> reader = new FlatFileItemReader<>();
        reader.setLinesToSkip(linesToSkip);
        reader.setLineMapper(new JsonOrderInfoLineMapper(delegator));
        reader.afterPropertiesSet();

        return reader;
    }
}
