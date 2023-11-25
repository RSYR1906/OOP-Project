package org.sc2002.repository;

import org.sc2002.entity.*;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.sc2002.repository.DBcsv.SEPARATOR;

public class SuggestionRepository extends Repository{

    private CampRepository campRepository;
    private StudentRepository studentRepository;
    public SuggestionRepository(CampRepository campRepository, StudentRepository studentRepository) {
        super();
        setFilePath("suggestion.csv");
        this.campRepository = campRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    protected Function<Entity, String> formatter() {
        return suggestion -> {
            Suggestion suggestionEntity = (Suggestion) suggestion;
            return suggestionEntity.getID() + SEPARATOR + suggestionEntity.getCamp().getID()+ SEPARATOR + suggestionEntity.getStudent().getID() + SEPARATOR + suggestionEntity.getStaffID() + SEPARATOR + suggestionEntity.getSuggestion() + SEPARATOR + suggestionEntity.getApproved();
        };
    }

    @Override
    protected LineMapper<Entity> mapper() {
        return fields -> formatStringToSuggestion(fields);
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

    private Suggestion formatStringToSuggestion(String[] fields){
        Suggestion suggestion = null;
        try{
            String suggestionID = fields[0].trim();
            Camp camp = campRepository.getCampByID(fields[1].trim());
            Student student = studentRepository.getStudentByID(fields[2].trim());
            String staffID = fields[3].trim();
            String suggestionContent = fields[4].trim();
            Boolean isApproved = fields[5].trim().equalsIgnoreCase("true");
            suggestion = new Suggestion(suggestionID, staffID, student, camp,suggestionContent, isApproved );
        } catch (EntityNotFoundException e){
            System.out.println("Failed to map Enquiry");
        }
        return suggestion;
    }

}
