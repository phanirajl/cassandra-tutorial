package com.dataart.cassandra.cassandracourse.exception;

/**
 * Primary key duplicate exception.
 *
 * @author alitvinov
 */
public class PrimaryKeyDuplicateException extends RuntimeException {

    public PrimaryKeyDuplicateException(String message) {
        super(message);
    }
}
