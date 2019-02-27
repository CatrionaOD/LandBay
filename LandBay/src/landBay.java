import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class landBay {

	@SuppressWarnings("deprecation")
	public static void main(String[] args)  throws Exception {
		 @SuppressWarnings({ "resource" })
		  
		    CSVReader reader = new CSVReader(new FileReader("loans.csv"), ',' , '"' , 1);
//		 CSVReader reader = new CSVReader(new FileReader("testLoans.csv"), ',' , '"' , 1);
		 
		 	ArrayList<Loan> loanArray = new ArrayList<Loan>();
		 
		      String[] nextLine;
		      while ((nextLine = reader.readNext()) != null) {
		         if (nextLine != null) {
		        	Loan newLoan = new Loan(nextLine);
		        	loanArray.add(newLoan);
		         }
		       }
		      
		      reader = new CSVReader(new FileReader("investmentRequests.csv"), ',' , '"' , 1);
//		      reader = new CSVReader(new FileReader("testInvestmentsRequests.csv"), ',' , '"' , 1);
		      ArrayList<InvestmentRequest> investmentsArray = new ArrayList<InvestmentRequest>();
		      
		      String[] investmentNextLine;
		      while ((investmentNextLine = reader.readNext()) != null) {
		         if (investmentNextLine != null) {
		        	InvestmentRequest newInvestment = new InvestmentRequest(investmentNextLine);
		        	investmentsArray.add(newInvestment);		        	
		         }
		       }
		   
		      ArrayList<Loan> trackerLoans = new ArrayList<Loan>();
		      ArrayList<Loan> fixedLoans = new ArrayList<Loan>();
		      ArrayList<InvestmentRequest> trackerInvestments = new ArrayList<InvestmentRequest>();
		      ArrayList<InvestmentRequest> fixedInvestments = new ArrayList<InvestmentRequest>();
		      
		      for (int i = 0; i < loanArray.size(); i++) {
		    	  if(loanArray.get(i).product.toString().equals("TRACKER")) {
		    		  trackerLoans.add(loanArray.get(i));
		    	  }
		    	  else {
		    		  fixedLoans.add(loanArray.get(i));
		    	  }
				}
		      
		      for (int i = 0; i < investmentsArray.size(); i++) {
		    	  if(investmentsArray.get(i).productType.toString().equals("TRACKER")) {
		    		  trackerInvestments.add(investmentsArray.get(i));
		    	  }
		    	  else {
		    		  fixedInvestments.add(investmentsArray.get(i));
		    	  }
				}
		      
		      ArrayList<Loan> investedTrackerLoans = new ArrayList<Loan>();
		      int investorTotal = 0;
		      boolean possibleToFund = false;  
		     
		      for (int i = 0; i < trackerLoans.size(); i++) {
		    	  possibleToFund = false;
		    	  investorTotal = 0;
		    	  for (int j = 0; j < trackerInvestments.size(); j++) {
		    		  if (trackerInvestments.get(j).term > trackerLoans.get(i).term) {
		    			  investorTotal += trackerInvestments.get(j).investment;
		    		  }
		    	  }
		    	  if (investorTotal >= trackerLoans.get(i).loanAmount) {
		    		  possibleToFund = true;
		    	  }
		    	  if (possibleToFund) {
		    		  for (int j = 0; j < trackerInvestments.size(); j++) {
		    			  String checkStatus = trackerLoans.get(i).status;
		    			  if ((trackerInvestments.get(j).term > trackerLoans.get(i).term)&&(trackerInvestments.get(j).investment>0)&&(checkStatus != "funded")) {		    			  		    			
		    			  
		    				  // Finishes funding - subtract loan from investment amount and mark loan as funded
		    				  if (trackerInvestments.get(j).investment >= trackerLoans.get(i).loanAmount) {
		    					  trackerInvestments.get(j).investment = trackerInvestments.get(j).investment - trackerLoans.get(i).loanAmount;
		    					  trackerLoans.get(i).status = "funded";
		    					  investedTrackerLoans.add(trackerLoans.get(i));
		    					  trackerLoans.get(i).investors.add(trackerInvestments.get(j).investor);
		    					  trackerLoans.get(i).investmentAmounts.add(trackerLoans.get(i).loanAmount);
		    				  }
		    			  
		    			     //	Partial funding
		    				  else {
		    					  trackerLoans.get(i).loanAmount = trackerLoans.get(i).loanAmount-trackerInvestments.get(j).investment;
		    					  trackerLoans.get(i).investors.add(trackerInvestments.get(j).investor);
		    					  trackerLoans.get(i).investmentAmounts.add(trackerInvestments.get(j).investment);
		    					  trackerInvestments.get(j).investment = 0;
		    				  }
		    			 }
		    		  }    	 
		    	  }
		      }
		      
		      ArrayList<Loan> investedFixedLoans = new ArrayList<Loan>();
		      investorTotal = 0;
		      possibleToFund = false;  
		     
		      for (int i = 0; i < fixedLoans.size(); i++) {
		    	  possibleToFund = false;
		    	  investorTotal = 0;
		    	  for (int j = 0; j < fixedInvestments.size(); j++) {
		    		  if (fixedInvestments.get(j).term > fixedLoans.get(i).term) {
		    			  investorTotal += fixedInvestments.get(j).investment;
		    		  }
		    	  }
		    	  if (investorTotal >= fixedLoans.get(i).loanAmount) {
		    		  possibleToFund = true;
		    	  }
		    	  if (possibleToFund) {
		    		  for (int j = 0; j < fixedInvestments.size(); j++) {
		    			  String checkStatus = fixedLoans.get(i).status;
		    			  if ((fixedInvestments.get(j).term > fixedLoans.get(i).term)&&(fixedInvestments.get(j).investment>0)&&(checkStatus != "funded")) {		    			  		    			
		    			  
		    				  // Finishes funding - subtract loan from investment amount and mark loan as funded
		    				  if (fixedInvestments.get(j).investment >= fixedLoans.get(i).loanAmount) {
		    					  fixedInvestments.get(j).investment = fixedInvestments.get(j).investment - fixedLoans.get(i).loanAmount;
		    					  fixedLoans.get(i).status = "funded";
		    					  investedFixedLoans.add(fixedLoans.get(i));
		    					  fixedLoans.get(i).investors.add(fixedInvestments.get(j).investor);
		    					  fixedLoans.get(i).investmentAmounts.add(fixedLoans.get(i).loanAmount);
		    				  }
		    			  
		    			     //	Partial funding
		    				  else {
		    					  fixedLoans.get(i).loanAmount = fixedLoans.get(i).loanAmount-fixedInvestments.get(j).investment;
		    					  fixedLoans.get(i).investors.add(fixedInvestments.get(j).investor);
		    					  fixedLoans.get(i).investmentAmounts.add(fixedInvestments.get(j).investment);
		    					  fixedInvestments.get(j).investment = 0;
		    				  }
		    			 }
		    		  }    	 
		    	  }
		      }
		      
		      for (int j = 0; j < investedTrackerLoans.size(); j++) {
		    	  System.out.println("Tracker Loan "+investedTrackerLoans.get(j).loanId+" has been invested in by: "+investedTrackerLoans.get(j).investors+" to the amounts of: "+investedTrackerLoans.get(j).investmentAmounts);
		      }
		      for (int j = 0; j < investedFixedLoans.size(); j++) {
		    	  System.out.println("Fixed Loan "+investedFixedLoans.get(j).loanId+" has been invested in by: "+investedFixedLoans.get(j).investors+" to the amounts of: "+investedFixedLoans.get(j).investmentAmounts);
		      }
	}
	
}
