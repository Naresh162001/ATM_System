package Zoho_preparation_ATM;
import java.util.*;
import java.io.*;

class ATMMachine{
	static int noOf2000;
	static int noOf500;
	static int noOf100;
	int limitNoOf2000=100000;
	int limitNoOf500=1000000;
	int limitNoOf100=1000000;
	public void loadCurrency(int noOf2000,int noOf500,int noOf100){
		if(this.noOf2000+noOf2000>limitNoOf2000 && this.noOf500+noOf500>limitNoOf500 && this.noOf100+noOf100>limitNoOf100) {
			System.out.println("ATM capacity is Full.");
			return;
		}
		this.noOf2000+=noOf2000;
		this.noOf500+=noOf500;
		this.noOf100+=noOf100;
	}
	public void loadCurrencyByFile() throws Exception {
		File currencyFile=new File("C:\\Users\\nares\\OneDrive\\Documents\\LoadMoney.txt");
		Scanner reader;
		
		reader = new Scanner(currencyFile);
		reader.nextLine();
		this.noOf2000+=reader.nextInt();
		this.noOf500+=reader.nextInt();
		this.noOf100+=reader.nextInt();

	}
	public void DisplayCurrencyAvailable() {
		System.out.println("Denomination\t"+"Number\t"+"Value");
		System.out.println("2000"+"\t\t"+noOf2000+"\t\t"+(2000*noOf2000));
		System.out.println("500"+"\t\t"+noOf500+"\t\t"+(2000*noOf500));
		System.out.println("100"+"\t\t"+noOf100+"\t\t"+(2000*noOf100));
	}
	public int getNoOf2000() {
		return noOf2000;
	}
	public int getNoOf500() {
		return noOf500;
	}
	public int getNoOf100() {
		return noOf100;
	}
	public void setNoOf2000(int noOf2000) {
		this.noOf2000=noOf2000;
	}
	public void setNoOf500(int noOf2000) {
		this.noOf500=noOf500;
	}
	public void setNoOf100(int noOf2000) {
		this.noOf100=noOf100;
	}
	public void withDraw(int accNo,int pinNo,Double amountToWithDraw,AllCustomerDetails currentCustomerDetails) {
		int noOf2000ToWithdraw=0;
		int noOf500ToWithdraw=0;
		int noOf100ToWithdraw=0;
		Double amountWithDrawn=amountToWithDraw;
		if(amountToWithDraw>2000) {
			noOf2000ToWithdraw=(int)(amountToWithDraw/2000);
			if(noOf2000ToWithdraw>noOf2000) {
				noOf2000ToWithdraw=noOf2000;
			}
			noOf2000-=noOf2000ToWithdraw;
			amountToWithDraw-=(noOf2000ToWithdraw*2000);
		}
		if(amountToWithDraw>500) {
			noOf500ToWithdraw=(int)(amountToWithDraw/500);
			if(noOf500ToWithdraw>noOf500) {
				noOf500ToWithdraw=noOf500;
			}
			noOf500-=noOf500ToWithdraw;
			amountToWithDraw-=(noOf500ToWithdraw*500);
		}
		if(amountToWithDraw>100) {
			noOf100ToWithdraw=(int)(amountToWithDraw/100);
			if(noOf100ToWithdraw>noOf100) {
				noOf100ToWithdraw=noOf100;
			}
			noOf100-=noOf100ToWithdraw;
			amountToWithDraw-=(noOf100ToWithdraw*100);
		}
		if(amountToWithDraw !=0) {
			System.out.println("ATM machine does not have enough space");
		}
		else {
			if(noOf2000ToWithdraw!=0) {
				System.out.println("2000 X "+noOf2000ToWithdraw);
			}
			if(noOf500ToWithdraw!=0) {
				System.out.println("500 X "+noOf500ToWithdraw);
			}
			if(noOf100ToWithdraw!=0) {
				System.out.println("100 X "+noOf100ToWithdraw);
			}
			currentCustomerDetails.updateBalance(accNo,pinNo,-amountWithDrawn);
		}
	}
	
	
}

