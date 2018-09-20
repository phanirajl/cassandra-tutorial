/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dataart.cassandra.cassandracourse.exception;

/**
 *
 * @author alitvinov
 */
public class ApplicationLogicException extends RuntimeException{

    public ApplicationLogicException(String message) {
        super(message);
    }
    
}
