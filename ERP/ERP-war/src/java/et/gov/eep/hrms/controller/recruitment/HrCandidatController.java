/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.recruitment;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.lookup.HrLuNationalityBeanLocal;
import et.gov.eep.hrms.businessLogic.recruitment.HrCandidateRegistrationBeanLocal;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.lookup.HrLuLanguages;
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrCandidateTrainings;
import et.gov.eep.hrms.entity.recruitment.HrCandidiateEducations;
import et.gov.eep.hrms.entity.recruitment.HrCandidateEmpHistories;
import et.gov.eep.hrms.entity.recruitment.HrCandidateLanguages;
import et.gov.eep.hrms.entity.recruitment.HrCandidateReferences;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import et.gov.eep.hrms.integration.HrAddressesBeanIntegrationLocal;
import et.gov.eep.hrms.mapper.recruitment.HrCandidiatesFacade;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author user
 */
@Named(value = "hrCandidatController")
@ViewScoped
public class HrCandidatController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity Injection">
    @Inject
            ColumnNameResolver columnNameResolver;
    @Inject
            HrCandidiates hrCandidiates;
    @Inject
            HrAdvertisements hrAdvertisements;
    @Inject
            HrAdvertisedJobs hrAdvertisedJobs;
    @Inject
            HrJobTypes hrJobTypes;
    @Inject
            HrAddresses hrAddresses;
    @Inject
            HrAddresses hrAddressesTrain;
    @Inject
            HrLuNationalities hrLuNationalities;
    @Inject
            HrCandidateTrainings hrCandidateTrainings;
    @Inject
            HrCandidateEmpHistories hrCandidateEmpHistories;
    @Inject
            HrCandidateLanguages hrCandidateLanguages;
    @Inject
            HrCandidateReferences hrCandidateReferences;
    @Inject
            HrLuEducTypes hrLuEducTypes;
    @Inject
            HrLuEducLevels hrLuEducLevels;
    @Inject
            HrCandidiateEducations hrCandidiateEducations;
    @Inject
            HrLuLanguages hrLuLanguages;
    @Inject
            SessionBean sessionBeanLocal;
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="EJB Declaration">
    
    @EJB
            HrCandidiatesFacade hrCandidiatesFaced;
    @EJB
            HrLuNationalityBeanLocal hrLuNationalityBeanLocal;
    @EJB
            HrCandidateRegistrationBeanLocal hrCandidateRegistrationBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List and DataModel declaration">
    List<HrCandidiates> candidateList = new ArrayList<>();
    List<HrAdvertisements> batchCodes;
    private List<HrAdvertisedJobs> jobTypes;
    DataModel<HrCandidiateEducations> hrCandidateEducationsDataModel = new ListDataModel<>();
    DataModel<HrCandidateTrainings> hrCandidateTrainingsDataModel = new ListDataModel<>();
    DataModel<HrCandidateEmpHistories> hrCandidateEmpHistoriesDataModel = new ListDataModel<>();
    DataModel<HrCandidateLanguages> hrCandidateLanguagesDataModel = new ListDataModel<>();
    DataModel<HrCandidateReferences> hrCandidateReferencesDataModel = new ListDataModel<>();
    DataModel<HrCandidiates> candidiatesListDataModel;
    StringDateManipulation stringDateManipulation = new StringDateManipulation();
    List<ColumnNameResolver> ColumnNameResolverList = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variable declaration">
    
    Set<String> declaration = new HashSet<>();
    Set<String> typecheck = new HashSet<>();
    int selectedRowIndexEducation = -1;
    int updateStatusEduc = 0;
    String duplicattion = null;
    private String createOrSearchBundle = "Add New";
    private String headerTitle = "Search Candidate";
    private String icone = "ui-icon-plus";
    private boolean disableBtnCreate;
    private boolean renderPnlCandidateForm = false;
    private boolean renderPnlManPage = true;
    private String tab = "disabled";
    private String preTab = "active";
    private String tabToggle = "";
    private String SaveOrUpdateButton = "Save";
    private boolean btnNewRender = false;
    int Update = 0;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Post construct">
    @PostConstruct
    public void init() {
        loadTree();
        hrCandidiates.setSex("Male");
        setBatchCodes(hrCandidateRegistrationBeanLocal.batchCodes("Outside"));
    }
