/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import et.gov.eep.prms.entity.PrmsMarketAssmntService;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
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
public class PrmsPurchaseRequestFacade extends AbstractFacade<PrmsPurchaseRequest> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsPurchaseRequestFacade() {
        super(PrmsPurchaseRequest.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public ArrayList<PrmsPurchaseRequest> searchPurchaseReNo(PrmsPurchaseRequest eepPurchaseRequest) {

        Query query = em.createNamedQuery("PrmsPurchaseRequest.findByPrNumber");
        query.setParameter("prNumber", eepPurchaseRequest.getPrNumber() + '%');
        try {
            ArrayList<PrmsPurchaseRequest> purchaseRequestList = new ArrayList(query.getResultList());

            return purchaseRequestList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequest> getViewHistoryLists() {
        Query q = em.createNamedQuery("PrmsPurchaseRequest.findByStatusForView", PrmsPurchaseRequest.class);
        try {
            List<PrmsPurchaseRequest> viewHistoryList = new ArrayList<>();
            if (q.getResultList().size() > 0) {
                viewHistoryList = q.getResultList();
            }
            return viewHistoryList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequest> getPrDeptOrStoreSeqNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsPurchaseRequest.findByPrNumberLike");
        query.setParameter("prNumbers", prefix + "-" + "%" + "/" + eYear);
        List<PrmsPurchaseRequest> purchaseRequestsLists = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            purchaseRequestsLists = query.getResultList();
        }
        return purchaseRequestsLists;
    }

    public PrmsPurchaseRequest searchEvent(PrmsPurchaseRequest eepPurchaseRequest) {
        Query query = em.createNamedQuery("PrmsPurchaseRequest.findByPrNumbers");
        query.setParameter("prNumber", eepPurchaseRequest.getPrNumber());
        try {
            PrmsPurchaseRequest eepPurchaseRequestList = (PrmsPurchaseRequest) (query.getResultList().get(0));

            return eepPurchaseRequestList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    public ArrayList<PrmsPurchaseRequest> getPrReNo() {
        Query query = em.createNamedQuery("PrmsPurchaseRequest.findAll");
        try {
            ArrayList<PrmsPurchaseRequest> eepPurchaseRequestList = new ArrayList(query.getResultList());
            return eepPurchaseRequestList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    /**
     *
     * @return
     */
    public List<PrmsPurchaseRequest> getPrNo() {

        List<PrmsPurchaseRequest> prNoLst = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseRequest.findByPrNum", PrmsPurchaseRequest.class);

            prNoLst = (List<PrmsPurchaseRequest>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return prNoLst;
    }

    public List<PrmsPurchaseRequest> getPrNList(String toString) {

        List<PrmsPurchaseRequest> prNList = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseRequest.findByPrNumbers", PrmsPurchaseRequest.class);
            query.setParameter("prNumber", toString.toLowerCase());
            prNList = (List<PrmsPurchaseRequest>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return prNList;
    }

    public List<PrmsPurchaseRequest> getPrNListByPurchaseType(PrmsPurchaseRequest pr) {

        List<PrmsPurchaseRequest> prNList = null;
        try {
            Query query = em.createNativeQuery("select * from Prms_Purchase_Request pr where pr.PURCHASE_TYPE='" + pr.getPurchaseType() + "'", PrmsPurchaseRequest.class);
            prNList = (List<PrmsPurchaseRequest>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return prNList;
    }

    public MmsItemRegistration getListOfItemName(String toString) {

        MmsItemRegistration itemNameLst = null;
        Query query = em.createNamedQuery("MmsItemRegistration.findByMatNam", MmsItemRegistration.class);
        query.setParameter("matNam", toString);
        try {
            if (query.getResultList().size() > 0) {
                itemNameLst = (MmsItemRegistration) query.getResultList().get(0);
            }

            return itemNameLst;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsMarketAssmntService> getserviceNo(String serviceName) {

        List<PrmsMarketAssmntService> serviceList = null;
        try {
            Query query = em.createNamedQuery("PrmsMarketAssmntService.findById", PrmsMarketAssmntService.class);
            query.setParameter("id", serviceName);
            System.out.println("servname" + serviceName);
            serviceList = (List<PrmsMarketAssmntService>) query.getResultList();
            System.out.println("lists" + serviceList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return serviceList;
    }

    public List<PrmsMarketAssessment> getServNo() {
        Query query = em.createNamedQuery("PrmsMarketAssessment.findAll");
        try {
            List<PrmsMarketAssessment> serviceList = (List<PrmsMarketAssessment>) query.getResultList();
            return serviceList;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public List<MmsServiceReg> getServiceName() {
        Query query = em.createNamedQuery("MmsServiceReg.findAll");
        try {
            List<MmsServiceReg> serviceList = (List<MmsServiceReg>) query.getResultList();
            return serviceList;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public List<PrmsPurchaseRequest> searchByPrNo(PrmsPurchaseRequest eepPurchaseRequest) {
        List<PrmsPurchaseRequest> purchaseRequestLst = new ArrayList();
        if (eepPurchaseRequest.getColumnName() != null && !eepPurchaseRequest.getColumnName().equals("")
                && eepPurchaseRequest.getColumnValue() != null && !eepPurchaseRequest.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_PURCHASE_REQUEST\n"
                    + "where " + eepPurchaseRequest.getColumnName().toLowerCase() + " = '" + eepPurchaseRequest.getColumnValue() + "'"
                    + "and " + eepPurchaseRequest.getPreparedBy() + "='" + eepPurchaseRequest.getPreparedBy() + "'", PrmsPurchaseRequest.class);
            try {
                if (query.getResultList().size() > 0) {
                    purchaseRequestLst = query.getResultList();
                }
                return purchaseRequestLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsPurchaseRequest.findByPreparedBy");
            query.setParameter("preparedBy", eepPurchaseRequest.getPreparedBy());
            purchaseRequestLst = query.getResultList();
            return purchaseRequestLst;
        }
    }

    public List<PrmsMarketAssessmentDetail> getItemName(String itemName) {

        List<PrmsMarketAssessmentDetail> servicename = null;
        Query query = em.createNamedQuery("PrmsMarketAssessmentDetail.findByItemNm", PrmsMarketAssessmentDetail.class);
        query.setParameter("itemNm", itemName);
        try {
            servicename = (List<PrmsMarketAssessmentDetail>) query.getResultList();
            System.out.println("======in finakl====" + servicename);
            return servicename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public MmsItemRegistration getItemName(MmsItemRegistration itemName) {
//        System.out.println("in item name-----"+itemName);
//        MmsItemRegistration servicename = null;
//        Query query = em.createNativeQuery("SELECT MMS_ITEM_REGISTRATION.MAT_NAME,\n"
//                + "  MMS_ITEM_REGISTRATION.MATERIAL_ID,\n"
//                + "  MMS_ITEM_REGISTRATION.MAT_CODE,\n"
//                + "  MMS_ITEM_REGISTRATION.UNIT_MEASURE_1,\n"
//                + "  PRMS_MARKET_ASSESSMENT_DETAIL.UNIT_PRICE\n"
//                + "FROM PRMS_MARKET_ASSESSMENT_DETAIL\n"
//                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
//                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_MARKET_ASSESSMENT_DETAIL.ITEM_ID\n"
//                + "where  MMS_ITEM_REGISTRATION.MATERIAL_ID='" + itemName + "'", MmsItemRegistration.class);
//        query.setParameter("id", itemName);

//        try {
//            servicename = (MmsItemRegistration) query.getResultList().get(0);
//        System.out.println("======in finakl====" + servicename.getMatName());
//        return servicename;
//    }
//    catch (Exception e
//
//    
//        ) {
//            e.printStackTrace();
//        return null;
//    }
//}
    public PrmsPurchaseRequest getIdNo(Long id) {
        PrmsPurchaseRequest idNo = null;
        Query query = em.createNamedQuery("PrmsPurchaseRequest.findById", PrmsPurchaseRequest.class);
        query.setParameter("id", id);
        try {
            idNo = (PrmsPurchaseRequest) query.getResultList().get(0);

            return idNo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsPurchaseRequest getprNoLast() {
        Query query = em.createNamedQuery("PrmsPurchaseRequest.findByIdMax");
        System.out.println("size p " + query.getResultList().size());
        PrmsPurchaseRequest exchange = null;
        try {
            if (query.getResultList().size() > 0) {
                exchange = (PrmsPurchaseRequest) query.getResultList().get(0);
            }
            return exchange;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HrDepartments getSelectDepartement(int departmentid) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", departmentid);
        try {
            HrDepartments selectdepartment = (HrDepartments) query.getResultList().get(0);
            return selectdepartment;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

//    public List<FmsOperatingBudget> getOnlyApprovedBudgetCode(HrDepartments departments) {
//        Query query = em.createNamedQuery("FmsOperatingBudget.findByDepIds");
//        query.setParameter("depId", departments.getDepId());
//
//        try {
//            List<FmsOperatingBudget> budgetCodeList = new ArrayList<>(query.getResultList());
//            System.out.println("choosed name==" + departments.getDepName() + " ==id==" + departments.getDepId());
//            return budgetCodeList;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }
//    public FmsOperatingBudget autoGettingBudgetAmount(FmsOperatingBudget fmsOperatingBudget) {
//        System.out.println("getting budget amount automatically");
//        Query queries = em.createNamedQuery("FmsOperatingBudget.findByOperatingBudgetId");
//        queries.setParameter("operatingBudgetId", fmsOperatingBudget.getOperatingBudgetId());
//        try {
//            if (queries.getResultList().size() > 0) {
//                FmsOperatingBudget budgetAmounts = (FmsOperatingBudget) (queries.getResultList().get(0));
//                System.out.println("approved===" + budgetAmounts);
//                return budgetAmounts;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public List<FmsOperatingBudget> listBCByDepId(FmsOperatingBudget fmsOperatingBudget) {
//        System.out.println("List of Budgetcode By those have same dep Id");
//        Query queries = em.createNamedQuery("FmsOperatingBudget.listofBudgetCodeWithDep");
//        queries.setParameter("operatingBudgetCode", fmsOperatingBudget.getOperatingBudgetCode());
//        System.out.println("1");
//        try {
//            List<FmsOperatingBudget> codeList = new ArrayList<>(queries.getResultList());
//            System.out.println("in number" + codeList.size());
//            return codeList;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public PrmsMarketAssmntService getUP(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        System.out.println("to find Up");
        Query ques = em.createNamedQuery("PrmsMarketAssmntService.findOtherByServName");
        System.out.println("20");
        ques.setParameter("serviceId", prmsServiceAndWorkReg);
        System.out.println("21");

        try {
            PrmsMarketAssmntService up = null;
            if (ques.getResultList().size() > 0) {
                up = (PrmsMarketAssmntService) (ques.getResultList().get(0));
                System.out.println("22==" + up);

            }
            return up;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<PrmsPurchaseRequest> getPurchaseReqOnLists() {
        Query query = em.createNamedQuery("PrmsPurchaseRequest.findByRequestForApproval", PrmsPurchaseRequest.class);
        ArrayList<PrmsPurchaseRequest> purchaseRequestList = new ArrayList(query.getResultList());
        return purchaseRequestList;
    }

    public PrmsMarketAssmntService newSelectedWorkName(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        System.out.println("w/n work name changed");
        Query quert = em.createNamedQuery("PrmsMarketAssmntService.findOtherByServName");
        quert.setParameter("serviceId", prmsServiceAndWorkReg);
        System.out.println("Serv ID===" + prmsServiceAndWorkReg);

        try {
            System.out.println("1==" + prmsServiceAndWorkReg.getServAndWorkId());
            if (quert.getResultList().size() > 0) {
                System.out.println("2==" + prmsServiceAndWorkReg.getWorkName());
                PrmsMarketAssmntService changedWorkName = (PrmsMarketAssmntService) (quert.getResultList().get(0));
                System.out.println("new selected id==>" + prmsServiceAndWorkReg.getServAndWorkId());

                System.out.println("its UM" + prmsServiceAndWorkReg.getUnitMeasures());
                System.out.println("its Esti. UP" + changedWorkName);
                return changedWorkName;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public PrmsMarketAssmntService getOthersBySerName(PrmsMarketAssmntService prmsMarketAssmntService) {
        Query que = em.createNamedQuery("PrmsMarketAssmntService.findOtherByServName", PrmsMarketAssmntService.class);
        que.setParameter("serviceId", prmsMarketAssmntService.getServiceId());
        try {
            if (que.getResultList().size() > 0) {
                PrmsMarketAssmntService servNames = (PrmsMarketAssmntService) (que.getResultList().get(0));
                return servNames;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    public ArrayList<PrmsPurchaseRequest> searchPurchaseReNo() {
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER\n"
                + "FROM PRMS_PURCHASE_REQUEST\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST_DET\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQUEST_DET.PURCHASE_REQ_ID\n"
                + "INNER JOIN MMS_ITEM_REGISTRATION\n"
                + "ON MMS_ITEM_REGISTRATION.MATERIAL_ID = PRMS_PURCHASE_REQUEST_DET.MATERIAL_CODE_ID\n"
                + "WHERE PRMS_PURCHASE_REQUEST.STATUS   = 3", PrmsPurchaseRequest.class);
        try {
            ArrayList<PrmsPurchaseRequest> purchaseRequestList = new ArrayList(query.getResultList());
            return purchaseRequestList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

    public List<PrmsPurchaseRequest> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_PURCHASE_REQUEST') \n"
                + "and column_name not in ('ID','DOCUMENT_ID','FILE_REF_NUMBER','REQSTR_DEP_ID','DATE_APPROVED','REMARK','STATUS','REQ_ID')");
        try {
            List<PrmsPurchaseRequest> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
