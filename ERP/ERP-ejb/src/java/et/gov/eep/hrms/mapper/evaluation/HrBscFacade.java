/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.evaluation;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.evaluation.HrBsc;
import et.gov.eep.hrms.entity.evaluation.HrBscSessions;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class HrBscFacade extends AbstractFacade<HrBsc> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrBscFacade() {
        super(HrBsc.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrBscSessions> readActiveSession(String toDayInEC) {
        Query query = em.createNamedQuery("HrBscSessions.findActiveSession", HrBscSessions.class);
        query.setParameter("toDayInEC", toDayInEC);
        try {
            return (List<HrBscSessions>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrBscSessions findById(HrBscSessions hrBscSessions) {
        Query query = em.createNamedQuery("HrBscSessions.findById");
        query.setParameter("id", hrBscSessions.getId());
        try {
            return (HrBscSessions) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrDepartments findByDepartmentId(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", hrDepartments.getDepId());
        try {
            HrDepartments getDepartment = (HrDepartments) query.getResultList().get(0);
            return getDepartment;
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<HrEmployees> searchEmp(String hrEmployee) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstNameLike", HrEmployees.class);
        query.setParameter("firstName", hrEmployee.toUpperCase() + '%');
        try {
            ArrayList<HrEmployees> employeeInformations = new ArrayList(query.getResultList());
            return employeeInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmployees getByFName(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByFirstName");
            query.setParameter("firstName", hrEmployees.getFirstName());
            HrEmployees empList = (HrEmployees) (query.getResultList().get(0));
            return empList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployees> findEmployee(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrBsc.findEmployee", HrEmployees.class);
        query.setParameter("deptId", hrEmployees.getDeptId().getDepId());
        try {
            System.out.println("emp size " + query.getResultList().size());
            System.out.println("emp list " + query.getResultList());
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrBsc> findAllBscResult() {
        Query query = em.createNamedQuery("HrBsc.findAll");
        try {
            ArrayList<HrBsc> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrBsc> findByDepName(HrDepartments hrDepartments) {
        Query query = em.createNamedQuery("HrBsc.findByDepName");
        query.setParameter("depName", hrDepartments.getDepName());
        try {
            ArrayList<HrBsc> bsc = new ArrayList<>(query.getResultList());
            return bsc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrBsc getSelectedBSCResult(int result) {
        Query query = em.createNamedQuery("HrBsc.findById");
        query.setParameter("id", result);
        try {
            HrBsc selectedBsc = (HrBsc) query.getResultList().get(0);
            return selectedBsc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

}
