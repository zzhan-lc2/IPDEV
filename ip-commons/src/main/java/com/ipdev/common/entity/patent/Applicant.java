package com.ipdev.common.entity.patent;

import java.util.List;

import com.google.common.collect.Lists;
import com.ipdev.common.entity.Person;

public class Applicant extends Person {
	private static final long serialVersionUID = 1L;
	
	private List<Patent> patents = Lists.newArrayList();
}
