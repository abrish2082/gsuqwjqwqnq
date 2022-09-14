package et.gov.eep.prms.controller;

import com.itextpdf.text.DocumentException;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.entity.committee.HrCommitteeMembers;
import et.gov.eep.hrms.entity.committee.HrCommittees;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.integration.HREmployeesBeanLocal;
import et.gov.eep.hrms.integration.HrCommitteeMembersIntegrationBeanLocal;
import et.gov.eep.hrms.integration.HrCommitteesIntegrationBeanLocal;
import et.gov.eep.prms.businessLogic.QuatationBeanLocal;
import et.gov.eep.prms.businessLogic.VendorRegBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsPurchaseReqService;
import et.gov.eep.prms.entity.PrmsPurchaseRequestDet;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.prms.entity.PrmsSpecification;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsServiceReg;
import et.gov.eep.prms.entity.PrmsPurchaseTypeLookup;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;
import org.insa.model.DocumentModel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Image;
import et.gov.eep.commonApplications.businessLogic.PrmsLuDmArchiveBeanLocal;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.hrms.businessLogic.employee.HrEmployeeBeanLocal;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.prms.businessLogic.PurchaseReqBeanLocal;
import et.gov.eep.prms.entity.PrmsQuotationFileUpload;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.insa.client.DmsHandler;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 * *****************************************************************************
 * @author pc
 * ***************************************************************************
 */