public class ATM {
	static ATMMachine atmMachine=new ATMMachine();
	static Scanner io=new Scanner(System.in);
	static AllCustomerDetails customerDetails=new AllCustomerDetails();
	static boolean flag=true;
	static ATM atmObj=new ATM();
	public void LoadCurrency() {
		System.out.println("Enter Currency Details");
		System.out.println("No. of 2000:");
		int noOf2000=io.nextInt();
		System.out.println("No. of 500:");
		int noOf500=io.nextInt();
		System.out.println("No. of 100:");
		int noOf100=io.nextInt();
		atmMachine.loadCurrency(noOf2000,noOf500,noOf100);
		System.out.println("Currency Loaded Successfull");
	}
	public void addNewCustomer() {
		System.out.println("Enter the New Customer Details:");
		System.out.println("Enter Account Holder Name:");
		String accountHolderName=io.next();
		System.out.println("Enter the Pin No:");
		int pinNo=io.nextInt();
		System.out.println("Enter the Account Balance:");
		double accountBalance=io.nextDouble();
		customerDetails.addCustomerDetails(accountHolderName,pinNo,accountBalance);
	}
	public void bankOfficerOperation() {
		System.out.println("1. Display Account Holder Details");
		System.out.println("2. Check ATM Balance");
		System.out.println("3. Load Currency");
		System.out.println("4. Add new Customer");
		System.out.println("5. Exist");
		int choice=io.nextInt();
		switch(choice) {
		case 1:
			customerDetails.displayDetails();
			break;
		case 2:
			atmMachine.DisplayCurrencyAvailable();
			break;
		case 3:
			atmObj.LoadCurrency();
			break;
		case 4:
			atmObj.addNewCustomer();
			break;
		case 5:
			flag=false;
			break;
		}
	}
	public void withDrawMoney(int accNo,AllCustomerDetails currentCustomerDetails) {
		System.out.println("Enter Your Pin:");
		int pinNo=io.nextInt();
		int status=currentCustomerDetails.verifyAccount(accNo, pinNo);
		if(status==-1) {
			System.out.println("Incorrect Pin");
			return;
		}
		System.out.println("Enter the amount to Withdraw:");
		Double amountToWithDraw=io.nextDouble();
		double balance=customerDetails.getBalance(accNo, pinNo);
		if(amountToWithDraw>balance) {
			System.out.println("Your account balance is low: "+balance);
			return;
		}
		if(amountToWithDraw<100 || amountToWithDraw>10000) {
			System.out.println("You can't withdraw!\nEnter the amount between 100 and 10000.");
			return;
		}
		if(!(amountToWithDraw%100==0)) {
			System.out.println("You can't Withdraw!\nEnter the valid amount.");
			return;
		}
		atmMachine.withDraw(accNo,pinNo,amountToWithDraw,currentCustomerDetails);
		System.out.println("Account Balance:"+customerDetails.getBalance(accNo, pinNo));
	}
	public void TransferMoney(int accNo,AllCustomerDetails currentCustomerDetails) {
		System.out.println("Enter Your Pin");
		int pinNo=io.nextInt();
		int status=currentCustomerDetails.verifyAccount(accNo, pinNo);
		if(status==-1) {
			System.out.println("Incorrect Pin");
			return;
		}
		System.out.println("Enter The Receiver Account No.:");
		int receiverAccNo=io.nextInt();
		if(accNo==receiverAccNo) {
			System.out.println("Verify the Account No.");
		}
		if(!customerDetails.isAccountAvailable(receiverAccNo)) {
			System.out.println("ReceiverAccount No. is Invalid");
			return;
		}
		System.out.println("Enter the amount to Transfer:");
		double amountToTransfer=io.nextDouble();
		double balance=customerDetails.getBalance(accNo, pinNo);
		if(amountToTransfer>balance) {
			System.out.println("Your account balance is low: "+balance);
			return;
		}
		if(amountToTransfer<1000 || amountToTransfer>10000) {
			System.out.println("You can't withdraw!\nEnter the amount between 1000 and 100000.");
			return;
		}
		currentCustomerDetails.transferMoney(accNo,pinNo,receiverAccNo,amountToTransfer);
	}
	public void CustomerOperations() {
		System.out.println("Enter Your Account No.:");
		int accNo=io.nextInt();
		System.out.println("Enter Your Pin");
		int pinNo=io.nextInt();
		AllCustomerDetails currentCustomerDetails=new AllCustomerDetails();
		int status=currentCustomerDetails.verifyAccount(accNo, pinNo);
		if(status==-1) {
			System.out.println("Incorrect Pin");
			return;
		}
		else if(status==-2) {
			System.out.println("Account No Is Invalid");
			return;
		}
		System.out.println("1. Check Balance");
		System.out.println("2. Withdraw Money");
		System.out.println("3. Transfer Money");
		System.out.println("4. Exit");
		System.out.println("Enter your choice:");
		int choice=io.nextInt();
		switch(choice) {
		case 1:
			double balance=customerDetails.getBalance(accNo, pinNo);
			if(balance==-1) {
				System.out.println("Try again");
			}
			else {
				System.out.println(balance);
			}
			break;
		case 2:
			atmObj.withDrawMoney(accNo,currentCustomerDetails);
			break;
		case 3:
			atmObj.TransferMoney(accNo,currentCustomerDetails);
			break;
		case 4:
			flag=false;
			break;
		}
	}
	public static void main(String[] args) throws Exception{		
		atmMachine.loadCurrencyByFile();
		customerDetails.addCustomerDetailsFromFile();
		
		while(flag){
			System.out.println("Choose your profession:");
			System.out.println("1. Bank Officer");
			System.out.println("2. Customer");
			System.out.println("3. Exit");
			int choice1=io.nextInt();
			switch(choice1) {
			case 1:
				atmObj.bankOfficerOperation();
				break;
			case 2:
				atmObj.CustomerOperations();
				break;				
			case 3:
				flag=false;
				break;
			}
			System.out.println("---------------------------------------------------------------------------------");
			
		}

	}

}
