
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsDisposalItems;
import et.gov.eep.mms.entity.MmsDisposalItemsDtl;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsDisposalItemBeanLocal {

    public List<MmsDisposalItems> searchDisposalByParameterPrefix(MmsDisposalItems disposalEntity);

    public ArrayList<MmsDisposalItems> searchByDispNo(MmsDisposalItems disposalEntity);

    public MmsDisposalItems getLastDisposalId();

    public void create(MmsDisposalItems disposalEntity);

    public void edit(MmsDisposalItems disposalEntity);

    public List<MmsDisposalItems> findAllInfo();

    public List<MmsDisposalItems> findAllDispInfo();

    public List<MmsDisposalItems> findAllbyApproveStatus(int Status);

    public List<MmsDisposalItems> getDispInfoByDispNo(MmsDisposalItems dispNo);

    public MmsDisposalItems getSelectedRequest(BigDecimal disposalId);

    public List<MmsDisposalItems> searchDisposalByParameterPrefixAndItemDispPrep(MmsDisposalItems disposalEntity);

    public List<MmsDisposalItems> findDispListByWfStatus(int dStatus);

    public List<MmsDisposalItems> searchAllTransmissionsInfoByPreparerId(Integer requestedBy);

    public List<MmsDisposalItemsDtl> getTagNoLists(MmsDisposalItems dispColectionEntity);
    
    public MmsDisposalItemsDtl findByTag(MmsDisposalItemsDtl dispCollectionDtlEntity);
    
    public List<MmsDisposalItems> getItemdisposalListsByParameter(MmsDisposalItems disposalEntity);

    public List<ColumnNameResolver> getColumnNameList();

}
