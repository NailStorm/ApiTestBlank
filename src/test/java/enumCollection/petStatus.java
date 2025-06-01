package enumCollection;

public enum petStatus {
    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    private String status;
    petStatus(String status) { }
    public String getTitle() {
        return status;
    }
}
