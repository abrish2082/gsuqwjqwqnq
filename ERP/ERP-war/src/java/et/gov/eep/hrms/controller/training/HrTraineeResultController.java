/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.training.HrTraineesResultBeanLocal;
import et.gov.eep.hrms.entity.training.HrTdPsvcCourses;
import et.gov.eep.hrms.entity.training.HrTdPsvcResultDetails;
import et.gov.eep.hrms.entity.training.HrTdPsvcResults;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import et.gov.eep.hrms.entity.training.HrTdPsvcTrainees;

/**
 *
 * @author insa
 */
@Named(value = "hrTraineeResultController")
@ViewScoped
public class HrTraineeResultController implements Serializable {
    //<editor-fold defaultstate="collapsed" desc="Entity, bussiness logic  & variables">

    @Inject
    HrTdPsvcResults hrTdPsvcResults;

    @Inject
    HrTdPsvcTrainees hrTdPsvcTrainees;

    @Inject
    HrTdPsvcCourses hrTdPsvcCourses;

    @Inject
    HrTdPsvcResultDetails hrTdPsvcResultDetails;

    @Inject
    HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails;

    @Inject
    SessionBean sessionBeanLocal;

    @EJB
    HrTraineesResultBeanLocal hrTraineesResultBeanLocal;

    private boolean renderPnlCreateAdditional = true;
    private String addorUpdate = "Add";
    private String SaveOrUpdateButton = "Save";
    boolean btnaddvisibility = true;
    private String headerTitle = "Search....";
    private String icone = "ui-icon-plus";
    private String createOrSearchBundle = "New";
    private boolean disableBtnCreate;
    private boolean renderPnlCreateGatePass = false;
    private boolean renderPnlManPage = true;
    private boolean btnNewRender = false;
    private String renderpnlContrat = "false";
    private int updateStatus = 0;
    DataModel<HrTdPsvcTrainees> hrResultDatamodel;
    DataModel<HrTdPsvcResultDetails> hrResultDetailsDatamodel;
    List<HrTdPsvcCourses> hrTrainingCourselist = new ArrayList<>();
    List<HrTdPsvcTraineeDetails> hrTrainerslist = new ArrayList<>();
    List<HrTdPsvcTrainees> hrTdyearList = new ArrayList<>();
    List<HrTdPsvcTrainees> hrTdBachCodeList = new ArrayList<>();
    List<HrTdPsvcResults> hrtdResultList = new ArrayList<>();

//</editor-fold>
    @PostConstruct
    public void Init() {
        hrTrainingCourselist = hrTraineesResultBeanLocal.findByCourse();
        hrTrainerslist = hrTraineesResultBeanLocal.findall(hrTdPsvcTraineeDetails);
        hrTdyearList = hrTraineesResultBeanLocal.findall();
        hrTdPsvcResults.setPreparedOn(StringDateManipulation.toDayInEc());
    }

    //<editor-fold defaultstate="collapsed" desc="Getters setter">
    public List<HrTdPsvcTrainees> getHrTdyearList() {
        return hrTdyearList;
    }

    public void setHrTdyearList(List<HrTdPsvcTrainees> hrTdyearList) {
        this.hrTdyearList = hrTdyearList;
    }

    public HrTdPsvcTrainees getHrTdPsvcTrainees() {
        if (hrTdPsvcTrainees == null) {
            hrTdPsvcTrainees = new HrTdPsvcTrainees();
        }
        return hrTdPsvcTrainees;
    }

    public void setHrTdPsvcTrainees(HrTdPsvcTrainees hrTdPsvcTrainees) {
        this.hrTdPsvcTrainees = hrTdPsvcTrainees;
    }

    public List<HrTdPsvcTrainees> getHrTdBachCodeList() {
        return hrTdBachCodeList;
    }

    public void setHrTdBachCodeList(List<HrTdPsvcTrainees> hrTdBachCodeList) {
        this.hrTdBachCodeList = hrTdBachCodeList;
    }

    public HrTdPsvcCourses getHrTdPsvcCourses() {
        if (hrTdPsvcCourses == null) {
            hrTdPsvcCourses = new HrTdPsvcCourses();
        }
        return hrTdPsvcCourses;
    }

    public void setHrTdPsvcCourses(HrTdPsvcCourses hrTdPsvcCourses) {
        this.hrTdPsvcCourses = hrTdPsvcCourses;
    }

    public HrTdPsvcResults getHrTdPsvcResults() {
        if (hrTdPsvcResults == null) {
            hrTdPsvcResults = new HrTdPsvcResults();
        }
        return hrTdPsvcResults;
    }

    public void setHrTdPsvcResults(HrTdPsvcResults hrTdPsvcResults) {
        this.hrTdPsvcResults = hrTdPsvcResults;
    }

