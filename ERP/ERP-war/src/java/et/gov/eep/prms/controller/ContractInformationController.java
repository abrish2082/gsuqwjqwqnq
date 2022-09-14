package et.gov.eep.prms.controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import et.gov.eep.commonApplications.controller.DataUpload;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.businessLogic.WfPrmsProcessedBeanLocal;
import et.gov.eep.commonApplications.entity.PrmsLuDmArchive;
import et.gov.eep.commonApplications.utility.GregorianCalendarManipulation;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.prms.businessLogic.ContractInformationBeanLocal;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.prms.businessLogic.PurchaseOrderBeanLocal;
import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsAward;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsAwardDetail;
import et.gov.eep.prms.entity.PrmsContractDetail;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.prms.businessLogic.PurchaseReqBeanLocal;
import et.gov.eep.prms.entity.PrmsPurchaseRequest;
import et.gov.eep.prms.entity.PrmsQuotation;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.businessLogic.InsuranceNotificationToBankBeanLocal;
import et.gov.eep.prms.entity.PrmsContractFileUpload;
import et.gov.eep.prms.entity.PrmsPortEntry;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.commons.io.FileUtils;
import org.insa.client.DmsHandler;
import org.insa.model.DocList;
import org.insa.model.DocumentModel;
import securityBean.Utility;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import securityBean.Constants;
import securityBean.SessionBean;
import securityBean.WorkFlow;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.prms.businessLogic.FinancialEvaluationBeanLocal;
import et.gov.eep.prms.entity.PrmsBidAmend;
import et.gov.eep.prms.entity.PrmsContractCurrencyDetail;
import java.io.IOException;
import java.util.Iterator;

@Named(value = "contractInformationController")
@ViewScoped
public class ContractInformationController implements Serializable {

    @Inject
    SessionBean SessionBean;
    @EJB
    FinancialEvaluationBeanLocal finanlEvalutionBeanLocal;
    @Inject
    private PrmsContractCurrencyDetail papmsContractCurrDetail;
    @EJB
    FmsLuCurrencyFacade fmsLuCurrencyFacade;
    @EJB
    private PurchaseReqBeanLocal purchaseReqBeanLocal;
    @EJB
    private ContractInformationBeanLocal contractInformationBeanLocal;
    @EJB
    private PurchaseOrderBeanLocal purchaseOrderBeanLocal;
    @EJB
    WfPrmsProcessedBeanLocal wfPrmsProcessedBeanLocal;
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;
    @EJB
    InsuranceNotificationToBankBeanLocal insuranceNotificationToBankBeanLocal;
    @Inject
    PrmsPurchaseRequest prmsPurchaseRequest;
    @Inject
    private PrmsContract papmsContract;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    private PrmsAward papmsAward;
    @Inject
    private PrmsSupplyProfile eepVendorReg;
    @Inject
    private PrmsBid eepBidReg;
    @Inject
    PrmsBidAmend prmsBidAmend;
    @Inject
    private PrmsQuotation eepQuotation;
    @Inject
    private PmsCreateProjects eepProject;
    @Inject
    private MmsItemRegistration mmsItemRegistration;
    @Inject
    private PrmsPortEntry prmsPortEntry;
    @Inject
    private PrmsContractDetail papmsContractDetail;
    @Inject
    private PrmsAwardDetail papmsAwardDetail;
    @Inject
    private DataUpload papmsDataUpload;
    @Inject
    PrmsContractFileUpload prmsContractFileUpload;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @EJB
    WfPrmsProcessedBeanLocal PrmsProcessedBeanLocal;
    @Inject
    WorkFlow workFlow;//    @Inject
    @Inject
    DataUpload dataUpload;
    @Inject
    PrmsLuDmArchive prmsLuDmArchive;
    List<FmsLuCurrency> fmsLuCurrencyList = new ArrayList<>();
    private String selectedValue = "";
    List<PrmsContract> contractSearchParameterLst;
    List<PrmsBidAmend> bidListFromAmendment;
    List<PrmsAwardDetail> prmsAwardDetails;
    List<PrmsContractDetail> prmsContractDetails;
//    List<PrmsContractCurrencyDetail> prmsContractCurrDetails;
    List<PrmsAward> prmsAwardsList;
    List<PrmsPortEntry> prmsPortList;
    List<PrmsSupplyProfile> prmsSupplierList;
    List<HrDepartments> departmentsLists;
    List<HrDepartments> arrayDepLists;
    private List<PrmsContract> filteredItems;
    private boolean disableBefrDay = true;
    double vat = 0.15;
    double groundTotal = 0.0;
    double totalSum = 0.0;
    double totalvat = 0.0;
    int saveStatus = 0;
    int requestNotificationCounter = 0;
    long unitprice = 0;
    long quantity = 0;
    long totalPrice = 0;

    private String icone = "ui-icon-plus";
    private boolean chooseselecttOption;
    private boolean chooseProjectreader = false;
    private String activeIndex;
    private String duplicattion = null;
    private String selectOptPartyName;
    String newcheckListNo;
    private String userName;
//    private String icone = "ui-icon-plus";
//    private String icone2 = "ui-icon-search";
    private String saveorUpdateBundle = "Save";
    private String addressInfoButton = "Add";
    private String createOrSearchBundle = "New";
    private String searchBundle = "Search";
    String supplierType = "local";
    String selecttOption = "hidden";
    String localreader = "hidden";
    private String checkAsBid = "Bid";
    private String chooserenderForcol1 = "Name";
    private String chooserenderForSupplier = "Supplier Name";
    String LastcheckListNo = "0";
    String slected = "Select One";
    String logerName;
    ArrayList<PrmsAward> listOfApprovedInspReqList;
    Set<String> addressCheck = new HashSet<>();
    private boolean disableBtnCreate = false;
    private boolean renderPnlCreateParty = false;
    private boolean renderPnlManPage = true;
    private boolean chooserenderForInternational = true;
    private boolean chooserenderForLocal = false;
    private boolean chooseDisableForLocal = false;
    private boolean chooserenderForDataTableItem = false;
    private boolean chooserenderForDataTableService = false;
    private boolean renderpnlToSearchPage;
    private List<PrmsBid> listOfBids;
    private List<PrmsQuotation> listOfQuotation;
    private List<PmsCreateProjects> listOfProjectses;
    int updateStatus = 0;
    DataModel<PrmsContractDetail> PrmsContractDetailModel;
    DataModel<PrmsContractCurrencyDetail> prmsContractCurrencyModel;
    DataModel<PrmsContract> PrmsContractModel;
    DataModel<WfPrmsProcessed> wfPrmsProcessedDModel;
    DataModel<PrmsLuDmArchive> prmsLuDmArchiveDataModel;
    List<PrmsContract> prmsContracts;
    private List<PrmsContractDetail> contractDetailList;
    private PrmsContract selectContractInformation;
    private PrmsContractCurrencyDetail selectContractCurrInformation;
    private List<FmsLuCurrency> listOfCur;
    private Date contSigningDatePlus;
    private String contSigningDatePlus_;
    boolean renderDecision;
    private boolean isRowFileSelected;
    boolean isRenderCreate;
    byte[] byteData;
    String docFormat;
    String fileName;
    private PrmsLuDmArchive prmsLuDmArchiveSelection;
    StreamedContent fileDownload;

