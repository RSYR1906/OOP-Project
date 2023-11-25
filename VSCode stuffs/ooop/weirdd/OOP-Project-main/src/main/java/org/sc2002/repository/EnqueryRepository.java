package org.sc2002.repository;

import org.sc2002.entity.*;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;

/**
 * The type Enquery repository.
 */
public class EnqueryRepository extends Repository{

    /**
     * The Filename.
     */
    protected final String FILENAME = "enquery.csv";

    @Override
    protected Function<Entity, String> formatter() {
        return enquery -> {
            Enquery enqueryEntity = (Enquery) enquery;
            return enqueryEntity.getID() + SEPARATOR +
                    enqueryEntity.getQuery() + SEPARATOR +
                    enqueryEntity.getAnswer() + SEPARATOR +
                    enqueryEntity.getCreateStudent() + SEPARATOR +
                    enqueryEntity.getSuggestStudent() + SEPARATOR+
                    enqueryEntity.getAnswerStaff()+SEPARATOR +
                    enqueryEntity.getCampId()+SEPARATOR +
                    enqueryEntity.getSuggestion()+SEPARATOR+
                    enqueryEntity.getReply()+SEPARATOR+
                    enqueryEntity.getIsApprove();
        };

    }

    @Override
    protected LineMapper<Entity> mapper() {//提取
        return fields -> new Enquery(fields[0].trim(),
                fields[1].trim(),
                fields[2].trim(),
                fields[3].trim(),
                fields[4].trim(),
                fields[5].trim(),
                fields[6].trim(),
                fields[7].trim(),
                fields[8].trim(),
                fields[9].trim().equals("true"));

    }

    @Override
    public String getFilePath() {
        return FILENAME;
    }

    /**
     * Gets all enquery.
     *
     * @return the all enquery
     */
    public List<Enquery> getAllEnquery() {
        List<Entity> entities = super.getAll();
        List<Enquery> enquery = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Enquery) {
                enquery.add((Enquery) entity);
            }
        }
        return enquery;
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
     * Save enquery boolean.
     *
     * @param enquery the enquery
     * @return the boolean
     */
    public boolean saveEnquery(Enquery enquery){
        try {
            super.update(enquery);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
