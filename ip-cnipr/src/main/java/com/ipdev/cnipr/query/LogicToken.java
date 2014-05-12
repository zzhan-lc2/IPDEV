package com.ipdev.cnipr.query;

public enum LogicToken {
    AND("and"),
    OR("or"),
    NOT("not"),
    TO("to");

    String name;

    private LogicToken(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static final String SPACE = " ";
    public String toOp() {
        return new StringBuilder()
            .append(SPACE).append(name).append(SPACE)
            .toString();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
