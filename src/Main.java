import java.util.Scanner;
import java.time.LocalDateTime;

class Transaction {
    int transactionID;
    LocalDateTime transactionDate;
    String transactionType;
    int amount;

    Transaction(int transactionID, String transactionType, int amount) {
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.amount = amount;
        transactionDate = LocalDateTime.now();
    }

    public int getTransactionID() {
        return transactionID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public int getTransactionAmount() {
        return amount;
    }
}

class Account {
    String username;
    int accountNumber;
    String accountType;
    int balance;
    Transaction[] transactions;
    int transactionID;
    int transactionIdx;
    LocalDateTime interestDate;

    Account(String username, int accountNumber, String accountType, int initial) {
        this.username = username;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        balance = initial;
        transactions = new Transaction[100];
        transactionIdx = 0;
        transactionID = 3000;
        interestDate = LocalDateTime.now().plusMonths(1);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getAccountBalance() {
        return balance;
    }

    public void depositMoney(int money) {
        balance += money;
    }

    public boolean withdrawMoney(int money) {
        if (balance < money) {
            return false;
        }

        else {
            balance -= money;
            return true;
        }
    }

    public void addTransaction(String transactionType, int amount) {
        transactions[transactionIdx++] = new Transaction(++transactionID, transactionType, amount);
    }

    public int getTransactionIdx() {
        return transactionIdx;
    }

    public Transaction getTransaction(int i) {
        return transactions[i];
    }

    public boolean addInterest() {
        if (LocalDateTime.now().isAfter(interestDate)) {
            balance += (int) (balance * 0.6f);
            interestDate = LocalDateTime.now().plusMonths(1);
            return true;
        }

        else {
            return false;
        }
    }

    public String getAccountType() {
        return accountType;
    }
}

class User {
    String username;
    String password;
    int accountsIdx;
    int accountNumber;
    Account[] accounts;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        accountsIdx = 0;
        accountNumber = 1000;
        accounts = new Account[100];
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getAccountsIdx() {
        return accountsIdx;
    }

    public void createAccount(String accountType, int initial) {
        accounts[accountsIdx++] = new Account(username, ++accountNumber, accountType, initial);
    }

