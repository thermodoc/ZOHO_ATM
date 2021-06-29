package zoho;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log 
{
	public static void updateCreditLog(int tempWithdraw, ArrayList<Customer> customer,int flag,int i)
	{
		Logger logger = Logger.getLogger("Logs");
		PropertyConfigurator.configure("log4j.properties");
		logger.info(tempWithdraw+" Credited towards "+customer.get(flag).accountNumber+ " From Account Number "+customer.get(i).accountNumber+" on "+java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))+" Time:"+java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm")));
		
		
	}
	public static void updateDebitLog(int tempWithdraw, ArrayList<Customer> customer,int i,int flag)
	{
		Logger logger = Logger.getLogger("Logs");
		PropertyConfigurator.configure("log4j.properties");
		logger.info(tempWithdraw+" Debited from "+customer.get(i).accountNumber+ " to Account Number "+customer.get(flag).accountNumber+" on "+java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))+" Time:"+java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm")));
		
		
	}
	public static void updateWithdraw(int tempWithdraw, Customer customer)
	{
		Logger logger = Logger.getLogger("Logs");
		PropertyConfigurator.configure("log4j.properties");
	
		logger.info(tempWithdraw+" Withdraw from "+customer.accountNumber+ " from the ATM  on "+java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"))+" Time:"+java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH.mm")));
		
		
	}
		
}


