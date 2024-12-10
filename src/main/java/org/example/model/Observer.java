package org.example.model;

import java.util.List;

public interface Observer {
    void update(List<String> tickets);
}