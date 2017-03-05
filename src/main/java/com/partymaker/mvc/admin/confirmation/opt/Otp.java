package com.partymaker.mvc.admin.confirmation.opt;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by anton
 * High level of abstraction on one tome password
 * @generate method must obtain secret and returns the otp value
 * @validate method must obtain otp and returns true/false
 */
public interface Otp<T,V> {

    T generate(V secret);

    boolean validate(T otp) throws JsonProcessingException;
}
