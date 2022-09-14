/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsInsuranceNotToBank;
import et.gov.eep.prms.entity.PrmsInsuranceRequisition;
import et.gov.eep.prms.entity.PrmsPortEntry;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface InsuranceNotificationToBankBeanLocal {

    public PrmsInsuranceNotToBank generateNotificationNo();

    public ArrayList<PrmsInsuranceNotToBank> fromServiceProvider();

    public ArrayList<PrmsInsuranceNotToBank> fromServiceProDetail();

    public ArrayList<PrmsPortEntry> fromPortEntry();

    public ArrayList<PrmsInsuranceNotToBank> fromInsuranceReg();

    public List<PrmsInsuranceNotToBank> searchBankNotificationNo(PrmsInsuranceNotToBank prmsInsuranceNotToBank);

    public void create(PrmsInsuranceNotToBank prmsInsuranceNotToBank);

    public void edit(PrmsInsuranceNotToBank prmsInsuranceNotToBank);

    public PrmsInsuranceRequisition autoGenerate(PrmsInsuranceRequisition insuranceregistration);

    public PrmsInsuranceNotToBank getSelectedRow(String insuranceNotificationNo);

    public List<PrmsInsuranceNotToBank> getParamNameList();

}
