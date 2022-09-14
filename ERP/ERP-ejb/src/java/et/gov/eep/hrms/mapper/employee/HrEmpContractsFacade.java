/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.employee;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmpContracts;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless

public class HrEmpContractsFacade extends AbstractFacade<HrEmpContracts> {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEmpContractsFacade() {
        super(HrEmpContracts.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Bussiness IMplementations">
    public ArrayList<HrEmpContracts> getContratlist(int id) {
        Query query = em.createNamedQuery("HrEmpContracts.findByempId");
        query.setParameter("empId", id);
        try {
            ArrayList<HrEmpContracts> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<HrEmpContracts> searchbyempforarray(HrEmpContracts hrEmpContracts) {
        Query query = em.createNamedQuery("HrEmpContracts.findByempIdforarray", HrEmpContracts.class);
        query.setParameter("empId", hrEmpContracts.getEmpId());
        try {
            ArrayList<HrEmpContracts> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
// for clear
    
    public HrEmpContracts searchByEmpSt(HrEmployees hrEmployees) {
        System.out.println("------inside FCD-----");
        Query query = em.createNamedQuery("HrEmpContracts.searchByEmpSt", HrEmpContracts.class);
        
        query.setParameter("empId", hrEmployees.getEmpId());
        query.setParameter("status", "Active");
        
        try {
            HrEmpContracts emp = (HrEmpContracts) (query.getResultList());
            System.out.println("------emp FCD-----" + emp);
            return emp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     *
     * @return
     */
    public int updatestate(HrEmployees hrEmployees) {
        try {
            Query q = em.createNamedQuery("HrEmpContracts.stateUpdate");
            q.setParameter("empId", hrEmployees);
            return q.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
        
    }
    
    public List<HrEmpContracts> findByEmpID(Integer empid) {
        Query query = em.createNamedQuery("HrEmpContracts.findByempIdforarray");
        query.setParameter("empId", empid);
        try {
            ArrayList<HrEmpContracts> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
//</editor-fold>
