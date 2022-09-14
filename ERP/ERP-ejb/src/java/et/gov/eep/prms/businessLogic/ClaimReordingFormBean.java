/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.fcms.mapper.FmsLuCurrencyFacade;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.prms.entity.PrmsClaimRecordingForm;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import et.gov.eep.prms.mapper.PrmsClaimRecordingFormFacade;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class ClaimReordingFormBean implements ClaimReordingFormBeanLocal {

    @EJB
    PrmsClaimRecordingFormFacade prmsClaimRecordingFormFacade;
    @EJB
    PrmsContractFacade prmsContractFacade;
    @EJB
    FmsLuCurrencyFacade fmsLuCurrencyFacade;

    @Override
    public void create(PrmsClaimRecordingForm prmsClaimRecordingForm) {
        prmsClaimRecordingFormFacade.create(prmsClaimRecordingForm);
    }

    @Override
    public void update(PrmsClaimRecordingForm prmsClaimRecordingForm) {
        prmsClaimRecordingFormFacade.edit(prmsClaimRecordingForm);
    }

    @Override
    public List<PrmsClaimRecordingForm> searchAllMrktNo(PrmsClaimRecordingForm prmsClaimRecordingForm) {
        return prmsClaimRecordingFormFacade.getMarketNo(prmsClaimRecordingForm);
    }

    @Override
    public List<PrmsContract> listOfContractNO() {
        return prmsContractFacade.findAll();
    }

    @Override
    public PrmsClaimRecordingForm getLastClaimNo() {
        return prmsClaimRecordingFormFacade.getLastClaimNo();
    }

    @Override
    public PrmsClaimRecordingForm getSelectedRequest(BigDecimal claimId) {
        return prmsClaimRecordingFormFacade.getSelectedId(claimId);
    }

    @Override
    public List<PrmsClaimRecordingForm> findAllClaimInfo() {

        return prmsClaimRecordingFormFacade.findAll();
    }

    @Override
    public List<PrmsClaimRecordingForm> findByStatus(int Stataus) {
        return prmsClaimRecordingFormFacade.findByStatus();
    }

    @Override
    public HrDepartments getSelectDepartement(int key) {
        return prmsClaimRecordingFormFacade.getSelectDepartement(key);
    }

    @Override
    public List<FmsLuCurrency> getCurrencyName() {
        return fmsLuCurrencyFacade.findAll();
    }

    @Override
    public List<PrmsClaimRecordingForm> getClaimLists() {
     return prmsClaimRecordingFormFacade.getClaimLists();
    }

    @Override
    public List<PrmsClaimRecordingForm> searchByClaimNo(PrmsClaimRecordingForm prmsClaimRecordingForm) {
        return prmsClaimRecordingFormFacade.findClaimNos(prmsClaimRecordingForm);
    }

    @Override
    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsCotract) {
        return prmsClaimRecordingFormFacade.checkAmendOrNot(prmsCotract);
    }

    @Override
    public PrmsContractAmendment getContractAmendment(PrmsContract prmsCotract) {
        return prmsClaimRecordingFormFacade.getContractAmendment(prmsCotract);
    }

    @Override
    public List<PrmsClaimRecordingForm> getMmsClaimRColumnNameList() {
       return  prmsClaimRecordingFormFacade.getMmsClaimRColumnNameList();
    }

    @Override
    public List<PrmsClaimRecordingForm> getClaimRListsByParameter(PrmsClaimRecordingForm prmsClaimRecordingForm) {
        return  prmsClaimRecordingFormFacade.getClaimRListsByParameter(prmsClaimRecordingForm);
    }
     @Override
    public List<PrmsClaimRecordingForm> getClaimListsByParameter(PrmsClaimRecordingForm prmsClaimRecordingForm) {
        return prmsClaimRecordingFormFacade.getClaimListsByParameter(prmsClaimRecordingForm);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return prmsClaimRecordingFormFacade.getColumnNameList();
    }

}
