package project.honey.business.form.analyze;

public enum VatType {

    Y("부가세포함"), N("부가세미포함");

    private final String description;

    VatType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
