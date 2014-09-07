package com.ipdev.common.dao.patent;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.ipdev.common.DaoException;
import com.ipdev.common.entity.patent.Patent;
import com.ipdev.common.recorder.JsonFileDataRecorder;
import com.ipdev.common.utility.file.PatentFileUtility;

/**
 * This file-based DAO is to support store and retrieve patent object in file system. The content of patent object will
 * be stored in JSON format.
 * 
 * @author ZZ
 * 
 */
public class PatentStorageFileDao extends JsonFileDataRecorder implements PatentStorageDao, PatentSimpleSearchDao {
    private static final Log LOG = LogFactory.getLog(PatentStorageFileDao.class);

    static String DEFAULT_CHARSET = "UTF-8";
    static String FILE_EXTENSION = ".json";

    private URI baseLocation;
    private boolean outputNulls = true;

    public PatentStorageFileDao() {
        // setup default file base location
        String path = System.getProperty("java.io.tmpdir");
        try {
            baseLocation = new File(path).toURI();
        } catch (Exception e) {
            LOG.error("Invalid path for baseLocation: " + path);
            // no need to error out, external module can still set it to some valid location.
        }
    }

    public void setBaseLocation(String baseLocation) {
        this.baseLocation = new File(baseLocation).toURI();
        LOG.debug("Setup baseLocation = " + this.baseLocation);
    }

    public void setOutputNulls(boolean outputNulls) {
        this.outputNulls = outputNulls;
    }

    @Override
    public void save(Patent patent) {
        Preconditions.checkNotNull(patent, "patent cannot be null");

        URI fileUri = null;
        try {
            fileUri = PatentFileUtility.getPatentFileURI(patent, baseLocation, FILE_EXTENSION);
        } catch (URISyntaxException e) {
            LOG.error("Fail to get a file location for patent:" + patent.getPid());
            throw new DaoException("Fail to get a file location for patent:" + patent.getPid(), e);
        }

        try {
            this.record(fileUri, patent, outputNulls);
        } catch (Exception e) {
            throw new DaoException("Caught exception while trying to store patent:" + patent.getPid(), e);
        }
    }

    @Override
    public Patent findPatentById(String pid) {
        Preconditions.checkNotNull(pid, "pid cannot be null");

        URI path = PatentFileUtility.patentPidToFolderPath(baseLocation, pid, /* sourceDb= */null, FILE_EXTENSION);
        if (path == null) {
            LOG.error("Cannot resolve file path to this pid:" + pid);
            return null;
        }

        try {
            String jsonStr = FileUtils.readFileToString(new File(path), DEFAULT_CHARSET);
            return this.getJsonHelper().fromJsonString(jsonStr, Patent.class);
        } catch (Exception e) {
            LOG.warn("Caught Exception when reading file:" + path, e);
            return null;
        }

    }
}
