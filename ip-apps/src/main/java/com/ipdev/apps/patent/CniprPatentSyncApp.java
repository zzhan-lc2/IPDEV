package com.ipdev.apps.patent;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.ipdev.apps.IpAppBase;
import com.ipdev.common.dao.patent.RequestControlParams;
import com.ipdev.common.dao.query.QueryDao;
import com.ipdev.common.manager.PatentSyncManager;
import com.ipdev.common.query.Query;

public class CniprPatentSyncApp extends IpAppBase {
    @Nonnull
    PatentSyncManager syncManager;
    QueryDao queryDao;

    int pageSize = 200;

    final RequestControlParams controlParams = new RequestControlParams();

    public void setQueryDao(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public void setForceUpdate(boolean forceUpdate) {
        controlParams.withForceUpdate(forceUpdate);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPatentSyncManager(PatentSyncManager syncManager) {
        this.syncManager = syncManager;
    }

    public void setFileBaseFolder(String baseFolder) {
        controlParams.withFileBaseDir(baseFolder);
        controlParams.withEnableFileStorage(true);
    }

    public void setSourceDbs(String sourceDbs) {
        Preconditions.checkNotNull(sourceDbs, "sourceDbs cannot be null");

        for (String db : StringUtils.split(sourceDbs, ",")) {
            controlParams.addSourceDb(db);
        }
    }

    public void addSourceDb(String sourceDb) {
        Preconditions.checkNotNull(sourceDb, "sourceDb cannot be null");

        controlParams.addSourceDb(sourceDb.toUpperCase());
    }

    int syncPatentsByUser(String user) {
        int numbChanged = 0;

        // 1. find number of queries belonged to this user
        List<Query> queries = queryDao.findByCreator(user);
        if (CollectionUtils.isEmpty(queries)) {
            LOG.info("No query found for this user");
            return 0;
        }

        // 2. for each query, sync the db with CNIPR
        for (Query query : queries) {
            numbChanged += this.syncPatentsByQuery(query);
        }
        return numbChanged;
    }

    int syncPatentsByQuery(Query query) {
        int totalCounts = syncManager.getTotalPatentsCountByQuery(query, controlParams);

        int counts = 0;
        for (int block = 0; block < (totalCounts / pageSize + 1); block++) {
            controlParams.withFromIndex(block * pageSize);
            controlParams.withToIndex((block + 1) * pageSize);
            counts += syncManager.syncPatentsByQuery(query, controlParams);
        }
        LOG.info("Updated patent counts = {} for Query = {}", counts, query);
        return counts;
    }

    public static void main(String[] args) {
        IpAppBase.configure(args, "CniprPatentSync");

        CommandLineParser parser = new GnuParser();
        Options options = createOptions();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);

            CniprPatentSyncApp app = IpAppBase.getApplication(CniprPatentSyncApp.class);

            if (cmd.hasOption("b")) {
                app.setFileBaseFolder(cmd.getOptionValue("b"));
            }
            if (cmd.hasOption("d")) {
                app.setSourceDbs(cmd.getOptionValue("d"));
            }
            if (cmd.hasOption("p")) {
                app.setPageSize(Integer.parseInt(cmd.getOptionValue("p")));
            }
            if (!cmd.hasOption("u")) {
                printUsage("CniprPatentSyncApp", options, System.out);
            }
            app.syncPatentsByUser(cmd.getOptionValue("u"));
        } catch (ParseException e) {
            System.err.println(
                "Encountered exception while parsing using GnuParser:\n"
                    + e.getMessage());
            System.exit(1);
        }

    }

    static Options createOptions() {
        Options options = new Options();

        options.addOption("b", true, "(optional) base directory for JSON output file");
        options.addOption("u", true, "the user name (i.e the name for the IP applicant)");
        options.addOption("d", true, "(optional) the source databases (separated by [,])");
        options.addOption("p", true, "(optional) the page size for each search");

        return options;
    }

    /**
     * Print usage information to provided OutputStream.
     * 
     * @param applicationName
     *            Name of application to list in usage.
     * @param options
     *            Command-line options to be part of usage.
     * @param out
     *            OutputStream to which to write the usage information.
     */
    static void printUsage(
        final String applicationName,
        final Options options,
        final OutputStream out)
    {
        final PrintWriter writer = new PrintWriter(out);
        final HelpFormatter usageFormatter = new HelpFormatter();
        usageFormatter.printUsage(writer, 80, applicationName, options);
        writer.close();
    }
}
