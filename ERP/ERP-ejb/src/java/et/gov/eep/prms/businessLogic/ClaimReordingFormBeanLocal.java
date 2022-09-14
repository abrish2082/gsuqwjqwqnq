/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.prms.entity.PrmsClaimDetail;
import et.gov.eep.prms.entity.PrmsClaimRecordingForm;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsContractAmendment;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ClaimReordingFormBeanLocal {

    public List<PrmsContract> listOfContractNO();

    public void create(PrmsClaimRecordingForm prmsClaimRecordingForm);

    public void update(PrmsClaimRecordingForm prmsClaimRecordingForm);

    public List<PrmsClaimRecordingForm> searchAllMrktNo(PrmsClaimRecordingForm prmsClaimRecordingForm);

    public List<FmsLuCurrency> getCurrencyName();

    public HrDepartments getSelectDepartement(int key);

    public PrmsClaimRecordingForm getLastClaimNo();

    public PrmsClaimRecordingForm getSelectedRequest(BigDecimal claimId);

    public List<PrmsClaimRecordingForm> findAllClaimInfo();

    public List<PrmsClaimRecordingForm> findByStatus(int Stataus);

    public List<PrmsClaimRecordingForm> getClaimLists();

    public List<PrmsClaimRecordingForm> searchByClaimNo(PrmsClaimRecordingForm prmsClaimRecordingForm);

    public List<PrmsContractAmendment> checkAmendOrNot(PrmsContract prmsContract);

    public PrmsContractAmendment getContractAmendment(PrmsContract prmsContract);

    public List<PrmsClaimRecordingForm> getMmsClaimRColumnNameList();

    public List<PrmsClaimRecordingForm> getClaimRListsByParameter(PrmsClaimRecordingForm prmsClaimRecordingForm);
    
    public List<PrmsClaimRecordingForm> getClaimListsByParameter(PrmsClaimRecordingForm prmsClaimRecordingForm);

    public List<ColumnNameResolver> getColumnNameList();
}
