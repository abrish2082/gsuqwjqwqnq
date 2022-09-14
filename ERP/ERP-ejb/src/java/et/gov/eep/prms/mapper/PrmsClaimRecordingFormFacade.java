/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsLostFixedAsset;
import et.gov.eep.prms.entity.PrmsClaimRecordingForm;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsGoodsEntrance;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PrmsClaimRecordingFormFacade extends AbstractFacade<PrmsClaimRecordingForm> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsClaimRecordingFormFacade() {
        super(PrmsClaimRecordingForm.class);
    }

    public List<PrmsClaimRecordingForm> getMarketNo(PrmsClaimRecordingForm PrmsClaimRecordingForm) {
        Query query = em.createNamedQuery("PrmsClaimRecordingForm.findByClaimNos", PrmsClaimRecordingForm.class);
        query.setParameter("claimNo", PrmsClaimRecordingForm.getClaimNo() + '%');

        try {
            List<PrmsClaimRecordingForm> marketAssessmentLst = new ArrayList(query.getResultList());
            return marketAssessmentLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsClaimRecordingForm getLastClaimNo() {
        Query query = em.createNamedQuery("PrmsClaimRecordingForm.findByMaxclaimId", PrmsClaimRecordingForm.class);
        PrmsClaimRecordingForm result = null;
        try {
            if (query.getResultList().size() > 0) {
                result = (PrmsClaimRecordingForm) query.getResultList().get(0);
            }

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsClaimRecordingForm getSelectedId(BigDecimal claimId) {
        Query query = em.createNamedQuery("PrmsClaimRecordingForm.findByclaimId", PrmsClaimRecordingForm.class);
        query.setParameter("claimId", claimId);
        try {
            PrmsClaimRecordingForm idlst = (PrmsClaimRecordingForm) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrDepartments getSelectDepartement(int key) {
        Query query = em.createNamedQuery("HrDepartments.findByDepId");
        query.setParameter("depId", key);
        try {
            HrDepartments selectdepartment = (HrDepartments) query.getResultList().get(0);
            return selectdepartment;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsClaimRecordingForm> findByStatus() {
        Query query = em.createNamedQuery("PrmsClaimRecordingForm.findByStatus");
//        query.setParameter("employeeName", fmsFieldAllowanceEnty.getEmployeeName() + '%');
        try {
            ArrayList<PrmsClaimRecordingForm> faCheckedList = new ArrayList(query.getResultList());
            return faCheckedList;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<PrmsClaimRecordingForm> getClaimLists() {
        Query query = em.createNamedQuery("PrmsClaimRecordingForm.findByReqForApproval");
        ArrayList<PrmsClaimRecordingForm> Claimlst = new ArrayList<>(query.getResultList());
        return Claimlst;
    }

    public List<PrmsClaimRecordingForm> findClaimNos(PrmsClaimRecordingForm prmsClaimRecordingForm) {
        Query query = em.createNamedQuery("PrmsClaimRecordingForm.findByClaimNossss", PrmsClaimRecordingForm.class);
        query.setParameter("claimNo", prmsClaimRecordingForm.getLcNo() + '%');

        try {
            List<PrmsClaimRecordingForm> marketAssessmentLst = new ArrayList(query.getResultList());
            return marketAssessmentLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsContract) {
        System.out.println("cont id --" + prmsContract.getContractId());
//        Query query = em.createNamedQuery("PrmsContractAmendment.findByContractId");
//        query.setParameter("contractId", prmsContract.getContractId());
        Query query = em.createNativeQuery("SELECT * FROM prms_contract_amendment contamd\n"
                + "inner join(select contract_id, max (contract_amend_id) as maxContAmendId \n"
                + "from prms_contract_amendment \n"
                + "group by contract_id)contamd2\n"
                + "on contamd.contract_amend_id=contamd2.maxContAmendId\n"
                + "inner join prms_contract cont\n"
                + "on cont.contract_id=contamd.contract_id\n"
                + "where cont.contract_id='" + prmsContract.getContractId() + "'");
        try {
            List<PrmsContractAmendment> amendedContractList = new ArrayList<>();
            if (query.getResultList().size() > 0) {
                amendedContractList = query.getResultList();
                System.out.println("size -- " + amendedContractList.size());
            }
            return amendedContractList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PrmsContractAmendment getContractAmendment(PrmsContract prmsContract) {
        System.out.println("when cont id --- " + prmsContract.getContractId());
        Query q = em.createNativeQuery("SELECT * FROM prms_contract_amendment contamd\n"
                + "inner join(select contract_id, max(contract_amend_id) as maxContAmendId\n"
                + "from prms_contract_amendment \n"
                + "group by contract_id) contamd2\n"
                + "on contamd.contract_amend_id=contamd2.maxContAmendId\n"
                + "where contamd.contract_id = '" + prmsContract.getContractId() + "'", PrmsContractAmendment.class);
        try {
            PrmsContractAmendment contractAmendedInfo = new PrmsContractAmendment();
            if (q.getResultList().size() > 0) {
                contractAmendedInfo = (PrmsContractAmendment) q.getResultList().get(0);
                System.out.println("Supplier Id from Amended Cntract " + contractAmendedInfo.getSuppId());
            }
            return contractAmendedInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsClaimRecordingForm> getMmsClaimRColumnNameList() {
       Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('PRMS_CLAIM_RECORDING_FORM')\n"
                + "   and COLUMN_NAME NOT IN ('DEPARTMENT','STORE_REQ_ID','PROJECT_ID')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<PrmsClaimRecordingForm> colNameLists = new ArrayList<>();
            colNameLists = query.getResultList();
            return colNameLists;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public List<PrmsClaimRecordingForm> getClaimRListsByParameter(PrmsClaimRecordingForm prmsClaimRecordingForm) {
        List<PrmsClaimRecordingForm> colValueLists = new ArrayList<>();
        if (prmsClaimRecordingForm.getColumnName() != null && !prmsClaimRecordingForm.getColumnName().equals("")
                && prmsClaimRecordingForm.getColumnValue() != null && !prmsClaimRecordingForm.getColumnValue().equals("")) {
          
            Query query = em.createNativeQuery("SELECT * FROM PRMS_CLAIM_RECORDING_FORM\n"
                    + "   WHERE " + prmsClaimRecordingForm.getColumnName().toLowerCase() + "='" + prmsClaimRecordingForm.getColumnValue() + "'"
                    + "and " + prmsClaimRecordingForm.getPreparedBy() + "='" + prmsClaimRecordingForm.getPreparedBy() + "'", PrmsClaimRecordingForm.class);
            try {

                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsClaimRecordingForm.findByPreparedBy");
            query.setParameter("preparedBy", prmsClaimRecordingForm.getPreparedBy());
            colValueLists = query.getResultList();
            return colValueLists;
        }
    }
     public List<PrmsClaimRecordingForm> getClaimListsByParameter(PrmsClaimRecordingForm prmsClaimRecordingForm) {
         List<PrmsClaimRecordingForm> colValueLists = new ArrayList<>();
        if (prmsClaimRecordingForm.getColumnName() != null  && prmsClaimRecordingForm.getColumnValue() != null ) {
            System.out.println("when if");
            Query query = em.createNativeQuery("SELECT * FROM PRMS_CLAIM_RECORDING_FORM\n"
                    + "   WHERE " + prmsClaimRecordingForm.getColumnName().toLowerCase() + "='" + prmsClaimRecordingForm.getColumnValue() + "'"
                    + " ", PrmsClaimRecordingForm.class);
            try {

                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsClaimRecordingForm.findByPreparedBy");
            query.setParameter("PreparedBy", prmsClaimRecordingForm.getPreparedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }
    }

    public List<ColumnNameResolver> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('PRMS_CLAIM_RECORDING_FORM')\n"
                + "   and COLUMN_NAME NOT IN ('UNIT_PRICE','REMARK','DETAIL_ID','MATERIAL_ID','REQUESTED_PART_NO','DELIVERED_PART_NO','UNIT_OF_MEASURE','QUANTITY','CLAIM_DESCRIPTION','CLAIM_REQUEST_AMOUNT','COMMECIAL_INVOICE','CONTRACT_AMOUNT','DELIVERED_AMOUNT','RETURNED_CLAIM_AMOUNT','STATUS','SUPPLIER','UNDELIVERED_BALANCE')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> RetrivedColumns = new ArrayList<>();
            RetrivedColumns = query.getResultList();
            List<ColumnNameResolver> ResolvedCol_list = new ArrayList<>();
            if (RetrivedColumns.size() > 0) {
                for (int i = 0; i < RetrivedColumns.size(); i++) {
                    ColumnNameResolver obj = new ColumnNameResolver();
                    System.out.println("RetrivedColumns.get(i)===" + RetrivedColumns.get(i));
                    obj.setCol_Name_FromTable(RetrivedColumns.get(i));
                    obj.setParsed_Col_Name(ColumnParser(RetrivedColumns.get(i)).toLowerCase());
                    ResolvedCol_list.add(obj);
                }
            }
            return ResolvedCol_list;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
        
    }
     public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

}

