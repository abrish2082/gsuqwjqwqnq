/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import jxl.Sheet;
import jxl.Workbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import jxl.read.biff.BiffException;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.prms.businessLogic.MarketAssesmntBeanLocal;
import et.gov.eep.prms.entity.PrmsMarketAssessment;
import et.gov.eep.prms.entity.PrmsMarketAssessmentDetail;
import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.mms.businessLogic.MmsCategoryBeanLocal;
import et.gov.eep.mms.businessLogic.MmsSubcatBeanLocal;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSubCat;
import et.gov.eep.prms.businessLogic.BidRegBeanLocal;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsFinancialEvalResult;
import et.gov.eep.prms.entity.PrmsFinancialEvlResultyDtl;
import et.gov.eep.prms.entity.PrmsMarketAssessmentLoad;
import et.gov.eep.prms.entity.PrmsMarketAssmntService;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsMarketAssessmentFacade;
import java.text.SimpleDateFormat;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
// </editor-fold>

//Market Assessment view scoped CDI Named Bean class
@Named(value = "marketAssessmentController")
@ViewScoped
public class MarketAssessmentController implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private MarketAssesmntBeanLocal marketAssmntInfoBeanLocal;
    @EJB
    private MmsCategoryBeanLocal categoryBeanLocal;
    @EJB
    private MmsSubcatBeanLocal subcatBeanLocal;
    @EJB
    VendorRegBeanLocal vendorRegBeanLocal;
    @EJB
    BidRegBeanLocal bidRegBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    private PrmsMarketAssessment eepPmsMarketAssessmentInfo;
    @Inject
    private PrmsMarketAssessmentDetail eepPmsMrktAsssmtInfoDetil;
    @Inject
    private ComLuCountry comLuCountry;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    MmsItemRegistration mmsItemRegistration;
    @Inject
    PrmsMarketAssmntService prmsMarketAssmntService;
    @Inject
    PrmsServiceAndWorkReg prmsServiceAndWorkReg;
    @Inject
    PrmsMarketAssessmentLoad prmsMarketAssessmentLoad;
    @Inject
    MmsCategory mmsCategory;
    @Inject
    MmsSubCat mmsSubCat;
    @Inject
    PrmsFinancialEvalResult financialEvalResult;
    @Inject
    PrmsBid prmsBid;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean SessionBean;
    @Inject
    WorkFlow workFlow;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declared Lists and Variables">
    List<MmsItemRegistration> itemRegistrationList;
    List<PrmsServiceAndWorkReg> prmsServiceRegLists;
    List<PrmsServiceAndWorkReg> prmsWorkRegLists;
    List<PrmsMarketAssessmentDetail> marketAssessmentDetailList = new ArrayList<>();
    List<PrmsMarketAssmntService> prmsMarketAssmntServicesList = new ArrayList<>();
    List<MmsCategory> mmsCategoryList;
    List<MmsSubCat> mmsSubCatList;
    List<ComLuCountry> comLuCountryList;
    List<FmsLuCurrency> fmsLuCurrencyList;
    List<PrmsSupplyProfile> prmsSupplyProfileList;
    List<PrmsFinancialEvalResult> prmsFinancialEvalResultList = new ArrayList<>();
    List<PrmsMarketAssessment> prmsMarketAssessmentLst;
    List<PrmsMarketAssessment> marketAssessmentSearchParameterLst;

    private DataModel<PrmsMarketAssessmentDetail> dataModel;
    private DataModel<PrmsMarketAssmntService> marketAssmntServiceMdl;
    private DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<PrmsMarketAssessment> marketAssessmentModel;
     // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Declared Variables">
    Set<String> marketAssDetlCheck = new HashSet<>();
    Set<String> marketAssItemCheck = new HashSet<>();

    double averagePrice;
    Integer requestNotificationCounter = 0;
    int updteStatus = 0;
    int rows = 0;
    int flagToSave = 0;
    private String saveupdate = "Save";
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    //render Add or Update button 
    private String addOrModifyBundle = "Add";
    private String duplicattion = null;
    private String marketNo = null;
    private String selectedValue = "";
    private String userName = "";
    private String icone = "ui-icon-plus";
    private String procurementType = "Goods";
    String newMarketNo;
    String marketNum = "0";
    String filePath;

    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderitem = true;
    private boolean renderService;
    private boolean serviceTbale;
    private boolean itemTable;
    private boolean countryOfOrgin;
    private boolean workName;
    private boolean serviceName;
    private boolean isRenderToGoodsEvaluation;
    private boolean isRenderToGoodsAssessment;
    private boolean local;
    private boolean foreign;
    private boolean purchaseAgency;
    private boolean evaluation;
    private boolean assessment;
    private boolean isRenderedIconWorkflow = false;
    private boolean isDecision = false;
    private boolean isCommentGiven = false;
    private boolean supplierName = true;
    private boolean consultancyAndContrtName;
    private boolean isCreateButton;
    private boolean renderPnlToSearchPage;
    private boolean disableWhenUpdate;
    private boolean disableDetailWhenUpdate;
    private boolean updatingTime = false;
    private UploadedFile uploadedFile;
    private Date fromYear;
    private Date toYear;

    private PrmsMarketAssessment marketAssessmentSelect;
    PrmsFinancialEvlResultyDtl prmsFinancialEvlResultyDtl = new PrmsFinancialEvlResultyDtl();
// </editor-fold>

