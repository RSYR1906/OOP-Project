package org.sc2002.repository;

import org.sc2002.entity.*;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;
import static org.sc2002.utils.CAMSDateFormat.formatStringToDate;

public class CampRepository extends Repository{

    protected final String FILENAME = "camp.csv";

    // SINGLETON PATTERN
    // Private static variable of the same class that is the only instance of the class.
    private static CampRepository instance;

    // Private constructor to restrict instantiation of the class from other classes.
    private CampRepository() {}

    // Public static method that returns the instance of the class.
    public static synchronized CampRepository getInstance() {
        if (instance == null) {
            instance = new CampRepository();
        }
        return instance;
    }

    @Override
    protected Function<Entity, String> formatter() {
        return camp -> formatEntityToCampString(camp);
    }

    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> formatCampStringToCamp(fields);
    }

    @Override
    public String getFilePath() {
        return FILENAME;
    }

    /**
     * Gets the list of camps stored in the repository
     *
     * @return the list of camps
     */
    public List<Camp> getAllCamps() {
        List<Entity> entities = super.getAll();
        List<Camp> camps = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Camp) {
                camps.add((Camp) entity);
            }
        }
        return camps;
    }

    private String formatEntityToCampString(Entity entity){
        Camp camp = (Camp) entity;
        return camp.toStringWithSeparator(SEPARATOR);
    }

    private Camp formatCampStringToCamp(String[] fields){
        return new Camp(
                fields[0].trim(), fields[1].trim(), formatStringToDate(fields[2].trim()),
                formatStringToDate(fields[3].trim()), formatStringToDate(fields[4].trim()),
                Faculty.valueOf(fields[5].trim()),
                fields[6].trim(), Integer.parseInt(fields[7].trim()), Integer.parseInt(fields[8].trim())
        );

    }
}
