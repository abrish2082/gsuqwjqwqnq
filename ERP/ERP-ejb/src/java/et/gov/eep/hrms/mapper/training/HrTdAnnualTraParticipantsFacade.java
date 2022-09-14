/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdAnnualTraParticipantsFacade extends AbstractFacade<HrTdAnnualTraParticipants> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdAnnualTraParticipantsFacade() {
        super(HrTdAnnualTraParticipants.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">

    public ArrayList<HrTdAnnualTraParticipants> SearchByAnnTraNeedId(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        Query queryReq = em.createNamedQuery("HrTdAnnualTraParticipants.findByAnnTraNeedId");
        try {
            queryReq.setParameter("annTraNeedId", hrTdAnnualTraParticipants.getAnnTraNeedId().getId());
            ArrayList<HrTdAnnualTraParticipants> trainList = new ArrayList(queryReq.getResultList());
            return trainList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrTdAnnualTraParticipants> SrchAnnTraNeedIdToBeApproved(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        Query queryReq = em.createNamedQuery("HrTdAnnualTraParticipants.findByTraNeedIdToBeApproved");
        try {
            queryReq.setParameter("annTraNeedId", hrTdAnnualTraParticipants.getAnnTraNeedId().getId());
            ArrayList<HrTdAnnualTraParticipants> trainList = new ArrayList(queryReq.getResultList());
            return trainList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrTdAnnualTrainingNeeds findByCourse(HrTdCourses hrTdCourses) {
        try {
            Query query = em.createNamedQuery("HrTdAnnualTraParticipants.findByCourse", HrTdAnnualTrainingNeeds.class);
            query.setParameter("trainingId", hrTdCourses);
            System.out.println("course on faced" + query.getResultList());
            return (HrTdAnnualTrainingNeeds) query.getResultList().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<HrTdAnnualTraParticipants> findAllApproved() {
        Query query = em.createNamedQuery("HrTdAnnualNeedRequests.findAllApproved");
        try {
            ArrayList<HrTdAnnualTraParticipants> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrEmployees> findByEmpId(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrTdAnnualTraParticipants.findByEmpId");
        query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrEmployees> empID = new ArrayList<>(query.getResultList());
            System.out.println("Emp Name === " + empID);
            return empID;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrEmployees> findByEmpName(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrTdAnnualTraParticipants.findByName");
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrEmployees> empName = new ArrayList<>(query.getResultList());
            System.out.println("Emp Name === " + empName);
            return empName;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrTdAnnualTrainingNeeds> findApproved() {
        Query query = em.createNamedQuery("HrTdAnnualTrainingNeeds.findAll");
        try {
            List<HrTdAnnualTrainingNeeds> course = new ArrayList(query.getResultList());
            return course;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrTdAnnualTrainingNeeds getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrTdAnnualTrainingNeeds.findById");
        query.setParameter("id", request);
        try {
            HrTdAnnualTrainingNeeds selectedRequest = (HrTdAnnualTrainingNeeds) query.getResultList().get(0);
            return selectedRequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

}