    public HrTdPsvcResultDetails getHrTdPsvcResultDetails() {
        if (hrTdPsvcResultDetails == null) {
            hrTdPsvcResultDetails = new HrTdPsvcResultDetails();
        }
        return hrTdPsvcResultDetails;
    }

    public void setHrTdPsvcResultDetails(HrTdPsvcResultDetails hrTdPsvcResultDetails) {
        this.hrTdPsvcResultDetails = hrTdPsvcResultDetails;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public String getAddorUpdate() {
        return addorUpdate;
    }

    public void setAddorUpdate(String addorUpdate) {
        this.addorUpdate = addorUpdate;
    }

    public String getSaveOrUpdateButton() {
        return SaveOrUpdateButton;
    }

    public void setSaveOrUpdateButton(String SaveOrUpdateButton) {
        this.SaveOrUpdateButton = SaveOrUpdateButton;
    }

    public boolean isBtnaddvisibility() {
        return btnaddvisibility;
    }

    public void setBtnaddvisibility(boolean btnaddvisibility) {
        this.btnaddvisibility = btnaddvisibility;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public boolean isDisableBtnCreate() {
        return disableBtnCreate;
    }

    public void setDisableBtnCreate(boolean disableBtnCreate) {
        this.disableBtnCreate = disableBtnCreate;
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

    public boolean isBtnNewRender() {
        return btnNewRender;
    }

    public void setBtnNewRender(boolean btnNewRender) {
        this.btnNewRender = btnNewRender;
    }

    public String getRenderpnlContrat() {
        return renderpnlContrat;
    }

    public void setRenderpnlContrat(String renderpnlContrat) {
        this.renderpnlContrat = renderpnlContrat;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public DataModel<HrTdPsvcTrainees> getHrResultDatamodel() {
        return hrResultDatamodel;
    }

    public void setHrResultDatamodel(DataModel<HrTdPsvcTrainees> hrResultDatamodel) {
        this.hrResultDatamodel = hrResultDatamodel;
    }

    public DataModel<HrTdPsvcResultDetails> getHrResultDetailsDatamodel() {
        return hrResultDetailsDatamodel;
    }

    public void setHrResultDetailsDatamodel(DataModel<HrTdPsvcResultDetails> hrResultDetailsDatamodel) {
        this.hrResultDetailsDatamodel = hrResultDetailsDatamodel;
    }

    public List<HrTdPsvcCourses> getHrTrainingCourselist() {
        return hrTrainingCourselist;
    }

    public void setHrTrainingCourselist(List<HrTdPsvcCourses> hrTrainingCourselist) {
        this.hrTrainingCourselist = hrTrainingCourselist;
    }

    public HrTdPsvcTraineeDetails getHrTdPsvcTraineeDetails() {
        if (hrTdPsvcTraineeDetails == null) {
            hrTdPsvcTraineeDetails = new HrTdPsvcTraineeDetails();
        }
        return hrTdPsvcTraineeDetails;
    }

    public void setHrTdPsvcTraineeDetails(HrTdPsvcTraineeDetails hrTdPsvcTraineeDetails) {
        this.hrTdPsvcTraineeDetails = hrTdPsvcTraineeDetails;
    }

    public List<HrTdPsvcTraineeDetails> getHrTrainerslist() {
        return hrTrainerslist;
    }

    public void setHrTrainerslist(List<HrTdPsvcTraineeDetails> hrTrainerslist) {
        this.hrTrainerslist = hrTrainerslist;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public List<HrTdPsvcResults> getHrtdResultList() {
        return hrtdResultList;
    }

    public void setHrtdResultList(List<HrTdPsvcResults> hrtdResultList) {
        this.hrtdResultList = hrtdResultList;
    }
//
//    public String getPreparedDate() {
//        return preparedDate;
//    }
//
//    public void setPreparedDate(String preparedDate) {
//        this.preparedDate = preparedDate;
//    }
//</editor-fold> 
    //<editor-fold defaultstate="collapsed" desc="Search">

    public void newOrSearchPage() {
        switch (createOrSearchBundle) {
            case "New":
                renderPnlCreateGatePass = true;
                renderPnlManPage = false;
                btnNewRender = false;
                createOrSearchBundle = "Search";
                headerTitle = "Electro Mecanics Courses";
                setIcone("ui-icon-search");
                break;
            case "Search":
                renderPnlCreateGatePass = false;
                renderPnlManPage = true;
                btnNewRender = false;
                createOrSearchBundle = "New";
                setIcone("ui-icon-plus");
                break;
        }
    }

    public void newButtonAction() {
        resetPreserviceTraineeResult();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = false;
        updateStatus = 0;
        SaveOrUpdateButton = "Save";
    }

    public void searchPage() {
        renderPnlCreateGatePass = false;
        renderPnlManPage = true;
    }

    public void populate(SelectEvent event) {
        hrTdPsvcResults = (HrTdPsvcResults) event.getObject();
        recreateDataModel();
        renderPnlCreateGatePass = true;
        renderPnlManPage = false;
        btnNewRender = true;
        updateStatus = 1;
        SaveOrUpdateButton = "Update";
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
    }

    public void findByYear(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            Integer years = Integer.parseInt(event.getNewValue().toString());
            hrTdBachCodeList = hrTraineesResultBeanLocal.findByyear(years);
        }
    }

    public void BachCode_vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            String code = String.valueOf(event.getNewValue().toString());
            hrtdResultList = hrTraineesResultBeanLocal.findByYearBach(code);
        }
    }

    public void findByTrainerName(ValueChangeEvent event) {
        if (event.getNewValue().toString() != null) {
            hrTdPsvcTraineeDetails = hrTdPsvcResults.getTraineeDetailId();
        }
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Main Methods">
    public void addPreserviceTraineeResult() {
        try {
            if (hrTdPsvcResultDetails.getResult() > 100) {
                JsfUtil.addFatalMessage("Result Can't Greater than 100");
            } else {
                hrTdPsvcResults.setTraineeDetailId(hrTdPsvcTraineeDetails);
                hrTdPsvcResultDetails.setTrainingId(hrTdPsvcCourses);
                hrTdPsvcResults.add(hrTdPsvcResultDetails);
                recreateDataModel();
                clearPreServiceTraineeResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addFatalMessage("error occured");
        }
    }

    public void recreateDataModel() {
        hrResultDetailsDatamodel = null;
        hrResultDetailsDatamodel = new ListDataModel(new ArrayList(hrTdPsvcResults.getHrTdPsvcResultDetailsList()));
    }

    public void clearPreServiceTraineeResult() {
        hrTdPsvcResultDetails = new HrTdPsvcResultDetails();
        hrTdPsvcCourses = new HrTdPsvcCourses();
        hrTdPsvcTraineeDetails = new HrTdPsvcTraineeDetails();
    }

    public void savePreserviceTraineeResult() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "savePreserviceTraineeResult", dataset)) {
                try {
                    String SStmonth[] = hrTdPsvcResults.getFromDate().split("/");
                    int ISstmonth = Integer.parseInt(SStmonth[1]);
                    String SSstyear[] = hrTdPsvcResults.getFromDate().split("/");
                    int ISyear = Integer.parseInt(SSstyear[2]);
                    String SSdate[] = hrTdPsvcResults.getFromDate().split("/");
                    int ISstdate = Integer.parseInt(SSdate[0]);
                    String SEday[] = hrTdPsvcResults.getToDate().split("/");
                    int IEday = Integer.parseInt(SEday[0]);
                    String SEMonth[] = hrTdPsvcResults.getToDate().split("/");
                    int IEMonth = Integer.parseInt(SEMonth[1]);
                    String SEYear[] = hrTdPsvcResults.getToDate().split("/");
                    int IEYear = Integer.parseInt(SEYear[2]);
                    int Start_end_DateGap = ((IEday - ISstdate) + ((IEMonth - ISstmonth) * 30) + ((IEYear - ISyear) * 365));
                    if (Start_end_DateGap < 0) {
                        JsfUtil.addFatalMessage("Ende date can't come Before Start date!!!");
                    } else if ((!(hrResultDetailsDatamodel.getRowCount() > 0))) {
                        JsfUtil.addFatalMessage("Data Table Should be filled");
                    } else {
                        hrTdPsvcResults.setPreparedBy(sessionBeanLocal.getUserId());
                        hrTraineesResultBeanLocal.saveOrUpdate(hrTdPsvcResults);
                        if (updateStatus == 0) {
                            JsfUtil.addSuccessMessage("Saved Sucessfully");
                            resetPreserviceTraineeResult();
                        } else {
                            JsfUtil.addSuccessMessage("Updated Sucessfully");
                            resetPreserviceTraineeResult();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JsfUtil.addFatalMessage("Somthing occure when data Save");
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
                JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "savePreserviceTraineeResult");
                eventEntry.setProgram(program);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void resetPreserviceTraineeResult() {
        hrTdPsvcResults = null;
        hrTdPsvcCourses = null;
        hrTdPsvcTraineeDetails = null;
        hrResultDetailsDatamodel = new ArrayDataModel<>();
    }

    public void selectedStudent(SelectEvent event) {
        hrTdPsvcResultDetails = (HrTdPsvcResultDetails) event.getObject();
        hrTdPsvcTraineeDetails = hrTdPsvcResults.getTraineeDetailId();
        hrTdPsvcCourses = hrTdPsvcResultDetails.getTrainingId();
        addorUpdate = "Modify";
    }
    //</editor-fold>
}
