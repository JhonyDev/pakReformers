package com.app.pakreformers.models;

public class Participant extends Super {
    String driveId;
    String participant;

    public Participant() {
    }

    public Participant(String driveId, String participant) {
        this.driveId = driveId;
        this.participant = participant;
    }

    public String getDriveId() {
        return driveId;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}
