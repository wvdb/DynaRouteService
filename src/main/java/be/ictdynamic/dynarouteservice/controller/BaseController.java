package be.ictdynamic.dynarouteservice.controller;

import be.ictdynamic.dynarouteservice.domain.TransportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by wvdbrand on 12/05/2017.
 */
public class BaseController {
    @Autowired
    protected TransportResponse transportResponse;

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        TransportResponse transportResponse = new TransportResponse();
        transportResponse.setResponseMessage((ex.getMessage() == null) ? ex.toString() : ex.getMessage());
        return new ResponseEntity<>(transportResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
