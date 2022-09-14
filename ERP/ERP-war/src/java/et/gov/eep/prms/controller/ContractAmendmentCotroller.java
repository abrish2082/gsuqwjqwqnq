package et.gov.eep.prms.controller;

import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.GregorianCalendarManipulation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.prms.businessLogic.AwardDetailBeanLocal;
import et.gov.eep.prms.businessLogic.ContractInformationBeanLocal;
import et.gov.eep.prms.businessLogic.ContractAmendmentBeanLocal;
import et.gov.eep.prms.businessLogic.InsuranceNotificationToBankBeanLocal;
import et.gov.eep.prms.businessLogic.PurchaseOrderBeanLocal;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.entity.PrmsContractAmendmentDt;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import et.gov.eep.prms.entity.PrmsContractamendCurrency;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;

@Named(value = "contractAmendmentCotroller")
@ViewScoped
public class ContractAmendmentCotroller implements Serializable {

    @EJB
    private PurchaseOrderBeanLocal purchaseOrderBeanLocal;
    @EJB
    private ContractInformationBeanLocal contractInformationBeanLocal;
    @EJB
    private AwardDetailBeanLocal awardDetailBeanLocal;
    @EJB
    private ContractAmendmentBeanLocal contractAmendmentBeanLocal;
    @EJB
    FmsLuCurrencyFacade fmsLuCurrencyFacade;
    @EJB
    InsuranceNotificationToBankBeanLocal insuranceNotificationToBankBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @Inject
    SessionBean SessionBean;
    @Inject
    private PrmsContractamendCurrency papmsContractCurrDetail;
    @Inject
    private PrmsContract prmsContract;
    @Inject
    private PrmsContractDetail prmsContractDetail;
    @Inject
    private PrmsAward papmsAward;
    @Inject
    private PrmsContractAmendment prmsContractAmendment;
    @Inject
    private PrmsBid eepBidReg;
    @Inject
    private PrmsContractAmendmentDt prmsContractAmendmentDt;
    @Inject
    private PrmsAwardDetail papmsAwardDetail;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    PrmsPortEntry prmsPortEntry;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    WorkFlow workFlow;
    @Inject
    private PrmsContractamendCurrency papmsContractAmeCurrDetail;
    double vat = 0.15;
    double groundTotal = 0.0;
    double totalSum = 0.0;
    double totalvat = 0.0;
    double totalPrice = 0.0;
    int saveStatus = 0;
    int updateStatus = 0;

    private boolean chooseselecttOption;
    private boolean disableBefrDay = true;
    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean renderForIntnalSupplier = false;
    private boolean chooserenderForLocal = false;
    boolean renderDecision;
    boolean isRenderCreate;
    private boolean renderpnlToSearchPage;
    private String icone = "ui-icon-plus";
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private String createSearch = "Search";
    private String duplicattion = null;
    private String activeIndex;
    private String selectedValue = "";
    private String saveorUpdateBundle = "Amend";
    String supplierType = "Local";
    String selecttOption = "hidden";
    String localreader = "hidden";
    private String selectOptPartyName;
//    private String icone = "ui-icon-plus";
//    private String icone2 = "ui-icon-search";
    Set<String> addressCheck = new HashSet<>();
    String interna = "Local";
    private String userName;
    private PrmsContractAmendment amendmentSelection;

    DataModel<PrmsContractAmendment> prmsContractAmendmentModel;
    DataModel<PrmsContractAmendmentDt> prmsContractAmendmentDtModel;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<PrmsContractamendCurrency> prmsContractCurrencyModel;
    List<PrmsContractAmendment> prmsContractAmendments;
    List<PrmsContractAmendment> contracAmendNoLists;
    private List<PrmsAwardDetail> awardDetailslist = new ArrayList<>();
    List<PrmsAwardDetail> prmsAwardDetails;
    List<FmsLuCurrency> luCurrencysLists;
    List<PrmsPortEntry> portsLists;
    List<FmsLuCurrency> savedCurrencyIds = new ArrayList<>();
    Set<FmsLuCurrency> checkEvaluation = new HashSet<>();
    List<PrmsContractAmendment> contractAmendSearchParameterLst;
    private List<PrmsAward> allAwardList;
    private List<PrmsContract> prmsContractList;
    private List<PrmsContractamendCurrency> prmsContractAmeList;
    List<FmsLuCurrency> fmsLuCurrencyList = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public ContractAmendmentCotroller() {
    }

    public boolean isDisableBefrDay() {
        return disableBefrDay;
    }

    public void setDisableBefrDay(boolean disableBefrDay) {
        this.disableBefrDay = disableBefrDay;
    }

    public AwardDetailBeanLocal getAwardDetailBeanLocal() {
        return awardDetailBeanLocal;
    }

    public void setAwardDetailBeanLocal(AwardDetailBeanLocal awardDetailBeanLocal) {
        this.awardDetailBeanLocal = awardDetailBeanLocal;
    }

    public PrmsContractamendCurrency getPapmsContractAmeCurrDetail() {

        if (papmsContractAmeCurrDetail == null) {
            papmsContractAmeCurrDetail = new PrmsContractamendCurrency();
        }
        return papmsContractAmeCurrDetail;
    }

    public void setPapmsContractAmeCurrDetail(
            PrmsContractamendCurrency papmsContractAmeCurrDetail) {
        this.papmsContractAmeCurrDetail = papmsContractAmeCurrDetail;
    }

    public PrmsContractamendCurrency getPapmsContractCurrDetail() {

        if (papmsContractCurrDetail == null) {
            papmsContractCurrDetail = new PrmsContractamendCurrency();
        }
        return papmsContractCurrDetail;
    }

