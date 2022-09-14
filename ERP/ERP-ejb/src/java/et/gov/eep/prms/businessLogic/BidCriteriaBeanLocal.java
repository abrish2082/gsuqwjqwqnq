/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsBid;
import et.gov.eep.prms.entity.PrmsBidCriteria;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface BidCriteriaBeanLocal {

    public List<PrmsBidCriteria> searchCriterialNo(PrmsBidCriteria prmsBidCriteria);

    public ArrayList<PrmsBid> getBidNo();

    public void save(PrmsBidCriteria prmsBidCriteria);

    public void update(PrmsBidCriteria prmsBidCriteria);

    public PrmsBidCriteria getBidCriteriaId(String id);

    public PrmsBidCriteria getBidCriteria();

    public List<PrmsBidCriteria> getParamNameList();

}
