package com.ipdev.common.recorder;

import java.net.URI;

import com.ipdev.common.entity.method.RequestResponse;

public interface PatentDataRecorder {

	void record(URI storagePath, RequestResponse requestResponse, boolean outputNulls);
}
