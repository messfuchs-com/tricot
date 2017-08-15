package com.messfuchs.geo.models;

import org.omg.CORBA.INTERNAL;

import java.time.LocalDateTime;

public class Response implements Responsable {

    public LocalDateTime created, measured;
    public String target;
    public int statusCode;

    public Response(String target, LocalDateTime created, LocalDateTime measured, int statusCode) {
        this.target = target;
        this.created = created;
        this.measured = measured;
        this.statusCode = statusCode;
    }

    public Response(LocalDateTime created, LocalDateTime measured, int statusCode) {
        this("target", created, measured, statusCode);
    }

    public Response(String target) {
        this();
        this.target = target;
    }

    public Response() {
        this(LocalDateTime.now(), LocalDateTime.now(), 200);
    }

}
