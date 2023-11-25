package org.sc2002.controller;

import org.sc2002.entity.Camp;
import org.sc2002.entity.Enquery;
import org.sc2002.entity.Staff;
import org.sc2002.entity.Student;
import org.sc2002.repository.CampRepository;
import org.sc2002.repository.EnqueryRepository;
import org.sc2002.utils.exception.DuplicateEntityExistsException;
import org.sc2002.utils.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Enquery controller.
 *
 * @ClassName: EnqueryController

 * @Create: 2023 -11-22 19:23
 */
public class EnqueryController {
    private EnqueryRepository enqueryRepository;
    private CampRepository campRepository;
    /**
     * Instantiates a new Enquery controller.
     *
     * @param enqueryRepository the enquery repository
     */
    public EnqueryController(EnqueryRepository enqueryRepository) {
        this.enqueryRepository = enqueryRepository;
    }

    public EnqueryController(EnqueryRepository enqueryRepository, CampRepository campRepository) {
        this.enqueryRepository = enqueryRepository;
        this.campRepository = campRepository;
    }

    /**
     * Add enquery boolean.
     *
     * @param enquery the enquery
     * @return the boolean
     */
    public boolean addEnquery(Enquery enquery) {
        try {
            enqueryRepository.add(enquery);
            setEnqueryCamp();
            return true;
        } catch (DuplicateEntityExistsException e) {
            System.out.println("Failed to add entity: " + e.getMessage());
        }
        return false;
    }

    /**
     * Add enquery by student boolean.
     *
     * @param studentID the student id
     * @param query     the query
     * @param campID    the camp id
     * @return the boolean
     */
    public boolean addEnqueryByStudent(String studentID, String query, String campID) {
        Enquery enquery = new Enquery(query, studentID, campID);
        setEnqueryCamp();
        if (enqueryRepository.saveEnquery(enquery)) return true;
        else return false;

    }


    /**
     * Edit enquery.
     *
     * @param enquery the enquery
     */
    public void editEnquery(Enquery enquery) {
        enqueryRepository.saveEnquery(enquery);
        setEnqueryCamp();
    }

    /**
     * Gets enquery by student.
     *
     * @param student the student
     * @return the enquery by student
     */
    public List<Enquery> getEnqueryByStudent(Student student) {
        List<Enquery> enquiries = enqueryRepository.getAllEnquery();
        List<Enquery> ans = new ArrayList<>();
        for (Enquery enquery : enquiries) {
            if (enquery.getCreateStudent().equals(student.getID())) {
                ans.add(enquery);
            }
        }
        return ans;
    }

    /**
     * Gets enquery by commitee.
     *
     * @param student the student
     * @return the enquery by commitee
     */
    public List<Enquery> getEnqueryByCommitee(Student student) {
        List<Enquery> enquires = enqueryRepository.getAllEnquery();
        List<Enquery> ans = new ArrayList<>();
        for (Enquery enquery : enquires) {
            if (enquery.getCampId().equals(student.getCampCommitteeMember())) {
                ans.add(enquery);
            }
        }
        return ans;
    }

    public List<Enquery> getEnqueryByCampId(String campId) {
        List<Enquery> enquires = enqueryRepository.getAllEnquery();
        List<Enquery> ans = new ArrayList<>();
        for (Enquery enquery : enquires) {
            if (enquery.getCampId().equals(campId)) {
                ans.add(enquery);
            }
        }
        return ans;
    }

    /**
     * Gets enquery by staff.
     *
     * @param staff the staff
     * @return the enquery by staff
     */
    public List<Enquery> getEnqueryByStaff(Staff staff) {
        List<Enquery> enquires = enqueryRepository.getAllEnquery();
        List<Enquery> ans = new ArrayList<>();
        for (Enquery enquery : enquires) {
            if (enquery.getAnswerStaff().equals(staff.getID())) {
                ans.add(enquery);
            }
        }
        return ans;
    }

    /**
     * Delete enquery.
     *
     * @param enqueryId the enquery id
     */
    public void deleteEnquery(String enqueryId){
        try{
            enqueryRepository.remove(enqueryId);
        } catch (EntityNotFoundException e){
            System.out.println("Failed to delete entity: " + e.getMessage());
        }
        setEnqueryCamp();
    }

    public void setEnqueryCamp(){
        List<Camp> camps = campRepository.getAllCamps();
        List<Enquery> enqueries = enqueryRepository.getAllEnquery();
        for (Camp camp : camps) {
            for (Enquery enquery : enqueries) {
                if(camp.getID().equals(enquery.getCampId())){
                    camp.getEnqueryList().add(enquery);
                }
            }
        }
    }
}
