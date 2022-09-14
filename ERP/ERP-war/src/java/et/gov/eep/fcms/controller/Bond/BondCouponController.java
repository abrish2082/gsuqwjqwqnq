/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Bond;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.Constants;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.FmsBondCouponBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.FmsBondCouponExtendBeanLocal;
import et.gov.eep.fcms.entity.Bond.FmsBondCoupon;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponExtend;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponSchedule;
import et.gov.eep.fcms.entity.Bond.FmsBondSingleCoupon;
import javax.annotation.PostConstruct;

/**
 *
 * @author mz
 */
@Named(value = "bondCouponController")
@ViewScoped
public class BondCouponController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
// Inject entity
    @Inject
    SessionBean sessionBean;
    @Inject
    FmsBondCoupon BondCoupon;
    @Inject
    FmsBondCouponExtend couponExtend;
    @Inject
    FmsBondSingleCoupon singleCoupon;
    @Inject
    FmsBondCouponSchedule couponSchedule;
    @Inject
    FmsBondCouponInterestPaymt couponInterestRePayment;
    @Inject
    ColumnNameResolver columnNameResolver;
//Inject @EJB business interface 
    @EJB
    FmsBondCouponBeanLocal couponBeanLocal;
    @EJB
    FmsBondCouponExtendBeanLocal couponExtendBeanLocal;

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="variables declaration">
//declaring variables, lists, and datamodels
    DataModel<FmsBondCoupon> BondCouponAddDatamodel;
    DataModel<FmsBondSingleCoupon> singleCouponAddDatamodel;
    DataModel<FmsBondCouponSchedule> couponScheduleAddDatamodel;

    private List<FmsBondCoupon> BondCouponList;
    private List<FmsBondCouponSchedule> couponRePaymentScheduleList;
    private List<FmsBondCouponInterestPaymt> couponInterestRePaymentList;
    private FmsBondCoupon BondCouponselect;
    private FmsBondSingleCoupon singleCouponSelect;
    Integer installmentNo;
    Integer extendBy;
    int updateStatus = 1;
    int singlePopulate = 0;
    long numberOfDays;
    Double interest;
    Double interestRate;
    Double totalBondAmount;
    Double principal;
    Double paidAmount = 0.0;
    double totalPaidInterest = 0.0;
    double totalPaidPrincipal = 0.0;
    double totalRemainPrincipal = 0.0;
    String forSingleCoAddOrUpdate = "Add";
    String serialNumber;
    String BondNumber;
    String saveUpdate = "Save";
    Date extendedForStartDate = new Date();
    Date extendedForEndDate = new Date();
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlMainPage = true;
    private boolean extendDisable = false;
    private boolean terminateDisable = false;
    private NumberConverter numberConverter = new NumberConverter();
    ColumnNames columnNames = new ColumnNames();
    List<ColumnNameResolver> ColumnNamesList = new ArrayList<>();
    private List<String> BondCouponInfColumnNameList;
