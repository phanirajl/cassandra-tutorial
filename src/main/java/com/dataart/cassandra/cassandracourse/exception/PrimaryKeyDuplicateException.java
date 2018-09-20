package com.dataart.cassandra.cassandracourse.exception;

/**
 *
 * @author alitvinov
 */
public class PrimaryKeyDuplicateException extends RuntimeException {

    public PrimaryKeyDuplicateException(String message) {
        super(message);
    }    
}
