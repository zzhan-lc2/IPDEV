package com.ipdev.common.entity;

public class Constants {
    /**
     * Some entities have collection (such as List) as value. In order to flatten it to be stored into DB's simple
     * column, we would like to use this element-separator to chain the values into single string.
     */
    public static final String LIST_ELEMENT_SEPARATOR = "|";
}
