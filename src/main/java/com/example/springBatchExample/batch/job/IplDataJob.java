package com.example.springBatchExample.batch.job;


import com.example.springBatchExample.batch.writer.IplDataWriter;
import com.example.springBatchExample.batch.mapper.IplDataMapper;
import com.example.springBatchExample.batch.processor.IplDataProcessor;
import com.example.springBatchExample.batch.config.IplDataSkipPolicy;
import com.example.springBatchExample.data.MatchInput;
import com.example.springBatchExample.batch.listerner.IplDataJobListener;
import com.example.springBatchExample.model.Match;
import com.example.springBatchExample.repository.IplDataRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.sql.DataSource;
@Configuration
public class IplDataJob {

    private String[] FIELD_NAMES = new String[] {
            "id",
            "city",
            "date",
            "player_of_match",
            "venue",
            "neutral_venue",
            "team1",
            "team2",
            "toss_winner",
            "toss_decision",
            "winner",
            "result",
            "result_margin",
            "eliminator",
            "method",
            "umpire1",
            "umpire2"

    };
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private IplDataProcessor iplDataProcessor;
    private IplDataWriter iplDataWriter;
    private DataSource dataSource;

    @Autowired
    public IplDataJob( JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, IplDataProcessor iplDataProcessor, DataSource dataSource,IplDataRepository iplDataRepository,IplDataWriter iplDataWriter){
          this.jobBuilderFactory = jobBuilderFactory;
          this.stepBuilderFactory = stepBuilderFactory;
          this.iplDataProcessor = iplDataProcessor;
          this.dataSource = dataSource;
          this.iplDataWriter = iplDataWriter;
    }

    @Qualifier("demo1")
    @Bean
    public Job demo1Job() throws Exception{
        return this.jobBuilderFactory.get("demo1")
                .incrementer(new RunIdIncrementer())
                .start(startDemo1())
                .listener(iplDataJobListener())
                .build();
    }

    @Bean
    public IplDataJobListener iplDataJobListener(){
        return new IplDataJobListener();
    }

    @Bean
    public Step startDemo1(){
         return this.stepBuilderFactory.get("step1")
                 .<MatchInput, Match>chunk(5)
                 .reader(iplDataReader())
                 .processor(iplDataProcessor)
                 .writer(iplDataWriter)
                 .faultTolerant().skipPolicy(iplDataSkipPolicy())
                 .taskExecutor(iplDataExecutor())
                 .allowStartIfComplete(true)
                 .build();
    }

    @Bean
    public TaskExecutor iplDataExecutor(){
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(5);
        return simpleAsyncTaskExecutor;
    }

    @Bean
    public SkipPolicy iplDataSkipPolicy(){
        return new IplDataSkipPolicy();
    }

    public Resource inputResource(final String fileName){
         return new ClassPathResource(fileName);
    }

    @Bean
    public FlatFileItemReader<MatchInput> iplDataReader(){
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("MatchItemReader")
                .resource(new ClassPathResource("match_data.csv"))
                .lineMapper(new DefaultLineMapper<>(){
                    {
                        setLineTokenizer(new DelimitedLineTokenizer(){{
                            setNames(FIELD_NAMES);
                            setDelimiter(",");
                        }
                        });
                        setFieldSetMapper(new IplDataMapper());
                    }
                })
                .build();
    }

/*    @Bean
    public JdbcBatchItemWriter<Match> iplDataDbWriter(){
        JdbcBatchItemWriter<Match> jdbcBatchItemWriter = new JdbcBatchItemWriter();
        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setSql(
                "INSERT INTO match (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, result,result_margin, umpire1, umpire2)"
                +" VALUES (: id, : city, : date, : playerOfMatch, : venue, : team1, : team2, : tossWinner, : tossDecision, : matchWinner, :result, :resultMargin, :umpire1, :umpire2)"
        );
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Match>());
        return jdbcBatchItemWriter;
    }*/

}
