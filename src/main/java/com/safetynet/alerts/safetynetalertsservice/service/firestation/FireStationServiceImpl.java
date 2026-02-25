package com.safetynet.alerts.safetynetalertsservice.service.firestation;


import com.safetynet.alerts.safetynetalertsservice.dto.responses.firestation.FireStationResponseDTO;
//import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
import com.safetynet.alerts.safetynetalertsservice.model.FireStation;
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
    public void updateFireStation(String address, String stationNumber) {
        updateCommand.execute(address, stationNumber);
    }

    @Override
    public void deleteFireStationByAddress(String address) {
        deleteCommand.executeByAddress(address);
    }

    @Override
    public void deleteFireStationByStation(String stationNumber) {
        deleteCommand.executeByStation(stationNumber);
    }


}
