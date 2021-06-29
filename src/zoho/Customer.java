package zoho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Customer {
	public int accountNumber,balance,PIN;
	public String accountHolder=new String();
	Customer(int accNo,String accHolder,int accPIN , int accBalance)
	{
		accountNumber=accNo;
		accountHolder=accHolder;
		PIN=accPIN;
		balance=accBalance;
		
	}
	Customer()
	{
		
	}
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
	public static ArrayList<Customer> getCustomerDetailsFromDB(ArrayList<Customer> customer,int index)
	{
		try
			{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			Statement Stmnt=con.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
			        ResultSet.CONCUR_UPDATABLE);
			String customerSelectStatement = " select * from customer";
			ResultSet re =Stmnt.executeQuery(customerSelectStatement );
			Customer cu = new Customer();
			while(re.next())
			{
				
//			customer.get(index).accountNumber=re.getInt(1);	
//			customer.get(index).accountHolder=re.getNString(2);
//			customer.get(index).PIN=re.getInt(3);
//			customer.get(index).balance=re.getInt(4);
		customer.add(new Customer(re.getInt(1),re.getNString(2),re.getInt(3),re.getInt(4)));
	
			index++;
			}
			
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customer;
		
	}
	private void Customer() {
		// TODO Auto-generated method stub
		
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
					pe.setInt(2, (this.accountNumber));
					pe.executeUpdate();
					
				
				
				
				
				;
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
	}
	public boolean isAccount(ArrayList<Customer> customer , int accNumber)
	{
		int flag = 0;
		for(int i = 0 ; i < customer.size() ; i++)
		{
			if(customer.get(i).accountNumber == accNumber)
			{
				flag=1;
			}
			{
			
			if(flag==1)
			{
				return true;
			}
			return false;
		
			
	
	}
	
}
