/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Bond;

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
import et.gov.eep.fcms.businessLogic.Bond.FmsBondCouponInterestRepaymentBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.FmsBondCouponScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.FmsBondSingleCouponBeanLocal;
import et.gov.eep.fcms.entity.Bond.FmsBondCoupon;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponExtend;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponInterestPaymt;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponSchedule;
import et.gov.eep.fcms.entity.Bond.FmsBondSingleCoupon;

/**
 *
 * @author mz
 */
@Named(value = "bondCouponScheduleController")
@ViewScoped
public class BondCouponScheduleController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
//Inject entity
    @Inject
    SessionBean sessionBean;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    FmsBondCoupon BondCoupon;
    @Inject
    FmsBondCouponExtend couponExtend;
    @Inject
    FmsBondSingleCoupon singleCoupon;
    @Inject
    FmsBondCouponInterestPaymt couponInterestRePayment;
    @Inject
    FmsBondCouponSchedule couponSchedule;
//Inject EJB for using bussiness logic 
    @EJB
    FmsBondSingleCouponBeanLocal singleCouponBeanLocal;
    @EJB
    FmsBondCouponInterestRepaymentBeanLocal couponInterestRePaymentBeanLocal;
    @EJB
    FmsBondCouponBeanLocal couponBeanLocal;
    @EJB
    FmsBondCouponExtendBeanLocal couponExtendBeanLocal;
    @EJB
    FmsBondCouponScheduleBeanLocal couponScheduleBean;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    DataModel<FmsBondCoupon> BondCouponAddDatamodel;
    DataModel<FmsBondCouponExtend> CouponExtendAddDatamodel;
    DataModel<FmsBondCouponSchedule> CouponScheduleAddDatamodel;
    DataModel<FmsBondCouponInterestPaymt> couponInterestRePaymentAddDatamodel;
    List<FmsBondCouponSchedule> unPiadCouponLists;
    private List<FmsBondCoupon> BondCouponList;
    private List<FmsBondCouponExtend> CouponExtendList;
    private List<FmsBondCouponSchedule> CouponScheduleList;
    private List<FmsBondCouponSchedule> CouponInterestList;
    private List<FmsBondCouponInterestPaymt> CouponInterestPaymtList;
    private List<FmsBondCouponSchedule> allCouponScheduleAllList;
    private FmsBondCoupon BondCouponselect;
    private FmsBondCouponSchedule CouponScheduleselect;
    private FmsBondSingleCoupon singleCouponSelect;
    private FmsBondCouponInterestPaymt couponInterestSelect;
    int updateStatus = 1;
    int paidStatus;
    int extendedStatus;
    int interestPopulate = 0;
    Double numberOfDays = 0.0;
    Date fromStartDate;
    Date toEndDate;
    Date interestPaymentStartDate;
    Date interestPaymentEndDate;
    double remainPrincipal = 0.0;
    double principalToBePaid = 0.0;
    double interestToBePaid = 0.0;
    double noOfInstallment;
    double totalPrincipalToBePaid = 0.0;
    double interestRate = 0.0;
    double totalPaidInterest = 0.0;
    double totalPaidPrincipal = 0.0;
    double totalRemainInterest = 0.0;
    double totalRemainPrincipal = 0.0;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlMainPage = true;
    private boolean isAllPaid = false;
    private String createOrSearchBundle = "Search";
    private String icone = " ";
    String saveUpdate = "Save";
    String BondNumber;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>

    public BondCouponScheduleController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(2);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
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

    public FmsBondCouponInterestPaymt getCouponInterestRePayment() {
        if (couponInterestRePayment == null) {
            couponInterestRePayment = new FmsBondCouponInterestPaymt();
        }
        return couponInterestRePayment;
    }

    public void setCouponInterestRePayment(FmsBondCouponInterestPaymt couponInterestRePayment) {
        this.couponInterestRePayment = couponInterestRePayment;
    }

    public FmsBondCouponSchedule getCouponSchedule() {
        if (couponSchedule == null) {
            couponSchedule = new FmsBondCouponSchedule();
        }
        return couponSchedule;
    }

    public void setCouponSchedule(FmsBondCouponSchedule couponSchedule) {
        this.couponSchedule = couponSchedule;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public FmsBondCoupon getBondCouponselect() {
        return BondCouponselect;
    }

    public void setBondCouponselect(FmsBondCoupon BondCouponselect) {
        this.BondCouponselect = BondCouponselect;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }

    public int getExtendedStatus() {
        return extendedStatus;
    }

    public void setExtendedStatus(int extendedStatus) {
        this.extendedStatus = extendedStatus;
    }

    public int getInterestPopulate() {
        return interestPopulate;
    }

    public void setInterestPopulate(int interestPopulate) {
        this.interestPopulate = interestPopulate;
    }

    public Double getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Double numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Date getFromStartDate() {
        return fromStartDate;
    }

    public void setFromStartDate(Date fromStartDate) {
        this.fromStartDate = fromStartDate;
    }

    public Date getToEndDate() {
        return toEndDate;
    }

    public void setToEndDate(Date toEndDate) {
        this.toEndDate = toEndDate;
    }

    public Date getInterestPaymentStartDate() {
        return interestPaymentStartDate;
    }

    public void setInterestPaymentStartDate(Date interestPaymentStartDate) {
        this.interestPaymentStartDate = interestPaymentStartDate;
    }

    public Date getInterestPaymentEndDate() {
        return interestPaymentEndDate;
    }

    public void setInterestPaymentEndDate(Date interestPaymentEndDate) {
        this.interestPaymentEndDate = interestPaymentEndDate;
    }

    public double getRemainPrincipal() {
        return remainPrincipal;
    }

    public void setRemainPrincipal(double remainPrincipal) {
        this.remainPrincipal = remainPrincipal;
    }

    public double getPrincipalToBePaid() {
        return principalToBePaid;
    }

    public void setPrincipalToBePaid(double principalToBePaid) {
        this.principalToBePaid = principalToBePaid;
    }

    public double getInterestToBePaid() {
        return interestToBePaid;
    }

    public void setInterestToBePaid(double interestToBePaid) {
        this.interestToBePaid = interestToBePaid;
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

    public boolean isIsAllPaid() {
        return isAllPaid;
    }

    public void setIsAllPaid(boolean isAllPaid) {
        this.isAllPaid = isAllPaid;
    }

    public String getSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public String getBondNumber() {
        return BondNumber;
    }

    public void setBondNumber(String BondNumber) {
        this.BondNumber = BondNumber;
    }

    public FmsBondCouponSchedule getCouponScheduleselect() {
        return CouponScheduleselect;
    }

    public void setCouponScheduleselect(FmsBondCouponSchedule CouponScheduleselect) {
        this.CouponScheduleselect = CouponScheduleselect;
    }

    public FmsBondCouponInterestPaymt getCouponInterestSelect() {
        return couponInterestSelect;
    }

    public void setCouponInterestSelect(FmsBondCouponInterestPaymt couponInterestSelect) {
        this.couponInterestSelect = couponInterestSelect;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public double getTotalPrincipalToBePaid() {
        return totalPrincipalToBePaid;
    }

    public void setTotalPrincipalToBePaid(double totalPrincipalToBePaid) {
        this.totalPrincipalToBePaid = totalPrincipalToBePaid;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
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

    public double getTotalRemainInterest() {
        return totalRemainInterest;
    }

    public void setTotalRemainInterest(double totalRemainInterest) {
        this.totalRemainInterest = totalRemainInterest;
    }

    public double getTotalRemainPrincipal() {
        return totalRemainPrincipal;
    }

    public void setTotalRemainPrincipal(double totalRemainPrincipal) {
        this.totalRemainPrincipal = totalRemainPrincipal;
    }

    public DataModel<FmsBondCoupon> getBondCouponAddDatamodel() {
        return BondCouponAddDatamodel;
    }

    public void setBondCouponAddDatamodel(DataModel<FmsBondCoupon> BondCouponAddDatamodel) {
        this.BondCouponAddDatamodel = BondCouponAddDatamodel;
    }

    public DataModel<FmsBondCouponExtend> getCouponExtendAddDatamodel() {
        return CouponExtendAddDatamodel;
    }

    public void setCouponExtendAddDatamodel(DataModel<FmsBondCouponExtend> CouponExtendAddDatamodel) {
        this.CouponExtendAddDatamodel = CouponExtendAddDatamodel;
    }

    public DataModel<FmsBondCouponSchedule> getCouponScheduleAddDatamodel() {
        return CouponScheduleAddDatamodel;
    }

    public void setCouponScheduleAddDatamodel(DataModel<FmsBondCouponSchedule> CouponScheduleAddDatamodel) {
        this.CouponScheduleAddDatamodel = CouponScheduleAddDatamodel;
    }

    public DataModel<FmsBondCouponInterestPaymt> getCouponInterestRePaymentAddDatamodel() {
        if (couponInterestRePaymentAddDatamodel == null) {
            couponInterestRePaymentAddDatamodel = new ArrayDataModel<>();
        }
        return couponInterestRePaymentAddDatamodel;
    }

    public void setCouponInterestRePaymentAddDatamodel(DataModel<FmsBondCouponInterestPaymt> couponInterestRePaymentAddDatamodel) {
        this.couponInterestRePaymentAddDatamodel = couponInterestRePaymentAddDatamodel;
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

    public List<FmsBondCouponExtend> getCouponExtendList() {
        return CouponExtendList;
    }

    public void setCouponExtendList(List<FmsBondCouponExtend> CouponExtendList) {
        this.CouponExtendList = CouponExtendList;
    }

    public List<FmsBondCouponSchedule> getCouponScheduleList() {
        if (CouponScheduleList == null) {
            CouponScheduleList = new ArrayList<>();
        }
        return CouponScheduleList;
    }

    public void setCouponScheduleList(List<FmsBondCouponSchedule> CouponScheduleList) {
        this.CouponScheduleList = CouponScheduleList;
    }

    public List<FmsBondCouponSchedule> getCouponInterestList() {
        if (CouponInterestList == null) {
            CouponInterestList = new ArrayList<>();
        }
        return CouponInterestList;
    }

    public void setCouponInterestList(List<FmsBondCouponSchedule> CouponInterestList) {
        this.CouponInterestList = CouponInterestList;
    }

    public List<FmsBondCouponInterestPaymt> getCouponInterestPaymtList() {
        if (CouponInterestPaymtList == null) {
            CouponInterestPaymtList = new ArrayList<>();
        }
        return CouponInterestPaymtList;
    }

    public void setCouponInterestPaymtList(List<FmsBondCouponInterestPaymt> CouponInterestPaymtList) {
        this.CouponInterestPaymtList = CouponInterestPaymtList;
    }

    public List<FmsBondCouponSchedule> getAllCouponScheduleAllList() {
        if (allCouponScheduleAllList == null) {
            allCouponScheduleAllList = new ArrayList<>();
        }
        return allCouponScheduleAllList;
    }

    public void setAllCouponScheduleAllList(List<FmsBondCouponSchedule> allCouponScheduleAllList) {
        this.allCouponScheduleAllList = allCouponScheduleAllList;
    }

    public List<FmsBondCouponSchedule> getUnPiadCouponLists() {
        if (unPiadCouponLists == null) {
            unPiadCouponLists = new ArrayList<>();
        }
        return unPiadCouponLists;
    }

    public void setUnPiadCouponLists(List<FmsBondCouponSchedule> unPiadCouponLists) {
        this.unPiadCouponLists = unPiadCouponLists;
    }

    //</editor-fold> 
    /*recreate method for assigning coupon list value to BondCouponAddDatamodel*/
    public void recreateModelDetailPop() {
        BondCouponAddDatamodel = null;
        BondCouponAddDatamodel = new ListDataModel(BondCouponList);
    }

    /*recreat method for assigning FmsBondCouponInterestPaymtList value to couponInterestRePaymentAddDatamodel */
    public void recreateCouponInterestDataModel() {
        couponInterestRePaymentAddDatamodel = null;
        couponInterestRePaymentAddDatamodel = new ListDataModel(new ArrayList(couponSchedule.getFmsBondCouponInterestPaymtList()));
    }

    /*select event for populating data of coupon interest payment to the input text*/
    public void interestPopulate(SelectEvent event) {
        couponInterestRePayment = (FmsBondCouponInterestPaymt) event.getObject();
        interestPopulate = 1;
    }

    /*search method for Bond coupon list from Bond coupon using coupon id and by default and also assigning constant value of the status*/
    public void searchBondCoupon() {
        if (BondCoupon.getBondNo() != null) {
            BondCouponList = couponBeanLocal.searchCouponId(BondCoupon);
            recreateModelDetailPop();
        } else {
            BondCouponList = couponBeanLocal.searchAll();
            recreateModelDetailPop();
        }

        if (BondCouponList != null) {
            for (int i = 0; i < BondCouponList.size(); i++) {
                if (BondCouponList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                    BondCouponList.get(i).setChangedStatus("PAID");
                } else if (BondCouponList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                    BondCouponList.get(i).setChangedStatus("NOT PAID");
                } else if (BondCouponList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.TERMINATED))) {
                    BondCouponList.get(i).setChangedStatus("TERMINATED");
                } else if (BondCouponList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PEND))) {
                    BondCouponList.get(i).setChangedStatus("PEND");
                } else if (BondCouponList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAST_DUE))) {
                    BondCouponList.get(i).setChangedStatus("PAST DUE");
                }

            }
        }
        recreateModelDetailPop();
    }

    /*method to update coupon schedule payment*/
    public void updateCouponSchedulePayment() {
        couponSchedule = getCouponScheduleAddDatamodel().getRowData();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        noOfInstallment = (BondCoupon.getBondMaturity() - BondCoupon.getGracePeriod()) * 2;
        principalToBePaid = BondCoupon.getTotalBondValue() / noOfInstallment;
        changedStatus();
    }

    /*method for coupon payment, first search couponScheduleBean for the unpaid coupon list then changing the status of the paid coupon*/
    public void couponPayment() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "couponPayment", dataset)) {
                couponSchedule.setStatus(String.valueOf(Constants.PAID));
                couponSchedule.setPaidAmount(principalToBePaid);
                couponSchedule.setRemainPrincipal(0.0);
                changedStatus();
                couponScheduleBean.Edit(couponSchedule);
                BondNumber = couponSchedule.getBondNo();
                unPiadCouponLists = couponScheduleBean.getNumberOfNotPaidStatus(BondNumber, Constants.NOT_PAID);
                saveUpdateClear();
                if (unPiadCouponLists.isEmpty()) {
                    isAllPaid = true;
                }
                if (isAllPaid == true) {
                    for (int k = 0; k < BondCoupon.getFmsBondCouponScheduleList().size(); k++) {
                        BondCouponList.add(BondCoupon.getFmsBondCouponScheduleList().get(k).getBondCouponNo());
                        for (int j = 0; j < BondCouponList.size(); j++) {
                            BondCouponList.get(j).setStatus(String.valueOf(Constants.PAID));
                        }
                        couponBeanLocal.Edit(BondCoupon);
                        JsfUtil.addSuccessMessage("Coupon payment is successfully Done");
                    }

                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
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

    /*method to extend the schedule of coupon payment*/
    public void extendCouponSchedule() {
        BondCoupon.getGracePeriod();
        BondCoupon.getBondMaturity();
        BondCoupon.getBondIssuedDate();
        BondCoupon.getPrincipalRepaymentStartDate();
        BondCoupon.getPrincipalRepaymentEndDate();
        BondCoupon.getTotalBondValue();
        couponSchedule.getPaidAmount();
        couponSchedule.getTotalPaidAmount();
        couponSchedule.getPaidInterest();

    }

    /* select event to fetch dat of the selected coupon*/
    public void getByBondCouponNo(SelectEvent event) {
        CouponScheduleAddDatamodel = null;
        CouponScheduleList = null;
        BondCoupon = (FmsBondCoupon) event.getObject();
        couponSchedule.setBondCouponNo(BondCoupon);
        couponInterestRePayment.setCouponNo(BondCoupon);
        couponInterestRePayment.setBondNo(BondCoupon.getBondNo());
        recreateModelDetailPop();
        renderPnlCreateAdditional = true;
        renderPnlMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        saveUpdate = "Save Payment";
    }

    /*method to searcch coupon schedule based on the given start and end date of repayment schedule and status which is paid */
    public void searchBondCouponSchedule() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String date1 = formatter.format(fromStartDate);
        String date2 = formatter.format(toEndDate);
        couponSchedule.setBondCouponNo(BondCoupon);
        BondCouponList = couponBeanLocal.findByStartAndEndDate(date1, date2);
        for (int k = 0; k < BondCouponList.size(); k++) {
            BondNumber = BondCouponList.get(k).getBondNo();
            CouponInterestPaymtList = couponBeanLocal.getInterestRepaymentList(BondCouponList.get(k).getBondNo());
            if (CouponInterestPaymtList.size() > 0) {
                for (int i = 0; i < CouponInterestPaymtList.size(); i++) {
                    totalPaidInterest += CouponInterestPaymtList.get(i).getPaidAmount();
                }
            } else {
                totalPaidInterest = 0.0;
            }
            couponSchedule.setBondNo(BondCouponList.get(k).getBondNo());
            BondNumber = couponSchedule.getBondNo();
            CouponScheduleList = couponScheduleBean.findPrincipalOfStartAndEndDate(date1, date2, BondNumber, Constants.PAID);

            if (CouponScheduleList.size() > 0) {
                for (int i = 0; i < CouponScheduleList.size(); i++) {
                    totalPaidPrincipal += CouponScheduleList.get(i).getPaidAmount();
                }

            } else {
                totalPaidPrincipal = 0.0;
            }

        }
        recreateModelDetailPop();
    }

    /* search method from coupon schedule by passing the start and end date of repayment and coupon schedule id*/
    public void searchStartEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String date1 = formatter.format(fromStartDate);
        String date2 = formatter.format(toEndDate);
        CouponScheduleList = couponScheduleBean.getByStartAndEndDate(date1, date2, couponSchedule);
        changedStatus();
        noOfInstallment = (BondCoupon.getBondMaturity() - BondCoupon.getGracePeriod()) * 2;
        CouponScheduleAddDatamodel = new ListDataModel(CouponScheduleList);
        CouponScheduleAddDatamodel.getRowIndex();
        if (CouponScheduleList.size() > 0) {
            for (int i = 0; i < CouponScheduleList.size(); i++) {
                if (CouponScheduleList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                    principalToBePaid = CouponScheduleList.get(i).getPaidAmount();
                    remainPrincipal = 0.0;
                    CouponScheduleList.get(i).setRemainPrincipal(remainPrincipal);
                    CouponScheduleList.get(i).setStatus(String.valueOf(Constants.PAID));
                    paidStatus = Integer.parseInt(CouponScheduleList.get(i).getStatus());

                } else if (CouponScheduleList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                    principalToBePaid = BondCoupon.getTotalBondValue() / noOfInstallment;
                    remainPrincipal = principalToBePaid;
                    CouponScheduleList.get(i).setRemainPrincipal(remainPrincipal);
                    totalPaidPrincipal += CouponScheduleList.get(i).getPaidAmount();
                }
                totalPrincipalToBePaid += principalToBePaid;
                totalRemainPrincipal += CouponScheduleList.get(i).getRemainPrincipal();
            }

        }
        if (CouponScheduleList != null) {
            for (int i = 0; i < CouponScheduleList.size(); i++) {
                couponSchedule = new FmsBondCouponSchedule();
                couponSchedule = CouponScheduleList.get(i);
            }
        }

    }
    /*constants for the status*/

    public void changedStatus() {
        for (int i = 0; i < CouponScheduleList.size(); i++) {
            if (CouponScheduleList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                CouponScheduleList.get(i).setChangeStatus("PAID");
                paidStatus = Integer.parseInt(CouponScheduleList.get(i).getStatus());
            } else if (CouponScheduleList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                CouponScheduleList.get(i).setChangeStatus("NOT PAID");
            } else if (CouponScheduleList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.TERMINATED))) {
                CouponScheduleList.get(i).setChangeStatus("TERMINATED");
            } else if (CouponScheduleList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PEND))) {
                CouponScheduleList.get(i).setChangeStatus("PEND");
            } else if (CouponScheduleList.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAST_DUE))) {
                CouponScheduleList.get(i).setChangeStatus("PAST_DUE");
            }
        }
    }

    /*extend coupon schedule method by checking eeither if the coupon is paid or terminated */
    public void extendCouponBond() {

        FmsBondCouponExtend couponExtend = new FmsBondCouponExtend();
        for (int j = 0; j < CouponScheduleList.size(); j++) {
            if (CouponScheduleList.get(j).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {

            } else if (CouponScheduleList.get(j).getStatus().equalsIgnoreCase(String.valueOf(Constants.TERMINATED))) {

            }
        }

        BondCoupon.getBondNo();
        BondCoupon.getPrincipalRepaymentStartDate();
        BondCoupon.getPrincipalRepaymentEndDate();
        BondCoupon.getExtendby();
        Calendar cal = Calendar.getInstance();
        cal.setTime(BondCoupon.getPrincipalRepaymentEndDate());
        cal.add(Calendar.YEAR, BondCoupon.getExtendby());
        Date extendDate = cal.getTime();

        couponExtend.setBondIssuedDate(BondCoupon.getBondIssuedDate());

    }

    /*method to add coupon interest repayment table*/
    public void addToPayInterest() {
        couponSchedule.getBondCouponNo();
        couponSchedule.addToFmsBondCouponInterestPaymt(couponInterestRePayment);
        recreateCouponInterestDataModel();
        saveUpdateClear();
    }

    /*value chenge event to handle payment of interest regarding issued date of the coupon*/
    public void handleInterestForPaymentDate(ValueChangeEvent event) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
        Date interestPaymentEndDate = sdf.parse(event.getNewValue().toString());
        interestPaymentStartDate = BondCoupon.getBondIssuedDate();
        String date1 = sdf.format(interestPaymentStartDate);
        String date2 = sdf.format(interestPaymentEndDate);
        BondNumber = couponInterestRePayment.getBondNo();
        CouponInterestPaymtList = couponBeanLocal.getInterestRepaymentList(BondNumber);
        if (CouponInterestPaymtList.size() > 0) {
            for (int i = 0; i < CouponInterestPaymtList.size(); i++) {
                totalPaidInterest += CouponInterestPaymtList.get(i).getPaidAmount();
            }
        }

    }

    /*select event for handling the interest payment amount by passing interest payment start and end date
     and status of coupon from coupon schedule*/
    public void handleInterestPayment(SelectEvent event) {
        interestPaymentStartDate = BondCoupon.getBondIssuedDate();
        interestPaymentEndDate = couponInterestRePayment.getPaymentDate();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String date1 = formatter.format(interestPaymentStartDate);
        String date2 = formatter.format(interestPaymentEndDate);
        couponInterestRePayment.setCouponNo(BondCoupon);
        interestRate = BondCoupon.getInterestRate();
        numberOfDays = Math.round(interestPaymentEndDate.getTime() - interestPaymentStartDate.getTime()) / (double) 86400000;
        couponSchedule.setBondCouponNo(BondCoupon);
        CouponScheduleList = couponScheduleBean.findPaidPrincipalOfStartAndEndDate(date1, date2, couponSchedule, Constants.PAID);
        if (CouponScheduleList.size() > 0) {
            for (int i = 0; i < CouponScheduleList.size(); i++) {
                totalPaidPrincipal += CouponScheduleList.get(i).getPaidAmount();

            }
            remainPrincipal = BondCoupon.getTotalBondValue() - totalPaidPrincipal;
            interestToBePaid = Math.round(numberOfDays * remainPrincipal * (interestRate / 365));

        } else {
            remainPrincipal = BondCoupon.getTotalBondValue() - totalPaidPrincipal;
            interestToBePaid = Math.round(numberOfDays * remainPrincipal * (interestRate / 365));

        }
    }

    // save method for coupon interest method
    public void saveCouponInterestPaymet() {

        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveCouponInterestPaymet", dataset)) {
                couponInterestRePayment.setCouponNo(BondCoupon);
                couponInterestRePayment.setCouponNo(couponSchedule.getBondCouponNo());
                couponInterestRePayment.setPaidAmount(interestToBePaid);
                couponInterestRePayment.setNumberOfDays(numberOfDays);
                couponInterestRePayment.setInterestRate(interestRate);
                couponInterestRePayment.setRemainPrincipal(remainPrincipal);
                couponInterestRePayment.setRemark(couponInterestRePayment.getRemark());
                couponInterestRePayment.setPaymentDocumentDate(couponInterestRePayment.getPaymentDocumentDate());
                couponInterestRePayment.setPaymentDocumentReference(couponInterestRePayment.getPaymentDocumentReference());
                couponInterestRePayment.setBondNo(BondCoupon.getBondNo());
                couponInterestRePayment.setStatus(String.valueOf(Constants.PAID));
                couponInterestRePaymentBeanLocal.Create(couponInterestRePayment);
                JsfUtil.addSuccessMessage("coupon Bond interest payment is successfully saved");
                saveUpdateClear();
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
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

    /*save update clear method for coupon schedule inputs*/
    private void saveUpdateClear() {
        remainPrincipal = 0.0;
        interestRate = 0.0;
        numberOfDays = 0.0;
        interestToBePaid = 0.0;
        principalToBePaid = 0.0;
        totalPaidInterest = 0.0;
        BondCoupon = null;
        BondCouponAddDatamodel = null;
        couponSchedule = null;
        couponInterestRePayment = null;
        couponInterestRePaymentAddDatamodel = null;
        CouponScheduleAddDatamodel = null;

    }
    /*create and search render method*/

    public void createAdditional() {
        switch (createOrSearchBundle) {
            case "Forecast":
                renderPnlCreateAdditional = true;
                renderPnlMainPage = false;
                createOrSearchBundle = "Search";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlCreateAdditional = false;
                renderPnlMainPage = true;
                createOrSearchBundle = "Forecast";
                setIcone("ui-icon-document");
                break;
        }
    }
}