    public void setPapmsContractCurrDetail(
            PrmsContractamendCurrency papmsContractCurrDetail) {
        this.papmsContractCurrDetail = papmsContractCurrDetail;
    }

//    public DataModel<PrmsContractamendCurrency> getPrmsContractCurrencyModel() {
//        if (prmsContractCurrencyModel == null) {
//            prmsContractCurrencyModel = new ListDataModel<>();
//        }
//        return prmsContractCurrencyModel;
//    }
//
//    public void setPrmsContractCurrencyModel(
//            DataModel<PrmsContractamendCurrency> prmsContractCurrencyModel) {
//        this.prmsContractCurrencyModel = prmsContractCurrencyModel;
//    }
    @PostConstruct
    public void init() {

        ContractAmenLis1 = contractAmendmentBeanLocal.getContAmendedNoByContNoStatus();
        setChooserenderForLocal(true);
        setUserName(workFlow.getUserName());
        setFmsLuCurrencyList(contractInformationBeanLocal.getCurrencylist());
        setLogerName(SessionBean.getUserName());
        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());

        if (workFlow.isPrepareStatus()) {
            renderDecision = false;
            isRenderCreate = true;
        }
        if (workFlow.isApproveStatus()) {
            renderDecision = true;
            isRenderCreate = false;
        }
        if (workFlow.isCheckStatus()) {
            renderDecision = true;
            isRenderCreate = false;
        }
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public WorkFlow getWorkFlow() {

        if (workFlow == null) {
            workFlow = new WorkFlow();
        }

        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public PrmsContract getPrmsContract() {

        if (prmsContract == null) {
            prmsContract = new PrmsContract();
        }
        return prmsContract;
    }

    public void setPrmsContract(PrmsContract prmsContract) {
        this.prmsContract = prmsContract;
    }

    public SelectItem[] getListOfVendorName() {
        return JsfUtil.getSelectItems(contractInformationBeanLocal.VendorName(), true);

    }

    public SelectItem[] bidReferenceNoFromBidSale() {
        return JsfUtil.getSelectItems(contractInformationBeanLocal.BidNoForCheck(), true);

    }

    public SelectItem[] getAwardLists() {
        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.getAwardLists(), true);

    }

    public SelectItem[] getListdepartment() {
        return JsfUtil.getSelectItems(contractInformationBeanLocal.searchdeptName(), true);

    }

    public PrmsAward getPapmsAward() {
        if (papmsAward == null) {
            papmsAward = new PrmsAward();
        }
        return papmsAward;
    }

    public void setPapmsAward(PrmsAward papmsAward) {
        this.papmsAward = papmsAward;
    }

    public PrmsContractAmendment getPrmsContractAmendment() {
        if (prmsContractAmendment == null) {
            prmsContractAmendment = new PrmsContractAmendment();
        }
        return prmsContractAmendment;
    }

    public void setPrmsContractAmendment(PrmsContractAmendment prmsContractAmendment) {
        this.prmsContractAmendment = prmsContractAmendment;
    }

    public PrmsBid getEepBidReg() {
        return eepBidReg;
    }

    public void setEepBidReg(PrmsBid eepBidReg) {
        this.eepBidReg = eepBidReg;
    }

    public PrmsContractAmendmentDt getPrmsContractAmendmentDt() {
        if (prmsContractAmendmentDt == null) {
            prmsContractAmendmentDt = new PrmsContractAmendmentDt();
        }
        return prmsContractAmendmentDt;
    }

    public void setPrmsContractAmendmentDt(PrmsContractAmendmentDt prmsContractAmendmentDt) {
        this.prmsContractAmendmentDt = prmsContractAmendmentDt;
    }

    public PrmsContractDetail getPrmsContractDetail() {
        return prmsContractDetail;
    }

    public void setPrmsContractDetail(PrmsContractDetail prmsContractDetail) {
        this.prmsContractDetail = prmsContractDetail;
    }

    public PrmsAwardDetail getPapmsAwardDetail() {

        if (papmsAwardDetail == null) {
            papmsAwardDetail = new PrmsAwardDetail();
        }
        return papmsAwardDetail;
    }

