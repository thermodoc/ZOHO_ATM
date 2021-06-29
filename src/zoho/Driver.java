package zoho;


import java.util.ArrayList;
import java.util.Scanner;



public class Driver  {
	
	public static boolean isAdmin()
	{	Scanner input =new Scanner(System.in);
		System.out.println("Enter Admin user name:");
		String adminUser = input.next();
		System.out.println("Enter Admin Password");
		String adminPass=input.next();
		
		if(adminUser.equalsIgnoreCase("admin")&&adminPass.equals("admin"))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public static void customerOperations(ArrayList<Customer> customer , int i, int tempPIN)
		{
		int atmTaskNumber = 0;
		Scanner input =new Scanner(System.in);
		while(atmTaskNumber != 5)
		{
				System.out.println("Choice \n 1.Check Balance \n 2. Withdraw money \n 3.Transfer Money \n 4.Mini statement \n 5.Exit");
				 atmTaskNumber = input.nextInt();
			if(atmTaskNumber == 1)// Checking Balance
			{
				String CustomerBalance = customer.get(i).showCustomerBalance();
				System.out.println(CustomerBalance);
				
				
			}
			 if(atmTaskNumber == 2)// Withdraw Amount 
			{	Balance.checkifBalalanceIsNull();
				System.out.println("Enter PIN :");
				tempPIN = input.nextInt();
				//int flagTotal=totalCash;
				int flagThousand=Balance.thousandCounter;
				int flagFivehundred=Balance.fiveHundredCounter;
				int flagHundred=Balance.hundredCounter;
				
				
				if(customer.get(i).PIN == tempPIN)
				{
					System.out.println("Enter Amount to withdraw");
					int tempWithdraw = input.nextInt();
					boolean isWithdraw=Balance.withDraw(tempWithdraw, customer.get(i));
					if(isWithdraw)
					{
					Balance.totalCash=Balance.totalCash-tempWithdraw;
					Balance.updateBalance();
					customer.get(i).balance=customer.get(i).balance-tempWithdraw;
					Ministatement.updateMiniStatementDebit(tempWithdraw,customer.get(i));
					customer.get(i).updateCustomerBalance(i);

						System.out.println("Withdraw Completed\n");
						System.out.println("1000s:"+(flagThousand-Balance.thousandCounter)+" 500s:"+(flagFivehundred-Balance.fiveHundredCounter)+" 100s:"+(flagHundred-Balance.hundredCounter));//TODO
					
					}
					else 
					{
						break;
					}
				}
				else 
				{
					System.out.println("Check pin again");
				}
			
			}
			 if(atmTaskNumber == 3)//TransferAmount
			{
				
				System.out.println("Enter account number to transfer money");
				int tempAccno = input.nextInt();
				if(!customer.get(i).isAccount(customer , tempAccno))
				{
					System.out.println("Enter Correct Account number");
					break;
				}
				if(tempAccno==customer.get(i).accountNumber)
					{
						System.out.println("Cant transfer to the same account");
						break;
					}
			
				int j=0;
				while(j < 5)
				{
					if(customer.get(j).accountNumber == tempAccno)
						
					{	int flag=j;
						System.out.println("Enter Amount to transfer");
						int tempTransfer = input.nextInt();
						if(tempTransfer > customer.get(i).balance)
						{
							System.out.println("insuffient bal");
							break;
						}
						if(tempTransfer >= 1000 && tempTransfer <= 10000)
						{
							customer.get(i).balance=customer.get(i).balance-tempTransfer;
							customer.get(j).balance=customer.get(j).balance+tempTransfer;
							customer.get(i).updateCustomerBalance(i);
							customer.get(flag).updateCustomerBalance(flag);
							
							
							Ministatement.updateMiniStatementTransferDebit(tempTransfer, customer, i, flag);
							Ministatement.updateMiniStatementTransferCredit(tempTransfer, customer, flag, i);
														
						
							
//											 
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
			 if(atmTaskNumber == 4)//MiniStatement 
			{
				 Ministatement.printMiniStatement(customer, i);
			}
			
		}
		
		}

	public static void main(String[] args) {
		
		try {
			Scanner input =new Scanner(System.in);
			Balance.getBalanceFromDB();
			//System.out.println(totalCash+" "+thousandCounter+" "+fiveHundredCounter+" "+" "+hundredCounter);
			int taskNo= 0;
			ArrayList<Customer> customer = new ArrayList<Customer>();
		
			customer=Customer.getCustomerDetailsFromDB(customer, 0);
			while(taskNo != 5)
			{
				int i=0;
			
				
				System.out.println("ATM \n\n Choose your task \n 1.Load cash(Admin) \n 2.Show Customer details(Admin) \n 3.Customer ATM operations(Customer) \n 4.Show Total Balance(ADMIN)\n 5.exit");
			
				taskNo=input.nextInt();
				
				if(taskNo==1)//											Load cash to ATM
				{
					
					Balance.checkifBalalanceIsNull();
					if(isAdmin() == true)
					{
						
								System.out.println("Enter how many lakhs to load :");
						int n = input.nextInt();
						if(n<0)
						{
							System.out.println("Enter Positive number ");
							break;
						}
					Balance.loadBalance(n);
					}
					else 
					{
						System.out.println("Wrong Credentials");
					}
					
					
					}
					else if(taskNo == 2) // Display the customer details 
					{
					
						
						if(isAdmin())
						{
							System.out.println("Account Number		Account Holder		PIN		Balance\n");
							for(i=0;i<customer.size();i++)
							{
							customer.get(i).showCustomerDetails();
							}
						}
						else 
						{
							System.out.println("Wrong Credentials");
						}
					}
				
				
				else if (taskNo == 3)//Show ATM process
				{
		
					System.out.println("Enter your account number: ");
					int tempAcc = input.nextInt();
					System.out.println("Enter your PIN: ");
					int tempPIN = input.nextInt();
					for(i=0;i<customer.size();i++)
					{
						if(!customer.get(i).isAccount(customer , tempAcc))
						{
							System.out.println("Enter Correct Account number");
							break;
						}
						Balance.checkifBalalanceIsNull();
						if(customer.get(i).accountNumber == tempAcc)//UserName and PIN VALIDATION
						{	
							if(customer.get(i).PIN == tempPIN)
							customerOperations( customer, i, tempPIN);
							else
								System.out.println("Check the Credentials");
							
							
							}

						}
						
					}
					else if(taskNo == 4)//Show Total Balance of the machine 
					{
			
						Balance.checkifBalalanceIsNull();
					
						if(isAdmin())
						{
							Balance.showBalance();
				
							}
						else 
						{
							System.out.println("Wrong Credentials");
						}
					}
				
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace( );
		}

		

	}

}
