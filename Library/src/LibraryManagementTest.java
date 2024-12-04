import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class LibraryManagementTest {

    private Book validBook;
    private Member member;
    private Transaction transaction;

    @Before
    public void setUp() throws Exception {
        // Create a valid book for tests
        validBook = new Book(100, "Valid Book");
        member = new Member(1, "John Doe");
        transaction = Transaction.getTransaction();
    }

    // Test Book ID Validation
    @Test
    public void testBookIdValidation() {
        try {
            // Valid ID
            Book valid = new Book(150, "Valid Book");
            assertNotNull(valid);

            // Invalid ID
            try {
                new Book(1000, "Invalid Book");
                fail("Expected an exception for an invalid book ID");
            } catch (Exception e) {
                assertEquals("Invalid book ID. Must be between 100 and 999.", e.getMessage());
            }
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    // Test Borrow and Return functionality
    @Test
    public void testBorrowAndReturn() {
        assertTrue(transaction.borrowBook(validBook, member));
        assertFalse(validBook.isAvailable());

        // Borrow the same book again (should fail)
        assertFalse(transaction.borrowBook(validBook, member));

        // Return the book
        transaction.returnBook(validBook, member);
        assertTrue(validBook.isAvailable());

        // Return the same book again (should fail)
        transaction.returnBook(validBook, member);
    }

    // Test Singleton Transaction
    @Test
    public void testSingletonTransaction() {
        try {
            Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor();
            constructor.setAccessible(true);

            // Test if constructor is private
            assertEquals(Modifier.PRIVATE, constructor.getModifiers());

            // Test Singleton behavior
            Transaction transaction1 = Transaction.getTransaction();
            Transaction transaction2 = Transaction.getTransaction();
            assertSame(transaction1, transaction2);  // Same instance should be returned

        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }
}
