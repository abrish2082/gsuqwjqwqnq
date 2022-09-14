/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.prms.entity.PrmsImportShippingInstruct;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ImportShippingInstructBeanLocal {

    public void create(PrmsImportShippingInstruct prmsImportShippingInstruct);

    public void update(PrmsImportShippingInstruct prmsImportShippingInstruct);

    public List<PrmsImportShippingInstruct> searchAllImportShippingInstructByISINo(PrmsImportShippingInstruct prmsImportShippingInstruct);

    public PrmsImportShippingInstruct getSelectedRequest(String id);

    public PrmsImportShippingInstruct getLastISINo();

    public List<PrmsLcRigistration> LcList(int approvedLc);

    public HrDepartments getSelectDepartement(int key);

    public List<PrmsImportShippingInstruct> getImportshipsRequested();

    public List<PrmsLcRigistrationAmend> checkingAsLcIsAmendedByLcId(PrmsLcRigistration prmsLcRigistration);

    public PrmsLcRigistrationAmend getLcAmendedInfoByLcId(PrmsLcRigistration prmsLcRigistration);
    
    public List<PrmsImportShippingInstruct> getShippingListsByParameter(PrmsImportShippingInstruct prmsImportShippingInstruct);

    public List<ColumnNameResolver> getColumnNameList();

}
