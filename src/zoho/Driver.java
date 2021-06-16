package zoho;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;

import java.util.Scanner;



public class Driver  {

	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","whoami8888");
			Statement Stmnt=con.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
			        ResultSet.CONCUR_UPDATABLE);
			Scanner input= new Scanner(System.in);
			int task_no= 0;
			int total_cash=0;
			int thousand_counter=0;
			int fivehundred_counter=0;
			int hundred_counter=0;
			String update_balance = "update balance set account_balance = ? , thousands = ? , fivehudreds = ? , hundreds = ? where id = 1 ";
			Customer[] cu=new Customer[5];
			
			
			for(int i=0;i<5;i++)
			{
				cu[i]=new Customer();
			}
			
			
			while(task_no!=5)
			{
				String Customer_select_statement = " select * from customer";
				ResultSet re =Stmnt.executeQuery(Customer_select_statement);
				int i=0;
				while(re.next())
				{
				cu[i].accountNumber=re.getInt(1);	
				cu[i].accountHolder=re.getNString(2);
				cu[i].PIN=re.getInt(3);
				cu[i].balance=re.getInt(4);
			
		
				i++;
				}
				System.out.println("ATM \n\n Choose your task \n 1.Load cash(Admin) \n 2.Show Customer details(Admin) \n 3.Customer ATM operations(Customer) \n 4.Show Total Balance(ADMIN)\n 5.exit");
				task_no=input.nextInt();
				if(task_no==1)//											Load cash to ATM
				{
					System.out.println("Enter Admin user name:");
					String adminUser = input.next();
					System.out.println("Enter Admin Password");
					String adminPass=input.next();
					if(adminUser.equalsIgnoreCase("admin")&&adminPass.equals("admin"))
					{
						System.out.println("Enter how many lakhs to load :");
						int n = input.nextInt();
						total_cash=total_cash+(n*100000);
						thousand_counter=thousand_counter + (n*20);
						fivehundred_counter=fivehundred_counter+ (n*100);
						hundred_counter=hundred_counter+ (n*300);
					
						PreparedStatement pe=con.prepareStatement(update_balance);
						pe.setInt(1, total_cash);
						pe.setInt(2, thousand_counter);
						pe.setInt(3,fivehundred_counter );
						pe.setInt(4, hundred_counter);
						pe.executeUpdate();
					}
					else 
					{
						System.out.println("Wrong Credentials");
					}
					
					
					}
				
					
					else if(task_no==2) // Display the customer details 
					{
						System.out.println("Enter Admin user name:");
						String adminUser = input.next();
						System.out.println("Enter Admin Password");
						String adminPass=input.next();
						if(adminUser.equalsIgnoreCase("admin")&&adminPass.equals("admin"))
						{
							System.out.println("Account Number		Account Holder		PIN		Balance\n");
							for(i=0;i<5;i++)
							{
							System.out.println(cu[i].accountNumber+"			"+cu[i].accountHolder+"			"+cu[i].PIN+"		"+cu[i].balance);
							}
						}
						else 
						{
							System.out.println("Wrong Credentials");
						}
					}
				
				
				else if (task_no==3)//Show ATM process
				{
					ArrayList<String> Trans_remarks = new ArrayList<String>(5); 
					ArrayList<String> Trans_type = new ArrayList<String>(5); ;
					ArrayList<Integer> Trans_amt = new ArrayList<Integer>(5); 
					
					System.out.println("Enter your account number: ");
					int tempAcc = input.nextInt();
					System.out.println("Enter your PIN: ");
					int tempPIN = input.nextInt();
					for(i=0;i<5;i++)
					{
						
						if(cu[i].accountNumber==tempAcc&&cu[i].PIN==tempPIN)//UserName and PIN VALIDATION
						{	int atmTaskNumber = 0;
							
							while(atmTaskNumber!=5)
							{
								System.out.println("Choice \n 1.Check Balance \n 2. Withdraw money \n 3.Transfer Money \n 4.Mini statement \n5.exit");
								 atmTaskNumber = input.nextInt();
							if(atmTaskNumber==1)// Checking Balance
							{
								System.out.println("The balance in your account is "+cu[i].balance);
								
								
							}
							 if(atmTaskNumber==2)// Withdraw Amount 
							{
								System.out.println("Enter PIN :");
								tempPIN = input.nextInt();
								
								
								if(cu[i].PIN==tempPIN)
								{
									System.out.println("ENter Amount to withdraw");
									int tempWithdraw = input.nextInt();
									if(total_cash<tempWithdraw)
									{
										System.out.println("Not enough money ");
										break;
									}
									if(tempWithdraw<100 || tempWithdraw>10000)
									{
										System.out.println("Exceeded the limits");
										break;
									}
									if(cu[i].balance<tempWithdraw)
									{
										System.out.println("Balance too low ");
										break;
										
									}
									if(tempWithdraw<=5000)
									{
										if(tempWithdraw<1000)
										{
											fivehundred_counter=fivehundred_counter-(tempWithdraw/500);
											hundred_counter=hundred_counter-((tempWithdraw%500)/100);		
											
										}
										else 
										{
											thousand_counter=thousand_counter-1;
											if(tempWithdraw-1000<500)
											{
												hundred_counter=hundred_counter-((tempWithdraw-1000)/100);
											}
											
											else if(((tempWithdraw-1000)/500)>6)
												{
													fivehundred_counter=fivehundred_counter-6;
													hundred_counter=hundred_counter-((tempWithdraw-4000)/100);
													
												}
											else 
											{
												fivehundred_counter=fivehundred_counter-((tempWithdraw-1000)/500);
												hundred_counter=hundred_counter-(((tempWithdraw-1000)%500)/100);
											}
											
										}
									}
									else if(tempWithdraw>5000)
									{
										thousand_counter=thousand_counter-3;
										fivehundred_counter=fivehundred_counter-3;
										if(tempWithdraw<6000)
										{
											fivehundred_counter=fivehundred_counter-((tempWithdraw-5000)/500);
											hundred_counter=hundred_counter-(((tempWithdraw-5000)%500)/100);	
										}
										else 
										{
											fivehundred_counter=fivehundred_counter-((tempWithdraw-5000)/500);
											hundred_counter=hundred_counter-(((tempWithdraw-5000)%500)/100);	
										}
									}
									//System.out.println(thousand_counter+"  "+fivehundred_counter+"  "+hundred_counter);
									total_cash=total_cash-tempWithdraw;
									PreparedStatement pe=con.prepareStatement(update_balance);
									pe.setInt(1, total_cash);
									pe.setInt(2, thousand_counter);
									pe.setInt(3,fivehundred_counter );
									pe.setInt(4, hundred_counter);
									pe.executeUpdate();
									cu[i].balance=cu[i].balance-tempWithdraw;
									
									 Trans_remarks.add("Debited "+tempWithdraw+" from ATM");
									 Trans_type.add("Debit");
									 Trans_amt.add(tempWithdraw);
									 String Customer_update2 = "update customer set balance = ? where account_no = ? ";
									 PreparedStatement pe1=con.prepareStatement(Customer_update2);
										
										pe1.setInt(1, cu[i].balance);
										pe1.setInt(2, (100+i+1));
										pe1.executeUpdate();
										System.out.println("Withdraw Completed\n");
									
									
								}
								
								
								
							}
							 if(atmTaskNumber==3)//TransferAmount
							{
								System.out.println("Enter account number to transfer money");
								int tempAccno = input.nextInt();
								int j=0;
								while(j<5)
								{
									if(cu[j].accountNumber==tempAccno)
										
									{	int flag=j;
										System.out.println("Enter Amount to transfer");
										int tempTransfer = input.nextInt();
										if(tempTransfer>=1000&&tempTransfer<=10000)
										{
											cu[i].balance=cu[i].balance-tempTransfer;
											cu[j].balance=cu[j].balance+tempTransfer;
											String Customer_update1 = "update customer set balance = ? where account_no = ? ";
											PreparedStatement pe=con.prepareStatement(Customer_update1);
											System.out.println(+cu[i].balance);
											pe.setInt(1, cu[i].balance);
											pe.setInt(2, (100+i+1));
											pe.executeUpdate();
											pe.setInt(1, cu[flag].balance);
											pe.setInt(2, (100+flag+1));
											pe.executeUpdate();
											 Trans_remarks.add("Funds transfered to "+(flag+100+1));
											 Trans_type.add("Debit");
											 Trans_amt.add(tempTransfer);
											 System.out.println("Transfer Completed\n");
										}
										else 
										{
											System.out.println("Beyond limits ");
											break;
										}
										 
										
										
										
								}
									j++;
								}
							
								
								
							
								
							}
							 if(atmTaskNumber==4)//MiniStatement 
							{
								 System.out.println("MINI STATEMENT\nAccount Holder:"+cu[i].accountHolder+"\nAccount Number:"+cu[i].accountNumber+"\nAccount Balance:"+cu[i].balance);
								System.out.println("Transaction ID	Transaction Remarks		Transaction Type	Transaction Amt");
								for(int j=0;j<Trans_remarks.size();j++)
								{
									System.out.println(j+1+"		"+Trans_remarks.get(j)+"		"+Trans_type.get(j)+"			"+Trans_amt.get(j)+"\n");
								}
							}
							
						}
						}
						
						else if(cu[i].accountNumber!=tempAcc&&cu[i].PIN!=tempPIN) 
						{
							System.out.println("Wrong Credentials");
							break;
						}
						
					
					}
					
				}
				else if(task_no==4)//Show Total Balance of the machine 
				{
					System.out.println("Enter Admin user name:");
					String adminUser = input.next();
					System.out.println("Enter Admin Password");
					String adminPass=input.next();
					if(adminUser.equalsIgnoreCase("admin")&&adminPass.equals("admin"))
					{
					String Balance_select_statement = " select * from balance";
					ResultSet re2 =Stmnt.executeQuery(Balance_select_statement);
					
					while(re2.next())
					{
						System.out.println("Total Balance:"+re2.getString(2)+"\n1000s:"+re2.getString(3)+"\n500s:"+re2.getString(4)+"\n100s:"+re2.getString(5));
					}
		
				}
					else 
					{
						System.out.println("Wrong Credentials");
					}
					}
				
			}
			
			
			
		input.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}
		

	}

}
