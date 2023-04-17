package friend.repository;

public class MemberPagerAndSorter {

    int limit;
    int offset;
    String sortingColumn;
    String sortingOrder;

    public MemberPagerAndSorter() {

    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSortingColumn() {
        return sortingColumn;
    }

    public void setSortingColumn(String sortingColumn) {
        this.sortingColumn = sortingColumn;
    }

    public String getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(String sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public String asQueryClause() {
        String clauseTempA = " ORDER BY %s %s LIMIT %d OFFSET %d;";
        String clauseTempB = " ORDER BY CONVERT(SUBSTR(%s,1,1) USING BIG5) %s, BINARY SUBSTR(%s,1,1) ASC LIMIT %d OFFSET %d;";
        String clauseFinal = null;

        switch (this.sortingColumn) {
            case "member_account" ->
                    clauseFinal = String.format(clauseTempA, this.sortingColumn, this.sortingOrder, this.limit, this.offset);
            case "member_name", "member_username" ->
                    clauseFinal = String.format(clauseTempB, this.sortingColumn, this.sortingOrder, this.sortingColumn, this.limit, this.offset);
            default ->
                    clauseFinal = String.format(clauseTempA, "member_id", this.sortingOrder, this.limit, this.offset);
        }
        return clauseFinal;
    }
}
