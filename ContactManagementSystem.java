import java.util.List;
import java.util.Scanner;

/**
 * Console-based Contact Management System.
 *
 * Supports adding, viewing, editing, and deleting contacts, with
 * File I/O (via ContactManager) so data persists between runs.
 */
public class ContactManagementSystem {

    private final ContactManager manager = new ContactManager();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new ContactManagementSystem().run();
    }

    private void run() {
        System.out.println("=== Contact Management System ===");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addContact();
                case "2" -> viewContacts();
                case "3" -> editContact();
                case "4" -> deleteContact();
                case "5" -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n1. Add Contact");
        System.out.println("2. View Contacts");
        System.out.println("3. Edit Contact");
        System.out.println("4. Delete Contact");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private void addContact() {
        System.out.print("\nEnter name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        if (manager.findByName(name) != null) {
            System.out.println("A contact with this name already exists.");
            return;
        }

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Enter email address: ");
        String email = scanner.nextLine().trim();

        manager.addContact(new Contact(name, phone, email));
        System.out.println("Contact added successfully.");
    }

    private void viewContacts() {
        List<Contact> contacts = manager.getAllContacts();

        if (contacts.isEmpty()) {
            System.out.println("\nNo contacts saved yet.");
            return;
        }

        System.out.println("\n--- Contact List ---");
        System.out.printf("%-20s | %-15s | %s%n", "Name", "Phone", "Email");
        for (Contact c : contacts) {
            System.out.println(c);
        }
    }

    private void editContact() {
        System.out.print("\nEnter the name of the contact to edit: ");
        String name = scanner.nextLine().trim();

        Contact contact = manager.findByName(name);
        if (contact == null) {
            System.out.println("No contact found with that name.");
            return;
        }

        System.out.println("Leave a field blank to keep its current value.");

        System.out.print("New phone [" + contact.getPhone() + "]: ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) {
            contact.setPhone(phone);
        }

        System.out.print("New email [" + contact.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) {
            contact.setEmail(email);
        }

        manager.saveToFile();
        System.out.println("Contact updated successfully.");
    }

    private void deleteContact() {
        System.out.print("\nEnter the name of the contact to delete: ");
        String name = scanner.nextLine().trim();

        boolean deleted = manager.deleteContact(name);
        if (deleted) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("No contact found with that name.");
        }
    }
}
