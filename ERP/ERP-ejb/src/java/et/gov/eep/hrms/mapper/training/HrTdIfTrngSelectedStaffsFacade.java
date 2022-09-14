/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.training.HrTdCourses;
import et.gov.eep.hrms.entity.training.HrTdIfTrngSelectedStaffs;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Behailu
 */
@Stateless
public class HrTdIfTrngSelectedStaffsFacade extends AbstractFacade<HrTdIfTrngSelectedStaffs> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdIfTrngSelectedStaffsFacade() {
        super(HrTdIfTrngSelectedStaffs.class);
    }

    public ArrayList<HrEmployees> searchByname(HrEmployees hrEmployees) {
        Query query = em.createNamedQuery("HrEmployees.findByFirstNameLike");
        query.setParameter("firstName", hrEmployees.getFirstName().toUpperCase() + '%');
            ArrayList<HrEmployees> empList = new ArrayList(query.getResultList());
            return empList;
    }

    public HrEmployees getbyName(HrEmployees hrEmployees) {
      Query query = em.createNamedQuery("HrEmployees.findByFirstName");
        query.setParameter("firstName", hrEmployees.getFirstName());
            HrEmployees empList = (HrEmployees) (query.getResultList().get(0));
            return empList;
    }

    public List<HrTdCourses> findalltrainings() {
        Query query = em.createNamedQuery("HrTdCourses.findAll");
            return query.getResultList();
        
    }

    public List<HrTdTrainerProfiles> findallinstitutions() {
        Query query = em.createNamedQuery("HrTdTrainerProfiles.findAll");
            return query.getResultList();
    }
 public List<HrAddresses> findallAdresses() {
        Query query = em.createNamedQuery("HrAddresses.findAll");
            return query.getResultList();
    }
    
   

    public HrAddresses findalladdrsse(HrAddresses hrAddresses) {
          Query query = em.createNamedQuery("HrAddresses.findByAddressId");
        query.setParameter("addressId", hrAddresses.getAddressId());
            HrAddresses getDepartment = (HrAddresses) query.getResultList().get(0);
            return getDepartment;
    }

    public HrTdIfTrngSelectedStaffs findstaffById(BigDecimal id) {
        Query query = em.createNamedQuery("HrTdIfTrngSelectedStaffs.findById");
        query.setParameter("id", id);
            HrTdIfTrngSelectedStaffs selectedu = (HrTdIfTrngSelectedStaffs) query.getResultList().get(0);
            return selectedu;
    }

   

   
}
