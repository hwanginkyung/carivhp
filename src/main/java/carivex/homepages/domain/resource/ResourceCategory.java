package carivex.homepages.domain.resource;

public enum ResourceCategory {
    EXPORT_STATS("수출 통계"),
    CUSTOMS_STANDARD("통관 기준"),
    LAW_OFFICIAL("법령/공문"),
    FORMS("서식");

    private final String label;

    ResourceCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
