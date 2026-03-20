package com.safetynet.alerts.safetynetalertsservice.repository;

import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.MedicalRecord;
import com.safetynet.alerts.safetynetalertsservice.model.Person;
import com.safetynet.alerts.safetynetalertsservice.util.Deduplicator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * File-backed JSON repository that serves as the single source of truth for all domain data.
 *
 * <p>Thread safety: {@code currentData} is declared {@code volatile} so every thread always
 * reads the latest reference after an update. Writes are further guarded by {@code synchronized}
 * on {@link #update} to prevent lost updates under concurrent requests.
 * Bootstrap logic: if the configured external file already exists it is loaded directly;
 * otherwise the classpath seed {@code data.json} is copied to the external path.
 */
@Repository
public class JsonDataRepository implements DataRepository {
            //making sure every threads see the latest version of currentData.
    protected volatile DataWrapper currentData;
    private final ObjectMapper objectMapper;
    private final Path dataPath;

    public JsonDataRepository(ObjectMapper objectMapper, @Value("${data.file.path}") String filePath) throws IOException {
        this.objectMapper = objectMapper;
        this.dataPath = Paths.get(filePath);
        if (Files.exists(dataPath)) {
            // if the external log file already exists we use it directly
            try (InputStream iS = Files.newInputStream(dataPath)) {
                this.currentData = load(iS);
            }
        } else {
            // Bootstrap from classpath
            try (InputStream iS = getClass().getClassLoader().getResourceAsStream("data.json")) {
                if (iS == null) {
                    throw new IOException("Seed data.json not found in classpath");
                }
                this.currentData = load(iS);
                // Create directories if needed
                Files.createDirectories(dataPath.getParent());
                persist(this.currentData);
            }
        }
    }

    /**
     * Deserializes a {@link DataWrapper} from the given stream and strips any duplicates
     * that may be present in the source JSON, logging a warning for each duplicate found.
     *
     * @param is the input stream containing the JSON payload
     * @return a deduplicated {@link DataWrapper}
     * @throws IOException if the stream cannot be read or parsed
     */
    protected DataWrapper load(InputStream is) throws IOException {
        DataWrapper loaded = objectMapper.readValue(is, DataWrapper.class);

        return new DataWrapper( //If the JSON seed has duplicates, it sends a warning
                Deduplicator.deduplicate(loaded.persons(), "persons"),
                Deduplicator.deduplicate(loaded.fireStations(), "fireStations"),
                Deduplicator.deduplicate(loaded.medicalRecords(), "medicalRecords")
        );
    }

    @Override
    public List<Person> findAllPersons() {
        return currentData.persons();
    }

    @Override
    public List<FireStation> findAllFireStations() {
        return currentData.fireStations();
    }

    @Override
    public List<MedicalRecord> findAllMedicalRecords() {
        return currentData.medicalRecords();
    }

    /**
     * Atomically applies {@code updateWrapper} to the current snapshot and persists the result.
     * Synchronized to serialize concurrent writes; {@code volatile} ensures every subsequent
     * read sees the new reference without stale-cache issues.
     * @param updateWrapper a pure function that produces a new {@link DataWrapper} from the old one;
     * any exception thrown inside will abort the update and leave the state unchanged
     */
    @Override
    public synchronized void update(UnaryOperator<DataWrapper> updateWrapper) {
        DataWrapper oldData = currentData;
        DataWrapper newData = updateWrapper.apply(oldData);
        currentData = newData;
        persist(newData);
    }

    /**
     * Writes {@code data} to the configured external file as pretty-printed JSON,
     * overwriting any previous content.
     *
     * @param data the snapshot to persist
     */
    public void persist(DataWrapper data) {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(dataPath.toFile(), data);
    }
}