/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Bond;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
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
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.BondLocalScheduleBeanLocal;
import et.gov.eep.fcms.businessLogic.Bond.BondLocalBeanLocals;
import et.gov.eep.fcms.entity.Bond.FmsBondLocal;
import et.gov.eep.fcms.entity.Bond.FmsBondLocalSchedule;
import et.gov.eep.fcms.mapper.Bond.FmsBondLocalFacade;
import et.gov.eep.fcms.mapper.Bond.FmsBondLocalScheduleFacade;

/**
 *
 * @author mora
 */
@Named(value = "bondLocalScheduleController")
@ViewScoped
public class BondLocalScheduleController implements Serializable {

//<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
//Inject entities
    @Inject
    FmsBondLocalSchedule localSchedule;
    @Inject
    SessionBean sessionBeanLocal;
    @Inject
    FmsBondLocal bondLocal;
    //Inject EJBs
    @EJB
    BondLocalScheduleBeanLocal localScheduleBean;
    @EJB
    FmsBondLocalScheduleFacade localScheduleFacade;
    @EJB
    BondLocalBeanLocals beanLocal;
    @EJB
    private FmsBondLocalFacade BondLocalFacade;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    double amountToBePaid = 0.0;
    Date fromStartDate, toEndDate;
    DataModel<FmsBondLocalSchedule> repaymentScheduleDatamodel;
    private List<FmsBondLocalSchedule> BondRepaymentSchedules;
    private List<FmsBondLocal> BondLocals = new ArrayList<>();
    List<FmsBondLocalSchedule> unpaidLocalBondLists;
    private NumberConverter numberConverter = new NumberConverter();
    private FmsBondLocal BondLocalselect;
    DataModel<FmsBondLocal> BondLocalAddDataModel;
    String saveUpdate = "Create";
    int updateStatus = 0;
    private String createOrSearchBundle = "";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlManPage = true;
    private boolean isAllPaid = false;
    private String icone = "ui-icon-document";
    private String activeIndex;
    String serialNo, Name;
    Double amount = new Double(0);
    Double interest = new Double(0);
    Double remain = 0.0;
    int paidStatus;

    //</editor-fold>
    public BondLocalScheduleController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(2);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">
    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public DataModel<FmsBondLocal> getBondLocalAddDataModel() {
        if (BondLocalAddDataModel == null) {
            BondLocalAddDataModel = new ListDataModel<>();
        }
        return BondLocalAddDataModel;
    }

    public void setBondLocalAddDataModel(DataModel<FmsBondLocal> BondLocalAddDataModel) {
        this.BondLocalAddDataModel = BondLocalAddDataModel;
    }

    List<FmsBondLocalSchedule> repaymentSchedules() {
        if (BondRepaymentSchedules == null) {
            BondRepaymentSchedules = new ArrayList<>();
        }
        return BondRepaymentSchedules;
    }

    public FmsBondLocal getBondLocal() {
        if (bondLocal == null) {
            bondLocal = new FmsBondLocal();
        }
        return bondLocal;
    }

    public void setBondLocal(FmsBondLocal BondLocal) {
        this.bondLocal = BondLocal;
    }

    public FmsBondLocal getBondLocalselect() {
        return BondLocalselect;
    }

