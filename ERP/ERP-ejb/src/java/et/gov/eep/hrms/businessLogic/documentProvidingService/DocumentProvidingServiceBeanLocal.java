/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.documentProvidingService;

import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.hrms.entity.documentProvidingService.HrDocumentRequests;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author Baya
 */
@Local
public interface DocumentProvidingServiceBeanLocal {


    public void save(HrDocumentRequests hrDocumentRequests);
    public void update(HrDocumentRequests hrDocumentRequests);
    public List<HrDocumentRequests> findAllrequests();

    public HrDocumentRequests loadDocumentRequestDetails(BigDecimal id);

    public List<HrDocumentRequests> findAllAprovedrequests(Integer status);

    public boolean searchByEmpIdAndDocType(HrDocumentRequests hrDocumentRequests);

    public String searchreqdatebyEmpId(HrDocumentRequests hrDocumentRequests);

    public List<HrDocumentRequests> findAllAprovedrequests();

    public List<HrDocumentRequests> findAll();

    public Integer searchByEmpIdAndDocType1(HrDocumentRequests hrDocumentRequests);
    
     public  List<SelectItem> filterByStatus();

     public List<HrDocumentRequests> loadDocumentRequests(int status);

    public List<SelectItem> filterByStatus1();

    public List<SelectItem> filterByStatus2();

    public List<HrAddresses> findAllAddress();

    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses);
    
     public List<HrDocumentRequests> loadDocumentRequests(int status, int UserId);
   
   

  

    

   

    

  
    
}
