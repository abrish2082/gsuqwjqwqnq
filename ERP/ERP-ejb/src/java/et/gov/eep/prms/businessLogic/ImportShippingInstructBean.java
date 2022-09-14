/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.prms.entity.PrmsImportShippingInstruct;
import et.gov.eep.prms.entity.PrmsLcRigistration;
import et.gov.eep.prms.mapper.PrmsImportShippingInstructFacade;
import et.gov.eep.prms.mapper.PrmsLcRigistrationFacade;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.prms.entity.PrmsLcRigistrationAmend;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ImportShippingInstructBean implements ImportShippingInstructBeanLocal {

    @EJB
    PrmsImportShippingInstructFacade prmsImportShippingInstructFacade;
    @EJB
    PrmsLcRigistrationFacade prmsLcRigistrationFacade;
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;

    @Override
    public void create(PrmsImportShippingInstruct prmsImportShippingInstruct) {
        prmsImportShippingInstructFacade.create(prmsImportShippingInstruct);
    }

    @Override
    public void update(PrmsImportShippingInstruct prmsImportShippingInstruct) {
        prmsImportShippingInstructFacade.edit(prmsImportShippingInstruct);
    }

    @Override
    public List<PrmsImportShippingInstruct> searchAllImportShippingInstructByISINo(PrmsImportShippingInstruct prmsImportShippingInstruct) {
        return prmsImportShippingInstructFacade.getgetISINo(prmsImportShippingInstruct);
    }

    @Override
    public PrmsImportShippingInstruct getSelectedRequest(String id) {
        return prmsImportShippingInstructFacade.getSelectedId(id);
    }

    @Override
    public PrmsImportShippingInstruct getLastISINo() {
        return prmsImportShippingInstructFacade.getLastISINo();
    }

    @Override
    public List<PrmsLcRigistration> LcList(int approvedLc) {
        return prmsImportShippingInstructFacade.findApprovedLc(approvedLc);
    }

    @Override
    public HrDepartments getSelectDepartement(int key) {
        return prmsImportShippingInstructFacade.getHrNames(key);
    }

    @Override
    public List<PrmsImportShippingInstruct> getImportshipsRequested() {
        return prmsImportShippingInstructFacade.getImportshipsRequested();
    }

    @Override
    public List<PrmsLcRigistrationAmend> checkingAsLcIsAmendedByLcId(PrmsLcRigistration prmsLcRigistration) {
        return prmsImportShippingInstructFacade.checkingAsLcIsAmendedByLcId(prmsLcRigistration);
    }

    @Override
    public PrmsLcRigistrationAmend getLcAmendedInfoByLcId(PrmsLcRigistration prmsLcRigistration) {
        return prmsImportShippingInstructFacade.getLcAmendedInfoByLcId(prmsLcRigistration);
    }
    
 @Override
    public List<PrmsImportShippingInstruct> getShippingListsByParameter(PrmsImportShippingInstruct prmsImportShippingInstruct) {
        return prmsImportShippingInstructFacade.getShippingListsByParameter(prmsImportShippingInstruct);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return prmsImportShippingInstructFacade.getColumnNameList();
    }
}
