
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.mms.entity.MmsDisposal;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author w_station
 */
@Local
public interface MmsFixedAssetReturnBeanLocal {

    public ArrayList<MmsFixedAssetReturn> searchByReturnNo(MmsFixedAssetReturn returnEntity);

    public MmsFixedAssetReturn getLastReturnId();

    public List<MmsFixedAssetReturn> searchReturnByParameterPrefix(MmsFixedAssetReturn returnEntity);

    public List<MmsFixedAssetReturn> searchReturnByParameterPrefixAndProcessedBy(MmsFixedAssetReturn returnEntity);

    public void edit(MmsFixedAssetReturn returnEntity);

    public void create(MmsFixedAssetReturn returnEntity);

    public List<MmsFixedAssetReturn> findAllDept();

    public List<MmsFixedAssetReturn> findAllInfo();

    public List<MmsFixedAssetReturn> searchByDept(MmsFixedAssetReturn returnEntity);

    public MmsFixedAssetReturn getSelectedRequest(Integer farId);

    public List<MmsFixedAssetReturn> findFarListByWfStatus(int status);

    public List<MmsFixedAssetReturn> findFarListForCheckerByWfStatus(int prepareStatus, int approverRejectstatus);

    public List<MmsFixedAssetReturn> searchAllFarInfoByPreparerId(Integer processedBy);

    public List<MmsFixedassetRegstDetail> getReturnByDepId(MmsFixedassetRegstration fixedassetRegstration);

    public List<Integer>  getEmpIdByName(String returnby);
    public MmsFixedassetRegstDetail gettingTagInfo(String tingOthersByTagNo);
    
    public List<MmsFixedAssetReturn> getReturnListsByParameter(MmsFixedAssetReturn returnEntity);

    public List<ColumnNameResolver> getColumnNameList();

}

