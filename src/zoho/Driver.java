package zoho;


import java.util.Scanner;



public class Driver extends Balance  {
	
	public static boolean isAdmin()
	{	Scanner input =new Scanner(System.in);
		System.out.println("Enter Admin user name:");
		String adminUser = input.next();
		System.out.println("Enter Admin Password");
		String adminPass=input.next();
		input.close();
		if(adminUser.equalsIgnoreCase("admin")&&adminPass.equals("admin"))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public static void customerOperations(int atmTaskNumber , Customer cu[] , int i, int tempPIN)
		{
		Scanner input =new Scanner(System.in);
		while(atmTaskNumber!=5)
		{
				System.out.println("Choice \n 1.Check Balance \n 2. Withdraw money \n 3.Transfer Money \n 4.Mini statement \n 5.Exit");
				 atmTaskNumber = input.nextInt();
			if(atmTaskNumber==1)// Checking Balance
			{
				String CustomerBalance = cu[i].showCustomerBalance();
				System.out.println(CustomerBalance);
				
				
			}
			 if(atmTaskNumber==2)// Withdraw Amount 
			{	checkifBalalanceIsNull();
				System.out.println("Enter PIN :");
				tempPIN = input.nextInt();
				//int flagTotal=totalCash;
				int flagThousand=thousandCounter;
				int flagFivehundred=fiveHundredCounter;
				int flagHundred=hundredCounter;
				
				
				if(cu[i].PIN==tempPIN)
				{
					System.out.println("Enter Amount to withdraw");
					int tempWithdraw = input.nextInt();
					boolean isWithdraw=withDraw(tempWithdraw, cu, i);
					if(isWithdraw)
					{
					totalCash=totalCash-tempWithdraw;
					updateBalance();
					cu[i].balance=cu[i].balance-tempWithdraw;
					updateMiniStatementDebit(tempWithdraw,cu,i);
					cu[i].updateCustomerBalance(i);

						System.out.println("Withdraw Completed\n");
						System.out.println("1000s:"+(flagThousand-thousandCounter)+" 500s:"+(flagFivehundred-fiveHundredCounter)+" 100s:"+(flagHundred-hundredCounter));
					
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
			 if(atmTaskNumber==3)//TransferAmount
			{
				
				System.out.println("Enter account number to transfer money");
				int tempAccno = input.nextInt();
				if(tempAccno==cu[i].accountNumber)
					{
						System.out.println("Cant transfer to the same account");
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
							cu[i].updateCustomerBalance(i);
							cu[flag].updateCustomerBalance(flag);
							
							
							updateMiniStatementTransferDebit(tempTransfer, cu, i, flag);
							updateMiniStatementTransferCredit(tempTransfer, cu, flag, i);
														
						
							
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
			 if(atmTaskNumber==4)//MiniStatement 
			{
				 printMiniStatement(cu, i);
			}
			
		}
		input.close();
		}

	public static void main(String[] args) {
		
		try {
			Scanner input =new Scanner(System.in);
			getBalanceFromDB();
			System.out.println(totalCash+" "+thousandCounter+" "+fiveHundredCounter+" "+" "+hundredCounter);
			int task_no= 0;
			Customer[] cu=new Customer[5];
			for(int i=0;i<5;i++)
			{
				cu[i]=new Customer();
			}
			while(task_no!=5)
			{
				int i=0;
				getCustomerDetailsFromDB(cu, i);
				
				System.out.println("ATM \n\n Choose your task \n 1.Load cash(Admin) \n 2.Show Customer details(Admin) \n 3.Customer ATM operations(Customer) \n 4.Show Total Balance(ADMIN)\n 5.exit");
				task_no=input.nextInt();
				if(task_no==1)//											Load cash to ATM
				{
					
					checkifBalalanceIsNull();
					if(isAdmin()==true)
					{
						
								System.out.println("Enter how many lakhs to load :");
						int n = input.nextInt();
						if(n<0)
						{
							System.out.println("Enter Positive number ");
							break;
						}
					loadBalance(n);
					}
					else 
					{
						System.out.println("Wrong Credentials");
					}
					
					
					}
					else if(task_no==2) // Display the customer details 
					{
					
						
						if(isAdmin())
						{
							System.out.println("Account Number		Account Holder		PIN		Balance\n");
							for(i=0;i<5;i++)
							{
							cu[i].showCustomerDetails();
							}
						}
						else 
						{
							System.out.println("Wrong Credentials");
						}
					}
				
				
				else if (task_no==3)//Show ATM process
				{
		
					System.out.println("Enter your account number: ");
					int tempAcc = input.nextInt();
					System.out.println("Enter your PIN: ");
					int tempPIN = input.nextInt();
					for(i=0;i<5;i++)
					{
						checkifBalalanceIsNull();
						if(cu[i].accountNumber==tempAcc&&cu[i].PIN==tempPIN)//UserName and PIN VALIDATION
						{	
							int atmTaskNumber = 0;
							customerOperations(atmTaskNumber, cu, i, tempPIN);
							
							
							}

						}
						
					}
					else if(task_no==4)//Show Total Balance of the machine 
					{
			
						checkifBalalanceIsNull();
					
						if(isAdmin())
						{
							showBalance();
				
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
