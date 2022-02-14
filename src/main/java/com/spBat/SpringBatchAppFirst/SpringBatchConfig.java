package com.spBat.SpringBatchAppFirst;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;



/* ====================== Pour La configuration d'un job ============================ */
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	@Autowired private JobBuilderFactory joBuilderFactory;
	@Autowired private StepBuilderFactory stepBuilderFactory;
	@Autowired private ItemReader<BankTransaction> bankTransactionItemReader;
	@Autowired private ItemWriter<BankTransaction> bankTransactionItemWriter;
	@Autowired private ItemProcessor<BankTransaction,BankTransaction> bankTransactionItemProcessor;
	
	/* ==================== methode de configuration pour returne un Job */
	@Bean
	public Job BankJob() {
		Step step1=stepBuilderFactory.get("step-load-data") // ona cree une step nommé step-load-data  
				.<BankTransaction,BankTransaction>chunk(100)
				.reader(bankTransactionItemReader)
				.processor(bankTransactionItemProcessor)
				.writer(bankTransactionItemWriter)
				.build();
		return joBuilderFactory.get("bank-data-loader-job")// ona cree un Job nommé bank-data-loader-job
				.start(step1).build();
	}
	@Bean
	public FlatFileItemReader<BankTransaction>flatFileItemReader(@Value("${inputFile}")Resource inputFile){
        FlatFileItemReader<BankTransaction> flatItemReader = new FlatFileItemReader<>();
        flatItemReader.setName("FLATFILE1");
        flatItemReader.setLinesToSkip(1);// ne lire pas la premiere ligne puisque n'est pas une donné
        flatItemReader.setResource(inputFile);
        flatItemReader.setLineMapper(lineMapper());
        return flatItemReader;

	}
	private LineMapper<BankTransaction> lineMapper() {
		   final DefaultLineMapper<BankTransaction> LineMapper = new DefaultLineMapper<>();
	        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	        lineTokenizer.setDelimiter(","); // illiminer les vergule dans notre DB moetez,maddouri,14/10/1997,tunis
	        lineTokenizer.setStrict(false);
	        lineTokenizer.setNames("id","accountID","strTransactionDate","strTransactionType","amount");// 
	        LineMapper.setLineTokenizer(lineTokenizer);
	        BeanWrapperFieldSetMapper fieledSetMapper=new BeanWrapperFieldSetMapper();
	        fieledSetMapper.setTargetType(BankTransaction.class);
	        LineMapper.setFieldSetMapper(fieledSetMapper);
	        return LineMapper;

	}
	
	

	

	

}
