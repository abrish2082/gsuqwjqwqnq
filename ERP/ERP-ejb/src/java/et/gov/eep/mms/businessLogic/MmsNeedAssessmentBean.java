/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import et.gov.eep.mms.mapper.MmsNeedAssessmentFacade;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsNeedAssessmentBean implements MmsNeedAssessmentBeanLocal {

    @EJB
    MmsNeedAssessmentFacade needAssessmentFacade;

    @Override
    public MmsNeedAssessment getNeededAssessmentinfo(MmsNeedAssessment mmsNeedAssessment) {
        return needAssessmentFacade.getNeededAssessmentinfo(mmsNeedAssessment);
    }

    @Override
    public void create(MmsNeedAssessment mmsNeedAssessment) {
        needAssessmentFacade.create(mmsNeedAssessment);
    }

    @Override
    public void edit(MmsNeedAssessment mmsNeedAssessment) {
        needAssessmentFacade.edit(mmsNeedAssessment);
    }

    @Override
    public List<MmsNeedAssessment> searchByParameterBudgetYearAndDepartmentIDAndProcessedBy(MmsNeedAssessment mmsNeedAssessment) {
        return needAssessmentFacade.searchByParameterBudgetYearAndDepartmentIDAndProcessedBy(mmsNeedAssessment);
    }

    @Override
    public ArrayList<MmsNeedAssessment> getNeeddedAssessentByStoreAndYear(MmsNeedAssessment papmsNeedAssessment) {
        return needAssessmentFacade.getNeeddedAssessentByStoreAndYear(papmsNeedAssessment);
    }

    @Override
    public ArrayList<MmsNeedAssessment> getNeeddedAssessentByStoreAndYearAndCheckedStatus(MmsNeedAssessment papmsNeedAssessment) {
        return needAssessmentFacade.getNeeddedAssessentByStoreAndYearAndCheckedStatus(papmsNeedAssessment);
    }

    @Override
    public ArrayList<MmsNeedAssessment> getNeeddedAssessentByBudgetYearAndCheckedStatus(MmsNeedAssessment papmsNeedAssessment) {
        return needAssessmentFacade.findByBudgetYearAndCheckedStatus(papmsNeedAssessment);
    }

    @Override
    public List<MmsNeedAssessment> searchByParameterDepartmentIdAndProcessedBy(MmsNeedAssessment needAssessmentEntity) {
        return needAssessmentFacade.searchByParameterDepatementIdAndProcessedBy(needAssessmentEntity);
    }

    @Override
    public List<MmsNeedAssessment> searchByParameterBudgetTypeAndDepartmentIDAndProcessedBy(MmsNeedAssessment needAssessmentEntity) {
        return needAssessmentFacade.searchByParameterBudgetTypeAndDepartmentIDAndProcessedBy(needAssessmentEntity);
    }

    @Override
    public List<MmsNeedAssessment> searchByAllParameter(MmsNeedAssessment needAssessmentEntity) {
        return needAssessmentFacade.searchByAllParameter(needAssessmentEntity);
    }

    @Override
    public List<MmsNeedAssessment> findNsListByWfStatus(int status) {
        return needAssessmentFacade.findNsListByWfStatus(status);
    }

    @Override
    public List<MmsNeedAssessment> findNsListForCheckerByWfStatus(int prepareStatus, int approverRejectstatus) {
        return needAssessmentFacade.findNsListForCheckerByWfStatus(prepareStatus, approverRejectstatus);
    }

    @Override
    public List<String> getMmsNeedColumnNameList() {
       return needAssessmentFacade.getMmsNeedColumnNameList();  
}

    @Override
    public List<MmsNeedAssessment> getNeedListsByParameter(ColumnNameResolver columnNameResolver, MmsNeedAssessment needAssessmentEntity, String columnValue) {
        return needAssessmentFacade.getNeedListsByParameter(columnNameResolver, needAssessmentEntity, columnValue);
    }
}
