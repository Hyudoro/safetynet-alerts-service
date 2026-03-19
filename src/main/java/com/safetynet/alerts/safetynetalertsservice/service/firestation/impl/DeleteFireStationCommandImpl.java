package com.safetynet.alerts.safetynetalertsservice.service.firestation.impl;

import com.safetynet.alerts.safetynetalertsservice.repository.DataWrapper;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithAddressNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationAndAddressNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.model.exception.MappingWithStationNotFoundException;
import com.safetynet.alerts.safetynetalertsservice.repository.DataRepository;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.DeleteFireStationCommand;
import org.springframework.stereotype.Service;


import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Service
public class DeleteFireStationCommandImpl implements DeleteFireStationCommand {
    DataRepository dataRepository;

    public DeleteFireStationCommandImpl(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    /**
     * Removes all fire-station mappings whose address equals {@code address}.
     *
     * @param address the address whose mappings should be deleted
     * @throws MappingWithAddressNotFoundException if no mapping exists for {@code address}
     */
    @Override
    public void executeByAddress(String address) {
        dataRepository.update(oldData -> {
            boolean mappingExist = oldData.fireStations().stream().anyMatch(fs ->
                    fs.address().equals(address));
            if (!mappingExist) {
                throw new MappingWithAddressNotFoundException(address);
            }
            Set<FireStation> fireStations = new LinkedHashSet<>(oldData.fireStations());
            fireStations.removeIf(fs -> fs.address().equals(address));
            return new DataWrapper(oldData.persons(),List.copyOf(fireStations), oldData.medicalRecords());
        });
    }

    /**
     * Removes all fire-station mappings whose station number equals {@code station}.
     *
     * @param station the station number whose mappings should be deleted
     * @throws MappingWithStationNotFoundException if no mapping exists for {@code station}
     */
    @Override
    public void executeByStation(String station) {
        dataRepository.update(oldData -> {
            boolean mappingExist = oldData.fireStations().stream().anyMatch(fs ->
                    fs.station().equals(station));
            if (!mappingExist) {
                throw new MappingWithStationNotFoundException((station));
            }
            Set<FireStation> fireStations = new LinkedHashSet<>(oldData.fireStations());
            fireStations.removeIf(fs -> fs.station().equals(station));
            return new DataWrapper(oldData.persons(),List.copyOf(fireStations), oldData.medicalRecords());
        });
    }

    /**
     * Removes the exact address+station mapping identified by {@code fireStation}.
     *
     * @param fireStation the exact mapping to delete (matched by address+station equality)
     * @throws MappingWithStationAndAddressNotFoundException if the mapping does not exist
     */
    @Override
    public void executeByFireStation(FireStation fireStation) {
        dataRepository.update(oldData -> {
            Set<FireStation> fireStations = new LinkedHashSet<>(oldData.fireStations());

            if(fireStations.contains(fireStation)){
                fireStations.remove(fireStation);
            }else {
                throw new MappingWithStationAndAddressNotFoundException(fireStation.address(), fireStation.station());
            }
            return new DataWrapper(oldData.persons(), List.copyOf(fireStations), oldData.medicalRecords());
        });
    }
}
