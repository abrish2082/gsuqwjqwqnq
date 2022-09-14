/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsBidOpeningCheckList;
import et.gov.eep.prms.entity.PrmsSpecification;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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
public class PrmsSpecificationFacade extends AbstractFacade<PrmsSpecification> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public PrmsSpecificationFacade() {
        super(PrmsSpecification.class);
    }

    /**
     *
     * @param specification
     * @return
     */
    public PrmsSpecification getSpc(PrmsSpecification specification) {
        Query query = em.createNamedQuery("PrmsSpecification.findBySpecificationId");
        query.setParameter("specificationId", specification.getSpecificationId());
        try {
            PrmsSpecification spcatxn = (PrmsSpecification) query.getResultList().get(0);
            return spcatxn;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param prmsSpecification
     * @return
     */
    public ArrayList<PrmsSpecification> searchSpecification(PrmsSpecification prmsSpecification) {

        Query query = em.createNamedQuery("PrmsSpecification.searchBySpecificationNo");
        query.setParameter("specficationNo", prmsSpecification.getSpecficationNo() + "%");

        try {
            ArrayList<PrmsSpecification> specificationList = new ArrayList(query.getResultList());

            return specificationList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param generatedSpcNo
     * @return
     */
    public List<Integer> getSpcId(String generatedSpcNo) {
        Query query = em.createNamedQuery("PrmsSpecification.searchBySpecificationIdNoGenerate");
        query.setParameter("specificationId", generatedSpcNo + '%');
        try {
            List<String> specificationIdList = (List<String>) query.getResultList();

            Integer j = 0;
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 0; i < specificationIdList.size(); i++) {
                j = Integer.parseInt(specificationIdList.get(i).split("-")[2]);

                numbers.add(j);

            }
            Collections.sort(numbers);

            return numbers;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<PrmsBid> referenceNo() {
        ArrayList<PrmsBid> supplier = new ArrayList<>();

        Query query = em.createNamedQuery("PrmsBid.findAll");
//      
        try {
            supplier = new ArrayList(query.getResultList());

            return supplier;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param toString
     * @return
     */
    public ArrayList<PrmsBid> getbidNo(String toString) {

        ArrayList<PrmsBid> deptJobs = null;
        try {
            Query query = em.createNamedQuery("PrmsBid.findByRefNo", PrmsBid.class);
            query.setParameter("refNo", toString);
            deptJobs = (ArrayList<PrmsBid>) query.getResultList().get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deptJobs;
    }

    /**
     *
     * @return
     */
    public ArrayList<PrmsBidDetail> getBidNo() {
        Query query = em.createNamedQuery("PrmsBidDetail.searchItemName");
        try {
            ArrayList<PrmsBidDetail> ListOfBid = new ArrayList<>(query.getResultList());

            return ListOfBid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<PrmsBidDetail> getItemName() {
        Query query = em.createNamedQuery("PrmsBidDetail.searchItemNamee");
        try {
            ArrayList<PrmsBidDetail> ListOfBid = new ArrayList<>(query.getResultList());

            return ListOfBid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param prmsSpecification
     * @return
     */
    public PrmsSpecification getSpecification(PrmsSpecification prmsSpecification) {
        Query query = em.createNamedQuery("PrmsBidDetail.searchItemNamee");
        try {
            PrmsSpecification ListOfBid = (PrmsSpecification) (query.getResultList().get(0));

            return ListOfBid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsBid getBid(PrmsBid prmsBid) {

        PrmsBid deptJobs = null;
        try {
            Query query = em.createNamedQuery("PrmsBid.findByRefNos", PrmsBid.class);
            query.setParameter("refNo", prmsBid);
            deptJobs = (PrmsBid) query.getResultList().get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return deptJobs;
    }

    public List<PrmsBidDetail> getBidDet(PrmsBidDetail prmsBidDetail) {
        Query query = em.createNamedQuery("PrmsBidDetail.findById", PrmsBidDetail.class);
        query.setParameter("id", prmsBidDetail.getId() + "%");
        try {
            List<PrmsBidDetail> deptJobs = new ArrayList<>(query.getResultList());
            return deptJobs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<PrmsSpecification> getBidDetailInfo(PrmsBidDetail prmsBidDetail) {

        Query query = em.createNamedQuery("PrmsSpecification.findByBidDetIdBy", PrmsBidDetail.class);
        query.setParameter("bidDetId", prmsBidDetail.getItemRegId().getMatCode());
        try {
            ArrayList<PrmsSpecification> hrBranchInfo = new ArrayList(query.getResultList());
            return hrBranchInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsSpecification getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsSpecification.findBySpecificationId");
        query.setParameter("specificationId", id);

        try {
            PrmsSpecification selectrequest = (PrmsSpecification) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsSpecification LastNo() {

        Query query = em.createNamedQuery("PrmsSpecification.findByMaxCheckListNum");

        try {
            PrmsSpecification CheckListNo = (PrmsSpecification) query.getResultList().get(0);

            return CheckListNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
