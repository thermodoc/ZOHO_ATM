package zoho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ministatement
{
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
	public static void updateMiniStatementDebit(int tempWithdraw, Customer cu[],int i)
	{
		try {
		 String miniInsert = "insert into ministatement(account_no,transaction_remarks,transaction_type,transaction_amt) values(?,?,?,?)";
		 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			PreparedStatement pe=con.prepareStatement( miniInsert);
			
			//pe3.setInt(1, ministatement_id_counter++);
			pe.setInt(1, cu[i].accountNumber);
			pe.setString(2, "Debit "+(tempWithdraw)+"from the ATM");
			pe.setString(3, "Debit");
			pe.setInt(4,tempWithdraw);
			pe.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	public static void updateMiniStatementTransferDebit(int tempWithdraw, Customer cu[],int i,int flag)
	{
		try {
		 String miniInsert = "insert into ministatement(account_no,transaction_remarks,transaction_type,transaction_amt) values(?,?,?,?)";
		 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			PreparedStatement pe=con.prepareStatement( miniInsert);
			
		
			pe.setInt(1, cu[i].accountNumber);
			pe.setString(2, "Funds transfered to "+(flag+100+1));
			pe.setString(3, "Debit");
			pe.setInt(4,tempWithdraw);
			pe.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	public static void updateMiniStatementTransferCredit(int tempWithdraw, Customer cu[],int flag,int i)
	{
		try {
		 String miniInsert = "insert into ministatement(account_no,transaction_remarks,transaction_type,transaction_amt) values(?,?,?,?)";
		 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			PreparedStatement pe=con.prepareStatement( miniInsert);
			
		
			pe.setInt(1, cu[flag].accountNumber);
			pe.setString(2, "Credited from"+(i+100+1));
			pe.setString(3, "Credit");
			pe.setInt(4,tempWithdraw);
			pe.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	public static void printMiniStatement(Customer cu[],int i)
	{
		try {
			 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			System.out.println("MINI STATEMENT\nAccount Holder:"+cu[i].accountHolder+"\nAccount Number:"+cu[i].accountNumber+"\nAccount Balance:"+cu[i].balance);
			System.out.println("Transaction ID	Transaction Remarks		Transaction Type	Transaction Amt");
			String ministatementSelectStatement = " select * from ministatement where account_no=? order by transaction_id desc limit 5 ;\r\n"
					+ " ";
			PreparedStatement pe=con.prepareStatement( ministatementSelectStatement);
			pe.setInt(1,cu[i].accountNumber);
			ResultSet re =pe.executeQuery();
			
			while(re.next())
			{
				System.out.println(re.getInt(1)+"		"+re.getString(3)+"		"+re.getString(4)+"			"+re.getInt(5)+"\n");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}

