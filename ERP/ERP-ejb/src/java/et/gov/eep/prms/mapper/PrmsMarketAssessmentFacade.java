/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
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
public class PrmsMarketAssessmentFacade extends AbstractFacade<PrmsMarketAssessment> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsMarketAssessmentFacade() {
        super(PrmsMarketAssessment.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public ArrayList<PrmsMarketAssessment> searchMarketAssmtId(PrmsMarketAssessment eepPmsMarketAssessmentInfo) {

        Query query = em.createNamedQuery("PrmsMarketAssessment.findByMarketAssNo");
        query.setParameter("marketAssNo", eepPmsMarketAssessmentInfo.getMarketAssNo() + '%');
        try {
            ArrayList<PrmsMarketAssessment> marketAssessmentInfoLst = new ArrayList(query.getResultList());

            return marketAssessmentInfoLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsMarketAssessment searchEvent(PrmsMarketAssessment eepPmsMarketAssessmentInfo) {
        Query query = em.createNamedQuery("PrmsMarketAssessment.findByMarketAssNos");
        query.setParameter("marketAssNo", eepPmsMarketAssessmentInfo.getMarketAssNo());
        try {
            PrmsMarketAssessment marketAssessmentInfoLst = (PrmsMarketAssessment) (query.getResultList().get(0));

            return marketAssessmentInfoLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsMarketAssessment> getMarketNo(PrmsMarketAssessment marketAssessment) {
        
        if (marketAssessment.getColumnValue() != null && marketAssessment.getColumnName() != null
                && !marketAssessment.getColumnValue().equals("") && !marketAssessment.getColumnName().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_MARKET_ASSESSMENT\n"
                    + "where " + marketAssessment.getColumnName().toLowerCase() + " = '" + marketAssessment.getColumnValue() + "' "
                    + "and " + marketAssessment.getPreparedby() + "='" + marketAssessment.getPreparedby() + "' ", PrmsMarketAssessment.class);
            try {
                List<PrmsMarketAssessment> marketAssessmentSearchLists = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    marketAssessmentSearchLists = query.getResultList();
                    System.out.println("==marketAssessmentSearchLists==" + marketAssessmentSearchLists);
                }
                return marketAssessmentSearchLists;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsMarketAssessment.findByPreparedby");
            query.setParameter("preparedby", marketAssessment.getPreparedby());
            return query.getResultList();
        }

    }

    public List<PrmsMarketAssessment> getItemNameAss() {
        Query query = em.createNamedQuery("PrmsMarketAssessmentDetail.searchINameByLatestDate", PrmsMarketAssessment.class);

        try {
            List<PrmsMarketAssessment> itemLst = null;
            itemLst = (List<PrmsMarketAssessment>) query.getResultList();
            return itemLst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public PrmsMarketAssessment getSelectedRequest(String id) {
        Query query = em.createNamedQuery("PrmsMarketAssessment.findById");
        query.setParameter("id", id);

        try {
            PrmsMarketAssessment selectrequest = (PrmsMarketAssessment) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsMarketAssessment getMarktNo() {
        Query query = em.createNamedQuery("PrmsMarketAssessment.findByAutoIncr");
        try {
            PrmsMarketAssessment marketObj = new PrmsMarketAssessment();
            if (query.getResultList().size() >= 0) {
                marketObj = (PrmsMarketAssessment) query.getResultList().get(0);
            }
            return marketObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsMarketAssessment> getMarketAssmentOnList() {

        Query query = em.createNamedQuery("PrmsMarketAssessment.findByMarketAssmentReq", PrmsMarketAssessment.class);
        ArrayList<PrmsMarketAssessment> marketAssessmentList = new ArrayList(query.getResultList());
        return marketAssessmentList;
    }

    public List<PrmsMarketAssessment> getMarketAssmentSeqNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsMarketAssessment.findByMarketAssNo");
        query.setParameter("marketAssNo", prefix + "-" + '%' + "/" + eYear);
        List<PrmsMarketAssessment> prmsMarketAssessmentsLists = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            prmsMarketAssessmentsLists = query.getResultList();
        }
        return prmsMarketAssessmentsLists;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    //get List of Column(Parameter) Names from PrmsMarketAssessment table
    public List<PrmsMarketAssessment> getMarketAssessmentSearchingParameterList() {
        Query query = em.createNativeQuery("SELECT column_name  FROM user_tab_columns\n"
                + "WHERE table_name = UPPER('PRMS_MARKET_ASSESSMENT')\n"
                + "and column_name NOT IN('ID','LU_SUPPLIER_NAME_ID','STATUS','REMARK') ORDER BY column_name ASC");
        try {
            List<PrmsMarketAssessment> searchParamLists = new ArrayList<>();
            searchParamLists = query.getResultList();
            System.out.println("searchParamLists" + searchParamLists);
            return searchParamLists;
        } catch (Exception ex) {
            ex.getMessage();
            return null;
        }
    }
    // </editor-fold>

    public ArrayList<MmsServiceReg> getServiceNo() {
        Query query = em.createNamedQuery("MmsServiceReg.findAll");
        try {
            ArrayList<MmsServiceReg> mmsServiceRegLst = new ArrayList(query.getResultList());
            return mmsServiceRegLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public List<MmsServiceReg> getServNo(String toString) {
        List<MmsServiceReg> mmsServiceRegLst = null;

        try {
            Query query = em.createNamedQuery("MmsServiceReg.findByName", MmsServiceReg.class);
            query.setParameter("name", toString);
            mmsServiceRegLst = (List<MmsServiceReg>) query.getResultList();

            return mmsServiceRegLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsServiceReg> getserNo() {
        Query query = em.createNamedQuery("MmsServiceReg.findAll");
        try {
            List<MmsServiceReg> mmsServiceRegLst = new ArrayList<>(query.getResultList());
            return mmsServiceRegLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsServiceReg> getServNm(String serviceNam) {

        List<MmsServiceReg> serviceName = null;
        Query query = em.createNamedQuery("MmsServiceReg.findName");
        query.setParameter("nam", serviceNam + '%');

        try {
            serviceName = (List<MmsServiceReg>) (query.getResultList());

            return serviceName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MmsServiceReg> getServiceType() {
        Query query = em.createNativeQuery("SELECT DISTINCT MMS_SERVICE_REG.SERVICE_TYPE\n"
                + "FROM MMS_SERVICE_REG\n"
                + "where service_type is not null", PrmsServiceAndWorkReg.class);

        try {
            List<MmsServiceReg> serviceType = new ArrayList<>(query.getResultList());
            return serviceType;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
