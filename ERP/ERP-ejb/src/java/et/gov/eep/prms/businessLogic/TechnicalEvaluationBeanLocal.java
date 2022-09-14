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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface TechnicalEvaluationBeanLocal {

    public void create(PrmsThechnicalEvaluation prmsThechnicalEvaluation);

    public void update(PrmsThechnicalEvaluation prmsThechnicalEvaluation);

    public void deleteThechincalEvaluationDet(PrmsThechincalEvaluationDet prmsThechincalEvaluationDet);

    public List<PrmsThechnicalEvaluation> searchByEvaluationNo(PrmsThechnicalEvaluation prmsThechnicalEvaluation);

    public List<PrmsBidDetail> getBidNo();

    public List<PrmsQuotation> getQuotationNo();

    public PrmsBidDetail getPrlst(String toString);

    public List<PrmsBidDetail> getRequestList(String prNumber);

    public List<PrmsPreminilaryEvalutionDt> getSupplierCode(String code);

    public PrmsThechnicalEvaluation getSelectedRequest(BigDecimal id);

    public PrmsThechnicalEvaluation LastCheckListNo();

    public List<PrmsBidDetail> getItemList(String toString);

    public List<PrmsPreminilaryEvalutionDt> getPrelinmarList(String toString);

    public PrmsSpecification getItemSpecficationList(String specficationLists);

    public List<PrmsQuotationDetail> getItemListFromQuotation(String quotationItemLists);

    public List<PrmsQuotationDetail> getQuotationDEtailList(String QuotationNo);

    public PrmsSupplierSpecificationDt getSupplierSpecficationList(MmsItemRegistration itemRegistration);

    public List<PrmsBidDetail> getBidListItem(String bidNo);

    public PrmsSupplierSpecificationDt getSupplierSpecification(String suppliercode, Integer itemRegId);

    public ArrayList<PrmsBid> BidNoFormPiliminaryEvaluation();

    public ArrayList<PrmsBid> BidNoFormPliminary();

    public List<PrmsThechnicalEvaluation> getTechinicalEvalList();

    public List<PrmsThechnicalEvaluation> searchByEvalNo(PrmsThechnicalEvaluation prmsThechnicalEvaluation);

    public List<PrmsThechnicalEvaluation> getPurchaseReqOnLists();

    public ArrayList<PrmsSupplyProfile> getsupplierNameFromResult(PrmsBid bidNo);

    public ArrayList<PrmsPreminilaryEvalutionDt> getsupplierNameF(PrmsBid bidNo);

    public ArrayList<PrmsSupplyProfile> getsupplierName(PrmsBid bidNo);

    public List<PrmsThechnicalEvaluation> getParamNameList();
}
