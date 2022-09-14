/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsPreminilaryEvaluation;
import et.gov.eep.prms.entity.PrmsPreminilaryEvalutionDt;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.mapper.PrmsPreminilaryEvaluationFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class PreliminaryBean implements PreliminaryBeanLocal {

    @EJB
    PrmsPreminilaryEvaluationFacade preminirayEvalFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<PrmsBid> getBidCreteriaList() {
        return preminirayEvalFacade.getBidCreteriaList();
    }

    @Override
    public List<PrmsBid> getBidCriteriaList(String bidCriteria) {
        return preminirayEvalFacade.getBidCriteriList(bidCriteria);
    }

    @Override
    public List<PrmsBid> getBidNoLst() {
        return preminirayEvalFacade.getBidNoLst();
    }

    @Override
    public List<PrmsBid> getSplit() {
        return preminirayEvalFacade.getSplitCrtreia();
    }

    @Override
    public void save(PrmsPreminilaryEvaluation prmsPreminirayEval) {
        preminirayEvalFacade.create(prmsPreminirayEval);
    }

    @Override
    public void Edit(PrmsPreminilaryEvaluation prmsPreminirayEval) {
        preminirayEvalFacade.edit(prmsPreminirayEval);
    }

    @Override
    public List<PrmsPreminilaryEvaluation> getBidPrelmnryEvNo(PrmsPreminilaryEvaluation prmsPreminirayEval) {
        return preminirayEvalFacade.getBidPrelmnryEvNo(prmsPreminirayEval);
    }

    @Override
    public PrmsPreminilaryEvaluation getProjectPlanId(String id) {
        return preminirayEvalFacade.getSelectedId(id);
    }

    @Override
    public List<PrmsPreminilaryEvaluation> getBidNo() {
        return preminirayEvalFacade.getBidNo();
    }

    @Override
    public PrmsPreminilaryEvaluation getPreliminarNo() {
        return preminirayEvalFacade.getPreliminarNo();
    }

    @Override
    public List<PrmsSupplyProfile> getSupplierName(String supplierName) {
        return preminirayEvalFacade.getSupplierName(supplierName);
    }

    @Override
    public boolean findDup(PrmsPreminilaryEvalutionDt preminilaryEvalutionDt) {
        return preminirayEvalFacade.findDup(preminilaryEvalutionDt);
    }

    @Override
    public List<PrmsPreminilaryEvaluation> getPrelmnryOnList() {
        return preminirayEvalFacade.getPrelmnryOnList();
    }

    @Override
    public List<PrmsPreminilaryEvaluation> getParamNameList() {
       return preminirayEvalFacade.getParamNameList();
    }
}