@Named(value = "quatationController")
@ViewScoped
public class QuatationController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Declaration">
    //<editor-fold defaultstate="collapsed" desc="Declaration Inject">
    @Inject
    SessionBean SessionBean;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    WorkFlow workFlow;
    private String selectedValue = "";
    @Inject
    HrEmployees hrEmployeesEntity;
    @Inject
    HrCommittees hrcommittesEntity;
    @Inject
    HrCommitteeMembers hrcommitteeMembersEntity;
    @Inject
    private PrmsQuotationDetail prmsQuotationDetail;
    @Inject
    private PrmsSpecification prmsSpecification;
    @Inject
    private MmsItemRegistration mmsItemRegistration;
    @Inject
    private MmsServiceReg mmsServiceReg;
    @Inject
    private PrmsPurchaseReqService prmsPurchaseReqService;
    @Inject
    private PrmsQuotation prmsQuotation;
    @Inject
    private PrmsPurchaseTypeLookup prmsPurchaseTypeLookup;
    @Inject
    private PrmsLuVatTypeLookup prmsVatTypeLookup;
    @Inject
    private PrmsPurchaseRequest prmsPurchaseRequest;
    @Inject
    private PrmsPurchaseRequestDet prmsPurchaseRequestDet;
    @Inject
    private PrmsServiceAndWorkReg prmsServiceAndWorkReg;
    @Inject
    private PrmsSupplyProfile prmsSupplyProfile;
    @Inject
    private PrmsBid prmsBid;
    @Inject
    DataUpload dataUpload;
    @Inject
    HrEmployees hrEmployees;
    @Inject
    HrDepartments departments;
      //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaration EJB">
    @EJB
    private QuatationBeanLocal quatationBeanLocal;
    @EJB
    VendorRegBeanLocal vendorRegBeanLocal;
    @EJB
    private HrCommitteesIntegrationBeanLocal committesInterface;
    @EJB
    private HrCommitteeMembersIntegrationBeanLocal committeMembersInterface;
    @EJB
    HREmployeesBeanLocal hrEmployeeInterface;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    HrEmployeeBeanLocal hrEmployeesBeanLocal;
    @EJB
    PrmsLuDmArchiveBeanLocal prmsLuDmArchiveBeanLocal;
    @EJB
    PurchaseReqBeanLocal purchaseReqBeanLocal;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declaration List">
    List<PrmsPurchaseRequestDet> prmsPurchaseRequestDets;
    List<PrmsPurchaseReqService> prmsPurchaseReqServices;
    List<PrmsPurchaseRequest> purchaseRequests;
    private List<HrEmployees> employeeInformations = new ArrayList<>();
    private List<HrCommittees> committeeList = new ArrayList<>();
    private List<HrCommitteeMembers> committeeMembersList = new ArrayList<>();
    private List<HrCommitteeMembers> selectedMembersList = new ArrayList<>();

    List<PrmsPurchaseReqService> purchaseReqServices;
    List<PrmsPurchaseRequest> prmsPurchaseRequestList;
    List<PrmsPurchaseRequest> prmsPurchaseRequestListforService;
    List<PrmsPurchaseRequest> prmsPurchaseRequestListForWorks;
    private List<PrmsPurchaseRequestDet> listPr = new ArrayList<>();
    private List<PrmsSupplyProfile> supplierList;
    List<PrmsLuVatTypeLookup> prmsVatTypeLookups;
    private List<PrmsQuotation> prmsQuotationLis1;// For WorkFlow
    @OneToMany(mappedBy = "quatationId")// For WorkFlow
    private List<WfPrmsProcessed> prmsWorkFlowProccedList;
    List<PrmsQuotation> prmsQuotations;
    private List<PrmsQuotation> quatationSearchParameterLst;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declaration ArrayList">
    ArrayList<PrmsBid> prmsBids;
    ArrayList<PrmsSupplyProfile> prmsSupplyProfiles;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<PrmsQuotationDetail> prmsQuotationDetailsModel;
    DataModel<PrmsQuotationDetail> itemDataModel;
    DataModel<PrmsQuotationDetail> serviceDataModel;
    DataModel<PrmsQuotationDetail> workDataModel;
    ArrayList<MmsItemRegistration> itemList;
    ArrayList<PrmsLuVatTypeLookup> typeLookups;
    ArrayList<PrmsServiceAndWorkReg> serviceList;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Declaration DataModel">
    private DataModel<PrmsQuotation> prmsQuotationsModel;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel_;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Declaration String">
    private String saveorUpdateBundle = "Save";
    private String[] selectedMembers;
    private String addOrModifyBundle = "Add";
    private String createOrSearchBundle = "New";
    private String icon = "ui-icon-plus";
    private String item = "item";
    private String fileName;
    private String fileType;
    private byte[] byteFile;
    String slected = "select one";
    String vatFree;
    String purchaseType;
    private String userName;
    String vatType;
    String lastQuotationNo = "0";
    String newQuotationNo;
    String logerName;
    private String duplicattion = null;
    String selecttOption;
    private String selectOptPartyName;
    Set<String> addressCheck = new HashSet<>();
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Declaration Boolean">
    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private double activeIndex;
    private boolean renderService = false;
    private boolean disableService = true;
    private boolean renderSrNo = false;
    private boolean disableSrNo = true;
    private boolean renderSeriveName = false;
    private boolean renderItemName = false;
    private boolean renderUnitMeasure = false;
    private boolean rederDesription = false;
    private boolean rederItemCode = false;
    private boolean rederServiceCode = false;
    private boolean service = false;
    private boolean items;
    private boolean itemstable = true;
    private boolean serviceTable = false;
    private boolean mItemName;
    private boolean itemPrNumber;
    private boolean servicePrNumber;
    private boolean servicePrPanel;
    private boolean worksPrPanel;
    private boolean mItemCode = false;
    private boolean mItemUnitMeasure = false;
    private boolean sServiceName = false;
    private boolean sServiceCode = false;
    private boolean sServiceUntMeasure = false;
    private boolean renderStoreTbale = false;
    private boolean serviceNumber;
    private boolean ServiceName;
    private boolean worksName;
    private boolean worksNumbers;
    private boolean renderMarketTable = true;
    private boolean searchPage = true;
    private boolean newPage = false;
    private boolean rndItem = true;
    private boolean rndSerive = false;
    private boolean rndWork = false;
    private boolean dsbVatType = false;
    boolean renderDecision;
    boolean isRenderCreate;
    private boolean isFileSelected = false;
    private boolean renderpnlToSearchPage;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Declaration Double">
    double totalSum = 0.0;
    double totalvat = 0.0;
    double totalWithholding = 0.02;
    double groundTotal = 0.0;
    double withholding = 0.02;
    double vat = 0.15;
    double vatAndWithholding = 0.17;
    double total;
    double totalVat;
    double rate;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Declaration Int">
    int quantity = 0;
    int updateStatus = 0;
    int requestNotificationCounter = 0;//for WorkFlow
    int saveStatus = 0;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Declaration Entity">
    private PrmsQuotation quotationSelection;
    DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel;
    List<PrmsLuDmArchive> prmsLuDmArchivesList;
    @Inject
    PrmsLuDmArchive prmsLuDmArchiveSelection;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;

    //</editor-fold>
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public QuatationController() {
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PostConstruct">
    @PostConstruct
    public void init() {
        prmsQuotationLis1 = quatationBeanLocal.searchPrmsQuotation();
        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        setUserName(workFlow.getUserName());
//        Date currentDate = Calendar.getInstance().getTime();
//        wfPrmsProcessed.setProcessedOn(currentDate);

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
        supplierList = vendorRegBeanLocal.getVondorName();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<PrmsPurchaseRequestDet> getListPr() {
        return listPr;
    }

    public void setListPr(List<PrmsPurchaseRequestDet> listPr) {
        this.listPr = listPr;
    }

    public List<PrmsPurchaseRequestDet> getItemCod() {
        setPrmsPurchaseRequestDets(quatationBeanLocal.getItemCode());
        return prmsPurchaseRequestDets;
    }

    public List<PrmsPurchaseRequestDet> getPrNo() {
        return listPr = quatationBeanLocal.getPrNoItem();
    }

    public List<PrmsSupplyProfile> getSupplierList() {
        if (supplierList == null) {
            supplierList = new ArrayList<>();
        }
        return supplierList;
    }

    public void setSupplierList(List<PrmsSupplyProfile> supplierList) {
        this.supplierList = supplierList;
    }

    public SelectItem[] getSuppName() {
        return JsfUtil.getSelectItems(vendorRegBeanLocal.getVondorName(), true);
    }

    public SelectItem[] getPurchaseRequestList() {
        return JsfUtil.getSelectItems(quatationBeanLocal.searchPurchaseReqNo(), true);
    }

    public List<PrmsPurchaseRequestDet> getItemNamee() {
        prmsPurchaseRequestDets = quatationBeanLocal.getItemNam();
        return prmsPurchaseRequestDets;
    }

    public List<PrmsLuVatTypeLookup> getPrmsVatTypeLookups() {
        if (prmsVatTypeLookups == null) {
            prmsVatTypeLookups = new ArrayList<>();
        }
        return prmsVatTypeLookups;
    }

    public void setPrmsVatTypeLookups(List<PrmsLuVatTypeLookup> prmsVatTypeLookups) {
        this.prmsVatTypeLookups = prmsVatTypeLookups;
    }

    public List<PrmsLuVatTypeLookup> getVate() {
        prmsVatTypeLookups = quatationBeanLocal.getVat();
        return prmsVatTypeLookups;
    }

    public List<PrmsPurchaseReqService> getServicePrNo() {
        prmsPurchaseReqServices = quatationBeanLocal.getServicePrNo();
        return prmsPurchaseReqServices;
    }

    public SelectItem[] getListOfItemCode() {
        if (prmsPurchaseRequestDet.getPurchaseReqId() != null) {
            return JsfUtil.getSelectItems(quatationBeanLocal.getItemList(prmsPurchaseRequestDet), true);
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--First Select PR Number --");
            return items;
        }
    }

    public SelectItem[] getItemList1() {
        if (prmsPurchaseRequest.getPrNumber() != null) {
            SelectItem[] steps = JsfUtil.getSelectItems(quatationBeanLocal.getItemCode(), true);
            return steps;
        } else {
            SelectItem[] items = new SelectItem[1];
            items[0] = new SelectItem("", "--First PR Number--");
            return items;
        }
    }

    public SelectItem[] prNumber() {
        return JsfUtil.getSelectItems(quatationBeanLocal.prNo(prmsPurchaseRequestDet), true);
    }

    public SelectItem[] getNumber() {
        return JsfUtil.getSelectItems(quatationBeanLocal.getNumber(prmsPurchaseRequestDet), true);
    }

    public void getPrList(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsPurchaseRequest.setPrNumber(event.getNewValue().toString());
            prmsPurchaseRequest = quatationBeanLocal.getPrList(prmsPurchaseRequest);
        }
    }

    public SelectItem[] getList() {
        return JsfUtil.getSelectItems(quatationBeanLocal.getPurchaseRquestNo(prmsPurchaseRequestDet), true);
    }

    public List<PrmsPurchaseRequestDet> PaymentReqInfolist(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        List<PrmsPurchaseRequestDet> paymentReqInfolist = null;
        paymentReqInfolist = quatationBeanLocal.getPurchaseRquestNo(prmsPurchaseRequestDet);
        prmsPurchaseRequestDet.setPurchaseReqId(prmsPurchaseRequest);

        return paymentReqInfolist;
    }

    public SelectItem[] getMaterialCode() {
        return JsfUtil.getSelectItems(quatationBeanLocal.getMaterialCodes(), true);
    }

    public void getPrLists(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            prmsPurchaseRequest.setPrNumber(event.getNewValue().toString());
            prmsPurchaseRequest = quatationBeanLocal.getPrList(prmsPurchaseRequest);
        }
    }

    public boolean isServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(boolean serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public boolean isServiceName() {
        return ServiceName;
    }

    public void setServiceName(boolean ServiceName) {
        this.ServiceName = ServiceName;
    }

    public boolean isWorksName() {
        return worksName;
    }

    public void setWorksName(boolean worksName) {
        this.worksName = worksName;
    }

    public boolean isWorksNumbers() {
        return worksNumbers;
    }

    public void setWorksNumbers(boolean worksNumbers) {
        this.worksNumbers = worksNumbers;
    }

    public boolean isItemPrNumber() {
        return itemPrNumber;
    }

    public void setItemPrNumber(boolean itemPrNumber) {
        this.itemPrNumber = itemPrNumber;
    }

    public boolean isServicePrNumber() {
        return servicePrNumber;
    }

    public void setServicePrNumber(boolean servicePrNumber) {
        this.servicePrNumber = servicePrNumber;
    }

    public String getVatType() {

        return vatType;
    }

    public void setVatType(String vatType) {
        this.vatType = vatType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getTotalWithholding() {
        return totalWithholding;
    }

    public void setTotalWithholding(double totalWithholding) {
        this.totalWithholding = totalWithholding;
    }

    public double getGroundTotal() {
        return groundTotal;
    }

    public void setGroundTotal(double groundTotal) {
        this.groundTotal = groundTotal;
    }

    public double getWithholding() {
        return withholding;
    }

    public void setWithholding(double withholding) {
        this.withholding = withholding;
    }

    public ArrayList<PrmsLuVatTypeLookup> getTypeLookups() {
        return typeLookups;
    }

    public void setTypeLookups(ArrayList<PrmsLuVatTypeLookup> typeLookups) {
        this.typeLookups = typeLookups;
    }

    public List<PrmsPurchaseRequest> getPrmsPurchaseRequestListforService() {
        setPrmsPurchaseRequestListforService(
                quatationBeanLocal.getPrListForService());
        return prmsPurchaseRequestListforService;
    }

    public void setPrmsPurchaseRequestListforService(
            List<PrmsPurchaseRequest> prmsPurchaseRequestListforService) {
        this.prmsPurchaseRequestListforService = prmsPurchaseRequestListforService;
    }

    public List<PrmsPurchaseRequest> getPrmsPurchaseRequestListForWorks() {
        setPrmsPurchaseRequestListForWorks(quatationBeanLocal.getPrListForWorks());
        return prmsPurchaseRequestListForWorks;
    }

    public void setPrmsPurchaseRequestListForWorks(
            List<PrmsPurchaseRequest> prmsPurchaseRequestListForWorks) {
        this.prmsPurchaseRequestListForWorks = prmsPurchaseRequestListForWorks;
    }

    public List<PrmsPurchaseRequest> getPrmsPurchaseRequestList() {
        setPrmsPurchaseRequestList(quatationBeanLocal.getPrList());
        return prmsPurchaseRequestList;
    }

    public void setPrmsPurchaseRequestList(
            List<PrmsPurchaseRequest> prmsPurchaseRequestList) {
        this.prmsPurchaseRequestList = prmsPurchaseRequestList;
    }

    public List<PrmsPurchaseRequest> getPurchaseRequests() {
        setPurchaseRequests(quatationBeanLocal.getPershaseRquest());
        return purchaseRequests;
    }

    public void setPurchaseRequests(List<PrmsPurchaseRequest> purchaseRequests) {
        this.purchaseRequests = purchaseRequests;
    }

    public void setPurchaseReqServices(
            List<PrmsPurchaseReqService> purchaseReqServices) {
        this.purchaseReqServices = purchaseReqServices;
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

    public ArrayList<PrmsSupplyProfile> getPrmsSupplyProfiles() {
        if (prmsSupplyProfiles == null) {
            prmsSupplyProfiles = new ArrayList<>();
        }
        return prmsSupplyProfiles;
    }

    public void setPrmsSupplyProfiles(
            ArrayList<PrmsSupplyProfile> prmsSupplyProfiles) {
        this.prmsSupplyProfiles = prmsSupplyProfiles;
    }

    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    public String getSelecttOption() {
        return selecttOption;
    }

    public void setSelecttOption(String selecttOption) {
        this.selecttOption = selecttOption;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public PrmsQuotation getQuotationSelection() {
        return quotationSelection;
    }

    public void setQuotationSelection(PrmsQuotation quotationSelection) {
        this.quotationSelection = quotationSelection;
    }

    public PrmsPurchaseTypeLookup getPrmsPurchaseTypeLookup() {
        if (prmsPurchaseTypeLookup == null) {
            prmsPurchaseTypeLookup = new PrmsPurchaseTypeLookup();
        }
        return prmsPurchaseTypeLookup;
    }

    public void setPrmsPurchaseTypeLookup(
            PrmsPurchaseTypeLookup prmsPurchaseTypeLookup) {
        this.prmsPurchaseTypeLookup = prmsPurchaseTypeLookup;
    }

    public PrmsLuVatTypeLookup getPrmsVatTypeLookup() {
        if (prmsVatTypeLookup == null) {
            prmsVatTypeLookup = new PrmsLuVatTypeLookup();
        }
        return prmsVatTypeLookup;
    }

    public void setPrmsVatTypeLookup(PrmsLuVatTypeLookup prmsVatTypeLookup) {
        this.prmsVatTypeLookup = prmsVatTypeLookup;
    }

    public PrmsPurchaseReqService getPrmsPurchaseReqService() {
        if (prmsPurchaseReqService == null) {
            prmsPurchaseReqService = new PrmsPurchaseReqService();
        }
        return prmsPurchaseReqService;
    }

    public boolean isServiceTable() {
        return serviceTable;
    }

    public void setServiceTable(boolean serviceTable) {
        this.serviceTable = serviceTable;
    }

    public void setPrmsPurchaseReqService(PrmsPurchaseReqService prmsPurchaseReqService) {
        this.prmsPurchaseReqService = prmsPurchaseReqService;
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

    public boolean isItemstable() {
        return itemstable;
    }

    public void setItemstable(boolean itemstable) {
        this.itemstable = itemstable;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }

    public boolean isItems() {
        return items;
    }

    public void setItems(boolean items) {
        this.items = items;
    }

    public boolean isRenderService() {
        return renderService;
    }

    public void setRenderService(boolean renderService) {
        this.renderService = renderService;
    }

    public boolean isDisableService() {
        return disableService;
    }

    public void setDisableService(boolean disableService) {
        this.disableService = disableService;
    }

    public boolean isRenderSrNo() {
        return renderSrNo;
    }

    public void setRenderSrNo(boolean renderSrNo) {
        this.renderSrNo = renderSrNo;
    }

    public boolean isDisableSrNo() {
        return disableSrNo;
    }

    public void setDisableSrNo(boolean disableSrNo) {
        this.disableSrNo = disableSrNo;
    }

    public boolean isRenderSeriveName() {
        return renderSeriveName;
    }

    public void setRenderSeriveName(boolean renderSeriveName) {
        this.renderSeriveName = renderSeriveName;
    }

    public boolean isRenderItemName() {
        return renderItemName;
    }

    public void setRenderItemName(boolean renderItemName) {
        this.renderItemName = renderItemName;
    }

    public boolean isRenderUnitMeasure() {
        return renderUnitMeasure;
    }

    public void setRenderUnitMeasure(boolean renderUnitMeasure) {
        this.renderUnitMeasure = renderUnitMeasure;
    }

    public boolean isRederDesription() {
        return rederDesription;
    }

    public void setRederDesription(boolean rederDesription) {
        this.rederDesription = rederDesription;
    }

    public boolean isRederItemCode() {
        return rederItemCode;
    }

    public void setRederItemCode(boolean rederItemCode) {
        this.rederItemCode = rederItemCode;
    }

    public boolean isRederServiceCode() {
        return rederServiceCode;
    }

    public void setRederServiceCode(boolean rederServiceCode) {
        this.rederServiceCode = rederServiceCode;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean ismItemName() {
        return mItemName;
    }

    public void setmItemName(boolean mItemName) {
        this.mItemName = mItemName;
    }

    public boolean ismItemCode() {
        return mItemCode;
    }

    public void setmItemCode(boolean mItemCode) {
        this.mItemCode = mItemCode;
    }

    public boolean ismItemUnitMeasure() {
        return mItemUnitMeasure;
    }

    public void setmItemUnitMeasure(boolean mItemUnitMeasure) {
        this.mItemUnitMeasure = mItemUnitMeasure;
    }

    public boolean issServiceName() {
        return sServiceName;
    }

    public void setsServiceName(boolean sServiceName) {
        this.sServiceName = sServiceName;
    }

    public boolean issServiceCode() {
        return sServiceCode;
    }

    public void setsServiceCode(boolean sServiceCode) {
        this.sServiceCode = sServiceCode;
    }

    public boolean issServiceUntMeasure() {
        return sServiceUntMeasure;
    }

    public void setsServiceUntMeasure(boolean sServiceUntMeasure) {
        this.sServiceUntMeasure = sServiceUntMeasure;
    }

    public boolean isRenderStoreTbale() {
        return renderStoreTbale;
    }

    public void setRenderStoreTbale(boolean renderStoreTbale) {
        this.renderStoreTbale = renderStoreTbale;
    }

    public boolean isRenderMarketTable() {
        return renderMarketTable;
    }

    public void setRenderMarketTable(boolean renderMarketTable) {
        this.renderMarketTable = renderMarketTable;
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public MmsServiceReg getMmsServiceReg() {
        if (mmsServiceReg == null) {
            mmsServiceReg = new MmsServiceReg();
        }
        return mmsServiceReg;
    }

    public void setMmsServiceReg(MmsServiceReg mmsServiceReg) {
        this.mmsServiceReg = mmsServiceReg;
    }

    public ArrayList<PrmsServiceAndWorkReg> getServiceList() {
        if (serviceList == null) {
            serviceList = new ArrayList<>();
        }
        return serviceList;
    }

    public void setServiceList(ArrayList<PrmsServiceAndWorkReg> serviceList) {
        this.serviceList = serviceList;
    }

    public ArrayList<MmsItemRegistration> getItemList() {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        return itemList;
    }

    public void setItemList(ArrayList<MmsItemRegistration> itemList) {

        this.itemList = itemList;
    }

    public boolean isRndItem() {
        return rndItem;
    }

    public void setRndItem(boolean rndItem) {
        this.rndItem = rndItem;
    }

    public boolean isRndSerive() {
        return rndSerive;
    }

    public void setRndSerive(boolean rndSerive) {
        this.rndSerive = rndSerive;
    }

    public boolean isRndWork() {
        return rndWork;
    }

    public void setRndWork(boolean rndWork) {
        this.rndWork = rndWork;
    }

    public boolean isDsbVatType() {
        return dsbVatType;
    }

    public void setDsbVatType(boolean dsbVatType) {
        this.dsbVatType = dsbVatType;
    }

    public boolean isSearchPage() {
        return searchPage;
    }

    public void setSearchPage(boolean searchPage) {
        this.searchPage = searchPage;
    }

    public boolean isNewPage() {
        return newPage;
    }

    public void setNewPage(boolean newPage) {
        this.newPage = newPage;
    }

    public HrEmployees getHrEmployeesEntity() {
        if (hrEmployeesEntity == null) {
            this.hrEmployeesEntity = new HrEmployees();
        }
        return hrEmployeesEntity;
    }

    public void setHrEmployeesEntity(HrEmployees hrEmployeesEntity) {
        this.hrEmployeesEntity = hrEmployeesEntity;
    }

    public HrCommittees getHrcommittesEntity() {
        if (hrcommittesEntity == null) {
            hrcommittesEntity = new HrCommittees();
        }
        return hrcommittesEntity;
    }

    public void setHrcommittesEntity(HrCommittees hrcommittesEntity) {
        this.hrcommittesEntity = hrcommittesEntity;
    }

    public HrCommitteeMembers getHrcommitteeMembersEntity() {
        if (hrcommitteeMembersEntity == null) {
            hrcommitteeMembersEntity = new HrCommitteeMembers();
        }
        return hrcommitteeMembersEntity;
    }

    public void setHrcommitteeMembersEntity(HrCommitteeMembers hrcommitteeMembersEntity) {
        this.hrcommitteeMembersEntity = hrcommitteeMembersEntity;
    }

    public void handleSelectCommittee(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrcommittesEntity.setCommitteeName(event.getNewValue().toString());
            hrcommittesEntity = committesInterface.getCommittee(hrcommittesEntity);
            prmsQuotation.setCommitteeId(hrcommittesEntity);
        }
    }

    public List<HrEmployees> searchEmployeeInformation(String firstName) {
        hrEmployeesEntity.setFirstName(firstName);
        employeeInformations = hrEmployeeInterface.searchEmployeeInfo(hrEmployeesEntity);
        return employeeInformations;
    }

    public List<HrCommittees> getCommittees() {
        committeeList = committesInterface.findAll();
        return committeeList;
    }

    public List<HrCommitteeMembers> getCommitteeMembers() {
        committeeMembersList
                = committeMembersInterface.getCommitteeMembers(hrcommittesEntity);
        return committeeMembersList;
    }

    public List<HrCommitteeMembers> getSelectedMembersList() {
        if (selectedMembersList == null) {
            selectedMembersList = new ArrayList<>();
        }
        return selectedMembersList;
    }

    public void setSelectedMembersList(List<HrCommitteeMembers> selectedMembersList) {
        this.selectedMembersList = selectedMembersList;
    }

    public String[] getSelectedMembers() {
        return selectedMembers;
    }

    public void setSelectedMembers(String[] selectedMembers) {
        this.selectedMembers = selectedMembers;
    }

    public DataModel<PrmsQuotationDetail> getItemDataModel() {
        if (itemDataModel == null) {
            itemDataModel = new ArrayDataModel<>();
        }
        return itemDataModel;
    }

    public void setItemDataModel(DataModel<PrmsQuotationDetail> itemDataModel) {
        this.itemDataModel = itemDataModel;
    }

    public DataModel<PrmsQuotationDetail> getServiceDataModel() {
        if (serviceDataModel == null) {
            serviceDataModel = new ArrayDataModel<>();
        }
        return serviceDataModel;
    }

    public void setServiceDataModel(DataModel<PrmsQuotationDetail> serviceDataModel) {
        this.serviceDataModel = serviceDataModel;
    }

    public DataModel<PrmsQuotationDetail> getWorkDataModel() {
        if (workDataModel == null) {
            workDataModel = new ArrayDataModel<>();
        }
        return workDataModel;
    }

    public void setWorkDataModel(DataModel<PrmsQuotationDetail> workDataModel) {
        this.workDataModel = workDataModel;
    }

    public SelectItem[] getItemName() {
        return JsfUtil.getSelectItems(this.getPrmsPurchaseRequestDets(), true);
    }

    public List<PrmsPurchaseRequestDet> getItemCode() {
        return null;
    }

    public PrmsPurchaseRequestDet getPrmsPurchaseRequestDet() {
        if (prmsPurchaseRequestDet == null) {
            prmsPurchaseRequestDet = new PrmsPurchaseRequestDet();
        }
        return prmsPurchaseRequestDet;
    }

    public void setPrmsPurchaseRequestDet(PrmsPurchaseRequestDet prmsPurchaseRequestDet) {
        this.prmsPurchaseRequestDet = prmsPurchaseRequestDet;
    }

    public List<PrmsPurchaseRequestDet> getPrmsPurchaseRequestDets() {

        return prmsPurchaseRequestDets;
    }

    public void setPrmsPurchaseRequestDets(List<PrmsPurchaseRequestDet> prmsPurchaseRequestDets) {
        this.prmsPurchaseRequestDets = prmsPurchaseRequestDets;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public PrmsPurchaseRequest getPrmsPurchaseRequest() {
        if (prmsPurchaseRequest == null) {
            prmsPurchaseRequest = new PrmsPurchaseRequest();
        }
        return prmsPurchaseRequest;
    }

    public void setPrmsPurchaseRequest(PrmsPurchaseRequest prmsPurchaseRequest) {
        this.prmsPurchaseRequest = prmsPurchaseRequest;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public int getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public SelectItem[] getAwardLists() {
        return JsfUtil.getSelectItems(quatationBeanLocal.getPurchaseRequestList(), true);
    }

    public PrmsQuotationDetail getPrmsQuotationDetail() {
        if (prmsQuotationDetail == null) {
            prmsQuotationDetail = new PrmsQuotationDetail();
        }
        return prmsQuotationDetail;
    }

    public void setPrmsQuotationDetail(PrmsQuotationDetail prmsQuotationDetail) {
        this.prmsQuotationDetail = prmsQuotationDetail;
    }

    public PrmsSpecification getPrmsSpecification() {
        return prmsSpecification;
    }

    public void setPrmsSpecification(PrmsSpecification prmsSpecification) {
        this.prmsSpecification = prmsSpecification;
    }

    public PrmsQuotation getPrmsQuotation() {
        if (prmsQuotation == null) {
            prmsQuotation = new PrmsQuotation();
        }
        return prmsQuotation;
    }

    public void setPrmsQuotation(PrmsQuotation prmsQuotation) {
        this.prmsQuotation = prmsQuotation;
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

    public PrmsBid getPrmsBid() {
        return prmsBid;
    }

    public void setPrmsBid(PrmsBid prmsBid) {
        this.prmsBid = prmsBid;
    }

    public String getAddOrModifyBundle() {
        return addOrModifyBundle;
    }

    public void setAddOrModifyBundle(String addOrModifyBundle) {
        this.addOrModifyBundle = addOrModifyBundle;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public double getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(double activeIndex) {
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

    public ArrayList<PrmsBid> getPrmsBids() {
        return prmsBids;
    }

    public void setPrmsBids(ArrayList<PrmsBid> prmsBids) {
        this.prmsBids = prmsBids;
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

    public DataModel<WfPrmsProcessed> getWfPrmsProcessedDModel_() {
        if (wfPrmsProcessedDModel_ == null) {
            wfPrmsProcessedDModel_ = new ArrayDataModel<>();
        }
        return wfPrmsProcessedDModel_;
    }

    public void setWfPrmsProcessedDModel_(DataModel<WfPrmsProcessed> wfPrmsProcessedDModel_) {
        this.wfPrmsProcessedDModel_ = wfPrmsProcessedDModel_;
    }

    public DataModel<PrmsQuotationDetail> getPrmsQuotationDetailsModel() {

        if (prmsQuotationDetailsModel == null) {
            prmsQuotationDetailsModel = new ArrayDataModel<>();
        }
        return prmsQuotationDetailsModel;
    }

    public void setPrmsQuotationDetailsModel(DataModel<PrmsQuotationDetail> prmsQuotationDetailsModel) {
        this.prmsQuotationDetailsModel = prmsQuotationDetailsModel;
    }

    public List<PrmsQuotation> getPrmsQuotations() {
        if (prmsQuotations == null) {
            prmsQuotations = new ArrayList<>();
        }
        return prmsQuotations;
    }

    public void setPrmsQuotations(List<PrmsQuotation> prmsQuotations) {
        this.prmsQuotations = prmsQuotations;
    }

    public List<PrmsQuotation> getQuatationSearchParameterLst() {
        if (quatationSearchParameterLst == null) {
            quatationSearchParameterLst = new ArrayList<>();
            quatationSearchParameterLst = quatationBeanLocal.getColumnNameList();
        }
        return quatationSearchParameterLst;
    }

    public void setQuatationSearchParameterLst(List<PrmsQuotation> quatationSearchParameterLst) {
        this.quatationSearchParameterLst = quatationSearchParameterLst;
    }

    public DataModel<PrmsQuotation> getPrmsQuotationsModel() {
//        if (prmsQuotationsModel == null) {
//            prmsQuotationsModel = new ArrayDataModel<>();
//        }
        return prmsQuotationsModel;
    }

    public void setPrmsQuotationsModel(DataModel<PrmsQuotation> prmsQuotationsModel) {
        this.prmsQuotationsModel = prmsQuotationsModel;
    }

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="calculation">
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalVat() {
        return totalVat;
    }

    public void setTotalVat(double totalVat) {
        this.totalVat = totalVat;
    }

    public void TotalPric() {
        List<PrmsQuotationDetail> ppods = new ArrayList<>();
        for (int i = 0; i < prmsQuotation.getPrmsQuotationDetailList().size(); i++) {
            prmsQuotation.getPrmsQuotationDetailList().get(i).setTotals(prmsQuotation.getPrmsQuotationDetailList().get(i).getUnitPrice() * prmsQuotation.getPrmsQuotationDetailList().get(i).getQuantity().doubleValue());
            totalSum = totalSum + prmsQuotation.getPrmsQuotationDetailList().get(i).getTotals();
        }
        totalvat = 0.15 * totalSum;
        totalWithholding = 0.02 * totalSum;
        groundTotal = totalvat + totalSum;
        groundTotal = totalWithholding + totalSum;
        if (vatType.equals(totalWithholding)) {
            totalWithholding = 0.02 * totalSum;
            groundTotal = totalWithholding + totalSum;
        } else if (vatType.equals(vat)) {
            totalvat = 0.15 * totalSum;
            groundTotal = totalvat + totalSum;
        } else if (vatType.equals(vatAndWithholding)) {
            totalvat = 0.15 * totalSum;
            // totalWithholding=0.02*totalSum;
            groundTotal = totalvat + totalSum + totalWithholding;
        } else if (vatType.equals(vatFree)) {
            groundTotal = totalSum;
        }
    }

    public void calculatingTotal() {

        total = 0;
        totalVat = 0;
        double totalWithOutVat = 0;
        double vatRate = 0;

        if (prmsQuotationDetail == null && prmsQuotationDetail.getVatType() != null) {
            JsfUtil.addFatalMessage("Can't get value. Try again!.");
        } else {
            totalWithOutVat = prmsQuotationDetail.getQuantity() * prmsQuotationDetail.getUnitPrice();
            vatRate = prmsQuotationDetail.getSupId().getVatTypeId().getVatRate();
            try {
                if (prmsQuotationDetail.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("vat")
                        && prmsQuotationDetail.getVatType().equalsIgnoreCase("Exclude")) {
                    total = totalWithOutVat + (totalWithOutVat * vatRate);
                    totalVat = total - totalWithOutVat;
                    prmsQuotationDetail.setTotalVat(totalVat);
                } else if (prmsQuotationDetail.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("vat")
                        && prmsQuotationDetail.getVatType().equalsIgnoreCase("Include")) {
                    total = totalWithOutVat;
                    totalVat = total * vatRate / 1.15;
                    total = total + totalVat;
                    prmsQuotationDetail.setTotalVat(totalVat);
                } else if (prmsQuotationDetail.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("vat and withholding")
                        && prmsQuotationDetail.getVatType().equalsIgnoreCase("Exclude")) {
                    total = totalWithOutVat + (totalWithOutVat * 0.15);
                    totalVat = total - totalWithOutVat;
                    prmsQuotationDetail.setTotalVat(totalVat);

                } else if (prmsQuotationDetail.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("vat and withholding")
                        && prmsQuotationDetail.getVatType().equalsIgnoreCase("Include")) {
                    total = totalWithOutVat;
                    totalVat = total * 0.15 / 1.15;
                    total = total + totalVat;
                    prmsQuotationDetail.setTotalVat(totalVat);

                } else if (prmsQuotationDetail.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("withholding")) {
                    total = prmsQuotationDetail.getQuantity() * prmsQuotationDetail.getUnitPrice();
                } else if (prmsQuotationDetail.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("no vat and withholding")) {
                    total = prmsQuotationDetail.getQuantity() * prmsQuotationDetail.getUnitPrice();
                }

                prmsQuotationDetail.setTotalPrice(total);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void calculatingTotalInPopulate() {

        total = 0;
        totalVat = 0;
        double totalWithOutVat = 0;
        double vatRate = 0;

        if (prmsQuotationDetail != null) {

            for (PrmsQuotationDetail quotation : prmsQuotation.getPrmsQuotationDetailList()) {

                totalWithOutVat = quotation.getQuantity() * quotation.getUnitPrice();
                vatRate = quotation.getSupId().getVatTypeId().getVatRate() == null ? 0.0
                        : quotation.getSupId().getVatTypeId().getVatRate();

                try {
                    if (quotation.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("vat") && quotation.getVatType().equalsIgnoreCase("Exclude")) {
                        total = totalWithOutVat + (totalWithOutVat * vatRate);
                        totalVat = total - totalWithOutVat;
                        quotation.setTotalVat(totalVat);
                    } else if (quotation.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("vat") && quotation.getVatType().equalsIgnoreCase("Include")) {
                        total = totalWithOutVat;
                        totalVat = total * vatRate / 1.15;
                        quotation.setTotalVat(totalVat);
                    } else if (quotation.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("vat and withholding") && quotation.getVatType().equalsIgnoreCase("Exclude")) {
                        total = totalWithOutVat + (totalWithOutVat * 0.15);
                        totalVat = total - totalWithOutVat;
                        quotation.setTotalVat(totalVat);
                    } else if (quotation.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("vat and withholding") && quotation.getVatType().equalsIgnoreCase("Include")) {
                        total = totalWithOutVat;
                        totalVat = total * 0.15 / 1.15;
                        quotation.setTotalVat(totalVat);
                    } else if (quotation.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("withholding")) {
                        total = quotation.getQuantity() * quotation.getUnitPrice();
                    } else if (quotation.getSupId().getVatTypeId().getVatType().equalsIgnoreCase("no vat and withholding")) {
                        total = quotation.getQuantity() * quotation.getUnitPrice();
                    }
                    quotation.setTotalPrice(total);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="WorkFlow">
    public WorkFlow getWorkFlow() {
        if (workFlow == null) {
            workFlow = new WorkFlow();
        }
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public void handleDecision(ValueChangeEvent eve) {
        if (eve.getNewValue() != null) {
            selectedValue = eve.getNewValue().toString();
        }
    }

    public List<PrmsQuotation> getPrmsQuotationLis1() {
        if (prmsQuotationLis1 == null) {
            prmsQuotationLis1 = new ArrayList<>();
        }
        return prmsQuotationLis1;
    }

    public void setPrmsQuotationLis1(List<PrmsQuotation> prmsQuotationLis1) {
        this.prmsQuotationLis1 = prmsQuotationLis1;
    }

    public int getRequestNotificationCounter() {
        prmsQuotationLis1 = quatationBeanLocal.searchPrmsQuotation();
        requestNotificationCounter = prmsQuotationLis1.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    @XmlTransient
    public List<WfPrmsProcessed> getPrmsWorkFlowProccedList() {
        if (prmsWorkFlowProccedList == null) {
            prmsWorkFlowProccedList = new ArrayList<>();
        }
        return prmsWorkFlowProccedList;
    }

    public void setHrWorkFlowProccedList(List<WfPrmsProcessed> prmsWorkFlowProccedList) {
        this.prmsWorkFlowProccedList = prmsWorkFlowProccedList;
    }

    public boolean isRenderDecision() {
        return renderDecision;
    }

    public void setRenderDecision(boolean renderDecision) {
        this.renderDecision = renderDecision;
    }

    public boolean isIsRenderCreate() {
        return isRenderCreate;
    }

    public void setIsRenderCreate(boolean isRenderCreate) {
        this.isRenderCreate = isRenderCreate;
    }

    public void RequestListChange(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            prmsQuotation = (PrmsQuotation) event.getNewValue();
            wfPrmsProcessedDModel_ = new ListDataModel(new ArrayList(prmsQuotation.getPrmsWorkFlowProccedList()));
            populateDatas();
        }
    }

    public void populateWorkFlow() {
        wfPrmsProcessed.setProcessedOn(prmsQuotation.getRegDate());
        prmsPurchaseRequest = prmsPurchaseRequestDet.getPurchaseReqId();
        mmsItemRegistration = prmsPurchaseRequestDet.getMaterialCodeId();
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        String select = "select";
        select = prmsQuotation.getPurchaseType();

        if (select.equalsIgnoreCase("item")) {
            items = true;
            itemstable = true;
            service = false;
        } else if (select.equalsIgnoreCase("service")) {
            items = false;
            service = true;
        }

        if (prmsQuotation.getDocumentId() != null) {
//            docId = prmsLcRigistration.getDocumentId();
            prmsLuDmArchive = prmsQuotation.getDocumentId();
            prmsLuDmArchivesList = prmsLuDmArchiveBeanLocal.getFileLists(prmsLuDmArchive);
//            doLst.add(docId);
//            getfile();
        }
        recreateprmsQuotationDetailsModel();
        recreatworkflow();
    }

    public void recreatworkflow() {
        wfPrmsProcessedDModel_ = null;
        wfPrmsProcessedDModel_ = new ListDataModel(new ArrayList(prmsQuotation.getPrmsWorkFlowProccedList()));

    }

    public void saveWorkFlowInformation() {

//        Date currentDate = Calendar.getInstance().getTime();//prmsQuotation.setRigisteredDate(currentDate);
        prmsQuotation.setStatus(Integer.parseInt(wfPrmsProcessed.getDecision()));
        prmsQuotation.getPrmsWorkFlowProccedList().add(wfPrmsProcessed);
//        wfPrmsProcessed.setProcessedOn(currentDate);
        prmsQuotation.getPrmsWorkFlowProccedList().add(wfPrmsProcessed);
        wfPrmsProcessed.setQuatationId(prmsQuotation);
    }

    public void recreateprmsQuotationsModel() {
        prmsQuotationsModel = null;
        prmsQuotationsModel = new ListDataModel(new ArrayList<>(getPrmsQuotations()));
    }

    public void recreateprmsQuotationDetailsModel() {
        prmsQuotationDetailsModel = null;
        prmsQuotationDetailsModel = new ListDataModel(new ArrayList<>(prmsQuotation.getPrmsQuotationDetailList()));
    }

    public void recreateItemDataModel() {
        itemDataModel = null;
        itemDataModel = new ListDataModel(new ArrayList<>(prmsQuotation.getPrmsQuotationDetailList()));
    }

    public void recreateServiceModel() {

        serviceDataModel = null;
        serviceDataModel = new ListDataModel(new ArrayList<>(prmsQuotation.getPrmsQuotationDetailList()));
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Save">

    public String saveForQuotationRigstration() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();// String systemBundle = "cfg/securityServer";
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
//            PrmsQuotationFileUpload quotatinoLocal = new PrmsQuotationFileUpload();

            if (security.checkAccess(SessionBean.getUserName(), "saveForQuotationRigstration", dataset)) {
                PrmsQuotationFileUpload quotationLocal = new PrmsQuotationFileUpload();
                if ((prmsQuotation.getPurchaseType().equalsIgnoreCase("Goods")) && (itemDataModel.getRowCount() <= 0)) {
                    JsfUtil.addFatalMessage("Item data table shoud be filled");
                } else if ((prmsQuotation.getPurchaseType().equalsIgnoreCase("Service")) && (serviceDataModel.getRowCount() <= 0)) {
                    JsfUtil.addFatalMessage("Service data table shoud be filled");
                } else if ((prmsQuotation.getPurchaseType().equalsIgnoreCase("Works")) && (workDataModel.getRowCount() <= 0)) {
                    JsfUtil.addFatalMessage("Work data table shoud be filled");
                } else {
                    if (updateStatus == 0) {
                        try {
                            generateQuotationNo();
                            setPrmsQuotation(getPrmsQuotation());
                            prmsQuotation.setQuotationNo(newQuotationNo);
                            prmsQuotationDetail.setMaterialCodeId(mmsItemRegistration);
                            prmsQuotation.setPreparedBy(String.valueOf(SessionBean.getUserId()));

                            Date currentDate = Calendar.getInstance().getTime();
                            prmsQuotation.setStatus(Constants.PREPARE_VALUE);
                            wfPrmsProcessed.setQuatationId(prmsQuotation);
                            wfPrmsProcessed.setDecision(String.valueOf(prmsQuotation.getStatus()));
                            wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
                            prmsQuotation.setRegDate(currentDate);
                            prmsQuotation.setPurchaser(hrEmployees);
                            prmsQuotation.setRequestedBy(departments);
                            System.out.println("Dep2" + prmsQuotation.getRequestedBy());

                            for (int i = 0; i < savedDocIds.size(); i++) {
                                quotationLocal = new PrmsQuotationFileUpload();
                                quotationLocal.setDocumentId(savedDocIds.get(i));
                                prmsQuotation.add(quotationLocal);
                            }

                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(fileType);
                            prmsLuDmArchive.setUploadFile(byteFile);
                            prmsLuDmArchiveBeanLocal.saveFileInfo(prmsLuDmArchive);
                            prmsQuotation.setDocumentId(prmsLuDmArchive);
                            quatationBeanLocal.create(prmsQuotation);
                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            JsfUtil.addSuccessMessage("Quotation Information Successfully Registered ");//  recreatworkflow();
                            clear();

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Error occured while Saving");
                        }
                    } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equals("Update")) {
                        try {
                            for (int i = 0; i < savedDocIds.size(); i++) {
                                quotationLocal = new PrmsQuotationFileUpload();
                                quotationLocal.setDocumentId(savedDocIds.get(i));
                                prmsQuotation.add(quotationLocal);
                            }
                            prmsQuotation.setPurchaser(hrEmployees);
                            prmsQuotation.setRequestedBy(departments);
                            System.out.println("Dep1" + departments.getDepName());
                            System.out.println("Dep2" + prmsQuotation.getRequestedBy());

                            prmsLuDmArchive.setFileName(fileName);
                            prmsLuDmArchive.setFileType(fileType);
                            prmsLuDmArchive.setUploadFile(byteFile);
                            prmsLuDmArchiveBeanLocal.updateFileInfo(prmsLuDmArchive);
                            prmsQuotation.setDocumentId(prmsLuDmArchive);
                            quatationBeanLocal.update(prmsQuotation);
                            JsfUtil.addSuccessMessage("Quotation Information Successfully Updated");
                            clear();
                            saveorUpdateBundle = "Save";
                            updateStatus = 0;

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Error occured while Updating");
                        }
                    } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equals("Update")) {
                        try {
                            quatationBeanLocal.update(prmsQuotation);
                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            JsfUtil.addSuccessMessage("Quotation Information Successfully Updated");
                            clear();
                            saveorUpdateBundle = "Save";
                            updateStatus = 0;

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addErrorMessage("Error occured while Updating");
                        }
                    } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equals("Update")) {

                        try {
                            if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                                workFlow.setUserStatus(Constants.APPROVE_VALUE);
                                prmsQuotation.setStatus(Constants.APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                                workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                                prmsQuotation.setStatus(Constants.CHECK_APPROVE_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                                workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                                prmsQuotation.setStatus(Constants.APPROVE_REJECT_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                            } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                                workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                                prmsQuotation.setStatus(Constants.CHECK_REJECT_VALUE);
                                wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                            }
                            prmsQuotation.setStatus(workFlow.getUserStatus());
                            quatationBeanLocal.update(prmsQuotation);
                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            JsfUtil.addSuccessMessage("Quotation Information has been Successfuly Approved");
                            clear();
                            saveorUpdateBundle = "Save";
                            updateStatus = 0;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JsfUtil.addErrorMessage("Something Error Occured on Data Update");
                        }
                    }
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

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Clear Information">
    private void ClearPopUp() {
        addOrModifyBundle = "Add";
        prmsPurchaseRequest = null;
        prmsPurchaseTypeLookup = null;
        prmsSupplyProfile = null;
        mmsItemRegistration = null;
        prmsQuotationDetail = null;
    }

    public void deleteSuppSpecDetailList() {
        int rowIndex = prmsQuotationsModel.getRowIndex();
        recreateprmsQuotationsModel();
    }

    public void clearItem() {
        addOrModifyBundle = "Add";
        prmsQuotationDetail = null;
        prmsPurchaseRequest = null;
        mmsItemRegistration = null;
    }

    public void clearServiceAndWork() {
        addOrModifyBundle = "Add";
        prmsQuotationDetail = null;
        prmsPurchaseRequest = null;
        prmsServiceAndWorkReg = null;
    }

    public void clear() {

        prmsQuotation = null;
        hrEmployees = null;
        departments = null;
        prmsPurchaseRequest = null;
        mmsItemRegistration = null;
        prmsQuotationDetail = null;
        itemDataModel = null;
        prmsServiceAndWorkReg = null;
        serviceDataModel = null;
        workDataModel = null;
        prmsQuotationDetail = null;
        prmsQuotationDetailsModel = null;
        prmsLuDmArchivesDModel = null;
        wfPrmsProcessed = null;
        prmsSupplyProfile = null;
        newDoclist = new ArrayList<>();
        savedDocIds = new ArrayList<>();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Add To Data Table">
    public void recreateWorkModel() {
        workDataModel = null;
        workDataModel = new ListDataModel(new ArrayList<>(prmsQuotation.getPrmsQuotationDetailList()));
    }

    public void addQuotationDetail() {
        prmsQuotation.addQuotationDetail(prmsQuotationDetail);
        ClearPopUp();
        recreateprmsQuotationDetailsModel();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Create New">
    public void createNewParty() {
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
        ClearPopUp();
        clear();
        clearItem();
        clearServiceAndWork();
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            icon = "ui-icon-search";
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            icon = "ui-icon-plus";
        }
    }

    public void addItem() {
        serviceDataModel = null;
        workDataModel = null;
        prmsQuotation.addQuotationDetail(prmsQuotationDetail);
        calculatingTotal();
        recreateItemDataModel();
        clearItem();
    }

    public void addService() {
        prmsQuotation.addQuotationDetail(prmsQuotationDetail);
        calculatingTotal();
        recreateServiceModel();
        clearServiceAndWork();
    }

    public void addWork() {
        prmsQuotation.addQuotationDetail(prmsQuotationDetail);
        calculatingTotal();
        recreateWorkModel();
        clearServiceAndWork();
    }

    public void getService(ValueChangeEvent event) {
        prmsPurchaseRequest = (PrmsPurchaseRequest) event.getNewValue();
        int size = prmsPurchaseRequest.getPrmsPurchaseReqServiceList().size();
        serviceList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            PrmsPurchaseReqService prDt2 = new PrmsPurchaseReqService();
            prDt2 = prmsPurchaseRequest.getPrmsPurchaseReqServiceList().get(i);
            PrmsServiceAndWorkReg serviceName = new PrmsServiceAndWorkReg();
            serviceName = prDt2.getServiceId();
            serviceList.add(serviceName);
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Edit Data Table">
    public void onRowEdit(RowEditEvent event) {
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = 0;
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = prmsQuotationsModel.getRowIndex();
        prmsQuotation = prmsQuotations.get(rowIndex);
        recreateprmsQuotationDetailsModel();
    }

    public void onContactRowEdit(RowEditEvent event) {

        int rowIndex = prmsQuotationDetailsModel.getRowIndex();
        PrmsQuotationDetail comContPerson = (PrmsQuotationDetail) event.getObject();
        boolean found = false;

        for (int i = 0; i < prmsQuotation.getPrmsQuotationDetailList().size(); i++) {

            if (i != rowIndex) {
                if (prmsQuotation.getPrmsQuotationDetailList().get(i).getQuantity().equals(comContPerson.getQuantity())
                        && prmsQuotation.getPrmsQuotationDetailList().get(i).getQuantity().equals(comContPerson.getQuantity())) {
                    found = true;
                    break;
                }
            }
        }
        if (found == true) {
            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");
            prmsQuotation.getPrmsQuotationDetailList().set(rowIndex, comContPerson);
            recreateprmsQuotationDetailsModel();
        } else {
            prmsQuotation.getPrmsQuotationDetailList().set(rowIndex, comContPerson);
            recreateprmsQuotationDetailsModel();
        }
    }

    public void editItemDataTbl() {
        addOrModifyBundle = "Modify";
        prmsQuotationDetail = itemDataModel.getRowData();
        prmsSupplyProfile = prmsQuotationDetail.getSupId();
        mmsItemRegistration = prmsQuotationDetail.getPurchaseRquestDetId().getMaterialCodeId();
        itemList = new ArrayList<>();
        itemList.add(mmsItemRegistration);
        prmsPurchaseRequest = prmsQuotationDetail.getPurchaseRquestDetId().getPurchaseReqId();
    }

    public void editServiceDataTbl() {
        addOrModifyBundle = "Modify";
        prmsQuotationDetail = serviceDataModel.getRowData();
        prmsServiceAndWorkReg = prmsQuotationDetail.getPurchaseReqServiceId().getServiceId();
        serviceList = new ArrayList<>();
        serviceList.add(prmsServiceAndWorkReg);
        prmsPurchaseRequest = prmsQuotationDetail.getPurchaseReqServiceId().getPurchaseReqId();
    }

    public void editWorkDataTbl() {
        addOrModifyBundle = "Modify";
        prmsQuotationDetail = workDataModel.getRowData();
        prmsServiceAndWorkReg = prmsQuotationDetail.getPurchaseReqServiceId().getServiceId();
        serviceList = new ArrayList<>();
        serviceList.add(prmsServiceAndWorkReg);
        prmsPurchaseRequest = prmsQuotationDetail.getPurchaseReqServiceId().getPurchaseReqId();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Search">
    public void searchQuotation() {

        prmsQuotation.setPreparedBy(String.valueOf(workFlow.getUserAccount()));
        prmsQuotations = quatationBeanLocal.searchPrmsQuotation(prmsQuotation);
        recreateprmsQuotationsModel();
        prmsQuotation=new PrmsQuotation();
    }

    public void PopulateQuotation(SelectEvent event) {

        prmsQuotation = (PrmsQuotation) event.getObject();
        hrEmployees = prmsQuotation.getPurchaser();
        departments = prmsQuotation.getRequestedBy();
        renderpnlToSearchPage = true;
        populateWorkFlow();
        populateDatas();
        recreateDmsDataModel(); //Commented Since it through Error
        recreateWorkModel();
    }

    public void populateDatas() {
        prmsPurchaseRequest = prmsPurchaseRequestDet.getPurchaseReqId();
        mmsItemRegistration = prmsPurchaseRequestDet.getMaterialCodeId();
        if (prmsQuotation.getPurchaseType().equalsIgnoreCase("Goods")) {
            rndItem = true;
            rndSerive = false;
            rndWork = false;
            recreateItemDataModel();
        } else if (prmsQuotation.getPurchaseType().equalsIgnoreCase("Service")) {
            rndItem = false;
            rndSerive = true;
            rndWork = false;
            recreateServiceModel();
        } else {
            rndItem = false;
            rndSerive = false;
            rndWork = true;
            recreateWorkModel();
        }
        calculatingTotalInPopulate();
        newPage = true;
        searchPage = false;
        updateStatus = 1;
        saveorUpdateBundle = "Update";
    }

    public void rowSelect(SelectEvent event) {
        prmsQuotation = (PrmsQuotation) event.getObject();
        prmsQuotation.setQuatationId(prmsQuotation.getQuatationId());
        prmsQuotation = quatationBeanLocal.getSelectedRequest(prmsQuotation.getQuatationId());
        prmsPurchaseRequest = prmsPurchaseRequestDet.getPurchaseReqId();
        mmsItemRegistration = prmsPurchaseRequestDet.getMaterialCodeId();
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        saveorUpdateBundle = "Update";
        updateStatus = 1;
        String select = "select";
        select = prmsQuotation.getPurchaseType();
        if (select.equalsIgnoreCase("item")) {
            items = true;
            itemstable = true;
            service = false;
        } else if (select.equalsIgnoreCase("service")) {
            items = false;
            service = true;
        }

        recreateprmsQuotationDetailsModel();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="User Interface Pannel Controller">
    public boolean isServicePrPanel() {
        return servicePrPanel;
    }

    public void setServicePrPanel(boolean servicePrPanel) {
        this.servicePrPanel = servicePrPanel;
    }

    public boolean isWorksPrPanel() {
        return worksPrPanel;
    }

    public void setWorksPrPanel(boolean worksPrPanel) {
        this.worksPrPanel = worksPrPanel;
    }

    public void newBtnAction() {
        searchPage = false;
        newPage = true;
    }

    public void searchBtnAction() {
        searchPage = true;
        newPage = false;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Value Change Lisener">
    public void getPurchaseRqustNo(ValueChangeEvent event) {

        prmsPurchaseRequest = (PrmsPurchaseRequest) event.getNewValue();
        int size = prmsPurchaseRequest.getPrmsPurchaseRequestDetList().size();
        itemList = new ArrayList<>();
        System.out.println("----------- one --------------");

        for (int i = 0; i < size; i++) {

            PrmsPurchaseRequestDet prDtl = new PrmsPurchaseRequestDet();
            prDtl = prmsPurchaseRequest.getPrmsPurchaseRequestDetList().get(i);
            MmsItemRegistration item = new MmsItemRegistration();
            item = prDtl.getMaterialCodeId();
            itemList.add(item);
        }

        System.out.println("----------- two --------------");
        prmsPurchaseRequest = purchaseReqBeanLocal.getSelectedRequest(prmsPurchaseRequest.getId());
//        departments = prmsPurchaseRequest.getReqstrDepId();
        setDepartments(prmsPurchaseRequest.getReqstrDepId());
        prmsQuotation.setRequestedBy(departments);

        System.out.println("=================================================");
        System.out.println("Department Name ===>>>" + departments.getDepName());
        System.out.println("=================================================");
    }

    public void changeItemService(ValueChangeEvent e) {

        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("service")) {
                service = true;
                items = false;
                itemstable = false;
                serviceTable = true;

            } else {
                serviceTable = true;
                service = false;
                items = true;
                itemstable = true;
            }
        }
    }

    public void PurchaseType_Vlc(ValueChangeEvent event) {

        if (null != event.getNewValue()) {

            slected = event.getNewValue().toString();

            if (slected.equalsIgnoreCase("Goods")) {
                prmsQuotationDetail = null;
                rndItem = true;
                rndSerive = false;
                rndWork = false;
            } else if (slected.equalsIgnoreCase("Service")) {
                prmsQuotationDetail = null;
                rndItem = false;
                rndSerive = true;
                rndWork = false;
            } else {
                prmsQuotationDetail = null;
                rndItem = false;
                rndSerive = false;
                rndWork = true;
            }
        }
    }

    public void selectedParamChangeEvent(ValueChangeEvent vce) {
        if (vce.getNewValue() != null) {
            prmsQuotation.setColumnName(vce.getNewValue().toString());
            prmsQuotation.setColumnValue(null);
        }
    }

    public void changePurchaseType(ValueChangeEvent e) {
        if (null != e.getNewValue()) {
            String select = "select";
            select = e.getNewValue().toString();
            if (select.equalsIgnoreCase("Goods")) {
                serviceTable = true;
                service = false;
                items = true;
                itemstable = true;
                itemPrNumber = true;
                servicePrNumber = false;
                servicePrPanel = false;
                worksPrPanel = false;
                worksName = false;
                worksNumbers = false;
            } else if (select.equalsIgnoreCase("Service")) {
                service = true;
                items = false;
                itemstable = false;
                serviceTable = true;
                ServiceName = true;
                serviceNumber = true;
                worksName = false;
                worksNumbers = false;
                servicePrNumber = true;
                itemPrNumber = false;
                servicePrPanel = true;
                worksPrPanel = false;
            } else if (select.equalsIgnoreCase("Works")) {
                servicePrNumber = true;
                itemPrNumber = false;
                ServiceName = false;
                serviceNumber = false;
                worksName = true;
                worksNumbers = true;
                servicePrPanel = false;
                worksPrPanel = true;
                items = false;
                itemstable = false;

            }
        }
    }

    public void SupplierName(ValueChangeEvent event) {

        if (null != event.getNewValue()) {

            prmsSupplyProfile.setVendorName(event.getNewValue().toString());
            prmsSupplyProfile
                    = quatationBeanLocal.getSupplierVendor(prmsSupplyProfile);
            rate = prmsSupplyProfile.getVatTypeId().getVatRate();
            vatType = prmsSupplyProfile.getVatTypeId().getVatType();
        }
    }

    public void supplierName_vlc(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            try {
                prmsSupplyProfile = (PrmsSupplyProfile) event.getNewValue();
                prmsQuotationDetail.setSupId(prmsSupplyProfile);
                prmsVatTypeLookup = prmsSupplyProfile.getVatTypeId();
                prmsQuotationDetail.setVatTypeId(prmsVatTypeLookup);
                prmsQuotationDetail.setVatType(prmsVatTypeLookup.getVatType());
                rate = prmsSupplyProfile.getVatTypeId().getVatRate();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void getSupName(ValueChangeEvent value) {
        prmsSupplyProfile = (PrmsSupplyProfile) value.getNewValue();
        typeLookups = new ArrayList<>();
        PrmsSupplyProfile supplierVat = new PrmsSupplyProfile();
        PrmsLuVatTypeLookup supplier = new PrmsLuVatTypeLookup();
        supplier = supplierVat.getVatTypeId();
        typeLookups.add(supplier);
    }

    public void BidDateGetter(ValueChangeEvent value) {
        if (null != value.getNewValue()) {
            mmsItemRegistration = (MmsItemRegistration) value.getNewValue();
            int size = prmsPurchaseRequest.getPrmsPurchaseRequestDetList().size();
            for (int i = 0; i < size; i++) {
                PrmsPurchaseRequestDet dtl
                        = prmsPurchaseRequest.getPrmsPurchaseRequestDetList().get(i);
                if (dtl.getMaterialCodeId().equals(mmsItemRegistration)) {
                    prmsQuotationDetail.setQuantity(dtl.getReqQuantity());
                    prmsQuotationDetail.setPurchaseRquestDetId(dtl);
                    prmsQuotationDetail.setMaterialCodeId(mmsItemRegistration);
                }
            }
        }
    }

    public void BidDateGet(ValueChangeEvent value) {
        if (null != value.getNewValue()) {
            prmsServiceAndWorkReg = (PrmsServiceAndWorkReg) value.getNewValue();
            int size = prmsPurchaseRequest.getPrmsPurchaseReqServiceList().size();
            for (int i = 0; i < size; i++) {
                PrmsPurchaseReqService dtl = prmsPurchaseRequest.getPrmsPurchaseReqServiceList().get(i);
                if (dtl.getServiceId().equals(prmsServiceAndWorkReg)) {
                    prmsQuotationDetail.setQuantity(dtl.getReqQuantity());
                    prmsQuotationDetail.setPurchaseReqServiceId(dtl);
                    prmsQuotationDetail.setServAndWorkId(prmsServiceAndWorkReg);
                }
            }
        }
    }

    public void serviceNo_Vlc(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            BigDecimal id = new BigDecimal(String.valueOf(prmsServiceAndWorkReg.getServAndWorkId()));
            prmsServiceAndWorkReg.setServAndWorkId(id);
            prmsServiceAndWorkReg = quatationBeanLocal.findById(prmsServiceAndWorkReg);
        }
    }

    public void workNo_Vlc(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            BigDecimal id = new BigDecimal(String.valueOf(prmsServiceAndWorkReg.getServAndWorkId()));
            prmsServiceAndWorkReg.setServAndWorkId(id);
            prmsServiceAndWorkReg = quatationBeanLocal.findById(prmsServiceAndWorkReg);
        }
    }

    public void getSupplierList(ValueChangeEvent value) {
        if (null != value.getNewValue()) {
            prmsSupplyProfile = (PrmsSupplyProfile) value.getNewValue();
            typeLookups = new ArrayList<>();
            PrmsSupplyProfile supplierVat = new PrmsSupplyProfile();
            if (supplierVat.getVatTypeId().equals(mmsItemRegistration)) {
                prmsQuotationDetail.setVatTypeId(prmsVatTypeLookup);
            }
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Generate Auto-Increment Number">
    public String generateQuotationNo() {

        if (saveorUpdateBundle.equals("Update")) {
            newQuotationNo = prmsQuotation.getQuotationNo();
        } else {
            PrmsQuotation LastCheckNo = quatationBeanLocal.LastQuotationNo();

            if (LastCheckNo != null) {
                lastQuotationNo = LastCheckNo.getQuatationId() == null ? "0"
                        : LastCheckNo.getQuatationId().toString();
            }

            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newQuotationN = 0;

            if (lastQuotationNo.equals("0")) {
                newQuotationN = 1;
                newQuotationNo = "QO-" + newQuotationN + "/" + f.format(now);
            } else {
                String[] lastInspNos = lastQuotationNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newQuotationN = Integer.parseInt(lastDatesPaterns[0]);
                newQuotationN = newQuotationN + 1;
                newQuotationNo = "QO-" + newQuotationN + "/" + f.format(now);
            }
        }
        return newQuotationNo;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Print Page as PDF"> 
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {

        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";

        try {
            pdf.add(Image.getInstance(logo));
        } catch (com.lowagie.text.DocumentException ex) {
            ex.printStackTrace();
        }
    }
    //</editor-fold>    
    // <editor-fold defaultstate="collapsed" desc="DMS service ">
    StreamedContent file;

    DocumentModel documentModel = new DocumentModel();

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    DataModel<DocumentModel> docValueModel;

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public void onRowSelect(SelectEvent event) {
        // documentModel = (DocumentModel) event.getObject();
        prmsLuDmArchive = (PrmsLuDmArchive) event.getObject();
        isFileSelected = true;
    }

    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public void recreateDmsDataModel() {

//        newDoclist.clear();
//        DmsHandler dmsHandler = new DmsHandler();
//        DocList docList = new DocList();
//        List<String> docId = new ArrayList<>();
//
//        for (int i = 0; i < prmsQuotation.getPrmsQuotationFileUploadList().size(); i++) {
//            docId.add(prmsQuotation.getPrmsQuotationFileUploadList().get(i).getDocumentId().toString());
//        }
//        docList.setDocIds(docId);
//        DocList docListNew = dmsHandler.getDocumentsById(docList);
//
//        if (docListNew != null) {
//
//            newDoclist = docListNew.getDocList();
//            docValueModel = new ListDataModel(docListNew.getDocList());
//        }
        prmsLuDmArchivesDModel = null;
        prmsLuDmArchivesDModel = new ListDataModel<>(prmsLuDmArchivesList);
    }

    List<DocumentModel> newDoclist = new ArrayList<DocumentModel>();
    List<Integer> savedDocIds = new ArrayList<>();
    DmsHandler dmsHandler = new DmsHandler();

    public void uploadListener(FileUploadEvent event) {
        InputStream inputStream = null;
        try {
//            
            fileName = event.getFile().getFileName().split("\\.")[0];
            fileType = event.getFile().getFileName().split("\\.")[1];
            try {
                inputStream = event.getFile().getInputstream();
            } catch (Exception ex) {
                System.out.println("Error Occured " + ex.getMessage());
            }
            byteFile = dataUpload.extractByteArray(inputStream);
            if (byteFile != null) {
                System.out.println("byte File is  " + byteFile);
//               
                JsfUtil.addSuccessMessage("Upload Successfully File Name " + fileName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public StreamedContent getFile() throws Exception {
        if (isFileSelected = true) {
            System.out.println("Selected Dile Name is " + prmsLuDmArchive.getFileName());
            dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
        } else {
            JsfUtil.addFatalMessage("Pls Select File Name");
        }
        return file;
    }

    public void remove(DocumentModel document) {
        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);
        recreateDmsDataModel();
    }
    // </editor-fold>

    public ArrayList<HrEmployees> searchEmployeeByName(String hrEmployee) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setFirstName(hrEmployee);
        employee = hrEmployeesBeanLocal.searchEmployeeByName(hrEmployees);
        return employee;
    }

    public ArrayList<HrEmployees> searchEmployeeById(String EmpId) {
        ArrayList<HrEmployees> employee = null;
        hrEmployees.setEmpId(EmpId);
        employee = hrEmployeesBeanLocal.searchEmployeeById(hrEmployees);

        return employee;
    }

    public void getByFirstName(SelectEvent event) {
        try {
            hrEmployees.setFirstName(event.getObject().toString());
            hrEmployees = hrEmployeesBeanLocal.getByFirstName(hrEmployees);
            prmsQuotation.setPurchaser(hrEmployees);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
    }

    public void getByEmpId(SelectEvent event) {
        try {
            hrEmployees.setEmpId(event.getObject().toString());
            hrEmployees = hrEmployeesBeanLocal.getByEmpId(hrEmployees);
//            departments = hrEmployees.getDeptId();
//            hrInsuranceInjuredEmployee.setEmpId(hrEmployees);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
    }

    /**
     * *************************************************************************
     * ------------------------ HR Department Related -----------------------
     * ************************************************************************
     */
    public HrDepartments getDepartments() {
        if (departments == null) {
            departments = new HrDepartments();
        }
        return departments;
    }

    public void setDepartments(HrDepartments departments) {
        this.departments = departments;
    }

    public boolean isIsFileSelected() {
        return isFileSelected;
    }

    public void setIsFileSelected(boolean isFileSelected) {
        this.isFileSelected = isFileSelected;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchivesDModel() {
        if (prmsLuDmArchivesDModel == null) {
            prmsLuDmArchivesDModel = new ListDataModel<>();
        }
        return prmsLuDmArchivesDModel;
    }

    public void setPrmsLuDmArchivesDModel(DataModel<PrmsLuDmArchive> prmsLuDmArchivesDModel) {
        this.prmsLuDmArchivesDModel = prmsLuDmArchivesDModel;
    }

    public List<PrmsLuDmArchive> getPrmsLuDmArchivesList() {
        if (prmsLuDmArchivesList == null) {
            prmsLuDmArchivesList = new ArrayList<>();
        }
        return prmsLuDmArchivesList;
    }

    public void setPrmsLuDmArchivesList(List<PrmsLuDmArchive> prmsLuDmArchivesList) {
        this.prmsLuDmArchivesList = prmsLuDmArchivesList;
    }

    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        if (prmsLuDmArchiveSelection == null) {
            prmsLuDmArchiveSelection = new PrmsLuDmArchive();
        }
        return prmsLuDmArchiveSelection;
    }

    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }

    public PrmsLuDmArchive getPrmsLuDmArchive() {
        if (prmsLuDmArchive == null) {
            prmsLuDmArchive = new PrmsLuDmArchive();
        }
        return prmsLuDmArchive;
    }

    public void setPrmsLuDmArchive(PrmsLuDmArchive prmsLuDmArchive) {
        this.prmsLuDmArchive = prmsLuDmArchive;
    }

    public void goBackSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }
}
