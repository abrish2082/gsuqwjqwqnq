/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.controller.payroll;

import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.hrms.businessLogic.payroll.HrPayrollPensionRatesBeanLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import et.gov.eep.hrms.entity.payroll.HrPayrollPensionRates;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import webService.AAA;
import webService.EventEntry;
import webService.IAdministration;

/**
 *
 * @author Administrator
 */
@Named(value = "pensionRateConrtoller")
@ViewScoped
public class PensionRateConrtoller implements Serializable {

    @Inject
    HrPayrollPensionRates hrPayrollPensionRates;
    @Inject
    SessionBean sessionBeanLocal;
    @EJB
    HrPayrollPensionRatesBeanLocal hrPayrollPensionRatesLocal;

    public HrPayrollPensionRates getHrPayrollPensionRates() {
        return hrPayrollPensionRates;
    }

    public void setHrPayrollPensionRates(HrPayrollPensionRates hrPayrollPensionRates) {
        this.hrPayrollPensionRates = hrPayrollPensionRates;
    }

    /**
     * Creates a new instance of PensionRateConrtoller
     */
    public PensionRateConrtoller() {
    }

    private boolean makePensionActive;

    public boolean isMakePensionActive() {
        return makePensionActive;
    }

    public void setMakePensionActive(boolean makePensionActive) {
        this.makePensionActive = makePensionActive;
    }

    /**
     * *
     * inserts the employees pension rates
     *
     *
     */
    public void SavePension() {
        try {
            AAA securityService = new AAA();
            IAdministration security = securityService.getMetadataExchangeHttpBindingIAdministration();
            String systemBundle = "et/gov/eep/commonApplications/securityServer";
            String dataset = securityBean.Utility.getBundleValue(systemBundle, "dataSet");
            if (security.checkAccess(sessionBeanLocal.getUserName(), "SavePension", dataset)) {
                if (makePensionActive) {
                    hrPayrollPensionRates.setStatus("1");
                } else {
                    hrPayrollPensionRates.setStatus("2");
                }
                hrPayrollPensionRatesLocal.create(hrPayrollPensionRates);
                JsfUtil.addSuccessMessage("Pension rate is successfully Saved!");

            } else {
                JsfUtil.addFatalMessage("You don't have appropriate permission, please contact the system administrator. ");
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
        }
    }

    public void onToDateSelect(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    }
}
