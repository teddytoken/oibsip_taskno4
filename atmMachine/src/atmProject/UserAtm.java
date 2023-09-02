package atmProject;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UserAtm {
	public static String name;
	public static int pin_no;
	
	
	public static void main(String args[]) {
		try {
			Connection conn = Db.provideConnection();
			Statement stm = conn.createStatement();
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter your name : ");
			name = sc.next();
			System.out.print("Enter your pin number :");
			pin_no = sc.nextInt();
			
			PreparedStatement st = conn.prepareStatement("select name, pin_number from user_records where name=? and pin_number=?");
			st.setString(1, name);
			st.setInt(2, pin_no);
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				System.out.println("You have successfully logged in");
				operations();
			}
			else {
				System.out.println("wrong name or pin number");
				System.out.println("\n");
				System.out.println("please enter your login details -");
				System.out.println("Enter your name :");
				String nm = sc.next();
				System.out.println("Enter your pin number :");
				int pin = sc.nextInt();
				
				try {
					PreparedStatement pst = conn.prepareStatement("insert into user_records(name, pin_number) values (?,?)");
					
					pst.setString(1, name);
					pst.setInt(2, pin_no);
					
					int data = pst.executeUpdate();
					if(data == 1) {
						System.out.println("Data inserted successfully");
						operations();
					}else {
						System.out.println("Data insertion failed");
					}
			   }catch(Exception e) {
				e.printStackTrace();
			   }
			}
		}catch(Exception e) {
			e.printStackTrace();
	}
}

	public static void operations() {
		try {
			Connection conn = Db.provideConnection();
			Statement stm = conn.createStatement();
			
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter your pin number :");
			pin_no =sc.nextInt(); 
			
			ResultSet rs = stm.executeQuery("select * from user_records where pin_number ="+pin_no);
			int count = 0;
			int balance = 0;
			while(rs.next()) {
				balance = rs.getInt(4);
				count++;
			}
			
			int choice;
			int amount = 0;
			int withdraw_amt = 0;
			BigInteger acc_no;
			int transfer_amt = 0;
			
			if(count>0) {
				while(true) {
					System.out.println("Select from the following operations: ");
					System.out.println(" Type 1 - View Balance ");
					System.out.println(" Type 2 - Deposit Funds ");
					System.out.println(" Type 3 - Withdraw Funds ");
					System.out.println(" Type 4 - Transfer Funds ");
					System.out.println(" Type 5 - Transactions History ");
					System.out.println(" Type 6 - Exit ");
					System.out.println("Choice: ");
					
					choice = sc.nextInt();
					switch(choice) {
						case 1:
							System.out.println("Your total balance is : "+balance);
							break;
						case 2:
							System.out.println("Enter the amount you want to deposit :");
							amount = sc.nextInt();
							balance = balance + amount;
							int update_bal = stm.executeUpdate("update user_records set total_balance = "+balance+" where pin_number= "+pin_no);
							System.out.println("Successfully added to your account");
							System.out.println("Current balance is : "+balance);
							break;
						case 3:
							System.out.println("Enter the amount you want to withdraw :");
							withdraw_amt = sc.nextInt();
							if(withdraw_amt > balance) {
								System.out.println("Insufficient Balance");
							}else {
								balance = balance - withdraw_amt;
								int balMinus = stm.executeUpdate("update user_records set total_balance = "+balance+" where pin_number= "+pin_no);
								System.out.println("Withdrawn Successfully!!!!");
								System.out.println("Current balance is : "+balance);
							}
							break;
						case 4:
							System.out.println("Enter the account number in which you want to transfer : ");
							acc_no  = sc.nextBigInteger();
							System.out.println("Enter the amount to be transferred : ");
							transfer_amt = sc.nextInt();
							if(transfer_amt > balance) {
								System.out.println("Insufficient Balance");
							}else {
								balance = balance - transfer_amt;
								int balMinus = stm.executeUpdate("update user_records set total_balance = "+balance+" where pin_number= "+pin_no);
								System.out.println("Transaction Successfull!!!!");
								System.out.println("Current balance is : "+balance);
							}
							break;
						case 5:
							System.out.println("Transaction History : ");
							System.out.println("CURRENT BALANCE\t\tAMOUNT ADDED\t\tAMOUNT WITHDRAWN\tAMOUNT TRANSFERRED");
							System.out.println("-------------------+---------------------+----------------------+------------------------------");
							System.out.println(balance+"\t\t\t"+amount+"\t\t\t"+withdraw_amt+"\t\t\t"+transfer_amt);
							break;
					}
					if(choice == 6) {
						return;
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
	}
}
}


