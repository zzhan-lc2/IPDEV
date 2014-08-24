package com.ipdev.common.recorder;

import java.net.URI;

import com.ipdev.common.entity.method.RequestResponse;

public interface PatentDataRecorder {

	/**
	 * Record patent query's request and response data to the specified storage path.
	 * Record the null field if the boolean flag [outputNulls] is set to true. 
	 * @param storagePath the desired storage path
	 * @param requestResponse the request and response of the data query
	 * @param outputNulls the flag for output null field or not
	 */
	void record(URI storagePath, RequestResponse requestResponse, boolean outputNulls);
}