    public Account getAccount(int i) {
        return accounts[i];
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        User[] users = new User[100];
        int userIdx = 0;
        boolean loggedIn = false;
        User currentUser = null;
        Account currentAccount = null;
        boolean accountSelected = false;

        System.out.println("Welcome to Banking Portal!");
        while (true) {
            boolean exit = false;

            while (!loggedIn) {
                System.out.println("\nPlease login or signup");
                System.out.println("\t1. Login");
                System.out.println("\t2. Signup");
                System.out.println("\t3. Exit");

                System.out.print("Enter: ");
                int input = s.nextInt();
                s.nextLine();

                if (input == 1) {
                    System.out.println("Please enter your username and password");
                    System.out.print("username: ");
                    String username = s.nextLine();
                    System.out.print("password: ");
                    String password = s.nextLine();

                    for (int i = 0; i < userIdx; i++) {
                        if (username.equals(users[i].getUsername()) && password.equals(users[i].getPassword())) {
                            loggedIn = true;
                            currentUser = users[i];
                            break;
                        }
                    }

                    if (loggedIn) {
                        break;
                    }

                    else {
                        System.out.println("Invalid credentials, please try again");
                        continue;
                    }
                }

                if (input == 2) {
                    while (true) {
                        System.out.println("Please enter new username and password");
                        System.out.print("username: ");
                        String username = s.nextLine();
                        System.out.print("password: ");
                        String password = s.nextLine();

                        boolean usernameValid = true;
                        for (int i = 0; i < userIdx; i++) {
                            if (username.equals(users[i].getUsername())) {
                                usernameValid = false;
                                break;
                            }
                        }

                        if (!usernameValid) {
                            System.out.println("Username already exists");
                            continue;
                        }

                        users[userIdx++] = new User(username, password);
                        System.out.println("New user created, please sign up");
                        break;
                    }
                }

                else if (input == 3) {
                    exit = true;
                    break;
                }

                else {
                    System.out.println("Invalid input! Please try again");
                }
            }

            if (exit) {
                break;
            }

            while (true) {
                System.out.println("\nCurrent user: " + currentUser.getUsername());
                System.out.println("Please select or create an account");
                System.out.println("\t1. Select account");
                System.out.println("\t2. Create checking account");
                System.out.println("\t3. Create savings account");
                System.out.println("\t4. Logout");
                System.out.print("Enter: ");

                int input = s.nextInt();
                s.nextLine();

                if (input == 1) {
                    if (currentUser.getAccountsIdx() == 0) {
                        System.out.println("No accounts exist, please create one");
                        continue;
                    }

                    System.out.println("Select account");
                    while (true) {
                        for (int i = 0; i < currentUser.getAccountsIdx(); i++) {
                            Account account = currentUser.getAccount(i);
                            System.out.println(i + ". Account number: " + account.getAccountNumber());
                        }

                        System.out.print("Enter: ");
                        int accountInput = s.nextInt();

                        if (accountInput > currentUser.getAccountsIdx()) {
                            System.out.println("Invalid input given");
                        }

                        else {
                            currentAccount = currentUser.getAccount(accountInput);
                            accountSelected = true;
                            System.out.println("Account selected");
                            break;
                        }
                    }

                    break;
                }

                else if (input == 2 || input == 3) {
                    System.out.println("Creating new account");
                    System.out.print("Enter initial deposit: ");
                    int initial = s.nextInt();

                    if (input == 2) {
                        currentUser.createAccount("checking", initial);
                    }

                    else {
                        currentUser.createAccount("savings", initial);
                    }

                    System.out.println("New account has been created!");
                }

                else if (input == 4) {
                    currentUser = null;
                    loggedIn = false;
                    break;
                }

                else {
                    System.out.println("Invalid input, please try again");
                }
            }

            while (accountSelected) {
                System.out.println("\nCurrent account: " + currentAccount.getAccountNumber());
                System.out.println("\t1. Display account statement");
                System.out.println("\t2. Display account balance");
                System.out.println("\t3. Deposit money");
                System.out.println("\t4. Withdraw money");
                System.out.println("\t5. Add interest amount");
                System.out.println("\t6. Select different account");
                System.out.print("Enter: ");

                int input = s.nextInt();
                s.nextLine();

                if (input == 1) {
                    if (currentAccount.getTransactionIdx() == 0) {
                        System.out.println("No transactions to display");
                        continue;
                    }

                    System.out.println("Account statement");
                    System.out.println("ID\t\t\tType\t\t\tAmount\t\t\tDate");
                    for (int i = 0; i < currentAccount.getTransactionIdx(); i++) {
                        Transaction transaction = currentAccount.getTransaction(i);
                        System.out.print(transaction.getTransactionID() + "\t\t");
                        System.out.print(transaction.getTransactionType() + "\t\t\t");
                        System.out.print(transaction.getTransactionAmount() + "\t\t\t");
                        System.out.println(transaction.getTransactionDate());
                    }
                }

                else if (input == 2) {
                    System.out.println("Current account balance: " + currentAccount.getAccountBalance());
                }

                else if (input == 3) {
                    System.out.print("Enter amount to deposit: ");
                    int deposit = s.nextInt();
                    currentAccount.depositMoney(deposit);
                    currentAccount.addTransaction("Deposit", deposit);
                    System.out.println("Deposit successful Balance: " + currentAccount.getAccountBalance());
                }

                else if (input == 4) {
                    System.out.print("Enter money to withdraw: ");
                    int withdraw = s.nextInt();
                    boolean success = currentAccount.withdrawMoney(withdraw);
                    if (success) {
                        currentAccount.addTransaction("Withdraw", withdraw);
                        System.out.println("Withdraw money successful. Balance: " + currentAccount.getAccountBalance());
                    }

                    else {
                        System.out.println("Not enough money in account to withdraw!");
                    }
                }

                else if (input == 5) {
                    if (currentAccount.getAccountType().equals("savings")) {
                        boolean status = currentAccount.addInterest();

                        if (status) {
                            System.out.println("Interest payment has successfully been added");
                        }

                        else {
                            System.out.println("Interest payment can only be added after a month");
                        }
                    }

                    else {
                        System.out.println("Interest payments can only be added to savings accounts");
                    }
                }

                else if (input == 6) {
                    currentAccount = null;
                    accountSelected = false;
                    break;
                }

                else {
                    System.out.println("Invalid input given");
                }
            }
        }
    }
}