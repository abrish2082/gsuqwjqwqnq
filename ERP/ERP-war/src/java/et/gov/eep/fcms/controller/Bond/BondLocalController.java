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
import javax.faces.model.SelectItem;
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
import et.gov.eep.commonApplications.businessLogic.ComLuCountryBeanLocal;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.BondLocalExtendBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.BondLocalScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.BondLocalBeanLocals;
import et.gov.eep.fcms.entity.Bond.FmsBondLocal;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalExtend;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalSchedule;
import et.gov.eep.fcms.entity.Bond.FmsLuBondType;
import et.gov.eep.fcms.mapper.Bond.FmsBondLocalFacade;
import et.gov.eep.fcms.mapper.FmsLuBondTypeFacade;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;

/**
 *
 * @author mora
 */
@Named(value = "bondLocalController")
@ViewScoped
public class BondLocalController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
//Inject EJBs for bussiness logic
    @EJB
    private FmsBondLocalFacade BondLocalFacade;
    @EJB
    BondLocalScheduleBeanLocal scheduleBeanLocal;
    @EJB
    BondLocalExtendBeanLocal extendBeanLocal;
    @EJB
    FmsLuCurrencyFacade currencyFacade;
    @EJB
    ComLuCountryBeanLocal countryBeanLocal;
    @EJB
    FmsLuBondTypeFacade BondTypeFacade;
    @EJB
    BondLocalBeanLocals beanLocal;
    //Inject entities
    @Inject
    FmsBondLocal BondLocal;
    @Inject
    FmsBondLocalSchedule localSchedule;
    @Inject
    FmsBondLocalExtend BondLocalExtend;
    @Inject
    FmsLuBondType fmsLuBondType;
    @Inject
    ComLuCountry luCountry;
    @Inject
    SessionBean sessionBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    //Declaring instance variables
    Date applicationdate;
    Integer graceperiod;
    Integer Bondrepaidyear;
    Integer numberofInstallment;
    Double Bondamountinbirr;
    Double interestbearing;
    Double principal;
    Double Bondinterest;
    Double remain;
    double interestMarigin = 0.0;
    String Bondserialnumber;
    String saveUpdate = "Save";
    String SerialNo;
    String Name;
    int updateStatus = 1;
    private boolean disableBtnCreate;
    private boolean extend = false;
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean isCheckedInterestFree = false;
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private String activeIndex;
    boolean disable = false;
    private NumberConverter numberConverter = new NumberConverter();
    DataModel<FmsBondLocal> BondLocalAddDataModel;
    List<FmsLuBondType> Bondtyplist;
    private List<FmsBondLocal> BondLocals;
    private List<FmsBondLocalSchedule> BondRepaymentSchedules;
    private FmsBondLocal BondLocalselect;
//</editor-fold>

    public BondLocalController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(2);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public FmsBondLocal getBondLocal() {
        if (BondLocal == null) {
            BondLocal = new FmsBondLocal();
        }
        return BondLocal;
    }

    public void setBondLocal(FmsBondLocal BondLocal) {
        this.BondLocal = BondLocal;
    }

    public FmsBondLocalExtend getBondLocalExtend() {
        return BondLocalExtend;
    }

    public void setBondLocalExtend(FmsBondLocalExtend BondLocalExtend) {
        this.BondLocalExtend = BondLocalExtend;
    }

    public FmsLuBondType getFmsLuBondType() {
        if (fmsLuBondType == null) {
            fmsLuBondType = new FmsLuBondType();
        }
        return fmsLuBondType;
    }

    public void setFmsLuBondType(FmsLuBondType fmsLuBondType) {
        this.fmsLuBondType = fmsLuBondType;
    }

    public FmsBondLocalSchedule getLocalSchedule() {
        if (localSchedule == null) {
            localSchedule = new FmsBondLocalSchedule();
        }
        return localSchedule;
    }

    public void setLocalSchedule(FmsBondLocalSchedule localSchedule) {
        this.localSchedule = localSchedule;
    }
