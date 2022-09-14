/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

   // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.prms.entity.PrmsBankClearance;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsBankClearanceFacade;
import et.gov.eep.prms.mapper.PrmsContractFacade;
import et.gov.eep.prms.mapper.PrmsLcRigistrationAmendFacade;
import et.gov.eep.prms.mapper.PrmsLcRigistrationFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

@Stateless
public class Bank_Clearance_RegistrationBean implements Bank_Clearance_RegistrationBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    private PrmsBankClearanceFacade bankClearanceFacade;
    @EJB
    PrmsLcRigistrationFacade prmsLcRigistrationFacade;
    @EJB
    PrmsLcRigistrationAmendFacade prmsLcRigistrationAmendFacade;
    @EJB
    PrmsSupplyProfileFacade prmsSupplyProfileFacade;
    @EJB
    PrmsContractFacade prmsContractFacade;
// </editor-fold>

    @Override
    public List<PrmsBankClearance> findClearanceNo(PrmsBankClearance bankClearanceRegistration) {
        return bankClearanceFacade.findClearanceNo(bankClearanceRegistration);
    }

    @Override
    public PrmsBankClearance getClearanceNo(PrmsBankClearance bankClearanceRegistration) {
        return bankClearanceFacade.getClearanceNo(bankClearanceRegistration);
    }

    @Override
    public void create(PrmsBankClearance bankClearanceRegistration) {
        bankClearanceFacade.create(bankClearanceRegistration);
    }

    @Override
    public void update(PrmsBankClearance bankClearanceRegistration) {
        bankClearanceFacade.edit(bankClearanceRegistration);
    }

    @Override
    public PrmsBankClearance getLastClearanceNo() {
        return bankClearanceFacade.getLastClearanceNo();
    }

    @Override
    public PrmsBankClearance getSelectedClearanceNo(String clearanceNo) {
        return bankClearanceFacade.getSelectedClearanceNo(clearanceNo);
    }

    @Override
    public PrmsSupplyProfile getVondorName(PrmsSupplyProfile prmsSupplyProfile) {
        return prmsSupplyProfileFacade.getBySuppId(prmsSupplyProfile);
    }

    @Override
    public PrmsContract getContNumber(PrmsContract prmsContract) {
        return prmsContractFacade.getByContId(prmsContract);
    }

    @Override
    public ArrayList<PrmsLcRigistration> lcNosItem() {
        return prmsLcRigistrationFacade.lcNosItemList();
    }

    @Override
    public PrmsLcRigistration getLcAmount(PrmsLcRigistration prmsLcRigistration) {
        return prmsLcRigistrationFacade.getLcAmountByLcNumber(prmsLcRigistration);
    }

    @Override
    public List<PrmsLcRigistration> getLcNolists(int approvedStatus) {
        return prmsLcRigistrationFacade.getApprovedLcNoLists(approvedStatus);
    }

    @Override
    public List<PrmsBankClearance> getBankClearanceReq() {
        return bankClearanceFacade.getBankClearanceReq();
    }

    @Override
    public List<PrmsLcRigistrationAmend> checkAsLcIsAmendedByLcId(PrmsLcRigistration prmsLcRigistration) {
        return prmsLcRigistrationAmendFacade.checkAsLcIsAmendedByLcId(prmsLcRigistration);
    }

    @Override
    public PrmsLcRigistrationAmend getLcAmendedInfoByLcId(PrmsLcRigistration prmsLcRigistration) {
        return prmsLcRigistrationAmendFacade.getLcAmendedInfoByLcId(prmsLcRigistration);
    }

    @Override
    public List<PrmsBankClearance> getBankClearanceSearchParameterLst() {
        return bankClearanceFacade.getBankClearanceSearchParameterLst();
    }
}
