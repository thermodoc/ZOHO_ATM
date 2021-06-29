package zoho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Balance 
{
public static int totalCash=0;
public static int thousandCounter=0;
public static int fiveHundredCounter=0;
public static int hundredCounter=0;
	{
	try
		{
		Class.forName("com.mysql.cj.jdbc.Driver");
	
		
		
	
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	}
	public static void updateBalance()
	{
		try
		{	String updateBalance="update balance set account_balance = ? , thousands = ? , fivehundreds = ? , hundreds = ? where id = '0'";
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			PreparedStatement pe=con.prepareStatement(updateBalance);
			pe.setInt(1,totalCash);
			pe.setInt(2, thousandCounter);
			pe.setInt(3,fiveHundredCounter );
			pe.setInt(4, hundredCounter);
			pe.executeUpdate();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	public static void getBalanceFromDB() // Getting balance from db;
	{
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			Statement Stmnt=con.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
			        ResultSet.CONCUR_UPDATABLE);
			String Balance_select_statement = " select * from balance";
			ResultSet re =Stmnt.executeQuery(Balance_select_statement);
			while(re.next())
			{	
				 totalCash=re.getInt(2);
				 thousandCounter=re.getInt(3);;
				 fiveHundredCounter=re.getInt(4);
				 hundredCounter=re.getInt(5);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void checkifBalalanceIsNull() // VALIDATING NULL VALUES IN BALANCETABLE
	{
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			Statement Stmnt=con.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
			        ResultSet.CONCUR_UPDATABLE);
			String insertBalanceZero = "insert into balance values()";
			String selectBalanceZero = "select * from balance";
			ResultSet rs = Stmnt.executeQuery(selectBalanceZero);
			
			if(! rs.next())
			{
			Stmnt.executeUpdate(insertBalanceZero);
			totalCash=0;
			thousandCounter=0;
			fiveHundredCounter=0;
			hundredCounter=0;
			}
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void loadBalance(int n) // LOADING BALANCE TO BALANCE TABLE 
	{
		
		
		totalCash=totalCash+(n*100000);
		thousandCounter=thousandCounter + (n*20);
		fiveHundredCounter=fiveHundredCounter+ (n*100);
		hundredCounter=hundredCounter+ (n*300);
		updateBalance();
		
		
		
	}
	public static boolean  withDraw(int tempWithdraw,Customer customer)
	{
		if(tempWithdraw % 100 != 0)
		{
			System.out.println("Enter Multiple of hundred");
			return false;
			
		}
		if(totalCash < tempWithdraw)
		{
			System.out.println("Not enough money ");
			return false;
			
		}
		if(tempWithdraw < 100 || tempWithdraw >= 10000)
		{
			System.out.println("Exceeded the limits");
			return false;
			
		}
		if(customer.balance < tempWithdraw)
		{
			System.out.println("Balance too low ");
			return false;
		
			
			
		}
		if(tempWithdraw <= 5000)
		{
			if(thousandCounter == 0)
			{
				if(fiveHundredCounter == 0)
				{
					if(tempWithdraw/100 <= hundredCounter)
					{
						hundredCounter = hundredCounter - (tempWithdraw/100);
						return true;
					}
					else
					{
						System.out.println("Not enough money");
						return false;
						
					}
				}
				else 
				{
					if(tempWithdraw / 500 <= fiveHundredCounter || (tempWithdraw - (fiveHundredCounter * 500)) / 100 <= hundredCounter)
					{
						fiveHundredCounter = fiveHundredCounter - ((tempWithdraw)/500);
						hundredCounter = hundredCounter - (((tempWithdraw)%500)/100);
						return true;
					}
					else
					{
						System.out.println("Not enough money");
						return false;
					
					}
				}
			}
			else
				{
				if(tempWithdraw<1000)
				{
					fiveHundredCounter = fiveHundredCounter-(tempWithdraw/500);
					hundredCounter = hundredCounter-((tempWithdraw%500)/100);		
					return true;
					
				}
				else 
				{
					thousandCounter=thousandCounter-1;
					if(tempWithdraw-1000<500)
					{
						hundredCounter = hundredCounter-((tempWithdraw-1000)/100);
						return true;
					}
					
					
					else if(((tempWithdraw-1000)/500)>6)
						{
							fiveHundredCounter = fiveHundredCounter - 6;
							hundredCounter=  hundredCounter - ((tempWithdraw-4000)/100);
							return true;
							
						}
					else 
					{
						fiveHundredCounter = fiveHundredCounter - ((tempWithdraw-1000)/500);
						hundredCounter = hundredCounter - (((tempWithdraw-1000)%500)/100);
						return true;
					}
					
				}
			}
		}
		else if(tempWithdraw > 5000)
		{
			if(thousandCounter == 0)
			{
				if(fiveHundredCounter == 0)
				{
					if(tempWithdraw/100 <= hundredCounter)
					{
						hundredCounter=hundredCounter-(tempWithdraw/100);
						return true;
					}
					else
					{
						System.out.println("Not enough money");
						return false;
					
					}
				}
				else 
				{
					if(tempWithdraw/500 <= fiveHundredCounter || (tempWithdraw-(fiveHundredCounter*500))/100<=hundredCounter)
					{
						fiveHundredCounter=fiveHundredCounter-((tempWithdraw)/500);
						hundredCounter=hundredCounter-(((tempWithdraw)%500)/100);
						return true;
					}
					
					else
					{
						System.out.println("Not enough money");
						return false;
						
					}
				}
			}
			else 
			{
				
			
			thousandCounter=thousandCounter-3;
			fiveHundredCounter=fiveHundredCounter-2;
			hundredCounter=hundredCounter-10;
	
			if(tempWithdraw < 6000)
			{
				fiveHundredCounter=fiveHundredCounter-((tempWithdraw-5000)/500);
				hundredCounter=hundredCounter-(((tempWithdraw-5000)%500)/100);	
				return true;
			}
			else if(tempWithdraw >= 6000)
			{
				fiveHundredCounter=fiveHundredCounter-((tempWithdraw-5000)/500);
				hundredCounter=hundredCounter-(((tempWithdraw-5000)%500)/100);	
				return true;
			}
			else 
			{
				return false;
			}
			
			}
			}
		return false;
		
	}
	public static void showBalance()
	{
		try {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
		Statement Stmnt=con.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
		        ResultSet.CONCUR_UPDATABLE);
		String balanceSelectStatement = " select * from balance";
		ResultSet re =Stmnt.executeQuery(balanceSelectStatement);
		
		while(re.next())
		{
			System.out.println("Total Balance:"+re.getString(2)+"\n1000s:"+re.getString(3)+"\n500s:"+re.getString(4)+"\n100s:"+re.getString(5));
		}

		}
		catch(SQLException e )
		{
			e.printStackTrace();
		}
	}
	
		
		
	
	

}
