package com.safetynet.alerts.safetynetalertsservice.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Deduplicator {
    private static final Logger logger = LoggerFactory.getLogger(Deduplicator.class);

    /**
     * @param dataWrapperField Represents MedicalRecords, FireStations and Persons in this program.
     * @param collectionName the wrapperField name.
     * @param <T> the pattern stills the same.
     * @return return the List that is going to be loaded (without the duplicates)
     */
    public static <T> List<T> deduplicate(List<T> dataWrapperField, String collectionName) {
        Set<T> uniqueSet = new LinkedHashSet<>(dataWrapperField);
        if (uniqueSet.size() < dataWrapperField.size()) {
            int duplicates = dataWrapperField.size() - uniqueSet.size();
            logger.warn("Detected {} duplicate entries in {}. Deduplicating.", duplicates, collectionName);
        }
        return List.copyOf(uniqueSet);
    }



}
