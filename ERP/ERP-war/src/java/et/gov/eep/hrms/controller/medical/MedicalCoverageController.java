/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.medical;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import et.gov.eep.hrms.businessLogic.medical.MedCoverageBeanLocal;
import et.gov.eep.hrms.entity.medical.HrLocalMedCoverage;

/**
 *
 * @author INSA
 */
@Named(value = "medicalCoverageController")
@ViewScoped
public class MedicalCoverageController implements Serializable {

    @Inject
    HrLocalMedCoverage hrLocalMedCoverage;
    @Inject
    SessionBean sessionBeanLocal;
    @EJB
    MedCoverageBeanLocal medCoverageBeanLocal;
    DataModel<HrLocalMedCoverage> medCoverageDataModel = new ListDataModel<>();

    private int updateStatus = 0;
    private String saveOrUpdate = "Save";

    public MedicalCoverageController() {
    }

    @PostConstruct
    public void init() {
        hrLocalMedCoverage.setStatus(0);
    }

    //<editor-fold defaultstate="collapsed" desc="getter setter">
    public HrLocalMedCoverage getHrLocalMedCoverage() {
        if (hrLocalMedCoverage == null) {
            hrLocalMedCoverage = new HrLocalMedCoverage();
        }
        return hrLocalMedCoverage;
    }

    public void setHrLocalMedCoverage(HrLocalMedCoverage hrLocalMedCoverage) {
        this.hrLocalMedCoverage = hrLocalMedCoverage;
    }

    public DataModel<HrLocalMedCoverage> getMedCoverageDataModel() {
        return medCoverageDataModel;
    }

    public void setMedCoverageDataModel(DataModel<HrLocalMedCoverage> medCoverageDataModel) {
        this.medCoverageDataModel = medCoverageDataModel;
    }

    public String getSaveOrUpdate() {
        return saveOrUpdate;
    }

    public void setSaveOrUpdate(String saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }
    //</editor-fold>

    public void saveMedCoverage() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "saveMedCoverage", dataset)) {

                if (updateStatus == 0) {
                    try {
                        medCoverageBeanLocal.save(hrLocalMedCoverage);
                        resetMedCoverage();
                        JsfUtil.addSuccessMessage("Saved sccessfully");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Error occurs while saving");
                    }
                } else {
                    try {
                        medCoverageBeanLocal.edit(hrLocalMedCoverage);
                        resetMedCoverage();
                        JsfUtil.addSuccessMessage("Updated sccessfully");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JsfUtil.addFatalMessage("Error occurs while updating");
                    }
                }

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator.");
                EventEntry eventEntry = new EventEntry();
                eventEntry.setSessionId(sessionBeanLocal.getSessionID());
                eventEntry.setUserId(sessionBeanLocal.getUserId());
                QName qualifiedName = new QName("", "project");
                JAXBElement<String> test = new JAXBElement<String>(qualifiedName, String.class, null, sessionBeanLocal.getUserName());
                eventEntry.setUserLogin(test);
                security.addEventLog(eventEntry, dataset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JsfUtil.addFatalMessage("Error occurs while updating");
        }
    }

    public void resetMedCoverage() {
        hrLocalMedCoverage = null;
        updateStatus = 0;
        saveOrUpdate = "Save";
    }

    public void populateMedCoverage(SelectEvent event) {
        hrLocalMedCoverage = (HrLocalMedCoverage) event.getObject();
        updateStatus = 1;
        saveOrUpdate = "Update";
    }

}
