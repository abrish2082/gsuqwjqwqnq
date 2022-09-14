/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

   //<editor-fold defaultstate="collapsed" desc="Imports">
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import org.primefaces.event.SelectEvent;
import et.gov.eep.commonApplications.entity.WfPrmsProcessed;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.prms.businessLogic.ServiceRegistrationBeanLocal;
import et.gov.eep.prms.entity.PrmsServiceAndWorkReg;
import et.gov.eep.commonApplications.utility.Dictionary;
import et.gov.eep.commonApplications.utility.StringDateManipulation;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import java.text.ParseException;
import webService.AAA;
import webService.IAdministration;
import webService.EventEntry;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import securityBean.Constants;
import securityBean.WorkFlow;
//</editor-fold>

/**
 * Service and work Setup Registration page View Scoped ManagedBean Class
 *
 * @author Bayisa Bedasa
 */
//Service and Work page view scoped CDI Named Bean class
@Named(value = "servAndWorkRegistrationController")
@ViewScoped
public class ServiceAndWorkRegistrationController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    ServiceRegistrationBeanLocal serviceBeanlocal;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inject Entities">
    @Inject
    PrmsServiceAndWorkReg serviceAndWorkReg;
    @Inject
    FmsGeneralLedger fmsGeneralLedger;
    @Inject
    Dictionary dictionary;
    @Inject
    WorkFlow workFlow;
    @Inject
    WfPrmsProcessed wfPrmsProcessed;
    @Inject
    SessionBean sessionBean;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare Variables">
    int updateStatus = 0;
    int newServOrWorkNo = 0;
    int dateWithTodayComparisonStatus;

    private boolean renderPnlCreateServiceRegistration = false;
    private boolean renderPnlManPage = true;
    private boolean renderPnlToSearchPage;
    private boolean setToService = true;
    private boolean setToWork = false;
    private boolean renderToConsultancy = true;
    private boolean renderToNonconsultancy = false;
    private boolean hideWhenWork = true;
    private boolean hideWhenService = true;
    private boolean disableServiceAndWorkRdbtnWhenSearch = false;
    private boolean changeStatus;

    private String currentServOrWorkNo;
    private String icone = "ui-icon-plus";
    private String activeIndex;
    private String saveorUpdateBundle = "Save";
    private String createOrSearchBundle = "New";
    private String loggerName;
    private String checkAsRegTypeIsServ = "service"; //give the default value
    private String checkAsdefaultConsultancy = "consultancy";
    private String renderTitle = "Service/Work";
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Declare Lists and models">
//Declare relational Variables
    List<PrmsServiceAndWorkReg> allServicesInfoList;
    List<FmsGeneralLedger> genLedgerCodeList;
    List<PrmsServiceAndWorkReg> serviceAndWorkSearchParameterLst;
    private DataModel<PrmsServiceAndWorkReg> prmsServiceAndWorkRegSearchInfoDataModel;

    private PrmsServiceAndWorkReg selectedRow;
