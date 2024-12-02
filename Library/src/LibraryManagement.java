import java.util.Scanner;

public class LibraryManagement {
    private Library library = new Library();

    public static void main(String[] args) {
        new LibraryManagement().run();
    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("===========================");
            System.out.println("Library Management System");
            System.out.println("1. Add Member");
            System.out.println("2. Add Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View Borrowed Books");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.println("===========================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Add Member
                    System.out.print("Enter member ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter member name: ");
                    String name = scanner.next();
                    scanner.nextLine(); // Consume the newline

                    Member newMember = new Member(id, name);
                    if (library.addMember(newMember)) {
                        System.out.println("Member added successfully.");
                    } else {
                        System.out.println("Failed to add member. Duplicate ID.");
                    }
                    break;

                case 2:
                    // Add Book
                    System.out.print("Enter book ID: ");
                    id = scanner.nextInt();
                    System.out.print("Enter book title: ");
                    String title = scanner.next();
                    scanner.nextLine(); // Consume the newline

                    Book newBook = new Book(id, title);
                    if (library.addBook(newBook)) {
                        System.out.println("Book added to library successfully.");
                    } else {
                        System.out.println("Failed to add book. Duplicate ID.");
                    }
                    break;

                case 3:
                    // Borrow Book
                    System.out.println("\n--- Available Members ---");
                    for (Member member : library.getMembers()) {
                        System.out.println(member.getId() + ". " + member.getName());
                    }

                    System.out.print("Enter member ID: ");
                    int memberId = scanner.nextInt();

                    System.out.println("\n--- Available Books ---");
                    for (Book book : library.getBooks()) {
                        if (book.isAvailable())
                            System.out.println(book.getId() + ". " + book.getTitle());
                    }

                    System.out.print("Enter book ID: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    Member member = library.findMemberById(memberId);
                    Book book = library.findBookById(bookId);

                    if (member != null && book != null) {
                        Transaction.getTransaction().borrowBook(book, member);
                    } else {
                        System.out.println("Invalid member or book ID.");
                    }
                    break;

                case 4:
                    // Return Book
                    System.out.print("Enter member ID: ");
                    memberId = scanner.nextInt();

                    System.out.print("Enter book ID: ");
                    bookId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    member = library.findMemberById(memberId);
                    book = library.findBookById(bookId);

                    if (member != null && book != null) {
                        Transaction.getTransaction().returnBook(book, member);
                    } else {
                        System.out.println("Invalid member or book ID.");
                    }
                    break;

                case 5:
                    // View Borrowed Books
                    System.out.print("Enter member ID: ");
                    memberId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline

                    Member specificMember = library.findMemberById(memberId);

                    if (specificMember != null) {
                        System.out.println("Books borrowed by " + specificMember.getName() + ":");
                        for (Book bk : specificMember.getBorrowedBooks()) {
                            System.out.println(" - " + bk.getTitle());
                        }
                    } else {
                        System.out.println("Invalid member ID.");
                    }
                    break;

                case 6:
                    // View Transaction History
                    Transaction.getTransaction().displayTransactionHistory();
                    break;

                case 7:
                    // Exit
                    System.out.println("Exiting. Good Bye..");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
