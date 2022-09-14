/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.mms.entity.MmsSubCat;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Bayisa
 */
@Local
public interface MarketAssesmntBeanLocal {

    public void save(PrmsMarketAssessment prmsMarketAssessment);

    public void edit(PrmsMarketAssessment prmsMarketAssessment);

    public ArrayList<PrmsMarketAssessment> searchMarketAssId(PrmsMarketAssessment prmsMarketAssessment);

    public PrmsMarketAssessment getMarketId(PrmsMarketAssessment prmsMarketAssessment);

    public MmsItemRegistration ItemName(String itemRegistration);

    public List<MmsItemRegistration> ItemCodeList();

    public ArrayList<HrDepartments> depList();

    List<PrmsMarketAssessment> searchAllMrktNo(PrmsMarketAssessment prmsMarketAssessment);

    public void deleteContactPersonInfo(PrmsMarketAssessmentDetail eepPmsMrktAsssmtInfoDetil);

    public MmsItemRegistration ItemNames(MmsItemRegistration ItemId);

    public List<PrmsPurchaseRequest> getPurchReqNo();

    public List<MmsServiceReg> getServiceNo();

    public List<MmsItemRegistration> getItemCode(String toString);

    public List<MmsItemRegistration> getItemCodes();

    public List<MmsItemRegistration> getItemCod(String itemRegistration);

    public List<MmsServiceReg> getServiceNo(String toString);

    public List<MmsServiceReg> getSerNo();

    public List<PrmsMarketAssessmentDetail> getItemN();

    public List<PrmsMarketAssessmentDetail> getSupplyName();

    public List<PrmsMarketAssessmentDetail> getItemNa(String itemName);

    public PrmsMarketAssessmentDetail find(PrmsMarketAssessmentDetail toString);

    public List<PrmsMarketAssessmentDetail> getServiceNa(String serName);

    public PrmsMarketAssessmentDetail findServ(String toString);

    public MmsItemRegistration findItemName(MmsItemRegistration mmsItemRegistration);

    public PrmsServiceAndWorkReg findsrvcNam(PrmsServiceAndWorkReg serviceName);

    public PrmsMarketAssessment getSelectedRequest(String id);

    public PrmsMarketAssessment getMarketNo();

    public List<MmsServiceReg> getSeriveType();

    public List<PrmsServiceAndWorkReg> getConsultancy();

    public List<PrmsServiceAndWorkReg> getService();

    public List<PrmsServiceAndWorkReg> getWorkName();

    public PrmsServiceAndWorkReg findWorkName(PrmsServiceAndWorkReg toString);

    public List<MmsCategory> getCateName();

    public List<ComLuCountry> getLucoutry();

    public List<MmsSubCat> subCatList(MmsCategory toString);

    public List<MmsItemRegistration> getItemList(MmsSubCat mmsSubCat);

    public List<FmsLuCurrency> getCurrency();

    public List<MmsSubCat> SubcatNameLst(Integer catId);

    public List<PrmsFinancialEvalResult> getFromYears();

    public List<MmsItemRegistration> getItemNameListFrmEval(Date fromYear, Date toYear);

    public List<PrmsServiceAndWorkReg> getServiceNameListByDate(Date fromDate, Date toDate);

    public List<PrmsServiceAndWorkReg> getWorkNameListByDate(Date startYear, Date endYear);

    public List<FmsLuCurrency> getCurrencyBirr();

    public List<PrmsMarketAssessment> getMarketAssmentOnList();

    public List<PrmsSupplyProfile> getSupplierName();

    public List<PrmsSupplyProfile> getContAndConsultancy();

    public String getMarketAssmentSeqNo(String procurementType);

    public ComLuCountry findCountry(ComLuCountry comLuCountry);

    public FmsLuCurrency findCurrency(FmsLuCurrency fmsLuCurrency);

    public PrmsServiceAndWorkReg findServiceFileUpLd(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public MmsSubCat findBySubCatId(MmsSubCat mmsSubCat);

    public MmsSubCat getSubCategoryByItemId(MmsItemRegistration itemName);

    public MmsCategory getCategoryByItemId(MmsItemRegistration itemName);

    public MmsItemRegistration getByMaterialId(MmsItemRegistration mmsItemRegistration);

    public List<PrmsFinancialEvalResult> findFinancialEvalResultByDatesAndItemId(Date fromYear, Date toYear, MmsItemRegistration itemName);

    public FmsLuCurrency getCurrenyByFrmEvalByItemId(MmsItemRegistration itemName);

    public FmsLuCurrency getCurrenyFrServiceByServiceOrWorkRegId(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public List<PrmsFinancialEvalResult> findFinancialEvalResultByDatesAndWorkId(Date fromYear, Date toYear, PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public List<PrmsFinancialEvalResult> findFinancialEvalResultByDatesAndServiceId(Date fromYear, Date toYear, PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public PrmsBid getBidTypeByItemId(MmsItemRegistration itemName);

    public PrmsBid getBidTypeByServiceOrWorkId(PrmsServiceAndWorkReg prmsServiceAndWorkReg);

    public List<PrmsMarketAssessment> getMarketAssessmentSearchingParameterList();

}
