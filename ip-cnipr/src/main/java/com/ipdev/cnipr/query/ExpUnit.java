package com.ipdev.cnipr.query;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.api.client.repackaged.com.google.common.base.Preconditions;

public class ExpUnit {
    AttrField attribute;
    ValueField value;
    LogicToken logic;
    ExpUnit next;

    private ExpUnit() {
    }

    public AttrField getAttribute() {
        return attribute;
    }

    public ExpUnit setAttribute(AttrField attribute) {
        this.attribute = attribute;
        return this;
    }

    public ValueField getValue() {
        return value;
    }

    public ExpUnit setValue(ValueField value) {
        this.value = value;
        return this;
    }

    public LogicToken getLogic() {
        return logic;
    }

    public ExpUnit getNext() {
        return next;
    }

    public ExpUnit setNext(LogicToken logic, ExpUnit next) {
        this.logic = logic;
        this.next = next;
        return this;
    }

    public String toExpression() {
        Preconditions.checkState(attribute != null, "attribute field cannot be null");
        Preconditions.checkState(value != null, "value cannot be null");

        StringBuilder sb = new StringBuilder()
            .append(attribute).append("=")
            .append("(").append(this.value.toExpression()).append(")");

        if (this.next != null && this.logic != null) {
            sb.append(logic.toOp())
                .append(this.next.toExpression());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    // ///

    public static ExpUnit withAttribute(AttrField attribute) {
        ExpUnit exp = new ExpUnit();
        exp.setAttribute(attribute);
        return exp;
    }
}
