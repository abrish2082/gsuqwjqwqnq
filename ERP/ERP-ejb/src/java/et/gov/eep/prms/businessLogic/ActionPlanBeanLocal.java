/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsActionPlan;
import et.gov.eep.prms.entity.PrmsActionPlanDetail;
import et.gov.eep.prms.entity.PrmsLuTaskDescription;
import et.gov.eep.prms.entity.PrmsProjectPlan;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import et.gov.eep.prms.entity.PrmsPurchasePlnDetail;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface ActionPlanBeanLocal {

    public List<PrmsLuTaskDescription> findAll();

    public void deleteActionPlanDet(PrmsActionPlanDetail prmsActionPlanDetail);

    public void save(PrmsActionPlan prmsActionPlan);

    void update(PrmsActionPlan prmsActionPlan);

    public List<PrmsActionPlan> searchByPlanNo(PrmsActionPlan prmsActionPlan);

    public PrmsActionPlan getLastPortNo();

    public PrmsActionPlan getSelectedRequest(String id);

    public PrmsPurchasePlnDetail getPlanNo(String planNo);

    public List<PrmsProjectPlan> getProjectPlanNo();

    public List<PrmsPurchasePlan> getPlanNum();

}
