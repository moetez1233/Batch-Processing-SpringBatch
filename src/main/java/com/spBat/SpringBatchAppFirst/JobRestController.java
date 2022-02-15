package com.spBat.SpringBatchAppFirst;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobRestController {
	@Autowired
	private JobLauncher jobLauncher; // qu'il va demarrer le job
	@Autowired
	private Job job;
	@Autowired
	private BankTransactionItemAnalyticsProcessor analyticsProcessor;
	
	@GetMapping("/startJob")
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		/*============================= end lancer un job =========================== */

		Map<String,JobParameter> params=new HashMap<>();
		params.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobparameters =new JobParameters(params);
		JobExecution jobExecution =jobLauncher.run(job, jobparameters);
		/*============================= end lancer un job =========================== */
		while(jobExecution.isRunning()) {
			System.out.println(".....");
		}
		
		  return jobExecution.getStatus();
		
	}
	/* =============================== appel du processor de modefication BankTransactionItemAnalyticsProcessor*/
	@GetMapping("/analytics")
	public Map<String,Double> analytics(){
		Map<String,Double> map=new HashMap<>();
		map.put("TotalCredit",analyticsProcessor.getTotalCredit());
		map.put("TotalDebit",analyticsProcessor.getTotalDebit());
return map;
	}
	
	/* =============================== La fin d'appel du processor de modefication BankTransactionItemAnalyticsProcessor*/


}
