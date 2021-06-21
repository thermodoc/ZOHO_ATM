package zoho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer extends Ministatement {
	public int accountNumber,balance,PIN;
	public String accountHolder=new String();
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
	public static void getCustomerDetailsFromDB(Customer cu[],int index)
	{
		try
			{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			Statement Stmnt=con.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
			        ResultSet.CONCUR_UPDATABLE);
			String customerSelectStatement = " select * from customer";
			ResultSet re =Stmnt.executeQuery(customerSelectStatement );
			while(re.next())
			{
				
			cu[index].accountNumber=re.getInt(1);	
			cu[index].accountHolder=re.getNString(2);
			cu[index].PIN=re.getInt(3);
			cu[index].balance=re.getInt(4);
		
	
			index++;
			}
			
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void showCustomerDetails()
	{
	
	
		System.out.println(this.accountNumber+"			"+this.accountHolder+"			"+this.PIN+"		"+this.balance);
		
	}
	public String showCustomerBalance()
	{
		return "The balance in your account is "+this.balance;
	}
	public void updateCustomerBalance(int i)
	{
		try {
			
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
				
				 String CustomerUpdate = "update customer set balance = ? where account_no = ? ";
				 PreparedStatement pe=con.prepareStatement(CustomerUpdate);
					
					pe.setInt(1, this.balance);
					pe.setInt(2, (100+i+1));
					pe.executeUpdate();
					
				
				
				
				
				;
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
	}
	
}
