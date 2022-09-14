/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsServiceAndWorkRegFacade extends AbstractFacade<PrmsServiceAndWorkReg> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsServiceAndWorkRegFacade() {
        super(PrmsServiceAndWorkReg.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    //search by Service name and Logger now
    public ArrayList<PrmsServiceAndWorkReg> searchServiceInfoByServiceName(PrmsServiceAndWorkReg serviceAndWorkReg) {
        Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByServiceNameLike", PrmsServiceAndWorkReg.class);

        query.setParameter("serviceName", serviceAndWorkReg.getServiceName() + '%');
        query.setParameter("preparedBy", serviceAndWorkReg.getPreparedBy());
        try {
            ArrayList<PrmsServiceAndWorkReg> serviceNameLists = new ArrayList<>(query.getResultList());
            return serviceNameLists;
        } catch (Exception e) {
            return null;
        }
    }

    public PrmsServiceAndWorkReg getServiceInfoByName(PrmsServiceAndWorkReg serviceAndWork) {
        Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByServiceName");
        query.setParameter("serviceName", serviceAndWork.getServiceName());
        try {

            PrmsServiceAndWorkReg workInformations = (PrmsServiceAndWorkReg) query.getSingleResult();
            return workInformations;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsServiceAndWorkReg getWorkInfoByName(PrmsServiceAndWorkReg serviceAndWork) {
        Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByWorkName");
        query.setParameter("workName", serviceAndWork.getWorkName());
        try {

            PrmsServiceAndWorkReg serviceInformation = (PrmsServiceAndWorkReg) query.getSingleResult();
            return serviceInformation;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //search by work name and logger now
    public List<PrmsServiceAndWorkReg> searchWorkInfoByWorkName(PrmsServiceAndWorkReg serviceAndWorkReg) {
         if (serviceAndWorkReg.getColumnValue() != null && serviceAndWorkReg.getColumnName() != null
                && !serviceAndWorkReg.getColumnValue().equals("") && !serviceAndWorkReg.getColumnName().equals("")) {
            Query query = em.createNativeQuery("SELECT * FROM PRMS_SERVICE_AND_WORK_REG\n"
                    + "where " + serviceAndWorkReg.getColumnName().toLowerCase() + " = '" + serviceAndWorkReg.getColumnValue() + "' "
                    + "and " + serviceAndWorkReg.getPreparedBy()+ "='" + serviceAndWorkReg.getPreparedBy()+ "' ", PrmsServiceAndWorkReg.class);
            try {
                List<PrmsServiceAndWorkReg> prmsServiceAndWorkRegList = new ArrayList<>();
                if (query.getResultList().size() > 0) {
                    prmsServiceAndWorkRegList = query.getResultList();
                   
                }
                return prmsServiceAndWorkRegList;
            } catch (Exception ex) {
                ex.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByPreparedBy");
            query.setParameter("preparedBy", serviceAndWorkReg.getPreparedBy());
            return query.getResultList();
        }
    }

    //search all without any criteria
    public List<PrmsServiceAndWorkReg> findAllof(PrmsServiceAndWorkReg serviceAndWorkReg) {
        Query qu = em.createNamedQuery("PrmsServiceAndWorkReg.findAll");
        try {
            ArrayList<PrmsServiceAndWorkReg> allLists = new ArrayList<>(qu.getResultList());
            return allLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsServiceAndWorkReg> searchServiceByGeneralLedgerIDAndRegistratioType(PrmsServiceAndWorkReg serviceAndWorkReg) {
        Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByRegistrationTypeAndGLID", PrmsServiceAndWorkReg.class);

        query.setParameter("fmsGeneralLedger", serviceAndWorkReg.getGeneralLedgerId().getGeneralLedgerId());
        query.setParameter("registerationType", serviceAndWorkReg.getRegisterationType());

        try {
            ArrayList<PrmsServiceAndWorkReg> serviceAndWorkRegsList = new ArrayList(query.getResultList());
            return serviceAndWorkRegsList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //search from PrmsServiceAndWorkReg by GL Id
    public List<PrmsServiceAndWorkReg> searchServiceByGeneralLedgerIDAndRegistratioType2(PrmsServiceAndWorkReg serviceAndWorkReg) {
        Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByGlId", PrmsServiceAndWorkReg.class);

        query.setParameter("fmsGeneralLedger", serviceAndWorkReg.getGeneralLedgerId().getGeneralLedgerId());;

        try {
            ArrayList<PrmsServiceAndWorkReg> serviceAndWorkRegsList = new ArrayList(query.getResultList());
            return serviceAndWorkRegsList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //generate sequentially next Service or Work Number
    public List<PrmsServiceAndWorkReg> getServOrWorkSeqNo(String prefix, String currentYear) {
        List<PrmsServiceAndWorkReg> serviceAndWorkRegListNo = new ArrayList<>();
        if (prefix.equals("SN")) {
            Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByServiceSeqNo");
            query.setParameter("serviceNo", prefix + "-" + '%' + "/" + currentYear);
            if (query.getResultList().size() > 0) {
                serviceAndWorkRegListNo = query.getResultList();
            }
        } else if (prefix.equals("WN")) {
            Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByWorkSeqNo");
            query.setParameter("workNo", prefix + "-" + '%' + "/" + currentYear);
            if (query.getResultList().size() > 0) {
                serviceAndWorkRegListNo = query.getResultList();
            }
        }
        return serviceAndWorkRegListNo;
    }

    public PrmsServiceAndWorkReg findServiceFileUpLd(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByServAndWorkId");
        query.setParameter("servAndWorkId", prmsServiceAndWorkReg.getServAndWorkId());

        try {
            PrmsServiceAndWorkReg selectrequest = (PrmsServiceAndWorkReg) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Native Queries">
    //get List of  Service REGISTERATION TYPE only
    public List<PrmsServiceAndWorkReg> getServiceTypeOnly() {
        Query query = em.createNativeQuery("SELECT PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE\n"
                + "FROM PRMS_SERVICE_AND_WORK_REG\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE = 'service'", PrmsServiceAndWorkReg.class);
        try {
            List<PrmsServiceAndWorkReg> consultancy = new ArrayList<>(query.getResultList());
            return consultancy;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //get List of  Work REGISTERATION TYPE only
    public List<PrmsServiceAndWorkReg> getWorkTypeOnly() {
        Query query = em.createNativeQuery("SELECT PRMS_SERVICE_AND_WORK_REG.WORK_NAME,\n"
                + " PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE\n"
                + "FROM PRMS_SERVICE_AND_WORK_REG\n"
                + "where PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE='work'", PrmsServiceAndWorkReg.class);
        try {
            List<PrmsServiceAndWorkReg> conConsultancy = new ArrayList<>(query.getResultList());
            return conConsultancy;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // get List of Consultancy service only
    public List<PrmsServiceAndWorkReg> getConsultancyServiceOnlyLists() {

        Query query = em.createNativeQuery("SELECT PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_TYPE,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID\n"
                + "FROM PRMS_SERVICE_AND_WORK_REG\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.SERVICE_TYPE = 'consultancy'", PrmsServiceAndWorkReg.class);
        try {
            List<PrmsServiceAndWorkReg> conConsultancy = new ArrayList<>(query.getResultList());
            return conConsultancy;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // get Service Name by ServiceOrWorkId
    public PrmsServiceAndWorkReg getServiceName(PrmsServiceAndWorkReg serName) {

        Query query = em.createNativeQuery("SELECT PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_TYPE,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + " PRMS_SERVICE_AND_WORK_REG.SERVICE_NO,\n"
                + "PRMS_SERVICE_AND_WORK_REG.UNIT_MEASURES\n"
                + "FROM PRMS_SERVICE_AND_WORK_REG\n"
                + "WHERE PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID = '" + serName.getServAndWorkId() + "'", PrmsServiceAndWorkReg.class);
        try {
            PrmsServiceAndWorkReg serviceObject = null;
            if (query.getResultList().size() > 0) {
                serviceObject = (PrmsServiceAndWorkReg) query.getResultList().get(0);
            }
            return serviceObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // get Work Name by ServiceOrWorkId
    public PrmsServiceAndWorkReg getWorkName(PrmsServiceAndWorkReg workName) {

        Query query = em.createNativeQuery("SELECT DISTINCT PRMS_SERVICE_AND_WORK_REG.WORK_NAME,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.REGISTERATION_TYPE,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.UNIT_MEASURES\n"
                + "FROM PRMS_SERVICE_AND_WORK_REG\n"
                + "where serv_and_work_id='" + workName.getServAndWorkId() + "'", PrmsServiceAndWorkReg.class);
        try {
            PrmsServiceAndWorkReg serviceObject = null;
            if (query.getResultList().size() > 0) {
                serviceObject = (PrmsServiceAndWorkReg) query.getResultList().get(0);
            }
            return serviceObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //get List of Service Names List By From and To Date
    public List<PrmsServiceAndWorkReg> getServiceNameListByDate(Date startdate, Date endDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateOne = df.format(startdate);
        String dateTwo = df.format(endDate);
        Query query = em.createNativeQuery("SELECT PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID  = PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID\n"
                + "where prms_service_and_work_reg.registeration_type='service'"
                + "and PRMS_FINANCIAL_EVAL_RESULT.DATE_REG between TO_DATE('" + dateOne + "', 'dd/mm/yyyy')\n"
                + "AND TO_DATE('" + dateTwo + "', 'dd/mm/yyyy')", PrmsServiceAndWorkReg.class);
        try {
            List<PrmsServiceAndWorkReg> prmsServiceAndWorkRegs = new ArrayList<>(query.getResultList());
            return prmsServiceAndWorkRegs;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //get List of Work Names List By From and To Date
    public List<PrmsServiceAndWorkReg> getWorkNameListByDate(Date startYear, Date endYear) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dateOne = df.format(startYear);
        String dateTwo = df.format(endYear);
        Query query = em.createNativeQuery("SELECT PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID,\n"
                + "  PRMS_SERVICE_AND_WORK_REG.SERVICE_NAME\n"
                + "FROM PRMS_FINANCIAL_EVL_RESULTY_DTL\n"
                + "INNER JOIN PRMS_FINANCIAL_EVAL_RESULT\n"
                + "ON PRMS_FINANCIAL_EVAL_RESULT.ID = PRMS_FINANCIAL_EVL_RESULTY_DTL.FNANCIAL_RESULT_ID\n"
                + "INNER JOIN PRMS_SERVICE_AND_WORK_REG\n"
                + "ON PRMS_SERVICE_AND_WORK_REG.SERV_AND_WORK_ID  = PRMS_FINANCIAL_EVL_RESULTY_DTL.SERVICE_ID\n"
                + "where prms_service_and_work_reg.registeration_type='work'"
                + "and PRMS_FINANCIAL_EVAL_RESULT.DATE_REG between TO_DATE('" + dateOne + "', 'dd/mm/yyyy')\n"
                + "AND TO_DATE('" + dateTwo + "', 'dd/mm/yyyy')", PrmsServiceAndWorkReg.class);
        try {
            List<PrmsServiceAndWorkReg> workNameLists = new ArrayList<>(query.getResultList());
            return workNameLists;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>

    public List<PrmsServiceAndWorkReg> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_SERVICE_AND_WORK_REG') \n"
                + "and column_name not in ('SERV_AND_WORK_ID','STATUS','GENERAL_LEDGER_ID','SPECIFICATION_REMARK')");
        try {
            List<PrmsServiceAndWorkReg> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
