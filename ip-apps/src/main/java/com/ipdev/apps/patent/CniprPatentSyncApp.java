package com.ipdev.apps.patent;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

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
import com.google.common.collect.Sets;
import com.ipdev.apps.IpAppBase;
import com.ipdev.cnipr.dao.patent.PatentSearchByCNIPRDao;
import com.ipdev.common.dao.patent.PatentSimpleSearchDao;
import com.ipdev.common.dao.patent.PatentStorageDao;
import com.ipdev.common.dao.patent.PatentStorageFileDao;
import com.ipdev.common.dao.query.QueryDao;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.query.Query;

public class CniprPatentSyncApp extends IpAppBase {
    @Nonnull
    PatentSearchByCNIPRDao cniprDao;
    PatentSimpleSearchDao localSearchDao;
    PatentStorageFileDao fileStorageDao;
    PatentStorageDao dbStorageDao;
    boolean enableFileStorage = false;

    QueryDao queryDao;
    Set<String> sourceDbs = Sets.newHashSet();

    public void setCniprDao(PatentSearchByCNIPRDao cniprDao) {
        this.cniprDao = cniprDao;
    }

    public void setLocalSearchDao(PatentSimpleSearchDao localSearchDao) {
        this.localSearchDao = localSearchDao;
    }

    public void setQueryDao(QueryDao queryDao) {
        this.queryDao = queryDao;
    }

    public void setFileStorageDao(PatentStorageFileDao storageDao) {
        this.fileStorageDao = storageDao;
    }

    public void setDbStorageDao(PatentStorageDao dbStorageDao) {
        this.dbStorageDao = dbStorageDao;
    }

    public void setFileBaseFolder(String baseFolder) {
        Preconditions.checkState(fileStorageDao != null, "fileStorageDao cannot be null");
        this.fileStorageDao.setBaseLocation(baseFolder);
        this.enableFileStorage = true;
    }

    public void setSourceDbs(String sourceDbs) {
        Preconditions.checkNotNull(sourceDbs, "sourceDbs cannot be null");

        for (String db : StringUtils.split(sourceDbs, ",")) {
            this.addSourceDb(db);
        }
    }

    public void addSourceDb(String sourceDb) {
        Preconditions.checkNotNull(sourceDb, "sourceDb cannot be null");

        this.sourceDbs.add(sourceDb.toUpperCase());
    }

    public int syncPatentsByUser(String user) {
        Preconditions.checkNotNull(user, "user cannot be null");

        LOG.info("Starting to sync patents from CNIPR for user: " + user);

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

        LOG.info("Total patents sync from CNIPR to local storage = {}", numbChanged);
        return numbChanged;
    }

    int syncPatentsByQuery(Query query) {
        int numbChanged = 0;

        LOG.info("Sync patents from CNIPR for query=[{}]", query);

        List<Patent> patents = cniprDao.findPatentsByQuery(query, sourceDbs, null);
        if (CollectionUtils.isEmpty(patents)) {
            LOG.info("No patent found from CNIPR");
            return 0;
        }

        // for each patent id, check if our DB already has it (TODO: we might still need to update it if there's status
        // change in Patent detail)
        for (Patent patent : patents) {
            Patent db = this.localSearchDao.findPatentById(patent.getPid());
            if (db != null) {
                // TODO: we might still need to update it if there's status change in Patent detail
                continue;
            }

            patent = cniprDao.findPatentById(patent.getPid());
            if (this.enableFileStorage) {
                this.fileStorageDao.save(patent);
            }
            this.dbStorageDao.save(patent);
            numbChanged++;
        }
        LOG.info("Number of patents sync from CNIPR to local storage = {}", numbChanged);

        return numbChanged;
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
