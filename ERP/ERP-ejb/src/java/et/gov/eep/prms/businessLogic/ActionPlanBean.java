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
import et.gov.eep.prms.mapper.PrmsActionPlanDetailFacade;
import et.gov.eep.prms.mapper.PrmsActionPlanFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class ActionPlanBean implements ActionPlanBeanLocal {

    @EJB
    PrmsActionPlanDetailFacade detailFacade;
    @EJB
    PrmsActionPlanFacade actionPlanFacade;

    @Override
    public void deleteActionPlanDet(PrmsActionPlanDetail prmsActionPlanDetail) {
        detailFacade.remove(prmsActionPlanDetail);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void save(PrmsActionPlan prmsActionPlan) {
        actionPlanFacade.create(prmsActionPlan);
    }

    @Override
    public void update(PrmsActionPlan prmsActionPlan) {
        actionPlanFacade.edit(prmsActionPlan);
    }

    @Override
    public List<PrmsActionPlan> searchByPlanNo(PrmsActionPlan prmsActionPlan) {
        return actionPlanFacade.getActionPlanNo(prmsActionPlan);
    }

    @Override
    public List<PrmsLuTaskDescription> findAll() {
        return actionPlanFacade.getTaskDesc();
    }

    @Override
    public PrmsActionPlan getLastPortNo() {
        return actionPlanFacade.getLastPortNo();
    }

    @Override
    public PrmsActionPlan getSelectedRequest(String id) {
        return actionPlanFacade.getSelectedRequest(id);
    }

    @Override
    public PrmsPurchasePlnDetail getPlanNo(String planNo) {
        return actionPlanFacade.getPlanNo(planNo);
    }

    @Override
    public List<PrmsProjectPlan> getProjectPlanNo() {
        return actionPlanFacade.getProjectPlanNo();
    }

    @Override
    public List<PrmsPurchasePlan> getPlanNum() {
        return actionPlanFacade.getPlanNum();
    }
}