    public void setPapmsAwardDetail(PrmsAwardDetail papmsAwardDetail) {
        this.papmsAwardDetail = papmsAwardDetail;
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

    public PrmsPortEntry getPrmsPortEntry() {
        return prmsPortEntry;
    }

    public void setPrmsPortEntry(PrmsPortEntry prmsPortEntry) {
        this.prmsPortEntry = prmsPortEntry;
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

    public List<FmsLuCurrency> getLuCurrencysLists() {

        if (luCurrencysLists == null) {
            luCurrencysLists = new ArrayList<>();
            luCurrencysLists = fmsLuCurrencyFacade.findAll();
        }
        return luCurrencysLists;
    }

    public void setLuCurrencysLists(List<FmsLuCurrency> luCurrencysLists) {
        this.luCurrencysLists = luCurrencysLists;
    }

    String slected = "Select One";

    public String getInterna() {
        return interna;
    }

    public void setInterna(String interna) {
        this.interna = interna;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public List<PrmsAwardDetail> getAwardDetailslist() {
        return awardDetailslist;
    }

    public void setAwardDetailslist(List<PrmsAwardDetail> awardDetailslist) {
        this.awardDetailslist = awardDetailslist;
    }

    public List<PrmsAwardDetail> getPrmsAwardDetails() {
        return prmsAwardDetails;
    }

    public void setPrmsAwardDetails(List<PrmsAwardDetail> prmsAwardDetails) {
        this.prmsAwardDetails = prmsAwardDetails;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getGroundTotal() {
        return groundTotal;
    }

    public void setGroundTotal(double groundTotal) {
        this.groundTotal = groundTotal;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public double getTotalvat() {
        return totalvat;
    }

    public void setTotalvat(double totalvat) {
        this.totalvat = totalvat;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSelecttOption() {
        return selecttOption;
    }

    public void setSelecttOption(String selecttOption) {
        this.selecttOption = selecttOption;
    }

    public String getLocalreader() {
        return localreader;
    }

    public void setLocalreader(String localreader) {
        this.localreader = localreader;
    }

    public boolean isChooseselecttOption() {
        return chooseselecttOption;
    }

    public void setChooseselecttOption(boolean chooseselecttOption) {
        this.chooseselecttOption = chooseselecttOption;
    }

    public String getAddressInfoButton() {
        return addressInfoButton;
    }

    public void setAddressInfoButton(String addressInfoButton) {
        this.addressInfoButton = addressInfoButton;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getCreateSearch() {
        return createSearch;
    }

    public void setCreateSearch(String createSearch) {
        this.createSearch = createSearch;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public String getDuplicattion() {
        return duplicattion;
    }

    public void setDuplicattion(String duplicattion) {
        this.duplicattion = duplicattion;
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

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
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

    public DataModel<PrmsContractAmendment> getPrmsContractAmendmentModel() {
        if (prmsContractAmendmentModel == null) {
            prmsContractAmendmentModel = new ArrayDataModel<>();
        }
        return prmsContractAmendmentModel;
    }

    public void setPrmsContractAmendmentModel(DataModel<PrmsContractAmendment> prmsContractAmendmentModel) {
        this.prmsContractAmendmentModel = prmsContractAmendmentModel;
    }

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel() {

        if (wfPrmsProcessedDModel == null) {
            wfPrmsProcessedDModel = new ArrayDataModel<>();
        }

        return wfPrmsProcessedDModel;
    }

    public void setWfPrmsProcessedDModel(
            DataModel<WfPrmsProcessed> wfPrmsProcessedDModel) {

        this.wfPrmsProcessedDModel = wfPrmsProcessedDModel;
    }

    public DataModel<PrmsContractAmendmentDt> getPrmsContractAmendmentDtModel() {
        if (prmsContractAmendmentDtModel == null) {
            prmsContractAmendmentDtModel = new ArrayDataModel<>();
        }
        return prmsContractAmendmentDtModel;
    }

    public void setPrmsContractAmendmentDtModel(DataModel<PrmsContractAmendmentDt> prmsContractAmendmentDtModel) {
        this.prmsContractAmendmentDtModel = prmsContractAmendmentDtModel;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    public boolean isChooserenderForLocal() {
        return chooserenderForLocal;
    }

    public void setChooserenderForLocal(boolean chooserenderForLocal) {
        this.chooserenderForLocal = chooserenderForLocal;
    }

    public boolean isRenderForIntnalSupplier() {
        return renderForIntnalSupplier;
    }

    public void setRenderForIntnalSupplier(boolean renderForIntnalSupplier) {
        this.renderForIntnalSupplier = renderForIntnalSupplier;
    }

    public List<PrmsContractAmendment> getContractAmendSearchParameterLst() {
         if (contractAmendSearchParameterLst == null) {
            contractAmendSearchParameterLst = new ArrayList<>();
            contractAmendSearchParameterLst = contractAmendmentBeanLocal.getParamNameList();
        }
        return contractAmendSearchParameterLst;
    }

    public void setContractAmendSearchParameterLst(List<PrmsContractAmendment> contractAmendSearchParameterLst) {
        this.contractAmendSearchParameterLst = contractAmendSearchParameterLst;
    }
 public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            prmsContractAmendment.setColumnName(event.getNewValue().toString());
            prmsContractAmendment.setColumnValue(null);
        }
    }
    public void createNewParty() {

        clearContractPage();
        saveorUpdateBundle = "Amend";
        disableBtnCreate = false;
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        createOrSearchBundle = "New";
    }

    public void newBtnAction() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        clearContractPage();
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }

    public void goBackToSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public List<PrmsContractAmendment> getPrmsContractAmendments() {
        if (prmsContractAmendments == null) {
            prmsContractAmendments = new ArrayList<>();
        }
        return prmsContractAmendments;
    }

    public void setPrmsContractAmendments(List<PrmsContractAmendment> prmsContractAmendments) {
        this.prmsContractAmendments = prmsContractAmendments;
    }

    public void recreateprmsContractAmendmentDtModel() {

        if (prmsContract.getPrmsContractDetailList().size() > 0) {

            for (int i = 0; i < prmsContractAmendment.getPrmsContractAmendmentDtList().size(); i++) {
                prmsContractAmendment.getPrmsContractAmendmentDtList().get(i).setTotals(
                        prmsContractAmendment.getPrmsContractAmendmentDtList().get(i).getUnitPrice().doubleValue()
                        * prmsContractAmendment.getPrmsContractAmendmentDtList().get(i).getQuantity().doubleValue());
                totalSum = totalSum + prmsContractAmendment.getPrmsContractAmendmentDtList().get(i).getTotals();
            }
            totalvat = 0.15 * totalSum;
            groundTotal = totalvat + totalSum;
            recreateprmsContractAmendmentDetModel();
        }
    }

    public void recreateprmsContractAmendmentDetModel() {

        prmsContractAmendmentDtModel = null;
        prmsContractAmendmentDtModel = new ListDataModel(
                (prmsContractAmendment.getPrmsContractAmendmentDtList()));
    }

    public void recreateprmsContractAmendmentModel() {

        prmsContractAmendmentModel = null;
        prmsContractAmendmentModel = new ListDataModel(
                new ArrayList<>(getPrmsContractAmendments()));
    }

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = prmsContractAmendmentModel.getRowIndex();
        prmsContractAmendment = prmsContractAmendments.get(rowIndex);
        recreateprmsContractAmendmentDtModel();
        recreateprmsContractAmendmentModel();
    }

    public void onContactRowEdit(RowEditEvent event) {

        int rowIndex = prmsContractAmendmentDtModel.getRowIndex();
        PrmsContractAmendmentDt comContPerson = (PrmsContractAmendmentDt) event.getObject();
        prmsContractAmendmentDt = (PrmsContractAmendmentDt) prmsContractAmendmentDtModel.getRowData();
        boolean found = false;

        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");
            prmsContractAmendment.getPrmsContractAmendmentDtList().set(rowIndex, comContPerson);
            recreateprmsContractAmendmentDtModel();
        } else {
            prmsContractAmendment.getPrmsContractAmendmentDtList().set(rowIndex, comContPerson);
            recreateprmsContractAmendmentDtModel();
        }

        calc_();
    }

    public void removeContactPersonInfo() {

        int rowIndex = prmsContractAmendmentDtModel.getRowIndex();
        prmsContractAmendmentDt = prmsContractAmendment.getPrmsContractAmendmentDtList().get(rowIndex);
        prmsContractAmendment.getPrmsContractAmendmentDtList().remove(rowIndex);
        recreateprmsContractAmendmentDtModel();
    }

    public boolean CheckValueExisted() {

        if (checkEvaluation.contains(fmsLuCurrency)) {

            return true;
        }
        return false;
    }

    int rowIndexCurr;

    public int getRowIndexCurr() {
        return rowIndexCurr;
    }

    public void setRowIndexCurr(int rowIndexCurr) {
        this.rowIndexCurr = rowIndexCurr;
    }

    boolean existedData = false;

    public void changeEventCurrencyID2(ValueChangeEvent event) {

        try {
            existedData = false;
            fmsLuCurrency = (FmsLuCurrency) event.getNewValue();// papmsContractCurrDetail = null;
            papmsContractCurrDetail = prmsContractCurrencyModel.getRowData();
            setRowIndexCurr(prmsContractCurrencyModel.getRowIndex());
            Iterator<PrmsContractamendCurrency> currDet = prmsContractCurrencyModel.iterator();
            boolean duplicateEntry = false;
            boolean duplicateEntrySecond = false;
            duplicateEntry = CheckValueExisted();
            checkEvaluation.clear();
            FmsLuCurrency tempCurr = new FmsLuCurrency();

            for (int i = 0; i < prmsContractCurrencyModel.getRowCount(); i++) {

                tempCurr = currDet.next().getCurrencyId();
                if (tempCurr == null && prmsContractCurrencyModel.getRowCount() == 1) {
                    existedData = true;
                    System.out.println("A1 true");
                    checkEvaluation.add(fmsLuCurrency);
                } else {
                    checkEvaluation.add(tempCurr);
                }
            }

            duplicateEntrySecond = CheckValueExisted();// Check Duplication after regenerating Sets

            if (!duplicateEntrySecond) {
                savedCurrencyIds.add(fmsLuCurrency);// papmsContract.addCOntractCurrInfo(papmsContractCurrDetail);
                checkEvaluation.add(fmsLuCurrency);
                papmsContractCurrDetail.setContractId(prmsContractAmendment);
                papmsContractCurrDetail.setCurrencyId(fmsLuCurrency);//set Up for Currency Detail Information
                prmsContractAmendment.getPrmsContractACurrencyDetailList().set(getRowIndexCurr(), papmsContractCurrDetail);

                recreateContractCurrDetailModel();//RECREATED Currency ID 
            } else {
                if (duplicateEntry == false && existedData == true) {
                    savedCurrencyIds.add(fmsLuCurrency);// papmsContract.addCOntractCurrInfo(papmsContractCurrDetail);
                    checkEvaluation.add(fmsLuCurrency);
                    papmsContractCurrDetail.setContractId(prmsContractAmendment);
                    papmsContractCurrDetail.setCurrencyId(fmsLuCurrency);//set Up for Currency Detail Information
                    prmsContractAmendment.getPrmsContractACurrencyDetailList().set(getRowIndexCurr(), papmsContractCurrDetail);

                    recreateContractCurrDetailModel();//RECREATED Currency ID 
                } else {
                    JsfUtil.addFatalMessage("Duplicate Entry ! Please Select unique Currency.");
                    fmsLuCurrency = null;
                    papmsContractCurrDetail.setContractId(prmsContractAmendment);
                    papmsContractCurrDetail.setCurrencyId(fmsLuCurrency);//set Up for Currency Detail Information
                    prmsContractAmendment.getPrmsContractACurrencyDetailList().set(getRowIndexCurr(), papmsContractCurrDetail);

                    recreateContractCurrDetailModel();//RECREATED Currency ID        
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ***********************************************************************
     */
    public String saveContractAmendment() {//Working

        AAA securityService = new AAA();
        IAdministration security
                = securityService.getMetadataExchangeHttpBindingIAdministration();// String systemBundle = "cfg/securityServer";
        String systemBundle = "et/gov/eep/commonApplications/securityServer";
        String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

        if (security.checkAccess(SessionBean.getUserName(),
                "saveContractAmendment", dataset)) {

            if (updateStatus == 0) {
                try {
                    generateSpecficationNo();
                    prmsContractAmendment.setContractAmendNo(newcheckListNo);
                    prmsContractAmendment.setContractId(prmsContract);// System.out.println("1===cont Id ==" + prmsContractAmendment.getContractId());
                    prmsContractAmendment.setContractPeriodTo(prmsContract.getContractPeriodTo());
                    //prmsContractAmendment.setContractDate(prmsContract.getContractsigningDate());
                    prmsContractAmendment.setCommencmentDate(prmsContract.getCommencmentDate());
                    prmsContractAmendment.setContractName(prmsContract.getContractName());
                    prmsContractAmendment.setDocumentId(prmsContract.getDocumentId());
                    prmsContractAmendment.setDeliveryPlace(prmsContract.getDeliveryPlace());// prmsContractAmendment.setPerformanceGarante(prmsContract.getPerformanceGarante());
                    prmsContractAmendment.setContractamount(prmsContract.getContractamount());
                    prmsContractAmendment.setIncomTerm(prmsContract.getIncoTerm());;
                    prmsContractAmendment.setPaymenttype(prmsContract.getPaymenttype());// prmsContractAmendment.setContractperiodtype(prmsContract.getContractperiodtype());// prmsContractAmendment.setPaymenttype(prmsContract.getPaymenttype());
                    prmsContractAmendment.setProjectId(prmsContract.getProjectId());
                    prmsContractAmendment.setSuppId(prmsContract.getSuppId());
                    prmsContractAmendment.setPreparedBy(String.valueOf(SessionBean.getUserId()));
                    Date currentDate = Calendar.getInstance().getTime();
                    prmsContractAmendment.setStatus(Constants.PREPARE_VALUE);
                    wfPrmsProcessed.setContractAmendId(prmsContractAmendment);
                    wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
                    wfPrmsProcessed.setDecision(String.valueOf(prmsContractAmendment.getStatus()));//-
                    wfPrmsProcessed.setProcessedBy(workFlow.getUserAccount());
                    prmsContractAmendment.setRigistrationDate(currentDate);

                    for (int i = 0; i < savedCurrencyIds.size(); i++) {
                        papmsContractCurrDetail.setContractId(prmsContractAmendment);
                        papmsContractCurrDetail.setCurrencyId(savedCurrencyIds.get(i));
                        prmsContractAmendment.addCOntractCurrInfo(papmsContractCurrDetail);
                    }

                    contractAmendmentBeanLocal.create(prmsContractAmendment);
                    wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                    savedCurrencyIds.clear();
                    checkEvaluation.clear();
                    prmsContractAmendment.setPrmsContractACurrencyDetailList(null);

                    JsfUtil.addSuccessMessage("Contract amendment Information is "
                            + "Successfuly registered at" + newcheckListNo);

                    return clearContractPage();

                } catch (Exception e) {
                    e.printStackTrace();
                    JsfUtil.addErrorMessage("Something Error Occured on Data Saved" + e);

                    return null;
                }

            } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equals("Update")) {

                prmsContractAmendment.setContractAmendNo(newcheckListNo);

                for (int i = 0; i < savedCurrencyIds.size(); i++) {
                    papmsContractCurrDetail.setContractId(prmsContractAmendment);
                    papmsContractCurrDetail.setCurrencyId(savedCurrencyIds.get(i));
                    prmsContractAmendment.addCOntractCurrInfo(papmsContractCurrDetail);
                }

                contractAmendmentBeanLocal.update(prmsContractAmendment);
                JsfUtil.addSuccessMessage("Contract amendment Information is Successfuly Updated at" + prmsContractAmendment.getContractAmendNo());
                savedCurrencyIds.clear();
                checkEvaluation.clear();
                prmsContractAmendment.setPrmsContractACurrencyDetailList(null);
                clearContractPage();
                saveorUpdateBundle = "Save";

                return clearContractPage();

            } else if (workFlow.isApproveStatus() && saveorUpdateBundle.equals("Update")) {

                if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                    workFlow.setUserStatus(Constants.APPROVE_VALUE);
                    prmsContractAmendment.setStatus(Constants.APPROVE_VALUE);
                    wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                    workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                    prmsContractAmendment.setStatus(Constants.CHECK_APPROVE_VALUE);
                    wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                    workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                    prmsContractAmendment.setStatus(Constants.APPROVE_REJECT_VALUE);
                    wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                    workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                    prmsContractAmendment.setStatus(Constants.CHECK_REJECT_VALUE);
                    wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                }

                prmsContractAmendment.setContractAmendNo(newcheckListNo);
                contractAmendmentBeanLocal.update(prmsContractAmendment);
                wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
                wfPrmsProcessed.setDecision(String.valueOf(prmsContractAmendment.getStatus()));
                wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                JsfUtil.addSuccessMessage("Contract Amendment Information is Successfuly Updated at" + prmsContractAmendment.getContractAmendNo());
                savedCurrencyIds.clear();
                checkEvaluation.clear();
                clearContractPage();
                prmsContractAmendment.setPrmsContractACurrencyDetailList(null);
                saveorUpdateBundle = "Save";

                return clearContractPage();
            }
        } else {
            JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
            EventEntry eventEntry = new EventEntry();
            eventEntry.setSessionId(SessionBean.getSessionID());
            eventEntry.setUserId(SessionBean.getUserId());
            QName qualifiedName = new QName("", "project");
            JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, SessionBean.getUserName());
            eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
            security.addEventLog(eventEntry, dataset);
        }
        return null;
    }

    /**
     * *************************************************************************
     *
     * @return
     * ***********************************************************************
     */
    private String clearContractPage() {

        prmsContract = null;
        prmsContractAmendment = null;
        prmsContractAmendmentDtModel = null;
        prmsContractAmendmentModel = null;
        prmsContractCurrencyModel = null;
        prmsContractAmendments = null;
        saveorUpdateBundle = "save";
        groundTotal = 0.0;
        totalSum = 0.0;
        totalvat = 0.0;
        updateStatus = 0;

        return null;
    }

    List<PrmsContractDetail> prmsContractDetails;
    List<PrmsContractAmendmentDt> prmsContractAmendmentDts;

    public List<PrmsContractAmendmentDt> getPrmsContractAmendmentDts() {

        return prmsContractAmendmentDts;
    }

    public void setPrmsContractAmendmentDts(
            List<PrmsContractAmendmentDt> prmsContractAmendmentDts) {
        this.prmsContractAmendmentDts = prmsContractAmendmentDts;
    }

    public List<PrmsContractDetail> getPrmsContractDetails() {
        return prmsContractDetails;
    }

    public void setPrmsContractDetails(
            List<PrmsContractDetail> prmsContractDetails) {
        this.prmsContractDetails = prmsContractDetails;
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ***********************************************************************
     */
    public void valueChangeContractNo(ValueChangeEvent event) {

        if (event.getNewValue() != null) {

            prmsContractAmendment.getPrmsContractAmendmentDtList().clear();
            groundTotal = 0.0;
            totalSum = 0.0;
            totalvat = 0.0;
            int quantity = 0;
            long unitprice = 0;
            prmsContract = (PrmsContract) event.getNewValue();
            prmsContractAmendment.setAwardId((prmsContract.getAwardId()));
            prmsContractAmendment.setBidId((prmsContract.getBidId()));
            contracAmendNoLists
                    = contractAmendmentBeanLocal.getContAmendedNoByContNo(
                            prmsContract);
            prmsContractAmendment.setContractPeriodTo(prmsContract.getContractPeriodTo());
            prmsContractAmendment.setCommencmentDate(prmsContract.getCommencmentDate());
            prmsContractAmendment.setContractName(prmsContract.getContractName());
            prmsContractAmendment.setDocumentId(prmsContract.getDocumentId());
            prmsContractAmendment.setDeliveryPlace(prmsContract.getDeliveryPlace());
            prmsContractAmendment.setPerformanceGarante(
                    prmsContract.getPerformanceGarante());
            prmsContractAmendment.setContractamount(prmsContract.getContractamount());
            prmsContractAmendment.setIncomTerm(prmsContract.getIncoTerm());
            prmsContractAmendment.setPaymenttype(prmsContract.getPaymenttype());
            prmsContractAmendment.setProjectId(prmsContract.getProjectId());
            prmsContractAmendment.setSuppId(prmsContract.getSuppId());
            recreateContractCurrDetailModel();

            for (int i = 0; i < prmsContract.getPrmsContractCurrencyDetailList().size(); i++) {

                PrmsContractCurrencyDetail tempConCurr = new PrmsContractCurrencyDetail();
                PrmsContractamendCurrency tempConAmeCurr = new PrmsContractamendCurrency();
                tempConCurr = prmsContract.getPrmsContractCurrencyDetailList().get(i);
                tempConAmeCurr.setAmount(tempConCurr.getAmount());
                tempConAmeCurr.setCurrencyId(tempConCurr.getCurrencyId());
                tempConAmeCurr.setContractId(prmsContractAmendment);
                prmsContractAmendment.getPrmsContractACurrencyDetailList().add(tempConAmeCurr);
            }
            recreateContractCurrDetailModel();

            for (int i = 0; i < prmsContract.getPrmsContractDetailList().size(); i++) {

                if (prmsContractAmendment.getPrmsContractAmendmentDtList() != null) {

                    prmsContractAmendmentDt.setMaterialId(prmsContract.getPrmsContractDetailList().get(i).getItemId());
                    prmsContractAmendmentDt.setQuantity(prmsContract.getPrmsContractDetailList().get(i).getQuantity());
                    prmsContractAmendmentDt.setUnitPrice(prmsContract.getPrmsContractDetailList().get(i).getUnitPrice());
                    quantity = Integer.parseInt(String.valueOf(prmsContract.getPrmsContractDetailList().get(i).getQuantity()));
                    unitprice = prmsContract.getPrmsContractDetailList().get(i).getUnitPrice();

                } else {
                    System.out.println("May be no item or qauntity or Up or ... ...");
                }

                double totalprice = quantity * unitprice;
                prmsContractAmendmentDt.setTotalPrice(totalprice);
                prmsContractAmendment.getPrmsContractAmendmentDtList().add(prmsContractAmendmentDt);
                prmsContractAmendmentDt.setContractAmendId(prmsContractAmendment);
                totalSum += totalprice;
                totalvat = 0.15 * totalSum;
                groundTotal = totalSum + totalvat;
            }

            try {
                if (prmsContract.getBidId().getBidType().equals("local")) {
                    renderForIntnalSupplier = false;
                    chooserenderForLocal = true;
                } else {
                    renderForIntnalSupplier = true;
                    chooserenderForLocal = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        recreateprmsContractAmendmentDetModel();
    }

    public void calc_() {

        double quantity = prmsContractAmendmentDt.getQuantity();
        double unitprice = prmsContractAmendmentDt.getUnitPrice();
        double totalprice = quantity * unitprice;
        prmsContractAmendmentDt.setTotalPrice(totalprice);
        prmsContractAmendment.getPrmsContractAmendmentDtList().add(prmsContractAmendmentDt);
        prmsContractAmendmentDt.setContractAmendId(prmsContractAmendment);
        totalSum += totalprice;
        totalvat = 0.15 * totalSum;
        groundTotal = totalSum + totalvat;

        this.setTotalSum(totalSum);
        this.setTotalvat(totalvat);
        this.setGroundTotal(groundTotal);
    }

    /**
     * *************************************************************************
     *
     *
     *************************************************************************
     */
    public void searchContractAmendmentNo() {

        prmsContractAmendment.setPreparedBy(String.valueOf(workFlow.getUserAccount()));
        prmsContractAmendments
                = contractAmendmentBeanLocal.getContractAmendmentNo(
                        prmsContractAmendment);

        for (int i = 0; i < prmsContractAmendment.
                getPrmsContractACurrencyDetailList().size(); i++) {
            FmsLuCurrency tempfmsLuCurrency = prmsContractAmendment.
                    getPrmsContractACurrencyDetailList().get(i).getCurrencyId();
            savedCurrencyIds.add(tempfmsLuCurrency);
        }

        recreateprmsContractAmendmentModel();
        prmsContractAmendment=new PrmsContractAmendment();
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public SelectItem[] getListContractNo() {

        return JsfUtil.getSelectItems(
                contractAmendmentBeanLocal.getContractList(), true);
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void valueChangeBidNoForBoth(ValueChangeEvent event) {

        if (event.getNewValue() != null) {

            prmsContractAmendment = contractAmendmentBeanLocal.getBidNumber(
                    event.getNewValue().toString());
        }

        recreateprmsContractAmendmentDtModel();
    }

    String newcheckListNo;
    String LastcheckListNo = "0";

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public String generateSpecficationNo() {

        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = prmsContractAmendment.getContractAmendNo();
        } else {
            PrmsContractAmendment lastAmendmentNo
                    = contractAmendmentBeanLocal.LastContractNo();

            if (lastAmendmentNo != null) {
                LastcheckListNo = lastAmendmentNo.getContractAmendId().toString();
            }

            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;

            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "Amend-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "Amend-" + newBidNoLast + "/" + f.format(now);
            }
        }

        return newcheckListNo;
    }

    public PrmsContractAmendment getAmendmentSelection() {
        return amendmentSelection;
    }

    public void setAmendmentSelection(PrmsContractAmendment amendmentSelection) {
        this.amendmentSelection = amendmentSelection;
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void rowSelect(SelectEvent event) {

        prmsContractAmendment = (PrmsContractAmendment) event.getObject();
        populateWorkFlow();
        recreatworkflow();
        recreateContractCurrDetailModel();
    }

    /**
     * *************************************************************************
     *
     *************************************************************************
     */
    public void populateWorkFlow() {

        contracAmendNoLists
                = contractAmendmentBeanLocal.getContAmendedNoByContNoStatus();
        prmsContractAmendment.setContractAmendId(
                prmsContractAmendment.getContractAmendId());
        prmsContractAmendment = contractAmendmentBeanLocal.getSelectedRequest(
                prmsContractAmendment.getContractAmendId());
        wfPrmsProcessed.setProcessedOn(prmsContractAmendment.getRigistrationDate());

        prmsContract = prmsContractAmendment.getContractId();
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";
        updateStatus = 1;

        if (prmsContract.getBidId().getBidType().equalsIgnoreCase("international")) {
            renderForIntnalSupplier = true;
        } else {
            renderForIntnalSupplier = false;
        }

        recreateprmsContractAmendmentDtModel();
        recreatworkflow();
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void handleSectionType(ValueChangeEvent event) {

        if (null != event.getNewValue()) {

            if (prmsContract.getBidId().getBidType().
                    equalsIgnoreCase("international")) {

                renderForIntnalSupplier = false;
                chooserenderForLocal = true;

            } else if (prmsContract.getBidId().getBidType().equalsIgnoreCase("local")) {

                renderForIntnalSupplier = true;
                chooserenderForLocal = false;
            }
        }
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void getValueofAwardNo(ValueChangeEvent event) {

        if (null != event.getNewValue()) {

            papmsAward = (PrmsAward) event.getNewValue();
        }
    }

    /**
     * @return the prmsContractList
     */
    public List<PrmsContract> getPrmsContractList() {
        if (prmsContractList == null) {
            prmsContractList = new ArrayList<>();
            prmsContractList = contractAmendmentBeanLocal.getContractList();
        }
        return prmsContractList;
    }

    /**
     * @param prmsContractList the prmsContractList to set
     */
    public void setPrmsContractList(List<PrmsContract> prmsContractList) {
        this.prmsContractList = prmsContractList;
    }

    public List<PrmsContractamendCurrency> getPrmsContractAmeList() {
        if (prmsContractAmeList == null) {
            prmsContractAmeList = new ArrayList<>();
            prmsContractAmeList = contractAmendmentBeanLocal.getContractAmeList();
        }

        return prmsContractAmeList;
    }

    public void setPrmsContractAmeList(List<PrmsContractamendCurrency> prmsContractAmeList) {
        this.prmsContractAmeList = prmsContractAmeList;
    }

    /**
     * @return the allAwardList
     */
    public List<PrmsAward> getAllAwardList() {
        if (allAwardList == null) {
            allAwardList = new ArrayList<>();
            allAwardList = purchaseOrderBeanLocal.getAwardLists();
        }
        return allAwardList;
    }

    /**
     * @param allAwardList the allAwardList to set
     */
    public void setAllAwardList(List<PrmsAward> allAwardList) {
        this.allAwardList = allAwardList;
    }

    public List<PrmsPortEntry> getPortsLists() {

        if (portsLists == null) {
            portsLists = new ArrayList<>();
            portsLists = insuranceNotificationToBankBeanLocal.fromPortEntry();
        }

        return portsLists;
    }

    public void setPortsLists(List<PrmsPortEntry> portsLists) {
        this.portsLists = portsLists;
    }

    public List<PrmsContractAmendment> getContracAmendNoLists() {

        if (contracAmendNoLists == null) {
            contracAmendNoLists = new ArrayList<>();
            contracAmendNoLists
                    = contractAmendmentBeanLocal.getContAmendedNoByContNo(prmsContract);
        }
        return contracAmendNoLists;
    }

    public void setContracAmendNoLists(List<PrmsContractAmendment> contracAmendNoLists) {
        this.contracAmendNoLists = contracAmendNoLists;
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void CommencemntDateChange(ValueChangeEvent event) {

        this.prmsContractAmendment.setCommencmentDate((Date) event.getNewValue());

        if (event.getNewValue() != null) {
            Date commencementDate_ = this.prmsContractAmendment.getCommencmentDate();
            // convert date to stirng , Gregorian calendar DateFormat used ( yyyy-MM-dd )
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strinEndDate = "-";
            strinEndDate = formatter.format(commencementDate_);
            String strincommencementDateET
                    = GregorianCalendarManipulation.gregorianToEthiopian(strinEndDate);
            DateFormat to = new SimpleDateFormat("dd/MM/yyyy");//25/09/2010
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd");

            try {
                String strDate = to.format(from.parse(strincommencementDateET));
                prmsContractAmendment.setCommencementdateam(strDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * *************************************************************************
     *
     *
     * @param event
     * ************************************************************************
     */
    public void ContractDateChange(ValueChangeEvent event) {
        this.prmsContractAmendment.setContractsigningDate((Date) event.getNewValue());

        if (event.getNewValue() != null) {

            System.out.println(" [ Not Null ] ");
            Date contractDateDate_ = this.prmsContractAmendment.getContractsigningDate();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strinEndDate = "-";
            strinEndDate = formatter.format(contractDateDate_);

            System.out.println("Formated Date=>" + strinEndDate);
            System.out.println("[[[ Contract End Date ;) " + strinEndDate);
            String stringcontractDate_ET
                    = GregorianCalendarManipulation.ethiopianToGregorian(strinEndDate);
            System.out.println("( Contract End Date Ethiopia ) " + stringcontractDate_ET);

            DateFormat to = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd");

            try {
                String strDate = to.format(from.parse(stringcontractDate_ET));
                prmsContractAmendment.setContractsigningdateam(strDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

        }
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ***********************************************************************
     */
    public void ContractEndDateChange(ValueChangeEvent event) {

        if (event.getNewValue() != null) {

            Date contractendDate_ = (Date) event.getNewValue();//this.papmsContract.getCommencmentDate();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strinEndDate = "-";
            strinEndDate = formatter.format(contractendDate_);
            String strinContractendDateET
                    = GregorianCalendarManipulation.gregorianToEthiopian(strinEndDate);
            DateFormat to = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd");

            try {
                String strDate = to.format(from.parse(strinContractendDateET));
                prmsContractAmendment.setContractenddateam(strDate);

            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ***********************************************************************
     */
    public void ContractEndDateAmChange(ValueChangeEvent event) {

        this.prmsContractAmendment.setContractenddateam((String) event.getNewValue());
        String ContractenddateAm_ = this.prmsContractAmendment.getContractenddateam();

        Date date_;

        try {
            date_ = new SimpleDateFormat("dd/MM/yyyy").parse(ContractenddateAm_);
            String parsedStr = new SimpleDateFormat("yyyy-MM-dd").format(date_);
            String dateString2 = GregorianCalendarManipulation.ethiopianToGregorian(parsedStr);
            Date ContractenddateAm_GC = new SimpleDateFormat("yyyy-MM-dd").parse(dateString2);
            this.prmsContractAmendment.setCommencmentDate(ContractenddateAm_GC);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void CommencemntDateAmChange(ValueChangeEvent event) {

        this.prmsContractAmendment.setCommencementdateam((String) event.getNewValue());
        String CommencementDateAm_ = this.prmsContractAmendment.getCommencementdateam();

        Date date_;

        try {
            date_ = new SimpleDateFormat("dd/MM/yyyy").parse(CommencementDateAm_);
            String parsedStr = new SimpleDateFormat("yyyy-MM-dd").format(date_);
            String dateString2
                    = GregorianCalendarManipulation.ethiopianToGregorian(parsedStr);
            Date CommencementDateGC = new SimpleDateFormat("yyyy-MM-dd").parse(dateString2);
            this.prmsContractAmendment.setCommencmentDate(CommencementDateGC);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * *************************************************************************
     *
     * @param eve
     * ***********************************************************************
     */
    public void handleDecision(ValueChangeEvent eve) {

        if (eve.getNewValue() != null) {
            selectedValue = eve.getNewValue().toString();
        }
    }

    /**
     * *************************************************************************
     *
     * Methods should be added for Workflow Notification and Pending Lists
     *
     *************************************************************************
     */
    int requestNotificationCounter = 0;
    private List<PrmsContractAmendment> ContractAmenLis1;

    public List<PrmsContractAmendment> getContractAmenLis1() {

        if (ContractAmenLis1 == null) {
            ContractAmenLis1 = new ArrayList<>();
        }

        return ContractAmenLis1;
    }

    public void setContractAmenLis1(List<PrmsContractAmendment> ContractAmenLis1) {
        this.ContractAmenLis1 = ContractAmenLis1;
    }

    public int getRequestNotificationCounter() {
        ContractAmenLis1 = contractAmendmentBeanLocal.getContAmendedNoByContNoStatus();
        requestNotificationCounter = ContractAmenLis1.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ***********************************************************************
     */
    public void RequestListChange(ValueChangeEvent event) {

//        if (event.getNewValue() != null) {ContractAmenLis1 = contractAmendmentBeanLocal.getContAmendedNoByContNoStatus(); }        
        if (event.getNewValue() != null) {
            prmsContractAmendment = (PrmsContractAmendment) event.getNewValue();
            populateWorkFlow();
        }
    }

    String logerName;

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public void recreatworkflow() {

        wfPrmsProcessedDModel = new ListDataModel(
                new ArrayList(prmsContractAmendment.getPrmsWorkFlowProccedList()));
        System.out.println("Three !!!" + wfPrmsProcessedDModel.getRowCount());
    }

    public void recreateContractCurrDetailModel() {
        prmsContractCurrencyModel = null;
        prmsContractCurrencyModel = new ListDataModel(
                new ArrayList<>(
                        prmsContractAmendment.getPrmsContractACurrencyDetailList()));
    }

    public void buttonActionDelete() {

        papmsContractCurrDetail = null;
        setRowIndexCurr(prmsContractCurrencyModel.getRowIndex());
        prmsContractAmendment.getPrmsContractACurrencyDetailList().remove(getRowIndexCurr());
        recreateContractCurrDetailModel();
    }

    public void buttonAction() {

        recreateContractCurrDetailModel();
        papmsContractCurrDetail = null;

        if (prmsContractCurrencyModel != null) {
            if (getRowIndexCurr() > 0) {
                setRowIndexCurr(getRowIndexCurr() + 1);
            } else if (getRowIndexCurr() == 0 && getPrmsContractCurrencyModel().getRowCount() > 0) {
                setRowIndexCurr(getRowIndexCurr() + 1);
            } else if (getPrmsContractCurrencyModel().getRowCount() < 0) {
                prmsContractCurrencyModel.setRowIndex(0);
                setRowIndexCurr(0);
            }
        } else if (prmsContractCurrencyModel == null) {
            setRowIndexCurr(0);
            prmsContractCurrencyModel.setRowIndex(0);
        }

        PrmsContractamendCurrency car2Add = new PrmsContractamendCurrency();//papmsContractCurrDetail
        car2Add.setAmount(0);
        prmsContractAmendment.getPrmsContractACurrencyDetailList().add(car2Add);

        recreateContractCurrDetailModel();
    }

    public void onContactRowCancel(RowEditEvent event) {
//  FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
//   FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public DataModel<PrmsContractamendCurrency> getPrmsContractCurrencyModel() {
        if (prmsContractCurrencyModel == null) {
            prmsContractCurrencyModel = new ListDataModel<>();
        }
        return prmsContractCurrencyModel;
    }

    public void setPrmsContractCurrencyModel(DataModel<PrmsContractamendCurrency> prmsContractCurrencyModel) {
        this.prmsContractCurrencyModel = prmsContractCurrencyModel;
    }

    public void amountVlc(ValueChangeEvent event) {

        try {
            String tempo = event.getNewValue().toString();
            papmsContractCurrDetail = prmsContractCurrencyModel.getRowData();
            papmsContractCurrDetail.setAmount(Double.parseDouble(tempo));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
