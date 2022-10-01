package com.app.pakreformers.singletons;

import com.app.pakreformers.models.Drive;

public class DriveSingleton {
    public static Drive selectedDrive = null;

    public static Drive getInstance() {
        return selectedDrive;
    }

    public static void setSelectedDrive(Drive selectedDrive) {
        DriveSingleton.selectedDrive = selectedDrive;
    }
}
