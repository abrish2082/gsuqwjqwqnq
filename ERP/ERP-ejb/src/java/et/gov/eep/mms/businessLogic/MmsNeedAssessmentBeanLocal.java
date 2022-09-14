/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsNeedAssessment;

/**
 *
 * @author Minab
 */
@Local
public interface MmsNeedAssessmentBeanLocal {

    public void create(MmsNeedAssessment mmsNeedAssessment);

    public MmsNeedAssessment getNeededAssessmentinfo(MmsNeedAssessment mmsNeedAssessment);

    public ArrayList<MmsNeedAssessment> getNeeddedAssessentByStoreAndYear(MmsNeedAssessment papmsNeedAssessment);

    public void edit(MmsNeedAssessment mmsNeedAssessment);

    public List<MmsNeedAssessment> searchByParameterBudgetYearAndDepartmentIDAndProcessedBy(MmsNeedAssessment mmsNeedAssessment);

    public List<MmsNeedAssessment> searchByParameterDepartmentIdAndProcessedBy(MmsNeedAssessment needAssessmentEntity);

    public List<MmsNeedAssessment> searchByParameterBudgetTypeAndDepartmentIDAndProcessedBy(MmsNeedAssessment needAssessmentEntity);

    public List<MmsNeedAssessment> searchByAllParameter(MmsNeedAssessment needAssessmentEntity);

    public List<MmsNeedAssessment> findNsListByWfStatus(int status);

    public List<MmsNeedAssessment> findNsListForCheckerByWfStatus(int prepareStatus, int approverRejectstatus);

    public ArrayList<MmsNeedAssessment> getNeeddedAssessentByStoreAndYearAndCheckedStatus(MmsNeedAssessment papmsNeedAssessment);

    public ArrayList<MmsNeedAssessment> getNeeddedAssessentByBudgetYearAndCheckedStatus(MmsNeedAssessment papmsNeedAssessment);

    public List<String> getMmsNeedColumnNameList();

    public List<MmsNeedAssessment> getNeedListsByParameter(ColumnNameResolver columnNameResolver, MmsNeedAssessment needAssessmentEntity, String columnValue);
}
