
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsFixedAssetReturnDtl;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author w_station
 */
@Stateless
public class MmsFixedAssetReturnDtlFacade extends AbstractFacade<MmsFixedAssetReturnDtl> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsFixedAssetReturnDtlFacade() {
        super(MmsFixedAssetReturnDtl.class);
    }

    public List<MmsFixedAssetReturnDtl> searchBytag1(MmsFixedAssetReturnDtl returnDtlEntity) {
        String tagNo = returnDtlEntity.getItemCode();
        Query query1 = em.createNativeQuery("SELECT * \n" +
"                FROM MMS_FIXED_ASSET_RETURN_DTL   fat   \n" +
"                INNER FMS_DPR_OFFICE_ASSET  fifat \n" +
"                ON fat.ITEM_CODE=fifat.TAG_NO\n" +
"                WHERE fifat.STATUS ='1' AND fat.SS_NO Like '"+tagNo+"' ",MmsFixedAssetReturnDtl.class);
        
        ArrayList<MmsFixedAssetReturnDtl> listOf= new ArrayList<>(query1.getResultList());
       
       return listOf;
               
    }
    
}
