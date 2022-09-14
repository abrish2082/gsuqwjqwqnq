/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.mapper.ComLuCountryFacade;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.mms.entity.MmsSubCat;
import et.gov.eep.mms.mapper.MmsCategoryFacade;
import et.gov.eep.mms.mapper.MmsItemRegistrationFacade;
import et.gov.eep.mms.mapper.MmsSubCatFacade;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsBidFacade;
import et.gov.eep.prms.mapper.PrmsFinancialEvalResultFacade;
import et.gov.eep.prms.mapper.PrmsMarketAssessmentDetailFacade;
import et.gov.eep.prms.mapper.PrmsMarketAssessmentFacade;
import et.gov.eep.prms.mapper.PrmsPurchaseRequestFacade;
import et.gov.eep.prms.mapper.PrmsServiceAndWorkRegFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

/**
 *
 * @author user
 */
@Stateless
public class MarketAssesmntBean implements MarketAssesmntBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsMarketAssessmentFacade eepPmsMarketAssessmentInfoFacade;
    @EJB
    PrmsMarketAssessmentDetailFacade assessmentDetailFacade;
    @EJB
    MmsItemRegistrationFacade mmsItemRegistrationFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;
    @EJB
    ComLuCountryFacade comLuCountryFacade;
    @EJB
    FmsLuCurrencyFacade fmsLuCurrencyFacade;
    @EJB
    PrmsSupplyProfileFacade prmsSupplyProfileFacade;
    @EJB
    PrmsPurchaseRequestFacade prmsPurchaseRequestFacade;
    @EJB
    PrmsServiceAndWorkRegFacade prmsServiceAndWorkRegFacade;
    @EJB
    MmsCategoryFacade mmsCategoryFacade;
    @EJB
    MmsSubCatFacade mmsSubCatFacade;
    @EJB
    PrmsFinancialEvalResultFacade prmsFinancialEvalResultFacade;
    @EJB
    PrmsBidFacade prmsBidFacade;
    // </editor-fold>

    @Override
    public void save(PrmsMarketAssessment prmsMarketAssessment) {
        eepPmsMarketAssessmentInfoFacade.create(prmsMarketAssessment);
    }

    @Override
    public void edit(PrmsMarketAssessment prmsMarketAssessment) {
        eepPmsMarketAssessmentInfoFacade.edit(prmsMarketAssessment);
    }

    @Override
    public ArrayList<PrmsMarketAssessment> searchMarketAssId(PrmsMarketAssessment prmsMarketAssessment) {
        return eepPmsMarketAssessmentInfoFacade.searchMarketAssmtId(prmsMarketAssessment);
    }

    @Override
    public PrmsMarketAssessment getMarketId(PrmsMarketAssessment prmsMarketAssessment) {
        return eepPmsMarketAssessmentInfoFacade.searchEvent(prmsMarketAssessment);
    }

    @Override
    public List<MmsItemRegistration> ItemCodeList() {
        return mmsItemRegistrationFacade.getItemLists();
    }

    @Override
    public ArrayList<HrDepartments> depList() {
        return hrDepartmentsFacade.searchAllDepSorted();
    }

    @Override
    public MmsItemRegistration ItemName(String matName) {
        return mmsItemRegistrationFacade.ItemName(matName);
    }

    @Override
    public void deleteContactPersonInfo(PrmsMarketAssessmentDetail eepPmsMrktAsssmtInfoDetil) {
        assessmentDetailFacade.remove(eepPmsMrktAsssmtInfoDetil);
    }

    @Override
    public List<PrmsMarketAssessment> searchAllMrktNo(PrmsMarketAssessment prmsMarketAssessment) {
        return eepPmsMarketAssessmentInfoFacade.getMarketNo(prmsMarketAssessment);
    }

    @Override
    public MmsItemRegistration ItemNames(MmsItemRegistration ItemId) {
        return mmsItemRegistrationFacade.getByMaterialId(ItemId);
    }

    @Override
    public List<PrmsPurchaseRequest> getPurchReqNo() {
        return prmsPurchaseRequestFacade.getPrReNo();
    }

    @Override
    public List<MmsServiceReg> getServiceNo() {
        return eepPmsMarketAssessmentInfoFacade.getServiceNo();
    }

    @Override
    public List<MmsItemRegistration> getItemCode(String toString) {
        return mmsItemRegistrationFacade.getByItemCode(toString);
    }

    @Override
    public List<MmsItemRegistration> getItemCodes() {
        return mmsItemRegistrationFacade.getItemLists();
    }

    @Override
    public List<MmsItemRegistration> getItemCod(String itemRegistration) {
        return mmsItemRegistrationFacade.searchItem(itemRegistration);
    }

    @Override
    public List<MmsServiceReg> getServiceNo(String toString) {
        return eepPmsMarketAssessmentInfoFacade.getServNo(toString);
    }

    @Override
    public List<MmsServiceReg> getSerNo() {
        return eepPmsMarketAssessmentInfoFacade.getserNo();
    }

    @Override
    public List<PrmsMarketAssessmentDetail> getItemN() {
        return assessmentDetailFacade.getItemNa();
    }

    @Override
    public List<PrmsMarketAssessmentDetail> getSupplyName() {
        return assessmentDetailFacade.getByMarketAssNoLike();
    }

    @Override
    public List<PrmsMarketAssessmentDetail> getItemNa(String itemName) {
        return assessmentDetailFacade.findByMarketAssNoLike(itemName);
    }

    @Override
    public PrmsMarketAssessmentDetail find(PrmsMarketAssessmentDetail toString) {
        return assessmentDetailFacade.findByMarketAssNo(toString);
    }

    @Override
    public List<PrmsMarketAssessmentDetail> getServiceNa(String sername) {
        return assessmentDetailFacade.findByMarketAssNoLike(sername);
    }

    @Override
    public PrmsMarketAssessmentDetail findServ(String toString) {
        return assessmentDetailFacade.getByMarketAssNo(toString);
    }

    @Override
    public MmsItemRegistration findItemName(MmsItemRegistration mmsItemRegistration) {
        return mmsItemRegistrationFacade.getByMaterialName(mmsItemRegistration);
    }

    @Override
    public PrmsServiceAndWorkReg findsrvcNam(PrmsServiceAndWorkReg serviceName) {
        return prmsServiceAndWorkRegFacade.getServiceName(serviceName);

    }

    @Override
    public PrmsMarketAssessment getSelectedRequest(String id) {
        return eepPmsMarketAssessmentInfoFacade.getSelectedRequest(id);
    }

    @Override
    public PrmsMarketAssessment getMarketNo() {
        return eepPmsMarketAssessmentInfoFacade.getMarktNo();
    }

    @Override
    public List<MmsServiceReg> getSeriveType() {
        return eepPmsMarketAssessmentInfoFacade.getServiceType();
    }

    @Override
    public List<PrmsServiceAndWorkReg> getConsultancy() {
        return prmsServiceAndWorkRegFacade.getConsultancyServiceOnlyLists();
    }

    @Override
    public List<PrmsServiceAndWorkReg> getService() {
        return prmsServiceAndWorkRegFacade.getServiceTypeOnly();
    }

    @Override
    public List<PrmsServiceAndWorkReg> getWorkName() {
        return prmsServiceAndWorkRegFacade.getWorkTypeOnly();
    }

    @Override
    public PrmsServiceAndWorkReg findWorkName(PrmsServiceAndWorkReg workName) {
        return prmsServiceAndWorkRegFacade.getWorkName(workName);
    }

    @Override
    public List<MmsCategory> getCateName() {
        return mmsCategoryFacade.getCatNameLists();
    }

    @Override
    public List<ComLuCountry> getLucoutry() {
        return comLuCountryFacade.getCountryName();
    }

    @Override
    public List<MmsSubCat> subCatList(MmsCategory subCatName) {
        return mmsSubCatFacade.getSubCatListByCategoryId(subCatName);
    }

    @Override
    public List<MmsItemRegistration> getItemList(MmsSubCat mmsSubCat) {
        return mmsItemRegistrationFacade.getItemListBySubCatId(mmsSubCat);
    }

    @Override
    public List<FmsLuCurrency> getCurrency() {
        return fmsLuCurrencyFacade.currencyNames();
    }

    @Override
    public List<MmsSubCat> SubcatNameLst(Integer catId) {
        return mmsSubCatFacade.getSubCatListById(catId);
    }

    @Override
    public List<PrmsFinancialEvalResult> getFromYears() {
        return prmsFinancialEvalResultFacade.getFromYears();
    }

    @Override
    public List<MmsItemRegistration> getItemNameListFrmEval(Date fromYear, Date toYear) {
        return mmsItemRegistrationFacade.getItemNameListFrmEval(fromYear, toYear);
    }

    @Override
    public List<PrmsServiceAndWorkReg> getServiceNameListByDate(Date fromDate, Date toDate) {
        return prmsServiceAndWorkRegFacade.getServiceNameListByDate(fromDate, toDate);
    }

    @Override
    public List<PrmsServiceAndWorkReg> getWorkNameListByDate(Date startYear, Date endYear) {
        return prmsServiceAndWorkRegFacade.getWorkNameListByDate(startYear, endYear);
    }

    @Override
    public List<FmsLuCurrency> getCurrencyBirr() {
        return fmsLuCurrencyFacade.getCurrencyInBirr();
    }

    @Override
    public List<PrmsMarketAssessment> getMarketAssmentOnList() {
        return eepPmsMarketAssessmentInfoFacade.getMarketAssmentOnList();
    }

    @Override
    public List<PrmsSupplyProfile> getSupplierName() {
        return prmsSupplyProfileFacade.getSupplierForSupplierProfile();
    }

    @Override
    public List<PrmsSupplyProfile> getContAndConsultancy() {
        return prmsSupplyProfileFacade.getSupplierForContOrConsultancyProfile();
    }

    @Override
    public ComLuCountry findCountry(ComLuCountry comLuCountry) {
        return comLuCountryFacade.findByCountryId(comLuCountry);
    }

    @Override
    public FmsLuCurrency findCurrency(FmsLuCurrency fmsLuCurrency) {
        return fmsLuCurrencyFacade.getByCurrencyId(fmsLuCurrency);
    }

    @Override
    public PrmsServiceAndWorkReg findServiceFileUpLd(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return prmsServiceAndWorkRegFacade.findServiceFileUpLd(prmsServiceAndWorkReg);
    }

    @Override
    public MmsSubCat findBySubCatId(MmsSubCat mmsSubCat) {
        return mmsSubCatFacade.getMmsMaterialSubCatInformation(mmsSubCat);
    }

    @Override
    public MmsSubCat getSubCategoryByItemId(MmsItemRegistration itemName) {
        return mmsSubCatFacade.getSubCategoryByItemId(itemName);
    }

    @Override
    public MmsCategory getCategoryByItemId(MmsItemRegistration itemName) {
        return mmsCategoryFacade.getCategoryByItemId(itemName);
    }

    @Override
    public MmsItemRegistration getByMaterialId(MmsItemRegistration mmsItemRegistration) {
        return mmsItemRegistrationFacade.getByMaterialId(mmsItemRegistration);
    }

    @Override
    public List<PrmsFinancialEvalResult> findFinancialEvalResultByDatesAndItemId(Date fromYear, Date toYear, MmsItemRegistration itemName) {
        return prmsFinancialEvalResultFacade.findFinancialEvalResultByDatesAndItemId(fromYear, toYear, itemName);
    }

    @Override
    public FmsLuCurrency getCurrenyByFrmEvalByItemId(MmsItemRegistration itemName) {
        return fmsLuCurrencyFacade.getCurrenyByFrmEvalByItemId(itemName);
    }

    @Override
    public FmsLuCurrency getCurrenyFrServiceByServiceOrWorkRegId(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return fmsLuCurrencyFacade.getCurrenyFrServiceByServiceOrWorkRegId(prmsServiceAndWorkReg);
    }

    @Override
    public List<PrmsFinancialEvalResult> findFinancialEvalResultByDatesAndWorkId(Date fromYear, Date toYear, PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return prmsFinancialEvalResultFacade.findFinancialEvalResultByDatesAndWorkId(fromYear, toYear, prmsServiceAndWorkReg);
    }

    @Override
    public List<PrmsFinancialEvalResult> findFinancialEvalResultByDatesAndServiceId(Date fromYear, Date toYear, PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return prmsFinancialEvalResultFacade.findFinancialEvalResultByDatesAndServiceId(fromYear, toYear, prmsServiceAndWorkReg);
    }

    @Override
    public PrmsBid getBidTypeByItemId(MmsItemRegistration itemName) {
        return prmsBidFacade.getBidTypeByItemId(itemName);
    }

    @Override
    public PrmsBid getBidTypeByServiceOrWorkId(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        return prmsBidFacade.getBidTypeByServiceOrWorkId(prmsServiceAndWorkReg);
    }

    @Override
    public List<PrmsMarketAssessment> getMarketAssessmentSearchingParameterList() {
        return eepPmsMarketAssessmentInfoFacade.getMarketAssessmentSearchingParameterList();
    }

    @Override
    public String getMarketAssmentSeqNo(String procurementType) {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        System.out.println("Current Ethiopian Year " + eYear);
        String goods_Service_WorkAssNo = null;
        String prefix = null;
        int maxNo = 0;
        if (procurementType.equals("Goods")) {
            prefix = "MA-Goods-No";
        } else if (procurementType.equals("Service")) {
            prefix = "MA-Service-No";
        } else if (procurementType.equals("Work")) {
            prefix = "MA-Work-No";
        }
        List<PrmsMarketAssessment> prmsMarketAssessmentList = eepPmsMarketAssessmentInfoFacade.getMarketAssmentSeqNo(prefix, eYear);
        for (int i = 0; i < prmsMarketAssessmentList.size(); i++) {
            goods_Service_WorkAssNo = prmsMarketAssessmentList.get(i).getMarketAssNo();
            String[] lastInspNos = goods_Service_WorkAssNo.split("-");
            String lastDatesPatern = lastInspNos[3];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increment = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increment) {
                maxNo = increment;
            }
        }
        maxNo = maxNo + 1;
        System.out.println("maxNo " + maxNo);
//        ServOrWorkNo = (prefix + "-" + maxNo + "/" + df1.format(now));
        goods_Service_WorkAssNo = (prefix + "-" + maxNo + "/" + eYear);
        return goods_Service_WorkAssNo;
    }

}
