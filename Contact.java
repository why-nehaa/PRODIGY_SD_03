/**
 * Represents a single contact record.
 */
public class Contact {

    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Serializes this contact to a single CSV line for file storage.
     * Format: name,phone,email
     */
    public String toCsv() {
        return name + "," + phone + "," + email;
    }

    public static Contact fromCsv(String line) {
        String[] parts = line.split(",", -1); // -1 keeps trailing empty fields
        String name = parts[0].trim();
        String phone = parts.length > 1 ? parts[1].trim() : "";
        String email = parts.length > 2 ? parts[2].trim() : "";
        return new Contact(name, phone, email);
    }

    @Override
    public String toString() {
        return String.format("%-20s | %-15s | %s", name, phone, email);
    }
}
