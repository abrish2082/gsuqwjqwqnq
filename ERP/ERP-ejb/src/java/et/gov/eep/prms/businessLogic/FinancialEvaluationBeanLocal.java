/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvaluation;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import et.gov.eep.prms.entity.PrmsThechnicalEvaluation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface FinancialEvaluationBeanLocal {

    public void save(PrmsFinancialEvaluation prmsFinancialEvaluation);

    public void update(PrmsFinancialEvaluation prmsFinancialEvaluation);

    public List<PrmsQuotation> getProformaList();

//    public List<PrmsThechincalEvaluationDet> getSuppCode();
    public List<PrmsThechnicalEvaluation> getItemCode();

    public MmsItemRegistration ItemName(String toString);

    public List<PrmsThechnicalEvaluation> getItemCod();

    public PrmsThechnicalEvaluation ItemNam(String itemName);

    public PrmsBid getBidType(String toString);

    public List<PrmsThechnicalEvaluation> getItemList(String toString);

    public List<PrmsThechincalEvaluationDet> getSupCode(String toString);

    public PrmsFinancialEvaluation getSelectedRequest(String id);

    public List<PrmsFinancialEvaluation> searchFinacialByNo(PrmsFinancialEvaluation prmsFinancialEvaluation);

    public List<PrmsPortEntry> getShipKind();

    public List<PrmsBid> findAll();

    public List<PrmsBidDetail> getByBidCode(Object newValue);

    public List<PrmsThechincalEvaluationDet> getBidNum();

    public List<PrmsThechincalEvaluationDet> getSuppCode(String toString);

    public List<PrmsBid> getBidNoList();

    public List<PrmsThechincalEvaluationDet> getItemListInProforma(String refNo);

    public List<PrmsQuotationDetail> getItemInQuatation(String toString);

    public List<PrmsBidDetail> getAwardType(String refNo);

    public PrmsBidDetail getPassLimit(String refNo);

    public List<PrmsBidDetail> getQuatittyFrmBdl(String toString);

    public List<PrmsFinancialEvaluation> getFinancialOnList();

    public List<FmsLuCurrency> getCurrency();

    public String getfinancialNoSeq();

    public List<PrmsFinancialEvaluation> getParamNameList();

}
