/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
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
import et.gov.eep.prms.mapper.PrmsBidFacade;
import et.gov.eep.prms.mapper.PrmsFinancialEvaluationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class FinancialEvaluationBean implements FinancialEvaluationBeanLocal {

    @EJB
    PrmsFinancialEvaluationFacade financialEvaluationFacade;
    @EJB
    PrmsBidFacade prmsBidFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;

    @Override
    public void save(PrmsFinancialEvaluation prmsFinancialEvaluation) {
        financialEvaluationFacade.create(prmsFinancialEvaluation);
    }

    @Override
    public void update(PrmsFinancialEvaluation prmsFinancialEvaluation) {
        financialEvaluationFacade.edit(prmsFinancialEvaluation);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<PrmsQuotation> getProformaList() {
        return financialEvaluationFacade.getProformaNo();
    }

//    @Override
//    public List<PrmsThechincalEvaluationDet> getSuppCode() {
//        return financialEvaluationFacade.findAll();
//    }
    @Override
    public List<PrmsThechnicalEvaluation> getItemCode() {
        return financialEvaluationFacade.getItemCode();
    }

    @Override
    public MmsItemRegistration ItemName(String itemName) {
        return financialEvaluationFacade.getItemName(itemName);
    }

    @Override
    public List<PrmsThechnicalEvaluation> getItemCod() {
        return financialEvaluationFacade.getimCode();

    }

    @Override
    public PrmsThechnicalEvaluation ItemNam(String itemName) {
        return financialEvaluationFacade.getItemNam(itemName);
    }

    @Override
    public PrmsBid getBidType(String bidType) {
        return financialEvaluationFacade.getBidType(bidType);
    }

    @Override
    public List<PrmsThechnicalEvaluation> getItemList(String ItemList) {
        return financialEvaluationFacade.getItemList(ItemList);
    }

    @Override
    public List<PrmsThechincalEvaluationDet> getSupCode(String supCode) {
        return financialEvaluationFacade.getSupCod(supCode);
    }

    @Override
    public PrmsFinancialEvaluation getSelectedRequest(String id) {
        return financialEvaluationFacade.getFinancialId(id);
    }

    @Override
    public List<PrmsFinancialEvaluation> searchFinacialByNo(PrmsFinancialEvaluation prmsFinancialEvaluation) {
        return financialEvaluationFacade.getFinancialEvaNo(prmsFinancialEvaluation);
    }

    @Override
    public List<PrmsPortEntry> getShipKind() {
        return financialEvaluationFacade.getShipKind();
    }

    @Override
    public List<PrmsBid> findAll() {
        return prmsBidFacade.findAll();
    }

    @Override
    public List<PrmsBidDetail> getByBidCode(Object newValue) {
        return prmsBidFacade.getByBidCode(newValue);
    }

    @Override
    public List<PrmsThechincalEvaluationDet> getBidNum() {
        return financialEvaluationFacade.getBidNum();
    }

    @Override
    public List<PrmsThechincalEvaluationDet> getSuppCode(String supCode) {
        return financialEvaluationFacade.getSupCod(supCode);
    }

    @Override
    public List<PrmsBid> getBidNoList() {
        return prmsBidFacade.getBidNoList();
    }

    @Override
    public List<PrmsThechincalEvaluationDet> getItemListInProforma(String proformNum) {
        return financialEvaluationFacade.getItemListInProfrm(proformNum);
    }

    @Override
    public List<PrmsQuotationDetail> getItemInQuatation(String itemName) {
        return financialEvaluationFacade.getItemInQuoatation(itemName);
    }

    @Override
    public List<PrmsBidDetail> getAwardType(String refNo) {
        return financialEvaluationFacade.getAwardType(refNo);
    }

    @Override
    public PrmsBidDetail getPassLimit(String refNo) {
        return financialEvaluationFacade.getPassLimit(refNo);
    }

    @Override
    public List<PrmsBidDetail> getQuatittyFrmBdl(String quantity) {
        return financialEvaluationFacade.getQuantityFrmbdl(quantity);
    }

    @Override
    public List<PrmsFinancialEvaluation> getFinancialOnList() {
        return financialEvaluationFacade.getFinancialOnList();
    }

    @Override
    public List<FmsLuCurrency> getCurrency() {
        return financialEvaluationFacade.getCurrency();
    }

    @Override
    public String getfinancialNoSeq() {
       String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String finanNo = null;
        String prefix = "FINANCIAL-NO";
        int maxNo = 0;
        List<PrmsFinancialEvaluation> financialEvaluationList = financialEvaluationFacade.getfinancialNoSeq(prefix, eYear);
        for (int rowCount = 0; rowCount < financialEvaluationList.size(); rowCount++) {
            finanNo = financialEvaluationList.get(rowCount).getFinancialNo();
            String[] lastLcRegNoNos = finanNo.split("-");
            String lastDatesPatern = lastLcRegNoNos[2];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increament = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increament) {
                maxNo = increament;
            }
        }
        maxNo = maxNo + 1;
        finanNo = "FINANCIAL-NO-" + maxNo + "/" + eYear;
        return finanNo;
    }

    @Override
    public List<PrmsFinancialEvaluation> getParamNameList() {
        return financialEvaluationFacade.getParamNameList();
    }
    
}
