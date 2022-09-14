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
import javax.inject.Named;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
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
import et.gov.eep.commonApplications.businessLogic.ComLuCountryBeanLocal;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.BondForeignScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.FmsBondLiborBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.LuBondTypeBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.BondForeignBeanLocals;
import et.gov.eep.fcms.entity.Bond.FmsBondForeign;
import et.gov.eep.fcms.entity.Bond.FmsBondForeignSchedule;
import et.gov.eep.fcms.entity.Bond.FmsBondLibor;
import et.gov.eep.fcms.entity.Bond.FmsLuBondType;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.Bond.FmsBondForeignFacade;
import et.gov.eep.fcms.mapper.FmsLuBondTypeFacade;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;

/**
 *
 * @author mora
 */
@Named(value = "bondForeignController")
@ViewScoped
public class BondForeignController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
//Inject entities
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsBondForeign BondForeign;
    @Inject
    FmsBondLibor BondLibor;
    @Inject
    FmsBondForeignSchedule foreignSchedule;
    @Inject
    FmsLuBondType luBondType;
    @Inject
    FmsLuCurrency fmsLuCurrency;
    @Inject
    ComLuCountry luCountry;
    @Inject
    SessionBean sessionBeanLocal;

    //Inject business logics using @EJB 
    @EJB
    FmsBondForeignFacade foreignFacade;
    @EJB
    FmsBondLiborBeanLocal liborBean;
    @EJB
    BondForeignScheduleBeanLocal scheduleBean;
    @EJB
    BondForeignBeanLocals foreignBean;
    @EJB
    ComLuCountryBeanLocal countryBeanLocal;
    @EJB
    FmsLuCurrencyFacade currencyFacade;
    @EJB
    FmsLuBondTypeFacade BondTypeFacade;
    @EJB
    LuBondTypeBeanLocal BondTypeBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    Date applicationdate;
    Date paymentDate;
    Date fromIssueDate;
    Date toIssueDate;
    Date paydupto;
    Date terminationdate;
    int lastinstantance = 0;
    int updateStatus = 1;
    Integer Bondvalueinyear;
    long numberOfDays = new Long(0L);
    double interestMarigin = 0.0;
    double totalAmount;
    double total = 0.00;
    double Amount = 0.0;
    double interest = 0.0;
    Double bondamount;
    Double interestBearing;
    Double principal;
    Double bondinterest;
    Double liborrate;
    String selectBondType = "";
    String Bondcurrency;
    String saveUpdate = "Save";
    String selectedValue = "";
    String terminate = "terminate";
    String documentReferenceNumber;
    private boolean renderPnlGourpPage = false;
    private boolean panelgroupreport = false;
    private boolean disableBtnCreate;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean saveupreder = true;
    private boolean termnetdisable = false;
    private boolean paydisable = false;
    private boolean submit = false;
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private String activeIndex;
    boolean disable = false;
    DataModel<FmsBondForeign> BondForeignAddDataModel;
    DataModel<FmsBondForeign> BondForeignGroupDataModel;
    private boolean isCheckedInterestFree = false;
    private boolean isCheckedLiborPlus = false;
    private List<FmsBondForeign> BondForeigns;
    private List<FmsBondForeign> BondForeignsReportList;
    private List<FmsBondForeignSchedule> BondForeignSchedules;
    private List<ComLuCountry> comLuCountriesList;
    private FmsBondForeign BondForeignselect;
    private FmsBondForeign BondForeignselectNew;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>

    public BondForeignController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(2);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsBondForeign getBondForeign() {
        if (BondForeign == null) {
            BondForeign = new FmsBondForeign();
        }
        return BondForeign;
    }

    public void setBondForeign(FmsBondForeign BondForeign) {
        this.BondForeign = BondForeign;
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

    public FmsBondLibor getBondLibor() {
        if (BondLibor == null) {
            BondLibor = new FmsBondLibor();
        }
        return BondLibor;
    }

    public void setBondLibor(FmsBondLibor BondLibor) {
        this.BondLibor = BondLibor;
    }

    public ComLuCountry getLuCountry() {
        if (luCountry == null) {
            luCountry = new ComLuCountry();
        }
        return luCountry;
    }

    public void setLuCountry(ComLuCountry luCountry) {
        this.luCountry = luCountry;
    }

    List<FmsBondForeignSchedule> repaymentSchedules() {
        if (BondForeignSchedules == null) {
            BondForeignSchedules = new ArrayList<>();

        }
        return BondForeignSchedules;

    }

    public DataModel<FmsBondForeign> getBondForeignGroupDataModel() {
        if (BondForeignAddDataModel == null) {
            BondForeignAddDataModel = new ArrayDataModel<>();
        }
        return BondForeignGroupDataModel;
    }

    public void setBondForeignGroupDataModel(DataModel<FmsBondForeign> BondForeignGroupDataModel) {
        this.BondForeignGroupDataModel = BondForeignGroupDataModel;
    }

    public DataModel<FmsBondForeign> getBondForeignAddDataModel() {

        if (BondForeignAddDataModel == null) {
            BondForeignAddDataModel = new ArrayDataModel<>();
        }
        return BondForeignAddDataModel;
    }

    public void setBondForeignAddDataModel(DataModel<FmsBondForeign> BondForeignAddDataModel) {
        this.BondForeignAddDataModel = BondForeignAddDataModel;
    }

    public List<FmsBondForeign> getBondForeignsReportList() {
        if (BondForeignsReportList == null) {
            BondForeignsReportList = new ArrayList<>();
        }
        return BondForeignsReportList;
    }

    public void setBondForeignsReportList(List<FmsBondForeign> BondForeignsReportList) {
        this.BondForeignsReportList = BondForeignsReportList;
    }

    public List<ComLuCountry> getComLuCountriesList() {
        if (comLuCountriesList == null) {
            comLuCountriesList = new ArrayList<>();
        }
        comLuCountriesList = countryBeanLocal.findAll();
        return comLuCountriesList;
    }

    public void setComLuCountriesList(List<ComLuCountry> comLuCountriesList) {
        this.comLuCountriesList = comLuCountriesList;
    }

    public List<FmsBondForeign> getBondForeigns() {
        if (BondForeigns == null) {
            BondForeigns = new ArrayList<>();
        }
        return BondForeigns;
    }

    public void setBondForeigns(List<FmsBondForeign> BondForeigns) {
        this.BondForeigns = BondForeigns;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public Date getTermineriondate() {
        return terminationdate;
    }

    public void setTermineriondate(Date terminationdate) {
        this.terminationdate = terminationdate;
    }

    public Date getPaydupto() {
        return paydupto;
    }

    public void setPaydupto(Date paydupto) {
        this.paydupto = paydupto;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isTermnetdisable() {
        return termnetdisable;
    }

    public void setTermnetdisable(boolean termnetdisable) {
        this.termnetdisable = termnetdisable;
    }

    public boolean isPaydisable() {
        return paydisable;
    }

    public void setPaydisable(boolean paydisable) {
        this.paydisable = paydisable;
    }

    public boolean isSubmit() {
        return submit;
    }

    public void setSubmit(boolean submit) {
        this.submit = submit;
    }

    public String getTerminate() {
        return terminate;
    }

    public String getDocumentReferenceNumber() {
        return documentReferenceNumber;
    }

    public void setDocumentReferenceNumber(String documentReferenceNumber) {
        this.documentReferenceNumber = documentReferenceNumber;
    }

    public void setTerminate(String terminate) {
        this.terminate = terminate;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    public boolean isPanelgroupreport() {
        return panelgroupreport;
    }

    public void setPanelgroupreport(boolean panelgroupreport) {
        this.panelgroupreport = panelgroupreport;
    }

    public FmsBondForeign getBondForeignselect() {
        return BondForeignselect;
    }

    public void setBondForeignselect(FmsBondForeign BondForeignselect) {
        this.BondForeignselect = BondForeignselect;
    }

    public Date getapplicationdate() {
        if (applicationdate == null) {
            applicationdate = new Date();
        }

        return applicationdate;
    }

    public void setapplicationdate(Date applicationdate) {
        this.applicationdate = applicationdate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Integer getBondvalueinyear() {
        return Bondvalueinyear;
    }

    public void setBondvalueinyear(Integer Bondvalueinyear) {
        this.Bondvalueinyear = Bondvalueinyear;
    }

    public long getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(long numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Double getBondinterest() {
        return bondinterest;
    }

    public void setBondinterest(Double bondinterest) {
        this.bondinterest = bondinterest;
    }

    public Double getinterestBearing() {
        return interestBearing;
    }

    public void setinterestBearing(Double interestBearing) {
        this.interestBearing = interestBearing;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getBondamount() {
        return bondamount;
    }

    public void setBondamount(Double bondamount) {
        this.bondamount = bondamount;
    }

    public String getBondcurrency() {
        return Bondcurrency;
    }

    public void setBondcurrency(String Bondcurrency) {
        this.Bondcurrency = Bondcurrency;
    }

    public FmsBondForeign getBondForeignselectNew() {
        return BondForeignselectNew;
    }

    public void setBondForeignselectNew(FmsBondForeign BondForeignselectNew) {
        this.BondForeignselectNew = BondForeignselectNew;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
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

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlGourpPage() {
        return renderPnlGourpPage;
    }

    public void setRenderPnlGourpPage(boolean renderPnlGourpPage) {
        this.renderPnlGourpPage = renderPnlGourpPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public Date getFromIssueDate() {
        return fromIssueDate;
    }

    public void setFromIssueDate(Date fromIssueDate) {
        this.fromIssueDate = fromIssueDate;
    }

    public Date getToIssueDate() {
        return toIssueDate;
    }

    public void setToIssueDate(Date toIssueDate) {
        this.toIssueDate = toIssueDate;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public boolean isSaveupreder() {
        return saveupreder;
    }

    public void setSaveupreder(boolean saveupreder) {
        this.saveupreder = saveupreder;
    }

    public boolean isIsCheckedInterestFree() {
        return isCheckedInterestFree;
    }

    public void setIsCheckedInterestFree(boolean isCheckedInterestFree) {
        this.isCheckedInterestFree = isCheckedInterestFree;
    }

    public boolean isIsCheckedLiborPlus() {
        return isCheckedLiborPlus;
    }

    public void setIsCheckedLiborPlus(boolean isCheckedLiborPlus) {
        this.isCheckedLiborPlus = isCheckedLiborPlus;
    }

    //</editor-fold> 
    /*value change event for handling select options*/
    public void handleselectOptions(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            selectedValue = event.getNewValue().toString();
        }
    }

    /*Value change event for interest marigin value change*/
    public void InterestMarigin(ValueChangeEvent event) {
        interestMarigin = BondForeign.getInterestBearing();
    }

    /*value change event for changing vale of interest bearing depending on the checking of interest free box*/
    public void onCheckInterestFreeEvent(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            if (event.getNewValue().toString().equalsIgnoreCase("true")) {//interest free checked
                isCheckedInterestFree = true;
//                isCheckedLiborPlus = true;
                if (updateStatus == 1) {//on save
                    BondForeign.setInterestBearing(0.00);
                    interestMarigin = BondForeign.getInterestBearing();
                    BondForeign.setInterestBearing(0.00);
                    foreignSchedule.setInterest(0.00);
                } else if (updateStatus == 2) {//on update
                    isCheckedInterestFree = true;
//                    isCheckedLiborPlus = true;
                    BondForeign.setInterestBearing(0.00);
                    interestMarigin = BondForeign.getInterestBearing();
                    BondForeign.setInterestBearing(0.00);
                    foreignSchedule.setInterest(0.00);

                }
            } else {//unchecked
                isCheckedInterestFree = false;
            }
        }
        onCheckLiborPlusEvent(event);
    }

    /*value change event for checking whether the Libor plus box is checked */
    public void onCheckLiborPlusEvent(ValueChangeEvent event) {
        if (null != event.getNewValue()) {

            if (event.getNewValue().toString().equalsIgnoreCase("true")) {
                isCheckedLiborPlus = true;
            } else {
                isCheckedLiborPlus = false;
            }
        }
    }

    /*value change event for changing the value of Bond maturity depending on the given application date and Bond value in year*/
    public void changeOnMaturity(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            Bondvalueinyear = Integer.parseInt((event.getNewValue().toString()));
            BondForeign.setBondValueYear(Bondvalueinyear);
            if (applicationdate != null) {
                Bondvalueinyear = BondForeign.getBondValueYear();
                applicationdate = BondForeign.getApplicationDate();//applicationdate for rigeter day
                Calendar cal = Calendar.getInstance();
                cal.setTime(applicationdate);
                cal.add(Calendar.YEAR, Bondvalueinyear);
                Date duedate = cal.getTime();
                BondForeign.setBondMaturityDate(duedate);
            }
        }

    }

    /*value change event for chenging the maturity date of the issued Bond dependingon the given application date and Bond value in year */
    public void changeOnMaturityDate(ValueChangeEvent event) throws ParseException {
        if (event.getNewValue() != null) {
            Bondvalueinyear = BondForeign.getBondValueYear();
            SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
            applicationdate = sdf.parse(event.getNewValue().toString());
            if (BondForeign.getBondValueYear() != null) {
                BondForeign.setApplicationDate(applicationdate);
                Bondvalueinyear = BondForeign.getBondValueYear();
                applicationdate = BondForeign.getApplicationDate();//applicationdate for rigeter day
                Calendar cal = Calendar.getInstance();
                cal.setTime(applicationdate);
                cal.add(Calendar.YEAR, Bondvalueinyear);
                Date duedate = cal.getTime();
                BondForeign.setBondMaturityDate(duedate);
            }
        }

    }

    /*value change event method for handling the selection of currency*/
    public void handleSelectCurrency(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            fmsLuCurrency = (FmsLuCurrency) event.getNewValue();
            BondForeign.setFmsLuCurrency(fmsLuCurrency);
        }
    }

    /*select event method to handle payment of the foreign Bond*/
    public void handlePayment(SelectEvent event) {
        Calendar cal = Calendar.getInstance();
        paymentDate = cal.getTime();
        applicationdate = BondForeign.getApplicationDate();
        interestBearing = BondForeign.getInterestBearing();
        bondamount = BondForeign.getPrincipalamount();
        principal = BondForeign.getPrincipalamount();
        numberOfDays = Math.round((paymentDate.getTime() - applicationdate.getTime()) / (double) 86400000);
        interest = Math.round(numberOfDays * bondamount * (interestBearing / 365));
        BondForeign.setInterest(interest);
        totalAmount = interest + principal;
    }

    /*select event to fetch data of the selected Bond using name by passing the Bond id the assign the result to BondForeigns List*/
    public void getByBuyersNames(SelectEvent event) {

        BondForeign.setBuyerFullName(event.getObject().toString());
        BondForeigns = foreignBean.searchName(BondForeign);

    }

    // foreign Bond save and edit method
    public void saveBondForeign() {
        try {

            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveBondForeign", dataset)) {
                if (updateStatus == 1) {
                    interestBearing = BondForeign.getInterestBearing();//InterestBearing
                    Bondcurrency = fmsLuCurrency.getCurrencyId();
                    BondForeign.setStatus(String.valueOf(Constants.NOT_PAID));
                    BondForeign.setFmsLuCurrency(fmsLuCurrency);
                    BondForeign.setDueDate(BondForeign.getBondMaturityDate());
                    FmsBondLibor libor = new FmsBondLibor();
                    if (Bondcurrency != null) {
                        libor = liborBean.searchdays(applicationdate, Bondcurrency);
                        if (libor != null) {
                            liborrate = libor.getLiborRate();//Libor Rate

                            if (isCheckedLiborPlus) {
                                liborrate = libor.getLiborRate();
                                interestBearing = BondForeign.getInterestBearing() + liborrate;
                                BondForeign.setInterestBearing(interestBearing);
                            } else {
                                liborrate = 0.00;
                                interestBearing = BondForeign.getInterestBearing() + liborrate;
                                BondForeign.setInterestBearing(interestBearing);
                            }
                            if (BondForeign instanceof FmsBondForeign) {
                                foreignBean.Create(BondForeign);
                                JsfUtil.addSuccessMessage("Foreigns Bond successfully registered");
                                foreignSchedule.setStatus(BondForeign.getStatus());
                                BondForeign.addToFmsBondForeignScheduleDetail(foreignSchedule);
                                saveUpdateClear();
                                clean();
                            }

                        } else {
                            JsfUtil.addFatalMessage("Libor is not Registered Please Check Application Date on Bond Libor!");
                        }
                    } else {
                        JsfUtil.addFatalMessage("Please Check currency!");
                    }
                } else if (updateStatus == 2) {
                    Bondcurrency = fmsLuCurrency.getCurrencyId();
                    BondForeign.setFmsLuCurrency(fmsLuCurrency);
                    BondForeign.setDueDate(BondForeign.getBondMaturityDate());
                    FmsBondLibor libor = new FmsBondLibor();
                    if (Bondcurrency != null) {
                        libor = liborBean.searchdays(applicationdate, Bondcurrency);
                        if (libor != null) {
                            liborrate = libor.getLiborRate();//Libor Rate

                            if (isCheckedLiborPlus) {
                                liborrate = libor.getLiborRate();
                                interestBearing = BondForeign.getInterestBearing() + liborrate;
                                BondForeign.setInterestBearing(interestBearing);
                            } else {
                                liborrate = 0.00;
                                interestBearing = BondForeign.getInterestBearing() + liborrate;
                                BondForeign.setInterestBearing(interestBearing);
                            }
                            if (BondForeign instanceof FmsBondForeign) {
                                foreignBean.Create(BondForeign);
                                JsfUtil.addSuccessMessage("Foreigns Bond successfully registerd");
                                foreignSchedule.setStatus(BondForeign.getStatus());
                                BondForeign.addToFmsBondForeignScheduleDetail(foreignSchedule);
                                saveUpdateClear();
                                clean();
                            }
                            foreignBean.Eidt(BondForeign);
                            JsfUtil.addSuccessMessage("Foreigns Bond successfully updated");
                            saveUpdateClear();
                            clean();
                        } else {
                            JsfUtil.addFatalMessage("Libor is not Registered Please Check Application Date on Bond Libor!");
                        }
                    } else {
                        JsfUtil.addFatalMessage("Please Check currency!");
                    }

                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
    }

    /* method to calculate the interset of the issued Bond */
    public void calculateInterest() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        applicationdate = BondForeign.getApplicationDate();
        paymentDate = BondForeign.getPaymentDate();
        interestBearing = BondForeign.getInterestBearing();
        long diff = paymentDate.getTime() - applicationdate.getTime();
        numberOfDays = (int) (diff / 24 * 60 * 60 * 1000);//end date-start date (in integer/number)
        interest = numberOfDays * BondForeign.getBondAmount() * (interestBearing / 365);
        BondForeign.setInterest(interest);
        totalAmount = BondForeign.getPrincipalamount() + BondForeign.getInterest();
    }

    /*method to add the value of the Bond to Bond foreign list */
    public String addForeignBondDetail() {
        getBondForeigns().add(BondForeign);
        recreateModelSrnDetailPop();
        saveUpdateClear();
        return null;
    }

    /*recreate method to assign BondForeigns list value to BondForeignAddDataModel*/
    public void recreateModelSrnDetailPop() {
        BondForeignAddDataModel = null;
        BondForeignAddDataModel = new ListDataModel(getBondForeigns());
    }

    /*recreate method to assign BondForeignsReportList data to BondForeignGroupDataModel */
    public void recreateBondForeignGroupDataModel() {
        BondForeignGroupDataModel = null;
        BondForeignGroupDataModel = new ListDataModel(BondForeignsReportList);
    }

    /*method to save payment of Bond foriegn to Bond foreign table */
    public void makePaymetnt() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "makePaymetnt", dataset)) {
                BondForeign.setStatus(String.valueOf(Constants.PAID));
                foreignBean.Eidt(BondForeign);
                JsfUtil.addSuccessMessage("payment is successfully done!!!");
                BondForeign.setLastRepaymentSchedule(paymentDate);
                saveUpdateClear();
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
//..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
    }

    // search method for Bond foreign from Bond foreign table using serial number, buyers full name, principal amoung and currency 
    public void search() {
        if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeigns = foreignBean.searchBondId(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeigns = foreignBean.searchName(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeigns = foreignBean.findBySerialNo(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeigns = foreignBean.findBySerialNameAmountAndCurency(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeigns = foreignBean.findBySerialAmountAndCurency(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeigns = foreignBean.findByNameAmountAndCurency(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeigns = foreignBean.findByAmountAndCurency(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeigns = foreignBean.findByBondCurrency(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeigns = foreignBean.findByBondAmount(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() == null && BondForeign.getBuyerFullName() != null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeigns = foreignBean.findByNameAndCurrency(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() == null && fmsLuCurrency.getCurrencyId() != null) {
            BondForeigns = foreignBean.findBySerialAndCurrency(BondForeign);
            setBondForeigns(BondForeigns);
        } else if (BondForeign.getSerialNo() != null && BondForeign.getBuyerFullName() == null && BondForeign.getPrincipalamount() != null && fmsLuCurrency.getCurrencyId() == null) {
            BondForeigns = foreignBean.findBySerialAndAmount(BondForeign);
            setBondForeigns(BondForeigns);
        } else {
            BondForeigns = foreignBean.searchAll();
            setBondForeigns(BondForeigns);
        }
        if (BondForeigns != null) {
            for (int i = 0; i < BondForeigns.size(); i++) {
                if (BondForeigns.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                    BondForeigns.get(i).setChangedStatus("PAID");
                } else if (BondForeigns.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                    BondForeigns.get(i).setChangedStatus("NOT PAID");
                } else if (BondForeigns.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.TERMINATED))) {
                    BondForeigns.get(i).setChangedStatus("TERMINATED");
                } else if (BondForeigns.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PEND))) {
                    BondForeigns.get(i).setChangedStatus("PEND");
                } else if (BondForeigns.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAST_DUE))) {
                    BondForeigns.get(i).setChangedStatus("PAST DUE");
                }
            }
        }
        recreateModelSrnDetailPop();
    }

    /*search method to fetch data of Bond issued country by passing the issued date and the country name from Bond foreign table*/
    public void getByCountryId() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String date1 = formatter.format(fromIssueDate);
        String date2 = formatter.format(toIssueDate);
        BondForeignsReportList = new ArrayList<>();
        BondForeignsReportList = foreignBean.getByCountryId1(date1, date2, luCountry);
        recreateBondForeignGroupDataModel();
        clean();
    }

    //<editor-fold defaultstate="collapsed" desc="select items method and array list search methods">
/*select items method to fetch data from currency, Bond type, Bond libor, Lu country, Bond foreign*/
    public SelectItem[] getCurrencyList() {
        return JsfUtil.getSelectItems(currencyFacade.findAll(), true);

    }
    /*value change event for Bond name*/

    public void selectBondType(ValueChangeEvent event) {
        BondForeign.setBondName(luBondType);
    }

    public SelectItem[] getBondTypeList() {
        return JsfUtil.getSelectItems(BondTypeFacade.findAll(), true);

    }

    public SelectItem[] getLiborRateList() {
        return JsfUtil.getSelectItems(liborBean.searchLiborRate(BondLibor), true);

    }

    public SelectItem[] getBondList() {
        return JsfUtil.getSelectItems(foreignFacade.findAll(), true);
    }

    public SelectItem[] getCunteryList() {
        return JsfUtil.getSelectItems(countryBeanLocal.findAll(), true);
    }

    public ArrayList<FmsBondForeign> searchCountry(String country) {
        ArrayList<FmsBondForeign> foreigns = null;
        BondForeign.setCountryBondIssued(luCountry);
        foreigns = foreignBean.searchCountry(BondForeign);
        return foreigns;
    }

    public ArrayList<FmsBondForeign> searchBondId(String BondId) {
        ArrayList<FmsBondForeign> foreigns = new ArrayList<>();
        BondForeign.setSerialNo(BondId);
        foreigns = foreignBean.searchBondId(BondForeign);
        return foreigns;
    }

    public ArrayList<FmsBondForeign> searchName(String BondId) {
        ArrayList<FmsBondForeign> foreigns = new ArrayList<>();
        BondForeign.setBuyerFullName(BondId);
        foreigns = foreignBean.searchName(BondForeign);
        return foreigns;
    }

    public ArrayList<FmsBondForeign> searchBybuyerName(String buyerName) {
        ArrayList<FmsBondForeign> foreigns = null;
        BondForeign.setBuyerFullName(buyerName);
        foreigns = foreignBean.searchName(BondForeign);
        return foreigns;
    }

    public List<FmsBondForeign> searchAll() {
        List<FmsBondForeign> foreigns = null;
        foreigns = foreignBean.searchAll();
        return foreigns;
    }
//</editor-fold> 
    /*method to check status of the selected Bond if it is qualified ( if its paid or not paid)for termination 
     inorder to perform Bond foreign termination*/

    public void Terminator() {
        BondForeignSchedules = BondForeign.getFmsBondForeignScheduleList();
        boolean paid = false;

        for (int i = 0; i < BondForeignSchedules.size(); i++) {
            if (BondForeignSchedules.get(i).getStatus().matches(String.valueOf(Constants.PAID))) {
                paid = true;
                paydupto = BondForeignSchedules.get(i).getInstallmentDueDate();
                BondForeign.getFmsBondForeignScheduleList().clear();
            }

        }
        if (!paid) {
            updateStatus = 2;
            BondForeign.getFmsBondForeignScheduleList().clear();
            paydupto = BondForeign.getApplicationDate();
        }

    }

    /*method to fetch row data of the Bond for the termination*/
    public void terminate() {
        BondForeign = new FmsBondForeign();
        BondForeign = BondForeignAddDataModel.getRowData();
    }

    /*select event to check whether the selected Bond is terminated, paid  or not paid*/
    public void BondForeigninfo(SelectEvent event) {
        BondForeign = (FmsBondForeign) event.getObject();
        if (BondForeign.getStatus().matches(String.valueOf(Constants.TERMINATED))) {
            termnetdisable = false;
            paydisable = false;
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            JsfUtil.addInformationMessage("The Bond is Already Terminated");

        } else if (BondForeign.getStatus().matches(String.valueOf(Constants.PAID))) {
            termnetdisable = false;
            paydisable = false;
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            JsfUtil.addInformationMessage("The Bond is Already Paid");
        } else {
            renderPnlCreateAdditional = true;
            fmsLuCurrency = BondForeign.getFmsLuCurrency();
            luBondType = BondForeign.getBondName();
            luCountry = BondForeign.getCountryBondIssued();
            recreateModelSrnDetailPop();
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
            updateStatus = 2;
            saveUpdate = "Update";
            renderPnlGourpPage = true;
            termnetdisable = true;
            paydisable = true;
            saveupreder = false;
        }
    }

    /*select event method for the termination of selected foreign Bond*/
    public void BondTerminater(SelectEvent event) {
        BondForeign = (FmsBondForeign) event.getObject();
        BondForeign.getFmsBondForeignScheduleList();
        BondForeign.getApplicationDate();
        BondForeign.getReferenceNumber();
        Terminator();
        terminationdate = BondForeign.getLastRepaymentSchedule();
        long df;
        df = terminationdate.getTime() / (1000 * 60 * 60 * 24) - paydupto.getTime() / (1000 * 60 * 60 * 24);
        Amount = df * BondForeign.getInterest() * BondForeign.getPrincipalamount();

    }

    /*select event method for country selection of foreign Bond registration by searching the data from ComLuCountry*/
    public void BondForeigncountrinfo(SelectEvent event) {
        clean();
        luCountry.setCountry(event.getObject().toString());
        luCountry = countryBeanLocal.luCountry(luCountry);
        BondForeign.setCountryBondIssued(luCountry);
        BondForeigns = foreignBean.searchCountry(BondForeign);
        BondForeignAddDataModel = new ListDataModel(BondForeigns);
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        updateStatus = 2;
        saveUpdate = "Save";
        Terminator();
        renderPnlGourpPage = false;
    }

    /*select event for country of the Bond issued by serarching ComLuCountry table*/
    public void lsnForeignController(SelectEvent event) {
        luCountry.setCountry(event.getObject().toString());
        luCountry = countryBeanLocal.luCountry(luCountry);
        BondForeign.setCountryBondIssued(luCountry);
    }

    /*method to save termination of foreign Bond and change the status both on foreign table and foreign schedule table*/
    public void singleBondTerminater() {
        Amount = new Double(0);
        BondForeign.getFmsBondForeignScheduleList();
        BondForeign.getApplicationDate();
        BondForeign.getPrincipalamount();
        interestBearing = BondForeign.getInterestBearing();
        Terminator();
        terminationdate = BondForeign.getLastRepaymentSchedule();
        long df;
        df = terminationdate.getTime() / (1000 * 60 * 60 * 24) - paydupto.getTime() / (1000 * 60 * 60 * 24);
        numberOfDays = (int) (df);//end date-start date (in integer/number)
        interest = numberOfDays * BondForeign.getPrincipalamount() * (interestBearing / 365);
        BondForeign.setInterest(interest);
        Amount = df * BondForeign.getInterest() * BondForeign.getPrincipalamount();
        for (int i = lastinstantance; i < BondForeign.getFmsBondForeignScheduleList().size(); i++) {
            BondForeign.getFmsBondForeignScheduleList().remove(i);
            foreignSchedule.setBondForeignId(BondForeign);
            foreignSchedule.setInstallmentDueDate(terminationdate);
            foreignSchedule.setInterest(Amount);
            foreignSchedule.setNoOfInstallmen(lastinstantance++);
            foreignSchedule.setSerialNo(BondForeign.getSerialNo());
            foreignSchedule.setStatus(String.valueOf(Constants.TERMINATED));
            BondForeign.setStatus(String.valueOf(Constants.TERMINATED));
            scheduleBean.Eidt(foreignSchedule);
            foreignBean.Eidt(BondForeign);
            JsfUtil.addSuccessMessage("Foreign Bond terminated");
            clean();
        }
    }

    /*Bond group search method, hold the status of the render variables and instanitiate BondForeignGroupDataModel*/
    public void searchBondgroup() {
        BondForeignGroupDataModel = new ListDataModel(BondForeigns);
        renderPnlGourpPage = true;
        renderPnlCreateAdditional = false;
        renderPnlManPage = false;
        panelgroupreport = true;

    }

    /*save update clear method */
    private void saveUpdateClear() {
        BondForeign = null;
        BondForeignAddDataModel = null;
        numberOfDays = new Long(0L);
        interest = 0.0;
        totalAmount = 0.0;
        saveUpdate = "Save";
    }

    /*clean method*/
    public void clean() {
        Amount = 0.0;
        interest = 0.0;
        foreignSchedule = new FmsBondForeignSchedule();
        luBondType = new FmsLuBondType();
        fmsLuCurrency = new FmsLuCurrency();
        luCountry = new ComLuCountry();
        BondForeign = new FmsBondForeign();
        BondLibor = new FmsBondLibor();
        BondForeignAddDataModel = new ArrayDataModel<>();
        BondForeignSchedules = new ArrayList<>();
        BondForeignAddDataModel = new ArrayDataModel<>();
        BondForeigns = new ArrayList<>();
        isCheckedInterestFree = false;
        isCheckedLiborPlus = false;
    }

    /*create and search render method*/
    public void createNewAdditionalAmount() {
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            renderPnlGourpPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            renderPnlGourpPage = false;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
            saveupreder = true;
        }
        clean();
    }
}
