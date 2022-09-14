/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.prms.entity.PrmsSupplierSpecificationDt;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import et.gov.eep.prms.entity.PrmsThechnicalEvaluation;
import javax.ejb.Stateless;
import et.gov.eep.prms.mapper.PrmsThechnicalEvaluationFacade;
import et.gov.eep.prms.mapper.PrmsThechincalEvaluationDetFacade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author user
 */
@Stateless
public class TechnicalEvaluationBean implements TechnicalEvaluationBeanLocal {

    @EJB
    PrmsThechnicalEvaluationFacade prmsThechnicalEvaluationFacade;
    @EJB
    PrmsThechincalEvaluationDetFacade prmsThechincalEvaluationDetFacade;

    @Override
    public void create(PrmsThechnicalEvaluation prmsThechnicalEvaluation) {
        prmsThechnicalEvaluationFacade.create(prmsThechnicalEvaluation);
    }

    @Override
    public void update(PrmsThechnicalEvaluation prmsThechnicalEvaluation) {
        prmsThechnicalEvaluationFacade.edit(prmsThechnicalEvaluation);
    }

    @Override
    public void deleteThechincalEvaluationDet(PrmsThechincalEvaluationDet prmsThechincalEvaluationDet) {
        prmsThechincalEvaluationDetFacade.remove(prmsThechincalEvaluationDet);

    }

    @Override
    public List<PrmsThechnicalEvaluation> searchByEvaluationNo(PrmsThechnicalEvaluation prmsThechnicalEvaluation) {
        return prmsThechnicalEvaluationFacade.searchByEvaluationNo(prmsThechnicalEvaluation);
    }

    @Override
    public List<PrmsBidDetail> getBidNo() {
        return prmsThechnicalEvaluationFacade.getBidNo();
    }

    @Override
    public PrmsBidDetail getPrlst(String toString) {

        return prmsThechnicalEvaluationFacade.getPrLsts(toString);
    }

    @Override
    public List<PrmsBidDetail> getRequestList(String prNumber) {
        return prmsThechnicalEvaluationFacade.getAwardDetailList(prNumber);
    }

    @Override
    public PrmsThechnicalEvaluation getSelectedRequest(BigDecimal id) {
        return prmsThechnicalEvaluationFacade.getSelectedRequest(id);
    }

    @Override
    public PrmsThechnicalEvaluation LastCheckListNo() {
        return prmsThechnicalEvaluationFacade.LastCheckListNo();
    }

    @Override
    public List<PrmsPreminilaryEvalutionDt> getSupplierCode(String code) {
        return prmsThechnicalEvaluationFacade.getSupplierCode(code);
    }

    @Override
    public List<PrmsQuotation> getQuotationNo() {
        return prmsThechnicalEvaluationFacade.getQuotationNo();
    }

    @Override
    public List<PrmsBidDetail> getItemList(String toString) {
        return prmsThechnicalEvaluationFacade.getItemList(toString);
    }

    @Override
    public List<PrmsPreminilaryEvalutionDt> getPrelinmarList(String toString) {
        return prmsThechnicalEvaluationFacade.getPrelinmarList(toString);
    }

    @Override
    public PrmsSpecification getItemSpecficationList(String specficationLists) {
        return prmsThechnicalEvaluationFacade.getItemSpecficationList(specficationLists);
    }

    @Override
    public List<PrmsQuotationDetail> getItemListFromQuotation(String quotationItemLists) {
        return prmsThechnicalEvaluationFacade.getItemListFromQuotation(quotationItemLists);
    }

    @Override
    public List<PrmsQuotationDetail> getQuotationDEtailList(String QuotationNo) {
        return prmsThechnicalEvaluationFacade.getQuotationDEtailList(QuotationNo);
    }

    @Override
    public PrmsSupplierSpecificationDt getSupplierSpecficationList(MmsItemRegistration itemRegistration) {
        return prmsThechnicalEvaluationFacade.getSupplierSpecficationList(itemRegistration);
    }

    @Override
    public List<PrmsBidDetail> getBidListItem(String bidNo) {
        return prmsThechnicalEvaluationFacade.getBidListItem(bidNo);
    }

    @Override
    public PrmsSupplierSpecificationDt getSupplierSpecification(String suppliercode, Integer itemRegId) {
        return prmsThechnicalEvaluationFacade.getSupplierSpecification(suppliercode, itemRegId);
    }

    @Override
    public ArrayList<PrmsBid> BidNoFormPiliminaryEvaluation() {
        return prmsThechnicalEvaluationFacade.BidNoFormPiliminaryEvaluation();
    }

    @Override
    public ArrayList<PrmsBid> BidNoFormPliminary() {
        return prmsThechnicalEvaluationFacade.BidNoFormPliminary();
    }

    @Override
    public List<PrmsThechnicalEvaluation> getTechinicalEvalList() {
      return  prmsThechnicalEvaluationFacade.getTechinicalEvalList();
    }

    @Override
    public List<PrmsThechnicalEvaluation> searchByEvalNo(PrmsThechnicalEvaluation prmsThechnicalEvaluation) {
        return prmsThechnicalEvaluationFacade.searchByEvalNo(prmsThechnicalEvaluation);
    }
      public List<PrmsThechnicalEvaluation> getPurchaseReqOnLists() {
        return prmsThechnicalEvaluationFacade.getPurchaseReqOnLists();
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsupplierNameFromResult(PrmsBid bidNo) {
       return prmsThechnicalEvaluationFacade.getsupplierNameFromResult(bidNo);
    }

    @Override
    public ArrayList<PrmsPreminilaryEvalutionDt> getsupplierNameF(PrmsBid bidNo) {
          return prmsThechnicalEvaluationFacade.getsupplierNameF(bidNo);
    }

    @Override
    public ArrayList<PrmsSupplyProfile> getsupplierName(PrmsBid bidNo) {
       return prmsThechnicalEvaluationFacade.getsupplierName(bidNo);
    }

    @Override
    public List<PrmsThechnicalEvaluation> getParamNameList() {
        return prmsThechnicalEvaluationFacade.getParamNameList();
    }
    
}
