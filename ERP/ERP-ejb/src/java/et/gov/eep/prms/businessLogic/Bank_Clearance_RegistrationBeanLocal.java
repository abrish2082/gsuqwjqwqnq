/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBankClearance;
import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

@Local
public interface Bank_Clearance_RegistrationBeanLocal {

    public void create(PrmsBankClearance bankClearanceRegistration);

    public void update(PrmsBankClearance bankClearanceRegistration);

    public List<PrmsBankClearance> findClearanceNo(PrmsBankClearance bankClearanceRegistration);

    public PrmsBankClearance getClearanceNo(PrmsBankClearance bankClearanceRegistration);

    public PrmsBankClearance getLastClearanceNo();

    public PrmsBankClearance getSelectedClearanceNo(String clearanceNo);

    public PrmsSupplyProfile getVondorName(PrmsSupplyProfile prmsSupplyProfile);

    public PrmsContract getContNumber(PrmsContract prmsContract);

    public ArrayList<PrmsLcRigistration> lcNosItem();

    public PrmsLcRigistration getLcAmount(PrmsLcRigistration prmsLcRigistration);

    public List<PrmsLcRigistration> getLcNolists(int approvedStatus);

    public List<PrmsBankClearance> getBankClearanceReq();

    public List<PrmsLcRigistrationAmend> checkAsLcIsAmendedByLcId(PrmsLcRigistration prmsLcRigistration);

    public PrmsLcRigistrationAmend getLcAmendedInfoByLcId(PrmsLcRigistration prmsLcRigistration);

    public List<PrmsBankClearance> getBankClearanceSearchParameterLst();

}