    public void setBondLocalselect(FmsBondLocal BondLocalselect) {
        this.BondLocalselect = BondLocalselect;
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

    public double getAmountToBePaid() {
        return amountToBePaid;
    }

    public void setAmountToBePaid(double amountToBePaid) {
        this.amountToBePaid = amountToBePaid;
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

    public DataModel<FmsBondLocalSchedule> getRepaymentScheduleDatamodel() {

        if (repaymentScheduleDatamodel == null) {
            repaymentScheduleDatamodel = new ListDataModel<>();
        }
        return repaymentScheduleDatamodel;
    }

    public void setRepaymentScheduleDatamodel(DataModel<FmsBondLocalSchedule> repaymentScheduleDatamodel) {
        this.repaymentScheduleDatamodel = repaymentScheduleDatamodel;
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

    public List<FmsBondLocalSchedule> getUnpaidLocalBondLists() {
        if (unpaidLocalBondLists == null) {
            unpaidLocalBondLists = new ArrayList<>();
        }
        return unpaidLocalBondLists;
    }

    public void setUnpaidLocalBondLists(List<FmsBondLocalSchedule> unpaidLocalBondLists) {
        this.unpaidLocalBondLists = unpaidLocalBondLists;
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

    public boolean isIsAllPaid() {
        return isAllPaid;
    }

    public void setIsAllPaid(boolean isAllPaid) {
        this.isAllPaid = isAllPaid;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getRemain() {
        return remain;
    }

    public void setRemain(double remain) {
        this.remain = remain;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="select items and array search">
    public SelectItem[] getBondList() {
        return JsfUtil.getSelectItems(localScheduleBean.searchStatus(localSchedule), true);
    }

    public void getBySchedule(SelectEvent event) {
        repaymentScheduleDatamodel = null;
        BondRepaymentSchedules = null;
        localSchedule.setSerialNo(event.getObject().toString());
        recreateModelSrnDetailPop();
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }

    public void getBySchedule1(SelectEvent event) {
        bondLocal.setSerialNo(event.getObject().toString());
        bondLocal.setSerialNo(event.getObject().toString());
        bondLocal = beanLocal.searchBondIdinfo(bondLocal);
        recreateModelSrnDetailPop();
        renderPnlCreateAdditional = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
    }

    public ArrayList<FmsBondLocalSchedule> scheduleList() {
        return this.localScheduleBean.searchSchedule(localSchedule);
    }

    public ArrayList<FmsBondLocal> searchBondType(String serialNo) {
        ArrayList<FmsBondLocal> BondTypes = null;
        bondLocal.setSerialNo(serialNo);
        BondTypes = BondLocalFacade.searchFmsBondSerial(bondLocal);
        return BondTypes;
    }

    public ArrayList<FmsBondLocal> searchName(String BondId) {
        ArrayList<FmsBondLocal> BondTypes = null;
        bondLocal.setBuyerFullName(BondId);
        BondTypes = beanLocal.searchname(bondLocal);
        return BondTypes;
    }

    public void getBuyersName(SelectEvent event) {

        bondLocal.setBuyerFullName(event.getObject().toString());
        BondLocals = beanLocal.searchname(bondLocal);
    }

    //</editor-fold>
    public String addRepaymentScheduleInfoDetail() {
        if (!repaymentSchedules().contains(localSchedule)) {
            repaymentSchedules().add(localSchedule);
            recreateModelSrnDetailPop();
        }
        return null;
    }

//recreate method to assign BondLocalScheduleList value to repaymentScheduleDatamodel
    public void recreateModelSrnDetailPop() {
        repaymentScheduleDatamodel = null;
        repaymentScheduleDatamodel = new ListDataModel(bondLocal.getFmsBondLocalScheduleList());

    }

//recreate method to assign BondLocals list value to BondLocalAddDataModel
    public void recreateModelSrnDetail() {
        BondLocalAddDataModel = null;
        BondLocalAddDataModel = new ListDataModel(BondLocals);
    }

//recreate method to assign BondRepaymentSchedules list value to repaymentScheduleDatamodel
    public void recreateModelPop() {
        repaymentScheduleDatamodel = null;
        repaymentScheduleDatamodel = new ListDataModel(BondRepaymentSchedules);
    }

    //method to get raw data of selected Bond n update schedule
    public void updatelocalSchedule() {
        localSchedule = getRepaymentScheduleDatamodel().getRowData();
        amountToBePaid = localSchedule.getInterest() + localSchedule.getPrincipal();
        localSchedule.setPaidAmount(amountToBePaid);

    }

    //local payment method
    public void localPayment() {
        System.out.println("inside payment security");
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "localPayment", dataset)) {
                localSchedule.setSerialNo(bondLocal.getSerialNo());
                localSchedule.setStatus(String.valueOf(Constants.PAID));
                bondLocal.setStatus(String.valueOf(Constants.PAID));
                localScheduleBean.Eidt(localSchedule);
                recreateModelSrnDetailPop();
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

    //local payment 2 method
    public void localPayment1() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "localPayment1", dataset)) {
                localSchedule.setStatus(String.valueOf(Constants.PAID));
                localScheduleBean.Eidt(localSchedule);
                changedStatus();
                serialNo = localSchedule.getSerialNo();
                unpaidLocalBondLists = localScheduleBean.getNumberOfNotPaidStatus(serialNo, Constants.NOT_PAID);
                saveUpdateClear();
                if (unpaidLocalBondLists.isEmpty()) {
                    isAllPaid = true;
                }
                if (isAllPaid == true) {
                    bondLocal.getFmsBondLocalScheduleList().add(localSchedule);
                    for (int k = 0; k < bondLocal.getFmsBondLocalScheduleList().size(); k++) {
                        bondLocal = bondLocal.getFmsBondLocalScheduleList().get(k).getLocalBondId();
                        BondLocals.add(bondLocal.getFmsBondLocalScheduleList().get(k).getLocalBondId());
                        for (int j = 0; j < BondLocals.size(); j++) {
                            BondLocals.get(j).setStatus(String.valueOf(Constants.PAID));
                        }
                        beanLocal.Edit(bondLocal);
                    }

                }
                JsfUtil.addSuccessMessage("Payment is successfully done");
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

    //Bond search method using serial number and by default
    public void Bondsearch() {
        if (bondLocal.getSerialNo() != null) {
            BondLocals = beanLocal.findBySerialNo(bondLocal);
            recreateModelSrnDetailPop();
        } else {
            BondLocals = beanLocal.searchall();
            recreateModelSrnDetailPop();
        }

    }

    //local schedule search using serial number, buyers full name, and value in birr
    public void searchLocalRepayment() {
        if (bondLocal.getSerialNo() != null && bondLocal.getBuyerFullName() != null && bondLocal.getValueBirr() == null) {
            BondLocals = beanLocal.searchBond(bondLocal);
        } else if (bondLocal.getSerialNo() != null && bondLocal.getBuyerFullName() == null && bondLocal.getValueBirr() == null) {
            BondLocals = beanLocal.findBySerialNo(bondLocal);
        } else if (bondLocal.getSerialNo() == null && bondLocal.getBuyerFullName() != null && bondLocal.getValueBirr() == null) {
            BondLocals = beanLocal.searchname(bondLocal);
        } else if (bondLocal.getSerialNo() == null && bondLocal.getBuyerFullName() == null && bondLocal.getValueBirr() != null) {
            BondLocals = beanLocal.findByPrincipal(bondLocal);
        } else if (bondLocal.getSerialNo() != null && bondLocal.getBuyerFullName() != null && bondLocal.getValueBirr() != null) {
            BondLocals = beanLocal.findBySerialNameAndPrincipal(bondLocal);
        } else if (bondLocal.getSerialNo() != null && bondLocal.getBuyerFullName() == null && bondLocal.getValueBirr() != null) {
            BondLocals = beanLocal.findBySerialAndPrincipal(bondLocal);
        } else if (bondLocal.getSerialNo() == null && bondLocal.getBuyerFullName() != null && bondLocal.getValueBirr() != null) {
            BondLocals = beanLocal.findByPrincipalAndName(bondLocal);
        } else {
            BondLocals = beanLocal.searchall();
        }
        setBondLocals(BondLocals);
        recreateModelSrnDetail();
    }

    // method to search name of Bond using serial number and full namr and by default
    public void Bondreport() {
        serialNo = bondLocal.getSerialNo();
        Name = bondLocal.getBuyerFullName();
        bondLocal.setSerialNo(serialNo);
        bondLocal.setBuyerFullName(Name);
        if (bondLocal.getSerialNo() == null && bondLocal.getBuyerFullName() != null) {
            BondLocals = beanLocal.searchname(bondLocal);
            for (int i = 0; i < BondLocals.size(); i++) {
            }
            recreateModelSrnDetail();
        } else {
            BondLocals = beanLocal.searchall();
            recreateModelSrnDetail();
            JsfUtil.addSuccessMessage("insert search parametr");
        }

    }

    //method to search start and end date of schedule payment
    public void searchStartEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String date1 = formatter.format(fromStartDate);
        String date2 = formatter.format(toEndDate);
        System.out.print("Date one controller" + date1);
        BondRepaymentSchedules = new ArrayList<>();
        BondRepaymentSchedules = localScheduleBean.getByStartAndEndDate(date1, date2, localSchedule);
        changedStatus();

        repaymentScheduleDatamodel = new ListDataModel(BondRepaymentSchedules);
        repaymentScheduleDatamodel.getRowIndex();
        for (int i = 0; i < BondRepaymentSchedules.size(); i++) {
            if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                amountToBePaid = BondRepaymentSchedules.get(i).getInterest() + BondRepaymentSchedules.get(i).getPrincipal();
                BondRepaymentSchedules.get(i).setPaidAmount(amountToBePaid);
                remain = (BondRepaymentSchedules.get(i).getInterest() + BondRepaymentSchedules.get(i).getPrincipal()) - amountToBePaid;
                BondRepaymentSchedules.get(i).setRemain(remain);
                BondRepaymentSchedules.get(i).setStatus(String.valueOf(Constants.PAID));
                paidStatus = Integer.parseInt(BondRepaymentSchedules.get(i).getStatus());
            } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                amountToBePaid = 0.0;
                BondRepaymentSchedules.get(i).setPaidAmount(amountToBePaid);
                remain = BondRepaymentSchedules.get(i).getInterest() + BondRepaymentSchedules.get(i).getPrincipal();
                BondRepaymentSchedules.get(i).setRemain(remain);
            }
        }
        if (BondRepaymentSchedules != null) {
            for (int i = 0; i < BondRepaymentSchedules.size(); i++) {
                localSchedule = new FmsBondLocalSchedule();
                localSchedule = BondRepaymentSchedules.get(i);
                amount = amount + localSchedule.getPrincipal();
                interest = interest + localSchedule.getInterest();
            }
        }

    }

    //change status method to assign name of constants to status 
    public void changedStatus() {
        for (int i = 0; i < BondRepaymentSchedules.size(); i++) {
            if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAID))) {
                BondRepaymentSchedules.get(i).setChangeStatus("PAID");
                paidStatus = Integer.parseInt(BondRepaymentSchedules.get(i).getStatus());
            } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.NOT_PAID))) {
                BondRepaymentSchedules.get(i).setChangeStatus("NOT PAID");
            } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.TERMINATED))) {
                BondRepaymentSchedules.get(i).setChangeStatus("TERMINATED");
            } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PEND))) {
                BondRepaymentSchedules.get(i).setChangeStatus("PEND");
            } else if (BondRepaymentSchedules.get(i).getStatus().equalsIgnoreCase(String.valueOf(Constants.PAST_DUE))) {
                BondRepaymentSchedules.get(i).setChangeStatus("PAST_DUE");
            }
        }
    }

    //save update clear method
    private void saveUpdateClear() {
        bondLocal = null;
        localSchedule = null;
        BondLocalAddDataModel = null;
        saveUpdate = "Save";
    }

    //create and search render method
    public void createNewAdditionalAmount() {
        saveUpdateClear();
        if (createOrSearchBundle.equals("Forecast")) {
            renderPnlCreateAdditional = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateAdditional = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Forecast";
            setIcone("ui-icon-document");

        }
    }
}
