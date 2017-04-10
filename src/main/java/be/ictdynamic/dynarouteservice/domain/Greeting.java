package be.ictdynamic.dynarouteservice.domain;

import lombok.Getter;
import lombok.Setter;

public class Greeting {
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}