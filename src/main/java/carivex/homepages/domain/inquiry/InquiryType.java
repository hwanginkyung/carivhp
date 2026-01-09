package carivex.homepages.domain.inquiry;

public enum InquiryType {
    OPTION1("옵션1"),
    OPTION2("옵션2"),
    OPTION3("옵션3");

    private final String label;

    InquiryType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
