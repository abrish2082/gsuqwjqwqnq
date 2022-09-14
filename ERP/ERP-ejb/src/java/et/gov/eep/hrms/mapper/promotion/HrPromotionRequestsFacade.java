/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.promotion;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
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
public class HrPromotionRequestsFacade extends AbstractFacade<HrPromotionRequests> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrPromotionRequestsFacade() {
        super(HrPromotionRequests.class);
    }

    public List<HrAdvertisements> findInternalVacancy() {
        List<HrAdvertisements> internalVacancyLists = null;
        try {
            Query query = em.createNamedQuery("HrAdvertisements.findByAdvertType", HrAdvertisements.class);
            query.setParameter("advertType", ("Inside"));
            internalVacancyLists = (List<HrAdvertisements>) query.getResultList();
            return internalVacancyLists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrAdvertisements> activeVacancies() {
        String strl = "SELECT HR_ADVERTISEMENTS.* "
                + "FROM HR_ADVERTISEMENTS "
                + "WHERE ADVERT_TYPE = 'Inside' "
                + "order by to_date(HR_ADVERTISEMENTS.ADVERT_DATE_FROM,'DD/MM/YYYY') desc";
        Query query = em.createNativeQuery(strl, HrAdvertisements.class);
        List<HrAdvertisements> activeVacancies = (List<HrAdvertisements>) query.getResultList();
        return activeVacancies;
    }

  

    public List<HrAdvertisedJobs> readAdverJobsQualification(int advertId) {
         try {
            Query query = em.createNamedQuery("HrAdvertisedJobs.findByAdvertId", HrAdvertisedJobs.class);
            query.setParameter("advertId", advertId);
            return (List<HrAdvertisedJobs>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrEmployees> searchPermanentEmp(String hrEmployees) {
        List<HrEmployees> employees = null;
        try {
            Query query = em.createNamedQuery("HrEmployees.findByFirstNameLike", HrEmployees.class);
            query.setParameter("firstName", hrEmployees.toUpperCase() + '%');
            return (List<HrEmployees>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrEmployees getPermanentEmp(HrEmployees hrEmployees) {
        try {
            Query query = em.createNamedQuery("HrEmployees.findByFirstName");
            query.setParameter("firstName", hrEmployees.getFirstName());
            HrEmployees empList = (HrEmployees) (query.getResultList().get(0));
            return empList;
        } catch (Exception ex) {
            // ex.printStackTrace();
            return null;
        }

    }

    public List<HrPromotionRequests> candList(String jobsId) {
        try {
            Query query = em.createNamedQuery("HrPromotionRequests.findByJobsId", HrPromotionRequests.class);
            query.setParameter("jobTitle", jobsId);
            return (List<HrPromotionRequests>) query.getResultList();
        } catch (Exception ex) {
            // ex.printStackTrace();
            return null;
        }
    }

    public List<HrPromotionRequests> findAlls() {
        try {
            Query query = em.createNamedQuery("HrPromotionRequests.findAllByDistinictJobTitle", HrPromotionRequests.class);
            return (List<HrPromotionRequests>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrAdvertisedJobs> getByHrAdvertisements(HrAdvertisements hrAdvertisements) {
        try {
            Query query = em.createNamedQuery("HrAdvertisedJobs.findByHrAdvertisements", HrAdvertisedJobs.class);
            query.setParameter("advertId", hrAdvertisements);
            return (List<HrAdvertisedJobs>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrPromotionRequests> getByJobsId(HrAdvertisedJobs advertisedJobsenty) {
        try {
            Query query = em.createNamedQuery("HrPromotionRequests.findByDistinictJobsId", HrPromotionRequests.class);
            query.setParameter("jobId", advertisedJobsenty.getJobId());
            return (List<HrPromotionRequests>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrPromotionRequests findPomoApplied(HrEmployees hrEmployees, HrAdvertisedJobs advertisedJobesEntity) {
        boolean checkup = true;
        try {
            Query query = em.createNamedQuery("HrPromotionRequests.findByEmpIdAndJobId", HrPromotionRequests.class);
            query.setParameter("empId", (hrEmployees.getId()));
            query.setParameter("advertJobId", advertisedJobesEntity.getId());
            if (query.getResultList().size() > 0) {
                HrPromotionRequests relname = (HrPromotionRequests) query.getResultList().get(0);
                return relname;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public int checkRequesterIsQualified(int empId) {
        int check = 0;
        String isEmployee = "SELECT employment_type,hire_date "
                + " FROM HR_EMPLOYEES"
                + " WHERE EMP_ID=?";

        Query query = em.createNativeQuery(isEmployee, HrPromotionRequests.class);
        query.setParameter(1, empId);
        HrPromotionRequests adverJobsQualification = (HrPromotionRequests) query.getSingleResult();

        String toDay = StringDateManipulation.currentDateInGC();
        try {
//
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//
        return check;
    }

}