//</editor-fold>

    //Empty default contructor method
    public ServiceAndWorkRegistrationController() {
    }

    // initial method declared using PostConstruct call-back lifecycle
    @PostConstruct
    public void init() {
        serviceAndWorkReg.setPreparedBy(workFlow.getUserAccount());
        setLoggerName(workFlow.getUserName());
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter of objects">
    //Entities's Getters and setters
    public PrmsServiceAndWorkReg getServiceAndWorkReg() {
        if (serviceAndWorkReg == null) {
            serviceAndWorkReg = new PrmsServiceAndWorkReg();
        }
        return serviceAndWorkReg;
    }

    public void setServiceAndWorkReg(PrmsServiceAndWorkReg serviceAndWorkReg) {
        this.serviceAndWorkReg = serviceAndWorkReg;
    }

    public FmsGeneralLedger getFmsGeneralLedger() {
        if (fmsGeneralLedger == null) {
            fmsGeneralLedger = new FmsGeneralLedger();
        }
        return fmsGeneralLedger;
    }

    public void setFmsGeneralLedger(FmsGeneralLedger fmsGeneralLedger) {
        this.fmsGeneralLedger = fmsGeneralLedger;
    }

    public Dictionary getDictionary() {
        if (dictionary == null) {
            dictionary = new Dictionary();
        }
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
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

    //for row selection
    public PrmsServiceAndWorkReg getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(PrmsServiceAndWorkReg selectedRow) {
        this.selectedRow = selectedRow;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter of Variables">
    //instance variables Getters and Setters
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

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
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

    public String getCheckAsRegTypeIsServ() {
        return checkAsRegTypeIsServ;
    }

    public void setCheckAsRegTypeIsServ(String checkAsRegTypeIsServ) {
        this.checkAsRegTypeIsServ = checkAsRegTypeIsServ;
    }

    public String getCheckAsdefaultConsultancy() {
        return checkAsdefaultConsultancy;
    }

    public void setCheckAsdefaultConsultancy(String checkAsdefaultConsultancy) {
        this.checkAsdefaultConsultancy = checkAsdefaultConsultancy;
    }

    public String getRenderTitle() {
        return renderTitle;
    }

    public void setRenderTitle(String renderBothTitle) {
        this.renderTitle = renderBothTitle;
    }

    public boolean isRenderPnlCreateServiceRegistration() {
        return renderPnlCreateServiceRegistration;
    }

    public void setRenderPnlCreateServiceRegistration(boolean renderPnlCreateServiceRegistration) {
        this.renderPnlCreateServiceRegistration = renderPnlCreateServiceRegistration;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public boolean isRenderPnlToSearchPage() {
        return renderPnlToSearchPage;
    }

    public void setRenderPnlToSearchPage(boolean renderPnlToSearchPage) {
        this.renderPnlToSearchPage = renderPnlToSearchPage;
    }

    public boolean isSetToService() {
        return setToService;
    }

    public void setSetToService(boolean setToService) {
        this.setToService = setToService;
    }

    public boolean isSetToWork() {
        return setToWork;
    }

    public void setSetToWork(boolean setToWork) {
        this.setToWork = setToWork;
    }

    public boolean isRenderToConsultancy() {
        return renderToConsultancy;
    }

    public void setRenderToConsultancy(boolean renderToConsultancy) {
        this.renderToConsultancy = renderToConsultancy;
    }

    public boolean isRenderToNonconsultancy() {
        return renderToNonconsultancy;
    }

    public void setRenderToNonconsultancy(boolean renderToNonconsultancy) {
        this.renderToNonconsultancy = renderToNonconsultancy;
    }

    public boolean isHideWhenWork() {
        return hideWhenWork;
    }

    public void setHideWhenWork(boolean hideWhenWork) {
        this.hideWhenWork = hideWhenWork;
    }

    public boolean isHideWhenService() {
        return hideWhenService;
    }

    public void setHideWhenService(boolean hideWhenService) {
        this.hideWhenService = hideWhenService;
    }

    public boolean isDisableServiceAndWorkRdbtnWhenSearch() {
        return disableServiceAndWorkRdbtnWhenSearch;
    }

    public void setDisableServiceAndWorkRdbtnWhenSearch(boolean disableServiceAndWorkRdbtnWhenSearch) {
        this.disableServiceAndWorkRdbtnWhenSearch = disableServiceAndWorkRdbtnWhenSearch;
    }

    public boolean isChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(boolean changeStatus) {
        this.changeStatus = changeStatus;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getter and setter of Lists and models">
    public List<PrmsServiceAndWorkReg> getAllServicesInfoList() {
        if (allServicesInfoList == null) {
            allServicesInfoList = new ArrayList<>();
        }
        return allServicesInfoList;
    }

    public void setAllServicesInfoList(List<PrmsServiceAndWorkReg> allServicesInfoList) {
        this.allServicesInfoList = allServicesInfoList;
    }

    public List<FmsGeneralLedger> getGenLedgerCodeList() {
        if (genLedgerCodeList == null) {
            genLedgerCodeList = new ArrayList<>();
            genLedgerCodeList = serviceBeanlocal.getGeneralLedgerCodes();
        }
        return genLedgerCodeList;
    }

    public void setGenLedgerCodeList(List<FmsGeneralLedger> genLedgerCodeList) {
        this.genLedgerCodeList = genLedgerCodeList;
    }

    public DataModel<PrmsServiceAndWorkReg> getPrmsServiceAndWorkRegSearchInfoDataModel() {
        if (prmsServiceAndWorkRegSearchInfoDataModel == null) {
            prmsServiceAndWorkRegSearchInfoDataModel = new ListDataModel<>();
        }
        return prmsServiceAndWorkRegSearchInfoDataModel;
    }

    public void setPrmsServiceAndWorkRegSearchInfoDataModel(DataModel<PrmsServiceAndWorkReg> prmsServiceAndWorkRegSearchInfoDataModel) {
        this.prmsServiceAndWorkRegSearchInfoDataModel = prmsServiceAndWorkRegSearchInfoDataModel;
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Event Changes">
    //Event that occured when we Select a row from our DataModel
    public List<PrmsServiceAndWorkReg> getServiceAndWorkSearchParameterLst() {
        if (serviceAndWorkSearchParameterLst == null) {
            serviceAndWorkSearchParameterLst = new ArrayList<>();
            serviceAndWorkSearchParameterLst = serviceBeanlocal.getParamNameList();
        }
        return serviceAndWorkSearchParameterLst;
    }

    public void setServiceAndWorkSearchParameterLst(List<PrmsServiceAndWorkReg> serviceAndWorkSearchParameterLst) {
        this.serviceAndWorkSearchParameterLst = serviceAndWorkSearchParameterLst;
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            serviceAndWorkReg.setColumnName(event.getNewValue().toString());
            serviceAndWorkReg.setColumnValue(null);
        }
    }

    public void onRowSelectServAndWork(SelectEvent event) {

        serviceAndWorkReg = (PrmsServiceAndWorkReg) event.getObject();
        checkAsRegTypeIsServ = serviceAndWorkReg.getRegisterationType();
        fmsGeneralLedger = serviceAndWorkReg.getGeneralLedgerId();
        if (serviceAndWorkReg.getRegisterationType() != null && serviceAndWorkReg.getRegisterationType().equals("service")) {
            checkAsdefaultConsultancy = serviceAndWorkReg.getServiceType();
            currentServOrWorkNo = serviceAndWorkReg.getServiceNo();
            hideWhenWork = true;
            hideWhenService = false;

        } else if (serviceAndWorkReg.getRegisterationType() != null && serviceAndWorkReg.getRegisterationType().equals("work")) {
            currentServOrWorkNo = serviceAndWorkReg.getWorkNo();
            hideWhenWork = false;
            hideWhenService = true;
        }
        renderPnlCreateServiceRegistration = true;
        renderPnlManPage = false;
        disableServiceAndWorkRdbtnWhenSearch = true;
        activeIndex = "0";
        saveorUpdateBundle = "Update";
        changeStatus = true;
        renderPnlToSearchPage = true;

        updateStatus = 1;

    }

    //Event Listener Method When We select Service or Work Radio Button
    public void changeListenerRegisType(ValueChangeEvent evented) {
        serviceAndWorkReg.setRegisterationType(checkAsRegTypeIsServ);
        if (evented.getNewValue() != null) {
            if (evented.getNewValue().toString().equalsIgnoreCase("service")) {
                setSetToService(true);
                setSetToWork(false);
                setHideWhenWork(true);
                setHideWhenService(false);
                serviceAndWorkReg.setRegisterationType("service");
            } else if (evented.getNewValue().toString().equalsIgnoreCase("work")) {
                setSetToService(false);
                setSetToWork(true);
                setHideWhenWork(false);
                setHideWhenService(true);
                serviceAndWorkReg.setRegisterationType("work");
            }
        }

    }

    //Event Listener Method When We select Service or Work Radio Button
    public void changedefaultConsultancy(ValueChangeEvent ev) {
        serviceAndWorkReg.setServiceType(checkAsdefaultConsultancy);
        if (ev.getNewValue() != null) {
            if (ev.getNewValue().toString().equalsIgnoreCase("non-consultancy")) {
                setRenderToConsultancy(false);
                setRenderToNonconsultancy(true);
                serviceAndWorkReg.setServiceType("non-consultancy");
            } else {
                setRenderToConsultancy(true);
                setRenderToNonconsultancy(false);
                serviceAndWorkReg.setServiceType("consultancy");
            }
        }
    }

    public void handleRegistrationDate(ValueChangeEvent event) throws ParseException {
        if (event.getNewValue() != null) {
            serviceAndWorkReg.setRegistrationDate(event.getNewValue().toString());

            DateFormat from = new SimpleDateFormat("dd/MM/yyyy"); // current format
            DateFormat to = new SimpleDateFormat("yyyy-MM-dd"); // wanted format

            serviceAndWorkReg.setRegistrationDate(to.format(from.parse(serviceAndWorkReg.getRegistrationDate())));
            dateWithTodayComparisonStatus = StringDateManipulation.compareWithToday(serviceAndWorkReg.getRegistrationDate());
            if (dateWithTodayComparisonStatus != 0) {
                JsfUtil.addFatalMessage("Registration Date Cannot be Before or After Todays Date");
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="other methods">
    //Auto Sequencial Generating Service or Work Number
    public String generateServOrWorkNo() {
        System.out.println("generating Service Or Work No==");
        if (saveorUpdateBundle.equals("Update")) {
            if (checkAsRegTypeIsServ.equalsIgnoreCase("service")) {
                currentServOrWorkNo = serviceAndWorkReg.getServiceNo();
            } else if (checkAsRegTypeIsServ.equalsIgnoreCase("work")) {
                currentServOrWorkNo = serviceAndWorkReg.getWorkNo();
            }
        } else {
            if (checkAsRegTypeIsServ != null) {
                currentServOrWorkNo = getSerOrWorkSeqNo(checkAsRegTypeIsServ);
            }
        }
        return currentServOrWorkNo;
    }

    public String getSerOrWorkSeqNo(String checkAsRegTypeIsServ) {
        String serv_Work_No = serviceBeanlocal.getServOrWorkSeqNo(checkAsRegTypeIsServ);
        return serv_Work_No;
    }

    //Search method
    public void searchServAndWorkInformation() {
        serviceAndWorkReg.setPreparedBy(workFlow.getUserAccount());
        allServicesInfoList = serviceBeanlocal.searchWorkInfoByWorkName(serviceAndWorkReg);
        recerateServiceRegSearchModel();
        serviceAndWorkReg = new PrmsServiceAndWorkReg();

    }

//to Reset a page that holds a data
    public String clearPage() {
        saveorUpdateBundle = "Save";
        createOrSearchBundle = "Search";
        icone = "ui-icon-search";
        updateStatus = 0;
        renderPnlToSearchPage = false;
        serviceAndWorkReg = new PrmsServiceAndWorkReg();
        checkAsRegTypeIsServ = "service";
        hideWhenService = false;
        hideWhenWork = true;
        prmsServiceAndWorkRegSearchInfoDataModel = null;
        fmsGeneralLedger = null;
        checkAsdefaultConsultancy = "consultancy";
        changeStatus = false;
        disableServiceAndWorkRdbtnWhenSearch = false;

        return null;
    }

    private void recerateServiceRegSearchModel() {
        prmsServiceAndWorkRegSearchInfoDataModel = null;
        prmsServiceAndWorkRegSearchInfoDataModel = new ListDataModel(new ArrayList<>(allServicesInfoList));
    }

    // to back to search page from view page 
    public void goBackSearchButtonAction() {
        renderPnlToSearchPage = false;
        renderPnlCreateServiceRegistration = false;
        renderPnlManPage = true;
    }

    //Page creation and Search renderer code
    public void createNewServiceRegInfo() {
        saveorUpdateBundle = "Save";
        renderPnlToSearchPage = false;
        if (createOrSearchBundle.equals("New")) {
            serviceAndWorkReg = new PrmsServiceAndWorkReg();
            fmsGeneralLedger = new FmsGeneralLedger();
            renderPnlCreateServiceRegistration = true;
            renderPnlManPage = false;
            disableServiceAndWorkRdbtnWhenSearch = false;
            hideWhenWork = true;
            hideWhenService = false;
            createOrSearchBundle = "Search";
            setIcone("ui-icon-search");
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateServiceRegistration = false;
            renderPnlManPage = true;
            createOrSearchBundle = "New";
            setIcone("ui-icon-plus");
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Save or Update method">
    //Save or Update method of Service or Work data to permanent storage 
    public String saveServAndWorkInfo() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBean.getUserName(), "saveServAndWorkInfo", dataset)) {

                if (dateWithTodayComparisonStatus == 0) {
                    if (updateStatus == 0) {

                        try {
                            generateServOrWorkNo();
                            if (checkAsRegTypeIsServ.equals("service")) {
                                serviceAndWorkReg.setServiceNo(currentServOrWorkNo);
                                serviceAndWorkReg.setWorkNo("");
                                serviceAndWorkReg.setServiceType(checkAsdefaultConsultancy);
                            } else if (checkAsRegTypeIsServ.equals("work")) {
                                serviceAndWorkReg.setWorkNo(currentServOrWorkNo);
                                serviceAndWorkReg.setServiceNo("");
                                serviceAndWorkReg.setServiceType("");
                            }
                            serviceAndWorkReg.setGeneralLedgerId(fmsGeneralLedger);
                            serviceAndWorkReg.setRegisterationType(checkAsRegTypeIsServ);
                            serviceAndWorkReg.setCurrentStatus(Constants.ACTIVE);
                            serviceAndWorkReg.setStatus(Constants.PREPARE_VALUE);
                            serviceAndWorkReg.setPreparedBy(workFlow.getUserAccount());
                            serviceBeanlocal.create(serviceAndWorkReg);
                            JsfUtil.addSuccessMessage("Service Information Successfully Registared ");
                            return clearPage();

                        } catch (Exception e) {
                            JsfUtil.addErrorMessage("Something Error Occured on Data Created");

                            return null;
                        }

                    } else {
                        try {
                            serviceAndWorkReg.setRegisterationType(checkAsRegTypeIsServ);
                            if (serviceAndWorkReg.getRegisterationType().equalsIgnoreCase("service")) {
                                serviceAndWorkReg.setServiceType(checkAsdefaultConsultancy);
                            } else if (serviceAndWorkReg.getRegisterationType().equalsIgnoreCase("work")) {
                                serviceAndWorkReg.setServiceType("");
                            }
                            serviceAndWorkReg.setStatus(Constants.UPDATED);
                            serviceAndWorkReg.setGeneralLedgerId(fmsGeneralLedger);

                            if (serviceAndWorkReg.getCurrentStatus().equals("1")) {
                                serviceAndWorkReg.setCurrentStatus(Constants.ACTIVE);
                            } else if (serviceAndWorkReg.getCurrentStatus().equals("0")) {
                                serviceAndWorkReg.setCurrentStatus(Constants.ACTIVE_REJECT_VALUE);
                            }
                            serviceBeanlocal.edit(serviceAndWorkReg);
                            if (serviceAndWorkReg.getRegisterationType().equalsIgnoreCase("service")) {

                                JsfUtil.addSuccessMessage("Service Information is Successfully Updated");
                            } else {

                                JsfUtil.addSuccessMessage("Work Information is Successfully Updated ");
                            }
                            return clearPage();
                        } catch (Exception e) {
                            JsfUtil.addErrorMessage("Something Error Occured on Data Updated");

                        }
                        return null;
                    }
                } else {
                    JsfUtil.addFatalMessage("Registration Date Cannot be Before or After Todays Date");
                }
            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBean.getSessionID());
                eventEntry.setUserId(sessionBean.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBean.getUserName());
                eventEntry.setUserLogin(test);

                // ..... add more information by calling fields defined in the object 
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return null;
    }
    //</editor-fold>
}