//</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter and Setter"> 
    public HrJobTypes getHrJobTypes() {
        if (hrJobTypes == null) {
            hrJobTypes = new HrJobTypes();
        }
        return hrJobTypes;
    }

    public void setHrJobTypes(HrJobTypes hrJobTypes) {
        this.hrJobTypes = hrJobTypes;
    }

    public ColumnNameResolver getColumnNameResolver() {
        return columnNameResolver;
    }

    public void setColumnNameResolver(ColumnNameResolver columnNameResolver) {
        this.columnNameResolver = columnNameResolver;
    }

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public HrCandidatController() {
    }

    public List<HrAdvertisements> getBatchCodes() {
        return batchCodes;
    }

    public void setBatchCodes(List<HrAdvertisements> batchCodes) {
        this.batchCodes = batchCodes;
    }

    public List<HrAdvertisedJobs> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(List<HrAdvertisedJobs> jobTypes) {
        this.jobTypes = jobTypes;
    }

    public HrCandidiates getHrCandidiates() {
        if (hrCandidiates == null) {
            hrCandidiates = new HrCandidiates();
        }
        return hrCandidiates;
    }

    public void setHrCandidiates(HrCandidiates hrCandidiates) {
        this.hrCandidiates = hrCandidiates;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getTabToggle() {
        return tabToggle;
    }

    public void setTabToggle(String tabToggle) {
        this.tabToggle = tabToggle;
    }

    public HrAdvertisements getHrAdvertisements() {
        if (hrAdvertisements == null) {
            hrAdvertisements = new HrAdvertisements();
        }
        return hrAdvertisements;
    }

    public void setHrAdvertisements(HrAdvertisements hrAdvertisements) {
        this.hrAdvertisements = hrAdvertisements;
    }

    public HrAdvertisedJobs getHrAdvertisedJobs() {
        if (hrAdvertisedJobs == null) {
            hrAdvertisedJobs = new HrAdvertisedJobs();
        }
        return hrAdvertisedJobs;
    }

    public void setHrAdvertisedJobs(HrAdvertisedJobs hrAdvertisedJobs) {
        this.hrAdvertisedJobs = hrAdvertisedJobs;
    }

    public HrCandidateEmpHistories getHrCandidateEmpHistories() {
        if (hrCandidateEmpHistories == null) {
            hrCandidateEmpHistories = new HrCandidateEmpHistories();
        }
        return hrCandidateEmpHistories;
    }

    public void setHrCandidateEmpHistories(HrCandidateEmpHistories hrCandidateEmpHistories) {
        this.hrCandidateEmpHistories = hrCandidateEmpHistories;
    }

    public HrLuLanguages getHrLuLanguages() {
        if (hrLuLanguages == null) {
            hrLuLanguages = new HrLuLanguages();
        }
        return hrLuLanguages;
    }

    public void setHrLuLanguages(HrLuLanguages hrLuLanguages) {
        this.hrLuLanguages = hrLuLanguages;
    }

    public HrCandidateLanguages getHrCandidateLanguages() {
        if (hrCandidateLanguages == null) {
            hrCandidateLanguages = new HrCandidateLanguages();
        }
        return hrCandidateLanguages;
    }

    public void setHrCandidateLanguages(HrCandidateLanguages hrCandidateLanguages) {
        this.hrCandidateLanguages = hrCandidateLanguages;
    }

    public HrCandidateReferences getHrCandidateReferences() {
        if (hrCandidateReferences == null) {
            hrCandidateReferences = new HrCandidateReferences();
        }
        return hrCandidateReferences;
    }

    public void setHrCandidateReferences(HrCandidateReferences hrCandidateReferences) {
        this.hrCandidateReferences = hrCandidateReferences;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public HrAddresses getHrAddresses() {
        if (hrAddresses == null) {
            hrAddresses = new HrAddresses();
        }
        return hrAddresses;
    }

    public void setHrAddresses(HrAddresses hrAddresses) {
        this.hrAddresses = hrAddresses;
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

    public String getPreTab() {
        return preTab;
    }

    public void setPreTab(String preTab) {
        this.preTab = preTab;
    }

    public HrLuNationalities getHrLuNationalities() {
        if (hrLuNationalities == null) {
            hrLuNationalities = new HrLuNationalities();
        }
        return hrLuNationalities;
    }

    public void setHrLuNationalities(HrLuNationalities hrLuNationalities) {
        this.hrLuNationalities = hrLuNationalities;
    }

    public List<HrCandidiates> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(List<HrCandidiates> candidateList) {
        this.candidateList = candidateList;
    }

    public HrLuEducLevels getHrLuEducLevels() {
        if (hrLuEducLevels == null) {
            hrLuEducLevels = new HrLuEducLevels();
        }
        return hrLuEducLevels;
    }

    public void setHrLuEducLevels(HrLuEducLevels hrLuEducLevels) {
        this.hrLuEducLevels = hrLuEducLevels;
    }

    public HrLuEducTypes getHrLuEducTypes() {
        if (hrLuEducTypes == null) {
            hrLuEducTypes = new HrLuEducTypes();
        }
        return hrLuEducTypes;
    }

    public void setHrLuEducTypes(HrLuEducTypes hrLuEducTypes) {
        this.hrLuEducTypes = hrLuEducTypes;
    }

    public HrCandidiateEducations getHrCandidiateEducations() {
        if (hrCandidiateEducations == null) {
            hrCandidiateEducations = new HrCandidiateEducations();
        }
        return hrCandidiateEducations;
    }

    public void setHrCandidiateEducations(HrCandidiateEducations hrCandidiateEducations) {
        this.hrCandidiateEducations = hrCandidiateEducations;
    }

    public int getSelectedRowIndexEducation() {
        return selectedRowIndexEducation;
    }

    public void setSelectedRowIndexEducation(int selectedRowIndexEducation) {
        this.selectedRowIndexEducation = selectedRowIndexEducation;
    }

    public int getUpdateStatusEduc() {
        return updateStatusEduc;
    }

    public void setUpdateStatusEduc(int updateStatusEduc) {
        this.updateStatusEduc = updateStatusEduc;
    }

    public SelectItem[] getListOfNantionality() {
        return JsfUtil.getSelectItems(hrLuNationalityBeanLocal.findAll(), true);
    }

    public SelectItem[] getEducationTypes() {
        return JsfUtil.getSelectItems(hrCandidateRegistrationBeanLocal.findEducationTypes(), true);
    }

    public SelectItem[] getEducationLeves() {
        return JsfUtil.getSelectItems(hrCandidateRegistrationBeanLocal.findEducationLeves(), true);
    }

    public DataModel<HrCandidiateEducations> getHrCandidateEducationsDataModel() {
        return hrCandidateEducationsDataModel;
    }

    public void setHrCandidateEducationsDataModel(DataModel<HrCandidiateEducations> hrCandidateEducationsDataModel) {
        this.hrCandidateEducationsDataModel = hrCandidateEducationsDataModel;
    }

    public HrCandidateTrainings getHrCandidateTrainings() {
        if (hrCandidateTrainings == null) {
            hrCandidateTrainings = new HrCandidateTrainings();
        }
        return hrCandidateTrainings;
    }

    public void setHrCandidateTrainings(HrCandidateTrainings hrCandidateTrainings) {
        this.hrCandidateTrainings = hrCandidateTrainings;
    }

    public DataModel<HrCandidateTrainings> getHrCandidateTrainingsDataModel() {
        return hrCandidateTrainingsDataModel;
    }

    public void setHrCandidateTrainingsDataModel(DataModel<HrCandidateTrainings> hrCandidateTrainingsDataModel) {
        this.hrCandidateTrainingsDataModel = hrCandidateTrainingsDataModel;
    }

    public SelectItem[] getListOfLanguages() {
        return JsfUtil.getSelectItems(hrCandidateRegistrationBeanLocal.findAllHrLuLanguages(), true);
    }

    public HrAddresses getHrAddressesTrain() {
        if (hrAddressesTrain == null) {
            hrAddressesTrain = new HrAddresses();
        }
        return hrAddressesTrain;
    }

    public void setHrAddressesTrain(HrAddresses hrAddressesTrain) {
        this.hrAddressesTrain = hrAddressesTrain;
    }

    public DataModel<HrCandidateEmpHistories> getHrCandidateEmpHistoriesDataModel() {
        return hrCandidateEmpHistoriesDataModel;
    }

    public void setHrCandidateEmpHistoriesDataModel(DataModel<HrCandidateEmpHistories> hrCandidateEmpHistoriesDataModel) {
        this.hrCandidateEmpHistoriesDataModel = hrCandidateEmpHistoriesDataModel;
    }

    public DataModel<HrCandidateLanguages> getHrCandidateLanguagesDataModel() {
        return hrCandidateLanguagesDataModel;
    }

    public void setHrCandidateLanguagesDataModel(DataModel<HrCandidateLanguages> hrCandidateLanguagesDataModel) {
        this.hrCandidateLanguagesDataModel = hrCandidateLanguagesDataModel;
    }

    public DataModel<HrCandidateReferences> getHrCandidateReferencesDataModel() {
        return hrCandidateReferencesDataModel;
    }

    public void setHrCandidateReferencesDataModel(DataModel<HrCandidateReferences> hrCandidateReferencesDataModel) {
        this.hrCandidateReferencesDataModel = hrCandidateReferencesDataModel;
    }

    public List<ColumnNameResolver> getColumnNameResolverList() {
        ColumnNameResolverList = hrCandidateRegistrationBeanLocal.findColumns();
        if (ColumnNameResolverList == null) {
            ColumnNameResolverList = new ArrayList<>();
        }
        return ColumnNameResolverList;
    }

    public void setColumnNameResolverList(List<ColumnNameResolver> ColumnNameResolverList) {
        this.ColumnNameResolverList = ColumnNameResolverList;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
    }

    public boolean isRenderPnlCandidateForm() {
        return renderPnlCandidateForm;
    }

    public void setRenderPnlCandidateForm(boolean renderPnlCandidateForm) {
        this.renderPnlCandidateForm = renderPnlCandidateForm;
    }

    public boolean isRenderPnlManPage() {
        return renderPnlManPage;
    }

    public void setRenderPnlManPage(boolean renderPnlManPage) {
        this.renderPnlManPage = renderPnlManPage;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public DataModel<HrCandidiates> getCandidiatesListDataModel() {
        return candidiatesListDataModel;
    }

    public void setCandidiatesListDataModel(DataModel<HrCandidiates> candidiatesListDataModel) {
        this.candidiatesListDataModel = candidiatesListDataModel;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Main methods">
    public void createNewGatePassInfo() {
        SaveOrUpdateButton = "Save";
        disableBtnCreate = false;
        if (createOrSearchBundle.equals("Add New")) {
            renderPnlCandidateForm = true;
            renderPnlManPage = false;
            createOrSearchBundle = "Search";
            headerTitle = "Candidate Profile";
            setIcone("ui-icon-search");
            btnNewRender = false;
        } else if (createOrSearchBundle.equals("Search")) {
            renderPnlCandidateForm = false;
            renderPnlManPage = true;
            createOrSearchBundle = "Add New";
            headerTitle = "Search Candidate ";
            candidiatesListDataModel = null;
            setIcone("ui-icon-plus");
            btnNewRender = false;
            clear();
        }
    }

    public void newButtonAction() {
        renderPnlCandidateForm = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        headerTitle = "Candidate Profile";
        setIcone("ui-icon-search");
        btnNewRender = false;
        clear();
    }

    // <editor-fold defaultstate="collapsed" desc="Education Tab">
    public List<HrLuEducTypes> getListOfEducationTypes() {
        return hrCandidateRegistrationBeanLocal.findEducationTypes();
    }

    public List<HrLuEducLevels> getListOfEducationLeves() {
        return hrCandidateRegistrationBeanLocal.findEducationLeves();
    }

    public void educLevel_vlc(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuEducLevels.setEducLevel(event.getNewValue().toString());
            hrLuEducLevels = hrCandidateRegistrationBeanLocal.findbyLuEduLevel(hrLuEducLevels);
            hrCandidiateEducations.setEducLevelId(hrLuEducLevels);
        }
    }

    public void educTypes_vlc(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuEducTypes.setEducType(event.getNewValue().toString());
            hrLuEducTypes = hrCandidateRegistrationBeanLocal.findbyLuEduType(hrLuEducTypes);
            hrCandidiateEducations.setEducTypeId(hrLuEducTypes);
        }
    }

    int educationDateDiff;

    public void educationDateValidation() {
        String startMonth[] = hrCandidiateEducations.getStartDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrCandidiateEducations.getStartDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrCandidiateEducations.getStartDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrCandidiateEducations.getEndDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrCandidiateEducations.getEndDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrCandidiateEducations.getEndDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        educationDateDiff = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }

    public void addToCandidateEducation() {
        educationDateValidation();
        if (educationDateDiff < 0) {
            JsfUtil.addFatalMessage("End date should be greater than Start date. Try again!");
        } else if (declaration.contains(hrLuEducLevels.getEducLevel() + hrLuEducTypes.getEducType())) {
            JsfUtil.addFatalMessage("This field of study and education level is already added!");
        } else if (selectedRowIndexEducation > -1) {
            hrCandidiateEducations.setId(hrCandidiates.getHrCandidiateEducationsList().get(selectedRowIndexEducation).getId());
            hrCandidiates.getHrCandidiateEducationsList().remove(selectedRowIndexEducation);
            declaration.add(hrCandidiates.getHrCandidiateEducationsList().get(selectedRowIndexEducation).getEducLevelId().getEducLevel()
                    + hrCandidiates.getHrCandidiateEducationsList().get(selectedRowIndexEducation).getEducTypeId().getEducType());
            hrCandidiates.getHrCandidiateEducationsList().add(selectedRowIndexEducation, hrCandidiateEducations);
            declaration.add(hrLuEducLevels.getEducLevel() + hrLuEducTypes.getEducType());
            recreateEducationPop();
            clearEducation();
        } else {
            hrCandidiates.addToCandidateEducation(hrCandidiateEducations);
            declaration.add(hrLuEducLevels.getEducLevel() + hrLuEducTypes.getEducType());
            recreateEducationPop();
            clearEducation();
        }
    }

    public void selectedEducation(SelectEvent event) {
        updateStatusEduc = 1;
        selectedRowIndexEducation = hrCandidateEducationsDataModel.getRowIndex();
        hrCandidiateEducations = (HrCandidiateEducations) event.getObject();
        hrLuEducTypes = hrCandidiateEducations.getEducTypeId();
        hrLuEducLevels = hrCandidiateEducations.getEducLevelId();
        hrAddresses = hrCandidiateEducations.getAddressId();
        if (!"".equals(hrCandidiateEducations.getAddressId().toString()) && null != hrCandidiateEducations.getAddressId()) {
            setAllAddressUnitAsOneEdu(hrAddresses.getAddressName());
        } else {
            setAllAddressUnitAsOneEdu("");
        }
    }

    public void recreateEducationPop() {
        hrCandidateEducationsDataModel = null;
        hrCandidateEducationsDataModel = new ListDataModel(new ArrayList<>(hrCandidiates.getHrCandidiateEducationsList()));
    }

    private void clearEducation() {
        hrCandidiateEducations = null;
        hrLuEducLevels = null;
        hrLuEducTypes = null;
        allAddressUnitAsOneEdu = null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Training Tab">
    int selectedRowIndexTraining = -1;
    int updateStatusTrn = 0;

    public int getSelectedRowIndexTraining() {
        return selectedRowIndexTraining;
    }

    public void setSelectedRowIndexTraining(int selectedRowIndexTraining) {
        this.selectedRowIndexTraining = selectedRowIndexTraining;
    }

    public int getUpdateStatusTrn() {
        return updateStatusTrn;
    }

    public void setUpdateStatusTrn(int updateStatusTrn) {
        this.updateStatusTrn = updateStatusTrn;
    }

    int trainingDateDiff;

    public void trainingDateValidation() {
        String startMonth[] = hrCandidateTrainings.getStartDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrCandidateTrainings.getStartDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrCandidateTrainings.getStartDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrCandidateTrainings.getEndDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrCandidateTrainings.getEndDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrCandidateTrainings.getEndDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        trainingDateDiff = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }

    public void addToCandidateTrainings() {
        trainingDateValidation();
        if (allAddressUnitAsOneTrn == null) {
            JsfUtil.addFatalMessage("Address can't be empty");
        } else if (trainingDateDiff < 0) {
            JsfUtil.addFatalMessage("End date should be greater than Start date. Try again!");
        } else if (declaration.contains(hrCandidateTrainings.getTrainingTitle() + hrCandidateTrainings.getInstitution())) {
            JsfUtil.addFatalMessage("This training title and institution already added!");
        } else if (selectedRowIndexTraining > -1) {
            hrCandidateTrainings.setId(hrCandidiates.getHrCandidateTrainingsList().get(selectedRowIndexTraining).getId());
            hrCandidiates.getHrCandidateTrainingsList().remove(selectedRowIndexTraining);
            declaration.add(hrCandidiates.getHrCandidateTrainingsList().get(selectedRowIndexTraining).getTrainingTitle()
                    + hrCandidiates.getHrCandidateTrainingsList().get(selectedRowIndexTraining).getInstitution());
            hrCandidiates.getHrCandidateTrainingsList().add(selectedRowIndexTraining, hrCandidateTrainings);
            declaration.add(hrCandidateTrainings.getTrainingTitle() + hrCandidateTrainings.getInstitution());
            recreateTrainingsPop();
            cleanTrainings();
        } else {
            hrCandidiates.addToCandidateTrainings(hrCandidateTrainings);
            declaration.add(hrCandidateTrainings.getTrainingTitle() + hrCandidateTrainings.getInstitution());
            recreateTrainingsPop();
            cleanTrainings();
        }
    }

    public void selectedTraining(SelectEvent event) {
        updateStatusTrn = 1;
        selectedRowIndexTraining = hrCandidateTrainingsDataModel.getRowIndex();
        hrCandidateTrainings = (HrCandidateTrainings) event.getObject();
        allAddressUnitAsOneTrn = hrCandidateTrainings.getAddressId().getAddressName();
    }

    public void recreateTrainingsPop() {
        hrCandidateTrainingsDataModel = null;
        hrCandidateTrainingsDataModel = new ListDataModel(new ArrayList<>(hrCandidiates.getHrCandidateTrainingsList()));
    }

    private String cleanTrainings() {
        hrCandidateTrainings = null;
        allAddressUnitAsOneTrn = null;
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Expriance Tab">
    int selectedRowIndexExpriance = -1;
    int updateStatusExp = 0;

    public int getSelectedRowIndexExpriance() {
        return selectedRowIndexExpriance;
    }

    public void setSelectedRowIndexExpriance(int selectedRowIndexExpriance) {
        this.selectedRowIndexExpriance = selectedRowIndexExpriance;
    }

    public int getUpdateStatusExp() {
        return updateStatusExp;
    }

    public void setUpdateStatusExp(int updateStatusExp) {
        this.updateStatusExp = updateStatusExp;
    }

    int exprienceDateDiff;

    public void exprienceDateValidation() {
        String startMonth[] = hrCandidateEmpHistories.getStartDate().split("/");
        int isSartMonth = Integer.parseInt(startMonth[1]);
        String startYear[] = hrCandidateEmpHistories.getStartDate().split("/");
        int isStartYear = Integer.parseInt(startYear[2]);
        String startDate[] = hrCandidateEmpHistories.getStartDate().split("/");
        int isStartDate = Integer.parseInt(startDate[0]);
        String endDay[] = hrCandidateEmpHistories.getEndDate().split("/");
        int isEndDay = Integer.parseInt(endDay[0]);
        String endMonth[] = hrCandidateEmpHistories.getEndDate().split("/");
        int isEndMonth = Integer.parseInt(endMonth[1]);
        String endYear[] = hrCandidateEmpHistories.getEndDate().split("/");
        int isEndYear = Integer.parseInt(endYear[2]);
        exprienceDateDiff = ((isEndDay - isStartDate) + ((isEndMonth - isSartMonth) * 30) + ((isEndYear - isStartYear) * 365));
    }

    public void addToCandidateExprience() {
        exprienceDateValidation();
        if (exprienceDateDiff < 0) {
            JsfUtil.addFatalMessage("End date should be greater than Start date. Try again!");
        } else if (declaration.contains(hrCandidateEmpHistories.getInstitution() + hrCandidateEmpHistories.getJobTitle())) {
            JsfUtil.addFatalMessage("This expriance already added!");
        } else if (selectedRowIndexExpriance > -1) {
            hrCandidateEmpHistories.setId(hrCandidiates.getHrCandidateEmpHistoriesList().get(selectedRowIndexExpriance).getId());
            hrCandidiates.getHrCandidateEmpHistoriesList().remove(selectedRowIndexTraining);
            hrCandidiates.getHrCandidateEmpHistoriesList().add(selectedRowIndexTraining, hrCandidateEmpHistories);
            declaration.add(hrCandidateEmpHistories.getInstitution() + hrCandidateEmpHistories.getJobTitle());
            recreateCandidateExprience();
            cleanExpriance();
        } else {
            hrCandidiates.addToCandidateEmpHistories(hrCandidateEmpHistories);
            declaration.add(hrCandidateEmpHistories.getInstitution() + hrCandidateEmpHistories.getJobTitle());
            recreateCandidateExprience();
            cleanExpriance();
        }
    }

    public void selectedExpriance(SelectEvent event) {
        updateStatusExp = 1;
        selectedRowIndexExpriance = hrCandidateEmpHistoriesDataModel.getRowIndex();
        hrCandidateEmpHistories = (HrCandidateEmpHistories) event.getObject();
    }

    public void recreateCandidateExprience() {
        hrCandidateEmpHistoriesDataModel = null;
        hrCandidateEmpHistoriesDataModel = new ListDataModel(new ArrayList<>(hrCandidiates.getHrCandidateEmpHistoriesList()));
    }

    private String cleanExpriance() {
        hrCandidateEmpHistories = null;
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Language Tab">
    int selectedRowIndexLanguage = -1;
    int updateStatueLang = 0;

    public int getSelectedRowIndexLanguage() {
        return selectedRowIndexLanguage;
    }

    public void setSelectedRowIndexLanguage(int selectedRowIndexLanguage) {
        this.selectedRowIndexLanguage = selectedRowIndexLanguage;
    }

    public int getUpdateStatueLang() {
        return updateStatueLang;
    }

    public void setUpdateStatueLang(int updateStatueLang) {
        this.updateStatueLang = updateStatueLang;
    }

    public void addToCandidateLanguages() {
        if (declaration.contains(hrLuLanguages.getLanguageName())) {
            JsfUtil.addFatalMessage(hrLuLanguages.getLanguageName() + " language already added!");
        } else if (selectedRowIndexLanguage > -1) {
            hrCandidateLanguages.setId(hrCandidiates.getHrCandidateLanguagesList().get(selectedRowIndexLanguage).getId());
            hrCandidiates.getHrCandidateLanguagesList().remove(selectedRowIndexLanguage);
            hrCandidiates.getHrCandidateLanguagesList().add(selectedRowIndexLanguage, hrCandidateLanguages);
            declaration.add(hrLuLanguages.getLanguageName());
            recreateCandidateLanguage();
            cleanLanguages();
        } else {
            hrCandidateLanguages.setLanguageId(hrLuLanguages);
            hrCandidiates.addToCandidateLanguages(hrCandidateLanguages);
            declaration.add(hrLuLanguages.getLanguageName());
            recreateCandidateLanguage();
            cleanLanguages();
        }
    }

    public void selectedLanguage(SelectEvent event) {
        updateStatueLang = 1;
        selectedRowIndexLanguage = hrCandidateLanguagesDataModel.getRowIndex();
        hrCandidateLanguages = (HrCandidateLanguages) event.getObject();
        hrLuLanguages = hrCandidateLanguages.getLanguageId();
    }

    public void recreateCandidateLanguage() {
        hrCandidateLanguagesDataModel = null;
        hrCandidateLanguagesDataModel = new ListDataModel(new ArrayList<>(hrCandidiates.getHrCandidateLanguagesList()));
    }

    private String cleanLanguages() {
        hrCandidateLanguages = null;
        hrLuLanguages = null;
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Reference Tab">
    int selectedRowIndexReference = -1;
    int updateStatusRfr = 0;

    public int getSelectedRowIndexReference() {
        return selectedRowIndexReference;
    }

    public void setSelectedRowIndexReference(int selectedRowIndexReference) {
        this.selectedRowIndexReference = selectedRowIndexReference;
    }

    public int getUpdateStatusRfr() {
        return updateStatusRfr;
    }

    public void setUpdateStatusRfr(int updateStatusRfr) {
        this.updateStatusRfr = updateStatusRfr;
    }

    public String addToCandidateReferences() {
        if (declaration.contains(hrCandidateReferences.getNameOfReferee() + hrCandidateReferences.getRelationshipType())) {
            JsfUtil.addFatalMessage("This references already added!");
        } else if (selectedRowIndexReference > -1) {
            hrCandidateReferences.setId(hrCandidiates.getHrCandidateReferencesList().get(selectedRowIndexReference).getId());
            hrCandidiates.getHrCandidateReferencesList().remove(selectedRowIndexReference);
            hrCandidiates.getHrCandidateReferencesList().add(selectedRowIndexReference, hrCandidateReferences);
            recreateCandidateReferences();
            declaration.add(hrCandidateReferences.getNameOfReferee() + hrCandidateReferences.getRelationshipType());
        } else {
            hrCandidiates.addToCandidateReferences(hrCandidateReferences);
            recreateCandidateReferences();
            declaration.add(hrCandidateReferences.getNameOfReferee() + hrCandidateReferences.getRelationshipType());
        }
        return cleanReference();
    }

    public void selectedReferences(SelectEvent event) {
        updateStatusExp = 1;
        selectedRowIndexReference = hrCandidateReferencesDataModel.getRowIndex();
        hrCandidateReferences = (HrCandidateReferences) event.getObject();
    }

    public void recreateCandidateReferences() {
        hrCandidateReferencesDataModel = null;
        hrCandidateReferencesDataModel = new ListDataModel(new ArrayList<>(hrCandidiates.getHrCandidateReferencesList()));
    }

    private String cleanReference() {
        hrCandidateReferences = null;
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Address Tree">
    @EJB
    HrAddressesBeanIntegrationLocal hrAddressesBeanIntegrationLocal;

    private int addressId = 0;
    private String addressType = "Country";
    private String selected;
    private String selectedid;
    private TreeNode root;
    private TreeNode selectedNode;
    private static List<HrAddresses> allAddressData;
    private static List<HrAddresses> addresses;
    private String allAddressUnitAsOne;
    private String allAddressUnitAsOneEdu;
    private String allAddressUnitAsOneTrn;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getSelectedid() {
        return selectedid;
    }

    public void setSelectedid(String selectedid) {
        this.selectedid = selectedid;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public static List<HrAddresses> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<HrAddresses> addresses) {
        HrCandidatController.addresses = addresses;
    }

    public String getAllAddressUnitAsOne() {
        return allAddressUnitAsOne;
    }

    public void setAllAddressUnitAsOne(String allAddressUnitAsOne) {
        this.allAddressUnitAsOne = allAddressUnitAsOne;
    }

    public String getAllAddressUnitAsOneEdu() {
        return allAddressUnitAsOneEdu;
    }

    public void setAllAddressUnitAsOneEdu(String allAddressUnitAsOneEdu) {
        this.allAddressUnitAsOneEdu = allAddressUnitAsOneEdu;
    }

    public String getAllAddressUnitAsOneTrn() {
        return allAddressUnitAsOneTrn;
    }

    public void setAllAddressUnitAsOneTrn(String allAddressUnitAsOneTrn) {
        this.allAddressUnitAsOneTrn = allAddressUnitAsOneTrn;
    }

    public void loadTree() {
        allAddressData = hrAddressesBeanIntegrationLocal.findAll();
        root = new DefaultTreeNode("Root", null);
        populateNodes(allAddressData, 0, root);
    }

    public void populateNodes(List<HrAddresses> addressData, int id, TreeNode node) {
        addresses = new ArrayList<>();
        for (HrAddresses k : getAllAddressData()) {
            if (k.getParentId() == id) {
                TreeNode childNode = new DefaultTreeNode(k.getAddressDescription() + "=>" + k.getAddressId(), node);
                addresses.add(k);
                populateNodes(addresses, k.getAddressId(), childNode);
            }
        }
    }

    public static List<HrAddresses> getAllAddressData() {
        return allAddressData;
    }

    public void onNodeSelect(NodeSelectEvent event) {
        System.out.print("this is from onNodeSelect");
        selectedNode = event.getTreeNode();
        addressId = Integer.parseInt((selectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOne(hrAddresses.getAddressName());
        hrCandidiates.setAddressId(hrAddresses);
    }

    public void onNodeSelectEdu(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        addressId = Integer.parseInt((selectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOneEdu(hrAddresses.getAddressName());
        hrCandidiateEducations.setAddressId(hrAddresses);
    }

    public void onNodeSelectTrn(NodeSelectEvent event) {
        selectedNode = event.getTreeNode();
        addressId = Integer.parseInt((selectedNode.getData().toString()).split("=>")[1]);
        hrAddresses.setAddressId(addressId);
        hrAddresses = hrAddressesBeanIntegrationLocal.findAllAddressUnitAsOne(hrAddresses);
        setAllAddressUnitAsOneTrn(hrAddresses.getAddressName());
        hrCandidateTrainings.setAddressId(hrAddresses);
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main methods">
    public ArrayList<HrCandidiates> searchByCandidateName(String canName) {
        ArrayList<HrCandidiates> candidateName = null;
        hrCandidiates.setCol_Value(canName);
        candidateName = hrCandidateRegistrationBeanLocal.searchByCol_NameAndCol_Value(columnNameResolver, hrCandidiates.getCol_Value());
        return candidateName;
    }

    public void getByCandidateName(SelectEvent event) {
        try {
            if (event.getObject() != null) {
                hrCandidiates = new HrCandidiates();

                hrCandidiates = (HrCandidiates) event.getObject();
                //hrCandidiates.setFirstName(event.getObject().toString());
                hrCandidiates = hrCandidateRegistrationBeanLocal.getByCandidateId(hrCandidiates);
                tab = "";
                preTab = "";
                tabToggle = "tab";
                Update = 1;
                SaveOrUpdateButton = "Update";
                hrLuNationalities = hrCandidiates.getNationality();
                batchCodes = hrCandidateRegistrationBeanLocal.batchCodes("Outside");
                if (hrCandidiates.getAddressId() != null) {
                    hrAddresses = hrCandidiates.getAddressId();
                    setAllAddressUnitAsOne(hrAddresses.getAddressName());
                } else {
                    hrAddresses = new HrAddresses();
                }
                hrAdvertisedJobs = hrCandidiates.getAdvJobId();
                hrAdvertisements = hrAdvertisedJobs.getAdvertId();
                if (hrAdvertisements.getId() != null) {
                    jobTypes = hrCandidateRegistrationBeanLocal.advertizedJobs(hrAdvertisements.getId());
                }

                renderPnlCandidateForm = true;
                renderPnlManPage = false;
                createOrSearchBundle = "Search";
                headerTitle = "Candidate Registration";
                setIcone("ui-icon-search");
                btnNewRender = true;
                recreateCandidateExprience();
                recreateCandidateLanguage();
                recreateCandidateReferences();
                recreateEducationPop();
                recreateTrainingsPop();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Exception");
        }
    }

    public void searchByCandidateName() {
        if (hrCandidiates.getFirstName() == null) {
            JsfUtil.addFatalMessage("Please insert candidate name");
        } else {
            //  candidiatesListDataModel = new ListDataModel(new ArrayList(hrCandidateRegistrationBeanLocal.searchByCandidateName(hrCandidiates)));
        }
    }

    public void recreateCandidateModel() {
        candidiatesListDataModel = null;
        candidiatesListDataModel = new ListDataModel(candidateList);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void batchCode_vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int advertId = Integer.parseInt(event.getNewValue().toString());
            jobTypes = hrCandidateRegistrationBeanLocal.advertizedJobs(advertId);
        }
    }

    public void NantionalityChanged(ValueChangeEvent event) {
        if (!event.getNewValue().toString().isEmpty()) {
            hrLuNationalities.setNationality(event.getNewValue().toString());
            hrLuNationalities = hrCandidateRegistrationBeanLocal.findNationality(hrLuNationalities);
            hrCandidiates.setNationality(hrLuNationalities);
        }
    }

    public void saveorUpdate() {
        if (allAddressUnitAsOne == null) {
            JsfUtil.addFatalMessage("Address can't be empty");
        } else {
            if (Update == 0) {
                hrCandidiates.setAdvJobId(hrAdvertisedJobs);
                hrCandidiates.setStatus(0);
                hrCandidateRegistrationBeanLocal.save(hrCandidiates);
                preTab = "active";
                tabToggle = "tab";
                Update = 1;
                SaveOrUpdateButton = "Update";
                clear();
                JsfUtil.addSuccessMessage("Save successfully");
            } else {
                hrCandidateRegistrationBeanLocal.edit(hrCandidiates);
                preTab = "active";
                tabToggle = "tab";
                SaveOrUpdateButton = "Update";
                clear();
                JsfUtil.addSuccessMessage("Update successfully");
            }
            candidiatesListDataModel = new ListDataModel<>();
        }
    }

    public void clear() {
        hrCandidiates = null;
        hrJobTypes = new HrJobTypes();
        hrAdvertisements = new HrAdvertisements();
        hrAddresses = new HrAddresses();
        hrLuNationalities = new HrLuNationalities();
        allAddressUnitAsOne = "";
        hrAdvertisedJobs = new HrAdvertisedJobs();
        clearEducation();
        cleanExpriance();
        cleanLanguages();
        cleanReference();
        cleanTrainings();
        tabToggle = "tab";
        tab = "disabled";
        preTab = "active";
        Update = 0;
        SaveOrUpdateButton = "Save";
    }

    public void saveCandidate() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveCandidate", dataset)) {

                String[] dateFromUI;
                String calcDateOfBirth;
                String today = stringDateManipulation.toDayInEc();
                if ((hrCandidiates.getDateOfBirth() != null) && (hrCandidiates.getDateOfBirth().compareTo("") != 0)) {
                    if (hrCandidiates.getDateOfBirth().contains("/")) {
                        dateFromUI = hrCandidiates.getDateOfBirth().split("/");
                        calcDateOfBirth = dateFromUI[2] + "-" + dateFromUI[1] + "-" + dateFromUI[0];
                        int calculatedAge = stringDateManipulation.differenceInYears(today, calcDateOfBirth);
                        if (calculatedAge >= 18 && calculatedAge < 100) {
                            saveorUpdate();
                        } else {
                            JsfUtil.addFatalMessage("Age must be between 18 and 100.");
                        }
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveCandidate");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
        }
    }

    public void selectedParamChangeEvent(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            columnNameResolver.setCol_Name_FromTable(event.getNewValue().toString());
            columnNameResolver.setParsed_Col_Name(ColumnParser(event.getNewValue().toString().toLowerCase() + ":"));
            // hrCandidiates.setCol_Value(columnNameResolver.getParsed_Col_Name());
        }
    }

    public String ColumnParser(String col_name) {
        String col = col_name.replace("_", " ");
        System.out.println("col==" + col);
        return col;
    }

    public void populateCandidate(SelectEvent event) {
        tab = "";
        preTab = "";
        tabToggle = "tab";
        Update = 1;
        SaveOrUpdateButton = "Update";
        hrCandidiates = (HrCandidiates) event.getObject();
        hrCandidiates.setId(hrCandidiates.getId());
        hrLuNationalities = hrCandidiates.getNationality();
        batchCodes = hrCandidateRegistrationBeanLocal.batchCodes("Outside");
        if (hrCandidiates.getAddressId() != null) {
            hrAddresses = hrCandidiates.getAddressId();
            setAllAddressUnitAsOne(hrAddresses.getAddressName());
        } else {
            hrAddresses = new HrAddresses();
        }
        hrAdvertisedJobs = hrCandidiates.getAdvJobId();
        hrAdvertisements = hrAdvertisedJobs.getAdvertId();
        jobTypes = hrCandidateRegistrationBeanLocal.advertizedJobs(hrAdvertisements.getId());
        renderPnlCandidateForm = true;
        renderPnlManPage = false;
        createOrSearchBundle = "Search";
        headerTitle = "Candidate Registration";
        setIcone("ui-icon-search");
        recreateCandidateExprience();
        recreateCandidateLanguage();
        recreateCandidateReferences();
        recreateEducationPop();
        recreateTrainingsPop();
    }
//</editor-fold>
//</editor-fold>
}
