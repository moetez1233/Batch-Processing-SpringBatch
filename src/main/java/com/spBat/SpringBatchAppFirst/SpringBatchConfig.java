package com.spBat.SpringBatchAppFirst;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;



/* ====================== Pour La configuration d'un job ============================ */
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	@Autowired private JobBuilderFactory joBuilderFactory;
	@Autowired private StepBuilderFactory stepBuilderFactory;
	@Autowired private ItemReader<BankTransaction> bankTransactionItemReader;
	@Autowired private ItemWriter<BankTransaction> bankTransactionItemWriter;
	//@Autowired private ItemProcessor<BankTransaction,BankTransaction> bankTransactionItemProcessor;
	
	/* ==================== methode de configuration pour returne un Job */
	@Bean
	public Job BankJob() {
		Step step1=stepBuilderFactory.get("step-load-data") // ona cree une step nommé step-load-data  
				.<BankTransaction,BankTransaction>chunk(100)
				.reader(bankTransactionItemReader)
				.processor(compositeItemProcessor()) // cote modefication du data
				.writer(bankTransactionItemWriter)
				.build();
		return joBuilderFactory.get("bank-data-loader-job")// ona cree un Job nommé bank-data-loader-job
				.start(step1).build();
	}
	
	/*====================================== Pour Processor:modefication du donné:
	 *  on cree 2 bean qui fait appel au 2 methode de modefication : 
	 *  BankTransactionItemProcessor() & BankTransactionItemAnalyticsProcessor
	 *  aller a Rest controller @GetMapping("/analytics")  pour appeler La methode au la processor que tu veut 
	 * 
	 * ==================*/
	@Bean
	public ItemProcessor<BankTransaction,BankTransaction> compositeItemProcessor() {
		List<ItemProcessor<BankTransaction,BankTransaction>> itemProcessors=new ArrayList<>();
		itemProcessors.add(itemProcessor1());
		itemProcessors.add(itemProcessor2());
		CompositeItemProcessor<BankTransaction,BankTransaction> compositeItemProcessor =new CompositeItemProcessor<>();
		compositeItemProcessor.setDelegates(itemProcessors);
		return compositeItemProcessor;
	}
	@Bean // le meme chose quand je je décommanté @Component
	BankTransactionItemProcessor itemProcessor1(){
		return new BankTransactionItemProcessor();
		
	}
	@Bean
	BankTransactionItemAnalyticsProcessor itemProcessor2(){
		return new BankTransactionItemAnalyticsProcessor();
		
	}
	
	
	
	
	/*====================================== fin  Pour Processor(modefication )==================*/

	
	/*====================================lire le fichier csv ==================*/

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
	/*==================================== fin lire le fichier csv ==================*/
	

	/*==================================== writer les donner de fichier csv dans Base de donneés ==================*/
             
	                /* ========== Voir JobRestController =============*/
	/*==================================== writer les donner de fichier csv dans Base de donneés ==================*/

	

}
