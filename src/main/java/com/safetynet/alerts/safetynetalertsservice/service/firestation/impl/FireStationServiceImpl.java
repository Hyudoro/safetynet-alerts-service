package com.safetynet.alerts.safetynetalertsservice.service.firestation.impl;


import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;
//import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.service.firestation.interfaces.*;
import org.springframework.stereotype.Service;

@Service
public class FireStationServiceImpl implements FireStationService {

    private final ReadFireStationCommand readCommand;
    private final AddFireStationCommand addCommand;
    private final UpdateFireStationCommand updateCommand;
    private final DeleteFireStationCommand deleteCommand;

    public FireStationServiceImpl(
            ReadFireStationCommand readCommand,
            AddFireStationCommand addCommand,
            UpdateFireStationCommand updateCommand,
            DeleteFireStationCommand deleteCommand
    ) {
        this.readCommand = readCommand;
        this.addCommand = addCommand;
        this.updateCommand = updateCommand;
        this.deleteCommand = deleteCommand;
    }

    @Override
    public FireStationResponseDTO getResidentsByStation(String stationNumber) {
        return readCommand.getResidentsByStation(stationNumber);
    }

    @Override
    public void addFireStation(FireStation fs) {
        addCommand.execute(fs);
    }

    @Override
    public void updateFireStation(FireStation oldFireStation ,Integer stationNumber) {
        updateCommand.execute(oldFireStation, stationNumber);
    }

    @Override
    public void deleteMappingsByAddress(String address) {
        deleteCommand.executeByAddress(address);
    }

    @Override
    public void deleteMappingsByStation(String stationNumber) {
        deleteCommand.executeByStation(stationNumber);

    }

    @Override
    public void deleteMapping(FireStation fs) {
        deleteCommand.executeByFireStation(fs);
    }

}