//</editor-fold>

    public BondCouponController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(2);
    }

    @PostConstruct
    public void initwfMmsProcessed() {
        getBondCouponInfColumnNameList();
    }

    public class ColumnNames implements Serializable {

        String Table_Col_Name;
        String Parsed_Col_name;

        //<editor-fold defaultstate="collapsed" desc="getter and setter">
        public String getTable_Col_Name() {
            return Table_Col_Name;
        }

        public void setTable_Col_Name(String Table_Col_Name) {
            this.Table_Col_Name = Table_Col_Name;
        }

        public String getParsed_Col_name() {
            return Parsed_Col_name;
        }

        public void setParsed_Col_name(String Parsed_Col_name) {
            this.Parsed_Col_name = Parsed_Col_name;
        }

//</editor-fold>
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    List<FmsBondCouponSchedule> couponRepaymentSchedules() {
        if (couponRePaymentScheduleList == null) {
            couponRePaymentScheduleList = new ArrayList<>();
        }
        return couponRePaymentScheduleList;
    }

    List<FmsBondCouponInterestPaymt> couponInterestPaymts() {
        if (couponInterestRePaymentList == null) {
            couponInterestRePaymentList = new ArrayList<>();
        }
        return couponInterestRePaymentList;
    }

    public List<ColumnNameResolver> getColumnNamesList() {
        return ColumnNamesList;
    }

    public void setColumnNamesList(List<ColumnNameResolver> ColumnNamesList) {
        this.ColumnNamesList = ColumnNamesList;
    }

    public List<FmsBondCouponInterestPaymt> getCouponInterestRePaymentList() {
        if (couponInterestRePaymentList == null) {
            couponInterestRePaymentList = new ArrayList<>();
        }
        return couponInterestRePaymentList;
    }

    public void setCouponInterestRePaymentList(List<FmsBondCouponInterestPaymt> couponInterestRePaymentList) {
        this.couponInterestRePaymentList = couponInterestRePaymentList;
    }

    public List<FmsBondCouponSchedule> getCouponRePaymentScheduleList() {
        if (couponRePaymentScheduleList == null) {
            couponRePaymentScheduleList = new ArrayList<>();
        }
        return couponRePaymentScheduleList;
    }

    public void setCouponRePaymentScheduleList(List<FmsBondCouponSchedule> couponRePaymentScheduleList) {
        this.couponRePaymentScheduleList = couponRePaymentScheduleList;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public List<FmsBondCoupon> getBondCouponList() {
        if (BondCouponList == null) {
            BondCouponList = new ArrayList<>();
        }
        return BondCouponList;
    }

    public void setBondCouponList(List<FmsBondCoupon> BondCouponList) {
        this.BondCouponList = BondCouponList;
    }

    public DataModel<FmsBondCoupon> getBondCouponAddDatamodel() {
        if (BondCouponAddDatamodel == null) {
            BondCouponAddDatamodel = new ListDataModel<>();
        }
        return BondCouponAddDatamodel;
    }

    public void setBondCouponAddDatamodel(DataModel<FmsBondCoupon> BondCouponAddDatamodel) {
        this.BondCouponAddDatamodel = BondCouponAddDatamodel;
    }

    public DataModel<FmsBondSingleCoupon> getSingleCouponAddDatamodel() {
        if (singleCouponAddDatamodel == null) {
            singleCouponAddDatamodel = new ArrayDataModel<>();
        }
        return singleCouponAddDatamodel;
    }

    public void setSingleCouponAddDatamodel(DataModel<FmsBondSingleCoupon> singleCouponAddDatamodel) {
        this.singleCouponAddDatamodel = singleCouponAddDatamodel;
    }

    public DataModel<FmsBondCouponSchedule> getCouponScheduleAddDatamodel() {
        if (couponScheduleAddDatamodel == null) {
            couponScheduleAddDatamodel = new ArrayDataModel<>();
        }
        return couponScheduleAddDatamodel;
    }

    public void setCouponScheduleAddDatamodel(DataModel<FmsBondCouponSchedule> couponScheduleAddDatamodel) {
        this.couponScheduleAddDatamodel = couponScheduleAddDatamodel;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public FmsBondCoupon getBondCoupon() {
        if (BondCoupon == null) {
            BondCoupon = new FmsBondCoupon();
        }
        return BondCoupon;
    }

    public void setBondCoupon(FmsBondCoupon BondCoupon) {
        this.BondCoupon = BondCoupon;
    }

    public FmsBondCouponExtend getCouponExtend() {
        if (couponExtend == null) {
            couponExtend = new FmsBondCouponExtend();
        }
        return couponExtend;
    }

    public void setCouponExtend(FmsBondCouponExtend couponExtend) {
        this.couponExtend = couponExtend;
    }

    public FmsBondSingleCoupon getSingleCoupon() {
        if (singleCoupon == null) {
            singleCoupon = new FmsBondSingleCoupon();
        }
        return singleCoupon;
    }

    public void setSingleCoupon(FmsBondSingleCoupon singleCoupon) {
        this.singleCoupon = singleCoupon;
    }

    public FmsBondCoupon getBondCouponselect() {
        return BondCouponselect;
    }

    public void setBondCouponselect(FmsBondCoupon BondCouponselect) {
        this.BondCouponselect = BondCouponselect;
    }

    public FmsBondSingleCoupon getSingleCouponSelect() {
        return singleCouponSelect;
    }

    public void setSingleCouponSelect(FmsBondSingleCoupon singleCouponSelect) {
        this.singleCouponSelect = singleCouponSelect;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getForSingleCoAddOrUpdate() {
        return forSingleCoAddOrUpdate;
    }

    public void setForSingleCoAddOrUpdate(String forSingleCoAddOrUpdate) {
        this.forSingleCoAddOrUpdate = forSingleCoAddOrUpdate;
    }

    public int getSinglePopulate() {
        return singlePopulate;
    }

    public void setSinglePopulate(int singlePopulate) {
        this.singlePopulate = singlePopulate;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public double getTotalPaidInterest() {
        return totalPaidInterest;
    }

    public void setTotalPaidInterest(double totalPaidInterest) {
        this.totalPaidInterest = totalPaidInterest;
    }

    public double getTotalPaidPrincipal() {
        return totalPaidPrincipal;
    }

    public void setTotalPaidPrincipal(double totalPaidPrincipal) {
        this.totalPaidPrincipal = totalPaidPrincipal;
    }

    public double getTotalRemainPrincipal() {
        return totalRemainPrincipal;
    }

    public void setTotalRemainPrincipal(double totalRemainPrincipal) {
        this.totalRemainPrincipal = totalRemainPrincipal;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBondNumber() {
        return BondNumber;
    }

    public void setBondNumber(String BondNumber) {
        this.BondNumber = BondNumber;
    }

    public Double getTotalBondAmount() {
        return totalBondAmount;
    }

    public void setTotalBondAmount(Double totalBondAmount) {
        this.totalBondAmount = totalBondAmount;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Integer getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(Integer installmentNo) {
        this.installmentNo = installmentNo;
    }

    public Integer getExtendBy() {
        return extendBy;
    }

    public void setExtendBy(Integer extendBy) {
        this.extendBy = extendBy;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public boolean isRenderPnlMainPage() {
        return renderPnlMainPage;
    }

    public void setRenderPnlMainPage(boolean renderPnlMainPage) {
        this.renderPnlMainPage = renderPnlMainPage;
    }

    public boolean isExtendDisable() {
        return extendDisable;
    }

    public void setExtendDisable(boolean extendDisable) {
        this.extendDisable = extendDisable;
    }

    public boolean isTerminateDisable() {
        return terminateDisable;
    }

    public void setTerminateDisable(boolean terminateDisable) {
        this.terminateDisable = terminateDisable;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public long getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
//</editor-fold>

    public void recreateSingleCouponDataModel() {
        singleCouponAddDatamodel = null;
        singleCouponAddDatamodel = new ListDataModel(new ArrayList(BondCoupon.getFmsBondSingleCouponList()));
    }

    public void recreateSingleCouponDModelPop() {
        singleCouponAddDatamodel = null;
        singleCouponAddDatamodel = new ListDataModel<>(BondCoupon.getFmsBondSingleCouponList());
    }
//Bond Coupon save and edit method

    public void saveBondCoupon() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveBondCoupon", dataset)) {

                if (updateStatus == 1) {
                    BondCoupon.setStatus(String.valueOf(Constants.NOT_PAID));
                    generateSchedule();
                    couponBeanLocal.Create(BondCoupon);
                    JsfUtil.addSuccessMessage("coupon Bond successfully saved");
                    saveUpdateClear();
                } else if (updateStatus == 2) {
                    couponBeanLocal.Edit(BondCoupon);
                    JsfUtil.addSuccessMessage("coupon Bond successfully updated");
                    saveUpdateClear();
                    updateStatus = 1;
                    saveUpdate = "Save";
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
    }

    public void columnNameChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            System.out.println("event.getNewValue().toString()==" + event.getNewValue().toString());
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(columnNameResolver.getCol_Name_FromTable()));
            BondCoupon.setColumnName(columnNameResolver.getCol_Name_FromTable());
            BondCoupon.setColumnValue(null);
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }
//Single coupon Bond Add detail method

    public void addToSingleCoupon() {
        serialNumber = singleCoupon.getSerialNo();

        if (BondCoupon.getFmsBondSingleCouponList().isEmpty()) {
            BondCoupon.setBondNo(singleCoupon.getSerialNo());
            BondCoupon.setTotalBondValue(singleCoupon.getBondValue());
            BondCoupon.setNumberOfBond(1);
            BondCoupon.setBondMaturity(singleCoupon.getBondMaturity());
            BondCoupon.setGracePeriod(singleCoupon.getGracePeriod());
        } else {
            BondCoupon.setBondNo(BondCoupon.getBondNo() + "-" + singleCoupon.getSerialNo());
            BondCoupon.setTotalBondValue(singleCoupon.getBondValue() + BondCoupon.getTotalBondValue());
            BondCoupon.setNumberOfBond(singleCouponAddDatamodel.getRowCount() + 1);
            BondCoupon.setBondMaturity(singleCoupon.getBondMaturity());
            BondCoupon.setGracePeriod(singleCoupon.getGracePeriod());
        }
        BondCoupon.addToFmsBondSingleCoupon(singleCoupon);
        recreateSingleCouponDataModel();

        singleCoupon = new FmsBondSingleCoupon();
    }

    /* Bond Coupon Schedule generate method by calculating number of days, interest */
    public void generateSchedule() {
        Calendar cal = Calendar.getInstance();
        BondNumber = BondCoupon.getBondNo();
        interestRate = BondCoupon.getInterestRate();
        totalBondAmount = BondCoupon.getTotalBondValue();
        cal.setTime(BondCoupon.getBondIssuedDate());
        cal.add(Calendar.MONTH, 6);
        Date InterestPaymStartDate = cal.getTime();
        BondCoupon.setInterestRepaymentStartDate(InterestPaymStartDate);
        cal.add(Calendar.YEAR, BondCoupon.getGracePeriod());
        Date PrincipalPaymentStartDate = cal.getTime();
        BondCoupon.setPrincipalRepaymentStartDate(PrincipalPaymentStartDate);
        cal.setTime(BondCoupon.getBondIssuedDate());
        cal.add(Calendar.YEAR, BondCoupon.getBondMaturity());
        Date principalPaymDueDate = cal.getTime();
        BondCoupon.setPrincipalRepaymentEndDate(principalPaymDueDate);
        numberOfDays = Math.round((InterestPaymStartDate.getTime() - BondCoupon.getBondIssuedDate().getTime()) / (double) 86400000);
        installmentNo = (BondCoupon.getBondMaturity() - BondCoupon.getGracePeriod()) * 2;
        principal = totalBondAmount / installmentNo;
        for (int i = 0; i < installmentNo; i++) {
            FmsBondCouponSchedule couponSchedule = new FmsBondCouponSchedule();
            BondCoupon.setBondNo(BondNumber);
            couponSchedule.setBondNo(BondNumber);
            if (i == 0) {
                couponSchedule.setInstallmentDueDate(PrincipalPaymentStartDate);
                couponSchedule.setPrincipalRepaymentStartDate(PrincipalPaymentStartDate);
                couponSchedule.setPrincipalRepaymentEndDate(principalPaymDueDate);
            } else {
                cal.setTime(PrincipalPaymentStartDate);
                couponSchedule.setPrincipalRepaymentStartDate(PrincipalPaymentStartDate);
                cal.add(Calendar.MONTH, 6);
                Date installmentDueDate = cal.getTime();
                couponSchedule.setInstallmentDueDate(installmentDueDate);
                couponSchedule.setPrincipalRepaymentEndDate(installmentDueDate);
                cal.setTime(installmentDueDate);
                PrincipalPaymentStartDate = cal.getTime();
            }
            interest = (numberOfDays * totalBondAmount * (interestRate / 365));
            couponSchedule.setInstallmentNo(i);
            couponSchedule.setPaidAmount(paidAmount);
            couponSchedule.setStatus(String.valueOf(Constants.NOT_PAID));
            couponSchedule.setRemainPrincipal(principal);
            couponRepaymentSchedules().add(couponSchedule);
            BondCoupon.addToFmsBondCouponSchedule(couponSchedule);
        }

    }

    /* value change event for interest and principal payment start date for Bond coupon*/
    public void changeIssueDate(ValueChangeEvent event) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
        Date issuedDate = sdf.parse(event.getNewValue().toString());
        Calendar cal = Calendar.getInstance();
        BondCoupon.setBondIssuedDate(issuedDate);
        if (BondCoupon.getBondIssuedDate() != null) {
            cal.setTime(BondCoupon.getBondIssuedDate());
            cal.add(Calendar.MONTH, 6);
            BondCoupon.setInterestRepaymentStartDate(cal.getTime());
            cal.setTime(BondCoupon.getBondIssuedDate());
            if (BondCoupon.getBondMaturity() != null) {
                cal.add(Calendar.YEAR, BondCoupon.getBondMaturity());
                BondCoupon.setPrincipalRepaymentEndDate(cal.getTime());
            }

        }

    }
    /* Coupon Bond search by coupon id and default*/

    public void searchBondCoupon() {
        System.out.println("in search");
        BondCouponList = couponBeanLocal.getBondCouponListsByParameter(columnNameResolver, BondCoupon, BondCoupon.getColumnValue());
        recreateModelDetailPop();
//        if (BondCoupon.getBondNo() != null) {
//            BondCouponList = couponBeanLocal.searchCouponId(BondCoupon);
//            recreateModelDetailPop();
//        } else {
//            BondCouponList = couponBeanLocal.searchAll();
//            recreateModelDetailPop();
//        }
    }

    public void recreateModelDetailPop() {
        BondCouponAddDatamodel = null;
        BondCouponAddDatamodel = new ListDataModel<>(BondCouponList);
    }

    public List<String> getBondCouponInfColumnNameList() {
        BondCouponInfColumnNameList = couponBeanLocal.getBondCouponInfColumnNameList();
        if (BondCouponInfColumnNameList == null) {
            BondCouponInfColumnNameList = new ArrayList<>();
        }
        System.out.println("=======mmsStoreColumnNameList==" + BondCouponInfColumnNameList);
        if (BondCouponInfColumnNameList.size() > 0) {
            ColumnNamesList = new ArrayList<>();
            for (int i = 0; i < BondCouponInfColumnNameList.size(); i++) {
                ColumnNameResolver obj = new ColumnNameResolver();
                obj.setCol_Name_FromTable((BondCouponInfColumnNameList.get(i).toLowerCase()));
                obj.setParsed_Col_Name(ColumnParser((BondCouponInfColumnNameList.get(i).toLowerCase())));
                ColumnNamesList.add(obj);
            }

        }
        return BondCouponInfColumnNameList;
    }

    public void setBondCouponInfColumnNameList(List<String> BondCouponInfColumnNameList) {
        this.BondCouponInfColumnNameList = BondCouponInfColumnNameList;
    }

    /* select event for single coupon to populate info to the input text for modification*/
    public void singlePopulate(SelectEvent event) {
        singleCoupon = (FmsBondSingleCoupon) event.getObject();
        singlePopulate = 1;
        forSingleCoAddOrUpdate = "Modify";
    }
    /*select event for fetching data using serial number of the coupon*/

    public void getBySerialNo(SelectEvent event) {
        BondCoupon = (FmsBondCoupon) event.getObject();
        recreateModelDetailPop();
        renderPnlCreateAdditional = true;
        renderPnlMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        saveUpdate = "Update";
        forSingleCoAddOrUpdate = "Add";
        updateStatus = 2;
    }
    /*select event for Bond coupon to fetch data of the selected coupon with existing value*/

    public void getByBondNo(SelectEvent event) {
        BondCoupon = (FmsBondCoupon) event.getObject();
        couponInterestRePayment.setCouponNo(BondCoupon);
        couponInterestRePayment.setBondNo(BondCoupon.getBondNo());
        recreateSingleCouponDModelPop();
        renderPnlCreateAdditional = true;
        renderPnlMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        extendDisable = true;
        terminateDisable = true;
        saveUpdate = "Update";
        updateStatus = 2;
        BondCouponList = couponBeanLocal.searchCouponId(BondCoupon);
        for (int r = 0; r < BondCouponList.size(); r++) {
            if (BondCouponList.get(r).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                extendDisable = false;
                terminateDisable = false;
            } else if (BondCouponList.get(r).getStatus().equalsIgnoreCase(String.valueOf(Constants.EXTENDED))) {
                extendDisable = false;
                terminateDisable = false;
            } else if (BondCouponList.get(r).getStatus().equalsIgnoreCase(String.valueOf(Constants.TERMINATED))) {
                extendDisable = false;
            }
        }
        couponRePaymentScheduleList = couponBeanLocal.getCouponScheduleList(BondCoupon.getBondNo());
        couponInterestRePaymentList = couponBeanLocal.getInterestRepaymentList(BondCoupon.getBondNo());
        for (int k = 0; k < couponRePaymentScheduleList.size(); k++) {
            totalPaidPrincipal += couponRePaymentScheduleList.get(k).getPaidAmount();
            totalRemainPrincipal += couponRePaymentScheduleList.get(k).getRemainPrincipal();
        }
        for (int i = 0; i < couponInterestRePaymentList.size(); i++) {
            totalPaidInterest += couponInterestRePaymentList.get(i).getPaidAmount();
        }
        couponExtend.setBondNo(BondCoupon.getBondNo());
        couponExtend.setPaidInterest(totalPaidInterest);
        couponExtend.setPaidPrincipal(totalPaidPrincipal);
        couponExtend.setRemainPrincipal(totalRemainPrincipal);
        couponExtend.setBondIssuedDate(BondCoupon.getBondIssuedDate());

    }
    /*method used to save the extending of payment for the selecte coupon*/

    public void confirmExtend() {
        if (couponExtend.getExtendBy() != null) {
            extendBy = couponExtend.getExtendBy();
            Calendar cal = Calendar.getInstance();
            for (int k = 0; k < couponRePaymentScheduleList.size(); k++) {
                if (couponRePaymentScheduleList.get(k).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                    couponExtend.setRepaymentStartDate(extendedForStartDate);
                    couponExtend.setRepaymentEndDate(extendedForEndDate);
                    couponRePaymentScheduleList.get(k).setPrincipalRepaymentStartDate(extendedForStartDate);
                    cal.setTime(extendedForStartDate);
                    cal.add(Calendar.MONTH, 6);
                    Date paymentEndDate = cal.getTime();
                    couponRePaymentScheduleList.get(k).setPrincipalRepaymentEndDate(paymentEndDate);
                    cal.setTime(paymentEndDate);
                    cal.add(Calendar.MONTH, 6);
                    extendedForStartDate = cal.getTime();
                    couponExtend.setStatus(String.valueOf(Constants.EXTENDED));
                    couponRePaymentScheduleList.get(k).setStatus(String.valueOf(Constants.EXTENDED));
                }
                couponRePaymentScheduleList.get(k).addToFmsBondCouponExtend(couponExtend);
            }

        }
        couponExtend.setCouponExtend(BondCoupon);
        BondCoupon.setStatus(String.valueOf(Constants.EXTENDED));
        couponExtendBeanLocal.Create(couponExtend);
        couponBeanLocal.Edit(BondCoupon);
        JsfUtil.addInformationMessage(couponExtend.getBondNo() + "  Bond Coupon Extended Successfully");

    }
    /*method to fetch data of the selected coupon for the extending of repayment date*/

    public void extendCouponBond() {
        BondCoupon.getPrincipalRepaymentEndDate();
        extendBy = couponExtend.getExtendBy();
        BondCoupon.getBondMaturity();
        BondCoupon.getBondNo();

    }
    /*value change event for the Bond coupon extend vary with the input date*/

    public void getExtendByCalculation(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            extendBy = Integer.parseInt(event.getNewValue().toString());
            for (int k = 0; k < couponRePaymentScheduleList.size(); k++) {

                if (couponRePaymentScheduleList.get(k).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                    Calendar cal = Calendar.getInstance();
                    Date schedulePrinRepayStartDate = couponRePaymentScheduleList.get(k).getPrincipalRepaymentStartDate();
                    Date schedulePrinRepayEndDate = couponRePaymentScheduleList.get(k).getPrincipalRepaymentEndDate();
                    cal.setTime(schedulePrinRepayStartDate);
                    cal.add(Calendar.YEAR, extendBy);
                    extendedForStartDate = cal.getTime();
                    cal.setTime(schedulePrinRepayEndDate);
                    cal.add(Calendar.YEAR, extendBy);
                    extendedForEndDate = cal.getTime();
                    break;
                }
            }
        }
    }
    /* method for saving coupon Bond termination*/

    public void terminateCouponBond() {
        for (int i = 0; i < BondCoupon.getFmsBondCouponScheduleList().size(); i++) {
            if (BondCoupon.getFmsBondCouponScheduleList().get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                BondCoupon.getFmsBondCouponScheduleList().get(i).setStatus(String.valueOf(Constants.TERMINATED));
            }
        }
        BondCoupon.setStatus(String.valueOf(Constants.TERMINATED));
        couponBeanLocal.Edit(BondCoupon);

    }
    /*clearing method for save and update*/

    private void saveUpdateClear() {
        BondCoupon = null;
        BondCouponAddDatamodel = null;
        singleCouponAddDatamodel = null;
    }
    /*create and search rendering method*/

    public void createAdditional() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            createOrSearchBundle = "Search";
            renderPnlMainPage = false;
            setIcone("ui-icon-search");
        } else {
            renderPnlCreateAdditional = false;
            createOrSearchBundle = "New";
            renderPnlMainPage = true;
            setIcone("ui-icon-plus");
        }
    }
}
