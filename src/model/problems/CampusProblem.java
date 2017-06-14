package model.problems;

import model.campus.CampusObject;

public class CampusProblem implements CampusObject {
    private final int id;
    private final int reporterID;
    private final int solverID;
    private final String title;
    private final String description;

    public CampusProblem(int id, int reporterID, int solverID, String title, String description) {

        this.id = id;
        this.reporterID = reporterID;
        this.solverID = solverID;
        this.title = title;
        this.description = description;
    }

    public int getReporterID() {
        return reporterID;
    }

    public int getSolverID() {
        return solverID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getID() {
        return id;
    }
}
