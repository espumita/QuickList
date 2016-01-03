package com.example.david.quicklist.application.android;

import java.util.ArrayList;
import java.util.List;

public class AnnotationSetReader {

    public List<String> read() {
        List<String> annotationSet = new ArrayList<>();
        annotationSet.add("First list");
        annotationSet.add("Second list");
        annotationSet.add("Shopping List");
        annotationSet.add("Another List");
        annotationSet.add("Weekend List");
        return annotationSet;
    }
}
