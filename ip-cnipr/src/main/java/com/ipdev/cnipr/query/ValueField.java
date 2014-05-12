package com.ipdev.cnipr.query;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.api.client.repackaged.com.google.common.base.Preconditions;

public class ValueField {
    Object value;
    LogicToken logic;
    ValueField next;

    public ValueField(Object value) {
        this.setValue(value);
    }

    public Object getValue() {
        return value;
    }

    public ValueField setValue(Object value) {
        this.value = value;
        return this;
    }

    public LogicToken getLogic() {
        return logic;
    }

    public ValueField getNext() {
        return next;
    }

    public ValueField setNext(LogicToken logic, ValueField next) {
        this.logic = logic;
        this.next = next;
        return this;
    }

    public String toExpression() {
        Preconditions.checkState(value != null, "value cannot be null");

        StringBuilder sb = new StringBuilder()
            .append(ValueStringHelper.toString(value));

        if (this.next != null && this.logic != null) {
            sb.append(logic.toOp())
                .append(next.toExpression());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
