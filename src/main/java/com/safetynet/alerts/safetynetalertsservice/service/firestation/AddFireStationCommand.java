package com.safetynet.alerts.safetynetalertsservice.service.firestation;

import com.safetynet.alerts.safetynetalertsservice.model.FireStation;

public interface AddFireStationCommand {
    void execute(FireStation fs);
}
