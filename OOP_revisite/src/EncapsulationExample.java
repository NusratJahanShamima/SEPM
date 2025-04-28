class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if(amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if(amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public double getBalance() {
        return balance;
    }
}

public class EncapsulationExample {
    public static void main(String[] args) {
        BankAccount myAccount = new BankAccount();
        myAccount.deposit(1000);
        myAccount.withdraw(500);
        System.out.println("Balance: " + myAccount.getBalance());
    }
}
