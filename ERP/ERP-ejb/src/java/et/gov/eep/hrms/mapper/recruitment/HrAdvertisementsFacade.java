/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuMediaTypes;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class HrAdvertisementsFacade extends AbstractFacade<HrAdvertisements> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @EJB
    HrRecruitmentRequestsFacade hrRecruitmentRequestsFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrAdvertisementsFacade() {
        super(HrAdvertisements.class);
    }

    /**
     *
     * @return
     */
    public List<String> distinctBatchCode() {
        Query query = em.createNativeQuery("SELECT DISTINCT BATCH_CODE "
                + "FROM HR_RECRUITMENT_REQUESTS "
                + "WHERE HR_RECRUITMENT_REQUESTS.STATUS =1 OR HR_RECRUITMENT_REQUESTS.STATUS =3"
                + "order by BATCH_CODE");
        try {
            return (List<String>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<HrLuMediaTypes> findhrluMedias() {
        Query query = em.createNamedQuery("HrLuMediaTypes.findAll");
        try {
            ArrayList<HrLuMediaTypes> hrLuMediaTypes = new ArrayList(query.getResultList());
            return hrLuMediaTypes;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param batchCode
     * @return
     */
    public List<Object[]> readApprovedRecruitments(String batchCode) {
        List<Object[]> reqList = null;
        String query = "SELECT hr_job_types.job_description, "
                + "  NVL(SUM(HR_RECRUITMENT_REQUESTS.NUM_OF_EMPS_APPROVED), 0) NUM_OF_EMPS_REQUESTED, "
                + "  COUNT(HR_RECRUITMENT_REQUESTS.ID) NUM_OF_REQ, "
                + "  HR_RECRUITMENT_REQUESTS.RECRUITMENT_TYPE, "
                + "  HR_RECRUITMENT_REQUESTS.JOB_ID, "
                + "  LISTAGG(HR_RECRUITMENT_REQUESTS.ID,',') WITHIN GROUP ( "
                + "ORDER BY HR_RECRUITMENT_REQUESTS.ID) AS ID, "
                + "  LISTAGG(HR_DEPARTMENTS.DEP_NAME ,',') WITHIN GROUP ( "
                + "ORDER BY HR_DEPARTMENTS.DEP_NAME) AS DEPT_ID "
                + "FROM HR_RECRUITMENT_REQUESTS, "
                + "  HR_JOB_TYPES, "
                + "  HR_DEPARTMENTS "
                + "WHERE HR_RECRUITMENT_REQUESTS.JOB_ID = HR_JOB_TYPES.ID "
                + "AND HR_RECRUITMENT_REQUESTS.DEPT_ID = HR_DEPARTMENTS.DEP_ID "
                + "AND HR_RECRUITMENT_REQUESTS.BATCH_CODE=?1 "
                + "GROUP BY HR_JOB_TYPES.JOB_DESCRIPTION, "
                + "  HR_RECRUITMENT_REQUESTS.RECRUITMENT_TYPE, "
                + "  HR_RECRUITMENT_REQUESTS.JOB_ID ";
        Query querystr = em.createNativeQuery(query);
        querystr.setParameter(1, batchCode);
        try {
            reqList = querystr.getResultList();
            return reqList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HrAdvertisements findByGrade(String bachCode) {
        try {
            Query query = em.createNamedQuery("HrAdvertisements.findByBatchCode", HrAdvertisements.class);
            query.setParameter("batchCode", bachCode);
            return (HrAdvertisements) query.getResultList().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param md
     * @return
     */
    public HrLuMediaTypes findByMediaName(HrLuMediaTypes md) {
        Query query = em.createNamedQuery("HrLuMediaTypes.findByMediaType");
        query.setParameter("mediaType", md.getMediaType());
        try {
            HrLuMediaTypes hrLuMediaTypes = (HrLuMediaTypes) query.getResultList().get(0);
            return hrLuMediaTypes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrAdvertisements> findAllDistinict() {
        Query query = em.createNamedQuery("HrAdvertisements.findAllDistinict");
        try {
            ArrayList<HrAdvertisements> list = new ArrayList(query.getResultList());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public ArrayList<HrAdvertisements> findAllDistinict() {
//        Query query = em.createNativeQuery("SELECT DISTINCT  BATCH_CODE from HR_ADVERTISEMENTS");
//        try {
//            ArrayList<HrAdvertisements> list=new ArrayList(query.getResultList());          
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    //    public List<HrRecruitmentRequests> distinctbatchCode1(HrRecruitmentRequests hrRecruitmentRequests) {
//        // HrRecruitmentRequests hrReq = new HrRecruitmentRequests();
//        TypedQuery<HrRecruitmentRequests> query = em.createQuery("SELECT sum(h.numOfEmpsRequested)numOfEmpsRequested,count(h.batchCode)batchCode FROM HrRecruitmentRequests h WHERE h.status = 1 and h.batchCode = :batchCode", HrRecruitmentRequests.class);
//        query.setParameter("batchCode", hrRecruitmentRequests.getBatchCode());
//        List<HrRecruitmentRequests> results = query.getResultList();
//        System.out.print("this is from readApprovedRecruitments" + results.size());        
//        for (HrRecruitmentRequests result : results) {
//            System.out.println(result.getNumOfEmpsApproved() + result.getBatchCode());
//        }        
//        return results;
//    }
//   
    //  Bekele
    public void findByBatchCodeAndEdit(HrRecruitmentRequests hrRecruitmentRequests) {
        Query query = em.createNamedQuery("HrRecruitmentRequests.findByBatchCode");
        query.setParameter("batchCode", hrRecruitmentRequests.getBatchCode());
        try {
            List<HrRecruitmentRequests> recruitList = new ArrayList(query.getResultList());
            for (HrRecruitmentRequests rec : recruitList) {
                rec.setStatus(6);   // status 6 means advertised requests
                hrRecruitmentRequestsFacade.edit(rec);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HrLuMediaTypes> searchByMediaType(HrLuMediaTypes hrLuMediaTypes) {
        Query query = em.createNamedQuery("HrLuMediaTypes.findMediaTypeLike");
        query.setParameter("mediaType", hrLuMediaTypes.getMediaType().toUpperCase() + '%');
        try {
            ArrayList<HrLuMediaTypes> mediaList = new ArrayList(query.getResultList());
            return mediaList;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrLuMediaTypes getByMediaType(HrLuMediaTypes mediaTypes) {
        Query query = em.createNamedQuery("HrLuMediaTypes.findByMediaType");
        query.setParameter("mediaType", mediaTypes.getMediaType());
        try {
            HrLuMediaTypes mediaList = (HrLuMediaTypes) query.getResultList().get(0);
            return mediaList;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean isMediaTypeExist(HrLuMediaTypes mediaTypes) {
        boolean isExist;
        Query query = em.createNamedQuery("HrLuMediaTypes.findByMediaType");
        query.setParameter("mediaType", mediaTypes.getMediaType());
        try {
            if (query.getResultList().size() > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
            return isExist;
        } catch (Exception ex) {
            return false;
        }
    }

}
