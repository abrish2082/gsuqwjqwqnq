
package et.gov.eep.mms.businessLogic;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.fcms.entity.fixedasset.FmsDprOfficeAsset;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.mms.entity.MmsFixedAssetReturn;
import et.gov.eep.mms.entity.MmsFixedassetRegstDetail;
import et.gov.eep.mms.entity.MmsFixedassetRegstration;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsSiv;
import et.gov.eep.mms.entity.MmsSivDetail;
import et.gov.eep.mms.entity.MmsStorereq;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface MmsFixedAssetRegistrationBeanLocal {

    /**
     *
     * @param fixedAssetInformation
     */
    void create(MmsFixedassetRegstration fixedAssetInformation);

    /**
     *
     * @param fixedAssetInformation
     */
    void edit(MmsFixedassetRegstration fixedAssetInformation);

    public List<MmsFixedassetRegstration> findALL();

    /**
     *
     * @return
     */
    public MmsFixedassetRegstration getLastFARNo();

    public List<MmsFixedassetRegstration> getFixedAssetInfoByDept(MmsFixedassetRegstration fixedassetRegstration);

    public List<MmsFixedassetRegstration> getFixedAssetInfoByRecivedBy(MmsFixedassetRegstration fixedassetRegstration);

    public List<MmsFixedassetRegstration> searchFARByParameterPrefix(MmsFixedassetRegstration fixedAssetRegEntity);

    public MmsFixedassetRegstration getSelectedRequest(Integer id);

    public MmsFixedassetRegstration findByMasterId(MmsFixedassetRegstration fixedAsserRegEntity);

    //public FmsDprOfficeAsset findBookvalue(String tagno);
    public MmsFixedassetRegstration findByNameAndTagNo(MmsFixedassetRegstration fixedassetRegstration, MmsFixedassetRegstDetail fixedAssetRegDtlEntity);

    public List<MmsFixedassetRegstration> findFarListByWfStatus(int PREPARE_VALUE);

    public List<MmsFixedassetRegstration> searchAllFarInfoByPreparerId(Integer approvedBy);

    public List<FmsDprOfficeAsset> findBookvalue(String tagNo);

    public MmsSiv getFixedSrNo(MmsSiv sivs);

    public MmsItemRegistration getcode(MmsItemRegistration itemRegistrationEntity);

    public List<MmsSiv> getSrNoLists(MmsSiv sivs);

    public List<MmsSivDetail> getItemBySivId(MmsSiv sivs);

    public List<MmsStorereq> getsrNoListsByTag(HrDepartments hrDepartmentsEntity);

    public List<MmsFixedassetRegstration> getMmsFixedAssetRColumnNameList();

    public List<MmsFixedassetRegstration> getFAListsByParameter(MmsFixedassetRegstration fixedAssetRegEntity);
    
   public List<MmsFixedassetRegstration> getAssetregstrationListsByParameter(MmsFixedassetRegstration fixedAssetRegEntity);

    public List<ColumnNameResolver> getColumnNameList();
}
