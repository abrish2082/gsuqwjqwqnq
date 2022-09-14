/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.entity.PrmsProjectPlan;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ProjectPlanBeanLocal {

    public void save(PrmsProjectPlan prmsProjectPlan);

    public void upDate(PrmsProjectPlan prmsProjectPlan);

    public List<PrmsProjectPlan> searchByPlanNo(PrmsProjectPlan prmsProjectPlan);

    public List<PrmsProjectPlan> searchByPlanNo(int status, int UserId);

    public PrmsProjectPlan getProjectPlanId(String id);

    public PrmsProjectPlan getProjectPlan();

    public List<PrmsProjectPlan> searchByPlanNo_();

    public List<PmsCreateProjects> getProjectId();

    public String getGoodsOrServiceOrWorkProjectSeqNo(String purchaseType);

    public List<PrmsProjectPlan> getParamNameList();

}
