package org.sc2002.repository;

import org.sc2002.entity.CampAttendee;
import org.sc2002.entity.Enquery;
import org.sc2002.entity.Entity;
import org.sc2002.entity.LineMapper;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;

/**
 * The type Camp attendee repository.
 *
 * @ClassName: CampAttendeeRepository

 * @Create: 2023 -11-23 16:49
 */
public class CampAttendeeRepository extends Repository {


    /**
     * The Filename.
     */
    protected final String FILENAME = "campAttendee.csv";

    @Override
    protected Function<Entity, String> formatter() {
        return campAttendee -> {
            CampAttendee campAttendeeEntity = (CampAttendee) campAttendee;
            return campAttendeeEntity.getID() + SEPARATOR +
                    campAttendeeEntity.getCampId() + SEPARATOR +
                    campAttendeeEntity.getStudentid() + SEPARATOR +
                    campAttendeeEntity.isForbid();
        };

    }

    @Override
    protected LineMapper<Entity> mapper() {//提取
        return fields -> new CampAttendee(
                fields[0].trim(),
                fields[1].trim(),
                fields[2].trim(),
                fields[3].trim().equals("true")
        );

    }

    @Override
    public String getFilePath() {
        return FILENAME;
    }

    /**
     * Gets all camp attendee.
     *
     * @return the all camp attendee
     */
    public List<CampAttendee> getAllCampAttendee() {
        List<Entity> entities = super.getAll();
        List<CampAttendee> campAttendees = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof CampAttendee) {
                campAttendees.add((CampAttendee) entity);
            }
        }
        return campAttendees;
    }

    /**
     * Gets enquery by id.
     *
     * @param entityID the entity id
     * @return the enquery by id
     * @throws EntityNotFoundException the entity not found exception
     */
    public Enquery getEnqueryByID(String entityID) throws EntityNotFoundException {
        Enquery enquery;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Enquery) {
            enquery = (Enquery) entity;
        } else {
            throw new EntityNotFoundException();
        }
        return enquery;
    }

    /**
     * Save camp attendee boolean.
     *
     * @param campAttendee the camp attendee
     * @return the boolean
     */
    public boolean saveCampAttendee(CampAttendee campAttendee) {
        try {
            super.update(campAttendee);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
