package carivex.homepages.domain.page;

public enum PageType {
    INTRO("조합 소개"),
    BUSINESS("사업 안내");

    private final String label;

    PageType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
