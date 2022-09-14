package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsDisposalItems;
import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsDisposalBeanLocal {

    public MmsDisposal getLastDisposalId();

    public List<MmsDisposal> searchDisposalByParameterPrefix(MmsDisposal dispEntity);

    public void create(MmsDisposal dispEntity);

    public void edit(MmsDisposal dispEntity);

    public MmsDisposal getSelectedRequest(BigDecimal dispId);

    public List<MmsDisposal> searchDisposalByParameterPrefixAndDispPrep(MmsDisposal dispEntity);

    public List<MmsDisposal> findDisposalListByWfStatus(int PREPARE_VALUE);

    public List<MmsDisposal> searchAllDisposalInfoByPreparerId(Integer proposedBy);

    public List<MmsDisposal> getMmsDisposeColumnNameList();

    public List<MmsDisposal> getDisposeListsByParameter(MmsDisposal dispEntity);
    
    public List<MmsDisposal> getdisposalListsByParameter(MmsDisposal dispEntity);

    public List<ColumnNameResolver> getColumnNameList();
    
    
     

}
