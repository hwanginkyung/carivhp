package carivex.homepages.domain.notice;

public enum NoticeCategory {
    PRESS("언론 보도"),
    UNION("조합 공지");

    private final String label;

    NoticeCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
