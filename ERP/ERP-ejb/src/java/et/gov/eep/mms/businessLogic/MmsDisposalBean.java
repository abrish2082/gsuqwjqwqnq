package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsDisposalItems;
import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import et.gov.eep.mms.mapper.MmsDisposalFacade;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsDisposalBean implements MmsDisposalBeanLocal {

    @EJB
    MmsDisposalFacade dispFacade;

    @Override
    public MmsDisposal getLastDisposalId() {
        return dispFacade.getLastDisposalId();
    }

    @Override
    public List<MmsDisposal> searchDisposalByParameterPrefix(MmsDisposal dispEntity) {
        return dispFacade.searchDisposalByParameterPrefix(dispEntity);
    }

    @Override
    public void create(MmsDisposal dispEntity) {
        dispFacade.create(dispEntity);
    }

    @Override
    public void edit(MmsDisposal dispEntity) {
        dispFacade.edit(dispEntity);
    }

    @Override
    public MmsDisposal getSelectedRequest(BigDecimal dispId) {
        return dispFacade.getSelectedRequest2(dispId);
    }

    @Override
    public List<MmsDisposal> searchDisposalByParameterPrefixAndDispPrep(MmsDisposal dispEntity) {
        return dispFacade.searchDisposalByParameterPrefixAndDispPrep(dispEntity);
    }

    @Override
    public List<MmsDisposal> findDisposalListByWfStatus(int PREPARE_VALUE) {
        return dispFacade.findDisposalListByWfStatus(PREPARE_VALUE);
    }

    @Override
    public List<MmsDisposal> searchAllDisposalInfoByPreparerId(Integer proposedBy) {
        return dispFacade.searchAllDisposalInfoByPreparerId(proposedBy);
    }

    @Override
    public List<MmsDisposal> getMmsDisposeColumnNameList() {
        return dispFacade.getMmsDisposeColumnNameList();
    }

    @Override
    public List<MmsDisposal> getDisposeListsByParameter(MmsDisposal dispEntity) {
        return dispFacade.getDisposeListsByParameter(dispEntity);
    }
 @Override
    public List<MmsDisposal> getdisposalListsByParameter(MmsDisposal dispEntity) {
        return dispFacade.getdisposalListsByParameter(dispEntity);
    }

    @Override
    public List<ColumnNameResolver> getColumnNameList() {
        return dispFacade.getColumnNameList();
    }
   

}
