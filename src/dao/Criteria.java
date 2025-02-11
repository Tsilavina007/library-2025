package dao;

public class Criteria {
    private String column;
    private Object value;

    public Criteria(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    public Criteria(String column) {
        this.column = column;
    }

	public Object getValue() {
        return value;
    }

    public String getColumn() {
        return column;
    }
}
