package com.ipdev.common.utility.json;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface JsonSkip {
	// Field tag only annotation
}
