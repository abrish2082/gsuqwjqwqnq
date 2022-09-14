/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface FinancialResultBeanLocal {

    public void create(PrmsFinancialEvalResult financialEvalResult);

    public void edit(PrmsFinancialEvalResult prmsFinancialEvalResult);

    public List<PrmsBid> getBidNoList();

    public List<PrmsQuotation> getProformaList();

    public List<MmsItemRegistration> getItemInQuatation(String mmsItemRegistration);

    public List<PrmsQuotationDetail> getSupplierList(String toString);

    public List<PrmsBidDetail> getItemLotPack();

    public List<PrmsFinancialEvaluaDetail> getLotNum(PrmsBidDetail bidDetail);

    public List<PrmsBidDetail> getItemBase();

    public List<PrmsFinancialEvaluaDetail> getItemBaseRank(String toString);

    public List<PrmsFinancialEvalResult> searchFinancialResult(PrmsFinancialEvalResult prmsFinancialEvalResult);

    public List<PrmsBidDetail> getBidDetailListForItemBased(PrmsBid bid);

    public List<PrmsFinancialEvaluaDetail> getItemsForCompliance(PrmsBidDetail bidDetail);

    public List<PrmsBidDetail> getBidDetailListForLotBased(PrmsBid bid);

    public List<PrmsBidDetail> getBidDetailListInfoForLotBased(PrmsBid bid, String lotNo);

    public List<PrmsFinancialEvaluaDetail> getItemsForMerit(PrmsBidDetail bidDetail);

    public List<PrmsThechincalEvaluationDet> getItemsForMeritForTech(PrmsBidDetail bidDetail);

    public List<MmsItemRegistration> getItemBasedItemName(MmsItemRegistration mmsItemRegistration);

    public List<PrmsBidDetail> getItemBasedList(PrmsBid prmsBid);

    public List<PrmsBid> getBidNoFrItemBased();

    public List<PrmsSupplyProfile> getSupplerId(PrmsFinancialEvaluaDetail prmsFinancialEvaluaDetail);

    public List<String> getEvalutionResult(PrmsBid prmsBidDetail);

    public List<PrmsFinancialEvaluaDetail> getListofUnitPrice(PrmsBidDetail prmsBidDetail, PrmsSupplyProfile prmsSupplyProfile);

    public List<PrmsFinancialEvaluaDetail> getListOfSpplierPrice(PrmsBid prmsBid, PrmsSupplyProfile prmsSupplyProfile);

    public List<PrmsFinancialEvalResult> getFinancialOnList();

    public List<PrmsServiceAndWorkReg> getServiceFrmQuotation(String toString);

    public List<PrmsQuotationDetail> getSupplierListFrService(String serviceName);

    public List<PrmsServiceAndWorkReg> getWorkFrmQuotation(String toString);

    public List<PrmsBidDetail> getItemBasedServiceList(PrmsBid prmsBid);

    public List<PrmsBidDetail> getItemBasedWorkList(PrmsBid prmsBid);

    public String getfinancialResltNoSeq();

    public List<PrmsFinancialEvalResult> getParamNameList();
}
