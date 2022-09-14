/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsInsuranceNotToBank;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsPortEntry;
import et.gov.eep.prms.mapper.PrmsInsuranceNotToBankFacade;
import et.gov.eep.prms.mapper.PrmsPortEntryFacade;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class InsuranceNotificationToBankBean implements InsuranceNotificationToBankBeanLocal {

    @EJB
    PrmsInsuranceNotToBankFacade prmsInsuranceNotToBankFacade;
    @EJB
    PrmsPortEntryFacade prmsPortEntryFacade;

    @Override
    public PrmsInsuranceNotToBank generateNotificationNo() {
        return prmsInsuranceNotToBankFacade.generateNotificationNoSequence();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public ArrayList<PrmsInsuranceNotToBank> fromServiceProvider() {
        return prmsInsuranceNotToBankFacade.toBank();
    }

    @Override
    public ArrayList<PrmsInsuranceNotToBank> fromServiceProDetail() {
        return prmsInsuranceNotToBankFacade.bankBranch();
    }

    @Override
    public ArrayList<PrmsPortEntry> fromPortEntry() {
        return prmsInsuranceNotToBankFacade.portVoyages();
    }

    @Override
    public ArrayList<PrmsInsuranceNotToBank> fromInsuranceReg() {
        return prmsInsuranceNotToBankFacade.forInsuranceNo();
    }

    @Override
    public List<PrmsInsuranceNotToBank> searchBankNotificationNo(PrmsInsuranceNotToBank prmsInsuranceNotToBank) {
        return prmsInsuranceNotToBankFacade.searchByNoificationNo(prmsInsuranceNotToBank);
    }

    @Override
    public void create(PrmsInsuranceNotToBank prmsInsuranceNotToBank) {
        prmsInsuranceNotToBankFacade.create(prmsInsuranceNotToBank);
    }

    @Override
    public void edit(PrmsInsuranceNotToBank prmsInsuranceNotToBank) {
        prmsInsuranceNotToBankFacade.edit(prmsInsuranceNotToBank);
    }

    @Override
    public PrmsInsuranceRequisition autoGenerate(PrmsInsuranceRequisition insuranceregistration) {
        return prmsInsuranceNotToBankFacade.getOtherAttributes(insuranceregistration);
    }

    @Override
    public PrmsInsuranceNotToBank getSelectedRow(String insuranceNotificationNo) {
        return prmsInsuranceNotToBankFacade.getSelectedRow(insuranceNotificationNo);
    }

    @Override
    public List<PrmsInsuranceNotToBank> getParamNameList() {
       return prmsInsuranceNotToBankFacade.getParamNameList();
    }
}
