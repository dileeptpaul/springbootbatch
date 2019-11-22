package sample.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@Configuration
@EnableTransactionManagement
@ComponentScan("nl.rabobank.gict.bkr.batch")
public class JobConfig {
    @Resource
    private JobBuilderFactory jobBuilderFactory;

    @Resource
    private Step processCsvFileStep;

    @Bean
    public Job processCsvFileJob() {
        return jobBuilderFactory.get("processCsvFileJob")
                .incrementer(new RunIdIncrementer())
                .start(processCsvFileStep)
                .build();

    }
}
