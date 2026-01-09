package carivex.homepages.domain.inquiry;

public enum InquiryStatus {
    NEW("신규"),
    IN_PROGRESS("처리중"),
    DONE("완료");

    private final String label;

    InquiryStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
