package org.sc2002.repository;

import org.sc2002.entity.*;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;
import static org.sc2002.utils.CAMSDateFormat.formatStringToDate;

/**
 * The type Camp repository.
 */
public class CampRepository extends Repository{

    /**
     * The Filename.
     */
    protected final String FILENAME = "camp.csv";

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

    /**
     * Gets camp by id.
     *
     * @param entityID the entity id
     * @return the camp by id
     * @throws EntityNotFoundException the entity not found exception
     */
    public Camp getCampByID(String entityID) throws EntityNotFoundException {
        Camp camp;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Camp) {
            camp = (Camp) entity;
        } 
        else {
            throw new EntityNotFoundException();
        }
        return camp;
    }

    /**
     * Gets camp by camp id.
     *
     * @param entityID the entity id
     * @return the camp by camp id
     * @throws EntityNotFoundException the entity not found exception
     */
    public Camp getCampByCampID(String entityID) throws EntityNotFoundException {
        Camp camp;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Camp) {
            camp = (Camp) entity;
        }
        else {
            throw new EntityNotFoundException();
        }
        return camp;
    }

    private String formatEntityToCampString(Entity entity){
        Camp camp = (Camp) entity;
        return camp.toStringWithSeparator(SEPARATOR);
    }


    private Camp formatCampStringToCamp(String[] fields){
        ArrayList<String> arrayField = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(fields, 11, fields.length)));

        return new Camp(
                fields[0].trim(),fields[1].trim(), fields[2].trim(), formatStringToDate(fields[3].trim()),
                formatStringToDate(fields[4].trim()), formatStringToDate(fields[5].trim()),
                Faculty.valueOf(fields[6].trim()),
                fields[7].trim(), Integer.parseInt(fields[8].trim()), Integer.parseInt(fields[9].trim()),
                fields[10].trim(), fields[11].trim().equals("true")

        );
    }

    /**
     * Save camp boolean.
     *
     * @param camp the camp
     * @return the boolean
     */
    public boolean saveCamp(Camp camp){
        try {
            super.update(camp);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
