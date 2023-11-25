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
 * The type Committee repository.
 *
 * @ClassName: CommiteeRepository

 * @Create: 2023 -11-23 16:50
 */
public class CommitteeRepository extends Repository {
    /**
     * The Filename.
     */
    protected final String FILENAME = "committee.csv";

    @Override
    protected Function<Entity, String> formatter() {
        return committee -> {
            Committee committeeEntity = (Committee) committee;
            return committeeEntity.getID() + SEPARATOR +
                    committeeEntity.getCampId() + SEPARATOR +
                    committeeEntity.getStudentid();

        };

    }
    @Override
    protected LineMapper<Entity> mapper() {//提取
        return fields -> new Committee(
                fields[0].trim(),
                fields[1].trim(),
                fields[2].trim()
                );

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
    public List<Committee> getAllCommittee() {
        List<Entity> entities = super.getAll();
        List<Committee> camps = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Committee) {
                camps.add((Committee) entity);
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
        if (entity instanceof Staff) {
            camp = (Camp) entity;
        } else {
            throw new EntityNotFoundException();
        }
        return camp;
    }

    /**
     * Save committee boolean.
     *
     * @param committee the committee
     * @return the boolean
     */
    public boolean saveCommittee(Committee committee) {
        try {
            super.update(committee);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