    // <editor-fold defaultstate="collapsed" desc="Post Construct">
    @PostConstruct
    public void init() {

        contractLis1 = contractInformationBeanLocal.ContractListStatus();
        chooserenderForDataTableService = false;
        chooserenderForDataTableItem = false;
        setChooserenderForInternational(false);
        setChooserenderForLocal(true);
        setUserName(workFlow.getUserName());
        setLogerName(SessionBean.getUserName());
        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
        setFmsLuCurrencyList(contractInformationBeanLocal.getCurrencylist());

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

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Create New Pages ...">
    public String generateContractNo() {

        PrmsContract LastContractNo = contractInformationBeanLocal.getLastContractNo();
        if (LastContractNo != null) {
            LastcheckListNo = LastContractNo.getContractId().toString();
        }
        DateFormat f = new SimpleDateFormat("yyyy");
        Date now = new Date();
        int newcheckListN = 0;

        if (LastcheckListNo.equals("0")) {
            newcheckListN = 1;
            newcheckListNo = "CO-" + newcheckListN + "/" + f.format(now);
        } else {
            String[] lastInspNos = LastcheckListNo.split("-");
            String lastDatesPatern = lastInspNos[0];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            newcheckListN = Integer.parseInt(lastDatesPaterns[0]);
            newcheckListN = newcheckListN + 1;
            newcheckListNo = "CO-" + newcheckListN + "/" + f.format(now);
        }

        return newcheckListNo;
    }

    public String generateSpecficationNo() {

        if (saveorUpdateBundle.equals("Update")) {
            newcheckListNo = papmsContract.getContractNo();

        } else {
            PrmsContract LastContractNo
                    = contractInformationBeanLocal.getLastContractNo();

            if (LastContractNo != null) {
                LastcheckListNo = LastContractNo.getContractId().toString();
            }

            DateFormat f = new SimpleDateFormat("yyyy");
            Date now = new Date();
            int newBidNoLast = 0;

            if (LastcheckListNo.equals("0")) {
                newBidNoLast = 1;
                newcheckListNo = "CO-" + newBidNoLast + "/" + f.format(now);
            } else {
                String[] lastInspNos = LastcheckListNo.split("-");
                String lastDatesPatern = lastInspNos[0];
                String[] lastDatesPaterns = lastDatesPatern.split("/");
                newBidNoLast = Integer.parseInt(lastDatesPaterns[0]);
                newBidNoLast = newBidNoLast + 1;
                newcheckListNo = "CO-" + newBidNoLast + "/" + f.format(now);
            }
        }

        return newcheckListNo;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Value Change Lisenter">
    public void changeEventGetawardNo(ValueChangeEvent event) {

        if (null != event.getNewValue() && !event.getNewValue().equals("")
                && !event.getNewValue().equals("--- Select One ---")) {

            eepBidReg = (PrmsBid) event.getNewValue();
            // If Local or International Bid Types
            if (eepBidReg.getBidType().equalsIgnoreCase("international")) {
                setChooserenderForLocal(false);
                setChooseDisableForLocal(true);
                setChooserenderForInternational(true);
            } else if (eepBidReg.getBidType().equalsIgnoreCase("local")) {
                setChooserenderForLocal(true);
                setChooseDisableForLocal(false);
                setChooserenderForInternational(false);
            }
            if (eepBidReg.getBidCategory().equalsIgnoreCase("Goods")) {
                chooserenderForSupplier = "Supplier Name:*";
                chooserenderForDataTableService = false;
                chooserenderForDataTableItem = true;
            } else if (eepBidReg.getBidCategory().equalsIgnoreCase("Work")) {
                chooserenderForSupplier = "Contractor Name:*";
                chooserenderForDataTableService = true;
                chooserenderForDataTableItem = false;
            } else if (eepBidReg.getBidCategory().equalsIgnoreCase("Service")) {
                chooserenderForSupplier = "Consultant Name:*";
                chooserenderForDataTableService = true;
                chooserenderForDataTableItem = false;
            } else {
                chooserenderForSupplier = "";
                chooserenderForDataTableService = false;
                chooserenderForDataTableItem = false;
            }
        }

        if (eepBidReg.getProjectId() != null) {
            chooseProjectreader = true;
        } else {
            chooseProjectreader = false;
        }
        if (eepBidReg.getBidType().equalsIgnoreCase("International")) {
            chooseselecttOption = true;
        } else {
            chooseselecttOption = false;
        }

        prmsBidAmend.setBidId(eepBidReg);
        bidListFromAmendment = contractInformationBeanLocal.checkBidFromAmended(eepBidReg);
        if (bidListFromAmendment.size() > 0) {
            prmsBidAmend = contractInformationBeanLocal.getBidAmendInfoByBidId(prmsBidAmend);
            papmsContract.setBidId(eepBidReg);
            eepBidReg.setBidCategory(eepBidReg.getBidCategory());
            eepBidReg.setBidType(eepBidReg.getBidType());
        }
//      Other Part -------------------------------------------------------------
        eepProject = eepBidReg.getProjectId();
        prmsAwardsList = contractInformationBeanLocal.getsupplierlist(eepBidReg);
        chooserenderForcol1 = eepBidReg.getBidCategory() + " Name";
    }

    public void changeEventGetSuppName(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            papmsAward = (PrmsAward) event.getNewValue();
            prmsAwardsList = contractInformationBeanLocal.getsupplierlist(eepBidReg);
        }
    }

    public void changeEventGetSuppNameAW(ValueChangeEvent event) {

        if (null != event.getNewValue() && !event.getNewValue().equals("")) {

            papmsAward = (PrmsAward) event.getNewValue();
            eepVendorReg = papmsAward.getSuppId();
            papmsContract.setAwardId(papmsAward);
            papmsContractDetail.setContractId(papmsContract);
            papmsAwardDetail.setAwardId(papmsAward);

            if (eepBidReg.getBidCategory().equalsIgnoreCase("Goods")) {
                chooserenderForDataTableService = false;
                chooserenderForDataTableItem = true;
            } else if (eepBidReg.getBidCategory().equalsIgnoreCase("Work")) {
                chooserenderForDataTableService = true;
                chooserenderForDataTableItem = false;
            } else if (eepBidReg.getBidCategory().equalsIgnoreCase("Service")) {
                chooserenderForDataTableService = true;
                chooserenderForDataTableItem = false;
            } else {
                chooserenderForSupplier = "";
                chooserenderForDataTableService = false;
                chooserenderForDataTableItem = false;
            }
            papmsContract.getPrmsContractDetailList().clear();

            for (int i = 0; i < papmsAward.getPrmsAwardDetailList().size(); i++) {
                if (papmsAward.getPrmsAwardDetailList().get(i).getMaterialId() != null) {
                    papmsContractDetail.setItemId(
                            papmsAward.getPrmsAwardDetailList().get(i).getMaterialId());
                } else if (papmsAward.getPrmsAwardDetailList().get(i).getServiceWorkId() != null) {
                    papmsContractDetail.setServiceWorkId(
                            papmsAward.getPrmsAwardDetailList().get(i).getServiceWorkId());
                }

                papmsContractDetail.setQuantity(new Long(
                        papmsAward.getPrmsAwardDetailList().get(i).getQuantity()));
                papmsContractDetail.setUnitPrice(new Double(
                        papmsAward.getPrmsAwardDetailList().get(i).getUnitPrice()).longValue());
                papmsContractDetail.setTotalPrice(
                        papmsAward.getPrmsAwardDetailList().get(i).getQuantity()
                        * papmsAward.getPrmsAwardDetailList().get(i).getUnitPrice());

                papmsContract.getPrmsContractDetailList().add(papmsContractDetail);
            }

            recreateContractDtlModel();
        }
    }

    public void getValueofAwardNo(ValueChangeEvent event) {

        if (null != event.getNewValue().toString()) {
            String AwardNo = event.getNewValue().toString();
            prmsAwardDetails = purchaseOrderBeanLocal.getAwardDetailLists(AwardNo);
            for (int i = 0; i < prmsAwardDetails.size(); i++) {
                papmsContractDetail = new PrmsContractDetail();
                papmsContractDetail.setQuantity(new Long(prmsAwardDetails.get(i).getQuantity()));
                papmsContractDetail.setUnitPrice(new Double(prmsAwardDetails.get(i).getUnitPrice()).longValue());
                unitprice = papmsContractDetail.getUnitPrice();
                double totalprice = quantity * unitprice;
                papmsContractDetail.setTotals(totalprice);
                totalSum = totalSum + totalprice;
                totalvat = 0.15 * totalSum;
                groundTotal = totalSum + totalvat;
                papmsContract.getPrmsContractDetailList().add(
                        papmsContractDetail);
            }
        }
        recreateContractDtlModel();
    }

    public void ContractEndDateAmChange(ValueChangeEvent event) {
        this.papmsContract.setContractenddateam((String) event.getNewValue());
        String ContractenddateAm_ = this.papmsContract.getContractenddateam();
        Date date_;
        try {
            date_ = new SimpleDateFormat("dd/MM/yyyy").parse(ContractenddateAm_);
            String parsedStr = new SimpleDateFormat("yyyy-MM-dd").format(date_);
            String dateString2 = GregorianCalendarManipulation.ethiopianToGregorian(parsedStr);
            Date ContractenddateAm_GC = new SimpleDateFormat("yyyy-MM-dd").parse(dateString2);
            this.papmsContract.setCommencmentDate(ContractenddateAm_GC);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ***********************************************************************
     */
    public void contractSigningDateAmChange(ValueChangeEvent event) {

        this.papmsContract.setContractdateam((String) event.getNewValue());
        String contractSigningdateAm_ = this.papmsContract.getContractdateam();
        Date date_;

        try {
            date_ = new SimpleDateFormat("dd/MM/yyyy").parse(contractSigningdateAm_);
            String parsedStr = new SimpleDateFormat("yyyy-MM-dd").format(date_);
            String dateString2 = GregorianCalendarManipulation.ethiopianToGregorian(parsedStr);
            Date contractSigningDateGC = new SimpleDateFormat("yyyy-MM-dd").parse(dateString2);
            this.papmsContract.setContractsigningDate(contractSigningDateGC);

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

        this.papmsContract.setCommencementdateam((String) event.getNewValue());
        String CommencementDateAm_ = this.papmsContract.getCommencementdateam();
        Date date_;

        try {
            date_ = new SimpleDateFormat("dd/MM/yyyy").parse(CommencementDateAm_);
            String parsedStr = new SimpleDateFormat("yyyy-MM-dd").format(date_);
            String dateString2 = GregorianCalendarManipulation.ethiopianToGregorian(parsedStr);
            Date CommencementDateGC = new SimpleDateFormat("yyyy-MM-dd").parse(dateString2);
            this.papmsContract.setCommencmentDate(CommencementDateGC);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    public void ContractEndDateChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            Date contractendDate_ = (Date) event.getNewValue();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strinEndDate = "-";
            strinEndDate = formatter.format(contractendDate_);
            String strinContractendDateET
                    = GregorianCalendarManipulation.gregorianToEthiopian(strinEndDate);
            DateFormat to = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String strDate = to.format(from.parse(strinContractendDateET));
                papmsContract.setContractenddateam(strDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void ContractDateChange(ValueChangeEvent event) {
        this.papmsContract.setContractsigningDate((Date) event.getNewValue());
        if (event.getNewValue() != null) {
            Date contractDateDate_ = this.papmsContract.getContractsigningDate();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strinEndDate = "-";
            strinEndDate = formatter.format(contractDateDate_);
            String stringcontractDate_ET
                    = GregorianCalendarManipulation.ethiopianToGregorian(strinEndDate);
            DateFormat to = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String strDate = to.format(from.parse(stringcontractDate_ET));
                papmsContract.setContractdateam(strDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void ContPeriodTChange(ValueChangeEvent event) {

        boolean signingDateNotnull = false;
        if (this.papmsContract.getContractsigningDate() != null) {
            signingDateNotnull = true;
        }
        if (this.papmsContract.getContractdateam() != null) {
            signingDateNotnull = true;
        }

        if (event.getNewValue() != null && signingDateNotnull) {
            this.papmsContract.setContractperiodtype(event.getNewValue().toString());
            if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("day")) {
                disableBefrDay = false;
            } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("month")) {
                disableBefrDay = false;
            } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("year")) {
                disableBefrDay = false;
            } else {
                disableBefrDay = true;
            }
        } else {
            disableBefrDay = true;
        }
    }

    public void CommencemntDateChange(ValueChangeEvent event) {

        this.papmsContract.setCommencmentDate((Date) event.getNewValue());

        if (event.getNewValue() != null) {
            Date commencementDate_ = this.papmsContract.getCommencmentDate();
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
                papmsContract.setCommencementdateam(strDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        if (event.getNewValue() != null
                && this.papmsContract.getContractperiodtype() != null) {
            this.papmsContract.setCommencmentDate((Date) event.getNewValue());
            if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("day")) {
                disableBefrDay = false;
            } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("month")) {
                disableBefrDay = false;
            } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("year")) {
                disableBefrDay = false;
            } else {
                disableBefrDay = true;
            }
        } else if (this.papmsContract.getContractperiodtype() == null) {
            disableBefrDay = true;
        } else {
            disableBefrDay = true;
        }
    }
//    int rowIndex;
//
//    public int getRowIndex() {
//        return rowIndex;
//    }
//
//    public void setRowIndex(int rowIndex) {
//        this.rowIndex = rowIndex;
//    }

    int rowIndexCurr;

    public int getRowIndexCurr() {
        return rowIndexCurr;
    }

    public void setRowIndexCurr(int rowIndexCurr) {
        this.rowIndexCurr = rowIndexCurr;
    }

    public void onContactCurrRowEdit(RowEditEvent event) {

        setRowIndexCurr(prmsContractCurrencyModel.getRowIndex());
        PrmsContractCurrencyDetail comContPerson
                = (PrmsContractCurrencyDetail) event.getObject();

        papmsContract.getPrmsContractCurrencyDetailList().set(getRowIndexCurr(), comContPerson);
        recreateContractCurrDetailModel();
    }

    public void onContactCurrencyRowCancel(RowEditEvent event) {

        setRowIndexCurr(prmsContractCurrencyModel.getRowIndex());
        PrmsContractCurrencyDetail comContPerson
                = (PrmsContractCurrencyDetail) event.getObject();

        papmsContract.getPrmsContractCurrencyDetailList().set(getRowIndexCurr(), comContPerson);
        recreateContractCurrDetailModel();
    }

    public PrmsLuDmArchive getPrmsLuDmArchive() {
        return prmsLuDmArchive;
    }

    public boolean isIsRowFileSelected() {
        return isRowFileSelected;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public boolean isRenderpnlToSearchPage() {
        return renderpnlToSearchPage;
    }

    public void setRenderpnlToSearchPage(boolean renderpnlToSearchPage) {
        this.renderpnlToSearchPage = renderpnlToSearchPage;
    }

    public StreamedContent getFileDownload() throws Exception {
        if (isRowFileSelected == true) {
            System.out.println("When Downloading");
//            fileDownload = dataUpload.getFile(documentModel);
            fileDownload = dataUpload.getPrmsFileRefNumber(prmsLuDmArchive);
            System.out.println("you downloaded  " + fileDownload.getName());
        } else {
            JsfUtil.addFatalMessage("Pls Select a Row File U want to Download");
        }
        return fileDownload;
    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }

    public void setIsRowFileSelected(boolean isRowFileSelected) {
        this.isRowFileSelected = isRowFileSelected;
    }

    public PrmsLuDmArchive getPrmsLuDmArchiveSelection() {
        return prmsLuDmArchiveSelection;
    }

    public void setPrmsLuDmArchiveSelection(PrmsLuDmArchive prmsLuDmArchiveSelection) {
        this.prmsLuDmArchiveSelection = prmsLuDmArchiveSelection;
    }

    public DataModel<PrmsLuDmArchive> getPrmsLuDmArchiveDataModel() {
        if (prmsLuDmArchiveDataModel == null) {
            prmsLuDmArchiveDataModel = new ListDataModel<>();
        }
        return prmsLuDmArchiveDataModel;
    }

    public void setPrmsLuDmArchiveDataModel(DataModel<PrmsLuDmArchive> prmsLuDmArchiveDataModel) {
        this.prmsLuDmArchiveDataModel = prmsLuDmArchiveDataModel;
    }

    public void setPrmsLuDmArchive(PrmsLuDmArchive prmsLuDmArchive) {
        if (prmsLuDmArchive == null) {
            prmsLuDmArchive = new PrmsLuDmArchive();
        }
        this.prmsLuDmArchive = prmsLuDmArchive;
    }

    public List<FmsLuCurrency> getFmsLuCurrencyList() {
        return fmsLuCurrencyList;
    }

    public void setFmsLuCurrencyList(List<FmsLuCurrency> fmsLuCurrencyList) {
        this.fmsLuCurrencyList = fmsLuCurrencyList;
    }

    public DataModel<PrmsContractCurrencyDetail> getPrmsContractCurrencyModel() {

        if (prmsContractCurrencyModel == null) {
            prmsContractCurrencyModel = new ListDataModel<>();
        }

        return prmsContractCurrencyModel;
    }

    public void setPrmsContractCurrencyModel(
            DataModel<PrmsContractCurrencyDetail> prmsContractCurrencyModel) {
        this.prmsContractCurrencyModel = prmsContractCurrencyModel;
    }

    public PrmsContractCurrencyDetail getPapmsContractCurrDetail() {
        if (papmsContractCurrDetail == null) {
            papmsContractCurrDetail = new PrmsContractCurrencyDetail();
        }
        return papmsContractCurrDetail;
    }

    public void setPapmsContractCurrDetail(PrmsContractCurrencyDetail papmsContractCurrDetail) {
        this.papmsContractCurrDetail = papmsContractCurrDetail;
    }

    public DataModel<PrmsContractDetail> getPrmsContractDetailModel() {
        if (PrmsContractDetailModel == null) {
            PrmsContractDetailModel = new ListDataModel<>();
        }
        return PrmsContractDetailModel;
    }

    public void setPrmsContractDetailModel(
            DataModel<PrmsContractDetail> PrmsContractDetailModel) {
        this.PrmsContractDetailModel = PrmsContractDetailModel;
    }

    public DataModel<PrmsContract> getPrmsContractModel() {
        return PrmsContractModel;
    }

    public void setPrmsContractModel(DataModel<PrmsContract> PrmsContractModel) {
        this.PrmsContractModel = PrmsContractModel;
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

    public Set<String> getAddressCheck() {
        return addressCheck;
    }

    public void setAddressCheck(Set<String> addressCheck) {
        this.addressCheck = addressCheck;
    }

    public ArrayList<PrmsAward> getListOfApprovedInspReqList() {
        return listOfApprovedInspReqList;
    }

    public void setListOfApprovedInspReqList(
            ArrayList<PrmsAward> listOfApprovedInspReqList) {
        this.listOfApprovedInspReqList = listOfApprovedInspReqList;
    }

    public long getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(long unitprice) {
        this.unitprice = unitprice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getSlected() {
        return slected;
    }

    public void setSlected(String slected) {
        this.slected = slected;
    }

    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    public PrmsSupplyProfile getEepVendorReg() {
        if (eepVendorReg == null) {
            eepVendorReg = new PrmsSupplyProfile();
        }
        return eepVendorReg;
    }

    public void setEepVendorReg(PrmsSupplyProfile eepVendorReg) {
        this.eepVendorReg = eepVendorReg;
    }

    public PrmsBid getEepBidReg() {
        if (eepBidReg == null) {
            eepBidReg = new PrmsBid();
        }
        return eepBidReg;
    }

    public void setEepBidReg(PrmsBid eepBidReg) {
        this.eepBidReg = eepBidReg;
    }

    public PrmsQuotation getEepQuotation() {
        return eepQuotation;
    }

    public void setEepQuotation(PrmsQuotation eepQuotation) {
        this.eepQuotation = eepQuotation;
    }

    public PmsCreateProjects getEepProject() {
        if (eepProject == null) {
            eepProject = new PmsCreateProjects();
        }
        return eepProject;
    }

    public void setEepProject(PmsCreateProjects eepProject) {
        this.eepProject = eepProject;
    }

    public PrmsPortEntry getPrmsPortEntry() {
        if (prmsPortEntry == null) {
            prmsPortEntry = new PrmsPortEntry();
        }
        return prmsPortEntry;
    }

    public void setPrmsPortEntry(PrmsPortEntry prmsPortEntry) {
        this.prmsPortEntry = prmsPortEntry;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isChooseProjectreader() {
        return chooseProjectreader;
    }

    public void setChooseProjectreader(boolean chooseProjectreader) {
        this.chooseProjectreader = chooseProjectreader;
    }

    public ContractInformationBeanLocal getContractInformationBeanLocal() {
        return contractInformationBeanLocal;
    }

    public void setContractInformationBeanLocal(
            ContractInformationBeanLocal contractInformationBeanLocal) {
        this.contractInformationBeanLocal = contractInformationBeanLocal;
    }

    public PrmsContract getPapmsContract() {
        if (papmsContract == null) {
            papmsContract = new PrmsContract();
        }
        return papmsContract;
    }

    public void setPapmsContract(PrmsContract papmsContract) {
        this.papmsContract = papmsContract;
    }

    public PrmsContractDetail getPapmsContractDetail() {

        if (papmsContractDetail == null) {
            papmsContractDetail = new PrmsContractDetail();
        }
        return papmsContractDetail;
    }

    public void setPapmsContractDetail(PrmsContractDetail papmsContractDetail) {
        this.papmsContractDetail = papmsContractDetail;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    private void clearPopUp() {

        papmsContractDetail = null;
    }

    private String clearContractPage() {

        papmsContract = null;
        PrmsContractDetailModel = null;
        prmsContractCurrencyModel = null;
        saveorUpdateBundle = "Save";
        updateStatus = 0;
        groundTotal = 0.0;
        totalSum = 0.0;
        totalvat = 0.0;
        papmsAward = null;
        eepBidReg = null;
        eepVendorReg = null;
        fmsLuCurrency = null;
        eepProject = null;
        prmsPortEntry = null;
        newDoclist = new ArrayList<>();
        savedDocIds = new ArrayList<>();
        fmsLuCurrency = null;

        return null;
    }

    public void createNewParty() {

        clearContractPage();
        listOfBids = contractInformationBeanLocal.bidNumberList();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;

        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateParty = true;
            renderPnlManPage = false;
            createOrSearchBundle = "New";
//            setIcone("ui-icon-plus");
        }
    }

    public void goBackToSearchPageBtnAction() {
        renderPnlCreateParty = false;
        renderpnlToSearchPage = false;
        renderPnlManPage = true;
    }

    public void newBtnAction() {
        clearContractPage();
        saveorUpdateBundle = "Save";
        disableBtnCreate = false;
        renderpnlToSearchPage = false;
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

    public void removeContactPersonInfo() {

        int rowIndex = PrmsContractDetailModel.getRowIndex();
        papmsContractDetail = papmsContract.getPrmsContractDetailList().get(rowIndex);
        papmsContract.getPrmsContractDetailList().remove(rowIndex);
        recreateContractDtlModel();

        if (saveorUpdateBundle.equals("Update")) {
            contractInformationBeanLocal.deletePrmsContractDetail(papmsContractDetail);
        }
    }

    public void onRowEdit(RowEditEvent event) {

        renderPnlCreateParty = true;
        renderPnlManPage = false;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        createOrSearchBundle = "Search";
//        setIcone("ui-icon-search");
        disableBtnCreate = true;
        updateStatus = 1;
        int rowIndex = PrmsContractModel.getRowIndex();
        papmsContract = prmsContracts.get(rowIndex);
        recreateContractDtlModel();
        recreatePrmsContractModel();
    }

    public void onContactRowEdit(RowEditEvent event) {

        int rowIndex = PrmsContractDetailModel.getRowIndex();
        PrmsContractDetail comContPerson = (PrmsContractDetail) event.getObject();
        boolean found = false;

        for (int i = 0; i < papmsContract.getPrmsContractDetailList().size(); i++) {

            if (i != rowIndex) {

                if (papmsContract.getPrmsContractDetailList().get(i).getItemId().equals(
                        comContPerson.getItemId())) {
                    found = true;
                    break;
                }
            }
        }

        if (found == true) {

            JsfUtil.addSuccessMessage("Duplicate Entry Is Not Allowed !!");
            comContPerson.setItemId(null);
            papmsContract.getPrmsContractDetailList().set(rowIndex, comContPerson);
            recreateContractDtlModel();

        } else {
            papmsContract.getPrmsContractDetailList().set(rowIndex, comContPerson);
            recreateContractDtlModel();
        }
    }

    public void onContactRowCancel(RowEditEvent event) {

        if (event != null) {
        }
    }

    /**
     * *************************************************************************
     *
     *************************************************************************
     */
    public void saveContractInfo() {

        try {
            AAA securityService = new AAA();
            IAdministration security
                    = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");

            if (security.checkAccess(SessionBean.getUserName(), "saveContractInfo", dataset)) {

                PrmsContractFileUpload hrDisciplineLocal = new PrmsContractFileUpload();
                // PrmsContractCurrencyDetail currencyLocal = new PrmsContractCurrencyDetail();

                if (saveorUpdateBundle.equals("Save")) {
                    if (updateStatus == 0) {
                        try {
                            generateContractNo();
                            papmsContract.setContractNo(newcheckListNo);
                            papmsContract.setBidId(eepBidReg);//  papmsContract.setSuppId(eepVendorReg);
                            papmsContract.setProjectId(eepBidReg.getProjectId());
                            eepProject = eepBidReg.getProjectId();
                            /*----------------------------------------------------------------------------                    
                             Find Current Date and set on Document Registeration
                             -----------------------------------------------------------------------------*/
                            Date currentDate = Calendar.getInstance().getTime();
                            papmsContract.setRigisteredDate(currentDate);
                            papmsContract.setStatus(Constants.PREPARE_VALUE);
                            papmsContract.setAwardId(papmsAward);
                            papmsContract.setPreparedBy(String.valueOf(SessionBean.getUserId()));
                            papmsContract.setPreparedDate(currentDate);
                            papmsContract.setSuppId(eepVendorReg);
                            wfPrmsProcessed.setDecision(String.valueOf(papmsContract.getStatus()));//-
                            wfPrmsProcessed.setContractId(papmsContract);//wfPrmsProcessed.setProcessedOn(currentDate);
                            wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());

                            for (int i = 0; i < savedDocIds.size(); i++) {
                                hrDisciplineLocal = new PrmsContractFileUpload();
                                hrDisciplineLocal.setDocumentId(savedDocIds.get(i));
                                papmsContract.add(hrDisciplineLocal);
                            }

                            for (int i = 0; i < savedCurrencyIds.size(); i++) {
                                papmsContractCurrDetail.setContractId(papmsContract);
                                papmsContractCurrDetail.setCurrencyId(savedCurrencyIds.get(i));
                                papmsContract.addCOntractCurrInfo(papmsContractCurrDetail);
                            }//papmsContractCurrDetail = currencyLocal;

                            contractInformationBeanLocal.create(papmsContract);
                            wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                            JsfUtil.addSuccessMessage("Contract information is successfuly Registered!!");
                            savedCurrencyIds.clear();
                            checkEvaluation.clear();
                            clearContractPage();

                        } catch (Exception e) {
                            e.printStackTrace();
                            JsfUtil.addFatalMessage("Something Error Occured on Data Saved" + e);
                        }
                    }
                } else if (workFlow.isPrepareStatus() && saveorUpdateBundle.equals("Update")) {

                    try {
                        papmsContract.setSuppId(eepVendorReg);
                        eepProject = eepBidReg.getProjectId();
                        papmsContract.setProjectId(eepProject);//papmsContractCurrDetail.setCurrencyId(fmsLuCurrency);
                        for (int i = 0; i < savedDocIds.size(); i++) {
                            hrDisciplineLocal = new PrmsContractFileUpload();
                            hrDisciplineLocal.setDocumentId(savedDocIds.get(i));
                            papmsContract.add(hrDisciplineLocal);
                        }
                        int numberOfCurrency = papmsContract.getPrmsContractCurrencyDetailList().size();

                        for (int i = 0; i < savedCurrencyIds.size(); i++) {
                            papmsContractCurrDetail.setContractId(papmsContract);
                            papmsContractCurrDetail.setCurrencyId(savedCurrencyIds.get(i));
                            papmsContract.addCOntractCurrInfo(papmsContractCurrDetail);
                        }
                        ;
                        contractInformationBeanLocal.update(papmsContract);// wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
                        JsfUtil.addSuccessMessage("Contract Information is Successfuly Updated!");
                        saveorUpdateBundle = "Save";
                        savedCurrencyIds.clear();
                        checkEvaluation.clear();
                        clearContractPage();

                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addFatalMessage("Something Error Occured on Data Modified");
                    }

                } else if ((workFlow.isApproveStatus() || workFlow.isCheckStatus()) && saveorUpdateBundle.equals("Update")) {

                    try {
                        if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_VALUE);
                            papmsContract.setStatus(Constants.APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Approve") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_APPROVE_VALUE);
                            papmsContract.setStatus(Constants.CHECK_APPROVE_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isApproveStatus()) {
                            workFlow.setUserStatus(Constants.APPROVE_REJECT_VALUE);
                            papmsContract.setStatus(Constants.APPROVE_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.APPROVE_REJECT_VALUE));
                        } else if (selectedValue.equalsIgnoreCase("Reject") && workFlow.isCheckStatus()) {
                            workFlow.setUserStatus(Constants.CHECK_REJECT_VALUE);
                            papmsContract.setStatus(Constants.CHECK_REJECT_VALUE);
                            wfPrmsProcessed.setDecision(String.valueOf(Constants.CHECK_REJECT_VALUE));
                        }

                        wfPrmsProcessed.setDecision(String.valueOf(papmsContract.getStatus()));//-
                        wfPrmsProcessed.setContractId(papmsContract);
                        wfPrmsProcessed.setProcessedBy(SessionBean.getUserId());
                        JsfUtil.addSuccessMessage("Contract Information is Successfuly Updated !!");
                        clearContractPage();
                        saveorUpdateBundle = "Save";

                    } catch (Exception e) {
                        e.printStackTrace();
                        JsfUtil.addFatalMessage("Something Error Occured on Data Modified");
                    }
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(SessionBean.getSessionID());
                eventEntry.setUserId(SessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<>(
                        qualifiedName, String.class, null, SessionBean.getUserName());
                eventEntry.setUserLogin(test);
                //...add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    public void handelerRadiobutton(SelectEvent event) {
        String selected = papmsContract.getContractName();

        if (selected.equalsIgnoreCase("Local")) {
            selecttOption = "hidden";
            localreader = "display";
        } else {
            selecttOption = "display";
            localreader = "hidden";
        }
    }

    public SelectItem[] quotationNumberList() {

        return JsfUtil.getSelectItems(contractInformationBeanLocal.quotationNumberList(), true);
    }

    public SelectItem[] getListOfVendorName() {

        return JsfUtil.getSelectItems(contractInformationBeanLocal.VendorName(), true);
    }

    public SelectItem[] getAwardLists() {

        return JsfUtil.getSelectItems(purchaseOrderBeanLocal.getAwardLists(), true);
    }

    public SelectItem[] bidReferenceNoFromBidSale() {

        return JsfUtil.getSelectItems(
                contractInformationBeanLocal.BidNoForCheck(), true);
    }

    public SelectItem[] getPrList() {
        return JsfUtil.getSelectItems(purchaseReqBeanLocal.getPurchReqNo(), true);
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

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setSaveStatus(int saveStatus) {
        this.saveStatus = saveStatus;
    }

    public boolean isChooserenderForForInternational() {
        return chooserenderForInternational;
    }

    public void setChooserenderForInternational(boolean chooserenderForInternational) {
        this.chooserenderForInternational = chooserenderForInternational;
    }

    public boolean isChooserenderForLocal() {
        return chooserenderForLocal;
    }

    public void setChooserenderForLocal(boolean chooserenderForLocal) {
        this.chooserenderForLocal = chooserenderForLocal;
    }

    public boolean isChooseDisableForLocal() {
        return chooseDisableForLocal;
    }

    public void setChooseDisableForLocal(boolean chooseDisableForLocal) {
        this.chooseDisableForLocal = chooseDisableForLocal;
    }

    public boolean isChooserenderForDataTableItem() {
        return chooserenderForDataTableItem;
    }

    public void setChooserenderForDataTableItem(boolean chooserenderForDataTableItem) {
        this.chooserenderForDataTableItem = chooserenderForDataTableItem;
    }

    public boolean isChooserenderForDataTableService() {
        return chooserenderForDataTableService;
    }

    public void setChooserenderForDataTableService(boolean chooserenderForDataTableService) {
        this.chooserenderForDataTableService = chooserenderForDataTableService;
    }

    public String getChooserenderForcol1() {
        return chooserenderForcol1;
    }

    public void setChooserenderForcol1(String chooserenderForcol1) {
        this.chooserenderForcol1 = chooserenderForcol1;
    }

    public String getChooserenderForSupplier() {
        return chooserenderForSupplier;
    }

    public void setChooserenderForSupplier(String chooserenderForSupplier) {
        this.chooserenderForSupplier = chooserenderForSupplier;
    }

    public void setCheckAsBid(String checkAsBid) {
        this.checkAsBid = checkAsBid;
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

    public List<PrmsBid> getListOfBids() {
        if (listOfBids == null) {
            listOfBids = new ArrayList<>();
        }
        return listOfBids;
    }

    public void setListOfBids(List<PrmsBid> listOfBids) {
        this.listOfBids = listOfBids;
    }

    public List<PrmsQuotation> getListOfQuotation() {

        if (listOfQuotation == null) {
            listOfQuotation = new ArrayList<>();
            listOfQuotation = contractInformationBeanLocal.getListProforma();
        }
        return listOfQuotation;
    }

    public void setListOfQuotation(List<PrmsQuotation> listOfQuotation) {
        this.listOfQuotation = listOfQuotation;
    }

    public List<PmsCreateProjects> getListOfProjectses() {

        if (listOfProjectses == null) {
            listOfProjectses = new ArrayList<>();
            listOfProjectses = contractInformationBeanLocal.getListProjects();
        }
        return listOfProjectses;
    }

    public void setListOfProjectses(List<PmsCreateProjects> listOfProjectses) {
        this.listOfProjectses = listOfProjectses;
    }

    public List<PrmsContractDetail> getContractDetailList() {

        if (contractDetailList == null) {
            contractDetailList = new ArrayList<>();
        }
        return contractDetailList;
    }

    public void setContractDetailList(
            List<PrmsContractDetail> contractDetailList) {
        this.contractDetailList = contractDetailList;
    }

    public String getCommencementDatePlus_() {
        return contSigningDatePlus_;
    }

    public void setCommencementDatePlus_(String contSigningDatePlus_) {
        this.contSigningDatePlus_ = contSigningDatePlus_;
    }

    public Date getCommencementDatePlus() {
        return contSigningDatePlus;
    }

    public void setCommencementDatePlus(Date contSigningDatePlus) {
        this.contSigningDatePlus = contSigningDatePlus;
    }

    public boolean isDisableBefrDay() {
        return disableBefrDay;
    }

    public void setDisableBefrDay(boolean disableBefrDay) {
        this.disableBefrDay = disableBefrDay;
    }

    public String getLogerName() {
        return logerName;
    }

    public void setLogerName(String logerName) {
        this.logerName = logerName;
    }

    public List<PrmsContract> getFilteredItems() {
        return filteredItems;
    }

    public void setFilteredItems(List<PrmsContract> filteredItems) {
        this.filteredItems = filteredItems;
    }

    public String getCheckAsBid() {
        return checkAsBid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<FmsLuCurrency> getListOfCur() {

        if (listOfCur == null) {
            listOfCur = new ArrayList<>();
            listOfCur = fmsLuCurrencyFacade.findAll();
        }
        return listOfCur;
    }

    public void setListOfCur(List<FmsLuCurrency> listOfCur) {
        this.listOfCur = listOfCur;
    }

    public ContractInformationController() {
    }

    public List<PrmsContract> getPrmsContracts() {

        if (prmsContracts == null) {
            prmsContracts = new ArrayList<>();
        }
        return prmsContracts;
    }

    public void setPrmsContracts(List<PrmsContract> prmsContracts) {
        this.prmsContracts = prmsContracts;
    }

    public List<PrmsSupplyProfile> getPrmsSupplierList() {
        return prmsSupplierList;
    }

    public void setPrmsSupplierList(List<PrmsSupplyProfile> prmsSupplierList) {
        this.prmsSupplierList = prmsSupplierList;
    }

    public List<PrmsAward> getPrmsAwardsList() {

        if (prmsAwardsList == null) {
            prmsAwardsList = new ArrayList<>();
        }
        return prmsAwardsList;
    }

    public void setPrmsAwardsList(List<PrmsAward> prmsAwardsList) {
        this.prmsAwardsList = prmsAwardsList;
    }

    public List<PrmsPortEntry> getPrmsPortList() {

        if (prmsPortList == null) {
            prmsPortList = new ArrayList<>();
            prmsPortList = insuranceNotificationToBankBeanLocal.fromPortEntry();
        }
        return prmsPortList;
    }

    public void setPrmsPortList(List<PrmsPortEntry> prmsPortList) {
        this.prmsPortList = prmsPortList;
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

    public PrmsAwardDetail getPapmsAwardDetail() {
        if (papmsAwardDetail == null) {
            papmsAwardDetail = new PrmsAwardDetail();
        }
        return papmsAwardDetail;
    }

    public void setPapmsAwardDetail(PrmsAwardDetail papmsAwardDetail) {
        this.papmsAwardDetail = papmsAwardDetail;
    }

    public List<PrmsBidAmend> getBidListFromAmendment() {
        if (bidListFromAmendment == null) {
            bidListFromAmendment = new ArrayList<>();
        }
        return bidListFromAmendment;
    }

    public void setBidListFromAmendment(List<PrmsBidAmend> bidListFromAmendment) {
        this.bidListFromAmendment = bidListFromAmendment;
    }

    public List<PrmsAwardDetail> getPrmsAwardDetails() {

        if (prmsAwardDetails == null) {
            prmsAwardDetails = new ArrayList<>();
        }
        return prmsAwardDetails;
    }

    public void setPrmsAwardDetails(List<PrmsAwardDetail> prmsAwardDetails) {
        this.prmsAwardDetails = prmsAwardDetails;
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

    public String getSearchBundle() {
        return searchBundle;
    }

    public void setSearchBundle(String searchBundle) {
        this.searchBundle = searchBundle;
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

    public PrmsContract getSelectContractInformation() {
        return selectContractInformation;
    }

    public void setSelectContractInformation(
            PrmsContract selectContractInformation) {
        this.selectContractInformation = selectContractInformation;
    }

    public PrmsContractCurrencyDetail getSelectContractCurrInformation() {
//        if (selectContractCurrInformation == null) {
//            selectContractCurrInformation = new PrmsContractCurrencyDetail();
//        }
        return selectContractCurrInformation;
    }

    public void setSelectContractCurrInformation(PrmsContractCurrencyDetail selectContractCurrInformation) {
        this.selectContractCurrInformation = selectContractCurrInformation;
    }

    public List<PrmsContractDetail> getPrmsContractDetails() {
        if (prmsContractDetails == null) {
            prmsContractDetails = new VirtualFlow.ArrayLinkedList<>();
        }
        return prmsContractDetails;
    }

    public void setPrmsContractDetails(
            List<PrmsContractDetail> prmsContractDetails) {
        this.prmsContractDetails = prmsContractDetails;
    }

    public String getSelectOptPartyName() {
        return selectOptPartyName;
    }

    public void setSelectOptPartyName(String selectOptPartyName) {
        this.selectOptPartyName = selectOptPartyName;
    }
//    public String getIcone() {
//        return icone;
//    }
//
//    public void setIcone(String icone) {
//        this.icone = icone;
//    }
//
//    public String getIcone2() {
//        return icone2;
//    }
//
//    public void setIcone2(String icone2) {
//        this.icone2 = icone2;
//    }

    public List<HrDepartments> getDepartmentsLists() {
        return departmentsLists;
    }

    public void setDepartmentsLists(List<HrDepartments> departmentsLists) {
        this.departmentsLists = departmentsLists;
    }

    public List<HrDepartments> getArrayDepLists() {
        return arrayDepLists;
    }

    public void setArrayDepLists(List<HrDepartments> arrayDepLists) {
        this.arrayDepLists = arrayDepLists;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Date Calculations">
    public void TotalPric() {

        int sizes = papmsContract.getPrmsContractDetailList().size();

        if ((sizes == 0) && (sizes > 0)) {
            for (int j = 0; j < sizes; j++) {
                long unitprice1 = papmsContract.getPrmsContractDetailList().
                        get(j).getUnitPrice();
                long quantity1 = papmsContract.getPrmsContractDetailList().
                        get(j).getQuantity();
                totalPrice = quantity1 * unitprice1;
                totalSum = totalSum + totalPrice;
                totalvat = 0.15 * totalSum;
                groundTotal = totalSum + totalvat;
            }

        } else {
            for (int i = 0; i < sizes; i++) {
                long unitprice = papmsContract.getPrmsContractDetailList().
                        get(i).getUnitPrice();
                long quantity = papmsContract.getPrmsContractDetailList().
                        get(i).getQuantity();
                totalPrice = quantity * unitprice;
                totalSum = totalSum + totalPrice;
                totalvat = 0.15 * totalSum;
                groundTotal = totalSum + totalvat;
            }
        }
    }

    /**
     * -------------------------------------------------------------------------
     *
     * -------------------------------------------------------------------------
     */
    public void calc_() {

        if (eepBidReg.getBidType().equalsIgnoreCase("international")
                && papmsContract.getContractPeriodTo() != null) {

            Date signingDate_ = this.papmsContract.getContractsigningDate();
            Calendar c = Calendar.getInstance();
            c.setTime(signingDate_);

            if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("day")) {
                c.add(Calendar.DATE, this.papmsContract.getContractPeriodTo());//same with c.add(Calendar.DAY_OF_MONTH, 1);
            } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("month")) {
                c.add(Calendar.MONTH, this.papmsContract.getContractPeriodTo());
            } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("year")) {
                c.add(Calendar.YEAR, this.papmsContract.getContractPeriodTo());
            }

            setCommencementDatePlus(c.getTime());
            papmsContract.setContractendDate(contSigningDatePlus);
            SimpleDateFormat formatter_AM = new SimpleDateFormat("yyyy-MM-dd");
            Date date_ = papmsContract.getContractendDate();
            String strinEndDateAM = formatter_AM.format(date_);
            String stringcontractDate_ET
                    = GregorianCalendarManipulation.gregorianToEthiopian(
                            strinEndDateAM);
            DateFormat to = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd");

            try {
                String strDate = to.format(from.parse(stringcontractDate_ET));
                papmsContract.setContractenddateam(strDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        if (eepBidReg.getBidType().equalsIgnoreCase("local")) {
            Date signingDate_ = this.papmsContract.getContractsigningDate();
            // convert date to calendar
            Calendar c = Calendar.getInstance();
            c.setTime(signingDate_);
            if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("day")) {
                c.add(Calendar.DATE, this.papmsContract.getContractPeriodTo());//same with c.add(Calendar.DAY_OF_MONTH, 1);
            } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("month")) {
                c.add(Calendar.MONTH, this.papmsContract.getContractPeriodTo());
            } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("year")) {
                c.add(Calendar.YEAR, this.papmsContract.getContractPeriodTo());
            }
            setCommencementDatePlus(c.getTime());
            papmsContract.setContractendDate(contSigningDatePlus);
            SimpleDateFormat formatter_AM = new SimpleDateFormat("yyyy-MM-dd");
            Date date_ = papmsContract.getContractendDate();
            String strinEndDateAM = formatter_AM.format(date_);
            String stringcontractDate_ET
                    = GregorianCalendarManipulation.gregorianToEthiopian(
                            strinEndDateAM);
            DateFormat to = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat from = new SimpleDateFormat("yyyy-MM-dd");

            try {
                String strDate = to.format(from.parse(stringcontractDate_ET));
                papmsContract.setContractenddateam(strDate);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } else if (this.papmsContract.getCommencementdateam()
                != null) {
            if (eepBidReg.getBidType().equalsIgnoreCase("local")) {
                Date signingDate_ = this.papmsContract.getContractsigningDate();
                Calendar c = Calendar.getInstance();
                c.setTime(signingDate_);
                if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("day")) {
                    c.add(Calendar.DATE, this.papmsContract.getContractPeriodTo());//same with c.add(Calendar.DAY_OF_MONTH, 1);
                } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("month")) {
                    c.add(Calendar.MONTH, this.papmsContract.getContractPeriodTo());
                } else if (this.papmsContract.getContractperiodtype().equalsIgnoreCase("year")) {
                    c.add(Calendar.YEAR, this.papmsContract.getContractPeriodTo());
                }
                setCommencementDatePlus(c.getTime());
                papmsContract.setContractendDate(contSigningDatePlus);
                SimpleDateFormat formatter_AM = new SimpleDateFormat("yyyy-MM-dd");//03/09/2010
                Date date_ = papmsContract.getContractendDate();
                String strinEndDateAM = formatter_AM.format(date_);
                String stringcontractDate_ET
                        = GregorianCalendarManipulation.gregorianToEthiopian(
                                strinEndDateAM);
                DateFormat to = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat from = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    String strDate = to.format(from.parse(stringcontractDate_ET));
                    papmsContract.setContractenddateam(strDate);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public Date minDate() {
        return this.papmsContract.getContractsigningDate();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Workflow">
    private List<PrmsContract> contractLis1;

    public void saveWorkFlowInformation() {
        wfPrmsProcessedBeanLocal.savewf(wfPrmsProcessed);
    }

    public WfPrmsProcessed getWfPrmsProcessed() {
        if (wfPrmsProcessed == null) {
            wfPrmsProcessed = new WfPrmsProcessed();
        }
        return wfPrmsProcessed;
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

    public void setWfPrmsProcessed(WfPrmsProcessed wfPrmsProcessed) {
        this.wfPrmsProcessed = wfPrmsProcessed;
    }

    public List<PrmsContract> getContractLis1() {
        if (contractLis1 == null) {
            contractLis1 = new ArrayList<>();
        }
        return contractLis1;
    }

    public void setContractLis1(List<PrmsContract> contractLis1) {
        this.contractLis1 = contractLis1;
    }

    public int getRequestNotificationCounter() {
        contractLis1 = contractInformationBeanLocal.ContractList();
        requestNotificationCounter = contractLis1.size();
        return requestNotificationCounter;
    }

    public void setRequestNotificationCounter(int requestNotificationCounter) {
        this.requestNotificationCounter = requestNotificationCounter;
    }

    public void RequestListChange(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            papmsContract = (PrmsContract) event.getNewValue();
            populateWorkFlow();
        }
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

    public void recreatworkflow() {
        wfPrmsProcessedDModel = new ListDataModel(new ArrayList(papmsContract.getPrmsWorkFlowProccedList()));
    }

    public void handleDecision(ValueChangeEvent eve) {
        if (eve.getNewValue() != null) {
            selectedValue = eve.getNewValue().toString();
        }
    }

    public void populateWorkFlow() {

        eepBidReg = papmsContract.getBidId();

        if (papmsContract.getProjectId() != null) {
            chooseProjectreader = true;
            eepProject = papmsContract.getBidId().getProjectId();
        } else {
            chooseProjectreader = false;
        }
        // fmsLuCurrency = papmsContract.getCurrency();
        prmsAwardsList = contractInformationBeanLocal.getsupplierlist(eepBidReg);
        papmsAward = papmsContract.getAwardId();
        renderPnlCreateParty = true;
        renderPnlManPage = false;
        renderpnlToSearchPage = true;
        saveorUpdateBundle = "Update";
        updateStatus = 1;

        if (eepBidReg.getBidType().equals("international")) {
            chooseselecttOption = true;
        } else if (eepBidReg.getBidType().equals("local")) {
            chooseselecttOption = false;
        }
        for (int i = 0; i < papmsContract.getPrmsContractDetailList().size(); i++) {
            papmsContract.getPrmsContractDetailList().get(i).setTotals(
                    papmsContract.getPrmsContractDetailList().get(i).
                    getUnitPrice().doubleValue()
                    * papmsContract.getPrmsContractDetailList().get(i).
                    getQuantity().doubleValue());
            totalSum = totalSum + papmsContract.getPrmsContractDetailList().
                    get(i).getTotals();
            totalvat = 0.15 * totalSum;
            groundTotal = totalvat + totalSum;
        }
        recreateContractDtlModel();
        recreatePrmsContractModel();
        recreatworkflow();
        recreateContractDetailModel();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Search ">
    public void searchContratNo(SelectEvent event) {

        String ContractNo = event.getObject().toString();
        papmsContract.setContractNo(ContractNo);
        papmsContract = contractInformationBeanLocal.getContractNo(papmsContract);
        saveStatus = 1;
        saveorUpdateBundle = "Update";
        int sizes = papmsContract.getPrmsContractDetailList().size();

        for (int i = 0; i < sizes; i++) {

            long unitprice = papmsContract.getPrmsContractDetailList().get(i).getUnitPrice();
            long quantity = papmsContract.getPrmsContractDetailList().get(i).getQuantity();
            totalPrice = quantity * unitprice;
            totalSum = totalSum + totalPrice;
            totalvat = 0.15 * totalSum;
            groundTotal = totalSum + totalvat;
        }

        recreateContractDtlModel();
    }

    public void searchIcon() {
        clearContractPage();
        if (searchBundle.equals("Search")) {
            renderPnlCreateParty = false;
            renderPnlManPage = true;
            searchBundle = "Search";
            // setIcone2("ui-icon-search");
        }
    }

    /**
     * *************************************************************************
     *
     *
     *************************************************************************
     */
    public void searchContractInfo() {

        papmsContract.setPreparedBy(String.valueOf(workFlow.getUserAccount()));
        prmsContracts = contractInformationBeanLocal.searchContract(papmsContract);
        // FmsLuCurrencyList = papmsContract.getPrmsContractCurrencyDetailList();

        for (int i = 0; i < papmsContract.getPrmsContractDetailList().size(); i++) {
            papmsContract.getPrmsContractDetailList().get(i).setTotals(
                    papmsContract.getPrmsContractDetailList().get(i).getUnitPrice().doubleValue()
                    * papmsContract.getPrmsContractDetailList().get(i).getQuantity().doubleValue());
            totalSum = totalSum + papmsContract.getPrmsContractDetailList().get(i).getTotals();
        }

        for (int i = 0; i < papmsContract.getPrmsContractCurrencyDetailList().size(); i++) {
            FmsLuCurrency tempfmsLuCurrency = papmsContract.getPrmsContractCurrencyDetailList().get(i).getCurrencyId();
            savedCurrencyIds.add(tempfmsLuCurrency);
        }

        totalvat = 0.15 * totalSum;
        groundTotal = totalvat + totalSum;
        recreatePrmsContractModel();
        recreateContractCurrDetailModel();
        papmsContract=new PrmsContract();
    }

    public void recreatePrmsContractModel() {
        PrmsContractModel = null;
        PrmsContractModel = new ListDataModel(new ArrayList<>(getPrmsContracts()));
    }

    public ArrayList<PrmsContract> searchContractNo(String ContractNo) {

        ArrayList<PrmsContract> Contract = null;
        papmsContract.setContractNo(ContractNo);
        Contract = contractInformationBeanLocal.searchContractByContractNo(
                papmsContract);

        return Contract;
    }

    public void rowSelect(SelectEvent event) {

        papmsContract = (PrmsContract) event.getObject();// fmsLuCurrency = papmsContractCurrDetail.getCurrencyId();
        recreateContractCurrDetailModel();// recreateDmsDataModel();
        populateWorkFlow();

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Recreate Data Tables">
    public String addContractDetialInfo() {
        papmsContract.addCOntractDetialInfo(papmsContractDetail);
        recreateContractDtlModel();
        clearPopUp();

        return null;
    }

    public void recreateContractDtlModel() {

        totalSum = 0.0;
        for (int i = 0; i < papmsContract.getPrmsContractDetailList().size(); i++) {
            papmsContract.getPrmsContractDetailList().get(i).setTotals(
                    papmsContract.getPrmsContractDetailList().get(i).getUnitPrice().doubleValue()
                    * papmsContract.getPrmsContractDetailList().get(i).getQuantity().doubleValue());
            totalSum = totalSum + papmsContract.getPrmsContractDetailList().get(i).getTotals();
        }
        totalvat = 0.15 * totalSum;
        groundTotal = totalvat + totalSum;
        PrmsContractDetailModel = null;
        PrmsContractDetailModel = new ListDataModel(papmsContract.getPrmsContractDetailList());
    }

    public void recreateContractDetailModel() {
        PrmsContractDetailModel = null;
        PrmsContractDetailModel = new ListDataModel(new ArrayList<>(papmsContract.getPrmsContractDetailList()));
    }

    public void recreateContractCurrDetailModel() {
        prmsContractCurrencyModel = null;
        prmsContractCurrencyModel = new ListDataModel(new ArrayList<>(papmsContract.getPrmsContractCurrencyDetailList()));
    }

    // </editor-fold>

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            papmsContract.setColumnName(event.getNewValue().toString());
            papmsContract.setColumnValue(null);
        }
    }

    public List<PrmsContract> getContractSearchParameterLst() {
        if (contractSearchParameterLst == null) {
            contractSearchParameterLst = new ArrayList<>();
            contractSearchParameterLst = contractInformationBeanLocal.getParamNameList();
        }
        return contractSearchParameterLst;
    }

    public void setContractSearchParameterLst(List<PrmsContract> contractSearchParameterLst) {
        this.contractSearchParameterLst = contractSearchParameterLst;
    }

    int noOfCurrency = 0;

    public boolean checkIfCurrency_Exist(String CurrId) {

        PrmsContractCurrencyDetail curDetail = new PrmsContractCurrencyDetail();

        for (int i = 0; i <= prmsContractCurrencyModel.getRowCount(); i++) {

            curDetail = prmsContractCurrencyModel.getRowData();
            String temp = curDetail.getCurrencyId().getCurrencyId();

            if (temp.equalsIgnoreCase(CurrId)) {
                return true;
            }
        }

        return false;
    }

    public void buttonAction() {

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

        PrmsContractCurrencyDetail car2Add = new PrmsContractCurrencyDetail();//papmsContractCurrDetail
        car2Add.setAmount(0);
        papmsContract.getPrmsContractCurrencyDetailList().add(car2Add);
        recreateContractCurrDetailModel();
    }

    public void buttonConfirm() {

        papmsContractCurrDetail.setCurrencyId(fmsLuCurrency);
        savedCurrencyIds.add(fmsLuCurrency);
        checkEvaluation.add(fmsLuCurrency);
        papmsContract.addCOntractCurrInfo(papmsContractCurrDetail);

        recreateContractCurrDetailModel();
    }

    public void onCurrencyChange(ValueChangeEvent event) {

        if (null != event.getNewValue()) {
            fmsLuCurrency = (FmsLuCurrency) event.getNewValue();
        }
    }

    public void changeEventCurrencyID(ValueChangeEvent event) {

        if (event.getNewValue() != null) {
            fmsLuCurrency = (FmsLuCurrency) event.getNewValue();
            papmsContractCurrDetail.setCurrencyId(fmsLuCurrency);

            for (int i = 0; i <= prmsContractCurrencyModel.getRowCount(); i++) {

                String temp = papmsContractCurrDetail.getCurrencyId().getCurrencyId();
                if (temp.equalsIgnoreCase(event.getNewValue().toString())) {
                    break;
                }

                papmsContract.addCOntractCurrInfo(papmsContractCurrDetail);
            }
        }
    }

    Set<FmsLuCurrency> checkEvaluation = new HashSet<>();

    /**
     * ************************************************************************
     *
     * @param name
     * @param currencyLst
     * @return
     * ************************************************************************
     */
    public boolean findUsingIterator(
            FmsLuCurrency name, List<PrmsContractCurrencyDetail> currencyLst) {

        Iterator<PrmsContractCurrencyDetail> iterator = currencyLst.iterator();

        while (iterator.hasNext()) {

            PrmsContractCurrencyDetail customer = iterator.next();
            FmsLuCurrency tempCurrency = customer.getCurrencyId();

            if (tempCurrency != null) {
                if (customer.getCurrencyId().equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean CheckValueExisted() {

        if (checkEvaluation.contains(fmsLuCurrency)) {
            return true;
        }

        return false;
    }

    boolean existedData = false;

    /**
     * -------------------------------------------------------------------------
     *
     * @param event
     * -------------------------------------------------------------------------
     */
    public void changeEventCurrencyID2(ValueChangeEvent event) {

        try {
            existedData = false;
            fmsLuCurrency = (FmsLuCurrency) event.getNewValue();// papmsContractCurrDetail = null;
            papmsContractCurrDetail = prmsContractCurrencyModel.getRowData();
            setRowIndexCurr(prmsContractCurrencyModel.getRowIndex());
            Iterator<PrmsContractCurrencyDetail> currDet = prmsContractCurrencyModel.iterator();

            boolean duplicateEntry = false;
            boolean duplicateEntrySecond = false;
            duplicateEntry = CheckValueExisted();
            checkEvaluation.clear();
            FmsLuCurrency tempCurr = new FmsLuCurrency();

            for (int i = 0; i < prmsContractCurrencyModel.getRowCount(); i++) {

                tempCurr = currDet.next().getCurrencyId();
                if (tempCurr == null && prmsContractCurrencyModel.getRowCount() == 1) {
                    existedData = true;
                    checkEvaluation.add(fmsLuCurrency);
                } else {
                    checkEvaluation.add(tempCurr);
                }
            }

            duplicateEntrySecond = CheckValueExisted();// Check Duplication after regenerating Sets

            if (!duplicateEntrySecond) {
                savedCurrencyIds.add(fmsLuCurrency);// papmsContract.addCOntractCurrInfo(papmsContractCurrDetail);
                checkEvaluation.add(fmsLuCurrency);
                papmsContractCurrDetail.setContractId(papmsContract);
                papmsContractCurrDetail.setCurrencyId(fmsLuCurrency);//set Up for Currency Detail Information
                papmsContract.getPrmsContractCurrencyDetailList().set(getRowIndexCurr(), papmsContractCurrDetail);

                recreateContractCurrDetailModel();//RECREATED Currency ID 
            } else {
                if (duplicateEntry == false && existedData == true) {
                    savedCurrencyIds.add(fmsLuCurrency);// papmsContract.addCOntractCurrInfo(papmsContractCurrDetail);
                    checkEvaluation.add(fmsLuCurrency);
                    papmsContractCurrDetail.setContractId(papmsContract);
                    papmsContractCurrDetail.setCurrencyId(fmsLuCurrency);//set Up for Currency Detail Information
                    papmsContract.getPrmsContractCurrencyDetailList().set(getRowIndexCurr(), papmsContractCurrDetail);

                    recreateContractCurrDetailModel();//RECREATED Currency ID 
                } else {
                    JsfUtil.addFatalMessage("Duplicate Entry ! Please Select unique Currency.");
                    fmsLuCurrency = null;
                    papmsContractCurrDetail.setContractId(papmsContract);
                    papmsContractCurrDetail.setCurrencyId(fmsLuCurrency);//set Up for Currency Detail Information
                    papmsContract.getPrmsContractCurrencyDetailList().set(getRowIndexCurr(), papmsContractCurrDetail);

                    recreateContractCurrDetailModel();//RECREATED Currency ID        
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buttonActionDelete() {

        papmsContractCurrDetail = null;
        setRowIndexCurr(prmsContractCurrencyModel.getRowIndex());
        papmsContract.getPrmsContractCurrencyDetailList().remove(getRowIndexCurr());
        recreateContractCurrDetailModel();
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void amountVlc(ValueChangeEvent event) {

        try {
            String tempo = event.getNewValue().toString();
            papmsContractCurrDetail = prmsContractCurrencyModel.getRowData();
            papmsContractCurrDetail.setAmount(Double.parseDouble(tempo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="DMS service ">
    StreamedContent file;
    List<DocumentModel> newDoclist = new ArrayList<DocumentModel>();
    List<Integer> savedDocIds = new ArrayList<>();
    List<FmsLuCurrency> savedCurrencyIds = new ArrayList<>();

    DmsHandler dmsHandler = new DmsHandler();
    DocumentModel documentModel = new DocumentModel();
    DataModel<DocumentModel> docValueModel;

    public DocumentModel getDocumentModel() {
        return documentModel;
    }

    public void setDocumentModel(DocumentModel documentModel) {
        this.documentModel = documentModel;
    }

    public DataModel<DocumentModel> getDocValueModel() {
        return docValueModel;
    }

    public void setDocValueModel(DataModel<DocumentModel> docValueModel) {
        this.docValueModel = docValueModel;
    }

    public void onRowSelect(SelectEvent event) {
        setIsRowFileSelected(true);
        System.out.println("file row is select");
        documentModel = (DocumentModel) event.getObject();
        System.out.println("The Selected File name " + documentModel.getName() + " and Format " + documentModel.getDocFormat());
    }

    /**
     * *************************************************************************
     *
     * @param event
     * ************************************************************************
     */
    public void onRowSelectCurr(SelectEvent event) {

        PrmsContractCurrencyDetail comContPerson
                = (PrmsContractCurrencyDetail) event.getObject();//PrmsContractCurrencyModel = (DataModel) event.getObject();
        setRowIndexCurr(prmsContractCurrencyModel.getRowIndex());//PrmsContractCurrencyModel     

        if (getRowIndexCurr() == -1) {
            setRowIndexCurr(0);
        }
        papmsContract.getPrmsContractCurrencyDetailList().set(getRowIndexCurr(), comContPerson);
        recreateContractCurrDetailModel();
    }

    public void onRowUnselect(SelectEvent event) {
        prmsContractCurrencyModel = null;
    }

    public void recreateDmsDataModel() {
        newDoclist.clear();
        DmsHandler dmsHandler = new DmsHandler();
        DocList docList = new DocList();
        List<String> docId = new ArrayList<>();
        for (int i = 0; i < papmsContract.getPrmsContractFileUploadList().size(); i++) {
            docId.add(papmsContract.getPrmsContractFileUploadList().get(i).getDocumentId().toString());
        }
        docList.setDocIds(docId);
        DocList docListNew = dmsHandler.getDocumentsById(docList);
        if (docListNew != null) {
            newDoclist = docListNew.getDocList();
            docValueModel = new ListDataModel(docListNew.getDocList());
        }
    }

    public void uploadListener(FileUploadEvent choiced) {
        InputStream fileByteFile_ = null;
        docFormat = choiced.getFile().getFileName().split("\\.")[1];
        fileName = choiced.getFile().getFileName().split("\\.")[0];
//        String fileNameWzExtent = choiced.getFile().getFileName();
//        String categoryBundle = "et/gov/eep/commonApplications/securityServer";
        try {
            fileByteFile_ = choiced.getFile().getInputstream();
        } catch (IOException e) {
            System.out.println("Upload Error[from Lisener]==>" + e.getMessage());
        }
        byteData = dataUpload.extractByteArray(fileByteFile_);
//        uploadId = dataUpload.uploadListener(fileByteFile_, docFormat, fileName, fileNameWzExtent, categoryBundle);
        System.out.println("Byte Array is===>" + byteData);
        if (byteData != null) {
            System.out.println("Uploaded Successfully");
            JsfUtil.addSuccessMessage("U uploaded " + fileName + " with Format " + docFormat + " Successfully");
        }
    }

    public void remove(DocumentModel document) {

        document = documentModel;
        DmsHandler dmsHandler = new DmsHandler();
        dmsHandler.getRemove(document);

        recreateDmsDataModel();
    }

    public DataUpload getPapmsDataUpload() {

        if (papmsDataUpload == null) {
            papmsDataUpload = new DataUpload();
        }
        return papmsDataUpload;
    }

    public void setPapmsDataUpload(DataUpload papmsDataUpload) {
        this.papmsDataUpload = papmsDataUpload;
    }
    // </editor-fold>
}
