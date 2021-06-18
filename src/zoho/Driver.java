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
			String Balance_select_statement = " select * from balance";
			ResultSet re2 =Stmnt.executeQuery(Balance_select_statement);
			int total_cash=0;
			int thousand_counter=0;
			int fivehundred_counter=0;
			int hundred_counter=0;
			//int ministatement_id_counter = 0;
			
			while(re2.next())
			{	
				 total_cash=re2.getInt(2);
				 thousand_counter=re2.getInt(3);;
				 fivehundred_counter=re2.getInt(4);
				 hundred_counter=re2.getInt(5);
				
			}
			String insertBalanceZero = "insert into balance values()";
			String selectBalanceZero = "select * from balance";
			ResultSet rs = Stmnt.executeQuery(selectBalanceZero);
		
			if(!rs.next())
			{
			Stmnt.executeUpdate(insertBalanceZero);
			total_cash=0;
			thousand_counter=0;
			fivehundred_counter=0;
			hundred_counter=0;
			}
			
			
			
		
			
			int task_no= 0;
			
			String update_balance = "update balance set account_balance = ? , thousands = ? , fivehundreds = ? , hundreds = ? where id = '0'";
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
					ResultSet rs6 = Stmnt.executeQuery(selectBalanceZero);
					
					if(!rs6.next())
					{
					Stmnt.executeUpdate(insertBalanceZero);
					total_cash=0;
					thousand_counter=0;
					fivehundred_counter=0;
					hundred_counter=0;
					}
					if(adminUser.equalsIgnoreCase("admin")&&adminPass.equals("admin"))
					{
						
								System.out.println("Enter how many lakhs to load :");
						int n = input.nextInt();
						if(n<0)
						{
							System.out.println("Enter Positive number ");
							break;
						}
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
//					ArrayList<String> Trans_remarks = new ArrayList<String>(5); 
//					ArrayList<String> Trans_type = new ArrayList<String>(5); ;
//					ArrayList<Integer> Trans_amt = new ArrayList<Integer>(5); 
					
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
								System.out.println("Choice \n 1.Check Balance \n 2. Withdraw money \n 3.Transfer Money \n 4.Mini statement \n 5.Exit");
								 atmTaskNumber = input.nextInt();
							if(atmTaskNumber==1)// Checking Balance
							{
								System.out.println("The balance in your account is "+cu[i].balance);
								
								
							}
							 if(atmTaskNumber==2)// Withdraw Amount 
							{
								System.out.println("Enter PIN :");
								tempPIN = input.nextInt();
								int flagTotal=total_cash;
								int flagThousand=thousand_counter;
								int flagFivehundred=fivehundred_counter;
								int flagHundred=hundred_counter;
								
								
								if(cu[i].PIN==tempPIN)
								{
									System.out.println("ENter Amount to withdraw");
									int tempWithdraw = input.nextInt();
									if(tempWithdraw%100!=0)
									{
										System.out.println("Enter Multiple of hundred");
										break;
									}
									if(total_cash<tempWithdraw)
									{
										System.out.println("Not enough money ");
										break;
									}
									if(tempWithdraw<100 || tempWithdraw>=10000)
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
										if(thousand_counter==0)
										{
											if(fivehundred_counter==0)
											{
												if(tempWithdraw/100<=hundred_counter)
												{
													hundred_counter=hundred_counter-(tempWithdraw/100);
												}
												else
												{
													System.out.println("Not enough money");
													break;
												}
											}
											else 
											{
												if(tempWithdraw/500<=fivehundred_counter||(tempWithdraw-(fivehundred_counter*500))/100<=hundred_counter)
												{
													fivehundred_counter=fivehundred_counter-((tempWithdraw)/500);
													hundred_counter=hundred_counter-(((tempWithdraw)%500)/100);
												}
												else
												{
													System.out.println("Not enough money");
													break;
												}
											}
										}
										else
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
									}
									else if(tempWithdraw>5000)
									{
										if(thousand_counter==0)
										{
											if(fivehundred_counter==0)
											{
												if(tempWithdraw/100<=hundred_counter)
												{
													hundred_counter=hundred_counter-(tempWithdraw/100);
												}
												else
												{
													System.out.println("Not enough money");
													break;
												}
											}
											else 
											{
												if(tempWithdraw/500<=fivehundred_counter||(tempWithdraw-(fivehundred_counter*500))/100<=hundred_counter)
												{
													fivehundred_counter=fivehundred_counter-((tempWithdraw)/500);
													hundred_counter=hundred_counter-(((tempWithdraw)%500)/100);
												}
												
												else
												{
													System.out.println("Not enough money");
													break;
												}
											}
										}
										else 
										{
											
										
										thousand_counter=thousand_counter-3;
										fivehundred_counter=fivehundred_counter-2;
										hundred_counter=hundred_counter-10;
								
										if(tempWithdraw<6000)
										{
											fivehundred_counter=fivehundred_counter-((tempWithdraw-5000)/500);
											hundred_counter=hundred_counter-(((tempWithdraw-5000)%500)/100);	
										}
										else if(tempWithdraw>=6000)
										{
											fivehundred_counter=fivehundred_counter-((tempWithdraw-5000)/500);
											hundred_counter=hundred_counter-(((tempWithdraw-5000)%500)/100);	
										}
										
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
									
//									 Trans_remarks.add("Debited "+tempWithdraw+" from ATM");
//									 Trans_type.add("Debit");
//									 Trans_amt.add(tempWithdraw);
									 String mini_insert = "insert into ministatement(account_no,transaction_remarks,transaction_type,transaction_amt) values(?,?,?,?)";
										PreparedStatement pe3=con.prepareStatement( mini_insert);
										//pe3.setInt(1, ministatement_id_counter++);
										pe3.setInt(1, cu[i].accountNumber);
										pe3.setString(2, "Debit "+(tempWithdraw)+"from the ATM");
										pe3.setString(3, "Debit");
										pe3.setInt(4,tempWithdraw);
										pe3.executeUpdate();
									 String Customer_update2 = "update customer set balance = ? where account_no = ? ";
									 PreparedStatement pe1=con.prepareStatement(Customer_update2);
										
										pe1.setInt(1, cu[i].balance);
										pe1.setInt(2, (100+i+1));
										pe1.executeUpdate();
										System.out.println("Withdraw Completed\n");
										System.out.println("1000s:"+(flagThousand-thousand_counter)+" 500s:"+(flagFivehundred-fivehundred_counter)+" 100s:"+(flagHundred-hundred_counter));
									
									
								}
								else 
								{
									System.out.println("Check pin again");
								}
								
								
								
							}
							 if(atmTaskNumber==3)//TransferAmount
							{
								System.out.println("Enter account number to transfer money");
								int tempAccno = input.nextInt();
								if(tempAccno==cu[i].accountNumber)
									{System.out.println("Cant tranfer to the same account");
									break;
									}
								int j=0;
								while(j<5)
								{
									if(cu[j].accountNumber==tempAccno)
										
									{	int flag=j;
										System.out.println("Enter Amount to transfer");
										int tempTransfer = input.nextInt();
										if(tempTransfer>cu[i].balance)
										{
											System.out.println("insuffient bal");
											break;
										}
										if(tempTransfer>=1000&&tempTransfer<=10000)
										{
											cu[i].balance=cu[i].balance-tempTransfer;
											cu[j].balance=cu[j].balance+tempTransfer;
											String Customer_update1 = "update customer set balance = ? where account_no = ? ";
											PreparedStatement pe=con.prepareStatement(Customer_update1);
											//System.out.println(+cu[i].balance);
											pe.setInt(1, cu[i].balance);
											pe.setInt(2, (100+i+1));
											pe.executeUpdate();
											pe.setInt(1, cu[flag].balance);
											pe.setInt(2, (100+flag+1));
											pe.executeUpdate();
											String mini_insert = "insert into ministatement(account_no,transaction_remarks,transaction_type,transaction_amt) values(?,?,?,?)";
											PreparedStatement pe3=con.prepareStatement( mini_insert);
											//pe3.setInt(1, ministatement_id_counter++);
											pe3.setInt(1, cu[i].accountNumber);
											pe3.setString(2, "Funds transfered to "+(flag+100+1));
											pe3.setString(3, "Debit");
											pe3.setInt(4,tempTransfer);
											pe3.executeUpdate();
											String mini_insert2 = "insert into ministatement(account_no,transaction_remarks,transaction_type,transaction_amt) values(?,?,?,?)";
											PreparedStatement pe4=con.prepareStatement( mini_insert2);
											//pe4.setInt(1, ministatement_id_counter++);
											pe4.setInt(1, cu[flag].accountNumber);
											pe4.setString(2, "Credited  from "+(i+100+1));
											pe4.setString(3, "Credit");
											pe4.setInt(4,tempTransfer);
											pe4.executeUpdate();
											
//											 Trans_remarks.add("Funds transfered to "+(flag+100+1));
//											 Trans_type.add("Debit");
//											 Trans_amt.add(tempTransfer);
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
								String ministatement_select_statement2 = " select * from ministatement where account_no=? order by transaction_id desc limit 5 ;\r\n"
										+ " ";
								PreparedStatement pe4=con.prepareStatement( ministatement_select_statement2);
								pe4.setInt(1,cu[i].accountNumber);
								ResultSet re3 =pe4.executeQuery();
								
								while(re3.next())
								{
									System.out.println(re3.getInt(1)+"		"+re3.getString(3)+"		"+re3.getString(4)+"			"+re3.getInt(5)+"\n");
								}
							}
							
						}
						}
						
					
					
					}
					
				}
				else if(task_no==4)//Show Total Balance of the machine 
				{ResultSet rs2 = Stmnt.executeQuery(selectBalanceZero);
		
	
			
				if(!rs2.next())
				{
				Stmnt.executeUpdate(insertBalanceZero);
				total_cash=0;
				thousand_counter=0;
				fivehundred_counter=0;
				hundred_counter=0;
				}
					System.out.println("Enter Admin user name:");
					String adminUser = input.next();
					System.out.println("Enter Admin Password");
					String adminPass=input.next();
					if(adminUser.equalsIgnoreCase("admin")&&adminPass.equals("admin"))
					{
						String Balance_select_statement2 = " select * from balance";
						ResultSet re3 =Stmnt.executeQuery(Balance_select_statement2);
						
						while(re3.next())
						{
							System.out.println("Total Balance:"+re3.getString(2)+"\n1000s:"+re3.getString(3)+"\n500s:"+re3.getString(4)+"\n100s:"+re3.getString(5));
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