//Variables Getter and Setter 

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String SerialNo) {
        this.SerialNo = SerialNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public FmsBondLocal getBondLocalselect() {
        return BondLocalselect;
    }

    public void setBondLocalselect(FmsBondLocal BondLocalselect) {
        this.BondLocalselect = BondLocalselect;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public boolean isIsCheckedInterestFree() {
        return isCheckedInterestFree;
    }

    public void setIsCheckedInterestFree(boolean isCheckedInterestFree) {
        this.isCheckedInterestFree = isCheckedInterestFree;
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

    public boolean isExtend() {
        return extend;
    }

    public void setExtend(boolean extend) {
        this.extend = extend;
    }

    public Date getApplicationdate() {
        return applicationdate;
    }

    public void setApplicationdate(Date applicationdate) {
        this.applicationdate = applicationdate;
    }

    public int getGraceperiod() {
        return graceperiod;
    }

    public void setGraceperiod(int graceperiod) {
        this.graceperiod = graceperiod;
    }

    public int getBondrepaidyear() {
        return Bondrepaidyear;
    }

    public void setBondrepaidyear(int Bondrepaidyear) {
        this.Bondrepaidyear = Bondrepaidyear;
    }

    public Integer getNumberofInstallment() {
        return numberofInstallment;
    }

    public void setNumberofInstallment(Integer numberofInstallment) {
        this.numberofInstallment = numberofInstallment;
    }

    public Double getBondamountinbirr() {
        return Bondamountinbirr;
    }

    public void setBondamountinbirr(Double Bondamountinbirr) {
        this.Bondamountinbirr = Bondamountinbirr;
    }

    public Double getInterestbearing() {
        return interestbearing;
    }

    public void setInterestbearing(Double interestbearing) {
        this.interestbearing = interestbearing;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getBondinterest() {
        return Bondinterest;
    }

    public void setBondinterest(Double Bondinterest) {
        this.Bondinterest = Bondinterest;
    }

    public String getBondserialnumber() {
        return Bondserialnumber;
    }

    public void setBondserialnumber(String Bondserialnumber) {
        this.Bondserialnumber = Bondserialnumber;
    }

    public List<FmsLuBondType> getBondtyplist() {
        Bondtyplist = BondTypeFacade.findAll();
        return Bondtyplist;
    }

    public void setBondtyplist(List<FmsLuBondType> Bondtyplist) {

        this.Bondtyplist = Bondtyplist;
    }

    public ComLuCountry getLuCountry() {
        return luCountry;
    }

    public void setLuCountry(ComLuCountry luCountry) {
        this.luCountry = luCountry;
    }

    public DataModel<FmsBondLocal> getBondLocalAddDataModel() {
        if (BondLocalAddDataModel == null) {
            BondLocalAddDataModel = new ArrayDataModel<>();
        }
        return BondLocalAddDataModel;
    }

    public void setBondLocalAddDataModel(DataModel<FmsBondLocal> BondLocalAddDataModel) {
        this.BondLocalAddDataModel = BondLocalAddDataModel;
    }

    public List<FmsBondLocal> getBondLocals() {
        if (BondLocals == null) {
            BondLocals = new ArrayList<>();
        }
        return BondLocals;
    }

    public void setBondLocals(List<FmsBondLocal> BondLocals) {
        this.BondLocals = BondLocals;
    }

    List<FmsBondLocalSchedule> repaymentSchedules() {
        if (BondRepaymentSchedules == null) {
            BondRepaymentSchedules = new ArrayList<>();
        }
        return BondRepaymentSchedules;
    }

    public List<FmsBondLocalSchedule> getBondRepaymentSchedules() {
        if (BondRepaymentSchedules == null) {
            BondRepaymentSchedules = new ArrayList<>();
        }
        return BondRepaymentSchedules;
    }

    public void setBondRepaymentSchedules(List<FmsBondLocalSchedule> BondRepaymentSchedules) {
        this.BondRepaymentSchedules = BondRepaymentSchedules;
    }

    //</editor-fold> 
    /*recreate method to assign BondLocals value to BondLocalAddDataModel */
    public void recreateModelSrnDetailPop() {
        BondLocalAddDataModel = null;
        BondLocalAddDataModel = new ListDataModel(getBondLocals());
    }

    //<editor-fold defaultstate="collapsed" desc="select items and array lists">
    /*select items and array list search methods */
    public SelectItem[] getBondTypeList() {
        return JsfUtil.getSelectItems(BondTypeFacade.findAll(), true);

    }

    public SelectItem[] getCurrencyList() {
        return JsfUtil.getSelectItems(currencyFacade.findAll(), true);

    }

    public ArrayList<FmsBondLocal> searchBondType(String serialNo) {
        ArrayList<FmsBondLocal> BondTypes = null;
        BondLocal.setSerialNo(serialNo);
        BondTypes = BondLocalFacade.searchFmsBondSerial(BondLocal);
        return BondTypes;
    }

    public ArrayList<FmsBondLocal> searchBondId(String BondId) {
        ArrayList<FmsBondLocal> BondTypes = null;
        BondLocal.setSerialNo(BondId);
        BondTypes = beanLocal.searchBondId(BondLocal);
        return BondTypes;
    }

    public ArrayList<FmsBondLocal> searchName(String BondId) {
        ArrayList<FmsBondLocal> BondTypes = null;
        BondLocal.setBuyerFullName(BondId);
        BondTypes = beanLocal.searchname(BondLocal);
        return BondTypes;
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="select events and value change events">

    public void getBuyersName(SelectEvent event) {

        BondLocal.setBuyerFullName(event.getObject().toString());
        BondLocals = beanLocal.searchname(BondLocal);
    }

    /**/
    public void getBySchedule(SelectEvent event) {
        BondLocal.setSerialNo(event.getObject().toString());
        BondLocal.setSerialNo(event.getObject().toString());
        BondLocal = beanLocal.searchBondIdinfo(BondLocal);
        recreateModelSrnDetailPop();
        updateStatus = 2;
        saveUpdate = "Update";
    }

    /*select event for Bond locals to fetch data using Bond id*/
    public void getBond(SelectEvent event) {
        BondLocalselect.setSerialNo(event.getObject().toString());
        BondLocalselect.setSerialNo(event.getObject().toString());
        BondLocal = beanLocal.searchBondIdinfo(BondLocalselect);
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        extend = true;
        setIcone("ui-icon-search");
        updateStatus = 2;
        saveUpdate = "Update";

    }

    /* method for change for maturity of the issued Bond*/
    public void changeOnMaturity(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            Bondrepaidyear = Integer.parseInt((event.getNewValue().toString()));
            BondLocal.setRePaid(Bondrepaidyear);
            if (applicationdate != null) {
                Bondrepaidyear = BondLocal.getRePaid();
                applicationdate = BondLocal.getApplicationDate();//applicationdate for rigeter day
                Calendar cal = Calendar.getInstance();
                cal.setTime(applicationdate);
                cal.add(Calendar.YEAR, Bondrepaidyear);
                Date duedate = cal.getTime();
                BondLocal.setBondMaturityDate(duedate);
            }
        }

    }

    /*method to change maturity date for the issued Bond*/
    public void changeOnMaturityDate(ValueChangeEvent event) throws ParseException {
        if (event.getNewValue() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
            applicationdate = sdf.parse(event.getNewValue().toString());
            if (BondLocal.getRePaid() != null) {
                BondLocal.setApplicationDate(applicationdate);
                Bondrepaidyear = BondLocal.getRePaid();
                applicationdate = BondLocal.getApplicationDate();//applicationdate for rigeter day
                Calendar cal = Calendar.getInstance();
                cal.setTime(applicationdate);
                cal.add(Calendar.YEAR, Bondrepaidyear);
                Date duedate = cal.getTime();
                BondLocal.setBondMaturityDate(duedate);
            }
        }

    }
    /*select event for interest marigin*/

    public void InterestMarigin(ValueChangeEvent event) {
        interestMarigin = BondLocal.getInterestBearing();
    }

    /*select event to hold the status if interest free checkboc is checked or not*/
    public void onCheckInterestFreeEvent(ValueChangeEvent event) {
        if (null != event.getNewValue()) {
            if (event.getNewValue().toString().equalsIgnoreCase("true")) {//checked
                isCheckedInterestFree = true;
                if (updateStatus == 1) {//on save
                    BondLocal.setInterestBearing(0.00);
                    interestMarigin = BondLocal.getInterestBearing();

                } else if (updateStatus == 2) {//on update
                    BondLocal.setInterestBearing(0.00);
                    interestMarigin = BondLocal.getInterestBearing();
                    BondLocal.setInterestBearing(0.00);

                }
            } else {//unchecked
                isCheckedInterestFree = false;
                if (updateStatus == 1) {//on save and unchecked
                    interestMarigin = BondLocal.getInterestBearing();
                    BondLocal.setInterestBearing(interestMarigin);
//                    
                } else if (updateStatus == 2) {//on update and unchecked
                    interestMarigin = BondLocal.getInterestBearing();
                    BondLocal.setInterestBearing(interestMarigin);

                }
            }
        }
    }

//</editor-fold>
    // save method for Bond local   
    public void saveBondLocal() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveBondLocal", dataset)) {

                if (updateStatus == 1) {
                    graceperiod = BondLocal.getGracePeriod();//G : for GracePeriod
                    interestbearing = BondLocal.getInterestBearing();//i Interest Bearing 
                    Bondamountinbirr = BondLocal.getValueBirr();//A: Bond Value Birr or amout
                    Bondrepaidyear = BondLocal.getRePaid(); // Y : the year for re payment 
                    applicationdate = BondLocal.getApplicationDate();// R_D : registered date 
                    Bondserialnumber = BondLocal.getSerialNo();// Bondserialnumber: serial Number or id
                    for (int i = 0; i < graceperiod; i++) {
                        Bondamountinbirr = Bondamountinbirr + (Bondamountinbirr * interestbearing);
                    }
                    numberofInstallment = (2 * (Bondrepaidyear - graceperiod));//r 
                    if (numberofInstallment == 0) {
                        JsfUtil.addFatalMessage("please check the value of maturity date and grace period, both values can not be equal");
                    } else {
                        principal = Bondamountinbirr / numberofInstallment;
                        int D = 0;
                        Calendar c = Calendar.getInstance();
                        c.setTime(applicationdate); // Now use today date.
                        c.add(Calendar.YEAR, graceperiod); // Adding 5 days 
                        Calendar duedate = Calendar.getInstance();
                        duedate.setTime(applicationdate);
                        int year;
                        year = c.get(Calendar.YEAR);
                        duedate.set(year, 6, 7);
                        Calendar july_check = Calendar.getInstance();
                        for (int i = 0; i < numberofInstallment; i++) {
                            FmsBondLocalSchedule localSchedule = new FmsBondLocalSchedule();
                            BondLocal.setSerialNo(Bondserialnumber);
                            localSchedule.setLocalBondId(BondLocal);
                            localSchedule.setPrincipal(principal);

                            if (i == 0) {
                                if (c.before(duedate)) {
                                    Date issueddate = c.getTime();
                                    Date july7 = duedate.getTime();
                                    long df;

                                    df = july7.getTime() / (1000 * 60 * 60 * 24) - issueddate.getTime() / (1000 * 60 * 60 * 24);
                                    localSchedule.setInstallmentDueDate(duedate.getTime());
                                    int numberofdays = c.getActualMaximum(Calendar.DAY_OF_YEAR);
                                    Bondinterest = ((Bondamountinbirr * interestbearing) * df) / numberofdays;
                                    july_check.set(year, 11, 31);
                                    localSchedule.setInterest(Bondinterest);
                                    localSchedule.setSerialNo(BondLocal.getSerialNo());
                                    localSchedule.setStatus(String.valueOf(Constants.NOT_PAID));
                                    BondLocal.setStatus(String.valueOf(Constants.NOT_PAID));
                                    localSchedule.setNoOfInstallmen(i);
                                    localSchedule.setInstallmentDueDate(duedate.getTime());
                                } else if (c.after(duedate)) {

                                    duedate.set(year, 11, 31);
                                    Date issueddate = c.getTime();
                                    Date Dec31 = duedate.getTime();
                                    long df;
                                    df = Dec31.getTime() / (1000 * 60 * 60 * 24) - issueddate.getTime() / (1000 * 60 * 60 * 24);
                                    localSchedule.setInstallmentDueDate(c.getTime());
                                    int numberofdays = c.getActualMaximum(Calendar.DAY_OF_YEAR);
                                    Bondinterest = ((Bondamountinbirr * interestbearing) * df) / numberofdays;
                                    localSchedule.setInterest(Bondinterest);
                                    localSchedule.setSerialNo(BondLocal.getSerialNo());
                                    localSchedule.setStatus(String.valueOf(Constants.NOT_PAID));
                                    BondLocal.setStatus(String.valueOf(Constants.NOT_PAID));
                                    localSchedule.setNoOfInstallmen(i);
                                    localSchedule.setInstallmentDueDate(duedate.getTime());
                                    year = year + 1;
                                    july_check.set(year, 6, 7);
                                }
                            } else {
                                Date issueddate = duedate.getTime();
                                long df = 0;
                                duedate = july_check;
                                localSchedule.setInstallmentDueDate(duedate.getTime());
                                int numberofdays = duedate.getActualMaximum(Calendar.DAY_OF_YEAR);
                                year = july_check.get(Calendar.YEAR);
                                Calendar july = Calendar.getInstance();
                                Calendar Dec = Calendar.getInstance();
                                Dec.set(year, 12, 31);
                                july.set(year, 6, 7);
                                if (july_check.get(Calendar.YEAR) == july.get(Calendar.YEAR) && july_check.get(Calendar.MONTH) == july.get(Calendar.MONTH)) {
                                    df = 177;
                                    Bondinterest = Bondamountinbirr * df * (interestbearing / numberofdays);
                                    localSchedule.setInterest(Bondinterest);
                                    localSchedule.setSerialNo(BondLocal.getSerialNo());
                                    localSchedule.setStatus(String.valueOf(Constants.NOT_PAID));
                                    BondLocal.setStatus(String.valueOf(Constants.NOT_PAID));
                                    localSchedule.setNoOfInstallmen(i);
                                    july_check.set(year, 11, 31);
                                } else {
                                    df = 188;
                                    Bondinterest = Bondamountinbirr * df * (interestbearing / numberofdays);
                                    localSchedule.setInterest(Bondinterest);
                                    localSchedule.setSerialNo(BondLocal.getSerialNo());
                                    localSchedule.setStatus(String.valueOf(Constants.NOT_PAID));
                                    BondLocal.setStatus(String.valueOf(Constants.NOT_PAID));
                                    localSchedule.setNoOfInstallmen(i);
                                    year = year + 1;
                                    july_check.set(year, 6, 7);
                                }
                            }
                            if (localSchedule != null) {
                                repaymentSchedules().add(localSchedule);
                                BondLocal.addToFmsBFmsBondLocalandForeignScheduleDetail(localSchedule);
                                localSchedule = null;
                            }
                        }
                        if (BondLocal != null) {
                            beanLocal.Create(BondLocal);
                            System.err.println("new sril " + BondLocal.getSerialNo());
                            JsfUtil.addSuccessMessage("Local Bond successfully registered");
                            cleaPopUp();
                        }
                    }
                } else if (updateStatus == 2) {
                    beanLocal.Edit(BondLocal);
                    graceperiod = BondLocal.getGracePeriod();//G : for GracePeriod
                    interestbearing = BondLocal.getInterestBearing();//i Interest Bearing 
                    Bondamountinbirr = BondLocal.getValueBirr();//A: Bond Value Birr or amout
                    Bondrepaidyear = BondLocal.getRePaid(); // Y : the year for re payment 
                    applicationdate = BondLocal.getApplicationDate();// R_D : registered date 
                    Bondserialnumber = BondLocal.getSerialNo();// Bondserialnumber: serial Number or id
                    for (int i = 0; i < graceperiod; i++) {
                        Bondamountinbirr = Bondamountinbirr + (Bondamountinbirr * interestbearing);
                    }
                    numberofInstallment = (2 * (Bondrepaidyear - graceperiod));//r 
                    principal = Bondamountinbirr / numberofInstallment;
                    int D = 0;
                    for (int i = 0; i < numberofInstallment; i++) {
                        FmsBondLocal BondLocal = new FmsBondLocal();
                        FmsBondLocalSchedule localSchedule = new FmsBondLocalSchedule();
                        BondLocal.setSerialNo(Bondserialnumber);
                        localSchedule.setLocalBondId(BondLocal);
                        localSchedule.setPrincipal(principal);
                        Calendar c = Calendar.getInstance();
                        c.setTime(applicationdate); // Now use today date.
                        c.add(Calendar.YEAR, graceperiod); // Adding 5 days 
                        c.add(Calendar.DATE, D); // Adding 5 days
                        localSchedule.setInstallmentDueDate(c.getTime());
                        Bondinterest = Bondamountinbirr * D * (interestbearing / 360);
                        localSchedule.setInterest(Bondinterest);
                        localSchedule.setSerialNo(BondLocal.getSerialNo());
                        localSchedule.setStatus(String.valueOf(Constants.NOT_PAID));
                        BondLocal.setStatus(String.valueOf(Constants.NOT_PAID));
                        localSchedule.setNoOfInstallmen(i);
                        D = 180;
                        if (localSchedule != null) {
                            repaymentSchedules().add(localSchedule);
                            BondLocal.addToFmsBFmsBondLocalandForeignScheduleDetail(localSchedule);
                        }
                        localSchedule = null;
                    }
                    if (BondLocal != null) {
                        beanLocal.Edit(BondLocal);
                        cleaPopUp();
                        saveUpdateClear();
                        JsfUtil.addSuccessMessage("Local Bond successfully updated");
                    }
                } else {
                    JsfUtil.addFatalMessage(" Local Bond update not alowed teporarly");
                }
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

    /*method to check status of the selected Bond if it is qualified ( if its paid or not paid)for termination 
     inorder to perform Bond foreign termination*/
    public void Terminator() {
        BondRepaymentSchedules = BondLocal.getFmsBondLocalScheduleList();
        remain = BondRepaymentSchedules.get(0).getRemain();
        boolean paid = false;
        for (int i = 0; i < BondRepaymentSchedules.size(); i++) {
            if (BondRepaymentSchedules.get(i).getStatus().matches("payed")) {
                paid = true;
                if (-1 == BondRepaymentSchedules.get(i).getRemain().compareTo(remain)) {
                    remain = BondRepaymentSchedules.get(i).getRemain();
                }
            }

        }

        if (!paid) {
            updateStatus = 2;
            saveUpdate = "Update";

        } else {
            saveUpdate = "Extend";
            BondLocalExtend.setRemainBirr(remain);
            BondLocal.setValueBirr(remain);
            BondLocal.setExtend_no(BondLocal.getSerialNo());
        }

        BondLocal.getFmsBondLocalScheduleList().clear();

    }

    /* method to set due date for the issued local Bond    */
    public String addLocalBondDetail() {
        Date end_day = BondLocal.getApplicationDate();
        int re_paid = BondLocal.getRePaid();
        Calendar c = Calendar.getInstance();
        c.setTime(end_day); // Now use today date.
        c.add(Calendar.YEAR, re_paid); // Adding 5 days
        BondLocal.setDueDate(c.getTime());
        getBondLocals().add(BondLocal);
        recreateModelSrnDetailPop();
        cleaPopUp();
        return null;

    }

    // Bond local search methods
    public void search() {
        SerialNo = BondLocal.getSerialNo();
        Name = BondLocal.getBuyerFullName();
        if (BondLocal.getSerialNo() != null && BondLocal.getBuyerFullName() != null) {
            BondLocals = beanLocal.searchBond(BondLocal);
            recreateModelSrnDetailPop();

        } else {
            JsfUtil.addSuccessMessage("insert search parameter");

        }

    }

// search method for Bond local from Bond local table using serial number, buyers full name, value in birr 
    public void searchBondLocal() {
        if (BondLocal.getSerialNo() != null && BondLocal.getBuyerFullName() != null && BondLocal.getValueBirr() == null) {
            BondLocals = beanLocal.searchBond(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();
        } else if (BondLocal.getSerialNo() != null && BondLocal.getBuyerFullName() == null && BondLocal.getValueBirr() == null) {
            BondLocals = beanLocal.findBySerialNo(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();
        } else if (BondLocal.getSerialNo() == null && BondLocal.getBuyerFullName() != null && BondLocal.getValueBirr() == null) {
            BondLocals = beanLocal.searchname(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();
        } else if (BondLocal.getSerialNo() == null && BondLocal.getBuyerFullName() == null && BondLocal.getValueBirr() != null) {
            BondLocals = beanLocal.findByPrincipal(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();
        } else if (BondLocal.getSerialNo() != null && BondLocal.getBuyerFullName() != null && BondLocal.getValueBirr() != null) {
            BondLocals = beanLocal.findBySerialNameAndPrincipal(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();
        } else if (BondLocal.getSerialNo() != null && BondLocal.getBuyerFullName() == null && BondLocal.getValueBirr() != null) {
            BondLocals = beanLocal.findBySerialAndPrincipal(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();
        } else if (BondLocal.getSerialNo() == null && BondLocal.getBuyerFullName() != null && BondLocal.getValueBirr() != null) {
            BondLocals = beanLocal.findByPrincipalAndName(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();
        } else {
            BondLocals = beanLocal.searchall();
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();
        }

    }

// search method for Bond local from Bond local table using serial number and buyers full name
    public void Bondsearch() {

        if (BondLocal.getSerialNo() != null && BondLocal.getBuyerFullName() != null) {
            BondLocals = beanLocal.searchBond(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();

        } else if (BondLocal.getSerialNo() != null && BondLocal.getBuyerFullName() == null) {
            BondLocals = beanLocal.searchBondId(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();

        } else if (BondLocal.getSerialNo() == null && BondLocal.getBuyerFullName() != null) {
            BondLocals = beanLocal.searchname(BondLocal);
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();

        } else {
            BondLocals = beanLocal.searchall();
            setBondLocals(BondLocals);
            recreateModelSrnDetailPop();
        }

    }

    /*method to fetch raw data for BondLocalAddDataModel*/
    public void edit() {
        BondLocal = BondLocalAddDataModel.getRowData();
        createNewAdditionalAmount();
    }

    //save update clear method 
    private void saveUpdateClear() {
        BondLocal = null;
        BondLocalAddDataModel = null;
        saveUpdate = "Save";
    }

    //clear method 
    private void cleaPopUp() {
        BondLocal = new FmsBondLocal();
        BondLocals = new ArrayList<>();
        isCheckedInterestFree = false;
        localSchedule = new FmsBondLocalSchedule();
        fmsLuBondType = new FmsLuBondType();
        BondRepaymentSchedules = new ArrayList<>();
        Bondtyplist = new ArrayList<>();
    }

    //method for create and search render
    public void createNewAdditionalAmount() {
        saveUpdateClear();
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");

        }
    }

}
