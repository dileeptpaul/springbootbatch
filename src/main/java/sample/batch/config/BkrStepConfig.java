package sample.batch.config;

import sample.batch.model.Employee;
import sample.batch.processor.CsvFileProcessor;
import sample.batch.reader.BkrReaderSeparatorPolicy;
import sample.batch.writer.CsvFileWriter;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Configuration
@EnableJpaRepositories("sample.batch.repository")
@EnableTransactionManagement
public class BkrStepConfig {

    @Value("C:/Strike/BkrCounter/src/main/resources/")
    private String csvFilePath;

    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Resource
    private BkrReaderSeparatorPolicy brReaderSeparatorPolicy;

    @Resource
    private CsvFieldSetMapper csvFieldSetMapper;

    @Bean
    public Step processCsvFileStep() throws IOException{
        return stepBuilderFactory.get("processCsvFileStep")
                .<Employee, Employee>chunk(10)
                .reader(processCsvFileReader())
                .processor(processCsvFileProcessor())
                .writer(processCsvFileWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<Employee> processCsvFileReader() throws IOException {
        final List<Path> files = Files.walk(Paths.get(csvFilePath))
                .filter(file -> file.getFileName().toString().contains("Bkr")).collect(toList());

        return new FlatFileItemReaderBuilder<Employee>()
                .name("BkrCsvFileReader")
                .resource(new FileSystemResource(files.get(0).toString()))
                .linesToSkip(1)
                .recordSeparatorPolicy(brReaderSeparatorPolicy)
                .lineMapper(createMapper())
                .build();
    }

    private LineMapper<Employee> createMapper() {
        final DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(createLineTokenizer());
        lineMapper.setFieldSetMapper(csvFieldSetMapper);

        return lineMapper;
    }

    private LineTokenizer createLineTokenizer() {
        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("ruleId","name", "place");
        return lineTokenizer;
    }

    @Bean
    public ItemProcessor<Employee, Employee> processCsvFileProcessor() {
        return new CsvFileProcessor();
    }

    @Bean
    public ItemWriter<Employee> processCsvFileWriter() {
        return new CsvFileWriter();
    }
}