//default non-argument constructor
    public MarketAssessmentController() {
    }

    //callback lifecycle method
    @PostConstruct
    public void init() {
        wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
        setUserName(workFlow.getUserName());

        if (workFlow.isApproveStatus() || workFlow.isCheckStatus()) {
            isDecision = true;
            isCommentGiven = true;
            isCreateButton = false;

        } else {
            isDecision = false;
            isCommentGiven = false;
            isCreateButton = true;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="getter and Setter of Objects">
    public PrmsMarketAssessment getEepPmsMarketAssessmentInfo() {
        if (eepPmsMarketAssessmentInfo == null) {
            eepPmsMarketAssessmentInfo = new PrmsMarketAssessment();
        }
        return eepPmsMarketAssessmentInfo;
    }

    public void setEepPmsMarketAssessmentInfo(PrmsMarketAssessment eepPmsMarketAssessmentInfo) {
        this.eepPmsMarketAssessmentInfo = eepPmsMarketAssessmentInfo;
    }

    public PrmsMarketAssessmentDetail getEepPmsMrktAsssmtInfoDetil() {
        if (eepPmsMrktAsssmtInfoDetil == null) {
            eepPmsMrktAsssmtInfoDetil = new PrmsMarketAssessmentDetail();
        }
        return eepPmsMrktAsssmtInfoDetil;
    }

    public void setEepPmsMrktAsssmtInfoDetil(PrmsMarketAssessmentDetail eepPmsMrktAsssmtInfoDetil) {
        this.eepPmsMrktAsssmtInfoDetil = eepPmsMrktAsssmtInfoDetil;
    }

    public MmsItemRegistration getMmsItemRegistration() {
        if (mmsItemRegistration == null) {
            mmsItemRegistration = new MmsItemRegistration();
        }
        return mmsItemRegistration;
    }

    public void setMmsItemRegistration(MmsItemRegistration mmsItemRegistration) {
        this.mmsItemRegistration = mmsItemRegistration;
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
    }

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public PrmsBid getPrmsBid() {
        if (prmsBid == null) {
            prmsBid = new PrmsBid();
        }
        return prmsBid;
    }

    public void setPrmsBid(PrmsBid prmsBid) {
        this.prmsBid = prmsBid;
    }

    public PrmsFinancialEvalResult getFinancialEvalResult() {
        if (financialEvalResult == null) {
            financialEvalResult = new PrmsFinancialEvalResult();
        }
        return financialEvalResult;
    }

    public void setFinancialEvalResult(PrmsFinancialEvalResult financialEvalResult) {
        this.financialEvalResult = financialEvalResult;
    }

    public MmsSubCat getMmsSubCat() {
        if (mmsSubCat == null) {
            mmsSubCat = new MmsSubCat();
        }
        return mmsSubCat;
    }

    public void setMmsSubCat(MmsSubCat mmsSubCat) {
        this.mmsSubCat = mmsSubCat;
    }

    public MmsCategory getMmsCategory() {
        if (mmsCategory == null) {
            mmsCategory = new MmsCategory();
        }
        return mmsCategory;
    }

    public void setMmsCategory(MmsCategory mmsCategory) {
        this.mmsCategory = mmsCategory;
    }

    public PrmsMarketAssessmentLoad getPrmsMarketAssessmentLoad() {
        if (prmsMarketAssessmentLoad == null) {
            prmsMarketAssessmentLoad = new PrmsMarketAssessmentLoad();
        }
        return prmsMarketAssessmentLoad;
    }

    public void setPrmsMarketAssessmentLoad(PrmsMarketAssessmentLoad prmsMarketAssessmentLoad) {
        this.prmsMarketAssessmentLoad = prmsMarketAssessmentLoad;
    }

    public ComLuCountry getComLuCountry() {
        if (comLuCountry == null) {
            comLuCountry = new ComLuCountry();
        }
        return comLuCountry;
    }

    public void setComLuCountry(ComLuCountry comLuCountry) {
        this.comLuCountry = comLuCountry;
    }

    public FmsLuCurrency getFmsLuCurrency() {
        if (fmsLuCurrency == null) {
            fmsLuCurrency = new FmsLuCurrency();
        }
        return fmsLuCurrency;
    }

    public void setFmsLuCurrency(FmsLuCurrency fmsLuCurrency) {
        this.fmsLuCurrency = fmsLuCurrency;
    }

    public PrmsSupplyProfile getPrmsSupplyProfile() {
        if (prmsSupplyProfile == null) {
            prmsSupplyProfile = new PrmsSupplyProfile();
        }
        return prmsSupplyProfile;
    }

    public void setPrmsSupplyProfile(PrmsSupplyProfile prmsSupplyProfile) {
        this.prmsSupplyProfile = prmsSupplyProfile;
    }

    public PrmsServiceAndWorkReg getPrmsServiceAndWorkReg() {
        if (prmsServiceAndWorkReg == null) {
            prmsServiceAndWorkReg = new PrmsServiceAndWorkReg();
        }
        return prmsServiceAndWorkReg;
    }

    public void setPrmsServiceAndWorkReg(PrmsServiceAndWorkReg prmsServiceAndWorkReg) {
        this.prmsServiceAndWorkReg = prmsServiceAndWorkReg;
    }

    public PrmsMarketAssmntService getPrmsMarketAssmntService() {
        if (prmsMarketAssmntService == null) {
            prmsMarketAssmntService = new PrmsMarketAssmntService();
        }
        return prmsMarketAssmntService;
    }

    public void setPrmsMarketAssmntService(PrmsMarketAssmntService prmsMarketAssmntService) {
        this.prmsMarketAssmntService = prmsMarketAssmntService;
    }

    public PrmsMarketAssessment getMarketAssessmentSelect() {
        return marketAssessmentSelect;
    }

    public void setMarketAssessmentSelect(PrmsMarketAssessment marketAssessmentSelect) {
        this.marketAssessmentSelect = marketAssessmentSelect;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Variables">
    public Integer getRequestNotificationCounter() {
        prmsMarketAssessmentLst = marketAssmntInfoBeanLocal.getMarketAssmentOnList();
        requestNotificationCounter = prmsMarketAssessmentLst.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(Integer requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public boolean isDisableWhenUpdate() {
        return disableWhenUpdate;
    }

    public void setDisableWhenUpdate(boolean disableWhenUpdate) {
        this.disableWhenUpdate = disableWhenUpdate;
    }

    public boolean isDisableDetailWhenUpdate() {
        return disableDetailWhenUpdate;
    }

    public void setDisableDetailWhenUpdate(boolean disableDetailWhenUpdate) {
        this.disableDetailWhenUpdate = disableDetailWhenUpdate;
    }

    public boolean isRenderPnlCreateParty() {
        return renderPnlCreateParty;
    }

    public void setRenderPnlCreateParty(boolean renderPnlCreateParty) {
        this.renderPnlCreateParty = renderPnlCreateParty;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isIsCreateButton() {
        return isCreateButton;
    }

    public void setIsCreateButton(boolean isCreateButton) {
        this.isCreateButton = isCreateButton;
    }

    public boolean isRenderPnlToSearchPage() {
        return renderPnlToSearchPage;
    }

    public void setRenderPnlToSearchPage(boolean renderPnlToSearchPage) {
        this.renderPnlToSearchPage = renderPnlToSearchPage;
    }

    public boolean isSupplierName() {
        return supplierName;
    }

    public void setSupplierName(boolean supplierName) {
        this.supplierName = supplierName;
    }

    public boolean isConsultancyAndContrtName() {
        return consultancyAndContrtName;
    }

    public void setConsultancyAndContrtName(boolean consultancyAndContrtName) {
        this.consultancyAndContrtName = consultancyAndContrtName;
    }

    public boolean isPurchaseAgency() {
        return purchaseAgency;
    }

    public void setPurchaseAgency(boolean purchaseAgency) {
        this.purchaseAgency = purchaseAgency;
    }

    public boolean isEvaluation() {
        return evaluation;
    }

    public void setEvaluation(boolean evaluation) {
        this.evaluation = evaluation;
    }

    public boolean isIsDecision() {
        return isDecision;
    }

    public void setIsDecision(boolean isDecision) {
        this.isDecision = isDecision;
    }

    public boolean isIsCommentGiven() {
        return isCommentGiven;
    }

    public void setIsCommentGiven(boolean isCommentGiven) {
        this.isCommentGiven = isCommentGiven;
    }

    public boolean isIsRenderedIconWorkflow() {
        return isRenderedIconWorkflow;
    }

    public void setIsRenderedIconWorkflow(boolean isRenderedIconWorkflow) {
        this.isRenderedIconWorkflow = isRenderedIconWorkflow;
    }

    public boolean isAssessment() {
        return assessment;
    }

    public void setAssessment(boolean assessment) {
        this.assessment = assessment;
    }

    public boolean isServiceName() {
        return serviceName;
    }

    public void setServiceName(boolean serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isServiceTbale() {
        return serviceTbale;
    }

    public void setServiceTbale(boolean serviceTbale) {
        this.serviceTbale = serviceTbale;
    }

    public boolean isItemTable() {
        return itemTable;
    }

    public void setItemTable(boolean itemTable) {
        this.itemTable = itemTable;
    }

    public boolean isIsRenderToGoodsEvaluation() {
        return isRenderToGoodsEvaluation;
    }

    public void setIsRenderToGoodsEvaluation(boolean isRenderToGoodsEvaluation) {
        this.isRenderToGoodsEvaluation = isRenderToGoodsEvaluation;
    }

    public boolean isIsRenderToGoodsAssessment() {
        return isRenderToGoodsAssessment;
    }

    public void setIsRenderToGoodsAssessment(boolean isRenderToGoodsAssessment) {
        this.isRenderToGoodsAssessment = isRenderToGoodsAssessment;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public boolean isForeign() {
        return foreign;
    }

    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public boolean isRenderitem() {
        return renderitem;
    }

    public void setRenderitem(boolean renderitem) {
        this.renderitem = renderitem;
    }

    public boolean isRenderService() {
        return renderService;
    }

    public void setRenderService(boolean renderService) {
        this.renderService = renderService;
    }

    public boolean isCountryOfOrgin() {
        return countryOfOrgin;
    }

    public void setCountryOfOrgin(boolean countryOfOrgin) {
        this.countryOfOrgin = countryOfOrgin;
    }

    public boolean isWorkName() {
        return workName;
    }

    public void setWorkName(boolean workName) {
        this.workName = workName;
    }

    public boolean isUpdatingTime() {
        return updatingTime;
    }

    public void setUpdatingTime(boolean updatingTime) {
        this.updatingTime = updatingTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getAddOrModifyBundle() {
        return addOrModifyBundle;
    }

    public void setAddOrModifyBundle(String addOrModifyBundle) {
        this.addOrModifyBundle = addOrModifyBundle;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
    }

    public String getProcurementType() {
        return procurementType;
    }

    public void setProcurementType(String procurementType) {
        this.procurementType = procurementType;
    }

    public String getMarketNo() {
        return marketNo;
    }

    public void setMarketNo(String marketNo) {
        this.marketNo = marketNo;
    }

    public String getSaveupdate() {
        return saveupdate;
    }

    public void setSaveupdate(String saveupdate) {
        this.saveupdate = saveupdate;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    //Lists
    public List<PrmsFinancialEvalResult> getPrmsFinancialEvalResultList() {
        if (prmsFinancialEvalResultList == null) {
            prmsFinancialEvalResultList = new ArrayList<>();
        }
        return prmsFinancialEvalResultList;
    }

    public void setPrmsFinancialEvalResultList(List<PrmsFinancialEvalResult> prmsFinancialEvalResultList) {
        this.prmsFinancialEvalResultList = prmsFinancialEvalResultList;
    }

    public List<PrmsSupplyProfile> getPrmsSupplyProfileList() {
        if (prmsSupplyProfileList == null) {
            prmsSupplyProfileList = new ArrayList<>();
        }
        return prmsSupplyProfileList;
    }

    public void setPrmsSupplyProfileList(List<PrmsSupplyProfile> prmsSupplyProfileList) {
        this.prmsSupplyProfileList = prmsSupplyProfileList;
    }

    public List<ComLuCountry> getComLuCountryList() {
        if (comLuCountryList == null) {
            comLuCountryList = new ArrayList<>();
        }
        return comLuCountryList;
    }

    public void setComLuCountryList(List<ComLuCountry> comLuCountryList) {
        this.comLuCountryList = comLuCountryList;
    }

    public List<PrmsMarketAssessmentDetail> getMarketAssessmentDetailList() {
        if (marketAssessmentDetailList == null) {
            marketAssessmentDetailList = new ArrayList<>();
        }
        return marketAssessmentDetailList;
    }

    public void setMarketAssessmentDetailList(List<PrmsMarketAssessmentDetail> marketAssessmentDetailList) {
        this.marketAssessmentDetailList = marketAssessmentDetailList;
    }

    public List<MmsCategory> getMmsCategoryList() {
        if (mmsCategoryList == null) {
            mmsCategoryList = new ArrayList<>();
        }
        return mmsCategoryList;
    }

    public void setMmsCategoryList(List<MmsCategory> mmsCategoryList) {
        this.mmsCategoryList = mmsCategoryList;
    }

    public List<FmsLuCurrency> getFmsLuCurrencyList() {
        if (fmsLuCurrencyList == null) {
            fmsLuCurrencyList = new ArrayList<>();
        }
        return fmsLuCurrencyList;
    }

    public void setFmsLuCurrencyList(List<FmsLuCurrency> fmsLuCurrencyList) {
        this.fmsLuCurrencyList = fmsLuCurrencyList;
    }

    public List<MmsItemRegistration> getItemRegistrationList() {
        if (itemRegistrationList == null) {
            itemRegistrationList = new ArrayList<>();
        }
        return itemRegistrationList;
    }

    public void setItemRegistrationList(List<MmsItemRegistration> itemRegistrationList) {
        this.itemRegistrationList = itemRegistrationList;
    }

    public List<MmsSubCat> getMmsSubCatList() {
        if (mmsSubCatList == null) {
            mmsSubCatList = new ArrayList<>();
        }
        return mmsSubCatList;
    }

    public void setMmsSubCatList(List<MmsSubCat> mmsSubCatList) {
        this.mmsSubCatList = mmsSubCatList;
    }

    public List<PrmsMarketAssessment> getPrmsMarketAssessmentLst() {
        if (prmsMarketAssessmentLst == null) {
            prmsMarketAssessmentLst = new ArrayList<>();
        }
        return prmsMarketAssessmentLst;
    }

    public void setPrmsMarketAssessmentLst(List<PrmsMarketAssessment> prmsMarketAssessmentLst) {
        this.prmsMarketAssessmentLst = prmsMarketAssessmentLst;
    }

    public List<PrmsServiceAndWorkReg> getPrmsServiceRegLists() {
        if (prmsServiceRegLists == null) {
            prmsServiceRegLists = new ArrayList<>();
        }
        return prmsServiceRegLists;
    }

    public void setPrmsServiceRegLists(List<PrmsServiceAndWorkReg> prmsServiceRegLists) {
        this.prmsServiceRegLists = prmsServiceRegLists;
    }

    public List<PrmsServiceAndWorkReg> getPrmsWorkRegLists() {
        if (prmsWorkRegLists == null) {
            prmsWorkRegLists = new ArrayList<>();
        }
        return prmsWorkRegLists;
    }

    public void setPrmsWorkRegLists(List<PrmsServiceAndWorkReg> prmsWorkRegLists) {
        this.prmsWorkRegLists = prmsWorkRegLists;
    }

    public ArrayList<PrmsMarketAssessment> searchMarketAssId(String marketId) {

        ArrayList<PrmsMarketAssessment> marketAssessmentLst = null;
        marketAssessmentLst = marketAssmntInfoBeanLocal.searchMarketAssId(eepPmsMarketAssessmentInfo);

        eepPmsMarketAssessmentInfo.setMarketAssNo(marketId);
        return marketAssessmentLst;
    }

    public List<PrmsFinancialEvalResult> getYearList() {

        setPrmsFinancialEvalResultList(marketAssmntInfoBeanLocal.getFromYears());
        return prmsFinancialEvalResultList;
    }

    public List<MmsItemRegistration> completeItemNam(String serviceName) {
        itemRegistrationList = marketAssmntInfoBeanLocal.getItemCod(serviceName);
        return itemRegistrationList;
    }

    public List<PrmsSupplyProfile> getSuppName() {
        setPrmsSupplyProfileList(marketAssmntInfoBeanLocal.getSupplierName());
        return prmsSupplyProfileList;
    }

    public List<PrmsSupplyProfile> getContAndConsultancy() {
        setPrmsSupplyProfileList(marketAssmntInfoBeanLocal.getContAndConsultancy());
        return prmsSupplyProfileList;
    }

    public List<ComLuCountry> getCoutry() {
        setComLuCountryList(marketAssmntInfoBeanLocal.getLucoutry());
        return comLuCountryList;
    }

    public List<FmsLuCurrency> getCurrency() {
        setFmsLuCurrencyList(marketAssmntInfoBeanLocal.getCurrency());
        return fmsLuCurrencyList;
    }

    public List<FmsLuCurrency> getCurrencyBirr() {
        setFmsLuCurrencyList(marketAssmntInfoBeanLocal.getCurrencyBirr());
        return fmsLuCurrencyList;
    }

    public List<PrmsServiceAndWorkReg> getWorkName() {
        setPrmsWorkRegLists(marketAssmntInfoBeanLocal.getWorkName());
        return prmsWorkRegLists;
    }

    public List<MmsItemRegistration> getItemCodes() {
        setItemRegistrationList(marketAssmntInfoBeanLocal.getItemCodes());
        return itemRegistrationList;
    }

    public List<MmsCategory> getcatName() {
        setMmsCategoryList(marketAssmntInfoBeanLocal.getCateName());
        return mmsCategoryList;
    }

    public List<PrmsServiceAndWorkReg> getConsultancy() {
        setPrmsServiceRegLists(marketAssmntInfoBeanLocal.getConsultancy());
        return prmsServiceRegLists;
    }

    public List<PrmsServiceAndWorkReg> service() {
        setPrmsServiceRegLists(marketAssmntInfoBeanLocal.getService());
        return prmsServiceRegLists;
    }

    public List<PrmsMarketAssessmentDetail> complete(String itemName) {

        List<PrmsMarketAssessmentDetail> assessmentDetailLst = marketAssmntInfoBeanLocal.getItemNa(itemName);

        return assessmentDetailLst;
    }

    public List<PrmsMarketAssessmentDetail> completeServ(String serviceName) {

        List<PrmsMarketAssessmentDetail> assessmentDetailLst = marketAssmntInfoBeanLocal.getServiceNa(serviceName);

        return assessmentDetailLst;
    }

    public List<PrmsMarketAssessmentDetail> getSupplierName() {
        setMarketAssessmentDetailList(marketAssmntInfoBeanLocal.getSupplyName());
        return marketAssessmentDetailList;

    }

    public List<MmsItemRegistration> getItemNm() {
        setItemRegistrationList(marketAssmntInfoBeanLocal.getItemCodes());
        return getItemRegistrationList();

    }

    public List<PrmsMarketAssessment> getMarketAssessmentSearchParameterLst() {
        if (marketAssessmentSearchParameterLst == null) {
            marketAssessmentSearchParameterLst = new ArrayList<>();
            marketAssessmentSearchParameterLst = marketAssmntInfoBeanLocal.getMarketAssessmentSearchingParameterList();
            System.out.println("marketAssessmentSearchParameterLst==" + marketAssessmentSearchParameterLst);
        }
        return marketAssessmentSearchParameterLst;
    }

    public void setMarketAssessmentSearchParameterLst(List<PrmsMarketAssessment> marketAssessmentSearchParameterLst) {
        this.marketAssessmentSearchParameterLst = marketAssessmentSearchParameterLst;
    }

    //Data Models
    public DataModel<PrmsMarketAssessment> getMarketAssessmentModel() {
        if (marketAssessmentModel == null) {
            marketAssessmentModel = new ListDataModel<>();
        }
        return marketAssessmentModel;
    }

    public void setMarketAssessmentModel(DataModel<PrmsMarketAssessment> marketAssessmentModel) {
        this.marketAssessmentModel = marketAssessmentModel;
    }

    public DataModel<PrmsMarketAssmntService> getMarketAssmntServiceMdl() {
        if (marketAssmntServiceMdl == null) {
            marketAssmntServiceMdl = new ListDataModel<>();
        }
        return marketAssmntServiceMdl;
    }

    public void setMarketAssmntServiceMdl(DataModel<PrmsMarketAssmntService> marketAssmntServiceMdl) {
        this.marketAssmntServiceMdl = marketAssmntServiceMdl;
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {
        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ListDataModel<>();
        }
        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {
        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public DataModel<PrmsMarketAssessmentDetail> getDataModel() {
        if (dataModel == null) {
            dataModel = new ListDataModel<>();
        }
        return dataModel;
    }

    public void setDataModel(DataModel<PrmsMarketAssessmentDetail> dataModel) {
        this.dataModel = dataModel;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Event changes">
    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepPmsMarketAssessmentInfo = (PrmsMarketAssessment) event.getNewValue();
            populateUI();
            saveorUpdateBundle = "Update";
        }
    }

    public void handleselectOptions(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            selectedValue = event.getNewValue().toString();
        }
    }

    public void searchByMarketId(SelectEvent event) {
        eepPmsMarketAssessmentInfo.setMarketAssNo(event.getObject().toString());
        eepPmsMarketAssessmentInfo = marketAssmntInfoBeanLocal.getMarketId(eepPmsMarketAssessmentInfo);
        reacreteDataModel();
        saveupdate = "Update";
    }

    public void changeEventSubCat(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            Integer matId = new Integer(event.getNewValue().toString());
            mmsCategory.setCatId(matId);
            mmsCategory = categoryBeanLocal.searchByCategoryId(mmsCategory);
            mmsSubCatList = marketAssmntInfoBeanLocal.subCatList(mmsCategory);

        }
    }

    public void changeEvent(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String matId = event.getNewValue().toString();
            mmsItemRegistration.setMaterialId(Integer.parseInt(matId));
            mmsItemRegistration = marketAssmntInfoBeanLocal.ItemNames(mmsItemRegistration);
        }
    }

    public void changeAveragePrice(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            if (procurementType.equalsIgnoreCase("Goods")) {
                int bidSize = prmsFinancialEvalResultList.size();
                int count = 0;
                double sum = 0.0;
                MmsItemRegistration itemName = new MmsItemRegistration();
                itemName = (MmsItemRegistration) event.getNewValue();
                prmsFinancialEvlResultyDtl.setItemId(itemName);

                prmsFinancialEvalResultList = marketAssmntInfoBeanLocal.findFinancialEvalResultByDatesAndItemId(fromYear, toYear, itemName);

                for (int i = 0; i < bidSize && i < 3; i++) {
                    sum += prmsFinancialEvalResultList.get(i).getPrmsFinancialEvlResultyDtlList().get(0).getTotalPrice();
                    count++;
                }
                averagePrice = sum / count;
                fmsLuCurrency = marketAssmntInfoBeanLocal.getCurrenyByFrmEvalByItemId(itemName);
                prmsBid = marketAssmntInfoBeanLocal.getBidTypeByItemId(itemName);
                eepPmsMrktAsssmtInfoDetil.setUnitPrice(BigDecimal.valueOf(averagePrice));

                eepPmsMrktAsssmtInfoDetil.setAvailabilty(prmsBid.getBidType());

                mmsCategory = marketAssmntInfoBeanLocal.getCategoryByItemId(itemName);
                mmsSubCat = marketAssmntInfoBeanLocal.getSubCategoryByItemId(itemName);

            } else if (procurementType.equalsIgnoreCase("Service")) {
                prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
                prmsServiceAndWorkReg = marketAssmntInfoBeanLocal.findsrvcNam(prmsServiceAndWorkReg);
                prmsMarketAssmntService.setServiceType(prmsServiceAndWorkReg.getServiceType());
                prmsMarketAssmntService.setUnitMeasure(prmsServiceAndWorkReg.getUnitMeasures());
                prmsFinancialEvlResultyDtl.setServiceId(prmsServiceAndWorkReg);
                prmsFinancialEvalResultList = marketAssmntInfoBeanLocal.findFinancialEvalResultByDatesAndServiceId(fromYear, toYear, prmsServiceAndWorkReg);
                int bidSize = prmsFinancialEvalResultList.size();
                int count = 0;
                double sum = 0.0;
                for (int i = 0; i < bidSize && i < 3; i++) {
                    sum += prmsFinancialEvalResultList.get(i).getPrmsFinancialEvlResultyDtlList().get(0).getTotalPrice();
                    count++;
                }
                averagePrice = sum / count;
                fmsLuCurrency = marketAssmntInfoBeanLocal.getCurrenyFrServiceByServiceOrWorkRegId(prmsServiceAndWorkReg);
                prmsBid = marketAssmntInfoBeanLocal.getBidTypeByServiceOrWorkId(prmsServiceAndWorkReg);
                prmsMarketAssmntService.setUnitPrice(BigDecimal.valueOf(averagePrice));
                prmsMarketAssmntService.setAvailabilty(prmsBid.getBidType());

            } else if (procurementType.equalsIgnoreCase("Work")) {
                prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
                prmsFinancialEvalResultList = marketAssmntInfoBeanLocal.findFinancialEvalResultByDatesAndWorkId(fromYear, toYear, prmsServiceAndWorkReg);
                int bidSize = prmsFinancialEvalResultList.size();
                int count = 0;
                double sum = 0.0;
                for (int i = 0; i < bidSize && i < 3; i++) {
                    sum += prmsFinancialEvalResultList.get(i).getPrmsFinancialEvlResultyDtlList().get(0).getTotalPrice();
                    count++;
                }
                averagePrice = sum / count;
                fmsLuCurrency = marketAssmntInfoBeanLocal.getCurrenyFrServiceByServiceOrWorkRegId(prmsServiceAndWorkReg);
                prmsBid = marketAssmntInfoBeanLocal.getBidTypeByServiceOrWorkId(prmsServiceAndWorkReg);
                prmsMarketAssmntService.setUnitPrice(BigDecimal.valueOf(averagePrice));
                prmsMarketAssmntService.setAvailabilty(prmsBid.getBidType());
            }
        }
    }

    public void handleFromYearEvents(SelectEvent select) {
        if (select.getObject() != null) {
            if (procurementType.equals("Goods")) {
                fromYear = eepPmsMrktAsssmtInfoDetil.getFromYear();
                toYear = eepPmsMrktAsssmtInfoDetil.getToYear();
                if (eepPmsMrktAsssmtInfoDetil.getToYear() != null) {
                    itemRegistrationList = marketAssmntInfoBeanLocal.getItemNameListFrmEval(fromYear, toYear);
                }

            } else if (procurementType.equals("Service")) {
                fromYear = prmsMarketAssmntService.getFromYear();
                toYear = prmsMarketAssmntService.getToYear();
                if (prmsMarketAssmntService.getToYear() != null) {
                    prmsServiceRegLists = marketAssmntInfoBeanLocal.getServiceNameListByDate(fromYear, toYear);
                }

            } else {
                fromYear = prmsMarketAssmntService.getFromYear();
                toYear = prmsMarketAssmntService.getToYear();
                if (prmsMarketAssmntService.getToYear() != null) {
                    prmsWorkRegLists = marketAssmntInfoBeanLocal.getWorkNameListByDate(fromYear, toYear);
                }

            }
        }
    }

    public void handleToYearEvents(SelectEvent select) {
        if (procurementType.equals("Goods")) {
            fromYear = eepPmsMrktAsssmtInfoDetil.getFromYear();
            toYear = eepPmsMrktAsssmtInfoDetil.getToYear();
            if (fromYear != null) {
                itemRegistrationList = marketAssmntInfoBeanLocal.getItemNameListFrmEval(fromYear, toYear);
            }
        } else if (procurementType.equals("Service")) {
            fromYear = prmsMarketAssmntService.getFromYear();
            toYear = prmsMarketAssmntService.getToYear();
            if (prmsMarketAssmntService.getFromYear() != null) {
                prmsServiceRegLists = marketAssmntInfoBeanLocal.getServiceNameListByDate(fromYear, toYear);
            }
        } else if (procurementType.equals("Work")) {
            fromYear = prmsMarketAssmntService.getFromYear();
            toYear = prmsMarketAssmntService.getToYear();
            if (prmsMarketAssmntService.getFromYear() != null) {
                prmsWorkRegLists = marketAssmntInfoBeanLocal.getWorkNameListByDate(fromYear, toYear);
            }
        }
    }

    public void changeEventItemNam(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            String matId = event.getNewValue().toString();
            mmsItemRegistration.setMaterialId(Integer.parseInt(matId));
            Integer matIds = new Integer(event.getNewValue().toString());
            mmsSubCat.setSubCatId(matIds);
            mmsSubCat = subcatBeanLocal.getMmsMaterialSubCatInformation(mmsSubCat);
            itemRegistrationList = marketAssmntInfoBeanLocal.getItemList(mmsSubCat);

        }
    }

    public void changeEventServiceNo(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) event.getNewValue();
            prmsServiceAndWorkReg = bidRegBeanLocal.findId(prmsServiceAndWorkReg);
            prmsServiceAndWorkReg = marketAssmntInfoBeanLocal.findsrvcNam(prmsServiceAndWorkReg);
        }
    }

    public void changeEventWorkName(ValueChangeEvent changeEvent) {
        if (null != changeEvent.getNewValue()) {
            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) changeEvent.getNewValue();
            prmsServiceAndWorkReg = bidRegBeanLocal.findId(prmsServiceAndWorkReg);
            prmsServiceAndWorkReg = marketAssmntInfoBeanLocal.findWorkName(prmsServiceAndWorkReg);
        }
    }

    public void changeCurrencyAndBirr(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = prmsSupplyProfile.getVendorType();
            if (select.equalsIgnoreCase("foreign")) {
                local = false;
                foreign = true;

            } else {
                local = true;
                foreign = false;
            }
        }
    }

    public void changePrRqType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String supplierProfile = "";
            clear();
            clearDetail();
            procurementType = e.getNewValue().toString();
            if (procurementType.equalsIgnoreCase("Goods")) {
                supplierName = true;
                consultancyAndContrtName = false;
                itemTable = true;
                serviceTbale = false;
                renderitem = true;
                renderService = false;
                countryOfOrgin = true;
                workName = false;
            } else if (procurementType.equalsIgnoreCase("Service")) {
                supplierName = false;
                consultancyAndContrtName = true;
                itemTable = false;
                serviceTbale = true;
                renderitem = false;
                renderService = true;
                countryOfOrgin = false;
                workName = false;
                serviceName = true;
            } else if (procurementType.equalsIgnoreCase("Work")) {
                supplierName = false;
                consultancyAndContrtName = true;
                itemTable = false;
                serviceTbale = true;
                renderitem = false;
                renderService = false;
                countryOfOrgin = false;
                workName = true;
                serviceName = false;
            }
        }

    }

    public void changeSearchByServItem(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("good")) {
                renderitem = false;
                renderService = true;
            } else {

                renderService = false;
                renderitem = true;
            }
        }

    }

    public void changeSourceOfAssessment(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            clearDetail();
            String select = "";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Evaluation")) {
                purchaseAgency = false;
                evaluation = true;
                assessment = false;
            } else if (select.equalsIgnoreCase("Purchase Agency")) {
                evaluation = false;
                purchaseAgency = true;
                assessment = false;
            } else {
                purchaseAgency = false;
                evaluation = false;
                assessment = true;
            }
        }

    }

    public void handleSelect(SelectEvent event) {

        eepPmsMarketAssessmentInfo.setMarketAssNo(event.getObject().toString());
        eepPmsMrktAsssmtInfoDetil.setMarketAssmntId(eepPmsMarketAssessmentInfo);
        eepPmsMrktAsssmtInfoDetil = marketAssmntInfoBeanLocal.find(eepPmsMrktAsssmtInfoDetil);

        reacreteDataModel();

    }

    public void handleSelectItemName(SelectEvent event) {

        mmsItemRegistration.setMatName(event.getObject().toString());
        mmsItemRegistration = marketAssmntInfoBeanLocal.findItemName(mmsItemRegistration);

    }

    public void handleSelectService(SelectEvent event) {

        eepPmsMrktAsssmtInfoDetil = marketAssmntInfoBeanLocal.findServ(event.getObject().toString());

    }

    public void rowSelect(SelectEvent event) {
        eepPmsMarketAssessmentInfo = (PrmsMarketAssessment) event.getObject();
        saveorUpdateBundle = "Update";
        renderPnlToSearchPage = true;
        populateUI();
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            eepPmsMarketAssessmentInfo.setColumnName(event.getNewValue().toString());
            eepPmsMarketAssessmentInfo.setColumnValue(null);
        }
    }
        // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="other methods">
    public void createNewParty() {

        saveorUpdateBundle = "Save";
        disableWhenUpdate = false;
        renderPnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            clear();
            clearDetail();
            createOrSearchBundle = "Search";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setCreateOrSearchBundle(createOrSearchBundle);
            setIcone("ui-icon-plus");
        }
    }

    public void goBackSearchButtonAction() {
        renderPnlToSearchPage = false;
        renderPnlCreateParty = false;
        renderPnlManPage = true;
    }

    public String generateMarketNo() {
        if (saveorUpdateBundle.equals("Update")) {
            newMarketNo = eepPmsMarketAssessmentInfo.getMarketAssNo();
        } else {
            if (procurementType != null) {
                newMarketNo = getMarketAssmentSeqNo(procurementType);
            }
        }
        return newMarketNo;
    }

    public String getMarketAssmentSeqNo(String procurementType) {
        String goods_Service_WorkNo = marketAssmntInfoBeanLocal.getMarketAssmentSeqNo(procurementType);
        return goods_Service_WorkNo;
    }

    public void addTable() {
        prmsMarketAssmntService.setServiceId(prmsServiceAndWorkReg);
        updatingTime = false;
        if (procurementType.equals("Service") && prmsMarketAssmntService.getSourceOfAssessment().equals("Evaluation")) {
            prmsServiceAndWorkReg.setServiceType(prmsMarketAssmntService.getServiceType());
            prmsServiceAndWorkReg.setUnitMeasures(prmsMarketAssmntService.getUnitMeasure());
        }
        if (marketAssDetlCheck.contains(prmsMarketAssmntService.getServiceId().getServiceName())) {

            prmsMarketAssmntService = null;
            prmsServiceAndWorkReg = null;
            JsfUtil.addFatalMessage("Duplicate Entry Is Not Allowed !!");
        } else if (marketAssDetlCheck.equals(prmsMarketAssmntService.getServiceId().getServiceName())) {
            JsfUtil.addSuccessMessage("Updated Successful !!");
            prmsMarketAssmntService = null;
            prmsServiceAndWorkReg = null;
            reCreatService();
        } else {
            eepPmsMarketAssessmentInfo.add(prmsMarketAssmntService);
            marketAssDetlCheck.add(prmsMarketAssmntService.getServiceId().getServiceName());
            prmsMarketAssmntService = null;
            prmsServiceAndWorkReg = null;
            reCreatService();
        }
        clearDetail();
    }

    public void addToItemTable() {
        eepPmsMrktAsssmtInfoDetil.setItemId(mmsItemRegistration);
        if (marketAssItemCheck.contains(eepPmsMrktAsssmtInfoDetil.getItemId().getMatName())) {
            JsfUtil.addFatalMessage("Duplicate Entry Is Not Allowed !!");
        } else if (marketAssItemCheck.contains(eepPmsMrktAsssmtInfoDetil.getItemId().getMatName())) {
//            clearDetail();
            JsfUtil.addSuccessMessage("Updated Successful !!");
        } else {
            marketAssItemCheck.add(eepPmsMrktAsssmtInfoDetil.getItemId().getMatName());
            eepPmsMrktAsssmtInfoDetil.setCategoryId(mmsCategory);
            eepPmsMrktAsssmtInfoDetil.setSubCategoryId(mmsSubCat);
            eepPmsMrktAsssmtInfoDetil.setCurrencyId(fmsLuCurrency);
            eepPmsMrktAsssmtInfoDetil.setCountryId(comLuCountry);
            eepPmsMarketAssessmentInfo.add(eepPmsMrktAsssmtInfoDetil);
        }
        reacreteDataModel();
        clearDetail();
    }

    public void edit() {
        prmsMarketAssmntService = marketAssmntServiceMdl.getRowData();
        prmsServiceAndWorkReg = prmsMarketAssmntService.getServiceId();
        String select = eepPmsMarketAssessmentInfo.getPurchaseType();
        addOrModifyBundle = "Modify";
        if ("Work".equals(select)) {
            workName = true;
            renderService = false;
        } else if ("Service".equals(select)) {
            renderService = true;
            workName = false;
        }
        if ("Evaluation".equals(prmsMarketAssmntService.getSourceOfAssessment())) {
            purchaseAgency = false;
            evaluation = true;
            assessment = false;
            if (saveorUpdateBundle.equals("Update")) {
                disableDetailWhenUpdate = true;
            }
            fmsLuCurrency = marketAssmntInfoBeanLocal.getCurrenyFrServiceByServiceOrWorkRegId(prmsServiceAndWorkReg);
        } else if ("Purchase Agency".equals(prmsMarketAssmntService.getSourceOfAssessment())) {
            purchaseAgency = false;
            evaluation = false;
            assessment = true;
        } else {
            purchaseAgency = false;
            evaluation = false;
            assessment = true;
        }

    }

    public void editItem() {

        eepPmsMrktAsssmtInfoDetil = dataModel.getRowData();
        mmsCategory = eepPmsMrktAsssmtInfoDetil.getCategoryId();
        mmsSubCat = eepPmsMrktAsssmtInfoDetil.getSubCategoryId();
        mmsItemRegistration = eepPmsMrktAsssmtInfoDetil.getItemId();
        fmsLuCurrency = eepPmsMrktAsssmtInfoDetil.getCurrencyId();
        comLuCountry = eepPmsMrktAsssmtInfoDetil.getCountryId();
        addOrModifyBundle = "Modify";

        mmsItemRegistration.setMatSubcategory(mmsSubCat);
        if ("Evaluation".equals(eepPmsMrktAsssmtInfoDetil.getSourceOfAssessment())) {
            purchaseAgency = false;
            evaluation = true;
            assessment = false;
            if (saveorUpdateBundle.equals("Update")) {
                disableDetailWhenUpdate = true;
            }
            if (eepPmsMrktAsssmtInfoDetil.getAvailabilty() != null) {
                prmsBid.setBidType(eepPmsMrktAsssmtInfoDetil.getAvailabilty());
            }
            fromYear = eepPmsMrktAsssmtInfoDetil.getFromYear();
            toYear = eepPmsMrktAsssmtInfoDetil.getToYear();
            if (fromYear != null && toYear != null) {
                itemRegistrationList = marketAssmntInfoBeanLocal.getItemNameListFrmEval(fromYear, toYear);
            }
        } else if ("Purchase Agency".equals(eepPmsMrktAsssmtInfoDetil.getSourceOfAssessment())) {
            purchaseAgency = true;
            evaluation = false;
            assessment = false;
        } else {
            purchaseAgency = false;
            evaluation = false;
            assessment = true;
            mmsSubCatList = marketAssmntInfoBeanLocal.subCatList(mmsCategory);
            itemRegistrationList = marketAssmntInfoBeanLocal.getItemList(mmsSubCat);

        }

    }

    public void search_MarketAssessment() {
        eepPmsMarketAssessmentInfo.setPreparedby(workFlow.getUserAccount());
        prmsMarketAssessmentLst = marketAssmntInfoBeanLocal.searchAllMrktNo(eepPmsMarketAssessmentInfo);
        recreat();

        eepPmsMarketAssessmentInfo = new PrmsMarketAssessment();
    }

    public SelectItem[] getItemCode() {

        return JsfUtil.getSelectItems(marketAssmntInfoBeanLocal.ItemCodeList(), true);
    }

    public SelectItem[] getServiceNo() {

        return JsfUtil.getSelectItems(marketAssmntInfoBeanLocal.getServiceNo(), true);
    }

    public SelectItem[] getItemCod() {
        return JsfUtil.getSelectItems(marketAssmntInfoBeanLocal.getItemCodes(), true);
    }

    public SelectItem[] getCategoryName() {
        return JsfUtil.getSelectItems(marketAssmntInfoBeanLocal.getCateName(), true);
    }

    public SelectItem[] getAllCategoryName() {
        return JsfUtil.getSelectItems(categoryBeanLocal.findAll(), true);
    }

    public SelectItem[] getItemName() {
        return JsfUtil.getSelectItems(marketAssmntInfoBeanLocal.getItemN(), true);
    }

    public void readExecle(String filePath) throws FileNotFoundException, ParseException, IOException, BiffException {
        this.filePath = filePath;
        String[] str = filePath.split("\\.");

        Workbook preservResWrkbk = Workbook.getWorkbook(uploadedFile.getInputstream());
        Sheet preservResSheet = preservResWrkbk.getSheet(0);

        rows = preservResSheet.getRows();
        prmsMarketAssessmentLoad = new PrmsMarketAssessmentLoad();

        for (int i = 0; i < rows; i++) {
            eepPmsMrktAsssmtInfoDetil.setSourceOfAssessment(preservResSheet.getCell(0, i).getContents().replace(" ", ""));
            eepPmsMrktAsssmtInfoDetil.setAvailabilty(preservResSheet.getCell(1, i).getContents().replace(" ", ""));
            eepPmsMrktAsssmtInfoDetil.setRemark(preservResSheet.getCell(2, i).getContents().replace(" ", ""));
            addToItemTable();
            reacreteDataModel();
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException, ParseException, BiffException {

        FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        uploadedFile = event.getFile();
        String filePath = "c:/prms";
        boolean success = (new File(filePath)).mkdirs();
        if (success) {
            read2003(filePath + event.getFile().getFileName());

        } else {
            read2003(filePath + event.getFile().getFileName());
        }

    }

    public void read2003(String filePath) throws FileNotFoundException, ParseException, IOException, BiffException {
        this.filePath = filePath;
        String[] str = filePath.split("\\.");
        if (procurementType.equalsIgnoreCase("Goods")) {
            if (str[1].equals("xls")) {
                try {
                    Workbook preservResWrkbk = Workbook.getWorkbook(uploadedFile.getInputstream());
                    Sheet preservResSheet = preservResWrkbk.getSheet(0);
                    int totalRows = preservResSheet.getRows();
                    int totalColumns = preservResSheet.getColumns();
                    String exclCategory = "";
                    String exclSubCategory = "";
                    String ExcelItemReg = "";
                    String ExcelUnitMeasure = "";
                    String ExcelEstimatedPrice = "";
                    String exclCurrency = "";
                    String exclOrigCountry = "";
                    String ExcelSourceAssessment = "";
                    String ExcelAvailabilty = "";
                    String ExcelRemark = "";
                    for (int i = 4; i < totalRows; i++) {
                        eepPmsMrktAsssmtInfoDetil = new PrmsMarketAssessmentDetail();

                        MmsSubCat mmsSubCat = new MmsSubCat();
                        int mmsSubCatId = Integer.parseInt(preservResSheet.getCell(1, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        mmsSubCat.setSubCatId(mmsSubCatId);
                        mmsSubCat = marketAssmntInfoBeanLocal.findBySubCatId(mmsSubCat);
                        eepPmsMrktAsssmtInfoDetil.setSubCategoryId(mmsSubCat);

                        MmsItemRegistration mmsItemRegistration = new MmsItemRegistration();
                        int mmsItemRegistrationId = Integer.parseInt(preservResSheet.getCell(2, i).getContents());
                        mmsItemRegistration.setMaterialId(mmsItemRegistrationId);
                        mmsItemRegistration = marketAssmntInfoBeanLocal.getByMaterialId(mmsItemRegistration);
                        eepPmsMrktAsssmtInfoDetil.setItemId(mmsItemRegistration);

                        exclCategory = (preservResSheet.getCell(3, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));

                        ExcelEstimatedPrice = (preservResSheet.getCell(4, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        eepPmsMrktAsssmtInfoDetil.setUnitPrice(BigDecimal.valueOf((Double.parseDouble(ExcelEstimatedPrice))));

                        FmsLuCurrency fmsLuCurrency = new FmsLuCurrency();
                        String fmsLuCurrencyId = (preservResSheet.getCell(5, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        fmsLuCurrency.setCurrencyId(fmsLuCurrencyId);
                        fmsLuCurrency = marketAssmntInfoBeanLocal.findCurrency(fmsLuCurrency);
                        eepPmsMrktAsssmtInfoDetil.setCurrencyId(fmsLuCurrency);

                        ComLuCountry comLuCountry = new ComLuCountry();
                        int comLuCountryId = Integer.parseInt(preservResSheet.getCell(6, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        comLuCountry.setId(comLuCountryId);
                        comLuCountry = marketAssmntInfoBeanLocal.findCountry(comLuCountry);
                        eepPmsMrktAsssmtInfoDetil.setCountryId(comLuCountry);

                        ExcelSourceAssessment = (preservResSheet.getCell(7, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        eepPmsMrktAsssmtInfoDetil.setSourceOfAssessment(ExcelSourceAssessment);

                        ExcelAvailabilty = (preservResSheet.getCell(8, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        eepPmsMrktAsssmtInfoDetil.setAvailabilty(ExcelAvailabilty);

                        ExcelRemark = (preservResSheet.getCell(9, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        eepPmsMrktAsssmtInfoDetil.setRemark(ExcelRemark);
                        eepPmsMrktAsssmtInfoDetil.setMarketAssmntId(eepPmsMarketAssessmentInfo);
                        marketAssessmentDetailList.add(eepPmsMrktAsssmtInfoDetil);
                        eepPmsMarketAssessmentInfo.getPrmsMarketAssessmentDetailList().add(eepPmsMrktAsssmtInfoDetil);
                    }

                    dataModel = null;
                    dataModel = new ListDataModel(new ArrayList<>(marketAssessmentDetailList));
                    eepPmsMrktAsssmtInfoDetil.setMarketAssmntId(eepPmsMarketAssessmentInfo);
                    preservResWrkbk.close();
                    flagToSave = 1;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (procurementType.equalsIgnoreCase("Service")) {

            if (str[1].equals("xls")) {
                try {
                    Workbook preservResWrkbk = Workbook.getWorkbook(uploadedFile.getInputstream());
                    Sheet preservResSheet = preservResWrkbk.getSheet(0);

                    int totalRows = preservResSheet.getRows();
                    int totalColumns = preservResSheet.getColumns();

                    String exclServiceType = "";
                    String exclServiceName = "";
                    String ExclUnitMeasure = "";
                    String ExclUnitePrice = "";
                    String ExcelSourceAssessment = "";
                    String ExcelAvailabilty = "";
                    String ExcelRemark = "";

                    for (int i = 4; i < totalRows; i++) {

                        PrmsMarketAssmntService prmsMarketAssmntService = new PrmsMarketAssmntService();

                        PrmsServiceAndWorkReg prmsServiceAndWorkReg = new PrmsServiceAndWorkReg();
                        int prmsServiceAndWorkRegId = Integer.parseInt(preservResSheet.getCell(0, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsServiceAndWorkReg.setServAndWorkId(BigDecimal.valueOf(prmsServiceAndWorkRegId));
                        prmsServiceAndWorkReg = marketAssmntInfoBeanLocal.findServiceFileUpLd(prmsServiceAndWorkReg);
                        prmsMarketAssmntService.setServiceId(prmsServiceAndWorkReg);

                        ExclUnitePrice = (preservResSheet.getCell(3, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsMarketAssmntService.setUnitPrice(BigDecimal.valueOf((Double.parseDouble(ExclUnitePrice))));
                        ExcelSourceAssessment = (preservResSheet.getCell(4, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsMarketAssmntService.setSourceOfAssessment(ExcelSourceAssessment);

                        ExcelAvailabilty = (preservResSheet.getCell(5, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsMarketAssmntService.setAvailabilty(ExcelAvailabilty);

                        ExcelRemark = (preservResSheet.getCell(6, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsMarketAssmntService.setRemark(ExcelRemark);
                        prmsMarketAssmntServicesList.add(prmsMarketAssmntService);
                        eepPmsMarketAssessmentInfo.getPrmsMarketAssmntServiceList().add(prmsMarketAssmntService);

                    }

                    marketAssmntServiceMdl = null;
                    marketAssmntServiceMdl = new ListDataModel(new ArrayList<>(prmsMarketAssmntServicesList));
                    preservResWrkbk.close();
                    flagToSave = 1;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (procurementType.equalsIgnoreCase("Work")) {

            if (str[1].equals("xls")) {
                try {
                    Workbook preservResWrkbk = Workbook.getWorkbook(uploadedFile.getInputstream());
                    Sheet preservResSheet = preservResWrkbk.getSheet(0);

                    int totalRows = preservResSheet.getRows();
                    int totalColumns = preservResSheet.getColumns();

                    String exclWorkName = "";
                    String ExclUnitMeasure = "";
                    String ExclUnitePrice = "";
                    String ExcelSourceAssessment = "";
                    String ExcelAvailabilty = "";
                    String ExcelRemark = "";

                    for (int i = 4; i < totalRows; i++) {

                        PrmsMarketAssmntService prmsMarketAssmntService = new PrmsMarketAssmntService();

                        PrmsServiceAndWorkReg prmsServiceAndWorkReg = new PrmsServiceAndWorkReg();
                        int prmsServiceAndWorkRegId = Integer.parseInt(preservResSheet.getCell(0, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsServiceAndWorkReg.setServAndWorkId(BigDecimal.valueOf(prmsServiceAndWorkRegId));
                        prmsServiceAndWorkReg = marketAssmntInfoBeanLocal.findServiceFileUpLd(prmsServiceAndWorkReg);
                        prmsMarketAssmntService.setServiceId(prmsServiceAndWorkReg);

                        ExclUnitePrice = (preservResSheet.getCell(2, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsMarketAssmntService.setUnitPrice(BigDecimal.valueOf((Double.parseDouble(ExclUnitePrice))));
                        ExcelSourceAssessment = (preservResSheet.getCell(3, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsMarketAssmntService.setSourceOfAssessment(ExcelSourceAssessment);

                        ExcelAvailabilty = (preservResSheet.getCell(4, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsMarketAssmntService.setAvailabilty(ExcelAvailabilty);

                        ExcelRemark = (preservResSheet.getCell(5, i).getContents().replace(" ", "").replace(",", "").replace(".", ""));
                        prmsMarketAssmntService.setRemark(ExcelRemark);
                        prmsMarketAssmntServicesList.add(prmsMarketAssmntService);
                        eepPmsMarketAssessmentInfo.getPrmsMarketAssmntServiceList().add(prmsMarketAssmntService);

                    }

                    marketAssmntServiceMdl = null;
                    marketAssmntServiceMdl = new ListDataModel(new ArrayList<>(prmsMarketAssmntServicesList));
                    preservResWrkbk.close();
                    flagToSave = 1;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void populateUI() {
        eepPmsMarketAssessmentInfo.setId(eepPmsMarketAssessmentInfo.getId());
        eepPmsMarketAssessmentInfo = marketAssmntInfoBeanLocal.getSelectedRequest(eepPmsMarketAssessmentInfo.getId());
        renderPnlManPage = false;
        renderPnlCreateParty = true;
        disableWhenUpdate = true;
        setIsRenderedIconWorkflow(true);
        procurementType = eepPmsMarketAssessmentInfo.getPurchaseType();
        clearDetail();
        if ("Goods".equals(procurementType)) {
            supplierName = true;
            consultancyAndContrtName = false;
        } else {
            supplierName = false;
            consultancyAndContrtName = true;
        }
        if ("Goods".equals(procurementType)) {
            renderitem = true;
            renderService = false;
            workName = false;
            serviceTbale = false;
            reacreteDataModel();

        } else if ("Service".equals(procurementType)) {
            renderitem = false;
            renderService = true;
            workName = false;
            serviceTbale = true;
            reCreatService();

        } else if ("Work".equals(procurementType)) {
            renderitem = false;
            renderService = false;
            workName = true;
            serviceTbale = true;
            reCreatService();
        }

        if (saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
            wfPrmsProcessed.setProcessedOn(eepPmsMarketAssessmentInfo.getRegistrationDate());
        }

        recreateWfDataModel();
    }

    public void recreateWfDataModel() {
        wfPrmsProcessedDModel = null;
        wfPrmsProcessedDModel = new ListDataModel(new ArrayList(eepPmsMarketAssessmentInfo.getWfPrmsProcessedLists()));
    }

    public Date getMinDate() {
        if (procurementType.equals("Goods")) {
            return this.eepPmsMrktAsssmtInfoDetil.getFromYear();
        } else if (procurementType.equals("Service")) {
            return this.prmsMarketAssmntService.getFromYear();
        } else {
            return this.prmsMarketAssmntService.getFromYear();
        }
    }

    public void Edit(PrmsMarketAssessment prmsMarketAssessment) {
        marketAssmntInfoBeanLocal.edit(prmsMarketAssessment);
    }

    public void removeContactPersonInfo() {
        int rowIndex = dataModel.getRowIndex();
        eepPmsMrktAsssmtInfoDetil = eepPmsMarketAssessmentInfo.getPrmsMarketAssessmentDetailList().get(rowIndex);
        eepPmsMarketAssessmentInfo.getPrmsMarketAssessmentDetailList().remove(rowIndex);
        reacreteDataModel();
        if (saveupdate.equals("Update")) {
            marketAssmntInfoBeanLocal.deleteContactPersonInfo(eepPmsMrktAsssmtInfoDetil);
        }
    }

    public void reacreteDataModel() {
        dataModel = null;
        dataModel = new ListDataModel(new ArrayList<>(eepPmsMarketAssessmentInfo.getPrmsMarketAssessmentDetailList()));
    }

    public void recreat() {
        marketAssessmentModel = null;
        marketAssessmentModel = new ListDataModel(new ArrayList(getPrmsMarketAssessmentLst()));
    }

    public void reCreatService() {
        marketAssmntServiceMdl = null;
        marketAssmntServiceMdl = new ListDataModel(new ArrayList<>(eepPmsMarketAssessmentInfo.getPrmsMarketAssmntServiceList()));
    }

    public void clearDetail() {
        eepPmsMrktAsssmtInfoDetil = null;
        prmsMarketAssmntService = null;
        mmsItemRegistration = null;
        mmsCategory = null;
        mmsSubCat = null;
        comLuCountry = null;
        fmsLuCurrency = null;
        addOrModifyBundle = "Add";
        prmsServiceAndWorkReg = null;
        prmsBid = null;
        disableDetailWhenUpdate = false;

    }

    public void clear() {
        eepPmsMarketAssessmentInfo = null;
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
        saveorUpdateBundle = "Save";
        addOrModifyBundle = "Add";
        marketAssmntServiceMdl = null;
        dataModel = null;
        wfPrmsProcessed = null;
        newMarketNo = null;
        procurementType = "Goods";
        disableWhenUpdate = false;
        renderPnlToSearchPage = false;
        clearDetail();
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Save or Update methods">
    public String save_marketAssessmnt() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(SessionBean.getUserName(), "save_marketAssessmnt", dataset)) {

                try {
                    if (eepPmsMarketAssessmentInfo.getPrmsMarketAssessmentDetailList().size() > 0 || eepPmsMarketAssessmentInfo.getPrmsMarketAssmntServiceList().size() > 0) {
                        if (saveorUpdateBundle.equals("Save")) {
                            generateMarketNo();
                            eepPmsMarketAssessmentInfo.setMarketAssNo(newMarketNo);
                            wfPrmsProcessed.setMarketId(eepPmsMarketAssessmentInfo);
                            eepPmsMarketAssessmentInfo.setPreparedby(wfPrmsProcessed.getProcessedBy());
                            wfPrmsProcessed.setDecision(String.valueOf(eepPmsMarketAssessmentInfo.getStatus()));
                            eepPmsMarketAssessmentInfo.setRegistrationDate(wfPrmsProcessed.getProcessedOn());
                            eepPmsMarketAssessmentInfo.setStatus(Constants.PREPARE_VALUE);
                            marketAssmntInfoBeanLocal.save(eepPmsMarketAssessmentInfo);
                            eepPmsMarketAssessmentInfo.setPurchaseType(procurementType);
                            eepPmsMarketAssessmentInfo.getWfPrmsProcessedLists().add(wfPrmsProcessed);
                            JsfUtil.addSuccessMessage("Market Information is Saved Successfully");
                            clear();
                        } else if (saveorUpdateBundle.equals("Update") && workFlow.isPrepareStatus()) {
                            wfPrmsProcessed.setMarketId(eepPmsMarketAssessmentInfo);
                            eepPmsMarketAssessmentInfo.setPurchaseType(procurementType);
                            eepPmsMarketAssessmentInfo.setRegistrationDate(wfPrmsProcessed.getProcessedOn());
                            eepPmsMarketAssessmentInfo.setPreparedby(wfPrmsProcessed.getProcessedBy());
                            marketAssmntInfoBeanLocal.edit(eepPmsMarketAssessmentInfo);
                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            saveorUpdateBundle = "Save";
                            wfPrmsProcessed = null;
                            JsfUtil.addSuccessMessage("Market Information is Updated Successfully");
                            clear();
                        } else if (saveorUpdateBundle.equals("Update") && (workFlow.isApproveStatus() || workFlow.isCheckStatus())) {
                            if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isApproveStatus()) {
                                workFlow.setUserStatus(Constants.APPROVE_VALUE);
                                eepPmsMarketAssessmentInfo.setStatus(Constants.APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Approved") && workFlow.isCheckStatus()) {
                                workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                                eepPmsMarketAssessmentInfo.setStatus(workFlow.getUserStatus());
                            } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isApproveStatus()) {
                                workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                                eepPmsMarketAssessmentInfo.setStatus(workFlow.getUserStatus());
                            } else if (selectedValue.equalsIgnoreCase("Rejected") && workFlow.isCheckStatus()) {
                                workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                                eepPmsMarketAssessmentInfo.setStatus(workFlow.getUserStatus());
                            }
                            marketAssmntInfoBeanLocal.edit(eepPmsMarketAssessmentInfo);
                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            wfPrmsProcessed = null;
                            JsfUtil.addSuccessMessage("Market Information is Approved Successfully");
                            clear();
                        }
                    } else {
                        JsfUtil.addFatalMessage("Please Add Data to Detail  Before Save");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addErrorMessage("Sorry Data is Not saved");
                }
            } else {
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);

                //..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }
 // </editor-fold>
}
