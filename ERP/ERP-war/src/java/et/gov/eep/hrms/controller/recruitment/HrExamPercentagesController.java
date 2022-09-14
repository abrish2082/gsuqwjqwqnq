/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.recruitment;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.commonApplications.utility.date.StringDateManipulation;
import et.gov.eep.hrms.businessLogic.recruitment.HrExamPercentagesBeanLocal;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.recruitment.HrExamPercentages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Benin
 */
@Named(value = "hrExamPercentagesController")
@ViewScoped
public class HrExamPercentagesController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Entity and sessionBean Initialization">
    @Inject
            HrExamPercentages hrExamPercentages;
    @Inject
            HrAdvertisements hrAdvertisements;
    @Inject
            SessionBean sessionBeanLocal;
    @EJB
            HrExamPercentagesBeanLocal hrExamPercentagesBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="var initialization">
    int updateStatus = 0;
    private String saveorUpdateBundle = "Save";
    private final String advertType = "Outside";
    List<HrAdvertisements> bachCodes = new ArrayList<>();
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="post construct">
    public HrExamPercentagesController() {
    }
    
    @PostConstruct
    public void init() {
        setBachCodes(hrExamPercentagesBeanLocal.batchCodes(advertType));
        hrExamPercentages.setPreparedOn(StringDateManipulation.toDayInEc());
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getters & setters">
    public HrExamPercentages getHrExamPercentages() {
        if (hrExamPercentages == null) {
            hrExamPercentages = new HrExamPercentages();
        }
        return hrExamPercentages;
    }

    public void setHrExamPercentages(HrExamPercentages hrExamPercentages) {
        this.hrExamPercentages = hrExamPercentages;
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

    public HrExamPercentagesBeanLocal getHrExamPercentagesBeanLocal() {
        return hrExamPercentagesBeanLocal;
    }

    public void setHrExamPercentagesBeanLocal(HrExamPercentagesBeanLocal hrExamPercentagesBeanLocal) {
        this.hrExamPercentagesBeanLocal = hrExamPercentagesBeanLocal;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getSaveorUpdateBundle() {
        return saveorUpdateBundle;
    }

    public void setSaveorUpdateBundle(String saveorUpdateBundle) {
        this.saveorUpdateBundle = saveorUpdateBundle;
    }

    public List<HrAdvertisements> getBachCodes() {
        return bachCodes;
    }

    public void setBachCodes(List<HrAdvertisements> bachCodes) {
        this.bachCodes = bachCodes;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="main">
    public void batchCode_vcl(ValueChangeEvent event) {
        if (event.getNewValue() != null) {
            int advertId = Integer.valueOf(event.getNewValue().toString());
            hrAdvertisements.setId(advertId);
            hrExamPercentages = hrExamPercentagesBeanLocal.selectExamPrecentage(hrAdvertisements);
            if (hrExamPercentages == null) {
                updateStatus = 0;
                saveorUpdateBundle = "Save";
            } else {
                updateStatus = 1;
                saveorUpdateBundle = "Update";
            }
        }
    }

    public void saveAssessmentWeight() {
        double writtenWeight = 0;
        double interviewWeight = 0;
        double experienceWeight = 0;
        double cgpaWeight = 0;
        double practicalWeight = 0;
        double otherWeight = 0;
        double sum = 0;

        if (!hrExamPercentages.getWrittenPercentage().isEmpty()) {
            writtenWeight = Float.valueOf(hrExamPercentages.getWrittenPercentage());
            sum += writtenWeight;
        }
        if (!hrExamPercentages.getInterviewPercentage().isEmpty()) {
            interviewWeight = Float.valueOf(hrExamPercentages.getInterviewPercentage());
            sum += interviewWeight;
        }
        if (!hrExamPercentages.getExperiencePercentage().isEmpty()) {
            experienceWeight = Float.valueOf(hrExamPercentages.getExperiencePercentage());
            sum += experienceWeight;
        }
        if (!hrExamPercentages.getCgpaPercentage().isEmpty()) {
            cgpaWeight = Float.valueOf(hrExamPercentages.getCgpaPercentage());
            sum += cgpaWeight;
        }
        if (!hrExamPercentages.getPracticalPercentage().isEmpty()) {
            practicalWeight = Float.valueOf(hrExamPercentages.getPracticalPercentage());
            sum += practicalWeight;
        }
        if (!hrExamPercentages.getOtherPercentage().isEmpty()) {
            otherWeight = Float.valueOf(hrExamPercentages.getOtherPercentage());
            sum += otherWeight;
        }
        if (sum == 100) {
            hrExamPercentages.setBatchCode(hrAdvertisements.getId());
            try {
                AAA securityService = new AAA();
                IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
                String systemBundle = "et/gov/eep/commonApplications/securityServer";
                String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
                if (security.checkAccess(sessionBeanLocal.getUserName(), "saveAssessmentWeight", dataset)) {
                    if (updateStatus == 0) {
                        try {
                            hrExamPercentages.setPreparedBy(sessionBeanLocal.getUserId());
                            hrExamPercentages.setPreparedOn(StringDateManipulation.toDayInEc());
                            hrExamPercentagesBeanLocal.save(hrExamPercentages);
                            JsfUtil.addSuccessMessage("Assessment weight saved successfully");
                            hrExamPercentages = null;
                            hrAdvertisements = null;
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Error occurs while saving assessmet weight");
                        }
                    } else {
                        try {
                            hrExamPercentagesBeanLocal.update(hrExamPercentages);
                            JsfUtil.addSuccessMessage("Assessment weight updated successfully");
                            hrExamPercentages = null;
                            hrAdvertisements = null;
                        } catch (Exception ex) {
                            JsfUtil.addFatalMessage("Error occurs while updating assessmet weight");
                            updateStatus = 0;
                            saveorUpdateBundle = "Save";
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
                    JAXBElement<String> program = new JAXBElement<String>(qualifiedName, String.class, null, "saveAssessmentWeight");
                    eventEntry.setProgram(program);
                    security.addEventLog(eventEntry, dataset);
                }
            } catch (Exception ex) {
                updateStatus = 0;
                saveorUpdateBundle = "Save";
            }
        } else {
            JsfUtil.addFatalMessage("The sum of the weights should be 100");
        }
    }

    public void resetAssessmentWeight() {
        hrExamPercentages = null;
        hrAdvertisements = null;
        updateStatus = 0;
        saveorUpdateBundle = "Save";
    }
    //</editor-fold>

}
