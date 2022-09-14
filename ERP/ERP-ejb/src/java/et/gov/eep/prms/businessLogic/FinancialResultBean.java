/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidDetail;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsThechincalEvaluationDet;
import et.gov.eep.prms.mapper.PrmsFinancialEvalResultFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class FinancialResultBean implements FinancialResultBeanLocal {

    @EJB
    PrmsFinancialEvalResultFacade financialEvalResultFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;

    @Override
    public void create(PrmsFinancialEvalResult financialEvalResult) {
        financialEvalResultFacade.create(financialEvalResult);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void edit(PrmsFinancialEvalResult prmsFinancialEvalResult) {
        financialEvalResultFacade.edit(prmsFinancialEvalResult);
    }

    @Override
    public List<PrmsBid> getBidNoList() {
        return financialEvalResultFacade.getBidNo();
    }

    @Override
    public List<PrmsQuotation> getProformaList() {
        return financialEvalResultFacade.getProformaNum();
    }

    @Override
    public List<MmsItemRegistration> getItemInQuatation(String proformaNum) {
        return financialEvalResultFacade.getProfomaNumList(proformaNum);
    }

    @Override
    public List<PrmsQuotationDetail> getSupplierList(String supplierName) {
        return financialEvalResultFacade.getSupplierList(supplierName);
    }

    @Override
    public List<PrmsBidDetail> getItemLotPack() {
        return financialEvalResultFacade.getItemLotPack();
    }

    @Override
    public List<PrmsFinancialEvaluaDetail> getLotNum(PrmsBidDetail bidDetail) {
        return financialEvalResultFacade.getLotNm(bidDetail);
    }

    @Override
    public List<PrmsBidDetail> getItemBase() {
        return financialEvalResultFacade.getItemBase();
    }

    @Override
    public List<PrmsFinancialEvaluaDetail> getItemBaseRank(String suppRank) {
        return financialEvalResultFacade.getItemBaseRank(suppRank);
    }
        @Override
    public List<PrmsFinancialEvalResult> searchFinancialResult(PrmsFinancialEvalResult prmsFinancialEvalResult) {
        return financialEvalResultFacade.getFinancialResult(prmsFinancialEvalResult);
    }

    @Override
    public List<PrmsBidDetail> getBidDetailListForItemBased(PrmsBid bid) {
        return financialEvalResultFacade.getBidDetailInfoForItemBased(bid);
    }

    @Override
    public List<PrmsFinancialEvaluaDetail> getItemsForCompliance(PrmsBidDetail bidDetail) {
        return financialEvalResultFacade.getItemsForCompliance(bidDetail);
    }

    @Override
    public List<PrmsBidDetail> getBidDetailListForLotBased(PrmsBid bid) {
        return financialEvalResultFacade.getBidDetailInfoForLotBased(bid);
    }

    @Override
    public List<PrmsBidDetail> getBidDetailListInfoForLotBased(PrmsBid bid, String lotNo) {
        return financialEvalResultFacade.getBidDetailInfoMaterialForLotBased(bid, lotNo);
    }

    @Override
    public List<PrmsFinancialEvaluaDetail> getItemsForMerit(PrmsBidDetail bidDetail) {
        return financialEvalResultFacade.getItemsForMerit(bidDetail);
    }

    @Override
    public List<PrmsThechincalEvaluationDet> getItemsForMeritForTech(PrmsBidDetail bidDetail) {
        return financialEvalResultFacade.getItemsForMeritForTech(bidDetail);
    }

    @Override
    public List<MmsItemRegistration> getItemBasedItemName(MmsItemRegistration mmsItemRegistration) {
        return financialEvalResultFacade.getItemBasedItemName(mmsItemRegistration);
    }

    @Override
    public List<PrmsBidDetail> getItemBasedList(PrmsBid prmsBid) {
        return financialEvalResultFacade.getItemBasedList(prmsBid);
    }

    @Override
    public List<PrmsBid> getBidNoFrItemBased() {
        return financialEvalResultFacade.getBidNoFrItemBased();
    }

    @Override
    public List<PrmsSupplyProfile> getSupplerId(PrmsFinancialEvaluaDetail prmsFinancialEvaluaDetail) {
        return financialEvalResultFacade.getsupplierId(prmsFinancialEvaluaDetail);
    }

    @Override
    public List<String> getEvalutionResult(PrmsBid prmsBidDetail) {
        return financialEvalResultFacade.getEvalutionResult(prmsBidDetail);
    }

    @Override
    public List<PrmsFinancialEvaluaDetail> getListofUnitPrice(PrmsBidDetail prmsBidDetail, PrmsSupplyProfile prmsSupplyProfile) {
        return financialEvalResultFacade.getListofUnitPrice(prmsBidDetail, prmsSupplyProfile);
    }

    @Override
    public List<PrmsFinancialEvaluaDetail> getListOfSpplierPrice(PrmsBid prmsBid, PrmsSupplyProfile prmsSupplyProfile) {
        return financialEvalResultFacade.getListOfSpplierPrice(prmsBid, prmsSupplyProfile);
    }

    @Override
    public List<PrmsFinancialEvalResult> getFinancialOnList() {
        return financialEvalResultFacade.getFinancialOnList();
    }

    @Override
    public List<PrmsServiceAndWorkReg> getServiceFrmQuotation(String ServiceName) {
        return financialEvalResultFacade.getServiceFrmQuotation(ServiceName);
    }

    @Override
    public List<PrmsQuotationDetail> getSupplierListFrService(String serviceName) {
        return financialEvalResultFacade.getSupplierListFrService(serviceName);
    }

    @Override
    public List<PrmsServiceAndWorkReg> getWorkFrmQuotation(String workName) {
        return financialEvalResultFacade.getWorkFrmQuotation(workName);
    }

    @Override
    public List<PrmsBidDetail> getItemBasedServiceList(PrmsBid prmsBid) {
        return financialEvalResultFacade.getItemBasedServiceList(prmsBid);
    }

    @Override
    public List<PrmsBidDetail> getItemBasedWorkList(PrmsBid prmsBid) {
        return financialEvalResultFacade.getItemBasedWorkList(prmsBid);
    }

    
    @Override
    public String getfinancialResltNoSeq() {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String finanResultNum = null;
        String prefix = "FIN-RESULT-NO";
        int maxNo = 0;
        List<PrmsFinancialEvalResult> financialEvalResultList = financialEvalResultFacade.getfinancialResltNumSeq(prefix, eYear);
        for (int rowCount = 0; rowCount < financialEvalResultList.size(); rowCount++) {
            finanResultNum = financialEvalResultList.get(rowCount).getFinancialResultNo();
            String[] lastLcRegNoNos = finanResultNum.split("-");
            String lastDatesPatern = lastLcRegNoNos[2];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increament = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increament) {
                maxNo = increament;
            }
        }
        maxNo = maxNo + 1;
        finanResultNum = "FIN-RESULT-NO-" + maxNo + "/" + eYear;
        return finanResultNum;
    }

    @Override
    public List<PrmsFinancialEvalResult> getParamNameList() {
        return financialEvalResultFacade.getParamNameList();
    }
}
