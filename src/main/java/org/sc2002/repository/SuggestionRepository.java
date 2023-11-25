package org.sc2002.repository;

import org.sc2002.entity.Entity;
import org.sc2002.entity.LineMapper;
import org.sc2002.entity.Suggestion;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;

public class SuggestionRepository extends Repository{
    public SuggestionRepository() {
        super();
        setFilePath("suggestions.csv");
    }

    @Override
    protected Function<Entity, String> formatter() {
        return suggestion -> {
            Suggestion suggestionEntity = (Suggestion) suggestion;
            return suggestionEntity.getID() + SEPARATOR + suggestionEntity.getCampID()+ SEPARATOR + suggestionEntity.getStudentID();
        };
    }

    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> new Suggestion(fields[0].trim(), fields[1].trim(), fields[1].trim());
    }

    public List<Suggestion> getAllSuggestions() {
        List<Entity> entities = super.getAll();
        List<Suggestion> suggestions = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof Suggestion) {
                suggestions.add((Suggestion) entity);
            }
        }
        return suggestions;
    }

    public Suggestion getSuggestionByID(String entityID) throws EntityNotFoundException {
        Suggestion suggestion;
        Entity entity = super.getByID(entityID);
        if (entity instanceof Suggestion) {
            suggestion = (Suggestion) entity;
        } else {
            throw new EntityNotFoundException();
        }
        return suggestion;
    }

}
