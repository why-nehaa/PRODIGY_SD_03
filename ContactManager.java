import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class ContactManager {

    private static final String CONTACTS_FILE = "contacts.txt";
    private final List<Contact> contacts = new ArrayList<>();

    public ContactManager() {
        loadFromFile();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveToFile();
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }
    public Contact findByName(String name) {
        for (Contact c : contacts) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public boolean deleteContact(String name) {
        Contact contact = findByName(name);
        if (contact == null) {
            return false;
        }
        contacts.remove(contact);
        saveToFile();
        return true;
    }
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONTACTS_FILE))) {
            for (Contact c : contacts) {
                writer.write(c.toCsv());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(CONTACTS_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    contacts.add(Contact.fromCsv(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }
}
