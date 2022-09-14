/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.recruitment;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.organization.HrJobTypesBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrAdvertisementBeanLocal;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.lookup.HrLuMediaTypes;
import et.gov.eep.hrms.entity.recruitment.HrMedias;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Biniyam Mathewos
 */
@Named(value = "hrMentainAdvertisement")
@ViewScoped
public class HrMentainAdvertisementController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            HrRecruitmentRequests hrRecruitmentRequests;
    @Inject
            HrAdvertisements hrAdvertisements;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrAdvertisedJobs hrAdvertisedJobs;
    @Inject
            HrEmployees hrEmployees;
    @Inject
            HrLuMediaTypes hrLuMediaTypes;
    @Inject
            HrMedias hrMedias;
    @Inject
            HrMedias m2;
    @Inject
            SessionBean sessionBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="EJB Initialization">
    @EJB
            HrAdvertisementBeanLocal hrAdvertisementBeanLocal;
    @EJB
            HrJobTypesBeanLocal hrJobTypesBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="list and dataModel Intialization">
    ArrayList<HrRecruitmentRequests> hrRecruitmentRequestList = new ArrayList<>();
    ArrayList<HrAdvertisedJobs> advertisedJobsList = new ArrayList<>();
    private DataModel<HrEmployees> hrAdvertisedJobsModel;
    private DataModel<HrAdvertisements> advertizmentsDataModel;
    private DataModel<HrMedias> hrMediasModel;
    private List<HrEmployees> hrAdvertisedJobsList = new ArrayList<>();
    DataModel<HrAdvertisements> advertiseDataModel = new ListDataModel<>();
    Set<String> declaration = new HashSet<>();
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    String duplicattion = null;
    int update = 0;
    String saveorUpdate = "Save";
    String item;
    private String renderedMedia = "true";
    boolean btnaddvisibility = true;
    private String createOrSearchBundle = "Add New";
    private String icone = "ui-icon-plus";
    private String renderPnlMedia = "hidden";
    private boolean renderPnlCreateGatePass = true;
    private boolean renderPnlManPage = false;
    private boolean batchCode1 = true;
    private boolean batchCode2 = false;
    private String batchCode;
    private String disableBatchCode = "true";
    private boolean btnNewRender = false;
    private String AddOrModify = "Add";
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    @PostConstruct
    public void init() {
        hrAdvertisements.setAdvertType("Outside");
        advertiseDataModel = new ListDataModel(new ArrayList(hrAdvertisementBeanLocal.findAll()));
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="gette setter">
    public String getDisableBatchCode() {
        return disableBatchCode;
    }

    public void setDisableBatchCode(String disableBatchCode) {
        this.disableBatchCode = disableBatchCode;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public String getAddOrModify() {
        return AddOrModify;
    }

    public void setAddOrModify(String AddOrModify) {
        this.AddOrModify = AddOrModify;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public DataModel<HrAdvertisements> getAdvertiseDataModel() {
        return advertiseDataModel;
    }

    public void setAdvertiseDataModel(DataModel<HrAdvertisements> advertiseDataModel) {
        this.advertiseDataModel = advertiseDataModel;
    }

    public String getRenderedMedia() {
        return renderedMedia;
    }

    public void setRenderedMedia(String renderedMedia) {
        this.renderedMedia = renderedMedia;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    /**
     *
     */
    public HrMentainAdvertisementController() {
    }

    /**
     *
     * @return
     */
    public List<String> getBatchCodeList() {
        return hrAdvertisementBeanLocal.distinctbatchCode();
    }

    public String getSaveorUpdate() {
        return saveorUpdate;
    }

    public void setSaveorUpdate(String saveorUpdate) {
        this.saveorUpdate = saveorUpdate;
    }

    public DataModel<HrAdvertisements> getAdvertizmentsDataModel() {
        if (advertizmentsDataModel == null) {
            advertizmentsDataModel = new ListDataModel<>();
        }
        return advertizmentsDataModel;
    }

    public void setAdvertizmentsDataModel(DataModel<HrAdvertisements> advertizmentsDataModel) {
        this.advertizmentsDataModel = advertizmentsDataModel;
    }

    public ArrayList<HrAdvertisedJobs> getAdvertisedJobsList() {
        return advertisedJobsList;
    }

    public void setAdvertisedJobsList(ArrayList<HrAdvertisedJobs> advertisedJobsList) {
        this.advertisedJobsList = advertisedJobsList;
    }

    public ArrayList<HrLuMediaTypes> findhrluMedias() {
        return hrAdvertisementBeanLocal.findhrluMedias();
    }

    /**
     *
     * @return
     */
    public HrRecruitmentRequests getHrRecruitmentRequests() {
        if (hrRecruitmentRequests == null) {
            hrRecruitmentRequests = new HrRecruitmentRequests();
        }
        return hrRecruitmentRequests;
    }

    /**
     *
     * @param hrRecruitmentRequests
     */
    public void setHrRecruitmentRequests(HrRecruitmentRequests hrRecruitmentRequests) {
        this.hrRecruitmentRequests = hrRecruitmentRequests;
    }

    /**
     *
     * @return
     */
    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    /**
     *
     * @param hrJobTypes
     */
    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    /**
     *
     * @return
     */
    public HrAdvertisements getHrAdvertisements() {
        if (hrAdvertisements == null) {
            hrAdvertisements = new HrAdvertisements();
        }
        return hrAdvertisements;
    }

    /**
     *
     * @param hrAdvertisements
     */
    public void setHrAdvertisements(HrAdvertisements hrAdvertisements) {
        this.hrAdvertisements = hrAdvertisements;
    }

    /**
     *
     * @return
     */
    public HrEmployees getHrEmployees() {
        if (hrEmployees == null) {
            hrEmployees = new HrEmployees();
        }
        return hrEmployees;
    }

    /**
     *
     * @param hrEmployees
     */
    public void setHrEmployees(HrEmployees hrEmployees) {
        this.hrEmployees = hrEmployees;
    }

    /**
     *
     * @return
     */
    public HrLuMediaTypes getHrLuMediaTypes() {
        if (hrLuMediaTypes == null) {
            hrLuMediaTypes = new HrLuMediaTypes();
        }
        return hrLuMediaTypes;
    }

    /**
     *
     * @param hrLuMediaTypes
     */
    public void setHrLuMediaTypes(HrLuMediaTypes hrLuMediaTypes) {
        this.hrLuMediaTypes = hrLuMediaTypes;
    }

    /**
     *
     * @return
     */
    public DataModel<HrEmployees> getHrAdvertisedJobsModel() {
        if (hrAdvertisedJobsModel == null) {
            hrAdvertisedJobsModel = new ListDataModel<>();
        }
        return hrAdvertisedJobsModel;
    }

    /**
     *
     * @param hrAdvertisedJobsModel
     */
    public void setHrAdvertisedJobsModel(DataModel<HrEmployees> hrAdvertisedJobsModel) {
        this.hrAdvertisedJobsModel = hrAdvertisedJobsModel;
    }

    /**
     *
     */
    public void recreatehrRecruitmentRequests() {
        hrAdvertisedJobsModel = null;
        hrAdvertisedJobsModel = new ListDataModel(new ArrayList<>(hrAdvertisedJobsList));
    }

    /**
     *
     * @return
     */
    public HrAdvertisedJobs getHrAdvertisedJobs() {
        return hrAdvertisedJobs;
    }

    /**
     *
     * @param hrAdvertisedJobs
     */
    public void setHrAdvertisedJobs(HrAdvertisedJobs hrAdvertisedJobs) {
        this.hrAdvertisedJobs = hrAdvertisedJobs;
    }

    /**
     *
     * @return
     */
    @XmlTransient
    public List<HrEmployees> getHrAdvertisedJobsList() {
        if (hrAdvertisedJobsList == null) {
            hrAdvertisedJobsList = new ArrayList<>();
        }
        return hrAdvertisedJobsList;
    }

    /**
     *
     * @param hrAdvertisedJobsList
     */
    public void setHrAdvertisedJobsList(List<HrEmployees> hrAdvertisedJobsList) {
        this.hrAdvertisedJobsList = hrAdvertisedJobsList;
    }

    //Media Type tb
    /**
     *
     * @return
     */
    public HrMedias getHrMedias() {
        if (hrMedias == null) {
            hrMedias = new HrMedias();
        }
        return hrMedias;
    }

    /**
     *
     * @param hrMedias
     */
    public void setHrMedias(HrMedias hrMedias) {
        this.hrMedias = hrMedias;
    }

    public void saveAdvJobs() {

    }

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isRenderPnlCreateGatePass() {
        return renderPnlCreateGatePass;
    }

    public void setRenderPnlCreateGatePass(boolean renderPnlCreateGatePass) {
        this.renderPnlCreateGatePass = renderPnlCreateGatePass;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String isRenderPnlMedia() {
        return renderPnlMedia;
    }

    public void setRenderPnlMedia(String renderPnlMedia) {
        this.renderPnlMedia = renderPnlMedia;
    }

    public boolean isBatchCode1() {
        return batchCode1;
    }

    public void setBatchCode1(boolean batchCode1) {
        this.batchCode1 = batchCode1;
    }

    public boolean isBatchCode2() {
        return batchCode2;
    }

    public void setBatchCode2(boolean batchCode2) {
        this.batchCode2 = batchCode2;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    /**
     *
     */
    String startDate;
    String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    StringDateManipulation stringDateManipulation = new StringDateManipulation();

    /**
     *
     * @return
     */
    public DataModel<HrMedias> getHrMediasModel() {
        if (hrMediasModel == null) {
            hrMediasModel = new ListDataModel<>();
        }
        return hrMediasModel;
    }

    /**
     *
     * @param hrMediasModel
     */
    public void setHrMediasModel(DataModel<HrMedias> hrMediasModel) {
        this.hrMediasModel = hrMediasModel;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void newButtonAction() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
        createOrSearchBundle = "Search";
        disableBatchCode = "false";
        setIcone("ui-icon-search");
        btnNewRender = false;
        Clear();
    }

    public void addMediaToAdvert() {
        if (declaration.contains(hrLuMediaTypes.getMediaType())) {
            JsfUtil.addFatalMessage("It is already exist!");
        } else {
            m2 = null;
            m2 = new HrMedias();
            m2 = hrMedias;
            hrAdvertisements.addToHrMedias(m2);
            declaration.add(hrLuMediaTypes.getMediaType());
        }
        recreatehrMediaType();
        CleanMediaTypeTb();
        hrLuMediaTypes = null;
        AddOrModify = "Add";
    }

    String selectedType;

    public void vlcRenderedMedia(HrAdvertisements adv) {
        if (adv.getAdvertType().equalsIgnoreCase("Inside")) {
            setRenderedMedia("false");
        } else {
            setRenderedMedia("true");
        }
    }

    public void vlcRenderedMedia(ValueChangeEvent event) {
        String selectedType = event.getNewValue().toString();
        if (selectedType.equalsIgnoreCase("Inside")) {
            setRenderedMedia("false");
        } else {
            setRenderedMedia("true");
        }
    }

    public void batchCodeChangedListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrAdvertisedJobsList = new ArrayList<>();
            String batchCode = event.getNewValue().toString();
            List<Object[]> depList = hrAdvertisementBeanLocal.readApprovedRecruitments(batchCode);
            HrEmployees advJob;
            for (Object[] res : depList) {
                advJob = new HrEmployees();
                advJob.setFirstName(res[0].toString());  //JOB_DESCRIPTION
                advJob.setHeight(Integer.parseInt(res[1].toString()));  //NUM_OF_EMPS_APPROVED
                advJob.setWeight(Integer.parseInt(res[2].toString()));  //NUM_OF_REQ
                advJob.setMiddleName(res[3].toString());  //RECRUITMENT_TYPE
                hrJobTypes.setId(Integer.parseInt(res[4].toString()));  //JOB_ID
                advJob.setJobId(hrJobTypes);
                advJob.setEmpId(res[5].toString());  //RECRUIT_REQUEST_ID
                advJob.setLastName(res[6].toString());   //DEP_NAME
                hrAdvertisedJobsList.add(advJob);
            }
        }
        recreatehrRecruitmentRequests();
    }

    public void handleBatchCodeEvent(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            String batchCode = event.getNewValue().toString();
            handleBatchCode(batchCode);
            hrAdvertisements.setBatchCode(batchCode);
        }
    }

    public void handleBatchCode(String batchCode) {
        try {
            hrAdvertisedJobsList = new ArrayList<>();
            advertisedJobsList = new ArrayList<>();
            List<Object[]> depList = hrAdvertisementBeanLocal.readApprovedRecruitments(batchCode);
            HrEmployees advJob;
            for (Object[] res : depList) {
                hrAdvertisedJobs = new HrAdvertisedJobs();
                advJob = new HrEmployees();
                advJob.setFirstName(res[0].toString());  //JOB_DESCRIPTION
                advJob.setHeight(Integer.parseInt(res[1].toString()));  //NUM_OF_EMPS_APPROVED
                advJob.setWeight(Integer.parseInt(res[2].toString()));  //NUM_OF_REQ
                advJob.setMiddleName(res[3].toString());  //RECRUITMENT_TYPE
                hrJobTypes.setId(Integer.parseInt(res[4].toString()));  //JOB_ID
                hrJobTypes = hrJobTypesBeanLocal.searchJobTypesById(Integer.parseInt(res[4].toString()));
                advJob.setJobId(hrJobTypes);
                advJob.setEmpId(res[5].toString());  //RECRUIT_REQUEST_ID
                advJob.setLastName(res[6].toString());   //DEP_NAME
                hrAdvertisedJobsList.add(advJob);
                hrAdvertisedJobs.setJobId(hrJobTypes);
                hrAdvertisedJobs.setAdvertId(hrAdvertisements);
                hrAdvertisedJobs.setEmpNeeded(res[1].toString());
                hrAdvertisedJobs.setRecruitmentType(res[3].toString());
                hrAdvertisedJobs.setRecruitRequestId(res[5].toString());
                hrAdvertisements.getHrAdvertisedJobsList().add(hrAdvertisedJobs);
                recreatehrRecruitmentRequests();
                hrAdvertisedJobs = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void batchCode(String batchCode) {
        try {
            hrAdvertisedJobsList = new ArrayList<>();
            List<Object[]> list = hrAdvertisementBeanLocal.readApprovedRecruitments(batchCode);
            HrEmployees advJob;
            for (Object[] res : list) {
                hrAdvertisedJobs = new HrAdvertisedJobs();
                advJob = new HrEmployees();
                advJob.setFirstName(res[0].toString());
                advJob.setHeight(Integer.parseInt(res[1].toString()));
                advJob.setWeight(Integer.parseInt(res[2].toString()));
                advJob.setMiddleName(res[3].toString());
                hrJobTypes = hrJobTypesBeanLocal.searchJobTypesById(Integer.parseInt(res[4].toString()));
                advJob.setJobId(hrJobTypes);
                advJob.setEmpId(res[5].toString());
                advJob.setLastName(res[6].toString());
                hrAdvertisedJobsList.add(advJob);
                hrAdvertisedJobs = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void recreatehrMediaType() {
        hrMediasModel = null;
        hrMediasModel = new ListDataModel(new ArrayList(hrAdvertisements.getHrMediasList()));
        declaration.removeAll(declaration);
        List<HrMedias> listMedia = new ArrayList(hrAdvertisements.getHrMediasList());
        listMedia.stream().forEach((media) -> {
            if (media.getMediaTypeId() != null) {
                declaration.add(media.getMediaTypeId().getMediaType());
            }
        });
    }

    private void CleanMediaTypeTb() {
        hrMedias = null;
    }

    public void updateEmpSkill() {
        hrMedias = getHrMediasModel().getRowData();

    }
    int selectedRow;

    public void editDataTable(SelectEvent event) {
        hrMedias = (HrMedias) event.getObject();
        selectedRow = hrMediasModel.getRowIndex();
        hrLuMediaTypes = hrMedias.getMediaTypeId();
        AddOrModify = "Modify";
    }

    public void Clear() {
        hrMedias = new HrMedias();
        hrAdvertisedJobs = new HrAdvertisedJobs();
        hrAdvertisements = new HrAdvertisements();
        hrAdvertisedJobsModel = new ListDataModel<>();
        hrMediasModel = new ListDataModel<>();
        batchCode = null;
        disableBatchCode = "false";
        update = 0;
        saveorUpdate = "Save";
        batchCode1 = true;
        batchCode2 = false;
    }

    public void changeMediatype(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            hrLuMediaTypes.setMediaType(event.getNewValue().toString());
            hrLuMediaTypes = hrAdvertisementBeanLocal.findByMediaName(hrLuMediaTypes);
            hrMedias.setMediaTypeId(hrLuMediaTypes);
        }
    }

    public void createNewGatePassInfo() {
        saveorUpdate = "Save";
        if (createOrSearchBundle.equals("Add New")) {
            renderPnlCreateGatePass = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Search";
            disableBatchCode = "false";
            setIcone("ui-icon-search");
            btnNewRender = false;
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCreateGatePass = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Add New";
            setIcone("ui-icon-plus");
            btnNewRender = false;
        }
    }

    public ArrayList<String> type() {
        ArrayList<String> annType = new ArrayList<>();
        annType.add("Inside");
        annType.add("Outside");
        return annType;
    }

    public SelectItem[] getType() {
        return JsfUtil.getSelectItems(type(), true);
    }

    public void recruitmentTypeChangedListener(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            hrAdvertisedJobsList = new ArrayList<>();
            String recruitmentType = event.getNewValue().toString();
            if (recruitmentType.equalsIgnoreCase("Inside")) {
                renderPnlMedia = "hidden";
            } else {
                renderPnlMedia = "display";
            }
        }
    }

    public void dateController() {
        if ((hrAdvertisements.getAdvertDateFrom() != null) && (hrAdvertisements.getAdvertDateFrom().compareTo("") != 0)
                && (hrAdvertisements.getAdvertDateFrom() != null) && (hrAdvertisements.getAdvertDateTo().compareTo("") != 0)) {
            if (hrAdvertisements.getAdvertDateTo().contains("/") && hrAdvertisements.getAdvertDateTo().contains("/")) {
                String[] satrtDateFromUI = hrAdvertisements.getAdvertDateFrom().split("/");
                String[] endDateFromUI = hrAdvertisements.getAdvertDateTo().split("/");
                startDate = hrAdvertisements.getAdvertDateFrom();
                endDate = hrAdvertisements.getAdvertDateTo();
                startDate = satrtDateFromUI[2] + "-" + satrtDateFromUI[1] + "-" + satrtDateFromUI[0];
                endDate = endDateFromUI[2] + "-" + endDateFromUI[1] + "-" + endDateFromUI[0];
                int calculatedDates = stringDateManipulation.datesDifferenceInDays(endDate, startDate);
                if (calculatedDates >= 0) {
                    saveMaintainAdvertisement();
                } else {
                    JsfUtil.addFatalMessage("Advert Dates Must Be Minimum One Day");
                }
            }
        }
    }

    String dateFrom;
    String dateTo;
    int calculatedDates = 0;

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public int getCalculatedDates() {
        return calculatedDates;
    }

    public void setCalculatedDates(int calculatedDates) {
        this.calculatedDates = calculatedDates;
    }

    public void AnnounceDatecontroller() {
        if ((hrAdvertisements.getAdvertDateFrom() != null && hrAdvertisements.getAdvertDateFrom().compareTo("") != 0)
                && (hrAdvertisements.getAdvertDateTo() != null && hrAdvertisements.getAdvertDateTo().compareTo("") != 0)) {
            if (hrAdvertisements.getAdvertDateTo().contains("/")) {
                String[] startDateFromUI = hrAdvertisements.getAdvertDateFrom().split("/");
                String[] endDateFromUI = hrAdvertisements.getAdvertDateTo().split("/");
                dateFrom = startDateFromUI[2] + "-" + startDateFromUI[1] + "-" + startDateFromUI[0];
                dateTo = endDateFromUI[2] + "-" + endDateFromUI[1] + "-" + endDateFromUI[0];
                saveMaintainAdvertisement();
            } else {
                saveMaintainAdvertisement();
            }
        }
    }

    int dateDifference;

    public void dateValidation() {
        String startMonth[] = hrAdvertisements.getAdvertDateFrom().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrAdvertisements.getAdvertDateFrom().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String strtDate[] = hrAdvertisements.getAdvertDateFrom().split("/");
        int isStartDate = Integer.parseInt(strtDate[0]);
        String endDay[] = hrAdvertisements.getAdvertDateTo().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrAdvertisements.getAdvertDateTo().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrAdvertisements.getAdvertDateTo().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        dateDifference = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }

    public void saveMaintainAdvertisement() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveMaintainAdvertisement", dataset)) {

                dateValidation();
                if (dateDifference < 0) {
                    JsfUtil.addFatalMessage("End date should be greater than Start date. Try again!");
                } else {
                    if (update == 0) {
                        hrAdvertisements.setBatchCode(hrRecruitmentRequests.getBatchCode());
                        hrAdvertisementBeanLocal.save(hrAdvertisements);
                        hrAdvertisementBeanLocal.findByBatchCodeAndEdit(hrRecruitmentRequests);
                        JsfUtil.addSuccessMessage("Successfullly saved");
                        Clear();
                    } else {
                        hrAdvertisementBeanLocal.edit(hrAdvertisements);
                        JsfUtil.addSuccessMessage("Successfullly updated");
                        Clear();
                    }
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> userName = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(userName);
                JAXBElement<String> module = new JAXBElement<String>(qualifiedName, String.class, null, "HRMS");
                eventEntry.setModule(module);
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveMaintainAdvertisement");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage("Error occurs while saving");
        }
    }

    public void populateMentainAdvertisement(SelectEvent event) {
        hrAdvertisements = (HrAdvertisements) event.getObject();
        vlcRenderedMedia(hrAdvertisements);
        getBatchCodeList();
        batchCode(hrAdvertisements.getBatchCode());
        recreatehrRecruitmentRequests();
        recreatehrMediaType();
        batchCode = hrAdvertisements.getBatchCode();
        btnaddvisibility = false;
        renderPnlCreateGatePass = false;
        disableBatchCode = "true";
        renderPnlManPage = true;
        batchCode1 = false;
        batchCode2 = true;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        update = 1;
        saveorUpdate = "Update";
        btnNewRender = true;
    }

    //<editor-fold defaultstate="collapsed" desc="popup">
    int popupUpdateStatus = 0;
    private String popupSaveOrUpdate = "Save";

    public String getPopupSaveOrUpdate() {
        return popupSaveOrUpdate;
    }

    public void setPopupSaveOrUpdate(String popupSaveOrUpdate) {
        this.popupSaveOrUpdate = popupSaveOrUpdate;
    }

    public void saveMediaType() {
        boolean checkDuplication = hrAdvertisementBeanLocal.isMediaTypeExist(hrLuMediaTypes);
        if (popupUpdateStatus == 0 && checkDuplication == false) {
            hrAdvertisementBeanLocal.save(hrLuMediaTypes);
            JsfUtil.addSuccessMessage("Successfully saved");
            hrLuMediaTypes = new HrLuMediaTypes();
        } else if (popupUpdateStatus == 0 && checkDuplication == true) {
            JsfUtil.addFatalMessage("Media type " + hrLuMediaTypes.getMediaType() + "  is already registered in the database");
        } else {
            hrAdvertisementBeanLocal.edit(hrLuMediaTypes);
            JsfUtil.addSuccessMessage("Successfully update");
            hrLuMediaTypes = new HrLuMediaTypes();
        }
        hrAdvertisementBeanLocal.findall();
        popupSaveOrUpdate = "Save";
        popupUpdateStatus = 0;
    }

    public ArrayList<HrLuMediaTypes> searchByMediaType(String mediaType) {
        ArrayList<HrLuMediaTypes> mediaTypeList = null;
        hrLuMediaTypes.setMediaType(mediaType);
        mediaTypeList = hrAdvertisementBeanLocal.searchByMediaType(hrLuMediaTypes);
        popupSaveOrUpdate = "Update";
        popupUpdateStatus = 1;
        return mediaTypeList;
    }

    public void getByMediaType(SelectEvent event) {
        try {
            hrLuMediaTypes.setMediaType(event.getObject().toString());
            hrLuMediaTypes = hrAdvertisementBeanLocal.getByMediaType(hrLuMediaTypes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void btnCancel() {
        hrLuMediaTypes = new HrLuMediaTypes();
        popupUpdateStatus = 0;
        popupSaveOrUpdate = "Save";
    }
    //</editor-fold>
//</editor-fold>
}
