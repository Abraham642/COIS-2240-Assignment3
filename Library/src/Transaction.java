import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Transaction {
    // Singleton instance of Transaction
    private static Transaction instance;

    private Transaction() {
        // Private constructor to prevent instantiation
    }

    public static Transaction getTransaction() {
        if (instance == null) {
            instance = new Transaction();
        }
        return instance;
    }

    // Perform the borrowing of a book
    public boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book);
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            System.out.println(transactionDetails);
            saveTransaction(transactionDetails);
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    // Perform the returning of a book
    public void returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
            String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            System.out.println(transactionDetails);
            saveTransaction(transactionDetails);
        } else {
            System.out.println("This book was not borrowed by the member.");
        }
    }

    // Get the current date and time in a readable format
    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    // Save the transaction to a file
    private void saveTransaction(String transactionDetails) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            writer.write(transactionDetails);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the transaction: " + e.getMessage());
        }
    }

    // Display all past transactions saved in transactions.txt
    public void displayTransactionHistory() {
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the transaction history: " + e.getMessage());
        }
    }

    // Ensure that the constructor is private
    public static void testSingleton() throws Exception {
        Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            Transaction instance = constructor.newInstance();
            System.out.println("Singleton instance creation failed.");
        } catch (Exception e) {
            System.out.println("Cannot instantiate Singleton: " + e.getMessage());
        }

        // Ensure Singleton behavior
        Transaction transaction1 = Transaction.getTransaction();
        Transaction transaction2 = Transaction.getTransaction();
        System.out.println("Singleton validation: " + (transaction1 == transaction2));

        // Check if constructor is private
        assert constructor.getModifiers() == Modifier.PRIVATE;
    }
}
