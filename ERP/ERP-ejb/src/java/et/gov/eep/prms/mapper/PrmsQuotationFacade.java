/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import java.math.BigDecimal;
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
public class PrmsQuotationFacade extends AbstractFacade<PrmsQuotation> {

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
    public PrmsQuotationFacade() {
        super(PrmsQuotation.class);
    }

//    public ArrayList<PrmsQuotation> searchPrmsQuotation(PrmsQuotation prmsQuotation) {
//
//        Query query = em.createNamedQuery("PrmsQuotation.SearchfindByQuotationNo");
//        query.setParameter("quotationNo", prmsQuotation.getQuotationNo() + "%");
//        query.setParameter("preparedBy", prmsQuotation.getPreparedBy());
//
//        try {
//            ArrayList<PrmsQuotation> CheckList = new ArrayList(query.getResultList());
//
//            return CheckList;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }
    /**
     *
     * @param prmsQuotation
     * @return
     */
    public List<PrmsQuotation> searchPrmsQuotation(PrmsQuotation prmsQuotation) {

        List<PrmsQuotation> quotationLst = new ArrayList<>();
        if (prmsQuotation.getColumnName() != null && !prmsQuotation.getColumnName().equals("")
                && prmsQuotation.getColumnValue() != null && !prmsQuotation.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_QUOTATION "
                    + "WHERE " + prmsQuotation.getColumnName().toLowerCase() + " = '" + prmsQuotation.getColumnValue() + "'"
                    + "and " + prmsQuotation.getPreparedBy() + " = '" + prmsQuotation.getPreparedBy() + "'", PrmsQuotation.class);
            try {
                if (query.getResultList().size() > 0) {
                    quotationLst = query.getResultList();
                }
                return quotationLst;
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                return null;

            }
        } else {
            Query query = em.createNamedQuery("PrmsQuotation.findByPreparedBy");
            query.setParameter("preparedBy", prmsQuotation.getPreparedBy());
            quotationLst = query.getResultList();
            return quotationLst;
        }

    }

    /**
     *
     * @return
     */
    public ArrayList<PrmsPurchaseRequest> searchPurchaseReNo() {

        Query query = em.createNamedQuery("PrmsPurchaseRequest.findAll");

        try {
            ArrayList<PrmsPurchaseRequest> purchaseRequestList = new ArrayList(query.getResultList());
            return purchaseRequestList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseReqService> getPrService() {
        List<PrmsPurchaseReqService> itemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseReqService.findByPrNumber", PrmsPurchaseReqService.class);
            itemLst = (List<PrmsPurchaseReqService>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return itemLst;
    }

    public List<PrmsPurchaseRequest> getPurchaseRequestList() {

        List<PrmsPurchaseRequest> requests = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseRequest.findAll", PrmsPurchaseRequest.class);

            requests = (List<PrmsPurchaseRequest>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;

    }

    public List<PrmsPurchaseRequestDet> getItemCode() {
        List<PrmsPurchaseRequestDet> itemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findByItemCod", PrmsPurchaseRequestDet.class);
            itemLst = (List<PrmsPurchaseRequestDet>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return itemLst;
    }

    public List<PrmsPurchaseRequestDet> getBidDates(String itemName) {

        List<PrmsPurchaseRequestDet> servicename = null;
        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findByItem", PrmsPurchaseRequestDet.class);
        query.setParameter("materialCodeId", itemName);
        try {
            servicename = (List<PrmsPurchaseRequestDet>) query.getResultList();

            return servicename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsPurchaseRequestDet> getItemList(PrmsPurchaseRequestDet prmsPurchaseRequest) {
        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findByItemCode", PrmsPurchaseRequest.class);
        query.setParameter("materialCodeId", prmsPurchaseRequest.getId());
        try {
            ArrayList<PrmsPurchaseRequestDet> itemList = new ArrayList(query.getResultList());
            return itemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsPurchaseRequestDet> prNo(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findByprNumber", PrmsPurchaseRequestDet.class);
        query.setParameter("id", prmsPurchaseRequestDet.getPurchaseReqId().getId().toString());
        try {
            ArrayList<PrmsPurchaseRequestDet> hrBranchInfo = new ArrayList(query.getResultList());
            return hrBranchInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsPurchaseRequestDet> getNumber(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findAll");

        try {

            ArrayList<PrmsPurchaseRequestDet> eepVendorRegList = new ArrayList(query.getResultList());
            return eepVendorRegList;
        } catch (Exception e) {
            e.printStackTrace();;
            return null;
        }

    }

    public PrmsPurchaseRequest getPrList(PrmsPurchaseRequest prmsPurchaseRequest) {
        Query query = em.createNamedQuery("PrmsPurchaseRequest.findByPrNumbers");
        query.setParameter("prNumber", prmsPurchaseRequest.getPrNumber());
        try {
            PrmsPurchaseRequest branch = (PrmsPurchaseRequest) query.getResultList();
            return branch;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public ArrayList<PrmsPurchaseRequestDet> getPurchaseRquestNo(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findByPrNo");
        query.setParameter("purchaseReqId", prmsPurchaseRequestDet.getPurchaseReqId().toString() + '%');
        try {
            ArrayList<PrmsPurchaseRequestDet> itemList = new ArrayList(query.getResultList());
            return itemList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequestDet> getItemName() {
        List<PrmsPurchaseRequestDet> itemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findAll", PrmsPurchaseRequestDet.class);
            itemLst = (List<PrmsPurchaseRequestDet>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return itemLst;
    }

    public List<PrmsPurchaseReqService> getServicePrNo() {
        List<PrmsPurchaseReqService> itemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseReqService.findAll", PrmsPurchaseReqService.class);
            itemLst = (List<PrmsPurchaseReqService>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemLst;
    }

    public ArrayList<PrmsPurchaseRequestDet> getMaterialCode(PrmsPurchaseRequestDet prmsPurchaseRequest) {//1//

        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findByItemCode", PrmsPurchaseRequestDet.class);//thhis is pr event//
        query.setParameter("materialCodeId", prmsPurchaseRequest);
        try {
            ArrayList<PrmsPurchaseRequestDet> hrBranchInfo = new ArrayList(query.getResultList());
            return hrBranchInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequestDet> getAwardDetailList(String prNumber) {

        List<PrmsPurchaseRequestDet> awardDetailList = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findById", PrmsPurchaseRequestDet.class);
            query.setParameter("id", prNumber);
            awardDetailList = (List<PrmsPurchaseRequestDet>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return awardDetailList;
    }

    public List<PrmsPurchaseRequestDet> getmatCode() {

        List<PrmsPurchaseRequestDet> itemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findAll", PrmsPurchaseRequestDet.class);
            itemLst = new ArrayList<>(query.getFirstResult());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return itemLst;
    }

    public PrmsQuotation getSelectedRequest(BigDecimal id) {
        Query query = em.createNamedQuery("PrmsQuotation.findByQuatationId");
        query.setParameter("quatationId", id);

        try {
            PrmsQuotation selectrequest = (PrmsQuotation) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequestDet> findAllRequestDet() {
        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findAll");
        try {
            List<PrmsPurchaseRequestDet> RequestDet = new ArrayList(query.getResultList());
            return RequestDet;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public PrmsPurchaseRequestDet purchaseRequstNo(PrmsPurchaseRequestDet PrmsPurchaseRequestDet) {
        Query query = em.createNamedQuery("PrmsPurchaseRequestDet.findById");
        query.setParameter("id", PrmsPurchaseRequestDet.getId());
        try {
            PrmsPurchaseRequestDet branch = (PrmsPurchaseRequestDet) query.getResultList();
            return branch;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsQuotation LastQuotationNo() {
        Query query = em.createNamedQuery("PrmsQuotation.findByMaxQuotationNum");
        try {
            PrmsQuotation CheckListNo = new PrmsQuotation();
            if (query.getResultList().size() > 0) {
                CheckListNo = (PrmsQuotation) query.getResultList().get(0);
            }
            return CheckListNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsLuVatTypeLookup> getVat() {
        List<PrmsLuVatTypeLookup> itemLst = null;
        try {
            Query query = em.createNamedQuery("PrmsVatTypeLookup.findAll", PrmsLuVatTypeLookup.class);
            itemLst = (List<PrmsLuVatTypeLookup>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return itemLst;
    }

    public PrmsSupplyProfile getSupplierVendor(PrmsSupplyProfile prmsSupplyProfile) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findByVendName");
        query.setParameter("vendorName", prmsSupplyProfile.getVendorName());
        try {
            PrmsSupplyProfile eepBidRegList = (PrmsSupplyProfile) (query.getResultList().get(0));
            return eepBidRegList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsSupplyProfile findBySpplier(PrmsLuVatTypeLookup prmsVatTypeLookup) {
        Query query = em.createNamedQuery("PrmsSupplyProfile.findByVatTypeID", PrmsSupplyProfile.class);
        query.setParameter("vatTypeId", prmsVatTypeLookup.getVatTypeId());
        try {
            return (PrmsSupplyProfile) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsServiceAndWorkReg findById(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        Query query = em.createNamedQuery("PrmsServiceAndWorkReg.findByServAndWorkId");
        query.setParameter("servAndWorkId", prmsServiceAndWorkReg.getServAndWorkId());
        try {
            return (PrmsServiceAndWorkReg) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequest> getPershaseRquest() {
        List<PrmsPurchaseRequest> suppLierList = null;
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "PRMS_PURCHASE_REQUEST.ID \n"
                + " PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID\n"
                + "FROM PRMS_PURCHASE_REQ_SERVICE\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQ_SERVICE.PURCHASE_REQ_ID", PrmsPurchaseRequest.class
        );
        try {

            suppLierList = (List<PrmsPurchaseRequest>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return suppLierList;
    }

    public ArrayList<PrmsPurchaseRequest> getPrNumber() {

        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_REQUEST.ID,\n"
                + "  PRMS_PURCHASE_REQUEST.PR_NUMBER,\n"
                + "  PRMS_PURCHASE_REQUEST_DET.PURCHASE_REQ_ID,\n"
                + "  PRMS_PURCHASE_REQUEST_DET.MATERIAL_CODE_ID\n"
                + "FROM PRMS_PURCHASE_REQUEST_DET\n"
                + "INNER JOIN PRMS_PURCHASE_REQUEST\n"
                + "ON PRMS_PURCHASE_REQUEST.ID = PRMS_PURCHASE_REQUEST_DET.PURCHASE_REQ_ID", PrmsPurchaseRequest.class);
        try {
            ArrayList<PrmsPurchaseRequest> prnumber = new ArrayList<>();
            prnumber = new ArrayList<>(query.getResultList());
            System.err.println("hhhh" + prnumber.size());
            return prnumber;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequest> getPrList() {
//        Query que = em.createNamedQuery("PrmsPurchaseRequest.findByPrNumForGoods");
//        try {
//            List<PrmsPurchaseRequest> prList = new ArrayList<>(que.getResultList());
//            return prList;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        try {
            Query query = em.createNativeQuery("SELECT * FROM Prms_Purchase_Request p WHERE p.purchase_Type ='Goods' ", PrmsPurchaseRequest.class);
            List<PrmsPurchaseRequest> prList = (List<PrmsPurchaseRequest>) query.getResultList();
            return prList;
//            return (List<PrmsSupplyProfile>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchaseRequest> getPrListForService() {
        Query que = em.createNamedQuery("PrmsPurchaseRequest.findByPrNumForService");
        try {
            List<PrmsPurchaseRequest> prList = new ArrayList<>(que.getResultList());
            return prList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public List<PrmsPurchaseRequest> getPrListForWorks() {
//        Query que = em.createNamedQuery("PrmsPurchaseRequest.findByPrNumForWorks");//Works
//        this change is for work case's
        try {
//            List<PrmsPurchaseRequest> prList = new ArrayList<>(que.getResultList());
//            return prList;
            Query query = em.createNativeQuery("select * FROM PRMS_PURCHASE_REQUEST where purchase_Type like 'Works'", PrmsPurchaseRequest.class);
            List<PrmsPurchaseRequest> users = (List<PrmsPurchaseRequest>) query.getResultList();
            return users;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @param prmsQuotation
     * @return
     * ************************************************************************
     */
    public List<PrmsSupplyProfile> FindBySuppID(PrmsQuotation prmsQuotation) {
        try {

            Query query = em.createNativeQuery("select psp.vendor_name,psp.id "
                    + "from prms_quotation pq inner join prms_quotation_detail pqd"
                    + " on pq.quatation_id=pq.quatation_id inner join\n"
                    + "prms_financial_evl_resulty_dtl pfd on "
                    + "pfd.qutation_detail_id=pqd.quat_det_id \n"
                    + "inner join prms_supply_profile psp on psp.id=pfd.supplier_id "
                    + "where pq.quatation_id='"
                    + prmsQuotation.getQuatationId() + "' ", PrmsSupplyProfile.class);
            List<PrmsSupplyProfile> users = (List<PrmsSupplyProfile>) query.getResultList();
            return users;
//            return (List<PrmsSupplyProfile>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * -------------------------------------------------------------------------
     *
     * @param prmsQuotation
     * @return
     * -----------------------------------------------------------------------
     */
    public ArrayList<PrmsQuotation> searchPrmsQuotation_(PrmsQuotation prmsQuotation) {

        Query query = em.createNamedQuery("PrmsQuotation.SearchfindByQuotationNoStatus");
        query.setParameter("quotationNo", prmsQuotation.getQuotationNo() + "%");
        query.setParameter("preparedBy", prmsQuotation.getPreparedBy());

        try {
            ArrayList<PrmsQuotation> CheckList = new ArrayList(query.getResultList());

            return CheckList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public ArrayList<PrmsQuotation> searchPrmsQuotation() {

        Query query = em.createNamedQuery("PrmsQuotation.SearchfindByQuotationNoStatus");

        try {
            ArrayList<PrmsQuotation> CheckList = new ArrayList(query.getResultList());

            return CheckList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsQuotation> getColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_cols\n"
                + "where table_name =UPPER('PRMS_QUOTATION') \n"
                + "and column_name not in ('QUATATION_ID','REMARK','STATUS','DOCID','COMMITTEE_ID','COMMITTEE_MEMBERS','DOCUMENT_ID','PURCHASER')"
                + "order by column_name ASC");
        try {
            List<PrmsQuotation> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
