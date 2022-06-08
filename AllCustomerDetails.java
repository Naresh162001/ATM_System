package Zoho_preparation_ATM;
import java.util.*;
import java.io.*;
class CustomerDetails {
	private int accNo;
	private String accountHolderName;
	private int pinNo;
	private double accountBalance;
	public CustomerDetails(int accNo,String accountHolderName,int pinNo,double accountBalance) {
		this.accNo=accNo;
		this.accountHolderName=accountHolderName;
		this.pinNo=pinNo;
		this.accountBalance=accountBalance;	
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public int getPinNo() {
		return pinNo;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setBalance(Double amount) {
		accountBalance+=amount;
	}
}
public class AllCustomerDetails{
	static int accNo=100;
	private boolean accountDetailsVerified=false;
	private static Map<Integer,CustomerDetails> CustomerDetailsList=new LinkedHashMap<>(); 
	public void addCustomerDetails(String accountHolderName,int pinNo,Double accountBalance)  {
		accNo+=1;
		CustomerDetailsList.put(accNo, new CustomerDetails(accNo,accountHolderName,pinNo,accountBalance));
		try {
			FileWriter writer=new FileWriter("C:\\Users\\nares\\OneDrive\\Documents\\Customer Details.txt",true);
			writer.write("\n");
			writer.write(accNo+"\t\t"+accountHolderName+"\t\t"+pinNo+"\t\t"+accountBalance);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addCustomerDetailsFromFile() throws Exception{
		File details=new File("C:\\Users\\nares\\OneDrive\\Documents\\Customer Details.txt");
		Scanner io=new Scanner(details);
		io.nextLine();
		while(io.hasNext()) {
			io.nextInt();
			accNo++;
			CustomerDetailsList.put(accNo,new CustomerDetails(accNo,io.next(),io.nextInt(),io.nextDouble()));
		}
		
	}
	public void updateBalance(int accNo,int pinNo,Double amountWithDrawned) {
		if(CustomerDetailsList.get(accNo).getPinNo()==pinNo) {
			CustomerDetailsList.get(accNo).setBalance(amountWithDrawned);
		}
	}
	
	public void displayDetails() {
		System.out.println("Acc No\tAccount Holder\tPin Number\tAccount Balance");
		for(int key:CustomerDetailsList.keySet()) {
			System.out.println(key+"\t\t"+CustomerDetailsList.get(key).getAccountHolderName()+
					"\t\t"+CustomerDetailsList.get(key).getPinNo()+"\t\t"+
					CustomerDetailsList.get(key).getAccountBalance());
		}
	}
	public boolean isAccountAvailable(int accNo) {
		if(CustomerDetailsList.containsKey(accNo)) {
			return true;
		}
		return false;
	}
	public int verifyAccount(int accNo,int pinNo) {
		if(CustomerDetailsList.containsKey(accNo)) {
			if(CustomerDetailsList.get(accNo).getPinNo()==pinNo) {
				accountDetailsVerified=true;
				return 1;
				
			}
			else return -1;
		}
		else return -2;
	}
	public double getBalance(int accNo,int pinNo) {
		if(CustomerDetailsList.get(accNo).getPinNo()==pinNo) {
			return CustomerDetailsList.get(accNo).getAccountBalance();
		}		
		return(-1);
	}
	public void transferMoney(int senderAccNo,int senderPinNo,int receiverAccNo,double amountToTransfer) {
		boolean amountTransfered=false;
		if(CustomerDetailsList.get(senderAccNo).getPinNo()==senderPinNo) {
			CustomerDetailsList.get(senderAccNo).setBalance(-amountToTransfer);
			amountTransfered=true;
		}
		if(amountTransfered) {
			CustomerDetailsList.get(receiverAccNo).setBalance(amountToTransfer);
			System.out.println("Transation Successfull!!!");
			return;
		}
		System.out.println("Transation Failed!!!");
		
	}
}
