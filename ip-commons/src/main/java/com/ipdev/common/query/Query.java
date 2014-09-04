package com.ipdev.common.query;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Lists;

public class Query implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private Date creationDate;
    private String creator;
    private QueryType queryType;
    private List<QueryExp> expressions = Lists.newArrayList();
    private Boolean isDeprecated;
    private Date lastUpdatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public List<QueryExp> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<QueryExp> expressions) {
        this.expressions = expressions;
    }

    public void addExpression(QueryExp exp) {
        if (exp == null) {
            return;
        }
        exp.setParent(this);
        this.expressions.add(exp);
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Boolean getIsDeprecated() {
        return isDeprecated;
    }

    public void setIsDeprecated(Boolean isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Query))
            return false;

        Query other = (Query) obj;
        if (StringUtils.isNotBlank(id)) {
            return id.equals(other.id);
        } else {
            return Objects.deepEquals(this, other);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
            ToStringStyle.MULTI_LINE_STYLE);
    }
}
