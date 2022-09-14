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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PreliminaryBeanLocal {

    public List<PrmsBid> getBidCreteriaList();

    public List<PrmsBid> getBidCriteriaList(String bidCriteria);

    public List<PrmsBid> getBidNoLst();

    public List<PrmsBid> getSplit();

 

    public void save(PrmsPreminilaryEvaluation prmsPreminirayEval);

    public void Edit(PrmsPreminilaryEvaluation prmsPreminirayEval);

    public List<PrmsPreminilaryEvaluation> getBidPrelmnryEvNo(PrmsPreminilaryEvaluation prmsPreminirayEval);

    public PrmsPreminilaryEvaluation getProjectPlanId(String id);
     public List<PrmsPreminilaryEvaluation> getBidNo();

    public PrmsPreminilaryEvaluation getPreliminarNo();

    public List<PrmsSupplyProfile> getSupplierName(String toString);

    public boolean findDup(PrmsPreminilaryEvalutionDt preminilaryEvalutionDt);

    public List<PrmsPreminilaryEvaluation> getPrelmnryOnList();

    public List<PrmsPreminilaryEvaluation> getParamNameList();

}
