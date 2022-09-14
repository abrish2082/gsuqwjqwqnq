/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class HrSmSuccessorFacade extends AbstractFacade<HrSmSuccessorEvaluation> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmSuccessorFacade() {
        super(HrSmSuccessorEvaluation.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrSmSuccessorEvaluation getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findById");
        query.setParameter("id", request);
        try {
            HrSmSuccessorEvaluation selectrequest = (HrSmSuccessorEvaluation) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrSmSuccessorEvaluation> findJobTitle(String hrJobTypes) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findBykmpId1", HrSmSuccessorEvaluation.class);
        query.setParameter("jobTitle1", hrJobTypes);
        try {
            return (List<HrSmSuccessorEvaluation>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean checkDuplicate(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findByDuplicate", HrSmSuccessorEvaluation.class);
        query.setParameter("empId", hrSmSuccessorEvaluation.getEmpId());
        query.setParameter("kmpId", hrSmSuccessorEvaluation.getKmpId());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }

    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findByEmpIdLike", HrEmployees.class);
            query.setParameter("empId", hrEmployees.getEmpId().toUpperCase() + '%');
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmployees getByEmpId(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findByEmpId", HrEmployees.class);
        query.setParameter("empId", hrEmployees.getEmpId());
        try {
            HrEmployees emp = (HrEmployees) (query.getResultList().get(0));
            return emp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmployees getByName(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrSmSuccessorEvaluation.findByFirstName");
            query.setParameter("firstName", hrEmployees.getFirstName());
            HrEmployees empList = (HrEmployees) (query.getResultList().get(0));
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>
}
