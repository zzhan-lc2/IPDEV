package com.ipdev.cnipr.dao.patent;

import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ipdev.cnipr.entity.method.Sf1Request;
import com.ipdev.cnipr.entity.method.Sf1Response;
import com.ipdev.cnipr.entity.method.Sf2Request;
import com.ipdev.cnipr.entity.method.Sf2Response;
import com.ipdev.cnipr.entity.patent.CNIPRDBS;
import com.ipdev.cnipr.query.AttrField;
import com.ipdev.common.dao.patent.PatentSearchDao;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.query.OrderExp;
import com.ipdev.common.query.OrderExp.Direction;
import com.ipdev.common.query.Query;
import com.ipdev.common.query.QueryExp;

public class PatentSearchByCNIPRDao extends CniprAPIDao implements PatentSearchDao {
    static final Log LOG = LogFactory.getLog(PatentSearchByCNIPRDao.class);

    static int MAX_RETURN_PATENTS = 5000; // we do not want to overwhelm the memory resource

    public PatentSearchByCNIPRDao() {
    }

    public List<Patent> findPatentsByQuery(Query query, Set<String> sourceDbs, OrderExp orderExp) {
        Preconditions.checkNotNull(query, "query cannot be null");
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(query.getExpressions()),
            "Expressions in Query cannot be empty");

        List<Patent> patents = Lists.newArrayList();

        Sf1Request request = new Sf1Request();
        if (null == orderExp) {
            request.setOrderBy("申请日", false); // default
        } else {
            request.setOrderBy(orderExp.getOrderBy(), orderExp.equals(Direction.ASC) ? true : false);
        }

        request.setExpression(queryToExpression(query));
        if (CollectionUtils.isEmpty(sourceDbs)) {
            sourceDbs = CNIPRDBS.getNames();
        }
        for (String db : sourceDbs) {
            if (CNIPRDBS.isValid(db)) {
                request.addDb(db);
            } else {
                LOG.error("The source DB string is invalid: " + db);
            }
        }

        int step = 50;
        int count = 0;
        while (count < MAX_RETURN_PATENTS) {
            request.setFrom(count);
            request.setTo(count + step);
            Sf1Response response = this.absSearchByExpression(request);

            int res_size = response.getResults() == null ? 0 : response.getResults().size();
            if (res_size > 0) {
                patents.addAll(response.getResults());

                if (count == 0 && response.getTotal() > MAX_RETURN_PATENTS) {
                    LOG.warn("The total result of this query (" + response.getTotal()
                        + ") is more than the maximum allowed return number: " + MAX_RETURN_PATENTS);
                }
            }
            if (res_size < step) {
                // no more data
                break;
            }
            count += res_size;
        }

        return patents;
    }

    static String queryToExpression(Query query) {
        StringBuilder sb = new StringBuilder();
        for (QueryExp exp : query.getExpressions()) {
            sb.append(exp.getExpKey()).append("=")
                .append("(")
                .append(exp.getExpValue())
                .append(")");
            if (exp.getOperator() != null) {
                sb.append(" ")
                    .append(StringUtils.lowerCase(exp.getOperator().name()))
                    .append(" ");
            }
        }
        return sb.toString();
    }

    public Patent findPatentById(String pid) {
        Preconditions.checkNotNull(pid, "pid cannot be null");

        Sf2Request request = new Sf2Request();
        request.setPid(pid);
        Sf2Response response = detailSearchByPid(request);
        if (response != null) {
            List<Patent> patents = response.getResults(); // even though the function prototype is "list", we can only
                                                          // have maximum 1 result
            if (patents.size() > 0) {
                return patents.get(0);
            }
        }
        return null;
    }

    public List<Patent> findPatentsByApplicant(String applicantName, Set<String> sourceDbs) {
        Preconditions.checkNotNull(applicantName, "applicantName cannot be null");

        Query query = new Query();
        QueryExp exp = new QueryExp();
        exp.setExpKey(AttrField.APPLICANT.getName());
        exp.setExpValue(applicantName);
        query.addExpression(exp);
        return this.findPatentsByQuery(query, sourceDbs, null);
    }
}
